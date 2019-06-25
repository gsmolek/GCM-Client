package managerWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ManagerClientInfoHandler implements Initializable{

	@FXML
	private ListView<String> _resList = new ListView<String>();
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
    private Label _notFindLabel;
    
    @FXML
    private TextField _emailField;

    @FXML
    private Button _updateUserInformationButton;

    @FXML
    private TextField _passwordField;

    @FXML
    private Button _forQuestionButton;

    @FXML
    private ImageView _backBattuon;

    @FXML
    private Pane _paneUserInfo;
    
    @FXML
    private Pane _paneUserInfoPruchase;
    

    @FXML
    private Button _userPruchaseDataButton;

    private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ChatClient chat = null;
	private ArrayList<ArrayList<String>> m;
    
	ObservableList<String> listToShow = FXCollections.observableArrayList();
	
    @FXML
    void clickSearchUser(ActionEvent event)
    {
    	sendSQL.clear();
		String sql=null;
		String textToSearch = _searchTextField.getText();
		
		sql = "SELECT first_name,last_name,user_name,password,email,phone,creditcard,expirationM,expirationY,cvv \r\n" + 
				"FROM gcm.users,gcm.user_card \r\n" + 
				"Where user_id=Id AND (email=\""+ textToSearch +"\" OR user_name=\"" + textToSearch + "\");";
		
		sendSQL.add("2");
		sendSQL.add(sql);
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
			_notFindLabel.setVisible(true);
			
			_paneUserInfo.setVisible(false);
	    	_paneUserInfoPruchase.setVisible(false);
			
			_userInformationDataButton.setVisible(false);
			_userPruchaseDataButton.setVisible(false);
			
			
			_firstNameField.setText("");
			_lastNameField.setText("");
			_userNameField.setText("");
			_passwordField.setText("");
			
			_emailField.setText("");
			_phoneField.setText("");
			_cardNumberField.setText("");
			_monthComboBox.setValue("mm");
			_yearComboBox.setValue("yyyy");
			_cVVField.setText("");
		}
		else 
		{
			_notFindLabel.setVisible(false);
			
			_userInformationDataButton.setVisible(true);
			_userPruchaseDataButton.setVisible(true);
			
			for (ArrayList<String> arrayList : m)
			{
				_firstNameField.setText(arrayList.get(0));
				_lastNameField.setText(arrayList.get(1));
				_userNameField.setText(arrayList.get(2));
				_passwordField.setText(arrayList.get(3));
				
				_emailField.setText(arrayList.get(4));
				_phoneField.setText(arrayList.get(5));
				_cardNumberField.setText(arrayList.get(6));
				_monthComboBox.setValue(arrayList.get(7));
				_yearComboBox.setValue(arrayList.get(8));
				_cVVField.setText(arrayList.get(9));
			}
			System.out.println("success");
		}
			
			
		//sql = "SELECT * FROM " + table + " WHERE email = '" + textToSearch + "';";
			
		
		
		///////////////////////////////////////////////////////
		//////////////////////////////////////////////////////

		
		
		/*
		 * byte[][] result = chat.returnByteArray(); String s = new String(result[0]);
		 * System.out.println("main: "+s); s = new String(result[1]);
		 * System.out.println("main: "+s);
		 */

    }
    
    @FXML
    void clickUserInformationDataButton(ActionEvent event)
    {
    	_paneUserInfo.setVisible(true);
    	_paneUserInfoPruchase.setVisible(false);
    }
    
    @FXML
    void clickUserPruchaseDataButton(ActionEvent event)
    {
    	_paneUserInfo.setVisible(false);
    	_paneUserInfoPruchase.setVisible(true);
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
