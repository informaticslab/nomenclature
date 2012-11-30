/**
 * 
 */
package gov.cdc.irdu.athena.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author Joel M. Rives
 * Mar 22, 2011
 */

@Entity
public class Classification extends DomainObject {

	private static final long serialVersionUID = 324743385791692820L;

	private Integer type;
	
	@Column(length = 32)
	private String abbreviation;
	
	private String clinicalTerm;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getClinicalTerm() {
		return clinicalTerm;
	}

	public void setClinicalTerm(String clinicalTerm) {
		this.clinicalTerm = clinicalTerm;
	}
	
}
