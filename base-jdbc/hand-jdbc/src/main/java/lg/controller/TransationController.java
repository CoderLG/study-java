package lg.controller;

import lg.service.TransationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: LG
 * date: 2019-09-04 22:18
 * desc:
 */
@RestController
public class TransationController {

    @Autowired
    private  TransationService dbHelper;

    /**
     * 通过EnableTransactionManagement 控制 datasource 实现事务
     * 以后也不会遇见
     * 遇见请看
     * https://www.bilibili.com/video/av44875350/?p=37
     *
     */
//    @Transactional
//    @GetMapping(value = "/transsationQuery")
//    public List<GFGX_Y_DMK_DMSJ> queryDLBM() {
//
//        System.out.println("transactional start");
//
//        String selectStr = "SELECT *,st_astext(\"WZ\") FROM	\"GFGX_Y_DMK_DMSJ\" WHERE \"DLBM\" =500000200000052";
//
//        List<GFGX_Y_DMK_DMSJ> dmsjs = dbHelper.pgQuery(selectStr, rs -> {
//
//            return getDMSJList(rs,true);
//
//        });
//        int inu = 1/0;
//        System.out.println("transactional end");
//
//        return dmsjs;
//    }

}
