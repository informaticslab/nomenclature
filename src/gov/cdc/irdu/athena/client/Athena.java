package gov.cdc.irdu.athena.client;

import gov.cdc.irdu.athena.shared.LabOrderDTO;
import gov.cdc.irdu.athena.shared.LabOrderGroupDTO;
import gov.cdc.irdu.athena.shared.LabService;
import gov.cdc.irdu.athena.shared.LabServiceAsync;
import gov.cdc.irdu.athena.shared.ServiceFacilityDTO;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Athena implements EntryPoint {
	
	private final LabServiceAsync labService = GWT.create(LabService.class);

	private MultiWordSuggestOracle oracle;
	private List<String> validChoices;
	
	private TextBox searchEntryBox;
	private FlexTable labOrdersTable;
	private Button editButton;
	private Button deleteButton;
	private EditGroupDialog editDialog = new EditGroupDialog();
	
	private LabOrderGroupDTO currentLabOrderGroup = null;
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setStyleName("mainPanel");
		mainPanel.setSpacing(10);
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.add(mainPanel);
		
		mainPanel.add(createSearchPanel());
		mainPanel.add(createLabOrdersPanel());		
		mainPanel.add(createButtonPanel());
	}
	
	private void clearLabOrdersTable() {
		while (labOrdersTable.getRowCount() > 1) {
			labOrdersTable.removeRow(1);
		}
	}
	
	private Panel createButtonPanel() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(6);
		
		Button createButton = new Button("Create");
		createButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				editGroup(null);
			}					
		});
		
		editButton = new Button("Edit");
		editButton.setEnabled(false);
		editButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				editGroup(currentLabOrderGroup);
			}					
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setEnabled(false);
		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (Window.confirm("Are you sure you want to delete this Lob Order Group?"))
					labService.deleteLabOrderGroup(currentLabOrderGroup, new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}

						public void onSuccess(Void result) {
							Window.alert("The Lab Order Group " + currentLabOrderGroup.getCode() + " was successfully deleted");
							currentLabOrderGroup = null;
							populateLabOrdersTable(null);
							searchEntryBox.setValue("");
							searchEntryBox.setFocus(true);
						}
					});					
			}					
		});
				
		panel.add(createButton);
		panel.add(editButton);
		panel.add(deleteButton);
		
		return panel;
	}
	
	private Panel createSearchPanel() {
		HorizontalPanel searchPanel = new HorizontalPanel();
		searchPanel.setSpacing(6);
		
		Label labOrderLabel = new Label("Lab Order");
		labOrderLabel.setStyleName("title");
		
		searchPanel.add(labOrderLabel);
		searchPanel.setCellHorizontalAlignment(labOrderLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		searchEntryBox = new TextBox();
		searchEntryBox.setStyleName("entryBox");
		searchEntryBox.setWidth("300px");
		searchEntryBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				String text = searchEntryBox.getText();
				
				if (text.length() == 0) {
					currentLabOrderGroup = null;
					populateLabOrdersTable(null);
				}
			}
		});
		searchEntryBox.addKeyPressHandler(new KeyPressHandler() {	
			public void onKeyPress(KeyPressEvent event) {
				char code = event.getCharCode();
				String text = searchEntryBox.getText();
				
				if (code == 13) {
					if (validChoices.contains(text))
						search(text);
					else
						Window.alert("The term '" + text + "' is not a valid choice");
					return;
				}
				
				if (code > 31 && code < 127) {
					if (text.length() > 0)
						return;
					
				}
				
				getSuggestions(text);
			}
		});
		
		oracle = new MultiWordSuggestOracle();
		SuggestBox suggest = new SuggestBox(oracle, searchEntryBox);
		
		searchPanel.add(suggest);
		searchPanel.setCellHorizontalAlignment(searchEntryBox, HasHorizontalAlignment.ALIGN_LEFT);
		
		Button goButton = new Button("Go");
		goButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				search(searchEntryBox.getText());
			}
			
		});
		
		searchPanel.add(goButton);
	
		return searchPanel;
	}
	
	private Panel createLabOrdersPanel() {
		ScrollPanel scrollPanel = new ScrollPanel();
		labOrdersTable = new FlexTable();
		labOrdersTable.setStyleName("labOrdersTable");
		scrollPanel.setWidget(labOrdersTable);
		
		labOrdersTable.setText(0, 0, "Lab Order");
		labOrdersTable.getCellFormatter().addStyleName(0, 0, "labOrderColumn");
		labOrdersTable.setText(0, 1, "Lab Provider");
		labOrdersTable.getCellFormatter().addStyleName(0, 1, "facilityColumn");
		
		labOrdersTable.getRowFormatter().addStyleName(0, "labOrdersHeader");

		return scrollPanel;
	}
	
	private void editGroup(final LabOrderGroupDTO group) {
		editDialog.edit(group, new AsyncCallback<LabOrderGroupDTO>() {
			public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
			}

			public void onSuccess(LabOrderGroupDTO labOrderGroup) {
				if (null == group)
					labService.createLabOrderGroup(labOrderGroup, new AsyncCallback<LabOrderGroupDTO>() {
						public void onFailure(Throwable caught) {
							Window.alert("Error: " + caught.getMessage());
						}
	
						public void onSuccess(LabOrderGroupDTO labOrderGroup) {
							populateLabOrdersTable(labOrderGroup);
						}
					});
				else
					labService.updateLabOrderGroup(labOrderGroup, new AsyncCallback<LabOrderGroupDTO>() {
						public void onFailure(Throwable caught) {
							Window.alert("Error: " + caught.getMessage());
						}
	
						public void onSuccess(LabOrderGroupDTO labOrderGroup) {
							populateLabOrdersTable(labOrderGroup);
						}
					});
			}
		});
	}
		
	private void getSuggestions(final String searchText) {
		labService.getSuggestions(searchText, new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Problem getting suggestions from server: " + caught.getMessage());
			}

			public void onSuccess(List<String> names) {
				if (names.size() == 0) {
					return;
				}

				oracle.clear();
				oracle.addAll(names);
				validChoices = names;
			}		
		});
	}
	
	private void populateLabOrdersTable(LabOrderGroupDTO group) {
		clearLabOrdersTable();
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
		if (null == group || null == group.getLabOrderSet())
			return;

		int row = 1;
		for (LabOrderDTO dto: group.getLabOrderSet()) {
			Label labOrderLabel = new Label(dto.getName());
			labOrderLabel.setTitle(dto.getDescription());
			labOrderLabel.addStyleName("tableLabel");
			labOrdersTable.setWidget(row, 0, labOrderLabel);
			ServiceFacilityDTO facility = dto.getServiceFacility();
			labOrdersTable.setText(row, 1, null == facility ? "None" : facility.getName());
			row++;
		}
		
		currentLabOrderGroup = group;
		editButton.setEnabled(true);
		deleteButton.setEnabled(true);
	}
	
	private void search(final String searchText) {
		currentLabOrderGroup = null;
		
		labService.getLabOrderGroup(searchText, new AsyncCallback<LabOrderGroupDTO>() {

			public void onFailure(Throwable caught) {
				Window.alert("Service error: " + caught.getMessage());
			}

			public void onSuccess(LabOrderGroupDTO labOrderGroup) {
				if (null == labOrderGroup) {
					if (Window.confirm("There is no entry for that search term. Would you like to create a new Lab Order Group?")) {
						editGroup(null);
					}
					return;
				}
				
				
				if (null == labOrderGroup.getLabOrderSet() || labOrderGroup.getLabOrderSet().size() == 0) {
					if (Window.confirm("This Lab Order Group is empty. Would you like to edit it?")) {
						currentLabOrderGroup = labOrderGroup;
						editGroup(currentLabOrderGroup);
					}
					return;
				}

				populateLabOrdersTable(labOrderGroup);
			}
			
		});
		
	}
	
}
