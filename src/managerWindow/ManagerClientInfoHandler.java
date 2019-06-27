package managerWindow;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Label _userNamePurchaseLabel;

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

    @FXML
    private Button _viewPurchaseHistoryButton;
    
    @FXML
    private TableView<PruchaseRowView> _resTable;

    @FXML
    private DatePicker _toDate;
    
    @FXML
    private DatePicker _fromDate;
    
    @FXML
    private TableColumn<PruchaseRowView, String> _mapCollectionColumn;
    
    @FXML
    private TableColumn<PruchaseRowView, String> _versionColumn;
    
    @FXML
    private TableColumn<PruchaseRowView, String> _typeOfPruchaseColumn;

    @FXML
    private TableColumn<PruchaseRowView, String> _periodColumn;
    
    
    private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ChatClient chat = null;
	private ArrayList<ArrayList<String>> m;
    
	ObservableList<String> listToShow = FXCollections.observableArrayList();
	
	private final String GET_DATA = "2";
	private final String UPDATE_DELETE_INSERT_DATA = "3";
	private final String GET_IMAGE = "5";
	
	private static String _idClient ;
	
    @FXML
    void clickSearchUser(ActionEvent event)
    {
    	sendSQL.clear();
		String sql=null;
		String textToSearch = _searchTextField.getText();
		
		sql = "SELECT first_name,last_name,user_name,password,email,phone,creditcard,expirationM,expirationY,cvv,Id \r\n" + 
				"FROM gcm.users,gcm.user_card \r\n" + 
				"Where user_id=Id AND (email=\""+ textToSearch +"\" OR user_name=\"" + textToSearch + "\");";
		
		sendSQL.add(GET_DATA);
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) 
		{
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
			
			/*
			_firstNameField.setText("");
			_lastNameField.setText("");
			_userNameField.setText("");
			_passwordField.setText("");
			
			_emailField.setText("");
			_phoneField.setText("");
			_cardNumberField.setText("");
			_monthComboBox.setValue("mm");
			_yearComboBox.setValue("yyyy");
			_cVVField.setText("");*/
		}
		else 
		{
			_notFindLabel.setVisible(false);
			
			_userInformationDataButton.setVisible(true);
			_userPruchaseDataButton.setVisible(true);
			
			
			ArrayList<String> arrayList = m.get(0);
			
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
			
			_idClient = arrayList.get(10);
			
			
			System.out.println("success");
		}

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
    	
    	_userNamePurchaseLabel.setText(_userNameField.getText());
    }
   
    @FXML
    void clickUpdateUserInformationButton(ActionEvent event)
    {
    	sendSQL.clear();
    	String sql = null; 
    	
    	sql="UPDATE user_card,users\r\n" + 
    			"SET\r\n" + 
    			"first_name=\" "+ _firstNameField.getText()
    			+"\",last_name=\""+ _lastNameField.getText() 
    			+"\",user_name=\""+ _userNameField.getText()
    			+"\",password=\""+ _passwordField.getText()
    			+"\",email = \""+ _emailField.getText()
    			+"\",phone = \""+ _phoneField.getText()
    			+"\",creditcard=\""+ _cardNumberField.getText()
    			+"\", expirationM=\""+ _monthComboBox.getValue()
    			+"\",expirationY=\""+ _yearComboBox.getValue()
    			+"\",cvv=\""+ _cVVField.getText() +"\"\r\n" + 
    			"WHERE (user_id = \""+ _idClient +"\" AND user_id=Id);";
    	
    	sendSQL.add(UPDATE_DELETE_INSERT_DATA);
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		m = chat.getArray();
		if(m.size()>0)
		{
			System.out.println(m.get(0).get(0));
		}
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Update");
		alert.setContentText("You just update " + _userNameField.getText() +" data");
		alert.showAndWait();
    	
    }

    @FXML
    void clickViewPurchaseHistoryButton()
    {
    
    	
    	sendSQL.clear();
		String sql=null;
	
		sql = "SELECT version,collaction_id,type_of_purchases,date_end\r\n" + 
				"FROM gcm.purchases ,map_collection\r\n" + 
				"Where user_id="+ _idClient +" AND "
						+ "collaction_id=map_collection.Id AND"
						+ " date_buy >= \""+_fromDate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE)+"\" AND date_buy <= \""+_toDate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE) +"\";";
		
		System.out.println(sql);
		
		sendSQL.add(GET_DATA);
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		m = chat.getArray();
		
		if (m == null || m.isEmpty())
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Find nothing");
			alert.setContentText("We didt find a thing!");
			alert.showAndWait();
		}
		else 
		{
			PruchaseRowView pruchaseRowView;
			
			for (ArrayList<String> arrayList : m) 
			{
				pruchaseRowView = new PruchaseRowView (arrayList.get(0),arrayList.get(1),arrayList.get(2),arrayList.get(3));
				_resTable.getItems().addAll(pruchaseRowView);
			}
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
		
		_mapCollectionColumn.setCellValueFactory(new PropertyValueFactory<>("mapCollection"));
		_versionColumn.setCellValueFactory(new PropertyValueFactory<>("version"));
		_typeOfPruchaseColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfPruchase"));
		_periodColumn.setCellValueFactory(new PropertyValueFactory<>("Period"));
		
		_periodColumn.setText("Until");
	}

	public class PruchaseRowView {
	   
		private String version;
	    private String mapCollection;
	    private String typeOfPruchase;
	    private String period;
	    
	    
		public PruchaseRowView(String version, String mapCollection, String typeOfPruchase, String periodEnd) {
			super();
			this.version = version;
			this.mapCollection = mapCollection;
			this.typeOfPruchase = typeOfPruchase;
			this.period = periodEnd;
			
		}
		
		
		/**
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}

		/**
		 * @return the mapCollection
		 */
		public String getMapCollection() {
			return mapCollection;
		}

		/**
		 * @return the typeOfPruchase
		 */
		public String getTypeOfPruchase() {
			return typeOfPruchase;
		}

		/**
		 * @return the period
		 */
		public String getPeriod() {
			return period;
		}

		/**
		 * @param version the version to set
		 */
		public void setVersion(String version) {
			this.version = version;
		}

		/**
		 * @param mapCollection the mapCollection to set
		 */
		public void setMapCollection(String mapCollection) {
			this.mapCollection = mapCollection;
		}

		/**
		 * @param typeOfPruchase the typeOfPruchase to set
		 */
		public void setTypeOfPruchase(String typeOfPruchase) {
			this.typeOfPruchase = typeOfPruchase;
		}

		/**
		 * @param period the period to set
		 */
		public void setPeriod(String period) {
			this.period = period;
		}

		

	
	}
}
