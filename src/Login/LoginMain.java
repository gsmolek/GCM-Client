package Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;



public class LoginMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Login/Login_MainWindow.fxml"));
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
