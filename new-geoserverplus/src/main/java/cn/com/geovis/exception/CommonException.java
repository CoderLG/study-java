package cn.com.geovis.exception;

import lombok.Getter;

/**
 * author: LG
 * date: 2019-10-22 10:21
 * desc:
 */
@Getter
public class CommonException extends RuntimeException{

    private static final long serialVersionUID = 4629642724634221942L;

    private String restError;
    public CommonException(String restError){
        this.restError = restError;
    }

}
