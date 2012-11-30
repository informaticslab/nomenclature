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
public class FacilityLabSetDTO extends BaseDTO {

	private static final long serialVersionUID = 7683170842542571371L;

	private ServiceFacilityDTO serviceFacility;
	private Set<SimpleLoincDTO> labOrders;
	
	public FacilityLabSetDTO() {
	}
	
	public FacilityLabSetDTO(Long id, ServiceFacilityDTO serviceFacility, Set<SimpleLoincDTO> labOrders) {
		super(id);
		this.serviceFacility = serviceFacility;
		this.labOrders = labOrders;
	}

	public ServiceFacilityDTO getServiceFacility() {
		return serviceFacility;
	}

	public void setServiceFacility(ServiceFacilityDTO serviceFacility) {
		this.serviceFacility = serviceFacility;
	}

	public Set<SimpleLoincDTO> getLabOrders() {
		return labOrders;
	}

	public void setLabOrders(Set<SimpleLoincDTO> labOrders) {
		this.labOrders = labOrders;
	}
	
}
