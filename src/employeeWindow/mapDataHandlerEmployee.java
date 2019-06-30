package employeeWindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import managerWindow.toursEntity;
import managerWindow.siteEntity;;

public class mapDataHandlerEmployee implements Initializable {
	private EmployeeHandler mployeeHandler;

	protected static ChatClient chat = null;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	private ArrayList<ArrayList<String>> m2;
	private int whatchoose;
	private int whatchooseTour;
	private int idTour;
	protected static int idOfCurrentMap;
	private String cityID;
	private ArrayList<ArrayList<Double>> cords;
	protected ArrayList<siteEntity> sites;
	protected ArrayList<toursEntity> tours;
	private ArrayList<Integer> removedTours;
	private ArrayList<siteEntity> sitesInsideTour;
	protected HashMap<Integer, ArrayList<siteEntity>> IdofToursAndSites = new HashMap<Integer, ArrayList<siteEntity>>();
	private static ObservableList<String> sitesInTourResult;
	private static ObservableList<String> sitesResult;
	private static ObservableList<String> tourResult;
	private static ObservableList<String> accessiableCombobox = FXCollections.observableArrayList();
	private boolean chageWasMadeInSiteOfTour = false;
	private boolean canDragPin = false;
	protected static String collection_id;
	private boolean ifNameFiledOfSiteEditeEmpty = true;
	private boolean ifTimeFiledOfSiteEditeEmpty = true;
	private boolean ifDescriptionFiledOfSiteEditeEmpty = true;
	private boolean ifTypeFiledOfSiteEditeEmpty = true;
	protected boolean initializeWithoutDB = true;
	protected boolean initializeSitesWithoutDB = true;
	private GraphicsContext context;
	private Image te = null;
	private Image pin;
	private byte[] imageFromServer;

	@FXML
	private Button deleteTourBtn;

	@FXML
	private Button deleteSiteBTN;

	@FXML
	private ListView<String> SitesListView;

	@FXML
	private TextField descriptionFeild;

	@FXML
	private TextField cityNameField;

	@FXML
	private TextField mapIDField;

	@FXML
	private Button _forQuestionButton;

	@FXML
	private Label _userNameLabel;

	@FXML
	private Button _editPlaceButton;

	@FXML
	private Button _addPlaceButton;

	@FXML
	private Button _addTourButton;

	@FXML
	private Button _editTourButton;

	@FXML
	private Button _addNewMapButton;

	@FXML
	private Canvas imageCanvas;

	@FXML
	private TableView<?> siteTableView;

	@FXML
	private TextField nameSiteField;

	@FXML
	private TextField typeSiteField;

	@FXML
	private TextField timeSiteField;

	@FXML
	private ComboBox<String> accessiableCombo;

	@FXML
	private Label xLocation;

	@FXML
	private Label yLocation;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private Button saveSite;

	@FXML
	private Button _addNewMapButton1;

	@FXML
	private TableColumn<?, ?> nameSiteForTable;

	@FXML
	private TableColumn<?, ?> siteDescriptionForTable;

	@FXML
	private TextArea tourDescription;

	@FXML
	private ListView<String> sitesIntour;

	@FXML
	private Button deleteSiteFromTour;

	@FXML
	private Button saveTourEdit;
	@FXML
	private ImageView backImg;

	@FXML
	void goBackToMain(MouseEvent event) {
		mployeeHandler.openThisWindow();
		Stage stage2 = (Stage) backImg.getScene().getWindow();
		stage2.close();

	}

	@FXML
	void clickAddPlace(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/employeeWindow/AddPlace.fxml"));
			Pane root = loader.load();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			addSiteHandler control = loader.getController();
			control.setEmployeeHandler(this);

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private ListView<String> tourListView;

	@FXML
	void clickAddTour(ActionEvent event) {
System.out.println("collection id: " +collection_id);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/employeeWindow/AddTour.fxml"));
			Pane root = loader.load();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			addTourHandler control = loader.getController();
			control.setMapDataHandlerEmployee(this);

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEmployeeHandler(EmployeeHandler mployeeHandler) {
		this.mployeeHandler = mployeeHandler;
	}

	private static byte[] readBytesFromFile(String filePath) {

		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;

		try {

			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return bytesArray;

	}

	@FXML
	void deleteTourAfterEdit(ActionEvent event) {
		removedTours.add(tours.get(whatchoose).getId());
		tours.remove(whatchoose);
		IdofToursAndSites.remove(idTour);
		initializeWithoutDB = false;

		initializeListViewOfTour();

		tourDescription.setVisible(false);
		sitesIntour.setVisible(false);
		deleteSiteFromTour.setVisible(false);
		saveTourEdit.setVisible(false);
		deleteTourBtn.setVisible(false);

		paintPlaces(context);

	}

	@FXML
	void deleteSite(ActionEvent event) {
		sites.remove(whatchoose);
		initializeSitesWithoutDB=false;
		initializeListViewOfsites();

		canDragPin = false;
		nameSiteField.setVisible(false);
		typeSiteField.setVisible(false);
		accessiableCombo.setVisible(false);
		timeSiteField.setVisible(false);
		descriptionTextArea.setVisible(false);
		xLocation.setVisible(false);
		yLocation.setVisible(false);
		saveSite.setVisible(false);
		deleteSiteBTN.setVisible(false);
		initializeWithoutDB = false;
		justSetImageOfMap();

	}

	private void drag2() {
		System.out.println("te drage2 " + te);
		context.clearRect(0, 0, 386, 386);
		context.drawImage(te, 0, 0);
		System.out.println("size: " + cords.size());
		for (int i = 0; i < cords.size(); i++) {
			context.drawImage(pin, cords.get(i).get(0), cords.get(i).get(1));
		}
	}

	private void drag(Image te, Image pin, double x, double y) {
		context.drawImage(te, 0, 0);
		context.drawImage(pin, x, y);
	}

	public void justSetImageOfMap() {
		context.drawImage(te, 0, 0);
	}

	public void paintPlaces(GraphicsContext context) {

		context.drawImage(te, 0, 0);
		double X = sites.get(whatchoose).getX();
		double Y = sites.get(whatchoose).getY();
		context.drawImage(pin, X, Y);

		imageCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e1) {
				if (canDragPin) {
					drag(te, pin, e1.getX(), e1.getY());
					xLocation.setText(Double.toString(e1.getX()));
					yLocation.setText(Double.toString(e1.getY()));
				}
			}
		});

	}

	@FXML
	void deleteSiteFromTourBtn(ActionEvent event) {
		whatchoose = sitesIntour.getSelectionModel().getSelectedIndex();
		System.out.println("whatchoose: " + whatchoose);
		System.out.println("before" + sitesInsideTour);
		sitesInsideTour.remove(whatchoose);

		insertSiteToTheListOfSpesificTour(sitesInsideTour);
		chageWasMadeInSiteOfTour = true;
		System.out.println("after delete " + sitesInsideTour.size());
	}

	@FXML
	void checkIfChoseSiteInTourList(MouseEvent event) {

		if (tourListView.getSelectionModel().getSelectedIndex() != -1) {
			deleteSiteFromTour.setDisable(false);
		}
	}

	// check if string is doubel
	public static boolean isNumeric(String str) {
		int i = 0;
		for (char c : str.toCharArray()) {
			if (Character.isDigit(c)) {

			} else {
				if (c == '.')
					i++;
				else
					return false;
			}

		}
		if (i > 1)
			return false;
		return true;
	}

	@FXML
	void editSiteBtn(ActionEvent event) {
		// save the index of which site to edit
		whatchoose = SitesListView.getSelectionModel().getSelectedIndex();
		// set the edit fields visible
		nameSiteField.setVisible(true);
		typeSiteField.setVisible(true);
		accessiableCombo.setVisible(true);
		timeSiteField.setVisible(true);
		descriptionTextArea.setVisible(true);
		xLocation.setVisible(true);
		yLocation.setVisible(true);
		saveSite.setVisible(true);
		deleteSiteBTN.setVisible(true);

		tourDescription.setVisible(false);
		sitesIntour.setVisible(false);
		deleteSiteFromTour.setVisible(false);
		saveTourEdit.setVisible(false);
		deleteTourBtn.setVisible(false);

		canDragPin = true;
		// set the details of the site inside the fields
		nameSiteField.setText(sites.get(whatchoose).getName());
		typeSiteField.setText(sites.get(whatchoose).getType());
		accessiableCombo.getSelectionModel().select((sites.get(whatchoose).getAccessiable()));
		timeSiteField.setText(Double.toString(sites.get(whatchoose).getTime()));
		descriptionTextArea.setText(sites.get(whatchoose).getDescription());
		xLocation.setText(Double.toString(sites.get(whatchoose).getX()));
		yLocation.setText(Double.toString(sites.get(whatchoose).getY()));

		// set the image of the map
		// context = imageCanvas.getGraphicsContext2D();
		paintPlaces(context);
	}

	@FXML
	void checkIfChoose(MouseEvent event) {
		if (SitesListView.getSelectionModel().getSelectedIndex() != -1) {
			_editPlaceButton.setDisable(false);
		}

	}

	@FXML
	void checkIfChooseTour(MouseEvent event) {
		if (tourListView.getSelectionModel().getSelectedIndex() != -1) {
			_editTourButton.setDisable(false);

			///////////////////////////////////
		}
	}

	@FXML
	void checkTimeText(KeyEvent event) {
		String time = timeSiteField.getText();
		if (!time.isEmpty() && !isNumeric(time)) {
			saveSite.setDisable(true);
			timeSiteField.getStyleClass().add("error");
			ifTimeFiledOfSiteEditeEmpty = false;
		} else {
			ifTimeFiledOfSiteEditeEmpty = true;
			timeSiteField.getStyleClass().clear();
			timeSiteField.getStyleClass().addAll("text-field", "text-input");
		}

		if (time.isEmpty()) {
			saveSite.setDisable(true);
			timeSiteField.getStyleClass().add("error");
			ifTimeFiledOfSiteEditeEmpty = false;
		}
		if (ifTypeFiledOfSiteEditeEmpty && ifDescriptionFiledOfSiteEditeEmpty && ifTimeFiledOfSiteEditeEmpty
				&& ifNameFiledOfSiteEditeEmpty)
			saveSite.setDisable(false);
		else
			saveSite.setDisable(true);
	}

	@FXML
	void checkSiteFilesIsEmpty(KeyEvent event) {
		String name, description, type;
		name = nameSiteField.getText();
		description = descriptionTextArea.getText();
		type = typeSiteField.getText();

		if (name.isEmpty()) {
			nameSiteField.getStyleClass().add("error");
			ifNameFiledOfSiteEditeEmpty = false;
		} else {
			ifNameFiledOfSiteEditeEmpty = true;
			nameSiteField.getStyleClass().clear();
			nameSiteField.getStyleClass().addAll("text-field", "text-input");
			// set red border
		}

		if (description.isEmpty()) {
			descriptionTextArea.getStyleClass().add("error");
			ifDescriptionFiledOfSiteEditeEmpty = false;
		} else {
			ifDescriptionFiledOfSiteEditeEmpty = true;
			descriptionTextArea.getStyleClass().clear();
			descriptionTextArea.getStyleClass().addAll("text-area", "text-input");
			// red border
		}

		if (type.isEmpty()) {
			typeSiteField.getStyleClass().add("error");
			ifTypeFiledOfSiteEditeEmpty = false;
		} else {
			ifTypeFiledOfSiteEditeEmpty = true;
			typeSiteField.getStyleClass().clear();
			typeSiteField.getStyleClass().addAll("text-field", "text-input");
			// red border
		}
		System.out.println("hey");
		if (ifTypeFiledOfSiteEditeEmpty && ifDescriptionFiledOfSiteEditeEmpty && ifTimeFiledOfSiteEditeEmpty
				&& ifNameFiledOfSiteEditeEmpty)
			saveSite.setDisable(false);
		else
			saveSite.setDisable(true);

	}

	////////////////// check if the fileds not empty and if time is double
	@FXML
	void saveSiteEdit(ActionEvent event) {
		// whatchoose
		String name, description, type, time;
		name = nameSiteField.getText();
		description = descriptionTextArea.getText();
		type = typeSiteField.getText();
		time = timeSiteField.getText();

		sites.get(whatchoose).setName(name);
		sites.get(whatchoose).setAccessiable(accessiableCombo.getSelectionModel().getSelectedIndex());
		sites.get(whatchoose).setDescription(description);
		sites.get(whatchoose).setType(type);
		sites.get(whatchoose).setTime(Double.valueOf(time));
		sites.get(whatchoose).setX(Double.valueOf(xLocation.getText()));
		sites.get(whatchoose).setY(Double.valueOf(yLocation.getText()));
		sites.get(whatchoose).setChange(1);
		// chageWasMade = true;
	}

	@FXML
	void editeTourBtn(ActionEvent event) {
		sitesInTourResult.clear();
		tourDescription.setVisible(true);
		sitesIntour.setVisible(true);
		deleteSiteFromTour.setVisible(true);
		saveTourEdit.setVisible(true);
		deleteTourBtn.setVisible(true);

		canDragPin = false;
		nameSiteField.setVisible(false);
		typeSiteField.setVisible(false);
		accessiableCombo.setVisible(false);
		timeSiteField.setVisible(false);
		descriptionTextArea.setVisible(false);
		xLocation.setVisible(false);
		yLocation.setVisible(false);
		saveSite.setVisible(false);
		deleteSiteBTN.setVisible(false);

		whatchooseTour = tourListView.getSelectionModel().getSelectedIndex();
		cords = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> tempCords;
		int i = 0;
		String idOfTour = Integer.toString(tours.get(whatchooseTour).getId());

		idTour = tours.get(whatchooseTour).getId();

		if (IdofToursAndSites.get(idTour) == null) {
			sql = "SELECT site.id,site.name,site.description FROM (site_tour INNER JOIN site ON site_tour.site_id=site.id) WHERE site_tour.tour_id = '"
					+ idOfTour + "'AND id_collection='"+collection_id+"';";
			sendSQL.clear();
			sendSQL.add("2");
			sendSQL.add(sql);
			mployeeHandler.chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m = mployeeHandler.chat.getArray();
			if (m == null || m.isEmpty()) {
				System.out.println("no result");

			} else {
				// IdofToursAndSites
				sitesInsideTour = new ArrayList<siteEntity>();
				String name;
				String id;
				String description;
				siteEntity temp;

				for (i = 0; i < m.size(); i++) {
					id = m.get(i).get(0);
					name = m.get(i).get(1);
					System.out.println(name);
					description = m.get(i).get(2);
					// create array list of site that in the specific tour
					temp = new siteEntity();
					temp.setID(id);
					temp.setName(name);
					temp.setDescription(description);

					// get the location of each site from the tour
					sql = "SELECT location_x,location_y FROM map_site WHERE map_id='" + mployeeHandler.mapIdForShow
							+ "' AND site_id='" + id + "'AND id_collection='"+collection_id+"';";
					sendSQL.clear();
					sendSQL.add("2");
					sendSQL.add(sql);
					mployeeHandler.chat.handleMessageFromClient(sendSQL);
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m2 = mployeeHandler.chat.getArray();
					// check if there is sites in the tour
					if (m == null || m.isEmpty()) {
						System.out.println("no result");
						// if there are sites in the tour get their locations and put them in the array
						// list of the tours (inside the entity)
					} else {
						String x;
						String y;

						x = m2.get(0).get(0);
						y = m2.get(0).get(1);
						temp.setX(Double.parseDouble(x));
						temp.setY(Double.parseDouble(y));

						// insert the cords into ArrayList
						tempCords = new ArrayList<Double>();
						tempCords.add(Double.parseDouble(x));
						tempCords.add(Double.parseDouble(y));
						cords.add(tempCords);
						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					}
					drag2();
					sitesInsideTour.add(temp);
				}

				IdofToursAndSites.put(idTour, sitesInsideTour);
				System.out.println(cords);
				insertSiteToTheListOfSpesificTour(sitesInsideTour);
			}
		} else {
			insertSiteToTheListOfSpesificTour(IdofToursAndSites.get(idTour));
			ArrayList<siteEntity> temp = new ArrayList<siteEntity>();
			temp = IdofToursAndSites.get(Integer.parseInt(idOfTour));

			for (i = 0; i < temp.size(); i++) {
				tempCords = new ArrayList<Double>();
				tempCords.add(temp.get(i).getX());
				tempCords.add(temp.get(i).getY());
				cords.add(tempCords);
			}
			drag2();
		}

	}

	@FXML
	void saveTourAction(ActionEvent event) {
		if (IdofToursAndSites.get(idTour) == null) {
			IdofToursAndSites.put(idTour, sitesInsideTour);
		} else {
			IdofToursAndSites.remove(idTour);
			IdofToursAndSites.put(idTour, sitesInsideTour);
		}

		chageWasMadeInSiteOfTour = true;
	}

	private void insertSiteToTheListOfSpesificTour(ArrayList<siteEntity> tt) {
		String tempstring;
		String name;
		String description;
		int index = 1;
		sitesIntour.getItems().removeAll(sitesInTourResult);
		for (siteEntity temp : tt) {
			tempstring = new String();
			name = temp.getName();
			description = temp.getDescription();
			if (description.length() >= 10)
				description = description.substring(0, 10);
			tempstring = index + " :  " + name + " -	" + tempstring + " ...";
			sitesInTourResult.add(tempstring);
		}
		sitesIntour.setItems(sitesInTourResult);
	}

	@FXML
	void saveOldVertion(ActionEvent event) {
		// create new collection in DB and set the user how created
		int mapId = mployeeHandler.mapIdForShow;
		// String s = mployeeHandler.idOfSelectedCollection;
		String mapDescription = descriptionFeild.getText();
		String userName = "user";
		double version = Double.parseDouble(mployeeHandler.versionNumber);
		version +=1;
		sql = "INSERT INTO map_collection (vertion,oneTimePrice,approved,subscriptionprice,userName) VALUES ('"+version+"', '0','0','0','"
				+ userName + "');";

		sendSQL.clear();
		sendSQL.add("3");
		sendSQL.add(sql);
		mployeeHandler.chat.handleMessageFromClient(sendSQL);

		try {
			TimeUnit.MILLISECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get the new id of the new collection
		String newIdCoolection = "";
		sql = "SELECT id FROM map_collection ORDER BY ID DESC LIMIT 1;";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		mployeeHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m = mployeeHandler.chat.getArray();
		// check if there is sites in the tour
		if (m == null || m.isEmpty()) {
			System.out.println("no result");
			// if there are sites in the tour get their locations and put them in the array
			// list of the tours (inside the entity)
		} else {
			newIdCoolection = m.get(0).get(0);
		}

		// insert the site with changes
		String idSite;
		String nameSite;
		String type;
		String description;
		int accessible;
		double time;
		double x;
		double y;
		System.out.println("sites: ");
		for (siteEntity sit : sites) {
			System.out.println(sit);
			
			idSite = sit.getID();
			nameSite = sit.getName();
			type = sit.getType();
			description = sit.getDescription();
			accessible = sit.getAccessiable();
			time = sit.getTime();
			x = sit.getX();
			y = sit.getY();
			
			if (sit.getChange() == 1) {
				sendSQL.clear();

				//
				//
				// if change was made insert the site with the changes to new row and get the
				// new id of the site to insert it into the table map_site
				if (sit.getChange() == 1) {
					sql = "INSERT INTO site (name,type,description,time,accessing) VALUES ('" + nameSite + "' , '" + type+ "' , '" + description + "' , '" + time + "' , '" + accessible + "');";
					sendSQL.add("3");
					sendSQL.add(sql);
					mployeeHandler.chat.handleMessageFromClient(sendSQL);
	
					try {
						TimeUnit.MILLISECONDS.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					sql = "SELECT id FROM site ORDER BY ID DESC LIMIT 1;";
					sendSQL.clear();
					sendSQL.add("2");
					sendSQL.add(sql);
					mployeeHandler.chat.handleMessageFromClient(sendSQL);
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m = mployeeHandler.chat.getArray();
					// check if there is sites in the tour
					if (m == null || m.isEmpty()) {
						System.out.println("no result");
						// if there are sites in the tour get their locations and put them in the array
						// list of the tours (inside the entity)
					} else {
						idSite = m.get(0).get(0);
					}
					
				}
			}
				
				
				

				//
				//
				// insert into map_site the site id and map id with the new collection number
				sql = "INSERT INTO map_site (map_id, site_id, location_x,location_y,id_collection) VALUES ('" + mapId
						+ "','" + idSite + "','" + x + "', '" + y + "', '" + newIdCoolection + "');";
				sendSQL.clear();
				sendSQL.add("3");
				sendSQL.add(sql);
				mployeeHandler.chat.handleMessageFromClient(sendSQL);

				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

		// if change was made in the tour
		// delete the sites from the tour
		// for each tour
		if (chageWasMadeInSiteOfTour) {
			HashMap<Integer, ArrayList<siteEntity>> temp = new HashMap<Integer, ArrayList<siteEntity>>();
			String siteId;
			temp = IdofToursAndSites;
			int i = 0;
			int tourId;
			for (Entry<Integer, ArrayList<siteEntity>> entry : IdofToursAndSites.entrySet()) {
				tourId = entry.getKey();
				ArrayList<siteEntity> tab = entry.getValue();
				siteId = tab.get(i++).getID();

				for (int k = 0; k < tours.size(); k++) {
					if (tours.get(i).getId() == tourId) {
						tours.remove(k);
						break;
					}
				}
				sql = "INSERT INTO site_tour (site_id, tour_id, id_collection) VALUES ('" + siteId + "','" + tourId
						+ "','" + newIdCoolection + "')";
				sendSQL.clear();
				sendSQL.add("3");
				sendSQL.add(sql);

				mployeeHandler.chat.handleMessageFromClient(sendSQL);

				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// do something with key and/or tab
			}

		}

		//delete from tours list the tours how removed
		for (int i = 0; i < tours.size(); i++) {
			for (int j = 0; j < removedTours.size(); j++) {
				if (tours.get(i).getId() == removedTours.get(j)) {
					tours.remove(i);
					break;
				}
			}
		}
		System.out.println("mployeeHandler.idOfSelectedCollection: " + mployeeHandler.idOfSelectedCollection);
		for (int i = 0; i < tours.size(); i++) {
			sql = "SELECT site_id,tour_id FROM site_tour WHERE tour_id = '" + tours.get(i).getId()
					+ "' AND id_collection= '" + mployeeHandler.idOfSelectedCollection + "';";
			sendSQL.clear();
			sendSQL.add("2");
			sendSQL.add(sql);
			mployeeHandler.chat.handleMessageFromClient(sendSQL);
			
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m = mployeeHandler.chat.getArray();
			// check if there is sites in the tour
			if (m == null || m.isEmpty()) {
				System.out.println("no result");
			} else {
				String tourId, siteId;
				System.out.println(sql);
				System.out.println(m);
				for (int j = 0; j < m.size(); j++) {
					tourId = m.get(j).get(1);
					siteId = m.get(j).get(0);
					sql = "INSERT INTO site_tour (site_id, tour_id, id_collection) VALUES ('" + siteId + "','" + tourId
							+ "','" + newIdCoolection + "');";
					sendSQL.clear();
					sendSQL.add("3");
					sendSQL.add(sql);
					mployeeHandler.chat.handleMessageFromClient(sendSQL);
					try {
						TimeUnit.MILLISECONDS.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}

		sql = "SELECT id FROM map WHERE id_collaction ='"+mployeeHandler.idOfSelectedCollection+"';";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		mployeeHandler.chat.handleMessageFromClient(sendSQL);
		m = mployeeHandler.chat.getArray();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// check if there is sites in the tour
		if (m == null || m.isEmpty()) {
			System.out.println("no result");
		} else {
			String idOfMap;
			for (int i = 0; i < m.size(); i++) {
				idOfMap = m.get(i).get(0);
				sql = "INSERT INTO map_mapcollection (map_id,collection_id) VALUES ('"+idOfMap+"','"+newIdCoolection+"');";
				sendSQL.clear();
				sendSQL.add("3");
				sendSQL.add(sql);

				mployeeHandler.chat.handleMessageFromClient(sendSQL);

				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

	}

	protected void initializeListViewOfTour() {
		tourResult = FXCollections.observableArrayList();

		if (initializeWithoutDB) {
			sendSQL.clear();
			sendSQL.add("2");
			// sql = "SELECT tours.id, tours.description FROM (map_site INNER JOIN tours ON
			// tours.id = map_site.site_id )WHERE map_site.map_id = '"
			// + managerHandler.mapIdForShow + "';";
			sql = "SELECT tours.id,tours.description,tours.name FROM ((map_site INNER JOIN site_tour ON map_site.site_id = site_tour.site_id) INNER JOIN tours ON site_tour.tour_id = tours.id ) WHERE map_id = '"
					+ mployeeHandler.mapIdForShow + "' AND map_site.id_collection='"+collection_id+"' GROUP BY (tours.id)";

			sendSQL.add(sql);
			mployeeHandler.chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			m = mployeeHandler.chat.getArray();
			if (m == null || m.isEmpty()) {
				System.out.println("no result tours");
			} else {
				tours = new ArrayList<toursEntity>();
				toursEntity temp;
				for (int i = 0; i < m.size(); i++) {
					temp = new toursEntity(Integer.valueOf(m.get(i).get(0)), m.get(i).get(1), m.get(i).get(2));
					tours.add(temp);
				}

			}
		}
		int index = 1;
		String toursListView;
		String tempstring;
		tourResult.clear();
		for (toursEntity tourRes : tours) {
			toursListView = new String();
			tempstring = tourRes.getDescription();
			if (tempstring.length() >= 15)
				tempstring = tempstring.substring(0, 15);
			toursListView = index + ":	" + tourRes.getName() + " -  " + tempstring + " ...";
			tourResult.add(toursListView);
			index++;
		}
		tourListView.setItems(tourResult);

	}

	protected boolean initializeListViewOfsites() {
		if (initializeSitesWithoutDB) {
			String mapIdForShow = String.valueOf(mployeeHandler.mapIdForShow);
			sql = "SELECT city.name, map.description,city.id FROM (map INNER JOIN city ON city.id = map.city_id) WHERE map.id = '"
					+ mapIdForShow + "';";
			sendSQL.clear();
			sendSQL.add("2");
			sendSQL.add(sql);
			mployeeHandler.chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m = mployeeHandler.chat.getArray();
			if (m == null || m.isEmpty()) {
				System.out.println("no result maps is initialize sites");
				return false;

			} else {
				cityID = m.get(0).get(2);
				mapIDField.setText(mapIdForShow);
				cityNameField.setText(m.get(0).get(0));
				descriptionFeild.setText(m.get(0).get(1));
				
				sql = "SELECT site.id,site.name,site.type,site.description,site.accessing,site.time,map_site.location_x,map_site.location_y FROM (site INNER JOIN map_site ON map_site.site_id = site.id) WHERE map_site.Map_id='"
						+ mapIdForShow + "' AND id_collection='"+collection_id+"';";
				sendSQL.clear();
				sendSQL.add("2");
				sendSQL.add(sql);
				mployeeHandler.chat.handleMessageFromClient(sendSQL);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.clear();
				m = mployeeHandler.chat.getArray();
				if (m == null || m.isEmpty()) {
					System.out.println("no result site inside map");
				} else {
					siteEntity temp;
					sites = new ArrayList<siteEntity>();
					for (int i = 0, j; i < m.size(); i++) {
						System.out.println("name: " + m.get(i).get(1));
						temp = new siteEntity();
						temp.setID(m.get(i).get(0));
						temp.setName(m.get(i).get(1));
						temp.setType(m.get(i).get(2));
						temp.setDescription(m.get(i).get(3));
						temp.setAccessiable(Integer.parseInt(m.get(i).get(4)));
						temp.setTime(Double.parseDouble((m.get(i).get(5))));
						temp.setX(Double.parseDouble(m.get(i).get(6)));
						temp.setY(Double.parseDouble(m.get(i).get(7)));
						sites.add(temp);

					}

				}
			}
		}
		String siteListView;
		String tempstring;
		// insert the sites of this map to the list view
		sitesResult.clear();
		int index = 1;
		for (siteEntity siteRes : sites) {
			siteListView = new String();
			tempstring = siteRes.getDescription();
			if (tempstring.length() >= 15)
				tempstring = tempstring.substring(0, 15);
			siteListView = index + ": " + siteRes.getName() + "  -	" + tempstring + " ...";
			sitesResult.add(siteListView);
			index++;

			SitesListView.setItems(sitesResult);
		}
		return true;

	}

	private void getTheRightCollectionID() {
		
		sql = "SELECT map_collection.id FROM (map INNER JOIN map_collection ON map.id_collaction = map_collection.id) WHERE map_collection.approved='1' AND map.id='"+mployeeHandler.mapIdForShow+"' ORDER BY map_collection.id DESC LIMIT 1;";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		mployeeHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m = mployeeHandler.chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("no result site inside map");
		} else {
			collection_id = m.get(0).get(0);					
		}
		
	}
	
	
	@FXML
	void saveNewVertion(ActionEvent event) {
	

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("map id: " + mployeeHandler.mapIdForShow);
		chat = mployeeHandler.chat;
		// set the images for the map and pin for the sites
		context = imageCanvas.getGraphicsContext2D();
		pin = new Image("/managerWindow/pin.png");
		

		// initialize the map of the city
		
		

		sitesInTourResult = FXCollections.observableArrayList();
		sitesResult = FXCollections.observableArrayList();
		tourResult = FXCollections.observableArrayList();
		removedTours = new ArrayList<Integer>();
		idOfCurrentMap = mployeeHandler.mapIdForShow;
		sitesResult.clear();
		accessiableCombobox = FXCollections.observableArrayList("Not accessible", "Accessible");
		accessiableCombo.getItems().addAll(accessiableCombobox);

		// System.out.println(mapIdForShow);
		sendSQL = new ArrayList<Object>();
		getTheRightCollectionID();
		System.out.println("collection id: " + collection_id+"\n\n");

		
		
		String path = EmployeeHandler.paths.get(EmployeeHandler.idOfMap);
		sendSQL.clear();
		sendSQL.add("5");
		sendSQL.add(path);
		
		mployeeHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("path: "+path);
		byte[] bytesArray  = mployeeHandler.chat.returnByteArray();
		
		System.out.println(bytesArray);
		
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytesArray));
			te = SwingFXUtils.toFXImage(img, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		justSetImageOfMap();
		
		
		// check if there are sites in the map
		// if it has look for tours in the map
		// and insert the tours of the map to the list view
		if (initializeListViewOfsites())
			initializeListViewOfTour();

	}
}
