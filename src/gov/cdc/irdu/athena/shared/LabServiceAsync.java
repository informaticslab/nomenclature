/**
 * 
 */
package gov.cdc.irdu.athena.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author Joel M. Rives
 * Mar 22, 2011
 */
public interface LabServiceAsync {
	
	void createLabOrderGroup(LabOrderGroupDTO labOrderGroup, AsyncCallback<LabOrderGroupDTO> callback);
	
	void createServiceFacility(ServiceFacilityDTO dto, AsyncCallback<ServiceFacilityDTO> callback);
	
	void deleteLabOrderGroup(LabOrderGroupDTO labOrderGroup, AsyncCallback<Void> callback);
	
	void getAllLabOrderGroupCodes(AsyncCallback<List<String>> callback);
	
	void getAllServiceFacilities(AsyncCallback<List<ServiceFacilityDTO>> callback);

	void getLabOrderGroup(String labOrderName, AsyncCallback<LabOrderGroupDTO> callback);
	
	void getSuggestions(String key, AsyncCallback<List<String>> callback);
	
	void updateLabOrderGroup(LabOrderGroupDTO labOrderGroup, AsyncCallback<LabOrderGroupDTO> callback);
	
}
