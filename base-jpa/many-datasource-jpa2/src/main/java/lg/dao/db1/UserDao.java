package lg.dao.db1;

import lg.domain.db1.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, Integer> {


}
