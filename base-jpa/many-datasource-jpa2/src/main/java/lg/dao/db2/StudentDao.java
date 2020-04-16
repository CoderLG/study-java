package lg.dao.db2;

import lg.domain.db2.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentDao extends JpaRepository<Student, Integer> {


}
