package lg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lg.domain.People;
import lg.domain.Role;
import lg.dao.PeopleDao;
import lg.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * author: LG
 * date: 2019-09-03 13:44
 * desc:
 * 能不用外键 就不用外键
 * 用级联增加 删除
 * 用导航查询
 */
@Api(tags = "多对多")
@RestController
public class ManyToManyController {

    @Autowired
    private PeopleDao peopleDao;

    @Autowired
    private RoleDao roleDao;

    @Transactional
    @GetMapping("manyToManySave")
    public void manyToManySave(){
        People people = new People();
        Role role = new Role();

        people.setName("people");
        role.setName("role");

        people.getRoles().add(role);
        //role.getPeoples().add(people);  //双方都配置了维护中间表的权限，这样提交会冲突

        peopleDao.save(people);
        roleDao.save(role);


    }

    @Transactional
    @GetMapping("manyToManySave1")
    public void manyToManySave1(){
        People people = new People();
        Role role = new Role();

        people.setName("people");
        role.setName("role");

        people.getRoles().add(role);
        role.getPeoples().add(people);  //被动的一方 放弃维护权限

        peopleDao.save(people);
        roleDao.save(role);

    }
    //-----------------------------级联----------------------------------
    @Transactional
    @GetMapping("manyCascadeSave")
    @ApiOperation(value = "级联保存")
    public void manyCascadeSave(){

        Role role = new Role();
        People people = new People();
        People people1 = new People();

        role.setName("role1");
        people.setName("people1");
        people1.setName("peole2");


//        role.setPeoples();
        people.getRoles().add(role);
        people1.getRoles().add(role);
        role.getPeoples().add(people);
        role.getPeoples().add(people1);

        roleDao.save(role);

    }

    @Transactional
    @GetMapping("manyCascadeDel")
    @ApiOperation(value = "级联删除")
    public void manyCascadeDel(Integer id){

        Role one = roleDao.getOne(id);
        roleDao.delete(one);
    }


    @Transactional
    @GetMapping("manyCascadeQuery")
    @ApiOperation(value = "级联查询")
    public  void manyCascadeQuery(Integer id){

        Role one = roleDao.getOne(id);
        Set<People> peoples = one.getPeoples();
        System.out.println( peoples.size());
    }

}
