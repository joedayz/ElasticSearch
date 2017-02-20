package pe.gob.sunat.megaproceso3.macroproceso.proceso.model;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;






/**
 * Created by josediaz on 30/11/2016.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SHPRBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(nillable = true)
    private Integer line_id;
	@XmlElement(nillable = true)
    private String play_name;
	@XmlElement(nillable = true)
    private Integer speech_number;
	@XmlElement(nillable = true)
    private String line_number;
	@XmlElement(nillable = true)
    private String speaker;
	@XmlElement(nillable = true)
    private String text_entry;

    @XmlTransient	
    private boolean export;

    public Integer getLine_id() {
        return line_id;
    }

    public void setLine_id(Integer line_id) {
        this.line_id = line_id;
    }

    public String getPlay_name() {
        return play_name;
    }
    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }
    public Integer getSpeech_number() {
        return speech_number;
    }
    public void setSpeech_number(Integer speech_number) {
        this.speech_number = speech_number;
    }
    public String getLine_number() {
        return line_number;
    }
    public void setLine_number(String line_number) {
        this.line_number = line_number;
    }
    public String getSpeaker() {
        return speaker;
    }
    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
    public String getText_entry() {
        return text_entry;
    }
    public void setText_entry(String text_entry) {
        this.text_entry = text_entry;
    }
    public boolean isExport() {
        return export;
    }
    public void setExport(boolean export) {
        this.export = export;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((line_id == null) ? 0 : line_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SHPRBean other = (SHPRBean) obj;
		if (line_id == null) {
			if (other.line_id != null)
				return false;
		} else if (!line_id.equals(other.line_id))
			return false;
		return true;
	}
    
    

}
