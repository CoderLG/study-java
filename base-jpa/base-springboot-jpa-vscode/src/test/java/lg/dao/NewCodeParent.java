//package lg.dao;
//
//import lg.domain.PgProvinceCityCounty;
//import lg.domain.User;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.apache.bcel.classfile.Module;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * author: LG
// * date: 2019-10-08 18:42
// * desc:
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class NewCodeParent {
//
//    @Autowired
//    private PgProvinceCityCountyDao pgProvinceCityCountyDao;
//
//    /**
//     * SELECT * FROM "pg_province_city_county" WHERE code=-1 ORDER BY full_name;
//     *
//     * SELECT code FROM "pg_province_city_county" WHERE "name"='西藏自治区' ;
//     *
//     * SELECT  (max(code) + 1) new_code FROM "pg_province_city_county" WHERE parent= 540000
//     *
//     * update  "pg_province_city_county" set parent = 540000 where id = 16531;
//     *
//     * update  "pg_province_city_county"  set code = 99542506  where id = 16531;
//
//     * SELECT * FROM "pg_province_city_county" WHERE code >90000000  ORDER BY code
//     *
//     * select count(1) from "pg_province_city_county"
//     */
//    @Test
//    public void modifyCity(){
//        List<PgProvinceCityCounty> all = pgProvinceCityCountyDao.findAllByCodeOrderByFullName(-1);
//        for (PgProvinceCityCounty p : all){
//
//            String fullName = p.getFullName();
//            log.warn("fullName -----> " +fullName);
//            if(fullName.indexOf("市") > 0 && fullName.indexOf("市") < (fullName.length()-1 )){
//                log.warn("---------开始----------");
//                String city = fullName.substring(fullName.indexOf("省")+1,fullName.indexOf("市")+1);
//                PgProvinceCityCounty byName = pgProvinceCityCountyDao.findByName(city);
//                if(byName == null)
//                    continue;
//                Integer parent = byName.getCode();
//                Integer maxCode = pgProvinceCityCountyDao.maxCode(parent);
//
//                if(maxCode == null)
//                    continue;
//
//                if(maxCode < 99000000)
//                    maxCode = maxCode + 99000000;
//
//                p.setParent(parent);
//                p.setCode(maxCode);
//                pgProvinceCityCountyDao.save(p);
//                log.warn("---------结束----------");
//            }
//        }
//
//    }
//
//
//    @Test
//    public void modifyProv(){
//        List<PgProvinceCityCounty> all = pgProvinceCityCountyDao.findAllByCodeOrderByFullName(-1);
//        for (PgProvinceCityCounty p : all){
//            String fullName = p.getFullName();
//            log.warn("fullName -----> " +fullName);
//            if(fullName.indexOf("省") > 0 && fullName.indexOf("省") < (fullName.length()-1 )){
//                log.warn("---------开始----------");
//                String prov = fullName.substring(0,fullName.indexOf("省")+1);
//                PgProvinceCityCounty byName = pgProvinceCityCountyDao.findByName(prov);
//                if(byName == null)
//                    continue;
//                Integer parent = byName.getCode();
//                Integer maxCode = pgProvinceCityCountyDao.maxCode(parent);
//
//                if(maxCode == null)
//                    continue;
//
//                if(maxCode < 99000000)
//                    maxCode = maxCode + 99000000;
//
//                p.setParent(parent);
//                p.setCode(maxCode);
//                pgProvinceCityCountyDao.save(p);
//                log.warn("---------结束----------");
//            }
//        }
//
//    }
//
//    @Test
//    public void modifyTaiWan(){
//        List<PgProvinceCityCounty> all = pgProvinceCityCountyDao.maxCodeEntity(99000000);
//        for (PgProvinceCityCounty p : all){
//
//            String fullName = p.getFullName();
//            log.warn("fullName -----> " +fullName);
//            if(fullName.indexOf("市") > 0 && fullName.indexOf("市") < (fullName.length()-1 )){
//                log.warn("---------开始----------");
//                String city = fullName.substring(fullName.indexOf("省")+1,fullName.indexOf("市")+1);
//                PgProvinceCityCounty byName = pgProvinceCityCountyDao.findByName(city);
//                if(byName == null)
//                    continue;
//                Integer parent = byName.getCode();
//                Integer maxCode = pgProvinceCityCountyDao.maxCode(parent);
//
//                if(maxCode == null)
//                    continue;
//
//                if(maxCode < 99000000)
//                    maxCode = maxCode + 99000000;
//
//                p.setParent(parent);
//                p.setCode(maxCode);
//                pgProvinceCityCountyDao.save(p);
//                log.warn("---------结束----------");
//            }
//        }
//
//    }
//
//
//
//    @Test
//    public void modifyTaiWana(){
//        List<PgProvinceCityCounty> all = pgProvinceCityCountyDao.maxCodeEntity(99000000);
//        System.out.println(all.size());
//    }
//
//
//
//}