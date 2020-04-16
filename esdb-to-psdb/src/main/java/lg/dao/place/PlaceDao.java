package lg.dao.place;

import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import lg.domain.ToponymData;
import lg.dvo.DMSJReq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-11 12:32
 * desc:
 */
public interface PlaceDao {
    @Select("select *,st_astext(\"WZ\") lon from \"GFGX_Y_DMK_DMSJ\" limit #{lim} offset #{off}")
    List<DMSJReq> findSome(@Param("lim") int lim, @Param("off") int off );


    @Select("select *,st_astext(\"WZ\") as DESC from \"GFGX_Y_DMK_DMSJ\" limit #{lim} offset #{off}")
    List<GFGX_Y_DMK_DMSJ_DAO> findArr(@Param("lim") int lim, @Param("off") int off );


    @Select("select \"DMBS\" as id,\"DMCJBM\" as toponymLayerCoding,\"DMMC\" as toponym,\"WZ\",\"MS\" as description,\"DLBM\" as geocoding,\"DMMCCD\" as toponymLength,\"DMJP\" as JianPin,\"DMQP\" as QuanPin,\"DMBM\" as toponymAlias,\"BDY\" as localName,\"YWMC\" as EnglishName,\"DMDZ\" as address,\"LV\" as level,st_astext(\"WZ\") as WZstr from \"GFGX_Y_DMK_DMSJ\" limit #{lim} offset #{off}")
    List<ToponymData> findToponymData(@Param("lim") int lim, @Param("off") int off );


}

