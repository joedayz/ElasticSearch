package pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.jpa;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.common.unit.TimeValue;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SHPRBean;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.BeanDao;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.util.ClientProvider;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.util.SearchMapperUtil;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by josediaz on 30/11/2016.
 */
@Stateless
public class BeanDaoImpl extends GenericDaoImpl implements BeanDao {


    private static final Logger logger = Logger.getLogger(BeanDaoImpl.class);
    private final String typeName = "line";

    public BeanDaoImpl() {
        elasticSearchClient = ClientProvider.instance().getClient();
    }
    @Override
    public SearchResponse searchBeanByQuery(QueryBuilder query) {
        return super.genericSearchByQuery(query);
    }

    @Override
    public SearchResponse searchBeanByQuery(QueryBuilder query, List<String> lista) {
        return super.genericSearchByQueryAndFilter(query,lista);
    }

    @Override
    public List<SHPRBean> searchWithoutDeselected(QueryBuilder query, List<String> deselectedList) {
        SearchResponse scrollResp = genericSearchByQueryAndNotFilter(query, deselectedList);
        long init =new Date().getTime();
        logger.info("DAO: searchWithoutDeselected Counter of the hits: " + scrollResp.getHits().getTotalHits());
        List<SHPRBean> listaBean = new ArrayList<SHPRBean>();
        try {
            while (true) {
                listaBean.addAll(SearchMapperUtil.getObjects(scrollResp, SHPRBean.class));

                logger.info("Nro de registro de la lista " + listaBean.size());
                scrollResp = elasticSearchClient
                        .prepareSearchScroll(scrollResp.getScrollId())
                        .setScroll(TimeValue.timeValueMinutes(1))
                        .execute().actionGet();

                logger.info("scrollResp.getHits().getHits().length " + scrollResp.getHits().getHits().length);
                if (scrollResp.getHits().getHits().length == 0) {
                    break;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long finish =new Date().getTime();
        logger.info("total of the delay is: " + (finish- init));
        return listaBean;

    }

    @Override
    public List<SHPRBean> searchScrollBByQuery(QueryBuilder query) {

        SearchResponse scrollResp = genericSearchScrollByQuery(query);
        long init =new Date().getTime();
        logger.info("DAO: searchScrollBeanByQuery Counter of the hits: " + scrollResp.getHits().getTotalHits());
        List<SHPRBean> listaBean = new ArrayList<SHPRBean>();
        try {
            while (true) {
                listaBean.addAll(SearchMapperUtil.getObjects(scrollResp, SHPRBean.class));

                logger.info("Nro de registro de la lista " + listaBean.size());
                scrollResp = elasticSearchClient
                        .prepareSearchScroll(scrollResp.getScrollId())
                        .setScroll(TimeValue.timeValueMinutes(1))
                        .execute().actionGet();

                logger.info("scrollResp.getHits().getHits().length " + scrollResp.getHits().getHits().length);
                if (scrollResp.getHits().getHits().length == 0) {
                    break;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long finish =new Date().getTime();
        logger.info("total of the delay is: " + (finish- init));
        return listaBean;
    }

    @Override
    public List<SHPRBean> searchPaginationBByQuery(QueryBuilder query, Integer start, Integer finish) {
        List<SHPRBean> listaBean = new ArrayList<SHPRBean>();

        try {
            SearchResponse response = elasticSearchClient.prepareSearch(getIndexName()).
                    setTypes(getTypeName())
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(query)
                    .setFrom(start)
                    .setSize(finish)
                    .setExplain(true)
                    .execute().actionGet();

            listaBean.addAll(SearchMapperUtil.getObjects(response, SHPRBean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaBean;
    }

    @Override
    public Long countQuery(QueryBuilder query) {
        return elasticSearchClient.prepareCount(getIndexName())
                .setTypes(typeName)
                .setQuery(query)
                .execute()
                .actionGet().getCount();
    }

    @Override
    public SearchResponse searchBeanByQuery(QueryBuilder query, List<String> lista, String filter) {
        return super.genericSearchByQueryAndFilter(query,lista, filter);
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public String getIndexName() {
        return ClientProvider.INDEX_NAME;
    }
}
