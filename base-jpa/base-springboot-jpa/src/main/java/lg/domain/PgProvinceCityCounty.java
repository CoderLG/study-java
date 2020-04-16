//package lg.domain;
//
//
//import com.vividsolutions.jts.geom.Geometry;
//import lombok.Data;
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//
///**
// * author: LG
// * date: 2019-10-12 13:48
// * desc:
// *
// * pg数据库
// * 使用自己建的seq
// */
//@Entity
//@Data
//public class PgProvinceCityCounty {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PgProvinceCityCounty_gene")
//    @SequenceGenerator(name = "PgProvinceCityCounty_seq_gene", sequenceName = "PgProvinceCityCounty_seq", allocationSize = 1)
//    Long id;
//    Integer code;
//    // 2:国家 3：省份 4：城市 5：县
//    Short level;
//    String name;
//    String fullName;
//    //父id
//    Integer parent;
//    //面积
//    Double shapeArea;
//    Double shapeLength;
//    @Type(type = "jts_geometry")
//    Geometry geom;
//}