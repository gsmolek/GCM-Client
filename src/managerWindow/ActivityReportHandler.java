package managerWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ActivityReportHandler implements Initializable {

	@FXML
	private TableColumn<ResForReport, String> _renewalsColumn;

	@FXML
	private TableView<String> _resTable;

	@FXML
	private TableColumn<ResForReport, String> _subscribersColumn;

	@FXML
	private TableColumn<ResForReport, String> _downloadsColumn;

	@FXML
	private Label _userNameLabel;

	@FXML
	private Pane _paneUserInfoPruchase;

	@FXML
	private TableColumn<ResForReport, String> _quantityMapsColumn;

	@FXML
	private Button _getReportCity;

	@FXML
	private ComboBox<String> _citySelect;

	@FXML
	private Button _forQuestionButton;

	@FXML
	private ImageView _backIcon;

	@FXML
	private DatePicker _toDate;

	@FXML
	private DatePicker _fromDate;

	@FXML
	private Button _getReportForCities;

	@FXML
	private TableColumn<ResForReport, String> _oneTimeColumn;

	@FXML
	private TableColumn<ResForReport, String> _viewsColumn;

	@FXML
	private TableColumn<ResForReport, String> _cityNameColumn;
	
	private final String GET_DATA = "2";
	private final String UPDATE_DELETE_INSERT_DATA = "3";
	private final String GET_IMAGE = "5";

	ObservableList<String> listToShow = FXCollections.observableArrayList();

	private ArrayList<Object> sendSQL = new ArrayList<Object>();
	private ChatClient chat = null;
	private ArrayList<ArrayList<String>> m;

	@FXML
	void clickforQuestionButton(ActionEvent event) {

	}

	@FXML
	void clickBackIcon(ActionEvent event) {

	}

	@FXML
	void clickgetReportForCities(ActionEvent event)
	{
		_cityNameColumn.setVisible(true);
		
		sendSQL.clear();
		String sql=null;
		
		//name of city and quantity Of maps 
		sql="SELECT city.name,COUNT(city_id) as maps_numbers,id_collaction\r\n" + 
				"FROM city,map\r\n" + 
				"where city.Id=map.city_id \r\n" + 
				"GROUP BY city_id;";
		
		//One time purchases and sub purchases and renew 
		sql ="SELECT collaction_id,SUM(type_of_purchases=0) as One_time ,SUM(type_of_purchases=1) as sub,sum(renew) as renew\r\n" + 
				"FROM gcm.purchases\r\n" + 
				" group by collaction_id;";

		
		//Views
		sql="SELECT views.collaction_id,count(collaction_id) As \"Views\"\r\n" + 
				"FROM gcm.views\r\n" + 
				"group by collaction_id;";
		
		
		//download
		sql="SELECT collection_id,COUNT(collection_id) AS Downlaod \r\n" + 
				"FROM gcm.download\r\n" + 
				"group by collection_id;\r\n" + 
				"";
		
		sendSQL.add(GET_DATA);
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		m = chat.getArray();
		
		
	}

	@FXML
	void clickgetReportCity(ActionEvent event) 
	{
		_cityNameColumn.setVisible(false);
	}

	public class ResForReport {
		private String quantityMaps;
		private String oneTimePruchase;
		private String subscribers;
		private String renewals;
		private String views;
		private String download;

		public ResForReport(String quantityMaps, String oneTimePruchase, String subscribers, String renewals,
				String views, String download) {
			super();
			this.quantityMaps = quantityMaps;
			this.oneTimePruchase = oneTimePruchase;
			this.subscribers = subscribers;
			this.renewals = renewals;
			this.views = views;
			this.download = download;
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		m = null;
		try {
			chat = new ChatClient();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		sendSQL.clear();
		String sql=null;
		
		sql = "SELECT name \r\n" + 
				"FROM city \r\n" + 
				";";
		
		sendSQL.add(GET_DATA);
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		m = chat.getArray();
		
		for (ArrayList<String> arrayList : m) 
		{
			listToShow.add(arrayList.get(0));
		}
		
		_citySelect.setItems(listToShow);
		
	}
}







