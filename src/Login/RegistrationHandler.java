package Login;

import java.io.IOException;
import java.net.URL;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.ArrayList;
>>>>>>> refs/remotes/origin/Doron
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegistrationHandler implements Initializable {

	private ObservableList<String> listMounth;
	private ObservableList<String> listYear;
	@FXML
	private ComboBox<String> _yearComboBox;

	@FXML
	private ComboBox<String> _monthComboBox;

	@FXML
	private ImageView _markQustionImage;
<<<<<<< HEAD
	
	@FXML
	private ImageView _cancelIcon;
	
     @FXML
     private ImageView _cvvImage;
	
     @FXML
	 private TextField _rePasswordFieldShow;
	 
    @FXML
    private TextField _passwordFieldShow;
=======
>>>>>>> refs/remotes/origin/Doron

	@FXML
	private ImageView _cvvImage;

	@FXML
	private TextField _rePasswordFieldShow;

	@FXML
	private TextField _passwordFieldShow;

<<<<<<< HEAD
    @FXML
    private PasswordField _rePasswordField;
   
    @FXML
    private Button _enterPaymentButton;
    
    @FXML
    private Button _toLoginButton;
    
    @FXML
    void clickToLoginButton(ActionEvent event)
    {
    	List<Window> open = Stage.getWindows().filtered(window -> window.isShowing());
    	
    	Stage s;
    	int lengh = open.size();
    	
    	for (int i = 0; i < lengh ; i++)
    	{
    		s = (Stage)open.get(0);
    		s.close();
		}
    	
    	new LoginMain().start(new Stage());
    }
    
    @FXML
    void seePassword()
    {
    	
    	_passwordFieldShow.setText(_passwordField.getText());
    	_passwordFieldShow.setVisible(true);
    	_passwordField.setVisible(false);
    }
=======
	@FXML
	private ImageView _passwordEye;
>>>>>>> refs/remotes/origin/Doron

	@FXML
	private ImageView _rePasswordEye;

	@FXML
	private PasswordField _passwordField;

<<<<<<< HEAD
    @FXML
    void clickRegister()
    {
    	//check for fields && payment
    	
    	if(_passwordField.getText().equals(_rePasswordField.getText()))
    	{
    		
    	}
    	
    	//if every thing is good 
    	try {
          	
  			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/Login/Registration_ConfirmRegistration.fxml"));
  			
  			Scene scene = new Scene( root );
  			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  			 Stage stage =new Stage();
  			 stage.setScene(scene);
  			 stage.showAndWait();
  			 
  		
          }
          catch (IOException e) {
              e.printStackTrace();
          }
    	
    }
    
    @FXML
    void cvvShow() 
    {
    	_cvvImage.setVisible(true);
    }
=======
	@FXML
	private PasswordField _rePasswordField;
>>>>>>> refs/remotes/origin/Doron

<<<<<<< HEAD
    @FXML
    void cvvUnshow()
    {
    	_cvvImage.setVisible(false);
    }
 
    @FXML
    void clickEnterPayment(ActionEvent event)
    {
    	  try {
         
  			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/Login/Registration_MethodsOfPayment.fxml"));
  			Scene scene = new Scene( root );
  			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  			 Stage stage =new Stage();
  			 stage.setScene(scene);
  			 stage.show();
=======
	@FXML
	private Button _enterPaymentButton;
>>>>>>> refs/remotes/origin/Doron

	@FXML
	private TextField _username;

<<<<<<< HEAD
    }
    
    @FXML
    void initializationMethodsOfPayment() 
    {
    	_yearComboBox.setItems(listYear);
	    	_monthComboBox.setItems(listMounth);
	    	
    }
  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		listMounth = FXCollections.observableArrayList(
				"01","02","03","04","05","06","07","08","09","10","11","12"); 
		listYear = FXCollections.observableArrayList(
				"2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030");	
		
=======
	@FXML
	private TextField _email;

	@FXML
	private TextField _reEmail;

	@FXML
	private TextField _phone;

	private ChatClient chat;
	private String sql;
	private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ArrayList<ArrayList<String>> m;
	private String table;

	@FXML
	void clickToLoginButton(ActionEvent event) {
		Stage stage = new Stage();
		stage.setScene(((Node) (event.getSource())).getScene());

		stage.close();

>>>>>>> refs/remotes/origin/Doron
	}

	@FXML
<<<<<<< HEAD
	void clickCancelIcon()
	{
		Stage stage = (Stage) _cancelIcon.getScene().getWindow();
	    // do what you have to do
		new LoginMain().start(new Stage());
		stage.close();
	}
	
=======
	void usernameChanged(KeyEvent event) {
		if (!_username.getText().isEmpty()) {
			_username.getStyleClass().clear();
			_username.getStyleClass().addAll("text-field", "text-input");
		}
	}

	@FXML
	void passwordChanged(KeyEvent event) {
		if (!_passwordField.getText().isEmpty()) {
			_passwordField.getStyleClass().clear();
			_passwordField.getStyleClass().addAll("text-field", "text-input");
			if (!_passwordField.getText().equals(_rePasswordField.getText())) {
				_rePasswordField.getStyleClass().add("error");
			} else {
				_rePasswordField.getStyleClass().clear();
				_rePasswordField.getStyleClass().addAll("text-field", "text-input");
			}
		}

	}

	@FXML
	void repasswrodChange(KeyEvent event) {
		if (!_rePasswordField.getText().isEmpty()) {
			_rePasswordField.getStyleClass().clear();
			_rePasswordField.getStyleClass().addAll("text-field", "text-input");
			if (!_passwordField.getText().equals(_rePasswordField.getText())) {
				_rePasswordField.getStyleClass().add("error");
			} else {
				_rePasswordField.getStyleClass().clear();
				_rePasswordField.getStyleClass().addAll("text-field", "text-input");
			}
		}
	}

	@FXML
	void emailChanged(KeyEvent event) {
		if (!_email.getText().isEmpty()) {
			_email.getStyleClass().clear();
			_email.getStyleClass().addAll("text-field", "text-input");
		}
		if (!_email.getText().equals(_reEmail.getText())) {
			_reEmail.getStyleClass().add("error");
		} else {
			_reEmail.getStyleClass().clear();
			_reEmail.getStyleClass().addAll("text-field", "text-input");
		}
	}

	@FXML
	void reemailChanged(KeyEvent event) {
		if (!_reEmail.getText().isEmpty()) {
			_reEmail.getStyleClass().clear();
			_reEmail.getStyleClass().addAll("text-field", "text-input");
		}
		if (!_email.getText().equals(_reEmail.getText())) {
			_reEmail.getStyleClass().add("error");
		} else {
			_reEmail.getStyleClass().clear();
			_reEmail.getStyleClass().addAll("text-field", "text-input");
		}
	}

	@FXML
	void phoneChanged(KeyEvent event) {
		if (!_phone.getText().isEmpty()) {
			_phone.getStyleClass().clear();
			_phone.getStyleClass().addAll("text-field", "text-input");
		}
	}

	@FXML
	void seePassword() {

		_passwordFieldShow.setText(_passwordField.getText());
		_passwordFieldShow.setVisible(true);
		_passwordField.setVisible(false);
	}

	@FXML
	void unSeePassword() {
		_passwordFieldShow.setVisible(false);
		_passwordField.setVisible(true);
	}

	@FXML
	void seeRePassword() {

		_rePasswordFieldShow.setText(_rePasswordField.getText());
		_rePasswordFieldShow.setVisible(true);
		_rePasswordField.setVisible(false);
	}

	@FXML
	void unSeeRePassword() {
		_rePasswordFieldShow.setVisible(false);
		_rePasswordField.setVisible(true);
	}

	@FXML
	void clickRegister() {
		boolean filedsEmpty = true, userFree = false, emailFree = false, canRegister=true;
		// check for fields && payment
		String userName = _username.getText();
		String fName = "av";
		String lName = "lac";
		String password = _passwordField.getText();
		String rePassword = _rePasswordField.getText();
		String email = _email.getText();
		String creditCard = "123";
		String reEmail = _reEmail.getText();
		String phone = _phone.getText();

		sendSQL.clear();
		// userName
		if (userName.isEmpty()) {
			filedsEmpty = true;
			_username.getStyleClass().add("error");
		} else {
			filedsEmpty = false;
			_username.getStyleClass().clear();
			_username.getStyleClass().addAll("text-field", "text-input");
		}

		// password
		if (password.isEmpty()) {
			filedsEmpty = true;
			_passwordField.getStyleClass().add("error");
		} else {
			filedsEmpty = false;
			_passwordField.getStyleClass().clear();
			_passwordField.getStyleClass().addAll("text-field", "text-input");
		}

		// rePassword
		if (rePassword.isEmpty()) {
			filedsEmpty = true;
			_rePasswordField.getStyleClass().add("error");
		} else {
			filedsEmpty = false;
			_rePasswordField.getStyleClass().clear();
			_rePasswordField.getStyleClass().addAll("text-field", "text-input");
		}

		// Email
		if (email.isEmpty()) {
			filedsEmpty = true;
			_email.getStyleClass().add("error");
		} else {
			filedsEmpty = false;
			_email.getStyleClass().clear();
			_email.getStyleClass().addAll("text-field", "text-input");
		}

		// reEmail
		if (reEmail.isEmpty()) {
			filedsEmpty = true;
			_reEmail.getStyleClass().add("error");
		} else {
			filedsEmpty = false;
			_reEmail.getStyleClass().clear();
			_reEmail.getStyleClass().addAll("text-field", "text-input");
		}

		// phone
		if (phone.isEmpty()) {
			filedsEmpty = true;
			_phone.getStyleClass().add("error");
		} else {
			filedsEmpty = false;
			_phone.getStyleClass().clear();
			_phone.getStyleClass().addAll("text-field", "text-input");
		}

		if (!filedsEmpty)
			if ((password.equals(rePassword)) && email.equals(reEmail)) {

				// check user name existence
				table = "users";
				sql = "SELECT user_name FROM " + table + " WHERE user_name = '" + userName + "';";
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
				if (m == null || m.isEmpty()) {
					System.out.println("user free");
					userFree = true;
				} else {
					System.out.println("user exist");
				}

				// check email existence
				table = "user_card";
				sendSQL.clear();
				sql = "SELECT user_id FROM " + table + " WHERE email = '" + email + "';";
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
				if (m == null || m.isEmpty()) {
					emailFree = true;
					System.out.println("email free");
				} else {
					System.out.println("email exist");
				}
			}

		// if every thing is good
		if (userFree && emailFree) {
			// create user in the table 'users'
			sendSQL.clear();
			sendSQL.add("3");
			table = "users";
			sql = "insert into " + table + " (user_name, first_name, last_name , password) " + "values ('" + userName
					+ "','" + fName + "','" + lName + "','" + password + "');";

			sendSQL.add(sql);

			chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				canRegister=false;
				e.printStackTrace();
			}
			
			//get the id of the new user
			String user_id = "";
			sendSQL.clear();
			sendSQL.add("2");
			sql = "SELECT id FROM " + table + " WHERE user_name = '" + userName + "';";
			sendSQL.add(sql);

			chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				canRegister=false;
				e.printStackTrace();
			}

			m = chat.getArray();
			if (m == null || m.isEmpty())
				canRegister=false;
			else
			for (int i = 0; i < m.size(); i++) {
				for (int j = 0; j < m.get(i).size(); j++) {
					user_id = m.get(i).get(j);
				}
			}

			
			//insert the email into the table 'user_card'
			
			sendSQL.clear();
			sendSQL.add("3");
			table = "user_card";
			sql = "insert into " + table + " (user_id, email,  phone, creditcard ) " + "values ('" + user_id
					+ "','" + email + "','" + phone + "','" + creditCard + "');";

			sendSQL.add(sql);
			chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				canRegister=false;
				e.printStackTrace();
			}
			
		}
		if (canRegister)

			try {
				FXMLLoader loader = new FXMLLoader();
				Pane root = (Pane) loader.load(getClass().getResource("/Login/ConfirmRegistration.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();

			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	@FXML
	void cvvShow() {
		_cvvImage.setVisible(true);
	}

	@FXML
	void cvvUnshow() {
		_cvvImage.setVisible(false);
	}

	@FXML
	void clickEnterPayment(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Pane root =  FXMLLoader.load(getClass().getResource("/Login/Methods of Payment.fxml"));
			visa s = loader.getController();
			
			int i =1;
			s.getting(i);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void initializationMethodsOfPayment() {
		_yearComboBox.setItems(listYear);
		_monthComboBox.setItems(listMounth);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listMounth = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
				"12");
		listYear = FXCollections.observableArrayList("2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026",
				"2027", "2028", "2029", "2030");
		try {
			chat = new ChatClient();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// return false;
		}
	}

>>>>>>> refs/remotes/origin/Doron
}
