package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class IpConfigurationController {

	@FXML
	private Button okButton;
	@FXML
	private TextField ipText;
	@FXML
	private TextField portText;
	
	private static String port;
	private static String ip;
	
	
	@FXML
	void clickOk(ActionEvent event)
	{
		System.out.println("!");
		this.ip=this.ipText.getText();
		this.port=this.portText.getText();
		
		if(port==null || port.isEmpty())
		{
			this.port="5550";
		}
		if(ip==null || ip.isEmpty())
		{
			this.ip="localhost";
		}
		
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/Login/Login_MainWindow.fxml"));
			AnchorPane root = loader.load();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			//Alert alert = new Alert(Alert.AlertType.ERROR);
			//alert.setTitle("");
			//alert.setContentText("what you want to showing");
			//alert.showAndWait();
			((Node) (event.getSource())).getScene().getWindow().hide();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getPort() {
		return port;
	}
	public static void setPort(String port) {
		IpConfigurationController.port = port;
	}
	public static String getIp() {
		return ip;
	}
	public static void setIp(String ip) {
		IpConfigurationController.ip = ip;
	}
}
