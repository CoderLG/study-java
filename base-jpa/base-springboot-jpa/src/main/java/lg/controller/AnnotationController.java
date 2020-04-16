package lg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lg.dvo.CarVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * author: LG
 * date: 2019-08-18 18:32
 * desc:
 * get put post delete 四种请求中只有get 不能传递body
 *
 * @RequestParam(name = "name",defaultValue = "lg" ,required = true)
 *
 * @PathVariable("name") String id  注解中name为准
 *
 * catch到异常后下面的代码继续运行
 *
 * 容器中的所有bean都是单例的
 * 上面修改，下次调用的就是修改之后的了
 */

@Controller
@Slf4j
@RequestMapping("/Annotation")
@Api(tags = "基本注解")
public class AnnotationController {
    @Autowired
    private CarVo carVo;

    /**
     * 	TypeError: Failed to execute 'fetch' on 'Window': Request with GET/HEAD method cannot have body.
     *
     * 	get 不能有body
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("getMapping1")
    public int getMapping1(){
        carVo.setName("yy");
        carVo.setAge(12);
        return 1;
    }

    @ResponseBody
    @GetMapping("getMapping2")
    public CarVo getMapping2(@RequestBody CarVo car){
        return carVo;
    }

    /**
     * 容器中的所有都是单例
     * 上面的controller设置值
     * 此controller返回值
     * @return
     */
    @ResponseBody
    @GetMapping("getMapping3")
    public CarVo getMapping3(){
        return carVo;
    }

    /**
     *  delete 可以是body
     * @param car
     * @return
     */
    @ResponseBody
    @DeleteMapping("deleteMapping1")
    public CarVo deleteMapping1(@RequestBody CarVo car){
        return car;
    }


    @ResponseBody
    @DeleteMapping("deleteMapping2")
    public int deleteMapping2(int a){

        return a;
    }

    /**
     * put 可以是body
     * @param a
     * @return
     */
    @ResponseBody
    @PutMapping("putMapping1")
    public int putMapping1(int a){
        return a;
    }

    @ResponseBody
    @PutMapping("putMapping2")
    public CarVo putMapping2(@RequestBody CarVo car){
        return car;
    }

    /**
     * post 可以是body
     * @param a
     * @return
     */
    @ResponseBody
    @PostMapping("postMapping1")
    public int postMapping1(int a){
        return a;
    }

    @ResponseBody
    @PostMapping("postMapping2")
    public CarVo postMapping2(@RequestBody CarVo car){
        return car;
    }


    /**
     * RequestParam 的常用属性
     * @param name
     * @param age
     * @return
     */
    @ApiOperation(value = "requestParamDetail", notes = "swagger requestParamDetail Desc")
    @ResponseBody
    @GetMapping("requestParamDetail")
    public String requestParamDetail(@RequestParam(name = "name",defaultValue = "lg" ,required = true) String name, String age){
        return name +" "+age;
    }


    /**
     * RequestParam
     * 如果不写，按实际名字匹配
     * @param id
     * @param name
     * @param age
     * @return
     */
    @ApiOperation(value = "试用swagger", notes = "swagger的小说明")
    @ResponseBody
    @GetMapping("indexFunction")
    public String indexFunction(String id, @RequestParam("nameaa")String name, String age){
        return id+" "+name +" "+age;
    }

    /**
     *  根据PathVariable 中的匹配
     * @param id
     * @param name
     * @return
     */
    @ResponseBody
    @GetMapping("indexFunc1/{id1}")
    public String indexFunc1(String id,@PathVariable("id1")String name){
        return id+" "+name;
    }

    /**
     * 依据PathVariable中写的
     * 和变量名没有关系
     * @param id
     * @param name
     * @return
     */
    @ResponseBody
    @GetMapping("indexFunc2/{id1}/{name}")
    public String indexFunc2(@PathVariable("name") String id, @PathVariable("id1")String name){
        return id+" "+name;
    }


    /**
     * http://localhost:8080/AnyTest/indexFunc3/1?name=2&orderType=3
     *
     * PathVariable 路径上的参数
     * RequestParam 默认为必填参数
     *
     * @param id
     * @param name
     * @param age
     * @return
     */
    @ResponseBody
    @GetMapping("indexFunc3/{id1}")
    public String indexFunc3(@PathVariable("id1") String id, @RequestParam("name")String name, @RequestParam("orderType")int age ){
        return id+" "+name+" "+age;
    }

    /**
     * 封装类 request response
     *
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/getHttpServletRequest")
    public String getHttpServletRequest( HttpServletRequest request){
        System.out.println("------------");
        String s = request.getRequestURL().toString();
        System.out.println(s);
        return "suss";
    }

    /**
     * catch到异常之后
     * 程序继续运行
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/getException")
    public String getException() throws Exception {
        log.info("进入getException");
        if(true)
            throw  new Exception();

        log.info("异常抛出");
        return "suss";
    }

    @ResponseBody
    @GetMapping("/getException1")
    public String getException1()  {

        try {
            throw  new Exception();
        } catch (Exception e) {
            log.info("进入getException");
            e.printStackTrace();
        }

        log.info("异常抛出");
        return "suss";
    }



}
