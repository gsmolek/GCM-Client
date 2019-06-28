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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import managerWindow.ManagerClientInfoHandler.PruchaseRowView;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javafx.animation.AnimationTimer;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;


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
		
		sql="select *\r\n" + 
				"from\r\n" + 
				"(select *\r\n" + 
				"from ( SELECT collection_id,count(collection_id) as Downloads\r\n" + 
				"FROM gcm.download\r\n" + 
				"group by collection_id) as t1, (SELECT collaction_id as collaction_id_view ,count(collaction_id) as Views\r\n" + 
				"FROM gcm.views\r\n" + 
				"group by collaction_id)as t2,\r\n" + 
				"(SELECT collaction_id as collaction_id_purchases ,SUM(type_of_purchases=0) as One_time ,SUM(type_of_purchases=1) as sub,sum(renew) as renew\r\n" + 
				"				FROM gcm.purchases \r\n" + 
				"				group by collaction_id)as t3\r\n" + 
				"where t1.collection_id=collaction_id_view AND t2.collaction_id_view = t3.collaction_id_purchases)\r\n" + 
				" as t4,(SELECT city.name,COUNT(city_id) as maps_numbers,id_collaction\r\n" + 
				"				FROM city,map\r\n" + 
				"				where city.Id=map.city_id\r\n" + 
				"				GROUP BY city_id) as t5\r\n" + 
				"where t5.id_collaction=t4.collaction_id_view\r\n" + 
				"group by collaction_id_purchases;";
		
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
	
		 
		ResForReportAllCities newRow;
		
		for (ArrayList<String> arrayList : m) 
		{
			//String[] newRow ={arrayList.get(8),arrayList.get(9), arrayList.get(5),
				//	arrayList.get(6), arrayList.get(7), arrayList.get(3), arrayList.get(1)};
			
			newRow = new ResForReportAllCities(arrayList.get(8),arrayList.get(9), arrayList.get(5),
					arrayList.get(6), arrayList.get(7), arrayList.get(3), arrayList.get(1));
		
			_resTable.getItems().addAll(newRow);
		}
		
	}

	@FXML
	void clickgetReportCity(ActionEvent event) 
	{
		_cityNameColumn.setVisible(false);
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
		
		
		_cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameMap"));
		_quantityMapsColumn.setCellValueFactory(new PropertyValueFactory<>("quantityMaps"));
		_oneTimeColumn.setCellValueFactory(new PropertyValueFactory<>("oneTimePruchase"));
		_subscribersColumn.setCellValueFactory(new PropertyValueFactory<>("subscribers"));
		_renewalsColumn.setCellValueFactory(new PropertyValueFactory<>("renewals"));
		_viewsColumn.setCellValueFactory(new PropertyValueFactory<>("views"));
		_downloadsColumn.setCellValueFactory(new PropertyValueFactory<>("download"));
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

		/**
		 * @return the quantityMaps
		 */
		public String getQuantityMaps() {
			return quantityMaps;
		}

		/**
		 * @return the oneTimePruchase
		 */
		public String getOneTimePruchase() {
			return oneTimePruchase;
		}

		/**
		 * @return the subscribers
		 */
		public String getSubscribers() {
			return subscribers;
		}

		/**
		 * @return the renewals
		 */
		public String getRenewals() {
			return renewals;
		}

		/**
		 * @return the views
		 */
		public String getViews() {
			return views;
		}

		/**
		 * @return the download
		 */
		public String getDownload() {
			return download;
		}

		/**
		 * @param quantityMaps the quantityMaps to set
		 */
		public void setQuantityMaps(String quantityMaps) {
			this.quantityMaps = quantityMaps;
		}

		/**
		 * @param oneTimePruchase the oneTimePruchase to set
		 */
		public void setOneTimePruchase(String oneTimePruchase) {
			this.oneTimePruchase = oneTimePruchase;
		}

		/**
		 * @param subscribers the subscribers to set
		 */
		public void setSubscribers(String subscribers) {
			this.subscribers = subscribers;
		}

		/**
		 * @param renewals the renewals to set
		 */
		public void setRenewals(String renewals) {
			this.renewals = renewals;
		}

		/**
		 * @param views the views to set
		 */
		public void setViews(String views) {
			this.views = views;
		}

		/**
		 * @param download the download to set
		 */
		public void setDownload(String download) {
			this.download = download;
		}

		
	}

	public class ResForReportAllCities {
		

		private String nameMap;
		private String quantityMaps;
		private String oneTimePruchase;
		private String subscribers;
		private String renewals;
		private String views;
		private String download;

		public ResForReportAllCities(String nameMap, String quantityMaps, String oneTimePruchase, String subscribers,
				String renewals, String views, String download) {
			super();
			this.nameMap = nameMap;
			this.quantityMaps = quantityMaps;
			this.oneTimePruchase = oneTimePruchase;
			this.subscribers = subscribers;
			this.renewals = renewals;
			this.views = views;
			this.download = download;
		}

		/**
		 * @return the nameMap
		 */
		public String getNameMap() {
			return nameMap;
		}

		/**
		 * @return the quantityMaps
		 */
		public String getQuantityMaps() {
			return quantityMaps;
		}

		/**
		 * @return the oneTimePruchase
		 */
		public String getOneTimePruchase() {
			return oneTimePruchase;
		}

		/**
		 * @return the subscribers
		 */
		public String getSubscribers() {
			return subscribers;
		}

		/**
		 * @return the renewals
		 */
		public String getRenewals() {
			return renewals;
		}

		/**
		 * @return the views
		 */
		public String getViews() {
			return views;
		}

		/**
		 * @return the download
		 */
		public String getDownload() {
			return download;
		}

		/**
		 * @param nameMap the nameMap to set
		 */
		public void setNameMap(String nameMap) {
			this.nameMap = nameMap;
		}

		/**
		 * @param quantityMaps the quantityMaps to set
		 */
		public void setQuantityMaps(String quantityMaps) {
			this.quantityMaps = quantityMaps;
		}

		/**
		 * @param oneTimePruchase the oneTimePruchase to set
		 */
		public void setOneTimePruchase(String oneTimePruchase) {
			this.oneTimePruchase = oneTimePruchase;
		}

		/**
		 * @param subscribers the subscribers to set
		 */
		public void setSubscribers(String subscribers) {
			this.subscribers = subscribers;
		}

		/**
		 * @param renewals the renewals to set
		 */
		public void setRenewals(String renewals) {
			this.renewals = renewals;
		}

		/**
		 * @param views the views to set
		 */
		public void setViews(String views) {
			this.views = views;
		}

		/**
		 * @param download the download to set
		 */
		public void setDownload(String download) {
			this.download = download;
		}
		
		
	}

}







