package clientWindow;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientWindowHandler implements Initializable {

    @FXML
    private Button _searchButton;

    @FXML
    private Button _buyMapButton;

    @FXML
    private Button _downloadMapButton;

    @FXML
    private Button _forQuestionButton;

    @FXML
    private RadioButton _radioCityName;

    @FXML
    private RadioButton _radioDescription;

    @FXML
    private ListView<?> _listViewResult;

    @FXML
    private Label _userNameLabel;

    @FXML
    private TextArea _searchTextFiled;

    @FXML
    private RadioButton _radioPlaceOfInterestName;

    @FXML
    private Button _noButton;
   
    @FXML
    private Button _yesButton;
    
    @FXML
    private ComboBox<String> _comboBoxPeriod;
    
    private ObservableList<String> periodList;
    
    @FXML
    void radioCitySelect(ActionEvent event)
    {
    	if(_radioCityName.isSelected())
    	{
    		_radioPlaceOfInterestName.setSelected(false);
    		_radioDescription.setSelected(false);
    	}
    }
    @FXML
    void radioPlaceOfInterestName(ActionEvent event)
    {
    	if(_radioPlaceOfInterestName.isSelected())
    	{
    		_radioCityName.setSelected(false);
    		_radioDescription.setSelected(false);
    	}
    }
    @FXML
    void radioDescription(ActionEvent event)
    {
    	if(_radioDescription.isSelected())
    	{
    		_radioPlaceOfInterestName.setSelected(false);
    		_radioCityName.setSelected(false);
    	}
    }

    @FXML
    void clickBuyMapButton(ActionEvent event)
    {
    	 try {
         	FXMLLoader loader = new FXMLLoader();
 			Pane root = (Pane) loader.load(getClass().getResource("/clientWindow/ConfirmBuy.fxml"));
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
    void clickYesButton(ActionEvent event)
    {
    	 try {
         	FXMLLoader loader = new FXMLLoader();
 			Pane root = (Pane) loader.load(getClass().getResource("/clientWindow/ChooseTypeOfPruchaseWindow.fxml"));
 			Scene scene = new Scene( root );
 			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
 			 Stage stage =new Stage();
 			 stage.setScene(scene);
 			 
 		
 			 stage.show();
 			
             // Hide this current window (if this is what you want)
             ((Node)(event.getSource())).getScene().getWindow().hide();
             
         }
         catch (IOException e) {
             e.printStackTrace();
         }
    }

    @FXML
    void clickNoButton(ActionEvent event) {
    	
		Stage stage=(Stage) _noButton.getScene().getWindow();
    	stage.close();
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		periodList = FXCollections.observableArrayList(
					"One Month - 1","Two Months - 2","Three Months - 3","Four Months - 4","Five Months -5","Six Months - 6"); 
			

	}

	@FXML
	void initializationPeriod()
	{
		_comboBoxPeriod.setItems(periodList);
	}
}
