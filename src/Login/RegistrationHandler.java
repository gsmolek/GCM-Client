package Login;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import managerWindow.aprroveHandler;

public class RegistrationHandler implements Initializable {

	 @FXML
	    private Label errorField;
	 
	@FXML
	private TextField _lastName;

	@FXML
	private TextField _firstName;

	@FXML
	private ImageView _markQustionImage;

	@FXML
	private ImageView _cvvImage;

	@FXML
	private TextField _rePasswordFieldShow;

	@FXML
	private TextField _passwordFieldShow;

	@FXML
	private ImageView _passwordEye;

	@FXML
	private ImageView _rePasswordEye;

	@FXML
	private PasswordField _passwordField;

	@FXML
	private PasswordField _rePasswordField;

	@FXML
	private Button _enterPaymentButton;

	@FXML
	private TextField _username;

	@FXML
	private TextField _email;

	@FXML
	private TextField _reEmail;

	@FXML
	private TextField _phone;

	@FXML
	private ImageView paymentImg;
	
	private LoginHandler log;

	protected static ChatClient chat = null;
	private String sql;
	private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ArrayList<ArrayList<String>> m;
	private String table;
	protected static visa s;
	private FXMLLoader loader;
	private Pane root;
	private Scene scene;
	private Stage stage;
	private Stage stage4;

	public void setRegistrationHandler( LoginHandler log) {
		this.log = log;
	}
	@FXML
	void clickToLoginButton(ActionEvent event) {
		Stage stage = new Stage();
		stage.setScene(((Node) (event.getSource())).getScene());
		stage.close();

	}

	@FXML
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
	void firstNameChanged(KeyEvent event) {
		if (!_firstName.getText().isEmpty()) {
			_firstName.getStyleClass().clear();
			_firstName.getStyleClass().addAll("text-field", "text-input");
		}
	}

	@FXML
	void lastNameChanged(KeyEvent event) {
		if (!_lastName.getText().isEmpty()) {
			_lastName.getStyleClass().clear();
			_lastName.getStyleClass().addAll("text-field", "text-input");
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
		boolean filedsEmpty = true, userFree = false, emailFree = false, canRegister = false;
		// check for fields && payment
		String userName = _username.getText();
		String fName = _firstName.getText();
		String lName = _lastName.getText();
		String password = _passwordField.getText();
		String rePassword = _rePasswordField.getText();
		String email = _email.getText();
		String creditCard = Integer.toString(s.getCard());
		String reEmail = _reEmail.getText();
		String phone = _phone.getText();
		int expirationM = s.getExM();
		int expirationY = s.getExY();
		String cvv = Integer.toString(s.getCvv());

		sendSQL.clear();

		// first name
		if (fName.isEmpty()) {
			filedsEmpty = true;
			_firstName.getStyleClass().add("error");
		} else {
			filedsEmpty = false;
			_firstName.getStyleClass().clear();
			_firstName.getStyleClass().addAll("text-field", "text-input");
		}

		// last name
		if (lName.isEmpty()) {
			filedsEmpty = true;
			_lastName.getStyleClass().add("error");
		} else {
			filedsEmpty = false;
			_lastName.getStyleClass().clear();
			_lastName.getStyleClass().addAll("text-field", "text-input");
		}

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

		// cardNumber
		if (creditCard.equals("0")) {
			filedsEmpty = true;
		} else {
			filedsEmpty = false;
		}

		// Expiration month
		if (expirationM == 0)
			filedsEmpty = true;
		else
			filedsEmpty = false;

		// Expiration year
		if (expirationY == 0)
			filedsEmpty = true;
		else
			filedsEmpty = false;

		// cvv
		if (cvv.equals("0")) {
			filedsEmpty = true;
			errorField.setVisible(true);
		} else {
			filedsEmpty = false;
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
				canRegister = false;
				e.printStackTrace();
			}
			canRegister = true;
			// get the id of the new user
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
				canRegister = false;
				e.printStackTrace();
			}

			m = chat.getArray();
			if (m == null || m.isEmpty())
				canRegister = false;
			else
				for (int i = 0; i < m.size(); i++) {
					for (int j = 0; j < m.get(i).size(); j++) {
						user_id = m.get(i).get(j);
					}
				}

			// insert the email into the table 'user_card'

			sendSQL.clear();
			sendSQL.add("3");
			table = "user_card";
			sql = "insert into " + table + " (user_id, email,  phone, creditcard,expirationM,expirationY,cvv ) " + "values ('" + user_id + "','"
					+ email + "','" + phone + "','" + creditCard + "','"+expirationM+"','"+expirationY+"','"+cvv+"');";

			sendSQL.add(sql);
			chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				canRegister = false;
				e.printStackTrace();
			}

		}
		if (canRegister) //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// success register
			try {
				loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/Login/Registration_ConfirmRegistration.fxml"));
				root = loader.load();

				scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

				successHandler control = loader.getController();
				control.setRegHandler(this);

				stage = new Stage();
				stage.setScene(scene);
				stage.show();

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	// enter the credit card pyment
	@FXML
	void clickEnterPayment(ActionEvent event) {
		errorField.setVisible(false);
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Login/Registration_MethodsOfPayment.fxml"));
			root = loader.load();

			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			paymentHandler control = loader.getController();
			control.setRegistrationHandler(this);

			stage = new Stage();
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    @FXML
    void goBackPlease(MouseEvent event) {
    	Stage stage2 = (Stage) _enterPaymentButton.getScene().getWindow();
		stage2.close();
    	log.showThisWindow();
    	
    }
    
	public void closeWindow() {
		Stage stage2 = (Stage) _enterPaymentButton.getScene().getWindow();
		stage2.close();
		log.showThisWindow();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		s = new visa();
		chat = log.chat;

	}

}
