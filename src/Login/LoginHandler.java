package Login;

import ServerConnection.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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

	
	//LoginController _logInController;
	
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
    
    
    
    
    
    private ArrayList<Object> sendSQL = new ArrayList<Object>();
    private ResultSet rs;
    
   @FXML
   public void logInClick(ActionEvent event) 
    {
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
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[][] result = chat.returnByteArray();	
		String s = new String(result[0]);
		System.out.println("main: "+s);
		s = new String(result[1]);
		System.out.println("main: "+s);
		
			
			
			
			// send SQL string to the server
			// get ResltSet back from the server
			/*Statement stmt;
			stmt = dbConnector.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);*/
			
			
		
			
			//if resultSet is empty the user doesn't exist & return false
			

			if (rs==null) {
				System.out.println("userName or password is incorect");
				//return false;
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


