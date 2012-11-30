/**
 * 
 */
package gov.cdc.irdu.athena.domain;

import gov.cdc.irdu.athena.shared.LabOrderDTO;
import gov.cdc.irdu.athena.shared.LabOrderGroupDTO;
import gov.cdc.irdu.athena.shared.ServiceFacilityDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Joel M. Rives
 * Mar 28, 2011
 */

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class LabOrder extends DomainObject {

	private static final long serialVersionUID = 8478051819038220294L;

	@Column(nullable=false)
	private String name;
	
	@ManyToOne
	private ServiceFacility serviceFacility;
	
	@ManyToOne
	private LabOrderGroup labOrderGroup;
	
	@Column(length = 4096)
	private String description;
	
	public LabOrder() {
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ServiceFacility getServiceFacility() {
		return serviceFacility;
	}


	public void setServiceFacility(ServiceFacility serviceFacility) {
		this.serviceFacility = serviceFacility;
	}


	public LabOrderGroup getGroup() {
		return labOrderGroup;
	}


	public void setGroup(LabOrderGroup group) {
		this.labOrderGroup = group;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public LabOrderDTO toDTO(LabOrderGroupDTO group) {
		ServiceFacilityDTO facility = null == serviceFacility ? null : serviceFacility.toDTO();
		return new LabOrderDTO(getId(), name, facility, group, description);
	}
}
