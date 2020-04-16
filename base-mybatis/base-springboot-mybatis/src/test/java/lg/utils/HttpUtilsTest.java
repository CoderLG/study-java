package lg.utils;

import org.junit.Test;

import java.util.HashMap;

/**
 * author: LG
 * date: 2019-09-18 10:40
 * desc:
 */
public class HttpUtilsTest {

    @Test
    public void doGet() {
        String url2 ="http://localhost:1111/user/findById?id=22";
        String s = HttpClientUtils.doGet(url2);
        System.out.println(s);
    }

    @Test
    public void getAndRedirect() {
        String url = "http://localhost:1111/toBaiDu?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VG9rZW4iLCJpZCI6MjIsIm5hbWUiOiIzMyIsImlhdCI6MTU2ODc5OTU3MywiZXhwIjoxNTY4ODAzMTczfQ.L9EOf0Yeaw3txctMwae1Ay-AqpeR3A10IcvazDAqubI";
        String s = HttpClientUtils.doGet(url);
        System.out.println(s);
    }

    @Test
    public void getAndNoResult() {
        String url = "http://localhost:1111/httpclient/getAndNoResult?id=1";
        String url2 = "http://localhost:1111/httpclient/getAndNoResult";        //400
        String s = HttpClientUtils.doGet(url);
        System.out.println(s);
    }


    @Test
    public void saveHttpDvNoBody() {
        String url = "http://localhost:1111/httpclient/saveHttpDvNoBody";
        String url2 = "http://localhost:1111/httpclient/saveHttpDvo";     //400 不可以
        HashMap<String, Object> map = new HashMap<>();
        map.put("tId",111);
        String res = HttpClientUtils.doPost(url, null);   //可以是空 不支持 requestbody
        System.out.println(res);
        String res2 = HttpClientUtils.doPost(url, map);
        System.out.println(res2);
    }

    @Test
    public void postSaveString() {
        String url = "http://localhost:1111/httpclient/postSaveString";
        HashMap<String, Object> map = new HashMap<>();
        map.put("str",111);     //支持requestparam  所以必须有这个参数
        map.put("mm",111);      //参数可以多余
        String s = HttpClientUtils.doPost(url, map);
        System.out.println(s);
    }

    //--------------------------

    @Test
    public void postUrlUpdate() {       //不可以
        String url = "http://localhost:1111/admin/update?id=20&name=34";
        HashMap<String, Object> map = new HashMap<>();
        String s = HttpClientUtils.doPost(url, map);
        System.out.println(s);
    }

    @Test
    public void postBodyUpdate() {      //不可以
        String url = "http://localhost:1111/admin/update";
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",20);
        map.put("name",34);
        String s = HttpClientUtils.doPost(url, map);
        System.out.println(s);
    }

    @Test
    public void getUrlUpdate() {    //不可以
        String url = "http://localhost:1111/admin/update?id=20&name=34";
        String s = HttpClientUtils.doGet(url);
        System.out.println(s);
    }



    @Test
    public void postUrlDelete() {   //不可以
        String url = "http://localhost:1111/admin/delete?id=20";
        String s = HttpClientUtils.doPost(url, null);
        System.out.println(s);
    }

    @Test
    public void postBodyDelete() {  //不可以
        String url = "http://localhost:1111/admin/delete";
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",21);     //支持requestparam  所以必须有这个参数
        String s = HttpClientUtils.doPost(url, map);
        System.out.println(s);
    }

    @Test
    public void getUrlDelete() {    //不可以
        String url = "http://localhost:1111/admin/delete?id=21";
        String s = HttpClientUtils.doGet(url);
        System.out.println(s);
    }




}