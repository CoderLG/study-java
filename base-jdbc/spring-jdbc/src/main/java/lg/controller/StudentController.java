package lg.controller;

import lg.dao.StudentDao;
import lg.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: LG
 * date: 2019-10-17 10:30
 * desc:
 */

@RestController
public class StudentController {

    @Autowired
    private StudentDao studentDao;



    @PostMapping("insertStu")
    public int insertStu(@RequestBody Student student){
        return studentDao.insert(student);
    }


    @PostMapping("batchInsertStu")
    public int[] batchInsertStu(@RequestBody List<Object[]> batchArgs){
        return studentDao.batchInsert(batchArgs);
    }

    @GetMapping("selectStu")
    public Student selectStu(int id){
        return studentDao.select(id);
    }

    @GetMapping("batchSelectStu")
    public List<Student> batchSelectStu(int id){
        return studentDao.batchSelect(id);
    }

    @PostMapping("updateStu")
    public int updateStu(@RequestBody  Student student){
        return studentDao.update(student);
    }
    @PostMapping("batchSelectStu")
    public int[] batchUpdateStu(@RequestBody  List<Object[]> batchArgs){
        return studentDao.batchUpdate(batchArgs);
    }


    @GetMapping("deleteStu")
    public int deleteStu(int id){
        return studentDao.delete(id);
    }


    @PostMapping("batchDeleteStu")
    public int[] batchDeleteStu(@RequestBody  List<Object[]> batchArgs){
        return studentDao.batchDelete(batchArgs);
    }

    @GetMapping("countStu")
    public int countStu(){
        return studentDao.count();
    }











}
