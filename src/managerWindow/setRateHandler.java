package managerWindow;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class setRateHandler implements Initializable {
	private ManagerHandler managerHandler;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	private static ObservableList<String> collectionResult;
	private static ObservableList<String> cityListResults;
	protected HashMap<String, String> idsAndCityNames;
	private ArrayList<collectionEntity> collectionsOfcity;
	private int selected;


	    
	@FXML
    private ImageView backImg;
	
	@FXML
	private TextField subscriptionPrice;

	@FXML
	private TextField priceOnce;

	@FXML
	private ComboBox<String> cityList;

	@FXML
	private ListView<String> mapCollectionListView;

	public void setManagerHandler(ManagerHandler managerHandler) {
		this.managerHandler = managerHandler;
	}

	// set the list of the cities in the combox
	private void setCityInCombox() {

		sql = "SELECT city.id,city.name FROM city;";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		managerHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		m = managerHandler.chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("no result");

		} else {

			String stringResult;
			String idOfCity, nameOfCity;
			cityListResults.clear();
			for (int i = 0; i < m.size(); i++) {
				idOfCity = m.get(i).get(0);
				nameOfCity = m.get(i).get(1);
				idsAndCityNames.put(nameOfCity, idOfCity);
				stringResult = new String();
				stringResult = m.get(i).get(1);
				cityListResults.add(stringResult);
			}
			cityList.setItems(cityListResults);
		}
	}

	// set the collections map in the view list according to the combox choose
	@FXML
	void chooseeCollection(ActionEvent event) {
		collectionsOfcity = new ArrayList<collectionEntity>();
		String nameOfCity = cityList.getValue();
		String idOfCity = idsAndCityNames.get(nameOfCity);
		sql = "SELECT map_collection.id,map_collection.vertion,map_collection.oneTimePrice,subscriptionPrice FROM (map INNER JOIN map_collection ON map_collection.id = map.id_collaction) WHERE map.city_id = '"
				+ idOfCity + "' AND approved='1'  GROUP BY (map_collection.id);";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		managerHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		m = managerHandler.chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("no result");
		} else {
			// collectionResult
			String stringResult;

			collectionResult.clear();
			String idMapCollection, mapCollectionVertion, collectionPrice, collectionSubscriptionPrice;
			for (int i = 0; i < m.size(); i++) {
				idMapCollection = m.get(i).get(0);
				mapCollectionVertion = m.get(i).get(1);
				collectionPrice = m.get(i).get(2);
				collectionSubscriptionPrice = m.get(i).get(3);
				collectionEntity temp = new collectionEntity(idMapCollection, mapCollectionVertion, collectionPrice,
						collectionSubscriptionPrice);
				stringResult = new String();
				stringResult = "Collection vetion: " + mapCollectionVertion;
				collectionsOfcity.add(temp);
				collectionResult.add(stringResult);
			}
			mapCollectionListView.setItems(collectionResult);
		}
	}

	@FXML
	void getDeitellsOfTheCollection(MouseEvent event) {
		selected = mapCollectionListView.getSelectionModel().getSelectedIndex();
		if (selected != -1) {
			priceOnce.setText(collectionsOfcity.get(selected).getPriceOne());
			subscriptionPrice.setText(collectionsOfcity.get(selected).getPriceSubscription());
		}
	}

	@FXML
	void setRates(ActionEvent event) {//////////////////////
		String PriceOnce,SubscriptionPrice,collectionid;
		PriceOnce = priceOnce.getText();
		SubscriptionPrice = subscriptionPrice.getText();
		collectionid =  collectionsOfcity.get(selected).getIdCollection();
		sql = "INSERT INTO price_for_approve (priceOnce,subsriptionPrice,collection_id) VALUES ('"+ PriceOnce+"','"+SubscriptionPrice +"','"+collectionid+"');";
		sendSQL.clear();
		sendSQL.add("3");
		sendSQL.add(sql);
		managerHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Your rate request has been sent");
	}
	
	@FXML
    void backToMain(MouseEvent event) {
		managerHandler.openThisWindow();
		Stage stage = (Stage) backImg.getScene().getWindow();
		stage.close();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		cityListResults = FXCollections.observableArrayList();
		collectionResult = FXCollections.observableArrayList();
		sendSQL = new ArrayList<Object>();
		idsAndCityNames = new HashMap<String, String>();

		setCityInCombox();

	}
}
//sql = "SELECT map_collection.id,map_collection.vertion,map_collection.price,map.id,map.city_id,map.description,map.id_collaction,city.id,city.name FROM ((map_collection INNER jOIN map ON map.id_collaction = map_collection.id) INNER JOIN city ON map.city_id = city.id) WHERE map_collection.approved='1' GROUP BY (map_collection.id) ;";