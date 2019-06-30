package Login;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;




public class LoginMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent e) {
			    	System.out.println("asdfasfsfs");
			     Platform.exit();
			     System.exit(0);
			    }
			  });
			
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Login/IpConfiguration.fxml"));
			Scene scene = new Scene( root );
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Alert alert = new Alert(Alert.AlertType.ERROR);
			//alert.setTitle("");
			//alert.setContentText("what you want to showing");
			//alert.showAndWait();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
