package employeeWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EmployeeHandler {

    @FXML
    private Button _searchButton;

    @FXML
    private Button _buyMapButton;

    @FXML
    private Button _addTourButton;

    @FXML
    private RadioButton _radioCityName;

    @FXML
    private Button _editTourButton;

    @FXML
    private RadioButton _radioDescription;

    @FXML
    private ListView<?> _listViewResult;

    @FXML
    private Label _userNameLabel;

    @FXML
    private Button _addPlaceButton;

    @FXML
    private Button _addNewMapButton;

    @FXML
    private Button _downloadMapButton;

    @FXML
    private Button _forQuestionButton;

    @FXML
    private Button _editPlaceButton;

    @FXML
    private TextArea _searchTextFiled;

    @FXML
    private RadioButton _radioPlaceOfInterestName;

    @FXML
    void radioCitySelect(ActionEvent event) {

    }

    @FXML
    void radioPlaceOfInterestName(ActionEvent event) {

    }

    @FXML
    void radioDescription(ActionEvent event) {

    }

    @FXML
    void clickAddPlace(ActionEvent event)
    {
    	try {
			FXMLLoader loader = new FXMLLoader();
			AnchorPane root = (AnchorPane) loader.load(getClass().getResource("/employeeWindow/AddPlace.fxml"));
			Scene scene = new Scene( root, 400,763);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void clickBuyMapButton(ActionEvent event) {

    }

}
