package Login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class successHandler implements Initializable{
	private RegistrationHandler reg;
	public void setRegHandler(RegistrationHandler reg) {
		this.reg = reg;
	}
	 @FXML
	    private Button _toLoginButton;

	    @FXML
	    void clickToLoginButton(ActionEvent event) {
	    	Stage stage2 = (Stage) _toLoginButton.getScene().getWindow();
			stage2.close();
			reg.closeWindow();
	    	//close reg window
	    }
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
