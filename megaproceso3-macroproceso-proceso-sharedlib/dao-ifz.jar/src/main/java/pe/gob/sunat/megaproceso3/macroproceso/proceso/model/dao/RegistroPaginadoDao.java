package pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SIVEPDatoBean;

/**
 * Created by josediaz on 30/11/2016.
 */
public interface RegistroPaginadoDao {

	public SearchResponse busquedaBeanQuery(QueryBuilder query);
	public SearchResponse busquedaBeanQuery(QueryBuilder query, List<String> lista);
	public List<SIVEPDatoBean> busquedaScrollBeanByQuery(QueryBuilder query);
	public List<SIVEPDatoBean> busquedaBeanQueryPaginado(QueryBuilder query, Integer start,Integer finish);
	public Long contadorQuery (QueryBuilder query);
	public SearchResponse busquedaBeanSeleccionados(QueryBuilder query, List<String> lista,String filter);
	public List<SIVEPDatoBean> busquedaBeanDeseleccionados(QueryBuilder query, List<String> lista, String filter);
}
