package lg.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


//@Data			//n对多的时候 不用lombok
@Entity
@Table(name="t_role")
public class Role {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;

	private String name;

//	@ManyToMany(targetEntity = People.class)
//	@JoinTable(name = "peopleRole",		//中间表表的名称
//			joinColumns = {@JoinColumn(name = "fRoleId",referencedColumnName = "roleId")},					//当前对象在中间表中的外键
//			inverseJoinColumns = {@JoinColumn(name = "fPid",referencedColumnName = "pId")}		//对方对象在中间表中的外键
//
//	)

	@ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
	private Set<People> peoples = new HashSet<>();

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<People> getPeoples() {
		return peoples;
	}

	public void setPeoples(Set<People> peoples) {
		this.peoples = peoples;
	}

	public void setPeoples() {
	}
}
