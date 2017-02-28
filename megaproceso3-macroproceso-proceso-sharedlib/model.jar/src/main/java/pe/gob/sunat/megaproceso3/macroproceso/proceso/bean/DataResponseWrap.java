package pe.gob.sunat.megaproceso3.macroproceso.proceso.bean;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.SHPRBean;


@XmlRootElement
public class DataResponseWrap implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String sEcho;
	private long iTotalRecords = 0L;
	private long iTotalDisplayRecords = 0L;
	
	@XmlElement
	private List<? extends Object> aaData;
	
	@XmlElement(nillable = true)
	private String iColumns;
	@XmlElement(nillable = true)
	private String sColumns;
	@XmlElement(nillable = true)
	private String iDisplayStart;
	@XmlElement(nillable = true)
	private String iDisplayLength;
	


	
	public DataResponseWrap(HttpServletRequest request, List<? extends Object> lista, Long size) {
		this.sEcho = request.getParameter("sEcho");
		this.iTotalRecords = size;
		this.iTotalDisplayRecords = size;
		this.iDisplayLength = request.getParameter("iDisplayLength");
		this.aaData = lista;
	}
	
	public DataResponseWrap(HttpServletRequest request, List<? extends Object> list) {
		this(request, list.size(), list.size(), list);
	}

	public DataResponseWrap(HttpServletRequest request, long iTotalRecords, long iTotalDisplayRecords,
					List<? extends Object> aaData) {
		
		String sEcho = request.getParameter("sEcho");
		
		if (sEcho != null)
			this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.aaData = aaData;
		
		this.iColumns = request.getParameter("iColumns");
		this.sColumns = request.getParameter("sColumns");
		this.iDisplayStart = request.getParameter("iDisplayStart");
	}

	public String getsEcho() {
		return this.sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public long getiTotalRecords() {
		return this.iTotalRecords;
	}

	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public long getiTotalDisplayRecords() {
		return this.iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<? extends Object> getAaData() {
		return this.aaData;
	}

	public void setAaData(List<? extends Object> aaData) {
		this.aaData = aaData;
	}
	/**
	 * @return the iColumns
	 */
	public String getiColumns() {
		return iColumns;
	}
	/**
	 * @param iColumns the iColumns to set
	 */
	public void setiColumns(String iColumns) {
		this.iColumns = iColumns;
	}
	/**
	 * @return the sColumns
	 */
	public String getsColumns() {
		return sColumns;
	}
	/**
	 * @param sColumns the sColumns to set
	 */
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	/**
	 * @return the iDisplayStart
	 */
	public String getiDisplayStart() {
		return iDisplayStart;
	}
	/**
	 * @param iDisplayStart the iDisplayStart to set
	 */
	public void setiDisplayStart(String iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	/**
	 * @return the iDisplayLength
	 */
	public String getiDisplayLength() {
		return iDisplayLength;
	}
	/**
	 * @param iDisplayLength the iDisplayLength to set
	 */
	public void setiDisplayLength(String iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	

}
