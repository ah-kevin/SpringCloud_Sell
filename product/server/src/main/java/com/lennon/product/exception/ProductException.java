package com.lennon.product.exception;

import com.lennon.product.enums.ResultEnum;

public class ProductException extends RuntimeException {
    private Integer code;

    public ProductException(ResultEnum resultEnum) {
        super((resultEnum.getMessage()));
        this.code =resultEnum.getCode();
    }
    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
