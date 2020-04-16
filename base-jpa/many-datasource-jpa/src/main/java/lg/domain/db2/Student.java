package lg.domain.db2;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data            //n对多的时候 不用lombok
@Entity
@Table(name="t_student")
public class Student {

	@Id
	private Integer id;

	private String name;



}
