package lg.dvo;

import lombok.ToString;

/**
 * 从pg数据库导出到es，新增dmsj表条目
 * @author Morris
 * @date 10:32 2019/9/10
 */
@ToString
public class DMSJReq {
    public String DMBS;
    public Integer DMCJBM;
    public String DMMC;
    public String WZ;
    public String MS;
    public Long DLBM;
    public Integer DMMCCD;
    public String DMJP;
    public String DMQP;
    public String DMBM;
    public String BDY;
    public String YWMC;
    public String DMDZ;
    public Long DMGC;
    public Integer LV;

    public Double lon;
    public Double lat;

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

    public Double getLon() {
        return lon;
    }

    public void setLon(String point) {

        String tmp = point.substring(6,point.length()-1);
        String[] split = tmp.split(" ");
        this.lon = Double.valueOf(split[0]);
        this.lat = Double.valueOf(split[1]);

    }

    public Double getLat() {
        return lat;
    }

    public void setLat(String point) {
        String tmp = point.substring(6,point.length()-1);
        String[] split = tmp.split(" ");
        this.lat = Double.valueOf(split[1]);

    }
}
