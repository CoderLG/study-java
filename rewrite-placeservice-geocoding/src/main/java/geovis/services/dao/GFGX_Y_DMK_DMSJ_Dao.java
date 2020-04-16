package geovis.services.dao;

import geovis.services.domain.GFGX_Y_DMK_DMSJ;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 *
 * @author Morris
 * @date 15:07 2019/10/9
 */
public interface GFGX_Y_DMK_DMSJ_Dao extends JpaRepository<GFGX_Y_DMK_DMSJ, Long>, JpaSpecificationExecutor<GFGX_Y_DMK_DMSJ> {
//    select "DMBS","DMRD","DMMC",st_astext("WZ") "WZ","MS","DLBM","DMMCCD","DMJP","DMQP","JC","DMDZ","LV"  from "GFGX_Y_DMK_DMSJ" where "DMBS"  >= 1 and "DMBS" <= 100

    @Query(value = "select \"DMBS\",\"DMRD\",\"DMMC\",st_astext(\"WZ\") \"WZ\",\"MS\",\"DLBM\",\"DMMCCD\",\"DMJP\",\"DMQP\",\"JC\",\"DMDZ\",\"LV\" from \"gfgx_y_dmk_dmsj\" where \"DMBS\"  >= ?1 and \"DMBS\" <= ?2", nativeQuery = true)
    List<GFGX_Y_DMK_DMSJ> rewriteDLBM(Integer strat, Integer end);

//    Page<GFGX_Y_DMK_DMSJ> findAllByGeocoding(Long geocoding, Pageable pageable);
//    Page<GFGX_Y_DMK_DMSJ> findAllByToponym(String name, Pageable pageable);
//
//    Page<GFGX_Y_DMK_DMSJ> findAllByToponymLikeOrJianPinLikeOrQuanPinLikeOrderByLevel(String toponym, String jianPin, String quanPin, Pageable pageable);
//
//    @Query(value = "select * from pg_toponym_data limit ?1 offset ?2", nativeQuery = true)
//    List<GFGX_Y_DMK_DMSJ> findLimitOffset(Integer limit, Integer offset);
//
//    @Query(value = "select *, st_distance(\"position_geo_point\",ST_GeomFromText(?1,4326)) as distance\n" +
//            "FROM \"pg_toponym_data\" where  ST_DWithin(\"position_geo_point\", ST_GeomFromText(?1,4326), 300000) \n" +
//            "AND \"geocoding\" < 900000000000000 AND \"geocoding\" > 400000000000000 ORDER BY distance  LIMIT 1", nativeQuery = true)
//    GFGX_Y_DMK_DMSJ findByLonLat(String pointStr);
//
//    List<GFGX_Y_DMK_DMSJ> findAllByToponymOrderByGeocoding(String name);
}
