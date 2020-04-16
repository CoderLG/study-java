package lg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lg.domain.Car;
import lg.domain.User;
import lg.dao.CarDao;
import lg.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * author: LG
 * date: 2019-09-03 10:05
 * desc:
 * 能不用外键 就不用外键
 * 用级联增加 删除
 * 用导航查询
 */

@RestController
@Api(tags = "一对多")
public class OneManyController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CarDao carDao;

    /**
     * oneToManySave 两个insert   一个update
     * 由于one的一方 配置了外键关系，所以它可以维护外键 所以多了一个update
     * 所以以上的方法是不建议使用的
     *
     *
     * 解决放弃 维护外键关系 声明以别人的为例子  @OneToMany(mappedBy = "user")
     * 配置级联操作，用级联操作做
     */
    @Transactional
    @GetMapping("oneToManySave")
    public void oneToManySave(){
        Car car = new Car();
        User user = new User();
        car.setName("凯斯拉客");

        user.setName("gg");
        user.getCar().add(car);

        userDao.save(user);
        carDao.save(car);
    }

    /**
     * 增加transactional 底部会自动优化
     * 必须有两个save
     *
     * manyToOneSave  两个insert
     */
    @Transactional
    @GetMapping("manyToOneSave")
    public void manyToOneSave(){
        Car car = new Car();
        User user = new User();

        car.setName("凯斯拉客");
        user.setName("gg");

        car.setUser(user);

        userDao.save(user);
        carDao.save(car);

    }
    //------------------------------------------- 级联操作 -------------------------------------------------
    @Transactional
    @GetMapping("cascadeSave")
    @ApiOperation(value = "级联保存")
    public void cascadeSave(){
        Car car = new Car();
        Car car1 = new Car();
        User user = new User();

        car.setName("凯斯拉客");
        car1.setName("马萨拉蒂");
        user.setName("gg");

        car.setUser(user);
        car1.setUser(user);
        user.getCar().add(car);
        user.getCar().add(car1);

        userDao.save(user);

    }

    @Transactional
    @GetMapping("cascadeDel")
    @ApiOperation(value = "级联删除")
    public void cascadeDel(Integer id){
        User one = userDao.getOne(id);
        userDao.delete(one);
    }


    @Transactional
    @GetMapping("cascadeQuery")
    @ApiOperation(value = "级联查询")
    public void cascadeQuery(Integer id){
        User one = userDao.getOne(id);
        Set<Car> cars = one.getCar();
        System.out.println("-----------------------------------");
        cars.forEach(car -> {
            System.out.println(car.getName());
        });
    }

}
