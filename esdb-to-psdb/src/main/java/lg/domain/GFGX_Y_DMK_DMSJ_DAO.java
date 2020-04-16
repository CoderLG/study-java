package lg.domain;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;


/**
 * dmsj表实体类
 * 相比pg数据库多了wzGeoPoint字段，以方便es查询
 * @author Morris
 * @date 10:30 2019/9/10
 */
@Document(indexName = "dmbs",type = "GFGX_Y_DMK_DMSJ", shards = 1,replicas = 0, refreshInterval = "-1")
public class GFGX_Y_DMK_DMSJ_DAO {

    /* id */
    @Id
    public String DMBS;
    /* 地名图层编码 */
    public Integer DMCJBM;
    /* 地名名称 */
    public String DMMC;
    /* 位置 */
//    @GeoPointField
    public String WZ;
    @GeoPointField
    public GeoPoint wzGeoPoint;
    /* 描述 */
    public String MS;
    /* 地理编码 */
    public Long DLBM;
    /* 地名名称长度 */
    public Integer DMMCCD;
    /* 地名简拼 */
    public String DMJP;
    /* 地名全拼 */
    public String DMQP;
    /* 地名别名 */
    public String DMBM;
    /* 本地语 */
    public String BDY;
    /* 英文名称 */
    public String YWMC;
    /* 地名地址 */
    public String DMDZ;
    /**/
    public Long DMGC;

    public Integer LV;

    public String DESC;

    public String getDMBS() {
        return DMBS;
    }

    public void setDMBS(String DMBS) {
        this.DMBS = DMBS;
    }

    public Integer getDMCJBM() {
        return DMCJBM;
    }

    public void setDMCJBM(Integer DMCJBM) {
        this.DMCJBM = DMCJBM;
    }

    public String getDMMC() {
        return DMMC;
    }

    public void setDMMC(String DMMC) {
        this.DMMC = DMMC;
    }

    public String getWZ() {
        return WZ;
    }

    public void setWZ(String WZ) {
        this.WZ = WZ;
    }

    public String getMS() {
        return MS;
    }

    public void setMS(String MS) {
        this.MS = MS;
    }

    public Long getDLBM() {
        return DLBM;
    }

    public void setDLBM(Long DLBM) {
        this.DLBM = DLBM;
    }

    public Integer getDMMCCD() {
        return DMMCCD;
    }

    public void setDMMCCD(Integer DMMCCD) {
        this.DMMCCD = DMMCCD;
    }

    public String getDMJP() {
        return DMJP;
    }

    public void setDMJP(String DMJP) {
        this.DMJP = DMJP;
    }

    public String getDMQP() {
        return DMQP;
    }

    public void setDMQP(String DMQP) {
        this.DMQP = DMQP;
    }

    public String getDMBM() {
        return DMBM;
    }

    public void setDMBM(String DMBM) {
        this.DMBM = DMBM;
    }

    public String getBDY() {
        return BDY;
    }

    public void setBDY(String BDY) {
        this.BDY = BDY;
    }

    public String getYWMC() {
        return YWMC;
    }

    public void setYWMC(String YWMC) {
        this.YWMC = YWMC;
    }

    public String getDMDZ() {
        return DMDZ;
    }

    public void setDMDZ(String DMDZ) {
        this.DMDZ = DMDZ;
    }

    public Long getDMGC() {
        return DMGC;
    }

    public void setDMGC(Long DMGC) {
        this.DMGC = DMGC;
    }

    public Integer getLV() {
        return LV;
    }

    public void setLV(Integer LV) {
        this.LV = LV;
    }


    public GeoPoint getWzGeoPoint() {
        return wzGeoPoint;
    }

    public void setWzGeoPoint(GeoPoint wzGeoPoint) {
        this.wzGeoPoint = wzGeoPoint;
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        String tmp = DESC.substring(6,DESC.length()-1);
        String[] split = tmp.split(" ");
        wzGeoPoint = new GeoPoint();
        wzGeoPoint.reset(Double.valueOf(split[1]),Double.valueOf(split[0]));
    }

}

