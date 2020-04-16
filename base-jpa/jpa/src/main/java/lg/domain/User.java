package lg.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


//@Data			//n对多的时候 不用lombok
@Entity
@Table(name="t_user")
public class User  {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	//数据库中的对应字段 user_id
	private Integer userId;

	private Integer age;
	private String name;

	@Column(name = "gridset")
	//数据库中的对应字段 gridset
	private String gridSet;

	//数据库中的对应字段 map_default
	private Integer mapDefault = 0;



	//@OneToMany(targetEntity = Car.class)		//targetEntity对方的实体类
	//@JoinColumn(name="carfid",referencedColumnName = "userId")		//name从表的外键名称  referencedColumnName主表的参照主键
	/**
	 * CascadeType.ALL			所有		推荐
	 * 			  meger			更新
	 * 			  persist		保存
	 * 			  remote		删除
	 */
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private Set<Car> car = new HashSet<>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGridSet() {
		return gridSet;
	}

	public void setGridSet(String gridSet) {
		this.gridSet = gridSet;
	}

	public Integer getMapDefault() {
		return mapDefault;
	}

	public void setMapDefault(Integer mapDefault) {
		this.mapDefault = mapDefault;
	}

	public Set<Car> getCar() {
		return car;
	}

	public void setCar(Set<Car> car) {
		this.car = car;
	}
}
