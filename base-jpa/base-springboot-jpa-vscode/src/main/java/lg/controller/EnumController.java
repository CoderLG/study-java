package lg.controller;

import io.swagger.annotations.Api;
import lg.common.RestError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: LG
 * date: 2019-09-29 19:11
 * desc:
 *
 * 枚举初始化的时候
 * 调用自己的构建方法
 * 还会调用tostring方法
 *
 * 后续不应该再有set 这是应该的用法
 *
 * return  RestError.INPUT_ERROR
 *      返回值为："INPUT_ERROR"
 *  return  RestError.INPUT_ERROR.toString()
 *      返回值为：{"code":"22200201","message":"输入参数异常!"}
 *      这个过程会再次调用toString 方法
 */
@Api(tags = "深入枚举")
@RestController
public class EnumController {

    @GetMapping("setReason")
    public String setReason(){
//        RestError ss = RestError.INPUT_ERROR.setReason("这样用正确么");
//        System.out.println(ss.getReason());
//        这样用不正确
        return  RestError.INPUT_ERROR.toString();
    }

    @GetMapping("getReasonRes")
    public RestError getReasonRes(){
        return  RestError.INPUT_ERROR;
    }

    @GetMapping("getReasonStr")
    public String getReasonStr(){
        return  RestError.INPUT_ERROR.toString();
    }

}
