package Login;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginHandler implements Initializable {

	@FXML
	private Label resultLable;
	
	@FXML
	private Button _searchButton;

	@FXML
	private RadioButton _radioCityName;

	@FXML
	private RadioButton _radioDescription;

	@FXML
	private ImageView _xIcon;

	@FXML
	private ListView<String> _listViewResult;

	@FXML
	private Hyperlink _hyperForgot;

	@FXML
	private Button _forQuestionButton;

	@FXML
	private Label _errorMessageLabel;

	@FXML
	private Hyperlink _hyperSignUp;

	@FXML
	private TextField _userNameFiled;

	@FXML
	private TextArea _searchTextFiled;

	@FXML
	private PasswordField _passwordFiled;

	@FXML
	private RadioButton _radioPlaceOfInterestName;

	private static final ObservableList<String> searchResult = FXCollections.observableArrayList();
	private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ArrayList<String> mapIDCollection;
	public static ChatClient chat = null;
	private ArrayList<ArrayList<String>> m;
	private FXMLLoader loader;
	private Pane root;
	private Scene scene;
	private Stage stage, stage2;
	public static String fisrtName;
	public static String theUserID;
	private int port;
	private String ip;
	private String sql;
	private int searchType = 1;
	

	public void showThisWindow() {
		stage2.show();
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

			// get number of maps
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
		}
		if (!searchResult.isEmpty()) {
			_listViewResult.setDisable(false);
			_listViewResult.setItems(searchResult);

		} else
			_listViewResult.setDisable(true);
		resultLable.setText(res);

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
	void clickHyperForgot(ActionEvent event)
	{
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/Login/Login_ForgotPassword.fxml"));
			AnchorPane root = loader.load();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			ForgotPasswordController control =loader.getController();
			control.setHandler(this,stage);
			
			stage.setScene(scene);
			stage.show();
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
		}
    
	}
	
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
	void clickBuyMapCollectionBeforeRegistration(ActionEvent event) {

	}

	@FXML
	void logInClick(ActionEvent event) throws IOException {
		sendSQL.clear();
		String sql;
		
		String username = _userNameFiled.getText();
		String password = _passwordFiled.getText();
		sql = "SELECT first_name,permission,login,id FROM users WHERE user_name = '" + username + "' AND password = '"
				+ password + "';";
		sendSQL.add("2");
		sendSQL.add(sql);

		///////////////////////////////////////////////////////
		//////////////////////////////////////////////////////

		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m = chat.getArray();
		if (m == null || m.isEmpty()) {
			_errorMessageLabel.setText("Username or password is incorrect");
			_errorMessageLabel.setVisible(true);
		} else {

			String permission;
			fisrtName = m.get(0).get(0);
			permission = m.get(0).get(1);
			theUserID =  m.get(0).get(3);
			System.out.println("login: "+theUserID);
			loader = new FXMLLoader();
			switch (permission) {
			// client user window
			case "1":
				loader.setLocation(getClass().getResource("/clientWindow/clientWindow.fxml"));
				root = loader.load();
				clientWindow.ClientWindowHandler controlClient = loader.getController();
				controlClient.setLoginHandler(this);
				break;
			// content manager window
			case "2":
				loader.setLocation(getClass().getResource("/managerWindow/ManagerContent_MainWindow.fxml"));
				root = loader.load();
				managerWindow.ManagerHandler controlContentManager = loader.getController();
				controlContentManager.setLoginHandler(this);
				break;
			// company manger window
			case "3":
				loader.setLocation(getClass().getResource("/managerWindow/ManagerCompany_MainWindow.fxml"));
				root = loader.load();
				managerWindow.ManagerCompanyHandler controlCompanyManager = loader.getController();
				controlCompanyManager.setLoginHandler(this);
				
			break;	
			// employee
			case "4":
				loader.setLocation(getClass().getResource("/employeeWindow/EmployeeWindow.fxml"));
				root = loader.load();
				employeeWindow.EmployeeHandler controlEmployee = loader.getController();
				controlEmployee.setLoginHandler(this);
				break;
			}

			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientWindow/application.css").toExternalForm());

			stage = new Stage();
			stage.setScene(scene);
			stage.show();

			stage2 = (Stage) _searchButton.getScene().getWindow();
			stage2.hide();

			System.out.println("success");
		}
		/*
		 * byte[][] result = chat.returnByteArray(); String s = new String(result[0]);
		 * System.out.println("main: "+s); s = new String(result[1]);
		 * System.out.println("main: "+s);
		 */
	}

	@FXML
	void signUpClick(ActionEvent event) {

		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Login/RegistrationWindow.fxml"));
			root = loader.load();

			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			RegistrationHandler control = loader.getController();
			control.setRegistrationHandler(this);

			stage = new Stage();
			stage.setScene(scene);
			stage.show();

			stage2 = (Stage) _searchButton.getScene().getWindow();
			stage2.hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		m = null;
		final ToggleGroup searchGroup = new ToggleGroup();
		_radioCityName.setToggleGroup(searchGroup);
		_radioPlaceOfInterestName.setToggleGroup(searchGroup);
		_radioDescription.setToggleGroup(searchGroup);
		try {
			port=Integer.valueOf(IpConfigurationController.getPort());
			ip=IpConfigurationController.getIp();
			chat = new ChatClient(ip,port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// return false;
		}
		// TODO Auto-generated method stub

	}
}
