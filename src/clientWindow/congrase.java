package clientWindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class congrase implements Initializable{
	private buyMapCollectionHandler b;
	 @FXML
	    private Label successMSG;
	 @FXML
	    private Button clsbtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void getbuyMapCollectionHandler(buyMapCollectionHandler b) {
		this.b = b;
	}
	
	@FXML
    void closeBTN(ActionEvent event) {
		Stage stage = (Stage) clsbtn.getScene().getWindow();
		stage.close();
    }
	 
	 
}
