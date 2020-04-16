package lg.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

/**
 * 重构数据库表
 * @author Morris
 * @date 15:43 2019/9/10
 */
@Document(indexName = "toponymid",type = "ToponymData", shards = 1,replicas = 0, refreshInterval = "-1")
public class ToponymData {
    @Id
    public String id;
    public Integer toponymLayerCoding;
    public String toponym;
    public String WZ;           //旧字段保留，“位置”
    @GeoPointField
    public GeoPoint positionGeoPoint;  //旧字段对应，
    public String description;
    public Long geocoding;
    public Integer toponymLength;
    public String JianPin;
    public String QuanPin;
    public String toponymAlias;
    public String localName;        //原名为BDY“本地语”？，什么鬼？推测为当地名称
    public String EnglishName;
    public String address;
    public Integer level;
    public String WZstr;
//    public Long DMGC;       //垃圾回收？？？数据库字段全为NULL，推测无用，

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getToponymLayerCoding() {
        return toponymLayerCoding;
    }

    public void setToponymLayerCoding(Integer toponymLayerCoding) {
        this.toponymLayerCoding = toponymLayerCoding;
    }

    public String getToponym() {
        return toponym;
    }

    public void setToponym(String toponym) {
        this.toponym = toponym;
    }

    public String getWZ() {
        return WZ;
    }

    public void setWZ(String WZ) {
        this.WZ = WZ;
    }

    public GeoPoint getPositionGeoPoint() {
        return positionGeoPoint;
    }

    public void setPositionGeoPoint(GeoPoint positionGeoPoint) {
        this.positionGeoPoint = positionGeoPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGeocoding() {
        return geocoding;
    }

    public void setGeocoding(Long geocoding) {
        this.geocoding = geocoding;
    }

    public Integer getToponymLength() {
        return toponymLength;
    }

    public void setToponymLength(Integer toponymLength) {
        this.toponymLength = toponymLength;
    }

    public String getJianPin() {
        return JianPin;
    }

    public void setJianPin(String jianPin) {
        JianPin = jianPin;
    }

    public String getQuanPin() {
        return QuanPin;
    }

    public void setQuanPin(String quanPin) {
        QuanPin = quanPin;
    }

    public String getToponymAlias() {
        return toponymAlias;
    }

    public void setToponymAlias(String toponymAlias) {
        this.toponymAlias = toponymAlias;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setWZstr(String WZstr) {
        this.WZstr = WZstr;
        String tmp = WZstr.substring(6,WZstr.length()-1);
        String[] split = tmp.split(" ");
        positionGeoPoint = new GeoPoint();
        positionGeoPoint.reset(Double.valueOf(split[1]),Double.valueOf(split[0]));
    }

    public String getWZstr() {
        return WZstr;
    }



//    public ToponymData() {
//    }
//
//    public ToponymData(DMSJReq req) {
//
//        this.id= StringUtils.isNotBlank(req.getDMBS())?req.getDMBS():null;
//        this.toponymLayerCoding = req.getDMCJBM();
//        this.toponym = StringUtils.isNotBlank(req.getDMMC())?req.getDMMC():null;
//        this.WZ = StringUtils.isNotBlank(req.getWZ())?req.getWZ():null;
//        this.description = StringUtils.isNotBlank(req.getMS())?req.getMS():null;
//        this.geocoding = req.getDLBM();
//        this.toponymLength = req.getDMMCCD();
//        this.JianPin  = StringUtils.isNotBlank(req.getDMJP())?req.getDMJP():null;
//        this.QuanPin = StringUtils.isNotBlank(req.getDMQP())?req.getDMQP():null;
//        this.toponymAlias = StringUtils.isNotBlank(req.getDMBM())?req.getDMBM():null;
//        this.localName = StringUtils.isNotBlank(req.getBDY())?req.getBDY():null;
//        this.EnglishName = StringUtils.isNotBlank(req.getYWMC())?req.getYWMC():null;
//        this.address = StringUtils.isNotBlank(req.getDMDZ())?req.getDMDZ():null;
////        this.DMGC = req.getDMGC();
//        this.level = req.getLV();
//
//        positionGeoPoint = new GeoPoint();
//        positionGeoPoint.reset(req.getLat(),req.getLon());
//    }
//
//
//    public ToponymData(DMSJNewReq dmsj) {
//        this.toponym = dmsj.getName();
//        this.level = dmsj.getLevel()!=null?dmsj.getLevel():7;       //原代码默认值
//        this.positionGeoPoint = new GeoPoint();
//        this.positionGeoPoint.reset(dmsj.getLat(),dmsj.getLon());
//        this.JianPin = dmsj.getJP();
//        this.QuanPin = dmsj.getQP();
//        this.toponymLayerCoding = 1721;         //原代码默认值
//        this.geocoding= LatLonLevel2Geocoding.geocoding(dmsj.getLat(),dmsj.getLon(),level);
//    }
}
