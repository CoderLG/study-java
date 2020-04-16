package lg.feign;


import lg.dvo.CarVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value="${feign.gspId}",url="${feign.gspurl}")
public interface GeoserverFeign {

    @PostMapping("/rest/layergg/image1")
    public String publishLayer1();

    @PostMapping("/rest/layergg/image2/{workspacename}")
    public String publishLayer2(@PathVariable(value="workspacename") String workspacename,
                               @RequestHeader(name = "Authorization") String authorization);

    @PostMapping("/rest/layergg/image3/{workspacename}")
    public CarVo publishLayer3(@PathVariable(value="workspacename") String workspacename,@RequestBody CarVo car,
                               @RequestHeader(name = "Authorization") String authorization);


}
