package lg.dao;

import lg.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleDao extends JpaRepository<People, Integer> {


}
