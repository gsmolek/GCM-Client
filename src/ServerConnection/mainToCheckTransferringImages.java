package ServerConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class mainToCheckTransferringImages extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		this.getIPfromUser(primaryStage);

	}
	public void getIPfromUser(Stage primaryStage)
	{
			BorderPane root = new BorderPane();
		   Scene scene = new Scene(root, 200, 200);
	        Stage stage = new Stage();

	        try {
			System.out.println("entered");
			ChatClient a= new ChatClient();
			ArrayList<Object> array= new ArrayList<Object>();
			array.add("5");
			array.add("afula.jpg");
			a.handleMessageFromClient(array);
	        }catch(Exception e)
	        {
	        	
	        }
	        
	        
	        
	        stage.setScene(scene);
	        stage.show();
	}

}
