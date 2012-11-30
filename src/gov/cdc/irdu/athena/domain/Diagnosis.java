package gov.cdc.irdu.athena.domain;

import gov.cdc.irdu.athena.shared.DiagnosisDTO;
import gov.cdc.irdu.athena.shared.FacilityLabSetDTO;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Diagnosis extends DomainObject {

	private static final long serialVersionUID = -6883523246726467214L;

	@Column(nullable=false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FacilityLabSet> labSets = new HashSet<FacilityLabSet>();
    
    public Diagnosis() {
    }
    
    public Diagnosis(DiagnosisDTO dto) {
    	setId(dto.getId());
    	this.name = dto.getName();
    	this.labSets = new HashSet<FacilityLabSet>();
    	for (FacilityLabSetDTO flsDTO: dto.getLabSets()) {
    		labSets.add(new FacilityLabSet(flsDTO));
    	}
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Set<FacilityLabSet> getLabSets() {
        return this.labSets;
    }

	public void setLabSets(Set<FacilityLabSet> labSets) {
        this.labSets = labSets;
    }
	
	public DiagnosisDTO toDTO() {
		Set<FacilityLabSetDTO> labSets = new HashSet<FacilityLabSetDTO>();
		for (FacilityLabSet labSet: this.labSets) {
			labSets.add(labSet.toDTO());
		}
		return new DiagnosisDTO(getId(), this.name, labSets);
	}

}
