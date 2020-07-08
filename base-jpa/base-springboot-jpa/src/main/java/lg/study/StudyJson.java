package lg.study;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * author: LG
 * date: 2020-04-26 15:21
 * desc:
 */
public class StudyJson {
    public static void main(String[] args) {

        /**
         * string 转 jsonObj
         */
        String ext = "{\"createTime\":1587885091456,\"dataPath\":\"e:/task\",\"dataType\":\"osgb_3dtiles\",\"error\":\"not any thing\",\"ext\":\"{name:\\\"xiaochao\\\"}\",\"id\":1,\"lastTime\":1587885091469,\"progress\":1.0,\"savePath\":\"d:/savepath\",\"status\":\"CREATED\",\"taskName\":\"taskName\",\"userId\":\"0\"}\n";
        JSONObject jsonObject = JSON.parseObject(ext);
        System.out.println( jsonObject.get("name"));

        /**
         * string 转 jsonArr
         */
        JSONArray jsonArray = JSON.parseArray ("[{name:'xxx',age:'18'},{name:'iii',age:'19'}]");
        System.out.println(jsonArray.getJSONObject(1).getString("age"));

        /**
         * jsonArr 转 string
         */
        String s = JSON.toJSONString(jsonArray);
        System.out.println(s);

        /**
         * entity 转 string
         */
//        TaskEntity allById = taskEntityRepository.findAllById(id);
//        String s = JSON.toJSONString(allById);
//        System.out.println(s);

        /**
         * string 转 entity
         */
//        TaskEntity taskEntity = JSON.parseObject(s, TaskEntity.class);
//        System.out.println(taskEntity.getCreateTime());


    }
}
