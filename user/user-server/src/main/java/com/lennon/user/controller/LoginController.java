package com.lennon.user.controller;

import com.lennon.user.VO.ResultVO;
import com.lennon.user.constant.CookieConstant;
import com.lennon.user.constant.RedisConstant;
import com.lennon.user.dataobject.UserInfo;
import com.lennon.user.enums.ResultEnum;
import com.lennon.user.enums.RoleEnum;
import com.lennon.user.service.UserService;
import com.lennon.user.utils.CookieUtil;
import com.lennon.user.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response){
        //1. openid和数据库里的数据匹配
        UserInfo userInfo = userService.findByOpenId(openid);
        if(userInfo==null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2. 判断角色,
        if(RoleEnum.BUYER.getCode() != userInfo.getRole()){
            return ResultVOUtil.error(ResultEnum.ROLE_FAIL);
        }
        //3 .设置cookie
        CookieUtil.set(response, CookieConstant.openId,openid,CookieConstant.expire);
        return  ResultVOUtil.success();
    }
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletRequest request,
                           HttpServletResponse response){
        // 判断是否已经登录
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie!=null &&
               ! StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVOUtil.success();
        }
        //1. openid和数据库里的数据匹配
        UserInfo userInfo = userService.findByOpenId(openid);
        if(userInfo==null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2. 判断角色,
        if(RoleEnum.SELLER.getCode() != userInfo.getRole()){
            return ResultVOUtil.error(ResultEnum.ROLE_FAIL);
        }

        //3. redis存 key=UUID,value=xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,token),
                openid,
                expire,
                TimeUnit.SECONDS);
        //4 .设置cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.expire);
        return  ResultVOUtil.success();
    }
}
