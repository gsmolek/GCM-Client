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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class typeOfpurchaseHandller implements Initializable {

	private Stage stage;
	private ObservableList<String> periodList;
	private ClientWindowHandler c;
	
	@FXML
	private RadioButton _radioOneTimeP;
	
	@FXML
    private Button cancelBtn;
	
	@FXML
	private Button butBTN;

	@FXML
	private RadioButton _radioSubP;

	@FXML
	private Label _labelToOneTimeP;

	@FXML
	private Label _labelSubP;

	@FXML
	private ComboBox<String> _comboBoxPeriod;

	@FXML
	void initializationPeriod() {
		_comboBoxPeriod.setItems(periodList);
	}

	@FXML
	private Label subsField;

	@FXML
	void setOne(ActionEvent event) {
		c.chois = 0;
		_comboBoxPeriod.setVisible(false);
		subsField.setVisible(false);
		butBTN.setDisable(false);
	}

	@FXML
	void setSubs(ActionEvent event) {
		c.chois = 1;
		_comboBoxPeriod.setVisible(true);
		subsField.setVisible(true);
		butBTN.setDisable(true);
	}

	@FXML
	void periodChoose(ActionEvent event) {
		if (_comboBoxPeriod.getSelectionModel().getSelectedIndex() != -1) {
			butBTN.setDisable(false);
			c.period = _comboBoxPeriod.getSelectionModel().getSelectedIndex()+1;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// int selected = _listViewResult.getSelectionModel().getSelectedIndex();
		periodList = FXCollections.observableArrayList("One Month - 1", "Two Months - 2", "Three Months - 3",
				"Four Months - 4", "Five Months -5", "Six Months - 6");
		// set the radio buttons in one group
		final ToggleGroup searchGroup = new ToggleGroup();
		_radioSubP.setToggleGroup(searchGroup);
		_radioOneTimeP.setToggleGroup(searchGroup);
		
		
	}

	public void setClientWindowHandler(ClientWindowHandler c) {
		this.c = c;
	}

	@FXML
	void butMap(ActionEvent event) {
		try { FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(getClass().getResource("/clientWindow/ConfirmBuy.fxml"));
		  Pane root = loader.load();
		  
		  Scene scene = new Scene(root);
		  scene.getStylesheets().add(getClass().getResource("application.css").
		  toExternalForm());
		  
		  buyMapCollectionHandler control = loader.getController();
		  control.setClientWindowHandler(c);
		  control.getMapID(c.mapIDForBuy);
		  control.swttypeOfpurchaseHandller(this);
		  
		  
		  stage = new Stage(); stage.setScene(scene); stage.show(); 
		  control.getStage(stage);  
		} catch
		  (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); 
			  }
		  }
	public void closeStage() {
		stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
		c.setInListPurcessMaps();
	}
	

	@FXML
	void cancelPurchase(ActionEvent event) {
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}

	
	  
	 
}
