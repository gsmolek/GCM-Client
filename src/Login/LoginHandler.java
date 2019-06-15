package Login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.media.jfxmediaimpl.platform.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

public class LoginHandler implements Initializable{

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

    @FXML
    void radioCitySelect(ActionEvent event)
    {
    	if(_radioCityName.isSelected())
    	{
    		_radioPlaceOfInterestName.setSelected(false);
    		_radioDescription.setSelected(false);
    	}
    }
    @FXML
    void radioPlaceOfInterestName(ActionEvent event)
    {
    	if(_radioPlaceOfInterestName.isSelected())
    	{
    		_radioCityName.setSelected(false);
    		_radioDescription.setSelected(false);
    	}
    }
    @FXML
    void radioDescription(ActionEvent event)
    {
    	if(_radioDescription.isSelected())
    	{
    		_radioPlaceOfInterestName.setSelected(false);
    		_radioCityName.setSelected(false);
    	}
    }


    @FXML
    void logInClick(ActionEvent event) 
    {
    	//LoginController loginController = new LoginController();
    	//loginController.login(_userNameFiled.getText(), _passwordFiled.getText());
    	
    }

    @FXML
    void signUpClick(ActionEvent event)
    {
    	 
        try {
        	FXMLLoader loader = new FXMLLoader();
			Pane root = (Pane) loader.load(getClass().getResource("/Login/RegistrationWindow.fxml"));
			Scene scene = new Scene( root );
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			 Stage stage =new Stage();
			 stage.setScene(scene);
			 stage.show();
			
			
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            stage.setOnCloseRequest(e -> new Main().start(new Stage()));;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
