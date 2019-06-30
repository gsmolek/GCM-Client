package managerWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ApproveVertionHandler implements Initializable{
    @FXML
    private Label _userNameLabel;
    
	@FXML
	private TableView<row> listRequest;
	@FXML
	private TableView<row> listApproved;
	@FXML
	private Button addButton;
	@FXML
	private Button approveButton;
	@FXML
	private Button rejectButton;
	@FXML
	private TableColumn<row,String> Versioncol;
	@FXML
	private TableColumn<row,String> CityNamecol;
	@FXML
	private TableColumn<row,String> Collectioncol;
	@FXML
	private TableColumn<row,String> usercol;
	@FXML
	private AnchorPane anchor;
	private Stage thisStage;
	@FXML
	private TableColumn<row,String> versionR;
	@FXML
	private TableColumn<row,String> cityNameR;
	@FXML
	private TableColumn<row,String> collectionR;
	@FXML
	private TableColumn<row,String> UserEditedR;
	
	private ChatClient chat;
	private ArrayList<ArrayList<String>> result;
	private ManagerHandler managerHandler;
	
	public void setManagerHandler(ManagerHandler managerHandler) {
		this.managerHandler = managerHandler;
		createRequestListView();
		_userNameLabel.setText(managerHandler.welcome);
	}
	
	@FXML
    void backToMain(MouseEvent event) {
		
    	managerHandler.openThisWindow();
		Stage stage2 = (Stage) addButton.getScene().getWindow();
		stage2.close();
    }
    
	@FXML
	public void clickOnAdd(ActionEvent event)
	{
		row a = listApproved.getSelectionModel().getSelectedItem();
		listApproved.getItems().remove(a);
		listRequest.getItems().add(a);
		System.out.println(a.getCityName());
	}
	@FXML
	public void clickOnRemove(ActionEvent event)
	{
		row a = listRequest.getSelectionModel().getSelectedItem();
		listRequest.getItems().remove(a);
		listApproved.getSelectionModel().clearSelection();
		listApproved.getItems().add(a);
	}

	public void sendNotificationsOfApprovedMaps(ArrayList<String> notify)
	{
		ArrayList<Object> toSent=new ArrayList<>();
		String sql="SELECT email,collaction_id FROM user_card d,purchases p WHERE p.user_id=d.user_id AND d.user_id IN \r\n" + 
				"(SELECT user_id FROM purchases c WHERE ";
		if(notify!=null || !notify.isEmpty())
		{
			String add="c.collaction_id="+notify.get(0);
			sql=sql+add;
			for(int i=1;i<notify.size();i++)
			{
				String temp=" OR c.collaction_id="+notify.get(i);
				sql=sql+temp;
			}
			sql=sql+")GROUP BY email;";
			System.out.println(sql);
			toSent.add("2");
			toSent.add(sql);
			managerHandler.chat.handleMessageFromClient(toSent);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = managerHandler.chat.getArray();
			System.out.println("result");
			System.out.println(result);
			if (result == null || result.isEmpty()) {
				System.out.println("Empty resultSet");
			} else {
				ArrayList<String> emails=new ArrayList<>();
				ArrayList<String> coll=new ArrayList<>();
				ArrayList<String> t = result.get(0);
				String strCol=" ";
				for(int i=0,j=1;i<result.size();i++,j++)
				{
					emails.add(t.get(i));
					coll.add(t.get(j));
					strCol=strCol+t.get(j);
					strCol=strCol+", ";
				}
				System.out.println("emails");
				System.out.println(emails);
				System.out.println("cols");
				System.out.println(coll);
				ArrayList<Object> to =new ArrayList<Object>();
				to.add("7");
				String head="Update";
				String body= "A new Version is available for collections: "+strCol;
				System.out.println("head:"+head);
				System.out.println("body:"+body);
				to.add(emails);
				to.add(head);
				to.add(body);
				managerHandler.chat.handleMessageFromClient(to);
			}
		}
		
	}
	
	ArrayList<String> notify=new ArrayList<>();
	
	@FXML
	public void clickApprove(ActionEvent event)
	{
		ObservableList<row> a=listRequest.getItems();
		if(a!=null && !a.isEmpty())
		{
		System.out.println(a.get(0).userName);
		System.out.println("a-size: "+a.size());
		int len=a.size();
		for(int i=0;i<len;i++)
		{
			ArrayList<Object> toSent=new ArrayList<>();
			toSent.add("3");
			String query="UPDATE map_collection SET approved=1 WHERE Id="+a.get(0).getCollection()+";";
			toSent.add(query);
			managerHandler.chat.handleMessageFromClient(toSent);
			notify.add(a.get(0).getCollection());
			listRequest.getItems().remove(a.get(0));
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sendNotificationsOfApprovedMaps(notify);
		}
	}
	@FXML
	public void clickReject()
	{
		ObservableList<row> a=listRequest.getItems();
		if(a!=null && !a.isEmpty())
		{
		System.out.println(a.get(0).userName);
		int len=a.size();
		for(int i=0;i<len;i++)
		{
			ArrayList<Object> toSent=new ArrayList<>();
			toSent.add("3");
			String query="UPDATE map_collection SET approved=2 WHERE Id="+a.get(0).getCollection()+";";
			toSent.add(query);
			managerHandler.chat.handleMessageFromClient(toSent);
			listRequest.getItems().remove(a.get(0));
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}
	public void createRequestListView()
	{
		String query="SELECT vertion,name,userName,collection_id  FROM gcm.map_collection c, gcm.map_mapcollection m, map p ,city t\r\n" + 
				"WHERE c.Id=m.collection_id AND m.map_id=p.Id AND p.city_id=t.Id AND c.approved=0 \r\n" + 
				"GROUP BY collection_id;";
		ArrayList<Object> toClient =new ArrayList<Object>();
		toClient.add("2");
		toClient.add(query);
		managerHandler.chat.handleMessageFromClient(toClient);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = managerHandler.chat.getArray();

		if (result == null || result.isEmpty()) {
			System.out.println("Empty resultSet");
		} else {

			for(int i=0;i<result.size();i++)
			{
				ArrayList<String> temp= result.get(i);
				System.out.println(temp);
				listApproved.getItems().add(new row (temp.get(0),temp.get(1),temp.get(3),temp.get(2)));
			}
			
		}
		}
	
	
	public void radioCitySelect(ActionEvent event)
	{}
	public void radioPlaceOfInterestName(ActionEvent event)
	{}
	public void radioDescription(ActionEvent event)
	{}
	public void clickBuyMapButton(ActionEvent event)
	{}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
			Versioncol.setCellValueFactory(new PropertyValueFactory<>("versionNumber"));
			CityNamecol.setCellValueFactory(new PropertyValueFactory<>("cityName"));
			Collectioncol.setCellValueFactory(new PropertyValueFactory<>("collection"));
			usercol.setCellValueFactory(new PropertyValueFactory<>("userName"));
			
			versionR.setCellValueFactory(new PropertyValueFactory<>("versionNumber"));
			cityNameR.setCellValueFactory(new PropertyValueFactory<>("cityName"));
			collectionR.setCellValueFactory(new PropertyValueFactory<>("collection"));
			UserEditedR.setCellValueFactory(new PropertyValueFactory<>("userName"));
			
			
			
		
		
		
	}
	public class row
	{
		private String versionNumber;
		public row(String versionNumber, String cityName, String collection, String userName) {
			super();
			this.versionNumber = versionNumber;
			this.cityName = cityName;
			this.collection = collection;
			this.userName = userName;
		}
		private String cityName;
		private String collection;
		private String userName;

		public String getVersionNumber() {
			return versionNumber;
		}
		public void setVersionNumber(String versionNumber) {
			this.versionNumber = versionNumber;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		public String getCollection() {
			return collection;
		}
		public void setCollection(String collection) {
			this.collection = collection;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
	}
}
