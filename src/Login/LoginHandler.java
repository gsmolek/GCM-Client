package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LoginHandler {

	LoginController _logInController;
	
    @FXML
    private Button _searchButton;

    @FXML
    private Button _forQuestionButton;

    @FXML
    private RadioButton _radioCityName;

    @FXML
    private Hyperlink _hyperSignUp;

    @FXML
    private RadioButton _radioDescription;

    @FXML
    private TextField _userNameFiled;

    @FXML
    private ListView<?> _listViewResult;

    @FXML
    private TextArea _searchTextFiled;

    @FXML
    private PasswordField _passwordFiled;

    @FXML
    private Hyperlink _hyperForgot;

    @FXML
    private RadioButton _radioPlaceOfInterestName;

    @FXML
    private Button _logInButton;

    @FXML
    private Label _errorMessageLabel;
    
    @FXML
    private ImageView _xIcon;
    
    
    @FXML
    void logInClick(ActionEvent event) 
    {
    	_logInController=new LoginController();
    	int logInReturn = _logInController.login(_userNameFiled.getText(),_passwordFiled.getText()))
    	switch (logInReturn)
    	{
		case 0:
			//check which kind of user it is (Client/employee/manager) and open the right window
			break;
		case 1: 
			_xIcon.setVisible(true);
			_errorMessageLabel.setVisible(true);
			_errorMessageLabel.setText("Wrong password try again !");
			
			break;
		case 2:
			_xIcon.setVisible(true);
			_errorMessageLabel.setVisible(true);
			_errorMessageLabel.setText("invalid userName try again !");
			
			break;
		default:
			break;
		}
    		
    }
    	
    @FXML
    void signUpClick(ActionEvent event) 
    {
    	//open the registrationWindow 
    }

}


