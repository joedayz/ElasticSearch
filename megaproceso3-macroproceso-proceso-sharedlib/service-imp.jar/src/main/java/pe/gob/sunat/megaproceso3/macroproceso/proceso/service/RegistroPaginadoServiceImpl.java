package pe.gob.sunat.megaproceso3.macroproceso.proceso.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.bean.DataResponseWrap;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SIVEPDatoBean;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.RegistroPaginadoDao;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.util.SearchMapperUtil;

/**
 * Created by josediaz on 1/12/2016.
 */
@Stateless
public class RegistroPaginadoServiceImpl implements RegistroPaginadoService {

    private static final Logger logger = Logger.getLogger(RegistroPaginadoServiceImpl.class.getName());

    @Inject
    private RegistroPaginadoDao beanDao;
	private static final String FILTER_NAME = "NUM_CORRIV";

	private QueryBuilder getQueryBuilder(String text) {
		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(text);
		return queryBuilder;
	}
	
	public List<SIVEPDatoBean> busquedaBeanQuery(String text) throws Exception {
		QueryBuilder queryBuilder = getQueryBuilder(text);
		List<SIVEPDatoBean> responseList = beanDao.busquedaScrollBeanByQuery(queryBuilder);
		
		logger.info("Method:searchBeanByQuery responseBean.size(): " + responseList.size());
		return responseList;
	}
	
	/**
	 * metodo servicio que devuelve la lista de resultado de la consulta hecha
	 * 
	 */
	public DataResponseWrap busquedaBeanQueryPaginado(HttpServletRequest request, String text, Integer start,Integer finish, String totalRecord) throws Exception {
		logger.info("Entrando al Method:searchBeanByQuery ");
		QueryBuilder queryBuilder = getQueryBuilder(text);
		Long counter = null; 
			if(StringUtils.isBlank(totalRecord) ||  totalRecord.equals("0")){
				counter = contadorQuery(text);
			}else{
				counter = new Long(totalRecord);
			}
		
		int limit = getLimit(text, start, finish, counter.longValue());
		
		List<SIVEPDatoBean> responseList = beanDao.busquedaBeanQueryPaginado(queryBuilder,start,limit);
		logger.info("Method:searchBeanByQuery responseBean.size(): " + responseList.size());
		DataResponseWrap dataResponseWrap = new DataResponseWrap(request, responseList,counter);
		
		return dataResponseWrap;
	}

	/**
	 * Metodo que limita la cantidad de registros que seran devueltos desde el ES
	 * @param text texto a buscar
	 * @param start comienzo de los registros devueltos
	 * @param finish final de los registros devueltos
	 * @return nro que indica el limite de la consulta
	 */
	private int getLimit(String text, Integer start, Integer finish,Long counter) {
		
		logger.info("Method:getLimit counter: " + counter);
		int endSize = finish < counter.intValue() ? finish : counter.intValue();
		int fparam = start + endSize;
		int limit = fparam < counter.intValue()? fparam:counter.intValue();
		return limit;
	}

	/**
	 * Obtiene el contador de la consulta
	 * return nro total de registros obtenidos de la consulta.
	 */
	public Long contadorQuery(String text) {
		
		return beanDao.contadorQuery(getQueryBuilder(text));
	}

	/**
	 * Metodo que llama al DAO para la ejecucion de la consulta, luego hace el parseo  a objetos de la lista obtenida desde el DAO.
	 */
	public List<SIVEPDatoBean> busquedaBeanSeleccionados(String text, List<String> selectedList) throws Exception {
		QueryBuilder queryBuilder = getQueryBuilder(text); 
		
		SearchResponse response = beanDao.busquedaBeanSeleccionados(queryBuilder,selectedList, FILTER_NAME);
		
		logger.info("Method:searchBByQuery para exportar al csv "+ response.getHits().totalHits());
		return SearchMapperUtil.getObjects(response, SIVEPDatoBean.class);
	}

	/**
	 * Metodo que realiza la consulta de la busqueda excluyendo los registros deseleccionados
	 */
	public List<SIVEPDatoBean> busquedaBeanDeseleccionados(String text, List<String> deselectedList) throws Exception {
		QueryBuilder queryBuilder = getQueryBuilder(text); 
		List<SIVEPDatoBean> responseList = beanDao.busquedaBeanDeseleccionados(queryBuilder,deselectedList, FILTER_NAME);
		
		logger.info("searchWithoutDeselected para exportar al csv "+ responseList.size());
		return responseList;
	}
	
}
