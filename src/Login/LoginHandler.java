package Login;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginHandler implements Initializable {

	@FXML
	private Button _searchButton;

	@FXML
	private RadioButton _radioCityName;

	@FXML
	private RadioButton _radioDescription;

	@FXML
	private ImageView _xIcon;

	@FXML
	private ListView<?> _listViewResult;

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
	

	private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ChatClient chat = null;
	private ArrayList<ArrayList<String>> m;

	
	
	
	@FXML
	void radioCitySelect(ActionEvent event) {
		if (_radioCityName.isSelected()) {
			_radioPlaceOfInterestName.setSelected(false);
			_radioDescription.setSelected(false);
		}
	}

	@FXML
	void radioPlaceOfInterestName(ActionEvent event) {
		if (_radioPlaceOfInterestName.isSelected()) {
			_radioCityName.setSelected(false);
			_radioDescription.setSelected(false);
		}
	}

	@FXML
	void radioDescription(ActionEvent event) {
		if (_radioDescription.isSelected()) {
			_radioPlaceOfInterestName.setSelected(false);
			_radioCityName.setSelected(false);
		}
	}

	@FXML
	void logInClick(ActionEvent event) {
		sendSQL.clear();
		String sql;
		String table = "users";
		String username = _userNameFiled.getText();
		String password = _passwordFiled.getText();
		sql = "SELECT * FROM " + table + " WHERE user_name = '" + username + "' AND password = '" + password + "';";
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

			FXMLLoader loader = new FXMLLoader();
			Pane root = (Pane) loader.load(getClass().getResource("/Login/RegistrationWindow.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

			// Hide this current window (if this is what you want)
			((Node) (event.getSource())).getScene().getWindow().hide();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    @FXML
    void clickForgotPassword(ActionEvent event) {
    	try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Login/Login_ForgotPassword.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
		}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		m = null;
		try {
			chat = new ChatClient();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// return false;
		}
		// TODO Auto-generated method stub

	}
}
