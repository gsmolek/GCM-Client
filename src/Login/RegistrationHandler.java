package Login;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegistrationHandler implements Initializable{

	private ObservableList<String> listMounth; 
	private ObservableList<String> listYear;
   @FXML
    private ComboBox<String> _yearComboBox;
    
   @FXML
   private ComboBox<String> _monthComboBox;
	 
	@FXML
	private ImageView _markQustionImage;
	
     @FXML
     private ImageView _cvvImage;
	
     @FXML
	 private TextField _rePasswordFieldShow;
	 
    @FXML
    private TextField _passwordFieldShow;

    @FXML
    private ImageView _passwordEye;

    @FXML
    private ImageView _rePasswordEye;

    @FXML
    private PasswordField _passwordField;

    @FXML
    private PasswordField _rePasswordField;
    
    @FXML
    void seePassword()
    {
    	
    	_passwordFieldShow.setText(_passwordField.getText());
    	_passwordFieldShow.setVisible(true);
    	_passwordField.setVisible(false);
    }

    @FXML
    void unSeePassword() 
    {
    	_passwordFieldShow.setVisible(false);
    	_passwordField.setVisible(true);
    }
    @FXML
    void seeRePassword()
    {
    	
    	_rePasswordFieldShow.setText(_rePasswordField.getText());
    	_rePasswordFieldShow.setVisible(true);
    	_rePasswordField.setVisible(false);
    }

    @FXML
    void unSeeRePassword() 
    {
    	_rePasswordFieldShow.setVisible(false);
    	_rePasswordField.setVisible(true);
    }

    
    @FXML
    void cvvShow() 
    {
    	_cvvImage.setVisible(true);
    }

    @FXML
    void cvvUnshow()
    {
    	_cvvImage.setVisible(false);
    }
 
    @FXML
    void clickEnterPayment(ActionEvent event)
    {
    	  try {
          	FXMLLoader loader = new FXMLLoader();
  			Pane root = (Pane) loader.load(getClass().getResource("/Login/Methods of Payment.fxml"));
  			Scene scene = new Scene( root );
  			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  			 Stage stage =new Stage();
  			 stage.setScene(scene);
  			 stage.show();

          }
          catch (IOException e) {
              e.printStackTrace();
          }

    }
    
    @FXML
    void initializationMethodsOfPayment() 
    {
    	_yearComboBox.setItems(listYear);
	    	_monthComboBox.setItems(listMounth);
	    	
    }
  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		listMounth =FXCollections.observableArrayList(
				"01","02","03","04","05","06","07","08","09","10","11","12"); 
		listYear =FXCollections.observableArrayList(
				"2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030");	
		
	}

}
