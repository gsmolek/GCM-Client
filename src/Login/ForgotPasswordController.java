package Login;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ForgotPasswordController implements Initializable {
	@FXML
	private TextField _forgetPasswordText;
	@FXML
	private Button _forgetPasswordSendButton;
	private ChatClient chat;
	private LoginHandler a;
	private Stage s;
	private Stage stageBad;
	private Stage stageGood;

	public ChatClient getChat() {
		return chat;
	}

	public void setChat(ChatClient chat) {
		this.chat = chat;
	}
	public void setHandler(LoginHandler a,Stage s)
	{
		this.a=a;
		this.s=s;
	}

	@FXML
	public void clickForgetPasswordSendButton(ActionEvent event)
	{
		try {
		String email=_forgetPasswordText.getText();
		System.out.println(email);
		ArrayList<Object> toSend=new ArrayList<Object>();
		toSend.add("6");
		toSend.add(email);
		this.a.chat=new ChatClient(IpConfigurationController.getIp(),Integer.valueOf(IpConfigurationController.getPort()));
		this.a.chat.handleMessageFromClient(toSend);
		
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<ArrayList<String>> arrayFromUser=this.a.chat.getArray();
		if( arrayFromUser == null || arrayFromUser.isEmpty())
		{
			try {
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("/Login/Login_ForgotPasswordBad.fxml"));
				AnchorPane root;
				
					root = loader.load();
	
				
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
				Stage stageBad = new Stage();
				stageBad.setScene(scene);
				stageBad.show();
				arrayFromUser=null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		else
		{
		
			
			
			try {
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("/Login/Login_ForgotPasswordGood.fxml"));
				AnchorPane root;
				
					root = loader.load();
	
				
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
				stageGood = new Stage();
				stageGood.setScene(scene);
				stageGood.show();
				this.s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void clickOkBad(ActionEvent event)
	{
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	@FXML
	public void clickOkGood(ActionEvent event)
	{
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
	}

}
