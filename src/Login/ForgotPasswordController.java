package Login;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ServerConnection.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ForgotPasswordController implements Initializable {
	@FXML
	private TextField _forgetPasswordText;
	@FXML
	private Button clickForgetPasswordSendButton;
	private ChatClient chat;
	
	public ForgotPasswordController(ChatClient chat)
	{
		this.chat=chat;
	}
	@FXML
	public void clickForgetPasswordSendButton(ActionEvent event)
	{
		String email=_forgetPasswordText.getText();
		String sql="SELECT email FROM user_card WHERE email='"+email+"';";
		ArrayList<Object> toSend=new ArrayList<Object>();
		this.chat.clearStr();
		toSend.add("2");
		toSend.add(sql);
		this.chat.handleMessageFromClient(toSend);
		ArrayList<ArrayList<String>> arrayFromUser=this.chat.getArray();
		if(arrayFromUser==null || arrayFromUser.isEmpty())
		{
			
		}
		else
		{
			
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
