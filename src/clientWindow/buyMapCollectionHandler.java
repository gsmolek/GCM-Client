package clientWindow;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class buyMapCollectionHandler implements Initializable{
	private String mapid;
	private typeOfpurchaseHandller t;
	private ClientWindowHandler c;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	private String table;
	private Stage previos;
	//private ClientWindowHandler c;
	
	public buyMapCollectionHandler() {
		// TODO Auto-generated constructor stub
		this.mapid = "sss";
		
	}
	public String sendMapId() {
		return mapid;
	}
	
	public void setClientWindowHandler(ClientWindowHandler c) {
		this.c = c;
	}
	
	public void swttypeOfpurchaseHandller(typeOfpurchaseHandller t) {
		this.t= t;
	}
	public void getMapID(String id) {
		this.mapid = id;
	}
	public void getStage(Stage g) {
		this.previos = g;
	}
	
    @FXML
    private Button _yesButton;

    @FXML
    private Button _noButton;
    @FXML
    private Label collectionID;

	@FXML
	void clickNoButton(ActionEvent event) {

		Stage stage = (Stage) _noButton.getScene().getWindow();
		stage.close();

	}
	@FXML
    void clickYesButton(ActionEvent event) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		//System.out.println();
		String userID=c.user_id;
		String dateBuy="";
		String period = String.valueOf(c.period);
		int per = c.period;
		if(per ==-1) {
			
		}
		String dateEnd="2019-08-06";
		String typeOfPurases=String.valueOf(c.chois);
		String renew ="0";
		sendSQL = new ArrayList<Object>();
		sendSQL.clear();
		if(per ==-1) 
			sql = "INSERT INTO purchases (user_id, collaction_id, date_buy, date_end, type_of_purchases, renew) values ('"+userID+"','"+mapid+"','"+dtf.format(now)+"','"+dtf.format(now)+"','"+typeOfPurases+"','0');";
		else
			sql = "INSERT INTO purchases (user_id, collaction_id, date_buy, date_end, type_of_purchases, renew) values ('"+userID+"','"+mapid+"','"+dtf.format(now)+"','"+dtf.format(now.plusMonths(per))+"','"+typeOfPurases+"','0');";
		sendSQL.add("3");
		sendSQL.add(sql);
		c.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m = c.chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("no result");
			
		} else {
			System.out.println(m.get(0).get(0));
			Stage stage = (Stage) _noButton.getScene().getWindow();
			stage.close();
			t.closeStage();
			
			
			try { FXMLLoader loader = new FXMLLoader();
			  loader.setLocation(getClass().getResource("/clientWindow/SuccessWindow.fxml"));
			  Pane root = loader.load();
			  
			  Scene scene = new Scene(root);
			  scene.getStylesheets().add(getClass().getResource("application.css").
			  toExternalForm());
			  
			  congrase control = loader.getController();
			  control.getbuyMapCollectionHandler(this);
			  
			  stage = new Stage(); stage.setScene(scene); stage.show(); 
			    
			} catch
			  (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); 
				  }
			
			
			
		}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//System.out.println(c.mapIDForBuy);
		
		
	}
}
