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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginHandler implements Initializable{

	
    @FXML
    private Button _forgetPasswordSendButton;



	
	@FXML
	private Label _incorrectInputLabel;
	
	@FXML
	private Button _buyMapCollection;
	
    @FXML
    private Button _searchButton;

    @FXML
    private RadioButton _radioCityName;

    @FXML
    private RadioButton _radioDescription;

    @FXML
    private ImageView _incorrectIcon;

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
    	
    	System.out.println("sdd");
    	String sql;
    	String table = "users";
		String username = _userNameFiled.getText();
		String password = _passwordFiled.getText();
		sql = "SELECT user_name, password FROM " + table + " WHERE user_name = '" + username
				+ "' AND password = '" + password + "';";
		sendSQL.add("2");
		sendSQL.add(sql);

		ChatClient chat =null;
		
		try {
			chat = new ChatClient();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//return false;
		}
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<ArrayList<String>> m = chat.getArray();
		for(int i = 0;i<m.size();i++) {
			for(int j = 0 ; j<m.get(i).size();j++) {
				System.out.println(m.get(i).get(j));
			}
		}
		/*byte[][] result = chat.returnByteArray();	
		String s = new String(result[0]);
		System.out.println("main: "+s);
		s = new String(result[1]);
		System.out.println("main: "+s); */
		
		
		//if not good user or password 
		//{
		//	_userNameFiled.setText("");
		//	_passwordFiled.setText("");
		//	_incorrectInputLabel.setVisible(true);
		//  _incorrectIcon.setVisible(true);
		//}
		
    }

    @FXML
    void signUpClick(ActionEvent event)
    {
    	 
        try {
       
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/Login/Registration_MainWindow.fxml"));
			Scene scene = new Scene( root );
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			 Stage stage =new Stage();
			 stage.setScene(scene);
			 stage.show();
			
			
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void clickBuyMapCollectionBeforeRegistration()
    {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Not A Client ! ");
		alert.setContentText("To purchase a map you need to register as a customer of the company.");
		alert.showAndWait();
    }
    
    @FXML
    void clickHyperForgot(ActionEvent event)
    {
    	 try {
 			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/Login/Login_ForgotPassword.fxml"));
 			Scene scene = new Scene( root );
 			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
 			 Stage stage = new Stage();
 			 stage.setScene(scene);
 			 stage.show();
 			
 			 Stage s = (Stage)((Node)(event.getSource())).getScene().getWindow();
             s.close();
             
         }
         catch (IOException e) {
             e.printStackTrace();
         }
    }
    
    @FXML
    void clickForgetPasswordSendButton(ActionEvent event)
    {
    	String openFxmlFile = "/Login/Login_ForgotPassword";
    	
    	//if the email is correct
    	//openFxmlFile = openFxmlFile + "Good.fxml";
    	
    	//if the email doest exist in DB 
    	openFxmlFile = openFxmlFile + "Bad.fxml";
    	
    	 try {
    	       
 			Pane root = (Pane) FXMLLoader.load(getClass().getResource(openFxmlFile));
 			Scene scene = new Scene( root );
 			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
 			 Stage stage =new Stage();
 			 stage.setScene(scene);
 			 stage.show();
 			
 			
             // Hide this current window (if this is what you want)
             Stage s = (Stage)((Node)(event.getSource())).getScene().getWindow();
             s.close();
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
