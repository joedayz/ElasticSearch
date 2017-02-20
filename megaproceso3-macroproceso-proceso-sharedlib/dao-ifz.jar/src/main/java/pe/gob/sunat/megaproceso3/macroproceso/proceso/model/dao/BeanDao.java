package pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SHPRBean;


import java.util.List;

/**
 * Created by josediaz on 30/11/2016.
 */
public interface BeanDao {

    SearchResponse searchBeanByQuery(QueryBuilder query);
    SearchResponse searchBeanByQuery(QueryBuilder query, List<String> lista);
    List<SHPRBean> searchWithoutDeselected(QueryBuilder query, List<String> lista);
    List<SHPRBean> searchScrollBByQuery(QueryBuilder query);
    List<SHPRBean> searchPaginationBByQuery(QueryBuilder query, Integer start,Integer finish);
    Long countQuery(QueryBuilder query);
    SearchResponse searchBeanByQuery(QueryBuilder query, List<String> lista, String filter);

}
