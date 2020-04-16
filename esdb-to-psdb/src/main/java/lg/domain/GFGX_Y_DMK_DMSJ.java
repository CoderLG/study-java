package lg.domain;

import lg.dvo.DMSJReq;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
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
@Data
@Document(indexName = "dmbs",type = "GFGX_Y_DMK_DMSJ", shards = 1,replicas = 0, refreshInterval = "-1")
public class GFGX_Y_DMK_DMSJ {

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










    public GFGX_Y_DMK_DMSJ() {
    }

    public GFGX_Y_DMK_DMSJ(DMSJReq req){
        this.DMBS= StringUtils.isNotBlank(req.getDMBS())?req.getDMBS():null;
        this.DMCJBM = req.getDMCJBM();
        this.DMMC = StringUtils.isNotBlank(req.getDMMC())?req.getDMMC():null;
        this.WZ = StringUtils.isNotBlank(req.getWZ())?req.getWZ():null;
        this.MS = StringUtils.isNotBlank(req.getMS())?req.getMS():null;
        this.DLBM = req.getDLBM();
        this.DMMCCD = req.getDMMCCD();
        this.DMJP  = StringUtils.isNotBlank(req.getDMJP())?req.getDMJP():null;
        this.DMQP = StringUtils.isNotBlank(req.getDMQP())?req.getDMQP():null;
        this.DMBM = StringUtils.isNotBlank(req.getDMBM())?req.getDMBM():null;
        this.BDY = StringUtils.isNotBlank(req.getBDY())?req.getBDY():null;
        this.YWMC = StringUtils.isNotBlank(req.getYWMC())?req.getYWMC():null;
        this.DMDZ = StringUtils.isNotBlank(req.getDMDZ())?req.getDMDZ():null;
        this.DMGC = req.getDMGC();
        this.LV = req.getLV();

        wzGeoPoint = new GeoPoint();
        wzGeoPoint.reset(req.getLat(),req.getLon());
    }
}

