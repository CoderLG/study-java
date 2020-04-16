package lg.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "worldid",type = "World", shards = 1,replicas = 0, refreshInterval = "-1")
public class World {
    @Id
    public String gid;
    public String name;
    public String geom;
    public String geomStr;
//
//    public World(WordNewReq req) {
//        this.gid= String.valueOf(req.getGid());
//        this.name = req.getName();
//        this.geom = req.getGeom();
//        this.geomStr = req.getGeomStr();
//    }
//
//    public World() {
//    }
}
