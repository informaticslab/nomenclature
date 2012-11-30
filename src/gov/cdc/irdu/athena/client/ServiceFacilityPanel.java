package gov.cdc.irdu.athena.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ServiceFacilityPanel extends PopupPanel {
	
	private TextBox nameEntryBox;
	private Button saveButton;
	
	private AsyncCallback<String> callback;

	public ServiceFacilityPanel() {
		super(true);
		setGlassEnabled(true);

		VerticalPanel mainPanel = new VerticalPanel();
		setWidget(mainPanel);
		mainPanel.setSize("100%", "100%");
		mainPanel.setSpacing(6);

		mainPanel.add(createEntryPanel());
		mainPanel.add(createButtonPanel());
		mainPanel.setCellHorizontalAlignment(mainPanel.getWidget(1), HasHorizontalAlignment.ALIGN_RIGHT);
	}
	
	public void show(AsyncCallback<String> callback) {
		this.callback = callback;
		show();
		nameEntryBox.setFocus(true);
	}
	
	private Panel createButtonPanel() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		panel.setSpacing(6);
	
		saveButton = new Button("Save");
		saveButton.setEnabled(false);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String name = nameEntryBox.getValue();
				callback.onSuccess(name);
				hide();
			}
		});
		
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		panel.add(saveButton);
		panel.add(cancelButton);
		
		return panel;
	}
	
	private Panel createEntryPanel() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		panel.setSpacing(6);
		
		Label label = new Label("Laboratory Service Provider Name:");
		
		nameEntryBox = new TextBox();
		nameEntryBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == 13 && saveButton.isEnabled()) {
					String name = nameEntryBox.getValue();
					callback.onSuccess(name);
					hide();
				}
				
				String name = nameEntryBox.getText();
				
				if (null == name || name.trim().length() == 0)
					saveButton.setEnabled(false);
				else
					saveButton.setEnabled(true);
			}
		});
		
		panel.add(label);
		panel.setCellHorizontalAlignment(label, HasHorizontalAlignment.ALIGN_RIGHT);
		panel.add(nameEntryBox);
		panel.setCellHorizontalAlignment(nameEntryBox, HasHorizontalAlignment.ALIGN_LEFT);
		
		return panel;
	}
	
}
