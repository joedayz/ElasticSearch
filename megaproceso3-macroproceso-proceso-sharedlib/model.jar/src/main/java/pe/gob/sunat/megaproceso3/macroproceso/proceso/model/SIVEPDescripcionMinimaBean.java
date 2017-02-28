package pe.gob.sunat.megaproceso3.macroproceso.proceso.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SIVEPDescripcionMinimaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlElement(nillable = true) private Long itemValDM;//": 1000001,
	@XmlElement(nillable = true) private Long numCorriv;//": 100,
	@XmlElement(nillable = true) private Long numSecdm;//": 1,
	@XmlElement(nillable = true) private String codTipdesc;//": "TE0200",
	@XmlElement(nillable = true) private String desDescripcion;//": "HILADO",
	@XmlElement(nillable = true) private String codTipvalor;//": "1",
	@XmlElement(nillable = true) private Long desDescripcion2;//": -1
	
	public Long getItemValDM() {
		return itemValDM;
	}
	public void setItemValDM(Long itemValDM) {
		this.itemValDM = itemValDM;
	}
	public Long getNumCorriv() {
		return numCorriv;
	}
	public void setNumCorriv(Long numCorriv) {
		this.numCorriv = numCorriv;
	}
	public Long getNumSecdm() {
		return numSecdm;
	}
	public void setNumSecdm(Long numSecdm) {
		this.numSecdm = numSecdm;
	}
	public String getCodTipdesc() {
		return codTipdesc;
	}
	public void setCodTipdesc(String codTipdesc) {
		this.codTipdesc = codTipdesc;
	}
	public String getDesDescripcion() {
		return desDescripcion;
	}
	public void setDesDescripcion(String desDescripcion) {
		this.desDescripcion = desDescripcion;
	}
	public String getCodTipvalor() {
		return codTipvalor;
	}
	public void setCodTipvalor(String codTipvalor) {
		this.codTipvalor = codTipvalor;
	}
	/**
	 * @return the desDescripcion2
	 */
	public Long getDesDescripcion2() {
		return desDescripcion2;
	}
	/**
	 * @param desDescripcion2 the desDescripcion2 to set
	 */
	public void setDesDescripcion2(Long desDescripcion2) {
		this.desDescripcion2 = desDescripcion2;
	}
     
}
