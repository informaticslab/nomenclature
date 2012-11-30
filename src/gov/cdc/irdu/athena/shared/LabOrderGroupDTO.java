/**
 * 
 */
package gov.cdc.irdu.athena.shared;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Joel M. Rives
 * Mar 23, 2011
 */
public class LabOrderGroupDTO extends BaseDTO {

	private static final long serialVersionUID = -6595398699162142401L;

	private String code;
	private String description;
	private Set<LabOrderDTO> labOrderSet = new TreeSet<LabOrderDTO>();
	
	public LabOrderGroupDTO() {
	}
	
	public LabOrderGroupDTO(Long id, String code, String description, Set<LabOrderDTO> labOrders) {
		super(id);
		this.code = code;
		this.description = description;
		this.labOrderSet = labOrders;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<LabOrderDTO> getLabOrderSet() {
		return labOrderSet;
	}

	public void setLabOrderSet(Set<LabOrderDTO> labOrderSet) {
		this.labOrderSet = labOrderSet;
	}
	
}
