package managerWindow;

import javafx.beans.property.SimpleStringProperty;

public class siteListsTable {
	private SimpleStringProperty siteName;
	private SimpleStringProperty siteDescription;
	
	public siteListsTable(String name, String description) {
		this.siteName = new SimpleStringProperty(name);
		this.siteDescription = new SimpleStringProperty(description);
	}
	
	public String getSiteName() {
		return siteName.get();
	}
	public void setSiteName(String siteName) {
		this.siteName = new SimpleStringProperty(siteName);
	}
	public String getSiteDescription() {
		return siteDescription.get();
	}
	public void setSiteDescription(String siteDescription) {
		this.siteDescription = new SimpleStringProperty(siteDescription);;
	}
	
	
}
