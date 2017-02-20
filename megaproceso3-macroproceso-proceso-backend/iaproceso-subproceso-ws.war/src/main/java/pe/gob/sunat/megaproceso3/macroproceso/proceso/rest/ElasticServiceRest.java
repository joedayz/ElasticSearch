package pe.gob.sunat.megaproceso3.macroproceso.proceso.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.bean.DataResponseWrap;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SHPRBean;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.service.BeanService;

@Path("data")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ElasticServiceRest {

	@Inject
	private BeanService beanService;

	@GET
	@Path("speechBeanList")
	@Produces(MediaType.APPLICATION_JSON)
	public DataResponseWrap sivepBeanSearch(@Context HttpServletRequest request, @QueryParam("text") String text,
			@QueryParam("page") String numberPage, @QueryParam("start") Integer start,
			@QueryParam("finish") Integer finish) {

		try {
			List<SHPRBean> listaBean = beanService.searchBByQuery(text, start, finish);
			Long listaBeanCount = beanService.countQuery(text);

			DataResponseWrap dataResponseWrap = new DataResponseWrap(request, listaBean, listaBeanCount);

			return dataResponseWrap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/downloadCSV")
	@Produces("application/csv")
	public Response downloadCSFile(@QueryParam("text") String text) throws Exception {

		List<SHPRBean> listaBean = beanService.searchBByQuery(text);

		ResponseBuilder response = getCSVExport(listaBean);
		
		return response.build();

	}

	
	@GET
	@Path("/selectedToExportCSV")
	@Produces("application/csv")
	public Response selectedToExportCSV(@QueryParam("text") String text, @QueryParam("seleccionados") String selectedList) throws Exception {

		List<SHPRBean> listaBean = beanService.searchBByQuery(text, Arrays.asList(selectedList.split(",")));

		ResponseBuilder response = getCSVExport(listaBean);
		
		return response.build();

	}
	
	@GET
	@Path("/deselectedToExportCSV")
	@Produces("application/csv")
	public Response deselectedToExportCSV(@QueryParam("text") String text, @QueryParam("desmarcados") String deselectedList) throws Exception {

		List<SHPRBean> listaBean = beanService.searchWithoutDeselected(text, Arrays.asList(deselectedList.split(",")));

		ResponseBuilder response = getCSVExport(listaBean);
		
		return response.build();

	}
	
	
	
	
	
	private ResponseBuilder getCSVExport(List<SHPRBean> listaBean) throws FileNotFoundException,
			UnsupportedEncodingException, IOException, JsonGenerationException, JsonMappingException {
		// create mapper and schema
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(SHPRBean.class).withHeader();
		schema = schema.withColumnSeparator(',');

		// output writer
		ObjectWriter myObjectWriter = mapper.writer(schema);
		File tempFile = new File("fileSHPRBean.csv");

		FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
        myObjectWriter.writeValue(writerOutputStream, listaBean);
		
		ResponseBuilder response = Response.ok((Object) tempFile);
		response.header("Content-Disposition", "attachment; filename=fileSHPRBean.csv");
		return response;
	}

}
