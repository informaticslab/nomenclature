/**
 * 
 */
package gov.cdc.irdu.athena.server;

import gov.cdc.irdu.athena.dao.LabOrderDAO;
import gov.cdc.irdu.athena.dao.LabOrderGroupDAO;
import gov.cdc.irdu.athena.dao.ServiceFacilityDAO;
import gov.cdc.irdu.athena.domain.LabOrder;
import gov.cdc.irdu.athena.domain.LabOrderGroup;
import gov.cdc.irdu.athena.domain.ServiceFacility;
import gov.cdc.irdu.athena.shared.LabOrderDTO;
import gov.cdc.irdu.athena.shared.LabOrderGroupDTO;
import gov.cdc.irdu.athena.shared.LabService;
import gov.cdc.irdu.athena.shared.NotUniqueException;
import gov.cdc.irdu.athena.shared.ServiceFacilityDTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Joel M. Rives
 * Mar 22, 2011
 */

@Service("labService")
public class LabServiceImpl implements LabService {

	@Autowired private ServiceFacilityDAO facilityDAO;
	@Autowired private LabOrderDAO labOrderDAO;
	@Autowired private LabOrderGroupDAO groupDAO;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LabOrderGroupDTO createLabOrderGroup(LabOrderGroupDTO dto) {
		LabOrderGroup group = groupDAO.findByCode(dto.getCode());
		
		if (null != group)
			throw new NotUniqueException("code");
		
		group = toLabOrderGroup(dto);
		groupDAO.persist(group);

		if (null != group.getLabOrderSet()) {
			for (LabOrder order: group.getLabOrderSet()) {
				order.setGroup(group);
				labOrderDAO.merge(order);
			}
		}
		
		return group.toDTO();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ServiceFacilityDTO createServiceFacility(ServiceFacilityDTO dto) {
		ServiceFacility facility = facilityDAO.findByName(dto.getName());
		
		if (null != facility)
			throw new NotUniqueException("name");
		
		facility = toServiceFacility(dto);
		facilityDAO.persist(facility);
		return facility.toDTO();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteLabOrderGroup(LabOrderGroupDTO labOrderGroup) {
		if (null == labOrderGroup.getId())
			return;
		
		LabOrderGroup group = toLabOrderGroup(labOrderGroup);
		group = groupDAO.findById(group.getId());
		groupDAO.remove(group);
	}
	
	public List<String> getAllLabOrderGroupCodes() {
		return groupDAO.getAllCodes();
	}
	
	public List<ServiceFacilityDTO> getAllServiceFacilities() {
		List<ServiceFacility> facilities = facilityDAO.findAll();
		List<ServiceFacilityDTO> list = new ArrayList<ServiceFacilityDTO>();
		
		for (ServiceFacility facility: facilities) 
			list.add(facility.toDTO());
		
		return list;
	}
	
	public LabOrderGroupDTO getLabOrderGroup(String name) {
		LabOrder labOrder = labOrderDAO.findByName(name);
		LabOrderGroup group = null == labOrder ? groupDAO.findByCode(name) : labOrder.getGroup();
		return group.toDTO();
	}
	
	public List<String> getSuggestions(String key) {
		List<String> names = labOrderDAO.findNamesThatBeginWith(key);
		List<String> codes = groupDAO.findCodesThatBeginWith(key);
		names.addAll(codes);
		return names;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LabOrderGroupDTO updateLabOrderGroup(LabOrderGroupDTO dto) {
		if (null == dto.getId()) 
			return createLabOrderGroup(dto);
	
		LabOrderGroup group = groupDAO.findById(dto.getId());
		
		if (null == group)
			return createLabOrderGroup(dto);
		
		LabOrderGroup update =  toLabOrderGroup(dto);
		group.setCode(update.getCode());
		group.setDescription(update.getDescription());
		group.getLabOrderSet().clear();
		
		for (LabOrder order: update.getLabOrderSet()) {
			if (null == order.getId()) 
				labOrderDAO.persist(order);
			else 
				order = updateLabOrder(order);
			
			group.addLabOrder(order);
		}
		
		groupDAO.merge(group);
		
		return group.toDTO();
	}
	
	private LabOrder toLabOrder(LabOrderDTO dto) {
		LabOrder order = new LabOrder();
		order.setId(dto.getId());
		order.setName(dto.getName());
		order.setDescription(dto.getDescription());
		
		if (null != dto.getGroup() && null != dto.getGroup().getId()) {
			LabOrderGroup group = groupDAO.findById(dto.getGroup().getId());
			order.setGroup(group);
		}
		
		if (null != dto.getServiceFacility()) {
			ServiceFacility facility;
			if (null == dto.getServiceFacility().getId()) {
				facility = toServiceFacility(dto.getServiceFacility());
				facilityDAO.persist(facility);
			} else {
				facility = facilityDAO.findById(dto.getServiceFacility().getId());
			}
			order.setServiceFacility(facility);
		}
		
		return order;
	}
	
	private LabOrderGroup toLabOrderGroup(LabOrderGroupDTO dto) {
		LabOrderGroup group = new LabOrderGroup();
		group.setId(dto.getId());
		group.setCode(dto.getCode());
		group.setDescription(dto.getDescription());
		
		for (LabOrderDTO order: dto.getLabOrderSet())
			group.addLabOrder(toLabOrder(order));
		
		return group;
	}
	
	private ServiceFacility toServiceFacility(ServiceFacilityDTO dto) {
		ServiceFacility facility = new ServiceFacility();
		facility.setId(dto.getId());
		facility.setName(dto.getName());
		return facility;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private LabOrder updateLabOrder(LabOrder update) {
		LabOrder order = labOrderDAO.findById(update.getId());
		order.setDescription(update.getDescription());
		order.setName(update.getName());
		order.setServiceFacility(update.getServiceFacility());
		labOrderDAO.merge(order);
		return order;
	}
				
}
