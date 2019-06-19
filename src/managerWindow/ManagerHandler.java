package managerWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class ManagerHandler {

    @FXML
    private Pane _paneArchive;
	
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
    private RadioButton _radioArchiveView;

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
    void clickBuyMapButton(ActionEvent event) {

    }

    @FXML
    void clickAddPlace(ActionEvent event) {

    }

    @FXML
    void clickAddTour(ActionEvent event) {

    }


    @FXML
    void selectArchiveView(ActionEvent event) {
    	if(_radioArchiveView.isSelected())
    	_paneArchive.setVisible(true);
    	else
    		_paneArchive.setVisible(false);
    }
}
