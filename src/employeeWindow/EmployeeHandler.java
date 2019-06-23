package employeeWindow;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class EmployeeHandler implements Initializable{

	static private Stack<Window> _openWin;
    
	@FXML
    private Button _buyMapButton;

    @FXML
    private Button _downloadMapButton;

    @FXML
    private Button _forQuestionButton;

    @FXML
    private ImageView _goBackButton;

    @FXML
    private Label _userNameLabel;

    @FXML
    private Button _addNewMapButton;

    @FXML
    private Button _editMapEmployee;

    @FXML
    void clickGoBackButtonInEdit() {
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Logout Confirm");
		alert.setContentText("you arr going back you sure ?? ");
		
		Optional <ButtonType> action = alert.showAndWait();
    	if(action.get() == ButtonType.OK)
    	{
    	    Stage s =(Stage) _openWin.pop();
    	    s.close();
    	    
	    	try {
				AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/employeeWindow/EmployeeWindow.fxml"));
				Scene scene = new Scene( root);
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setScene(scene);
				
				
				
				stage.show();
				
				
				
			} catch(Exception e) {
				e.printStackTrace();
			}
    	}
    }
	
    @FXML
    void clickAddPlace(ActionEvent event)
    {
    	try {
			
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/employeeWindow/AddPlace.fxml"));
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
    void clickAddTour(ActionEvent event)
    {
    	try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/employeeWindow/AddTour.fxml"));
			Scene scene = new Scene( root);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }


    @FXML
    void clickEditMapEmployee() {
    	
    	
    	Stage s = (Stage)_editMapEmployee.getScene().getWindow();
    	
    	AnchorPane root;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/employeeWindow/EmployeeEditMapWindow.fxml"));
			Scene scene = new Scene( root);
		
			s.setScene(scene);
			
			s.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

    @FXML
    void clickAddMapEmployee() {
    	
    	Stage s = (Stage)_editMapEmployee.getScene().getWindow();
    	
    	Pane root;
		try {
			root = (Pane) FXMLLoader.load(getClass().getResource("/employeeWindow/Employee_AddNewMap.fxml"));
			Scene scene = new Scene( root);
		
			s.setScene(scene);
			_openWin.add(s);
			s.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    @FXML
    void clickGoBackButton() {
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Logout Confirm");
		alert.setContentText("you are doing logout are you sure ?? ");
		
		Optional <ButtonType> action = alert.showAndWait();
    	if(action.get() == ButtonType.OK)
    	{
    	    Stage s = (Stage) _goBackButton.getScene().getWindow();
    	    s.close();
    	    
	    	try {
				AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Login/Login_MainWindow.fxml"));
				Scene scene = new Scene( root);
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setScene(scene);
				
				stage.show();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
    	}
    }

    @FXML
    void clickBuyMapButton(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
}
