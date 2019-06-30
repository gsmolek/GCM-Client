/**
 * @author GILAD MOLEK
 * @author DORON TUCHMAN
 * @author MATI HALFA
 * @author MATAN ASULIN
 * @author SHARONE BURSHTIEN
 *
 *	@version 1.40
 *	@since 2019
 */
package clientWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import Login.LoginHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import ServerConnection.ChatClient;
/**
 * this class handles the client window
 */
public class ClientWindowHandler implements Initializable {
	
/*
 *private parameters for the first window 
 * 
 */
	private LoginHandler log;
	private ArrayList<String> mapIDCollection;
	protected int chois;
	protected int period;
	protected static String user_id ;
	public String mapIDForBuy = "0";
	protected ChatClient chat = null;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	private ArrayList<String> paths;
	private int searchType = 1;
	private ArrayList<String> collections;
	protected static String idCollection;
	private static final ObservableList<String> purchaseMap = FXCollections.observableArrayList();
	private static final ObservableList<String> searchResult = FXCollections.observableArrayList();
	
	private FXMLLoader loader;
	private Pane root;
	private Scene scene;
	private Stage stage, stage2;
	public static String fisrtName;
	

	public void setLoginHandler(LoginHandler log) {
		this.log = log;
		user_id = log.theUserID;
	}
	
    /*
     * fxml buttons params
     */
	
	/*
	 * search button
	 */
	@FXML
	private Button _searchButton;
	/*
	 * a button to buy map
	 */
	@FXML
	private Button _buyMapButton;
	/*
	 * a button to download map
	 */
	@FXML
	private Button _downloadMapButton;
	/*
	 * our question button
	 */
	@FXML
	private Button _forQuestionButton;
	/*
	 * a button for city name
	 */
	@FXML
	private RadioButton _radioCityName;
	/*
	 * a button for map description
	 */
	@FXML
	private RadioButton _radioDescription;

	@FXML
	private ListView<String> _listViewResult;

	@FXML
	private Label _userNameLabel;

	@FXML
	private TextArea _searchTextFiled;
	/*
	 * a button for a place of interest 
	 */
	@FXML
	
	private RadioButton _radioPlaceOfInterestName;

	@FXML
	private Button _noButton;

	@FXML
	private Button _yesButton;

	@FXML
	private ComboBox<String> _comboBoxPeriod;

	@FXML
	private ListView<String> purchasesList;

	@FXML
	private Label resultLable;

	@FXML
	void radioCitySelect(ActionEvent event) {
		searchType = 1;
	}

	@FXML
	void radioPlaceOfInterestName(ActionEvent event) {
		searchType = 2;
	}

	@FXML
	void radioDescription(ActionEvent event) {
		searchType = 3;
	}

	@FXML
	/*
	 * this method handles the event of the click buy a map 
	 */
	void clickBuyMapButton(ActionEvent event) {
		int selected = _listViewResult.getSelectionModel().getSelectedIndex();
		mapIDForBuy = mapIDCollection.get(selected);
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/clientWindow/ChooseTypeOfPruchaseWindow.fxml"));
			 root = loader.load();

			 scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			typeOfpurchaseHandller control = loader.getController();

			control.setClientWindowHandler(this);
			// control.getMapID(mapIDForBuy);

			 stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    /**
    * checks if text filed is empty
    */
	@FXML
	void checkIfEmpty(KeyEvent event) {
		if (!_searchTextFiled.getText().isEmpty()) {
			_searchButton.setDisable(false);
		} else
			_searchButton.setDisable(true);
	}
	/**
	 * search of map handle 
	 * gets an event of map search
	 * @param event
	 */

	@FXML
	void searchForMap(ActionEvent event) {
		resultLable.setText("");
		String text = _searchTextFiled.getText();
		mapIDCollection = new ArrayList<String>();
		String resultSearch = "";
		searchResult.clear();
		sendSQL.clear();
		String res = "", resultOfMapList = null;
		/*
		 * searchType: 1 - Search by city ,2 - search by places ,3 - search by
		 * description.
		 */

		switch (searchType) {
		case 1:
			sendSQL.add("2");

			// get number of maps
			sql = "SELECT count(city.name) FROM (city INNER JOIN map ON city.id = map.city_id) WHERE city.name = '"
					+ text + "';";
			sendSQL.add(sql);
			chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m = chat.getArray();
			if (m == null || m.isEmpty()) {
				System.out.println("no result");
				res = "";
			} else {
				res += "number of maps is: " + m.get(0).get(0) + " \n";

				sendSQL.clear();
				sendSQL.add("2");

				// get the number of place of interest of the city
				sql = "SELECT count(*) FROM ((city INNER JOIN map ON map.city_id = city.id) INNER JOIN map_site ON map.id = map_site.map_id) WHERE city.name='"+text+"' GROUP BY (map_site.id_collection) ORDER BY id_collection DESC LIMIT 1;";
				sendSQL.add(sql);
				chat.handleMessageFromClient(sendSQL);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m = chat.getArray();
				if (m == null || m.isEmpty()) {
					System.out.println("no result");
				} else {
					res += "number of place of intrest is: " + m.get(0).get(0) + " \n";

					sendSQL.clear();
					sendSQL.add("2");

					// get the number of tours in the city
					sql = "SELECT count(*) FROM (((city INNER JOIN map ON city.id = map.city_id) INNER JOIN map_site ON map_site.map_id = map.id) INNER JOIN site_tour ON site_tour.site_id = map_site.site_id) WHERE city.name='"+text+"' AND site_tour.id_collection = ( SELECT map_mapcollection.collection_id fROM ((city INNER JOIN map ON map.city_id = city.id) INNER JOIN map_mapcollection ON map_mapcollection.map_id = map.id) WHERE city.name = '"+text+"' ORDER BY collection_id DESC LIMIT 1 )  GROUP BY site_tour.tour_id; " ;
					sendSQL.add(sql);

					chat.handleMessageFromClient(sendSQL);
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m = chat.getArray();
					if (m == null || m.isEmpty()) {
						System.out.println("no result");
						res = "";
					} else {
						res += "number of tours is: " + m.size() + " \n";

						sendSQL.clear();
						sendSQL.add("2");

						// fill the list view with the maps list
						sql = "SELECT map.id_collaction, map.description FROM ((city INNER JOIN map ON city.id = map.city_id) INNER JOIN map_collection ON map.id_collaction = map_collection.id) WHERE approved='1' AND city.name='"
								+ text + "';";
						sendSQL.add(sql);
						chat.handleMessageFromClient(sendSQL);
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						m = chat.getArray();
						if (m == null || m.isEmpty()) {
							System.out.println("not buy anything");
							res = "";
						} else {
							// get the maps description of the requested city and insert into the listView
							for (int i = 0; i < m.size(); i++) {
								mapIDCollection.add(m.get(i).get(0));
								resultOfMapList = new String();
								resultOfMapList += m.get(i).get(1) + " ";
								searchResult.add(resultOfMapList);
							}

						}

						// resultOfMapList
					}
					_listViewResult.setItems(searchResult);
					// System.out.println("search" +);
					System.out.println(res);
				}
			}

			break;
		case 2:

			sendSQL.add("2");

			// get number of maps
			sql = "SELECT count(map.id) FROM (((site INNER JOIN map_site ON site.id = map_site.site_id) INNER JOIN map ON map_site.map_id = map.id) INNER JOIN map_collection ON map_collection.id = map.id_collaction) WHERE map_collection.approved ='1' AND site.name = '"
					+ text + "';";
			sendSQL.add(sql);
			chat.handleMessageFromClient(sendSQL);

			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			m = chat.getArray();
			if (m == null || m.isEmpty()) {
				System.out.println("no result");
				res = "";
			} else {
				res += "number of maps is: " + m.get(0).get(0) + " \n";

				sendSQL.clear();
				sendSQL.add("2");

				// get the number of place of interest of the city
				sql = "SELECT count(site.name) FROM site WHERE  site.name='" + text + "';";
				sendSQL.add(sql);
				chat.handleMessageFromClient(sendSQL);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m = chat.getArray();
				if (m == null || m.isEmpty()) {
					System.out.println("no result");
					res = "";
				} else {
					res += "number of place of intrest is: " + m.get(0).get(0) + " \n";

					sendSQL.clear();
					sendSQL.add("2");

					// get the number of tours in the city
					sql = "SELECT count(site.id) FROM (site INNER JOIN site_tour ON site.id = site_tour.site_id) WHERE site.name='"
							+ text + "' GROUP BY site_tour.tour_id;";
					sendSQL.add(sql);

					chat.handleMessageFromClient(sendSQL);
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m = chat.getArray();
					if (m == null || m.isEmpty()) {
						System.out.println("no result");
						res = "";
					} else {
						res += "number of tours is: " + m.size() + " \n";

						sendSQL.clear();
						sendSQL.add("2");

						// fill the list view with the maps list
						sql = "SELECT map.id_collaction,city.name,map.description FROM ((((site INNER JOIN map_site ON site.id = map_site.site_id) INNER JOIN map ON map_site.map_id = map.id) INNER JOIN city ON map.city_id = city.id) INNER JOIN map_collection ON map.id_collaction = map_collection.id ) WHERE map_collection.approved ='1' AND site.name ='"
								+ text + "';";
						sendSQL.add(sql);
						chat.handleMessageFromClient(sendSQL);
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						m = chat.getArray();
						if (m == null || m.isEmpty()) {
							System.out.println("not buy anything");
						} else {
							// get the maps description of the requested city and insert into the listView
							for (int i = 0; i < m.size(); i++) {
								mapIDCollection.add(m.get(i).get(1));
								resultOfMapList = new String();
								resultOfMapList += m.get(i).get(2) + " ";
								searchResult.add(resultOfMapList);
							}

						}

						// resultOfMapList
					}
					// System.out.println("search" +);
					System.out.println(res);
				}
			}
			break;
		case 3:
			System.out.println("3");
			String temp = text;
			temp = removeWord(temp);

			sendSQL.add("2");

			/**
			 *  get number of maps
			 */
			sql = "SELECT count(map.id) FROM (((site INNER JOIN map_site ON site.id = map_site.site_id) INNER JOIN map ON map_site.map_id = map.id) INNER JOIN map_collection ON map_collection.id = map.id_collaction) WHERE map_collection.approved ='1' AND (site.description LIKE '%"
					+ temp + "%' OR site.type LIKE '%" + temp + "%');";
			sendSQL.add(sql);
			chat.handleMessageFromClient(sendSQL);

			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			m = chat.getArray();
			if (m == null || m.isEmpty()) {
				System.out.println("no result");
				res = "";
			} else {
				res += "number of maps is: " + m.get(0).get(0) + " \n";

				sendSQL.clear();
				sendSQL.add("2");

				/**
				 * get the number of place of interest of the city
				 */ 
				
				sql = "SELECT count(site.name) FROM site WHERE  site.description LIKE '%" + temp
						+ "%' OR site.type LIKE '%" + temp + "%';";
				sendSQL.add(sql);
				chat.handleMessageFromClient(sendSQL);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m = chat.getArray();
				if (m == null || m.isEmpty()) {
					System.out.println("no result");
					res = "";
				} else {
					res += "number of place of intrest is: " + m.get(0).get(0) + " \n";

					sendSQL.clear();
					sendSQL.add("2");

					/**
					 * get the number of tours in the city
					 */ 
					sql = "SELECT count(site.id) FROM (site INNER JOIN site_tour ON site.id = site_tour.site_id) WHERE (site.description LIKE '%"
							+ temp + "%' OR site.type LIKE '%" + temp + "%') GROUP BY site_tour.tour_id;";
					sendSQL.add(sql);

					chat.handleMessageFromClient(sendSQL);
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m = chat.getArray();
					if (m == null || m.isEmpty()) {
						System.out.println("no result");
						res = "";
					} else {
						res += "number of tours is: " + m.size() + " \n";

						sendSQL.clear();
						sendSQL.add("2");

						/**
						 * fill the list view with the maps list
						 */ 
						sql = "SELECT map.id_collaction,city.name,map.description FROM ((((site INNER JOIN map_site ON site.id = map_site.site_id) INNER JOIN map ON map_site.map_id = map.id) INNER JOIN city ON map.city_id = city.id) INNER JOIN map_collection ON map.id_collaction = map_collection.id ) WHERE map_collection.approved ='1' AND (site.description LIKE '%"
								+ temp + "%' OR site.type LIKE '%" + temp + "%');";
						sendSQL.add(sql);
						chat.handleMessageFromClient(sendSQL);
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						m = chat.getArray();
						if (m == null || m.isEmpty()) {
							System.out.println("not buy anything");
						} else {
							/**
							 * get the maps description of the requested city and insert into the listView
							 */ 
							for (int i = 0; i < m.size(); i++) {
								mapIDCollection.add(m.get(i).get(1));
								resultOfMapList = new String();
								resultOfMapList += m.get(i).get(2) + " ";
								searchResult.add(resultOfMapList);
							}

						}

						/**
						 * resultOfMapList
						 */ 
					}
					// System.out.println("search" +);
					System.out.println(res);
				}
			}

			break;
		}
		if (!searchResult.isEmpty()) {
			_listViewResult.setDisable(false);
			_listViewResult.setItems(searchResult);

		} else
			_listViewResult.setDisable(true);
		resultLable.setText(res);

	}

	protected void openThisWindow() {
		stage2.show();
	}

  
	/**
	 * open view of map window
	 * get event as param
	 * @param event
	 */
	
	@FXML
    void openViewMapWindow(ActionEvent event) {
    	idCollection = collections.get( purchasesList.getSelectionModel().getSelectedIndex() );
    	
    	try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/clientWindow/ShowMap.fxml"));
			 root = loader.load();

			 scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			WatchMapHandler control = loader.getController();

			control.setClientWindowHandler(this,idCollection);
			// control.getMapID(mapIDForBuy);

			
			
			stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			stage2 = (Stage) _listViewResult.getScene().getWindow();
			stage2.hide();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//controlEmployee.setLoginHandler(this);
    }
    
	  @FXML
	    void checkIfPurchasesChoose(MouseEvent event) {
		  if (purchasesList.getSelectionModel().getSelectedIndex() != -1)
			  _downloadMapButton.setDisable(false);
	    }
	  
	@FXML
	void checkIfChoose(MouseEvent event) {
		if (_listViewResult.getSelectionModel().getSelectedIndex() != -1)
			_buyMapButton.setDisable(false);
	}

	private static String removeWord(String string) {
		String[] words = { "a", "for", "while", "to", "and", "then", "than", "with", "like", "as", "it", "by", "the",
				"of" };
		for (String remove : words) {
			if (string.contains(remove)) {
				String tempWord = remove + " ";
				string = string.replaceAll(tempWord, "");
				tempWord = " " + remove;
				string = string.replaceAll(tempWord, "");
			}
		}
		return string;
	}
    /**
     * initialize method override
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		/**
		 * make the radio buttons of the search area be in one group
		 */ 
		final ToggleGroup searchGroup = new ToggleGroup();
		_radioCityName.setToggleGroup(searchGroup);
		_radioPlaceOfInterestName.setToggleGroup(searchGroup);
		_radioDescription.setToggleGroup(searchGroup);
		String nameOfUser="avi";
		String typeOfUser="client";
		String headerText ="Hello: "+log.fisrtName; 
		_userNameLabel.setText(headerText);
		chat = log.chat;
		collections = new ArrayList<String>();
		if (chat != null) {
			setInListPurcessMaps();

		}
	}

	public void setInListPurcessMaps() {
		/**
		 * fill the maps the user buy
		 */ 
		purchaseMap.clear();
		paths = new ArrayList<String>();
		sendSQL = new ArrayList<Object>();
		sendSQL.clear();
		sendSQL.add("2");
		String res = "";

		sql = "SELECT purchases.collaction_id,city.name,map_collection.vertion,map.path FROM (((( purchases INNER JOIN map_mapcollection ON map_mapcollection.collection_id = purchases.collaction_id) INNER JOIN  map ON map_mapcollection.map_id = map.id) INNER JOIN city ON city.id = map.city_id) INNER JOIN map_collection ON map_collection.id = map_mapcollection.collection_id ) WHERE user_id='"+log.theUserID+"' GROUP BY (map_mapcollection.collection_id);";
		
		System.out.println(sql);
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m = chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("not buy anything");
		} else {
			/**
			 *  get city name, map collection and map version
			 * print only the city name and the num of the map collection
			 * save the path of the map in the arraylist 'paths'
			 * paths and names have the same index
			 */
			
			for (int i = 0; i < m.size(); i++) {
				
					res = "Map collection no. "+ m.get(i).get(0) + "# of the city "+ m.get(i).get(1) + "version "+ m.get(i).get(2);
				paths.add(m.get(i).get(3));
				collections.add(m.get(i).get(0));
				purchaseMap.add(res);
				res = new String();
			}

			System.out.println(paths);
			purchasesList.setItems(purchaseMap);
			if (!purchaseMap.isEmpty())
				purchasesList.setDisable(false);
		}
	}

}
