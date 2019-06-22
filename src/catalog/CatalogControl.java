package catalog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class CatalogControl {

    @FXML
    private Button _searchButton;

    @FXML
    private Pane _catalogPane;

    @FXML
    private RadioButton _radioCityName;

    @FXML
    private RadioButton _radioDescription;

    @FXML
    private ListView<?> _listViewResult;

    @FXML
    private TextArea _searchTextFiled;

    @FXML
    private RadioButton _radioPlaceOfInterestName;

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


}
