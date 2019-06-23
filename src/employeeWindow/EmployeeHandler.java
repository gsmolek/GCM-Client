package employeeWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;

import ServerConnection.ChatClient;
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

	static private ArrayList<Scene> _openWin = new ArrayList<>();
    
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
    private Label _tempLabel;
    
    @FXML
    private Button _addNewMapButton;

    @FXML
    private Button _editMapEmployee;

    @FXML
    private Pane _editPane;

	private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ChatClient chat = null;
	private ArrayList<ArrayList<String>> m;
    
    @FXML
    void clickGoBackButtonInEdit() {
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Logout Confirm");
		alert.setContentText("you arr going back you sure ?? ");
		
		Optional <ButtonType> action = alert.showAndWait();
    	
		if(action.get() == ButtonType.OK)
    	{
    		
    	    
	    	try {
				
				Stage stage = (Stage)_openWin.get(1).getWindow();
				stage.close();
				
				stage = (Stage)_openWin.get(0).getWindow();
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
    	
    	_openWin.add(_editMapEmployee.getScene());
    	Stage sToClose =(Stage)(_editMapEmployee.getScene().getWindow());
    	sToClose.hide();
    	
    	AnchorPane root;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/employeeWindow/EmployeeEditMapWindow.fxml"));
			Scene scene = new Scene( root);
			
			_openWin.add(scene);
			
			Stage sToOpen=new Stage();
			sToOpen.setScene(scene);
			sToOpen.show();
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
    }

    @FXML
    void clickAddMapEmployee() {
    	
    	_openWin.add(_editMapEmployee.getScene());
    	Stage s = (Stage)_editMapEmployee.getScene().getWindow();
    	s.hide();
    	
    	Pane root;
		try {
			root = (Pane) FXMLLoader.load(getClass().getResource("/employeeWindow/Employee_AddNewMap.fxml"));
			Scene scene = new Scene(root);
			_openWin.add(scene);
			Stage s2 = new Stage();
			s2.setScene(scene);
			s2.show();
			
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
    void clickShowMapButton(ActionEvent event)
    {
    	
    	//if there is choose map 
    	_editPane.setVisible(true);
    	_tempLabel.setVisible(false);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		m = null;
		try {
			chat = new ChatClient();
		} catch (IOException e1) {
			
			e1.printStackTrace();
			// return false;
		}
		
	}
}
