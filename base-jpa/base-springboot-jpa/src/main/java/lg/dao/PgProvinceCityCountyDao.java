//package lg.dao;
//
//import lg.domain.PgProvinceCityCounty;
//import lg.domain.User;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface PgProvinceCityCountyDao extends JpaRepository<PgProvinceCityCounty, Long> {
//    List<PgProvinceCityCounty> findAllByCodeOrderByFullName(Integer code);
//    PgProvinceCityCounty findByName(String name);
//
//    @Query(value = "SELECT (max(code) + 1) new_code FROM \"pg_province_city_county\" WHERE parent= ?1" ,nativeQuery = true)
//    Integer maxCode(Integer parent);
//
//    @Query(value = "SELECT *  FROM \"pg_province_city_county\" WHERE code > ?1" ,nativeQuery = true)
//    List<PgProvinceCityCounty>  maxCodeEntity(Integer parent);
//}
