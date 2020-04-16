package lg.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: LG
 * date: 2020-03-09 13:17
 * desc:
 *
 */


@RestController
@RequestMapping("/bat")
@Api(tags = "下载B站视频")
public class BatController {

    /**
     * 创建下载脚本
     * @return
     */

    @RequestMapping("createScript")
    public String createDownloadScript(){
        boolean flag = false;


        return "success";

    }

}
