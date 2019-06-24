package managerWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ManagerClientInfoHandler implements Initializable{

    @FXML
    private ComboBox<String> _yearComboBox;

    @FXML
    private ComboBox<String> _monthComboBox;
    
    @FXML
    private Button _userInformationDataButton;

    @FXML
    private TextField _userNameField;

    @FXML
    private TextField _firstNameField;

    @FXML
    private Pane _paneManageClient;

    @FXML
    private TextField _cardNumberField;

    @FXML
    private TextField _lastNameField;

    @FXML
    private Button _searchUserButton;

    @FXML
    private TextField _searchTextField;

    @FXML
    private Label _userNameLabel;

    @FXML
    private TextField _phoneField;

    @FXML
    private PasswordField _cVVField;

    @FXML
    private RadioButton _searchByUserName;

    @FXML
    private TextField _emailField;

    @FXML
    private Button _updateUserInformationButton;

    @FXML
    private PasswordField _passwordField;

    @FXML
    private Button _forQuestionButton;

    @FXML
    private ImageView _backBattuon;

    @FXML
    private Button _userPruchaseDataButton;

    @FXML
    private RadioButton _searchByEmail;

    @FXML
    private ListView<String> _resList;
    
    private static final int SEARCH_NOT_CHOOSE = -1;
    private static final int SEARCH_BY_USER_NAME = 0;
    private static final int SEARCH_BY_EMAIL = 1;
    
    private int chooseHowToSearch = SEARCH_BY_USER_NAME;
    
    private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ChatClient chat = null;
	private ArrayList<ArrayList<String>> m;
    
    @FXML
    void clickSearchByUserName(ActionEvent event)
    {
    	if(_searchByUserName.isSelected())
    	{
    		_searchByEmail.setSelected(false);
    		chooseHowToSearch = SEARCH_BY_USER_NAME;
    	}
    	else
    	{
    		chooseHowToSearch = SEARCH_NOT_CHOOSE;
    	}
    }

    @FXML
    void clickSearchByEmail(ActionEvent event) 
    {
    	if(_searchByEmail.isSelected())
    	{
    		_searchByUserName.setSelected(false);
    		chooseHowToSearch = SEARCH_BY_EMAIL;
    	}
    	else
    	{
    		chooseHowToSearch = SEARCH_NOT_CHOOSE;
    	}
    }

    @FXML
    void clickSearchUser(ActionEvent event)
    {
    	sendSQL.clear();
		String sql=null;
		String table = "users";
		
		String textToSearch = _searchTextField.getText();
		
		switch (chooseHowToSearch) {
		case SEARCH_BY_USER_NAME:
			sql = "SELECT * FROM " + table + " WHERE user_name = '" + textToSearch + "';";
			break;
		case SEARCH_BY_EMAIL:
			sql = "SELECT * FROM " + table + " WHERE email = '" + textToSearch + "';";
			break;
		default:
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Search Error");
			alert.setContentText("Not Choose How to Search");
			alert.showAndWait();
			break;
		}
		
		if(chooseHowToSearch!=SEARCH_NOT_CHOOSE)
		{
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
		
		if (m == null || m.isEmpty())
		{
			_resList.setAccessibleText("Empty");
		}
		else 
		{
			System.out.println("success");
		}
		/*
		 * byte[][] result = chat.returnByteArray(); String s = new String(result[0]);
		 * System.out.println("main: "+s); s = new String(result[1]);
		 * System.out.println("main: "+s);
		 */
		}

    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		ObservableList<String> listMounth = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
				"12");
		ObservableList<String> listYear = FXCollections.observableArrayList("2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026",
				"2027", "2028", "2029", "2030");
		_monthComboBox.setItems(listMounth);
		_yearComboBox.setItems(listYear);
		
		m = null;
		try {
			chat = new ChatClient();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// return false;
		}
		
	}

}
