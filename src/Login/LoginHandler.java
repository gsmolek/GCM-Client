package Login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LoginHandler  implements Initializable {

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
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    
    @FXML
    void logInClick(ActionEvent event) 
    {
    	_logInController=new LoginController();
    	int logInReturn=0;// = _logInController.login(_userNameFiled.getText(),_passwordFiled.getText()))
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

    @FXML
    void radioCitySelect(ActionEvent event) {
    	_radioCityName.setSelected(true);
    	_radioDescription.setSelected(false);
    	_radioPlaceOfInterestName.setSelected(false);
    	//()event.getSource()
    }

	

}


