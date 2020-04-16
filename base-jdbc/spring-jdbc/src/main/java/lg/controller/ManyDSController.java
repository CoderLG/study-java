package lg.controller;

import lg.dao.StudentDao;
import lg.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * author: LG
 * date: 2019-10-29 09:56
 * desc:
 */
@RestController
public class ManyDSController {

    @Autowired
    private StudentDao studentDao;

    @Resource
    private JdbcTemplate primaryJdbcTemplate;

    @Resource
    JdbcTemplate secondaryJdbcTemplate;

    @PostMapping("manDSInsertStu")
    public void manDSInsertStu(){
        Student student = new Student();

        student.setId(22);
        student.setAge(222);
        student.setStudentName("222");
        studentDao.manyDSInsert(student,primaryJdbcTemplate);
        studentDao.manyDSInsert(student,secondaryJdbcTemplate);
    }
}
