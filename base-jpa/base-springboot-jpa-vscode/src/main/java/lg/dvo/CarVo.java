package lg.dvo;

import org.springframework.stereotype.Component;

@Component
public class CarVo {

    private static final long serialVersionUID = -5809782578272943999L;
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
