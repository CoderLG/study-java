package geovis.services.domain;//package geovis.services.domain;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.cache.annotation.CacheConfig;

import javax.persistence.*;


@CacheConfig
public class GFGX_Y_DMK_DMSJ {



    public Long DMBS;
    public Integer DMRD;
    public String DMMC;


    public String WZ;
    public String MS;
    public Long DLBM;
    public Integer DMMCCD;
    public String DMJP;
    public String DMQP;
    public String  JC;

    public String DMDZ;
    public Integer LV;
}
