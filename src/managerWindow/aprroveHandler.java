package managerWindow;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class aprroveHandler implements Initializable {
	private ManagerHandler managerHandler;
	private String sql;
	private ArrayList<Object> sendSQL;
	private ArrayList<ArrayList<String>> m;
	private ArrayList<ArrayList<String>> m2;
	private static ObservableList<String> aprroveList;
	private ArrayList<approveRequsts> requestList;
	private int selected;
	private String idCollection;

	@FXML
	private Label preOncePrice;

	@FXML
	private Label preSubscriptionPrice;

	@FXML
	private Label newOncePrice;

	@FXML
	private Label newSubscriptionPrice;

	@FXML
	private ListView<String> listOfrequests;
	 @FXML
	    private Button approveBtn;

	    @FXML
	    private Button rejectBtn;

	public void setManagerHandler(ManagerHandler managerHandler) {
		this.managerHandler = managerHandler;
	}

	private void loadListeToApprove() {
		// get the new price request
		if(selected!=-1)
		listOfrequests.getItems().remove(selected);
		sql = "SELECT priceOnce,subsriptionPrice,collection_id,map_collection.vertion, map_collection.oneTimePrice,map_collection.subscriptionPrice,price_for_approve.id FROM (price_for_approve INNER Join map_collection ON price_for_approve.collection_id = map_collection.id);";
		sendSQL.clear();
		sendSQL.add("2");
		sendSQL.add(sql);
		managerHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		m = managerHandler.chat.getArray();
		if (m == null || m.isEmpty()) {
			System.out.println("no result");
		} else {
			String stringResult;
			String priceOnce, subscriptionPrice, collectionId, vertion, oldOneTimePrice, oldSubscriptionPrice, requestId;
			approveRequsts temp;
			aprroveList.clear();
			for (int i = 0; i < m.size(); i++) {
				priceOnce = m.get(i).get(0);
				subscriptionPrice = m.get(i).get(1);
				collectionId = m.get(i).get(2);
				vertion = m.get(i).get(3);
				oldOneTimePrice = m.get(i).get(4);
				oldSubscriptionPrice = m.get(i).get(5);
				requestId = m.get(i).get(6);
				temp = new approveRequsts(priceOnce, subscriptionPrice, collectionId, vertion, oldOneTimePrice,
						oldSubscriptionPrice,requestId);
				requestList.add(temp);

				stringResult = new String();
				stringResult = "Collection id: " + collectionId;
				aprroveList.add(stringResult);

			}
			rejectBtn.setDisable(true);
			approveBtn.setDisable(true);
			listOfrequests.setItems(aprroveList);
		}
	}

	@FXML
	void getDetailsOfEachRequest(MouseEvent event) {
		selected = listOfrequests.getSelectionModel().getSelectedIndex();
		if (selected != -1) {
			preOncePrice.setText(requestList.get(selected).getOldPriceOnce());
			preSubscriptionPrice.setText(requestList.get(selected).getOldSubscriptionPrice());
			newOncePrice.setText(requestList.get(selected).getPriceOnce());
			newSubscriptionPrice.setText(requestList.get(selected).getSubscriptionPrice());
			idCollection = requestList.get(selected).getCollectionId();
			rejectBtn.setDisable(false);
			approveBtn.setDisable(false);
		}

	}

	@FXML
	void acceptRequest(ActionEvent event) {		
		String OncePrice, SubscriptionPrice;
		OncePrice = requestList.get(selected).getPriceOnce();
		SubscriptionPrice = requestList.get(selected).getOldSubscriptionPrice();
		sql = "UPDATE map_collection SET onetimeprice='"+OncePrice+"', subscriptionPrice='"+SubscriptionPrice+"' WHERE id='"+idCollection+"';";
		sendSQL.clear();
		sendSQL.add("3");
		sendSQL.add(sql);
		managerHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(idCollection);
		String idToRemove = requestList.get(selected).getRequestId();
		sql = "DELETE FROM price_for_approve WHERE id = '"+idToRemove+"';";
		sendSQL.clear();
		sendSQL.add("3");
		sendSQL.add(sql);
		managerHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "The request has been confirmed");
		loadListeToApprove();
	}

	@FXML
	void rejectRequest(ActionEvent event) {
		String idToRemove = requestList.get(selected).getRequestId();
		sql = "DELETE FROM price_for_approve WHERE id = '"+idToRemove+"';";
		sendSQL.clear();
		sendSQL.add("3");
		sendSQL.add(sql);
		managerHandler.chat.handleMessageFromClient(sendSQL);
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "The request has been rejected");
	}
	@FXML
    void backToMain(MouseEvent event) {
		managerHandler.openThisWindow();
		Stage stage2 = (Stage) approveBtn.getScene().getWindow();
		stage2.close();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		aprroveList = FXCollections.observableArrayList();
		sendSQL = new ArrayList<Object>();
		requestList = new ArrayList<approveRequsts>();
		selected=-1;
		loadListeToApprove();
	}

}
