/**
 * 
 */
package gov.cdc.irdu.athena.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author Joel M. Rives
 * Mar 22, 2011
 */

@RemoteServiceRelativePath("services/labService")
public interface LabService extends RemoteService {
	
	public LabOrderGroupDTO createLabOrderGroup(LabOrderGroupDTO labOrderGroup);
	
	public ServiceFacilityDTO createServiceFacility(ServiceFacilityDTO dto);
	
	public void deleteLabOrderGroup(LabOrderGroupDTO labOrderGroup);
	
	public List<String> getAllLabOrderGroupCodes();
	
	public List<ServiceFacilityDTO> getAllServiceFacilities();
	
	public LabOrderGroupDTO getLabOrderGroup(String labOrderName);
	
	public List<String> getSuggestions(String key);
	
	public LabOrderGroupDTO updateLabOrderGroup(LabOrderGroupDTO labOrderGroup);
	
}
