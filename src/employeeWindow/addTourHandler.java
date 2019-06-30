package employeeWindow;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import managerWindow.siteEntity;
import managerWindow.toursEntity;

public class addTourHandler implements Initializable {
	private mapDataHandlerEmployee mapDataHandler;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	protected static ChatClient chat = null;
	private static ObservableList<String> sitesResult;
	private static ObservableList<String> sitesInTourListView;
	private ArrayList<siteEntity> site;
	private ArrayList<siteEntity> siteInTour;
	private boolean ifCanSave = false;

	@FXML
	private TextArea descriptionOfTour;

	@FXML
	private Button delieteSiteBtn;

	@FXML
	private Label _labelAddOrEdit;

	@FXML
	private TextField nameOfTheTour;

	@FXML
	private ListView<String> tourListView;

	@FXML
	private ListView<String> sitesInCityListview;

	@FXML
	private Button addPlaceBTN;

	@FXML
	private Button saveTheTourBtn;

	public void setMapDataHandlerEmployee(mapDataHandlerEmployee mapDataHandler) {
		this.mapDataHandler = mapDataHandler;
	}

	@FXML
	void somethingChanged(KeyEvent event) {
		if (!nameOfTheTour.getText().isEmpty() && !siteInTour.isEmpty() && !descriptionOfTour.getText().isEmpty())
			ifCanSave = true;
		if (ifCanSave)
			saveTheTourBtn.setDisable(false);
		else
			saveTheTourBtn.setDisable(true);
	}

	@FXML
	void checkIfChoose(MouseEvent event) {
		if (sitesInCityListview.getSelectionModel().getSelectedIndex() != -1) {
			addPlaceBTN.setDisable(false);
		}
	}

	@FXML
	void checkIfTourListClicked(MouseEvent event) {
		if (tourListView.getSelectionModel().getSelectedIndex() != -1) {
			delieteSiteBtn.setDisable(false);
		}
	}

	@FXML
	void deleteSiteFromTour(ActionEvent event) {
		if (tourListView.getSelectionModel().getSelectedIndex() != -1) {
			int index = tourListView.getSelectionModel().getSelectedIndex();
			site.add(siteInTour.remove(index));
			insertItems(siteInTour, tourListView, sitesInTourListView);
			insertItems(site, sitesInCityListview, sitesResult);
		}
	}

	@FXML
	void saveTheTour(ActionEvent event) {
		String idOfTour="";
		String name = nameOfTheTour.getText();
		String description = descriptionOfTour.getText();
		sql = "INSERT INTO tours (description,name) VALUES ('" + description + "','" + name + "');";
		sendSQL.clear();
		sendSQL.add("3");
		sendSQL.add(sql);
		mapDataHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get the id of the last insert
		sql = "SELECT id FROM tours ORDER BY ID DESC LIMIT 1;";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		mapDataHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m = mapDataHandler.chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("no result");

		} else {
			idOfTour = m.get(0).get(0);
			String idOfSite;
			for (siteEntity res : siteInTour) {
				System.out.println(res);
				idOfSite = res.getID();
				sql = "INSERT INTO site_tour (site_id, tour_id, id_collection) VALUES ('" + idOfSite + "','" + idOfTour + "','"+mapDataHandler.collection_id+"');";
				sendSQL.clear();
				sendSQL.add("3");
				sendSQL.add(sql);
				mapDataHandler.chat.handleMessageFromClient(sendSQL);
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(null, "The tour has been added successfully");
			 mapDataHandler.initializeListViewOfTour();
			 Stage stage2 = (Stage) saveTheTourBtn.getScene().getWindow();
				stage2.close();

		}
		toursEntity tE = new toursEntity(Integer.parseInt(idOfTour), description,name);
		mapDataHandler.tours.add(tE);
		mapDataHandler.initializeListViewOfTour();		
	}

	 @FXML
	    void closeTour(ActionEvent event) {
		 Stage stage2 = (Stage) saveTheTourBtn.getScene().getWindow();
			stage2.close();
	    }
	 
	@FXML
	void addPlaceTotour(ActionEvent event) {
		if (sitesInCityListview.getSelectionModel().getSelectedIndex() != -1) {
			int index = sitesInCityListview.getSelectionModel().getSelectedIndex();
			siteInTour.add(site.remove(index));
			insertItems(siteInTour, tourListView, sitesInTourListView);
			insertItems(site, sitesInCityListview, sitesResult);
			
			if (!nameOfTheTour.getText().isEmpty() && !siteInTour.isEmpty() && !descriptionOfTour.getText().isEmpty())
				ifCanSave = true;
			if (ifCanSave)
				saveTheTourBtn.setDisable(false);
			else
				saveTheTourBtn.setDisable(true);
		}
	}

	private void insertItems(ArrayList<siteEntity> arr, ListView<String> t, ObservableList<String> ob) {
		ob = FXCollections.observableArrayList();
		String res;
		for (int i = 0; i < arr.size(); i++) {
			res = new String();
			i++;
			res = i + " - " + arr.get(i - 1).getName();
			i--;
			ob.add(res);
		}
		t.setItems(ob);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("tour collection_id: "+mapDataHandler.collection_id);
		chat = mapDataHandler.chat;
		siteInTour = new ArrayList<siteEntity>();
		sendSQL = new ArrayList<Object>();
		sql = "SELECT site.id,site.name,site.type,site.description,site.accessing,site.time,map_site.location_x,map_site.location_y FROM (site INNER JOIN map_site ON map_site.site_id = site.id) WHERE map_site.Map_id='"
				+ mapDataHandler.idOfCurrentMap 
				+ "' AND id_collection='"+ mapDataHandler.collection_id +"';";
		/*sql = "SELECT site.id,site.name FROM (map_site INNER JOIN site ON map_site.site_id = site.id) WHERE map_site.map_id = '"
				+ mapDataHandler.idOfCurrentMap + "';";*/
		sendSQL.add("2");
		sendSQL.add(sql);
		mapDataHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		m = mapDataHandler.chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("no result");

		} else {
			sitesResult = FXCollections.observableArrayList();
			sitesInTourListView = FXCollections.observableArrayList();
			sitesResult.clear();
			site = new ArrayList<siteEntity>();

			siteEntity temp;
			String siteString, nameOfSite;
			for (int i = 0, k; i < m.size(); i++) {
				siteString = new String();
				temp = new siteEntity();
				nameOfSite = m.get(i).get(1);

				temp.setID(m.get(i).get(0));
				temp.setName(nameOfSite);
				k = i + 1;
				siteString = k + " - " + nameOfSite;
				site.add(temp);
				sitesResult.add(siteString);
			}
			sitesInCityListview.setItems(sitesResult);
			// System.out.println(sitesResult);


		}
	}
}
