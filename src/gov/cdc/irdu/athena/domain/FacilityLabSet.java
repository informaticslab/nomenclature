package gov.cdc.irdu.athena.domain;

import gov.cdc.irdu.athena.dao.LoincDAO;
import gov.cdc.irdu.athena.shared.FacilityLabSetDTO;
import gov.cdc.irdu.athena.shared.ServiceFacilityDTO;
import gov.cdc.irdu.athena.shared.SimpleLoincDTO;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class FacilityLabSet extends DomainObject {

	private static final long serialVersionUID = -4434182057184233095L;
	
	@Autowired
	private transient LoincDAO loincDAO;

	@ManyToOne
    private ServiceFacility serviceFacility;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<Loinc> labOrders = new HashSet<Loinc>();
    
    public FacilityLabSet() {
    }
    
    public FacilityLabSet(FacilityLabSetDTO dto) {
    	setId(dto.getId());
    	this.serviceFacility = new ServiceFacility(dto.getServiceFacility());
    	this.labOrders = new HashSet<Loinc>();
    	for (SimpleLoincDTO simple: dto.getLabOrders()) {
    		Loinc loinc = loincDAO.findById(simple.getId());
    		this.labOrders.add(loinc);
    	}
    }

	public ServiceFacility getServiceFacility() {
        return this.serviceFacility;
    }

	public void setServiceFacility(ServiceFacility serviceFacility) {
        this.serviceFacility = serviceFacility;
    }

	public Set<Loinc> getLabOrders() {
        return this.labOrders;
    }

	public void setLabOrders(Set<Loinc> labOrders) {
        this.labOrders = labOrders;
    }
	
	public FacilityLabSetDTO toDTO() {
		Set<SimpleLoincDTO> labOrders = new HashSet<SimpleLoincDTO>();
		for (Loinc loinc: this.labOrders) {
			Loinc.ClassType type = Loinc.ClassType.get(loinc.getClassType());
			labOrders.add(new SimpleLoincDTO(loinc.getId(), type.name(), loinc.getClassification(),
					                         loinc.getCode(), loinc.getShortName(), loinc.getLongCommonName()));
		}
		ServiceFacilityDTO sfDTO = this.serviceFacility.toDTO();
		return new FacilityLabSetDTO(getId(), sfDTO, labOrders);
	}

}
