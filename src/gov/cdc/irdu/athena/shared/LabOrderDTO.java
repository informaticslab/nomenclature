/**
 * 
 */
package gov.cdc.irdu.athena.shared;

/**
 *
 * @author Joel M. Rives
 * Mar 28, 2011
 */
public class LabOrderDTO extends BaseDTO implements Comparable<LabOrderDTO> {

	private static final long serialVersionUID = 7453122414130317219L;

	private String name;
	private ServiceFacilityDTO serviceFacility;
	private LabOrderGroupDTO group;
	private String description;
	
	public LabOrderDTO() {
	}
	
	public LabOrderDTO(Long id, String name, ServiceFacilityDTO facility, LabOrderGroupDTO group, String description) {
		super(id);
		this.name = name;
		this.serviceFacility = facility;
		this.group = group;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServiceFacilityDTO getServiceFacility() {
		return serviceFacility;
	}

	public void setServiceFacility(ServiceFacilityDTO serviceFacility) {
		this.serviceFacility = serviceFacility;
	}

	public LabOrderGroupDTO getGroup() {
		return group;
	}

	public void setGroup(LabOrderGroupDTO group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public int compareTo(LabOrderDTO other) {
		return this.name.compareTo(other.name);
	}
	
}
