package managerWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import clientWindow.typeOfpurchaseHandller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ManagerHandler implements Initializable {

	private ArrayList<String> mapID;
	protected static ChatClient chat = null;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<Integer> idResultsFromQuery;
	private ArrayList<ArrayList<String>> m;
	private Stage stage2;
	private int searchType = 1;
	protected static int mapIdForShow;
	private static final ObservableList<String> searchResult = FXCollections.observableArrayList();
	private Pane root;
	private FXMLLoader loader;
	private Scene scene;
	private Stage stage;
	
	@FXML
    private Label numOfRequested;

	@FXML
	private RadioButton _radioManageClient;

	@FXML
	private Pane _paneArchive;

	@FXML
	private Button _searchButton;

	@FXML
	private Button _buyMapButton;

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
	private RadioButton _radioArchiveView;

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

	public int returnMapId() {
		return mapIdForShow;
	}

	@FXML
	void clickBuyMapButton(ActionEvent event) {
		/////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println(mapIdForShow);
		mapIdForShow = idResultsFromQuery.get(_listViewResult.getSelectionModel().getSelectedIndex());
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/managerWindow/Manager_EditMapWindow.fxml"));
			root = loader.load();

			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			mapDataHandler control = loader.getController();
			control.setManagerHandler(this);
			// control.setClientWindowHandler(this);
			// control.getMapID(mapIDForBuy);

			stage = new Stage();
			stage.setScene(scene);
			stage.show();
			stage2 = (Stage) _buyMapButton.getScene().getWindow();
			stage2.hide();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openThisWindow() {
		stage2.show();
		setNumOfrequest();
	}

	@FXML
	void openManagerContent_SetAMapCollectionRateWindow(ActionEvent event) {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/managerWindow/ManagerContent_SetAMapCollectionRate.fxml"));
			root = loader.load();

			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			setRateHandler control = loader.getController();
			control.setManagerHandler(this);
			// control.setClientWindowHandler(this);
			// control.getMapID(mapIDForBuy);

			stage = new Stage();
			stage.setScene(scene);
			stage.show();

			stage2 = (Stage) _buyMapButton.getScene().getWindow();
			stage2.hide();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void gotoApproveWindow(ActionEvent event) {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/managerWindow/ManagerContent_ApproveVersion.fxml"));
			root = loader.load();

			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			aprroveHandler control = loader.getController();
			control.setManagerHandler(this);
			// control.setClientWindowHandler(this);
			// control.getMapID(mapIDForBuy);

			stage = new Stage();
			stage.setScene(scene);
			stage.show();

			stage2 = (Stage) _buyMapButton.getScene().getWindow();
			stage2.hide();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				sql = "SELECT count(map_site.site_id) FROM ((city INNER JOIN map ON city.id = map.city_id) INNER JOIN map_site ON map_site.map_id = map.id) WHERE city.name = '"
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
				} else {
					res += "number of place of intrest is: " + m.get(0).get(0) + " \n";

					sendSQL.clear();
					sendSQL.add("2");

					// get the number of tours in the city
					sql = "SELECT count(site_tour.tour_id) as first_count FROM (((city INNER JOIN map ON city.id=map.city_id) INNER JOIN map_site ON map_site.map_id = map.id) INNER JOIN site_tour ON map_site.site_id = site_tour.site_id) WHERE city.name = '"
							+ text + "' GROUP BY site_tour.tour_id ;";
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
						sql = "SELECT map.id_collaction, map.description, map.id FROM ((city INNER JOIN map ON city.id = map.city_id) INNER JOIN map_collection ON map.id_collaction = map_collection.id) WHERE city.name='"
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
								mapID.add(m.get(i).get(0));
								resultOfMapList = new String();
								resultOfMapList += m.get(i).get(1) + " ";
								idResultsFromQuery.add(Integer.valueOf(m.get(i).get(2)));
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
			sql = "SELECT count(map.id) FROM (((site INNER JOIN map_site ON site.id = map_site.site_id) INNER JOIN map ON map_site.map_id = map.id) INNER JOIN map_collection ON map_collection.id = map.id_collaction) WHERE site.name = '"
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
						sql = "SELECT map.id_collaction,city.name,map.description,map.id FROM ((((site INNER JOIN map_site ON site.id = map_site.site_id) INNER JOIN map ON map_site.map_id = map.id) INNER JOIN city ON map.city_id = city.id) INNER JOIN map_collection ON map.id_collaction = map_collection.id ) WHERE site.name ='"
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
			_buyMapButton.setDisable(true);
			_downloadMapButton.setDisable(true);
		}
		// resultLable.setText(res);

	}

	@FXML
	void checkIfChoose(MouseEvent event) {
		if (_listViewResult.getSelectionModel().getSelectedIndex() != -1) {
			_buyMapButton.setDisable(false);
			_downloadMapButton.setDisable(false);
		}

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

	private void setNumOfrequest() {
		//numOfRequested
		String sum="0";
		sql = "SELECT COUNT(*) FROM price_for_approve;";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}m = chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("no result");
		} else {
			sum = m.get(0).get(0);
		}
		numOfRequested.setText(sum);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		sendSQL = new ArrayList<Object>();
		final ToggleGroup searchGroup = new ToggleGroup();
		_radioCityName.setToggleGroup(searchGroup);
		_radioPlaceOfInterestName.setToggleGroup(searchGroup);
		_radioDescription.setToggleGroup(searchGroup);

		try {
			chat = new ChatClient();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// return false;
		}
		setNumOfrequest();
	}

	@FXML
	void clickAddPlace(ActionEvent event) {

	}

	@FXML
	void clickAddTour(ActionEvent event) {

	}

	@FXML
	void selectArchiveView(ActionEvent event) {

	}

	@FXML
	void selectManageClientView(ActionEvent event) {

	}
}
