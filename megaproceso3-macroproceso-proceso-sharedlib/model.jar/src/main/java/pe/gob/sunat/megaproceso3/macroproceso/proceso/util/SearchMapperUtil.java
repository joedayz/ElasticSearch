package pe.gob.sunat.megaproceso3.macroproceso.proceso.util;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

/**
 * Created by josediaz on 1/12/2016.
 */
public class SearchMapperUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> List<T> getObjects(SearchResponse response, Class<T> valueType) throws Exception {
        List<T> res = new ArrayList<T>();
        for (SearchHit hit : response.getHits()) {
            res.add(getObject(hit, valueType));
        }
        return res;
    }

    public static <T> T getObject(SearchResponse response, Class<T> valueType) throws Exception {
        for (SearchHit hit : response.getHits()) {
            return getObject(hit, valueType);
        }
        return null;
    }

    public static <T> T getObject(SearchHit hit, Class<T> valueType) throws Exception {
        T res = MAPPER.readValue(hit.getSourceAsString(), valueType);
        return res;
    }
}
