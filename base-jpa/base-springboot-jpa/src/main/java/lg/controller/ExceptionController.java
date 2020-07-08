package lg.controller;

import io.swagger.annotations.Api;
import lg.exception.RestError;
import lg.dvo.ImageParamsVo;
import lg.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

@RestController
@RequestMapping("/exceptionController")
@Slf4j
@Api(tags = "异常测试")
public class ExceptionController {
    @GetMapping("/demo/")
    public ResponseEntity<ImageParamsVo> sayHello(@Valid ImageParamsVo imageParamsVo) {
        if (imageParamsVo.getGeo().equals("123")) {
            throw new RestException(RestError.UNKNOW_ERROR);
        } else if(imageParamsVo.getGeo().equals("456")){
            throw new RestException(RestError.INPUT_ERROR);
        }else if(imageParamsVo.getGeo().equals("789")){
           //自己分风格的异常处理
            log.error("自己异常的处理方法");
            throw new RestException(RestError.INPUT_ERROR);
        }else{
            return ResponseEntity.ok(imageParamsVo);
        }

    }


}
