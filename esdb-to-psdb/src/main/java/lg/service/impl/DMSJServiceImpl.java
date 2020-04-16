package lg.service.impl;

import lg.dao.es.DMSJDao;
import lg.dao.es.LGDMSJDao;
import lg.domain.GFGX_Y_DMK_DMSJ;
import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import lg.service.DMSJService;
import lg.dvo.DMSJReq;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * dmsj表的相关操作
 * @author Morris
 * @date 10:29 2019/9/10
 */
@Service
@Slf4j
public class DMSJServiceImpl implements DMSJService {

    @Autowired
    private DMSJDao dmsjDao;

    @Autowired
    private LGDMSJDao lgdmsjDao;

    private int saveListSize = 0;

    @Override
    public void save(DMSJReq req) {
        GFGX_Y_DMK_DMSJ dmsj = new GFGX_Y_DMK_DMSJ(req);
        dmsjDao.save(dmsj);
    }

    @Override
    @Async
    public void saveList(List<DMSJReq> req) {

        int me = ++saveListSize;
        log.info("第 " + me +"个saveList 被执行");
        long saveStart = System.currentTimeMillis();

        for (int i = 0; i < req.size(); i++) {
            log.info("第 " + me +"个saveList 正在保存: " +req.get(i).getDMBS());
            GFGX_Y_DMK_DMSJ dmsj = new GFGX_Y_DMK_DMSJ(req.get(i));
            dmsjDao.save(dmsj);
            ArrayList<GFGX_Y_DMK_DMSJ> gfgx_y_dmk_dmsjs = new ArrayList<>();
            gfgx_y_dmk_dmsjs.add(dmsj);
            dmsjDao.saveAll(gfgx_y_dmk_dmsjs);
        }
        long saveEnd = System.currentTimeMillis();
        log.info("第"+me+"个saveList 保存用时--> " + (saveEnd - saveStart));
        log.info("第 " + me +"个saveList 执行结束");
    }

    @Override
    @Async
    public void saveArr(List<GFGX_Y_DMK_DMSJ_DAO> arr) {
        long saveStart = System.currentTimeMillis();
        lgdmsjDao.saveAll(arr);
        long saveEnd = System.currentTimeMillis();
        log.info("保存用时--> " + (saveEnd - saveStart));

//        int me = ++saveListSize;
//        log.info("第 " + me +"个saveList 被执行");
//        long saveStart = System.currentTimeMillis();
//
//        for (int i = 0; i < req.size(); i++) {
//            log.info("第 " + me +"个saveList 正在保存: " +req.get(i).getDMBS());
//            GFGX_Y_DMK_DMSJ dmsj = new GFGX_Y_DMK_DMSJ(req.get(i));
//            dmsjDao.save(dmsj);
//            ArrayList<GFGX_Y_DMK_DMSJ> gfgx_y_dmk_dmsjs = new ArrayList<>();
//            gfgx_y_dmk_dmsjs.add(dmsj);
//            dmsjDao.saveAll(gfgx_y_dmk_dmsjs);
//        }
//        long saveEnd = System.currentTimeMillis();
//        log.info("第"+me+"个saveList 保存用时--> " + (saveEnd - saveStart));
//        log.info("第 " + me +"个saveList 执行结束");
    }


    @Override
    public GFGX_Y_DMK_DMSJ get(String id) throws Exception {
        Optional<GFGX_Y_DMK_DMSJ> result =  dmsjDao.findById(id);
        if(result.isPresent())
            return result.get();
        else
            throw new Exception("id:"+id+"不存在对应数据");
    }

    @Override
    public List<GFGX_Y_DMK_DMSJ> findAllByDLBM(String dlbm) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("DLBM",dlbm));
        FieldSortBuilder sort = SortBuilders.fieldSort("DLBM").order(SortOrder.DESC);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(queryBuilder);
        nativeSearchQueryBuilder.withSort(sort);
        Page<GFGX_Y_DMK_DMSJ> page = dmsjDao.search(nativeSearchQueryBuilder.build());
        return page.getContent();
    }

    /**
     * 根据名称模糊查询DMMC、DMJP、DMQP
     * @author Morris
     * @date 14:00 2019/9/10
     */
    @Override
    public List<GFGX_Y_DMK_DMSJ> findAllByFuzzyName(String placeName) {
        //逻辑：lv>=3&&dmmc~name || lv>=3&&dmjp~name || lv>=3&&dmqp~name
        //传说,不能使用 lv>=3 && (dmmc~name || dmjp~name || dmqp~name),会出错
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.rangeQuery("LV").gte("3"))
                        .filter(QueryBuilders.fuzzyQuery("DMMC",placeName)))
                .should(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.rangeQuery("LV").gte("3"))
                        .filter(QueryBuilders.fuzzyQuery("DMJP",placeName)))
                .should(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.rangeQuery("LV").gte("3"))
                        .filter(QueryBuilders.fuzzyQuery("DMQP",placeName)));

        FieldSortBuilder sort = SortBuilders.fieldSort("DLBM").order(SortOrder.DESC);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withPageable(PageRequest.of(0,50));
        nativeSearchQueryBuilder.withSort(sort);

        return null;


    }
}
