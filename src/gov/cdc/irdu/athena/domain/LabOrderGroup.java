package gov.cdc.irdu.athena.domain;

import gov.cdc.irdu.athena.shared.LabOrderDTO;
import gov.cdc.irdu.athena.shared.LabOrderGroupDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class LabOrderGroup extends DomainObject {

	private static final long serialVersionUID = -7675709310235217758L;

	@Column(nullable=false)
	private String code;
	
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<LabOrder> labOrderSet;
	
	public LabOrderGroup() {
	}
	
	public void addLabOrder(LabOrder order) {
		if (null == labOrderSet)
			labOrderSet = new HashSet<LabOrder>();
		
		labOrderSet.add(order);
		order.setGroup(this);
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

	public Set<LabOrder> getLabOrderSet() {
		return labOrderSet;
	}

	public void setLabOrderSet(Set<LabOrder> labOrderSet) {
		for (LabOrder order: labOrderSet)
			order.setGroup(this);
		this.labOrderSet = labOrderSet;
	}

	public LabOrderGroupDTO toDTO() {
		LabOrderGroupDTO group = new LabOrderGroupDTO(getId(), code, description, null);
		
		Set<LabOrderDTO> labOrders = new TreeSet<LabOrderDTO>();
		for (LabOrder order: labOrderSet)
			labOrders.add(order.toDTO(group));
		
		group.setLabOrderSet(labOrders);
		
		return group;
	}

}
