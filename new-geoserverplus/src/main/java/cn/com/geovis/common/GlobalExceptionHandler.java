package cn.com.geovis.common;


import cn.com.geovis.exception.CommonException;
import cn.com.geovis.exception.GeoException;
import cn.com.geovis.exception.ImageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 所有的返回 ResponseEntity
 *      都应该是状态码 加返回值
 *
 * catch 到的异常 打log  给自己看的信息
 *      log.error("给自己看的信息",ex);
 *
 * 全局异常得到的异常 打log  给前端看的信息
 *      log.error(RestError.INPUT_ERROR.getMessage(),ex);
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    /**
     * 通用异常处理
     *
     * 出现问题的地方
     * 打log   这样问题异常 描述的更清楚
     *
     * 发布倾斜摄影 实例
     *
     * @param response
     * @param ex
     * @return
     *
     */
    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public String handleCommonException(HttpServletResponse response, Exception ex) {

        response.setStatus(500);
        CommonException e = (CommonException) ex;
        String restError = e.getRestError();
        return restError;

    }



    /**
     * DMDefinedException的异常的处理
     * @param response
     * @param ex
     * @return
     *
     * 直接返回枚举
     */
    @ResponseBody
    @ExceptionHandler(GeoException.class)
    public String handleGeoException(HttpServletResponse response, Exception ex) {

        response.setStatus(500);
        log.error(RestError.INPUT_ERROR.getMessage(),ex);
        return RestError.INPUT_ERROR.toString();

    }

    /**
     * GeoDataDefinedException的异常处理
     * @param
     * @param ex
     * @return
     *
     * 返回异常类
     */
    @ResponseBody
    @ExceptionHandler(ImageException.class)
    public ResponseEntity handleImageException(Exception ex) {
        log.warn(ex.getMessage(), ex);
        ImageException apiException = (ImageException) ex;
        Map<String, Object> returnError = new HashMap();
        returnError.put("code", apiException.getCode());
        returnError.put("message", apiException.getMessage());

        return new ResponseEntity(returnError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 参数的验证处理
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity handleBindException(final BindException ex) {
        log.error(ex.getMessage(),ex);

        String errorMsg = ex.getFieldErrors().stream().map(this::getFieldErrorMessage).collect(Collectors.joining(" "));
        Map<String, Object> returnError = new HashMap();
        returnError.put("code",  RestError.INPUT_ERROR.getCode());
        returnError.put("message",errorMsg);

        return new ResponseEntity(returnError, HttpStatus.BAD_REQUEST);
    }

    private String getFieldErrorMessage(FieldError err) {
        return err.getField() + " " + err.getDefaultMessage() + "!";
    }


    /**
     * 没有匹配到， 此类匹配
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletResponse response, Exception ex) {
        response.setStatus(500);
        log.error(ex.getMessage(),ex);
        return RestError.UNKNOW_ERROR.toString();

    }


}