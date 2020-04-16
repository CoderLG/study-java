package lg.controller;

import lg.domain.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: LG
 * date: 2019-10-28 20:17
 * desc:
 */

@RestController
public class JsonController {

    @PostMapping("testJackJson")
    public Student testJackJson(@RequestBody  Student student){
        return student;
    }

}
