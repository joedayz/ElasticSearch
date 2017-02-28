package pe.gob.sunat.megaproceso3.macroproceso.proceso.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.bean.DataResponseWrap;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SIVEPDatoBean;

/**
 * Created by josediaz on 1/12/2016.
 */
public interface RegistroPaginadoService {

	public List<SIVEPDatoBean> busquedaBeanQuery(String text) throws Exception;
	public  DataResponseWrap busquedaBeanQueryPaginado(HttpServletRequest request, String text, Integer start,Integer finish, String totalRecord) throws Exception ;
	public Long contadorQuery(String text);
	public List<SIVEPDatoBean> busquedaBeanSeleccionados(String text, List<String> selectedList) throws Exception;
	public List<SIVEPDatoBean> busquedaBeanDeseleccionados(String text, List<String> deselectedList) throws Exception;
	
}
