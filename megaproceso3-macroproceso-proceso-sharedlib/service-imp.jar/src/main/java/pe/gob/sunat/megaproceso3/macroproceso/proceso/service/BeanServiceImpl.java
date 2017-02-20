package pe.gob.sunat.megaproceso3.macroproceso.proceso.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SHPRBean;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.BeanDao;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.service.BeanService;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.util.SearchMapperUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by josediaz on 1/12/2016.
 */
@Stateless
public class BeanServiceImpl implements BeanService {

    private static final Logger logger = Logger.getLogger(BeanServiceImpl.class.getName());

    @Inject
    private BeanDao beanDao;


    @Override
    public List<SHPRBean> searchBByQuery(String text) throws Exception {
        QueryBuilder queryBuilder = getQueryBuilder(text);
        List<SHPRBean> responseList = beanDao.searchScrollBByQuery(queryBuilder);

        logger.info("Method:searchBByQuery responseBean.size(): " + responseList.size());
        return responseList;
    }

    @Override
    public List<SHPRBean> searchBByQuery(String text, List<String> selectedList) throws Exception {
        QueryBuilder queryBuilder = getQueryBuilder(text);
        SearchResponse response = beanDao.searchBeanByQuery(queryBuilder,selectedList);
        //String filter = "play_name";
        //SearchResponse response = beanDao.searchBeanByQuery(queryBuilder,selectedList, filter);

        logger.info("Method:searchBByQuery para exportar al csv "+ response.getHits().totalHits());
        return SearchMapperUtil.getObjects(response, SHPRBean.class);
    }

    @Override
    public List<SHPRBean> searchWithoutDeselected(String text, List<String> deselectedList) throws Exception {
        QueryBuilder queryBuilder = getQueryBuilder(text);
        List<SHPRBean> responseList = beanDao.searchWithoutDeselected(queryBuilder,deselectedList);

        logger.info("searchWithoutDeselected para exportar al csv "+ responseList.size());
        return responseList;
    }

    @Override
    public List<SHPRBean> searchBByQuery(String text, Integer start, Integer finish) throws Exception {
        QueryBuilder queryBuilder = getQueryBuilder(text);
        //List<SHPRBean> responseList = beanDao.searchScrollBByQuery(queryBuilder);
        int limit = getLimit(text, start, finish);
        List<SHPRBean> responseList = beanDao.searchPaginationBByQuery(queryBuilder,start,limit);
        logger.info("Method:searchBeanByQuery responseList.size(): " + responseList.size());
        return responseList;
    }

    private QueryBuilder getQueryBuilder(String text) {
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("*" + text + "*");
        return queryBuilder;
    }

    private int getLimit(String text, Integer start, Integer finish) {
        Long counter = countQuery(text);
        logger.info("Method:getLimit counter: " + counter);
        int endSize = finish < counter.intValue() ? finish : counter.intValue();
        int fparam = start + endSize;
        int limit = fparam < counter.intValue()? fparam:counter.intValue();
        return limit;
    }

    public Long countQuery(String text) {
        return beanDao.countQuery(getQueryBuilder(text));
    }


}
