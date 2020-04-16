package lg.controller;

import io.swagger.annotations.Api;
import lg.dto.Student;
import lg.dto.Teacher;
import lg.utils.DozerUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * author: LG
 * date: 2019-10-28 20:17
 * desc:
 *
 * jackJson控制前端输入和实体之间的关系
 *
 * DozerBeanMapper 实体间相同属性的映射
 */

@Api(tags = "属性映射")
@RestController
public class JsonController {

    @Autowired
    DozerBeanMapper dozerBeanMapper;


    @PostMapping("testJackJson")
    public Student testJackJson(@RequestBody  Student student){
        return student;
    }

    /**
     * 单实体映射
     * @return
     */
    @PostMapping("testDozerBeanMapper")
    public Teacher testDozerBeanMapper(){
        Student student = new Student();
        student.setAge(11);
        student.setId(11);
        student.setStudentName("11");

        Teacher teacher = new Teacher();
        //dozerBeanMapper.map(student,teacher);
        Teacher map = dozerBeanMapper.map(student, Teacher.class);
        return map;
    }

    /**
     * 多实体映射
     * @return
     */
    @PostMapping("testDozerBeanMapperList")
    public  List<Teacher> testDozerBeanMapperList(){
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setAge(11);
        student.setId(11);
        student.setStudentName("11");

        Student student2 = new Student();
        student2.setAge(11);
        student2.setId(11);
        student2.setStudentName("11");
        students.add(student);
        students.add(student2);
        List<Teacher> teachers = DozerUtils.mapList(students, Teacher.class);
        return teachers;
    }




}
