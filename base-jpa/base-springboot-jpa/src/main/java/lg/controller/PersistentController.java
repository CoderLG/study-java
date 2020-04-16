package lg.controller;

import io.swagger.annotations.Api;
import lg.dao.UserDao;
import lg.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * author: LG
 * date: 2019-09-25 16:08
 * desc:
 *
 * 临时态		    刚new的 delete
 * 持久化状态		给entityManager管理后的 saveOrUpdate select
 * 游离态			事务提交后的  close clean
 *
 *
 * 事务最后 会对持久化太的实体 提交
 *
 * 没有事务的时候 save会被立刻执行 flush也会立刻生效
 * 有事务的时候  中间的flush 不会起作用 最后的事务的flush 才会起作用
 *
 * userDao.getOne(id)
 *  返回一个error，操作它的时候 会抛出异常
 * userDao.findById(id)
 *  返回一个null，操作它的时候 会抛出异常
 *  动态代理和非动态代理，所做的修改 在有事务的前提下都会被修改
 *  动态代理对象可以被返回 在实体类上加@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
 *
 *
 *  事务默认是不开启的 手动在service层开启比较合适
 */
@RestController
@Api(tags = "持久化态",description = "SpringData 底层的hibernate的持久化状")
public class PersistentController {

    @Autowired
    private UserDao userDao;

    /**
     * 证明查询是否有事务
     * 通过Transactional的开启与关闭
     * 的到 select 和 save一样
     * 拥有事务
     *
     * @param id
     * @return
     */

    @GetMapping("studySelect")
    @Transactional
    public User studySelect(int id){
        User u = userDao.findById(id).get();
        userDao.flush();
        u.setName("studySelect11");
        //未开启事务的时候，flush瞬间起作用
        //开启事务之后，flush没有起作用
        userDao.flush();

        System.out.println(u.getName());
        u.setName("studySelect22");
        //未开启事务的时候，flush瞬间起作用
        //开启事务之后，flush没有起作用
        userDao.flush();
        return u;
    }

    @GetMapping("testAny")
    @Transactional
    public User testAny(int id){
        User u= userDao.getOne(id);             //返回动态代理对象， 当没有查到的时候 会返回一个异常 并不会抛出
        System.out.println(u.getName());

        User user = userDao.findById(id).get();  //返回动态代理对象，当没有查到的时候 直接抛出一个异常
        System.out.println(user.getName());
        return user;                            //动态代理对象可以被返回 在实体类上加@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
    }

    @GetMapping("testType")
   // @Transactional                                          //同比上个方法 getOne 生成动态代理对象 findById不是动态代理对象 因为findById在前面
    public User testType(int id,int age){
        User user = userDao.findById(id).get();
        user.setAge(age);
        user.setAge(99);                                         //和想象中一样会被提交

        User user1 = userDao.getOne(id);
        user1.setAge(age);
        user1.setAge(66);                                         //和想象中一样会被提交

        return user;                                               //动态代理和非动态代理，所做的修改 在有事务的前提下都会被修改
                                                                    //有此可见，事务默认是不开启的 在service层开启比较合适
    }

    @GetMapping("testFindByID1Type")
    @Transactional
    public User testFindByID1Type(int id){
        User one = null;
       try {
            one = userDao.getOne(id);                   //当查询id 不在的时候，会返回一个error给one 并且它也能返回，catch无法将他捕获。但操作它的时候，异常会抛出
       }catch (Exception e){
           System.out.println("error");
       }
                                                                //但当你给他 set值的时候 会抛出异常
       // one.setName("333h");                                 //动态代理会被修改
        return one;
    }

    @GetMapping("testFindByID2Type")
    @Transactional
    public  Optional<User> testFindByID2Type(int id){

        Optional<User> byId = userDao.findById(id);            //当查询id 不在的时候，会返回一个null给byId 并且 它(null)也能返回
        User user = byId.get();                               //但当操作它的时候 会抛出异常，
        //user.setName("333mm");                              //会被修改
        return byId;
    }


    @GetMapping("testSaveType")
  //  @Transactional                                              //去掉 试试看 深入理解
    public User testSaveType(){
        User user = new User();
        user.setAge(7777);
        user.setName("qq");
        User save = userDao.save(user);                           // 没有事务的时候 save会被立刻执行 flush也会立刻生效
        userDao.flush();                                          //有事务的时候 flush 之后 user也没有被保存到数据库中
        user.setAge(77);
        userDao.flush();
        save.setAge(7);                                           //有事务的时候 和想象中一样 会听最后一个  save和user 是指向的一个东西
        userDao.flush();
        return user;
    }

    @GetMapping("testErrorType")
    @Transactional(dontRollbackOn = Exception.class)
    public User testErrorType(){
        User user = new User();
        user.setAge(888);
        user.setName("qq");
        User save = userDao.save(user);
        save.setName("ming");
        userDao.flush();
        System.out.println("111");
        int i =1/0;                                                           //sql 语句打印了，但数据库并没有更新
        return user;
    }


    @GetMapping("testDeleteType")
   // @Transactional
    public User testDeleteType(){
        User user = new User();
        user.setUserId(1);
        userDao.delete(user);
        user.setAge(333);
        userDao.flush();        //并不会更新  临时态
        return user;
    }




}
