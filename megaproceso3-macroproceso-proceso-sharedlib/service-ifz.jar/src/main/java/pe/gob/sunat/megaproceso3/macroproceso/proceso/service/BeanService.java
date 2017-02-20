package pe.gob.sunat.megaproceso3.macroproceso.proceso.service;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SHPRBean;

import java.util.List;

/**
 * Created by josediaz on 1/12/2016.
 */
public interface BeanService {

    public List<SHPRBean> searchBByQuery(String text) throws Exception;
    public List<SHPRBean> searchBByQuery(String text, List<String> selectedList) throws Exception;
    public List<SHPRBean> searchWithoutDeselected(String text, List<String> deselectedList) throws Exception;
    public Long countQuery(String text);
    public  List<SHPRBean> searchBByQuery(String text, Integer start,Integer finish) throws Exception ;

}
