package lg.dao;

import lg.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDao extends JpaRepository<Car, Integer> {



}
