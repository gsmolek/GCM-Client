package clientWindow;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;


import javax.imageio.ImageIO;

import managerWindow.siteEntity;
import managerWindow.toursEntity;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

public class WatchMapHandler implements Initializable {
	private ClientWindowHandler clientWindowHandler;
	protected static ChatClient chat = null;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	private static ObservableList<String> mapList;
	private static ObservableList<String> siteList;
	private static ObservableList<String> tourList;
	private ArrayList<String> paths;
	private ArrayList<String> maps;
	private ArrayList<siteEntity> sites;
	private ArrayList<String> tours;
	private ArrayList<toursEntity> listOftours;
	private String idCollection;
	private String idCollection2;
	private String mapID;
	private ArrayList<ArrayList<Double>> cords;
	private int choiseOfSite;
	private String result;
	private GraphicsContext context;
	private GraphicsContext behind;
	private Image te = null;
	private Image pin;
	private Stage stage2;
	private byte[] imageFromServer;
	private int choseMap;
	
    @FXML
    private Canvas behinde;

    @FXML
    private Canvas igmageShow;

    @FXML
    private Label resultField;
    
	@FXML
	private ListView<String> mapListView;

	@FXML
	private ListView<String> placesListView;

    @FXML
    private ListView<String> toursListView;
    
    @FXML
    private Button downloadBTN;

	public void setClientWindowHandler(ClientWindowHandler clientWindowHandler, String id) {
		this.clientWindowHandler = clientWindowHandler;
		chat = clientWindowHandler.chat;
		idCollection = clientWindowHandler.idCollection;
		idCollection2 = id;
		
		stage2 = (Stage) resultField.getScene().getWindow();
		if (setListOfMaps()) {
			System.out.println("id collection: "+idCollection);
			
		}

		// clientWindowHandler.idCollection
	}

	private boolean setListOfMaps() {
		mapList.clear();
		sql = "SELECT map.path,map.description,map.id FROM (map_mapcollection INNER JOIN map ON map.id = map_mapcollection.map_id) WHERE collection_id='"
				+ idCollection + "';";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		m = chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("1");
			return false;
		} else {
			String res, description;
			System.out.println(m);
			for (int i = 0; i < m.size(); i++) {
				description = m.get(i).get(1);
				if (description.length() >= 15)
					description = description.substring(0, 15);
				res = (i + 1) + " " + description;
				maps.add(m.get(i).get(2));
				paths.add(m.get(i).get(0));
				mapList.add(res);
				res = new String();
			}
			mapListView.setItems(mapList);
		}
		return true;
	}

	private boolean setPlacesList() {
		siteList.clear();
		sql = "SELECT map_site.location_x,map_site.location_y, site.name,site.type,site.description,site.accessing, site.time,site.id FROM (map_site INNER JOIN site ON site.id = map_site.site_id) WHERE map_site.map_id ='"
				+ mapID + "' AND id_collection='" + idCollection + "';";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		m = chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("2");
			return false;
		} else {
			ArrayList<Double> temp;
			siteEntity tempSite;
			String x, y, name, type, description, accessible, time, id, res;
			for (int i = 0; i < m.size(); i++) {
				temp = new ArrayList<Double>();
				x = m.get(i).get(0);
				y = m.get(i).get(1);
				temp.add(Double.parseDouble(x));
				temp.add(Double.parseDouble(y));
				cords.add(temp);

				name = m.get(i).get(2);
				description = m.get(i).get(4);
				type = m.get(i).get(3);
				accessible = m.get(i).get(5);
				time = m.get(i).get(6);
				id = m.get(i).get(7);

				tempSite = new siteEntity();
				tempSite.setName(name);
				tempSite.setDescription(description);
				tempSite.setAccessiable(Integer.parseInt(accessible));
				tempSite.setTime(Double.parseDouble(time));
				tempSite.setType(type);
				tempSite.setID(id);

				sites.add(tempSite);
				
				res = new String();
				if (description.length() >= 15)
					description = description.substring(0, 15);
				res = (i + 1) + ". " + name + ": " + description;

				siteList.add(res);
			}
			placesListView.setItems(siteList);
		}
		return true;
		// placesListView
	}

	private boolean searchForToursid() {
		String id, temp;
		// run on each site that the map contain and search in which tour it contain
		if(sites.isEmpty())
			return false;
		for (siteEntity sit : sites) {
			id = sit.getID();
			sql = "SELECT tour_id From site_tour where site_id='" + id + "' AND id_collection='" + idCollection + "';";
			sendSQL.clear();
			sendSQL.add("2");
			sendSQL.add(sql);
			chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m = chat.getArray();
			if (m == null || m.isEmpty()) {
				System.out.println("3");
			} else {
				for (int i = 0; i < m.size(); i++) {
					temp = m.get(i).get(0);
					// check if the tour id already insert into the tours arrayList
					// if not add the id to the tours list
					if (!tours.contains(temp))
						tours.add(m.get(i).get(0));
				}
			}

		}
		if(tours.isEmpty())
			return false;
		System.out.println("search tours id: "+ tours);
		return true;
	}

	private void setTourList() {
		tourList.clear();
		String tourId;
		for (int i = 0; i < tours.size(); i++) {
			tourId = tours.get(i);
			sql = "SELECT id,description,name FROM tours WHERE id='" + tourId + "';";
			sendSQL.clear();
			sendSQL.add("2");
			sendSQL.add(sql);
			chat.handleMessageFromClient(sendSQL);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			m = chat.getArray();
			if (m == null || m.isEmpty()) {
				System.out.println("4");
			} else {
				toursEntity temp;
				String id, name, description, res;
				for (int k = 0; k < m.size(); k++) {
					id = m.get(k).get(0);
					description = m.get(k).get(1);
					name = m.get(k).get(2);

					temp = new toursEntity(Integer.parseInt(id), description, name);
					listOftours.add(temp);
					
					res = new String();
					if (description.length() >= 15)
						description = description.substring(0, 15);
					res = (k + 1) + ". " + name + " - " + description;
					System.out.println(res);
					tourList.add(res);
				}
			}
		}
		
		if (!tourList.isEmpty())
			toursListView.setItems(tourList);   

	}

	private void dragImageWithCords() {
		System.out.println("te drage2 " + te);
		context.clearRect(0, 0, 386, 386);
		context.drawImage(te, 0, 0);
		
		behind.clearRect(0, 0, 386, 386);
		behind.drawImage(te, 0, 0);
		
		System.out.println("size: " + cords.size());
		for (int i = 0; i < cords.size(); i++) {
			context.drawImage(pin, cords.get(i).get(0), cords.get(i).get(1));
			behind.drawImage(pin, cords.get(i).get(0), cords.get(i).get(1));
		}
	}
	
	@FXML
	void getMapId(MouseEvent event) {
		if (mapListView.getSelectionModel().getSelectedIndex() != -1) {
			mapID = maps.get(mapListView.getSelectionModel().getSelectedIndex());
			setPlacesList();
			searchForToursid();
			choseMap = mapListView.getSelectionModel().getSelectedIndex();
			setTourList();
			LoadImage();
			dragImageWithCords();
			downloadBTN.setDisable(false);
			insertViews();
		}
	}
	
    @FXML
    void goBack(MouseEvent event) {

    	clientWindowHandler.openThisWindow();
    	
    	stage2 = (Stage) mapListView.getScene().getWindow();
		stage2.close();
    }
    
    @FXML
    void showDetails(MouseEvent event) {
    	choiseOfSite = placesListView.getSelectionModel().getSelectedIndex();
		if (choiseOfSite != -1) {
			resultField.setText("");
			siteEntity temp = sites.get(choiseOfSite);
			result = "Name: " + temp.getName() + "\n";
			result += "Type: " + temp.getType() +"\n";
			if(temp.getAccessiable()==1)
				result += "Accesible : Yes";
			else
				result += "Accesible : No";
			result += "\nDesscription: " + temp.getDescription() +"\n";
			resultField.setText(result);
		}
		else
			resultField.setText("");
		
    }


    private void LoadImage() {
    	
    	
    	String mapPath = paths.get(choseMap);
    	sendSQL.clear();
    	sendSQL.add("5");
    	sendSQL.add(mapPath);
    	System.out.println("path: "+mapPath);
    	chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		imageFromServer = chat.returnByteArray();
    	
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageFromServer));
			te = SwingFXUtils.toFXImage(img, null);
			
			//justSetImageOfMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    
    public void justSetImageOfMap() {
		context.drawImage(te, 0, 0);
	}
    
    @FXML
    void saveCanvas(ActionEvent event) {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter clockTime = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		
		
		
        sql = "INSERT INTO download (date, user_id, collaction_id, time) VALUES ('"+ dtf.format(now)+"', '"+idCollection2+"','"+ClientWindowHandler.user_id+"' , '"+clockTime.format(now)+"');";
        System.out.println(sql);
		sendSQL.clear();
		sendSQL.add("3");
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
				
    	FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
       
        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage2);        
        
        if(file != null){
        	System.out.println("in if");
            try {
                WritableImage writableImage = new WritableImage(386, 386);
                behinde.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
                System.out.println("in try");
            } catch (IOException ex) {
                Logger.getLogger(WatchMapHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
    private void insertViews() {

    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	        DateTimeFormatter clockTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    			LocalDateTime now = LocalDateTime.now();
    			
    			
    	
    	sql = "INSERT INTO views (user_id,collaction_id,date,time,map_id) VALUES ('"+ClientWindowHandler.user_id+"', '"+idCollection2+"', '"+dtf.format(now)+"','"+clockTime.format(now)+"','"+mapID+"');";
    	sendSQL.clear();
    	sendSQL.add("3");
		sendSQL.add(sql);
		chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
    }
    
	//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		sendSQL = new ArrayList<Object>();
		mapList = FXCollections.observableArrayList();
		siteList = FXCollections.observableArrayList();
		tourList = FXCollections.observableArrayList();
		maps = new ArrayList<String>();
		cords = new ArrayList<ArrayList<Double>>();
		sites = new ArrayList<siteEntity>();
		paths = new ArrayList<String>();
		tours = new ArrayList<String>();
		listOftours = new ArrayList<toursEntity>();
		context = igmageShow.getGraphicsContext2D();
		behind = behinde.getGraphicsContext2D();
		pin = new Image("/managerWindow/pin.png");
		
		
	}

}
