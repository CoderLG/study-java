package lg.dvo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CarVo implements Serializable {


    private static final long serialVersionUID = 6381260043827097520L;
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
