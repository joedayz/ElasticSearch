package pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SIVEPDatoBean;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.RegistroPaginadoDao;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.util.ClientProvider;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.util.SearchMapperUtil;

/**
 * Created by josediaz on 30/11/2016.
 */
@Stateless
public class RegistroPaginadoDaoImpl extends GenericDaoImpl implements RegistroPaginadoDao {

	private static final Logger logger = Logger.getLogger(RegistroPaginadoDaoImpl.class);
	private final String typeName = "sunattest4";

	public RegistroPaginadoDaoImpl() {
		elasticSearchClient = ClientProvider.instance().getClient();
	}

	@Override
	public SearchResponse busquedaBeanQuery(QueryBuilder query) {
		return super.genericSearchByQuery(query);
	}

	@Override
	public SearchResponse busquedaBeanQuery(QueryBuilder query, List<String> lista) {
		return super.genericSearchByQueryAndFilter(query, lista);
	}

	@Override
	public List<SIVEPDatoBean> busquedaScrollBeanByQuery(QueryBuilder query) {
		SearchResponse scrollResp = genericSearchScrollByQuery(query);
		long init = new Date().getTime();
		logger.info("DAO: searchScrollBeanByQuery Counter of the hits: " + scrollResp.getHits().getTotalHits());
		List<SIVEPDatoBean> listaBean = new ArrayList<SIVEPDatoBean>();
		try {
			while (true) {
				listaBean.addAll(SearchMapperUtil.getObjects(scrollResp, SIVEPDatoBean.class));

				logger.info("Nro de registro de la lista " + listaBean.size());
				scrollResp = elasticSearchClient.prepareSearchScroll(scrollResp.getScrollId())
						.setScroll(new TimeValue(3000)).execute().actionGet();

				logger.info("scrollResp.getHits().getHits().length " + scrollResp.getHits().getHits().length);
				if (scrollResp.getHits().getHits().length == 0
						|| listaBean.size() == ClientProvider.TOTAL_NUMBER_RECORD) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long finish = new Date().getTime();
		logger.info("total of the delay is: " + (finish - init));
		return listaBean;
	}

	@Override
	public List<SIVEPDatoBean> busquedaBeanQueryPaginado(QueryBuilder query, Integer start, Integer finish) {
		List<SIVEPDatoBean> listaBean = new ArrayList<SIVEPDatoBean>();

		try {
			SearchResponse response = elasticSearchClient.prepareSearch(getIndexName()).setTypes(getTypeName())
					.setQuery(query).setFrom(start).setSize(finish).execute().actionGet();

			listaBean.addAll(SearchMapperUtil.getObjects(response, SIVEPDatoBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaBean;
	}

	@Override
	public Long contadorQuery(QueryBuilder query) {
		return elasticSearchClient.prepareCount(getIndexName())
				.setTypes(typeName)
		        .setQuery(query)
		        .execute()
		        .actionGet().getCount();
	}

	@Override
	public SearchResponse busquedaBeanSeleccionados(QueryBuilder query, List<String> lista, String filter) {
		return super.genericSearchByQueryAndFilter(query,lista, filter);
	}

	@Override
	public List<SIVEPDatoBean> busquedaBeanDeseleccionados(QueryBuilder query, List<String> deselectedList, String filter) {
		SearchResponse scrollResp = genericSearchByQueryAndNotFilter(query, deselectedList, filter);
		long init =new Date().getTime();
		logger.info("DAO: searchWithoutDeselected Counter of the hits: " + scrollResp.getHits().getTotalHits());
		List<SIVEPDatoBean> listaBean = new ArrayList<SIVEPDatoBean>();
		try {
			while (true) {
				listaBean.addAll(SearchMapperUtil.getObjects(scrollResp, SIVEPDatoBean.class));
				
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
			e.printStackTrace();
		}
		long finish =new Date().getTime();
		logger.info("total of the delay is: " + (finish- init));
		return listaBean;
	}

	@Override
	protected String getTypeName() {
		return typeName;
	}

	@Override
	protected String getIndexName() {
		return ClientProvider.INDEX_NAME;
	}

}
