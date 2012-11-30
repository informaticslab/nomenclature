package gov.cdc.irdu.athena.client;

import gov.cdc.irdu.athena.shared.LabOrderDTO;
import gov.cdc.irdu.athena.shared.LabOrderGroupDTO;
import gov.cdc.irdu.athena.shared.LabService;
import gov.cdc.irdu.athena.shared.LabServiceAsync;
import gov.cdc.irdu.athena.shared.ServiceFacilityDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditGroupDialog extends DialogBox {
	
	private static final String ADD_NEW = "Add new ...";

	private final LabServiceAsync labService = GWT.create(LabService.class);
	
	private FlexTable labOrdersTable;
	private TextBox codeTextBox;
	private Button saveButton;

	private LabOrderGroupDTO labOrderGroup;
	private List<ServiceFacilityDTO> serviceFacilities;
	private List<String> existingGroupCodes;
	private AsyncCallback<LabOrderGroupDTO> callback;
	private ServiceFacilityPanel serviceFacilityPanel = new ServiceFacilityPanel();

	public EditGroupDialog() {
		setGlassEnabled(true);
		
		labService.getAllLabOrderGroupCodes(new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());	
			}

			public void onSuccess(List<String> result) {
				existingGroupCodes = result;
			}			
		});
		
		labService.getAllServiceFacilities(new AsyncCallback<List<ServiceFacilityDTO>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());	
			}

			public void onSuccess(List<ServiceFacilityDTO> result) {
				serviceFacilities = result;
			}						
		});
		
		createDialog();
	}
	
	public void edit(LabOrderGroupDTO group, AsyncCallback<LabOrderGroupDTO> callback) {
		this.callback = callback;
		
		clearLabOrdersTable();
		saveButton.setEnabled(false);
		
		if (null == group) {
			setText("Create Lab Order Group");
			labOrderGroup = new LabOrderGroupDTO();
			codeTextBox.setValue("");
		} else {
			setText("Edit Lab Order Group");
			labOrderGroup = group;
			codeTextBox.setValue(group.getCode());
		}
		
		if (null != labOrderGroup.getLabOrderSet()) {
			for (LabOrderDTO dto: labOrderGroup.getLabOrderSet()) 
				addLabOrder(dto);

			updateAllDropDowns(null);
		}
		
		this.center();
		this.show();
	}
	
	private void addLabOrder(LabOrderDTO labOrder) {
		int row = labOrdersTable.getRowCount();
		
		TextBox box = new TextBox();
		if (null != labOrder.getId())
			box.setTitle(labOrder.getId().toString());
		box.setWidth("100%");
		box.setValue(labOrder.getName());
		labOrdersTable.setWidget(row, 0, box);
		labOrdersTable.getCellFormatter().addStyleName(row, 0, "editLabOrderColumn");

		labOrdersTable.setWidget(row, 1, createFacilityDropDown(labOrder.getServiceFacility()));
		labOrdersTable.getCellFormatter().addStyleName(row, 1, "editFacilityColumn");
		
		labOrdersTable.setWidget(row, 2, createDescriptionButton(labOrder));
		labOrdersTable.getCellFormatter().addStyleName(row, 2, "descriptionColumn");
		
		labOrdersTable.setWidget(row, 3, createRemoveButton(row));
		labOrdersTable.getCellFormatter().addStyleName(row, 3, "deleteColumn");
		
		saveButton.setEnabled(true);
	}
	
	private void cancel() {
		hide();
	}
	
	private Button createAddButton() {
		Button addButton = new Button("+ Add");
		addButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				LabOrderDTO labOrder = new LabOrderDTO();
				labOrder.setName("New Lab Order");
				addLabOrder(labOrder);
			}
			
		});
		
		return addButton;
	}
	
	private void clearLabOrdersTable() {
		while (labOrdersTable.getRowCount() > 1) {
			labOrdersTable.removeRow(1);
		}
	}
	
	private Panel createButtonPanel() {
		saveButton = new Button("Save");
		saveButton.setEnabled(false);
		saveButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				done();				
			}
			
		});
		
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				cancel();				
			}
			
		});
		
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(6);
		panel.add(saveButton);
		panel.add(cancelButton);
		
		return panel;
	}
	
	private Panel createCodePanel() {
		Label label = new Label("Group Code");
		label.addStyleName("title");
		
		codeTextBox = new TextBox();
		codeTextBox.setWidth("300px");
		if (null != labOrderGroup)
			codeTextBox.setValue(labOrderGroup.getCode());
		codeTextBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				String value = codeTextBox.getText().trim();
				
				if (labOrderGroup.getCode() != null && value.equals(labOrderGroup.getCode()))
					return;
				
				for (String code: existingGroupCodes) {
					if (value.equals(code)) {
						Window.alert("The group code value must be unique. The code you have entered already exists");
						codeTextBox.setFocus(true);
						codeTextBox.selectAll();
					}
				}
			}			
		});
		
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(6);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		panel.add(label);
		panel.add(codeTextBox);
		
		return panel;
	}
	
	private Button createDescriptionButton(final LabOrderDTO labOrder) {
		final Button button = new Button("Edit");
		button.setTitle(labOrder.getDescription());
		
		final DescriptionPanel descriptionPanel = new DescriptionPanel();
		
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				descriptionPanel.center();
				descriptionPanel.edit(labOrder.getDescription(), new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						Window.alert("Internal error: " + caught.getMessage());
					}

					public void onSuccess(String result) {
						labOrder.setDescription(result);
						button.setTitle(result);
					}					
				});	
			}
		});
				
		return button;
	}
	
	private void createDialog() {
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setSpacing(6);
		
		mainPanel.add(createCodePanel());
		mainPanel.add(createLabOrdersTable());
		mainPanel.add(createAddButton());
		mainPanel.setCellHorizontalAlignment(mainPanel.getWidget(2), HasHorizontalAlignment.ALIGN_LEFT);
		mainPanel.add(createButtonPanel());
		mainPanel.setCellHorizontalAlignment(mainPanel.getWidget(3), HasHorizontalAlignment.ALIGN_RIGHT);
		
		setWidget(mainPanel);
	}
	
	private FlexTable createLabOrdersTable() {
		labOrdersTable = new FlexTable();
		labOrdersTable.setStyleName("labOrdersTable");
		
		labOrdersTable.setText(0, 0, "Lab Order");
		labOrdersTable.getCellFormatter().addStyleName(0, 0, "editLabOrderColumn");
		
		labOrdersTable.setText(0, 1, "Lab Provider");
		labOrdersTable.getCellFormatter().addStyleName(0, 1, "editFacilityColumn");
		
		labOrdersTable.setText(0, 2, "Description");
		labOrdersTable.getCellFormatter().addStyleName(0, 2, "descriptionColumn");
		
		labOrdersTable.setText(0, 3, "Delete");
		labOrdersTable.getCellFormatter().addStyleName(0, 3, "deleteColumn");
		
		labOrdersTable.getRowFormatter().addStyleName(0, "labOrdersHeader");
		
		return labOrdersTable;
	}
	
	private ListBox createFacilityDropDown(ServiceFacilityDTO facility) {
		final ListBox box = new ListBox();
		box.addStyleName("facilityListBox");
		box.setVisibleItemCount(1);
		box.addItem("None");
		
		int selectedIndex = 0;
		
		for (int i = 0; i < serviceFacilities.size(); i++) {
			String name = serviceFacilities.get(i).getName();
			box.addItem(name);
			if (null != facility && facility.getName().equals(name))
				selectedIndex = i + 1;
		}
		
		box.addItem(ADD_NEW);
		box.setSelectedIndex(selectedIndex);
		
		box.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String value = box.getItemText(box.getSelectedIndex());
				if (value.equals(ADD_NEW)) {
					showServiceFacilityPanel();
				} else {
					updateAllDropDowns(null);
				}
			}
		});
		
		return box;
	}
	
	private Button createRemoveButton(final int row) {
	    Button removeButton = new Button("x");
	    removeButton.setStyleName("removeButton");
	    removeButton.addClickHandler(new ClickHandler() {
    		public void onClick(ClickEvent event) {
    			labOrdersTable.removeRow(row);
    			updateAllDropDowns(null);
    		}
	    });
	    
	    return removeButton;
	}
	
	private void done() {
		labOrderGroup.setCode(codeTextBox.getValue());
		
		Set<LabOrderDTO> labOrders = new TreeSet<LabOrderDTO>();
		
		for (int row = 1; row < labOrdersTable.getRowCount(); row++) {
			LabOrderDTO order = new LabOrderDTO();

			order.setGroup(labOrderGroup);
			
			TextBox textBox = (TextBox) labOrdersTable.getWidget(row, 0);
			order.setName(textBox.getValue());
			
			String value = textBox.getTitle().trim();
			if (null != value && value.length() > 0)
				order.setId(new Long(value));
			
			ListBox listBox = (ListBox) labOrdersTable.getWidget(row, 1);
			order.setServiceFacility(findServiceFacility(listBox.getValue(listBox.getSelectedIndex())));
			
			Button button = (Button) labOrdersTable.getWidget(row, 2);
			order.setDescription(button.getTitle());
			
			labOrders.add(order);
		}	
		
		labOrderGroup.setLabOrderSet(labOrders);
		
		callback.onSuccess(labOrderGroup);
		hide();
	}
	
	private ServiceFacilityDTO findServiceFacility(String name) {
		for (ServiceFacilityDTO dto: serviceFacilities) 
			if (dto.getName().equals(name))
				return dto;
		
		return null;
	}
	
	private void showServiceFacilityPanel() {
		serviceFacilityPanel.center();
		serviceFacilityPanel.show(new AsyncCallback<String> () {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			public void onSuccess(String result) {
				ServiceFacilityDTO dto = new ServiceFacilityDTO();
				dto.setName(result);
				labService.createServiceFacility(dto, new AsyncCallback<ServiceFacilityDTO>() {
					public void onFailure(Throwable caught) {
						Window.alert("Failed to create a new Laboratory Service Provider");									
					}

					public void onSuccess(ServiceFacilityDTO result) {
						serviceFacilities.add(result);
						updateAllDropDowns(result.getName());
					}
				});
			}
		});
	}
	
	private void updateAllDropDowns(String newFacility) {
		List<String> unused = new ArrayList<String>();
		
		for (ServiceFacilityDTO s: serviceFacilities)
			unused.add(s.getName());

		for (int row = 1; row < labOrdersTable.getRowCount(); row++) {
			ListBox listBox = (ListBox) labOrdersTable.getWidget(row, 1);
			String name = listBox.getItemText(listBox.getSelectedIndex());
			unused.remove(name);
		}
		
		for (int row = 1; row < labOrdersTable.getRowCount(); row++) {
			ListBox listBox = (ListBox) labOrdersTable.getWidget(row, 1);
			String name = listBox.getItemText(listBox.getSelectedIndex());
			listBox.clear();
			
			if (name.equals(ADD_NEW))
				listBox.addItem(newFacility);
			else
				listBox.addItem(name);
			
			if (!name.equals("None"))
				listBox.addItem("None");
			
			for (String text: unused)
				if (!text.equals(newFacility))
					listBox.addItem(text);
			
			listBox.addItem(ADD_NEW);
			listBox.setItemSelected(0, true);
		}
	}
	
}
