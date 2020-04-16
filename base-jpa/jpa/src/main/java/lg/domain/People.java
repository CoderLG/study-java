package lg.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


//@Data			//n对多的时候 不用lombok
@Entity
@Table(name="t_people")
public class People {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pId	;
	private String name;

	/**
	 * targetEntity 对方的实体类
	 */
	@ManyToMany(targetEntity = Role.class)			//这也支持级联增加 和 删除
	@JoinTable(name = "peopleRole",		//中间表表的名称
			joinColumns = {@JoinColumn(name = "fPid",referencedColumnName = "pId")},					//当前对象在中间表中的外键
			inverseJoinColumns = {@JoinColumn(name = "fRoleId",referencedColumnName = "roleId")}		//对方对象在中间表中的外键

	)
	private Set<Role> roles = new HashSet<>();

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
