package lg.controller;

import io.swagger.annotations.Api;
import lg.domain.User;
import lg.dao.UserDao;
import lg.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * author: LG
 * date: 2019-09-02 18:40
 * desc:
 *
 * 增    单条增加   多条增加   （自带）
 * 查    查一条     查所有     （自带）        段字段模糊查询_字段倒序_分页        （命名规则查询）
 * 改    改一条     改多条     （自带 先查在改）
 * 删    删一条     删一堆     （自带）        按某个名字模糊删除
 *
 *查询是没事务的
 */
@Api(tags = "自带方法")
@Controller
public class UserController {

    @Autowired
    private UserDao userDao;


    @Autowired
    private UserServiceImpl userService;



    //------------------调用service 测试事务----------start------------
    @GetMapping("noTransactionalUpdateSomeThing")
    @ResponseBody
    public void noTransactionalUpdateSomeThing(String name ,int userid){
        userService.noTransactionalUpdateSomeThing(name,userid);
    }

    @GetMapping("transactionalUpdateSomeThing")
    @ResponseBody
    public void transactionalUpdateSomeThing(String name ,int userid){
        userService.transactionalUpdateSomeThing(name,userid);
    }


    @GetMapping("transactionalUpdateSomeThing2")
    @ResponseBody
    public void transactionalUpdateSomeThing2(String name ,int userid){
        userService.transactionalUpdateSomeThing2(name,userid);
    }

    @GetMapping("deleteOne")
    @ResponseBody
    public void deleteOne(int userid){
        userService.deleteOne(userid);
    }

    @GetMapping("deleteOne2")
    @ResponseBody
    public void deleteOne2(int userid){
        userService.deleteOne2(userid);
    }

    @GetMapping("deleteOne3")
    @ResponseBody
    public void deleteOne3(int userid){
        userService.deleteOne3(userid);
    }

    //------------------调用service 测试事务----------end------------







    //-------------------JpaRepository 中自带的方法--------------start------------

    //单条增加
    @GetMapping("defAddUser")
    @ResponseBody
    public void defAddUser(){
        User user = new User();
        user.setAge(13);
        user.setGridSet("set");
        user.setName("nn");
        userDao.save(user);
    }

    //多条增加
    @GetMapping("defAddUsers")
    @ResponseBody
    public void defAddUsers(){
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setAge(11);
        user.setGridSet("set");
        user.setName("mm");

        User user2 = new User();
        user2.setAge(22);
        user2.setGridSet("set");
        user2.setName("yy");

        users.add(user);
        users.add(user2);

        userDao.saveAll(users);
    }

    //查询
    @GetMapping("defFindAll")
    @ResponseBody
    @Transactional
    @Cacheable(value = "userCache")
    public List<User> defFindAll(){
        List<User> all = userDao.findAll(new Sort(Sort.Direction.DESC,"age"));
        System.out.println("update end");
        //int i =1/0;
        return all;
    }

    //复杂查询
    @GetMapping("specifiFind")
    @ResponseBody
    public void specifiFind(){

        Specification<User> aa = new Specification<User>() {

            private static final long serialVersionUID = 136546983164948941L;

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Path<Object> age = root.get("age");
                Predicate equal = cb.equal(age, 12);
                return equal;
            }

        };
    }


    @GetMapping("autoSave")
    @ResponseBody
    @Transactional
    public List<User> autoSave(){
        List<User> all = userDao.findAll();
        for (User u:all){
            u.setAge(55);                   //会被自动修改
        }
        System.out.println("update end");
        return all;
    }

    @GetMapping("autoSave2")
    @ResponseBody
    @Transactional
    public User autoSave2(){
        Optional<User> byId = userDao.findById(3);       //debugger 中能看到
        User user = byId.get();
        user.setAge(555);                                //会 自动更新 没有事务不会更新，说明是事务引起的
        System.out.println("update end");
        return user;
    }


    //修改
    @GetMapping("defUpdate")
    @ResponseBody
    //@Transactional
    public Object defUpdate(){
        User one = userDao.getOne(2);
        one.setName("new name333");         //debugger 看不见one中内容
        System.out.println(one.getAge());     //能get到值


        //   one.setAge(55);                      //不会自动更新

        one.setName("new name644");       //手动开启事务
        User save = userDao.save(one);      //debugger 能看见one中内容


        System.out.println(save.getAge());
        System.out.println("update end");


        save.setAge(878);                    //会 自动更新 没有事务不会更新，说明是事务引起的


        // int i =1/0;
        return 11;
    }

    //删除
    @GetMapping("defDel")
    @ResponseBody
    public void defDel(){
        userDao.deleteById(1);
    }

    //删除全部
    @GetMapping("defDelAll")
    @ResponseBody
    public void defDelAll(){
        userDao.deleteAll();
    }
    //-------------------JpaRepository 中自带的方法--------------end------------



    //--------------------@query 方法------nativeQuery-----------start----------
    //查询
    @GetMapping("querySomeThing")
    @ResponseBody
    // @Transactional
    public List<Object[]> querySomeThing(){
        System.out.println("start");
        List<Object[]> nn = userDao.querySomeThing("n%");
        System.out.println("end");
        // int i = 1/0;
        return  nn;
    }

    //修改
    @GetMapping("updateSomeThing")
    @ResponseBody
    @Transactional
    public void updateSomeThing(){
        System.out.println("start");
        userDao.updateSomeThing("yyy",3);
        System.out.println("end");
        // int i = 1/0;
    }
    //--------------------@query 方法------nativeQuery-----------end----------

    //-------------------命名规则查询----------------------------start---------
    //查询
    @GetMapping("findByNameLikeOrderByIdDesc")
    @ResponseBody
    @Transactional
    @Cacheable(value = "cacheNameOrder", key = "'cache' + #name + #gridSet")
    public Page<User> findByNameLikeOrderByIdDesc(String name,String gridSet){

//        pageable 不只可以分页 还能放查询
//        Pageable pageable = PageRequest.of(currentPage - 1, pageSize,
//                new Sort((orderType != null && orderType.equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC,
//                        orderColumn));

        Pageable pageable = PageRequest.of(0, 2);

        Page<User> byNameLikeOrderByIdDesc = userDao.findByNameLikeAndGridSetLikeOrderByUserIdDesc(name+"%","%"+gridSet,pageable);

        //  System.out.println("end");
        // int i= 1/0;
        return  byNameLikeOrderByIdDesc;
    }
    //-------------------命名规则查询----------------------------end---------

    //-------------------调用私有方法----------------------------start---------
    /**
     * private中无法调用 beean
     *
     * 这是可以的，所以证明私有方法是可以的
     */
    @GetMapping("testPrivate")
    public void testPrivate(){
        List<User> users = testBean();
        System.out.println(users.size());
    }
    private  List<User> testBean(){
        List<User> all = userDao.findAll();
        return all;
    }
    //-------------------调用私有方法----------------------------end---------




}
