package employeeWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;



import Login.LoginHandler;
import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EmployeeHandler implements Initializable{
	
	private LoginHandler log;
	private static final ObservableList<String> searchResult = FXCollections.observableArrayList();
	protected static ChatClient chat = null;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	private int searchType = 1;
	private Pane root;
	private FXMLLoader loader;
	private Scene scene;
	private Stage stage, stage2;
	private ArrayList<Integer> idResultsFromQuery;
	private ArrayList<String> collections_id;
	protected static  ArrayList<String> paths;
	protected static int idOfMap;
	
	protected static String idOfSelectedCollection;
	protected static String versionOfTheCollection;
	protected static int mapIdForShow;
	private ArrayList<String> mapID;
	private ArrayList<String> versionList;
	protected static String versionNumber;
	
	
	public void setLoginHandler(LoginHandler log) {
		this.log = log;
	}
	
    @FXML
    private Button _searchButton;

    @FXML
    private Button showMapData;

    @FXML
    private Button _addTourButton;

    @FXML
    private RadioButton _radioCityName;

    @FXML
    private Button _editTourButton;

    @FXML
    private RadioButton _radioDescription;

    @FXML
    private ListView<String> _listViewResult;

    @FXML
    private Label _userNameLabel;

    @FXML
    private Button _addPlaceButton;

    @FXML
    private Button _addNewMapButton;

    @FXML
    private Button _downloadMapButton;

    @FXML
    private Button _forQuestionButton;

    @FXML
    private Button _editPlaceButton;

    @FXML
    private TextArea _searchTextFiled;

    @FXML
    private Label resultLable;
    
    @FXML
    private RadioButton _radioPlaceOfInterestName;

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
	void checkIfEmpty(KeyEvent event) {
		if (!_searchTextFiled.getText().isEmpty()) {
			_searchButton.setDisable(false);
		} else
			_searchButton.setDisable(true);
	}
    
  	@FXML
	void searchForMap(ActionEvent event) {
		// resultLable.setText("");
		String text = _searchTextFiled.getText();
		mapID = new ArrayList<String>();
		collections_id = new ArrayList<String>();
		String resultSearch = "";
		searchResult.clear();
		sendSQL.clear();
		String res = "", resultOfMapList = null;
		idResultsFromQuery = new ArrayList<Integer>();
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

			// get number of maps
			sql = "SELECT count(map.id) FROM (((site INNER JOIN map_site ON site.id = map_site.site_id) INNER JOIN map ON map_site.map_id = map.id) INNER JOIN map_collection ON map_collection.id = map.id_collaction) WHERE (site.description LIKE '%"
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

				// get the number of place of interest of the city
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

					// get the number of tours in the city
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

						// fill the list view with the maps list
						sql = "SELECT map.id_collaction,city.name,map.description,map.id FROM ((((site INNER JOIN map_site ON site.id = map_site.site_id) INNER JOIN map ON map_site.map_id = map.id) INNER JOIN city ON map.city_id = city.id) INNER JOIN map_collection ON map.id_collaction = map_collection.id ) WHERE (site.description LIKE '%"
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
							// get the maps description of the requested city and insert into the listView
							for (int i = 0; i < m.size(); i++) {
								mapID.add(m.get(i).get(1));
								resultOfMapList = new String();
								resultOfMapList += m.get(i).get(2) + " ";
								idResultsFromQuery.add(Integer.valueOf(m.get(i).get(3)));
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
		}
		if (!searchResult.isEmpty()) {
			_listViewResult.setDisable(false);
			_listViewResult.setItems(searchResult);

		} else {
			_listViewResult.setDisable(true);
			showMapData.setDisable(true);
			_downloadMapButton.setDisable(true);
		}
		// resultLable.setText(res);

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
    
    
    
    
	@FXML
	void checkIfChoose(MouseEvent event) {
		
		if (_listViewResult.getSelectionModel().getSelectedIndex() != -1) {
			showMapData.setDisable(false);
		idOfMap = _listViewResult.getSelectionModel().getSelectedIndex();
		}
	}
    
    
	
	public int returnMapId() {
		return mapIdForShow;
	}
	
  
    public void openThisWindow() {
		stage2.show();
	}

	@FXML
	void clickBuyMapButton(ActionEvent event) {
		/////////////////////////////////////////////////////////////////////////////////////////////
		idOfSelectedCollection = collections_id.get (_listViewResult.getSelectionModel().getSelectedIndex() );
		mapIdForShow = idResultsFromQuery.get(_listViewResult.getSelectionModel().getSelectedIndex());
		versionNumber = versionList.get(_listViewResult.getSelectionModel().getSelectedIndex());
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/employeeWindow/Manager_EditMapWindow.fxml"));
			root = loader.load();

			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			mapDataHandlerEmployee control = loader.getController();
			control.setEmployeeHandler(this);
			// control.setClientWindowHandler(this);
			// control.getMapID(mapIDForBuy);

			stage = new Stage();
			stage.setScene(scene);
			stage.show();
			stage2 = (Stage) _searchButton.getScene().getWindow();
			stage2.hide();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		final ToggleGroup searchGroup = new ToggleGroup();
		_radioCityName.setToggleGroup(searchGroup);
		_radioPlaceOfInterestName.setToggleGroup(searchGroup);
		_radioDescription.setToggleGroup(searchGroup);
		sendSQL = new ArrayList<Object>();
		paths = new ArrayList<String>();
		chat = log.chat;
		
		_userNameLabel.setText("Hello: " + log.fisrtName + " [Content Manager] ");
		
		
	}

}
