package lg.domain;

import javax.persistence.*;

/**
 * author: LG
 * date: 2019-09-02 23:02
 * desc:
 */


//@Data
@Entity
@Table(name="t_car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //数据库中的对应字段 car_id
    private Integer car_Id;
    private String name;


   //jointable
    @ManyToOne(targetEntity = User.class)       //targetEntity对方的实体类
    @JoinColumn(name = "carfid",referencedColumnName = "userId")        //name从表的外键名称  referencedColumnName主表的参照主键
    private User user;

    public Integer getCar_Id() {
        return car_Id;
    }

    public void setCar_Id(Integer car_Id) {
        this.car_Id = car_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
