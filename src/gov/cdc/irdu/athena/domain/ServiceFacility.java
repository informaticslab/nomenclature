package gov.cdc.irdu.athena.domain;

import gov.cdc.irdu.athena.shared.ServiceFacilityDTO;

import javax.persistence.Entity;

@Entity
public class ServiceFacility extends DomainObject {

	private static final long serialVersionUID = -7675709310235217758L;

	private String name;
	
	public ServiceFacility() {
	}
	
	public ServiceFacility(ServiceFacilityDTO dto) {
		this.setId(dto.getId());
		this.name = dto.getName();
	}

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }
	
	public ServiceFacilityDTO toDTO() {
		return new ServiceFacilityDTO(getId(), getName());
	}

}
