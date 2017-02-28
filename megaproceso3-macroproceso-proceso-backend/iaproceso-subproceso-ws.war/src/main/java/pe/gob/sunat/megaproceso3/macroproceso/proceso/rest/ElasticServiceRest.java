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
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SIVEPDatoBean;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.service.RegistroPaginadoService;

@Path("data")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ElasticServiceRest {

	@Inject
	private RegistroPaginadoService beanService;

	
	/**
	 * Metodo que hace la busqueda realizada por el usuario.
	 * @param text texto a buscar
	 * @param numberPage numero de pagina actual
	 * @param start Donde va a empezar la lista devuelta
	 * @param finish es el final de los registros de la lista devuelta.
	 * @return DataResponseWrap
	 */
	
	@GET
	@Path("speechBeanList")
	@Produces(MediaType.APPLICATION_JSON)
	public DataResponseWrap sivepBeanBusqueda(@Context HttpServletRequest request, @QueryParam("text") String text,
			@QueryParam("totalrecords") String totalRecord, @QueryParam("start") Integer start,
			@QueryParam("finish") Integer finish) {

		
		try {
			DataResponseWrap dataResponseWrap = beanService.busquedaBeanQueryPaginado(request, text, start, finish,
					totalRecord);

			return dataResponseWrap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * Metodo para exportar la busqueda que ha sido hecha por el usuario .
	 * @param text .- texto a buscar en el elasticsearch.
	 * @param response .- reponse para inserta el csv file
	 * @return return csv
	 * @throws Exception
	 */
	@GET
	@Path("/downloadCSV")
	@Produces("application/csv")
	public Response downloadCSFile(@QueryParam("text") String text) throws Exception {

		List<SIVEPDatoBean> listaBean = beanService.busquedaBeanQuery(text);

		ResponseBuilder response = getCSVExport(listaBean);

		return response.build();

	}

	
	/**
	 * Metodo que exporta los registros que han sido seleccionados por el usuario
	 * @param text texto a buscar
	 * @param response para inserta el archivo csv
	 * @param selectedList lista devuelta en la busqueda
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/selectedToExportCSV")
	@Produces("application/csv")
	public Response selectedToExportCSV(@QueryParam("text") String text,
			@QueryParam("seleccionados") String selectedList) throws Exception {

		
		
		List<SIVEPDatoBean> listaBean = beanService.busquedaBeanSeleccionados(text, Arrays.asList(selectedList.split(",")));

		ResponseBuilder response = getCSVExport(listaBean);

		return response.build();

	}

	/**
	 * Metodo que exporta los registros que han sido seleccionados por el usuario, excluyendo a los registros que han sido deseleccionados.
	 * @param text
	 * @param response
	 * @param deselectedList
	 * @return
	 * @throws Exception
	 */
	
	@GET
	@Path("/deselectedToExportCSV")
	@Produces("application/csv")
	public Response deselectedToExportCSV(@QueryParam("text") String text,
			@QueryParam("desmarcados") String deselectedList) throws Exception {

		List<SIVEPDatoBean> listaBean = beanService.busquedaBeanDeseleccionados(text, Arrays.asList(deselectedList.split(",")));
		
		ResponseBuilder response = getCSVExport(listaBean);

		return response.build();

	}
	/**
	 * Metodo comun para generar el parseo y conversion al csv.
	 * @param response
	 * @param listaBean
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	private ResponseBuilder getCSVExport(List<SIVEPDatoBean> listaBean) throws FileNotFoundException,
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
