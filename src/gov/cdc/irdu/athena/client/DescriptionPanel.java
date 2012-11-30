package gov.cdc.irdu.athena.client;

import gov.cdc.irdu.athena.shared.LabOrderDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DescriptionPanel extends PopupPanel {
	
	private VerticalPanel mainPanel;
	private RichTextArea editArea;
	private Button saveButton;
	private HorizontalPanel buttonPanel;
	
	private AsyncCallback<String> callback;

	public DescriptionPanel() {
		super(true);
		setGlassEnabled(true);
		
		mainPanel = new VerticalPanel();
		setWidget(mainPanel);
		mainPanel.setSize("100%", "100%");
		mainPanel.setSpacing(6);
		
		mainPanel.add(createTextPanel());
		mainPanel.add(createButtonPanel());
		mainPanel.setCellHorizontalAlignment(mainPanel.getWidget(1), HasHorizontalAlignment.ALIGN_RIGHT);
	}
	
	public void edit(String description, AsyncCallback<String> callback) {
		editArea.setText(description);
		this.callback = callback;
		show();
	}
	
	private Panel createButtonPanel() {
		buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(6);
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		saveButton = new Button("Save");
		saveButton.setEnabled(false);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				callback.onSuccess(editArea.getText());
				hide();
			}
		});
		
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
	
		return buttonPanel;
	}
	
	private Panel createTextPanel() {
		ScrollPanel panel = new ScrollPanel();
		
		editArea = new RichTextArea();
		editArea.setWidth("500px");
		editArea.setHeight("400px");
		
		editArea.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				saveButton.setEnabled(true);	
			}			
		});
		
		panel.add(editArea);
		
		return panel;
	}

}
