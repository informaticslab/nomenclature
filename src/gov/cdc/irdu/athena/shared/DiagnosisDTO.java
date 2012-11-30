/**
 * 
 */
package gov.cdc.irdu.athena.shared;

import java.util.Set;

/**
 *
 * @author Joel M. Rives
 * Mar 23, 2011
 */
public class DiagnosisDTO extends BaseDTO {
	
	private static final long serialVersionUID = 2841971731460597931L;

	private String name;
	private Set<FacilityLabSetDTO> labSets;

	public DiagnosisDTO() {
	}
	
	public DiagnosisDTO(Long id, String name, Set<FacilityLabSetDTO> labSets) {
		this.name = name;
		this.labSets = labSets;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FacilityLabSetDTO> getLabSets() {
		return labSets;
	}

	public void setLabSets(Set<FacilityLabSetDTO> labSets) {
		this.labSets = labSets;
	}
	
}
