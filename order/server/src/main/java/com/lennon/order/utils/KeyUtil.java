package com.lennon.order.utils;

import java.util.Random;

public class KeyUtil {
    /**
     * 生成唯一的主见
     * 格式:时间+随机数
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}