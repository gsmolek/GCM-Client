package managerWindow;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import ServerConnection.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class addSiteHandler implements Initializable {

	private mapDataHandler mapHandler = null;
	protected static ChatClient chat = null;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	private boolean save;
	private boolean k=false;

	@FXML
	private TextArea ddescriptionField;

	@FXML
	private CheckBox accessibleArea;

	@FXML
	private TextField nameOfPlaceField;

	@FXML
	private TextField classifictionField;

	@FXML
	private TextField timeField;

	@FXML
	private Button saveBTN;

	@FXML
	private Label errorTimeField;

	public void setMapHandler(mapDataHandler map) {
		this.mapHandler = map;
	}

	// check if the string is double
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
	void saveBTNclick(ActionEvent event) {
		if (save) {

			String name;
			String type;
			String description;
			String time;
			String idOSite = "";

			siteEntity temp = new siteEntity();

			int accessible = 0;
			if (accessibleArea.isSelected())
				accessible = 1;
			name = nameOfPlaceField.getText();
			type = classifictionField.getText();
			description = ddescriptionField.getText();
			time = timeField.getText();

			if (isNumeric(time)) {

				sql = "INSERT INTO site (name,type,description,time,accessing) VALUES ('" + name + "','" + type + "','"
						+ description + "','" + time + "','" + accessible + "');";

				sendSQL.clear();
				sendSQL.add("3");
				sendSQL.add(sql);
				mapHandler.chat.handleMessageFromClient(sendSQL);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				sql = "SELECT id FROM site ORDER BY ID DESC LIMIT 1;";
				sendSQL.clear();
				sendSQL.add("2");
				sendSQL.add(sql);
				mapHandler.chat.handleMessageFromClient(sendSQL);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				m = mapHandler.chat.getArray();
				if (m == null || m.isEmpty()) {
					System.out.println("no result");

				} else {

					idOSite = m.get(0).get(0);
					sql = "INSERT INTO map_site (map_id,site_id,location_x,location_y, id_collection) values ('"
					+ mapDataHandler.idOfCurrentMap + "','" + idOSite + "','0','0','"+mapDataHandler.collection_id+"');";
					sendSQL.clear();
					sendSQL.add("3");
					sendSQL.add(sql);
					mapHandler.chat.handleMessageFromClient(sendSQL);

					JOptionPane.showMessageDialog(null, "The site has been added successfully");
					Stage stage2 = (Stage) saveBTN.getScene().getWindow();
					stage2.close();

					System.out.println("");
				}

				temp.setAccessiable(accessible);
				temp.setDescription(description);
				temp.setName(name);
				temp.setTime(Double.parseDouble(time));
				temp.setID(idOSite);

				mapHandler.sites.add(temp);
				mapHandler.initializeListViewOfsites();
			} else {
				errorTimeField.setVisible(true);
				timeField.setFocusTraversable(true);
				saveBTN.setDisable(true);
			}
		}
	}

	@FXML
	void closeAddSite(ActionEvent event) {
		Stage stage2 = (Stage) saveBTN.getScene().getWindow();
		stage2.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		sendSQL = new ArrayList<Object>();
		save = false;
	}

	@FXML
	void checkIfChanged(KeyEvent event) {
		canSave();
	}

	public void canSave() {
		String name;
		String type;
		String description;
		String time;
		name = nameOfPlaceField.getText();
		type = classifictionField.getText();
		description = ddescriptionField.getText();
		time = timeField.getText();
		if(!time.isEmpty()) {
			time = timeField.getText();
			k = isNumeric(time);
			if (k==false) {
				errorTimeField.setVisible(true);
				saveBTN.setDisable(true);
			}
			else
				errorTimeField.setVisible(false);
		}
		if (!name.isEmpty() && !type.isEmpty() && !description.isEmpty() && !time.isEmpty()) {
			saveBTN.setDisable(false);
			save = true;
		} else {
			save = false;
			saveBTN.setDisable(true);
		}

	}
}
