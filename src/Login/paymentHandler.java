package Login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ServerConnection.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class paymentHandler implements Initializable {
	private ObservableList<String> listMounth;
	private ObservableList<String> listYear;
	private RegistrationHandler regHandler;

	@FXML
	private Button saveBtn;

	@FXML
	private ImageView _cvvImage;

	@FXML
	private TextField cvvfieldNewfield;

	@FXML
	private ComboBox<String> _yearComboBox;

	@FXML
	private ComboBox<String> _monthComboBox;

	@FXML
	private TextField cardNumberField;

	@FXML
	private TextField cvvField;

	public void setRegistrationHandler(RegistrationHandler regHandler) {
		this.regHandler = regHandler;
	}

	@FXML
	void cvvShow() {
		_cvvImage.setVisible(true);
	}

	@FXML
	void cvvUnshow() {
		_cvvImage.setVisible(false);
	}

	@FXML
	private TextField cardnumber;

	@FXML
	private ImageView visaIMG;

    @FXML
    void cancelPayment(ActionEvent event) {
		System.out.println("asfasdfasf");
		Stage stage2 = (Stage) visaIMG.getScene().getWindow();
		stage2.close();
	}

	@FXML
	void savePayment(ActionEvent event) {
		System.out.println("123456");
		String cvv, cardNumber;
		cardNumber = cardNumberField.getText();
		int exM = _monthComboBox.getSelectionModel().selectedIndexProperty().intValue();
		int exY = _yearComboBox.getSelectionModel().selectedIndexProperty().intValue();
		cvv = cvvField.getText();
		// insert the payment details to the class visa
		if (!cardNumber.isEmpty())
			regHandler.s.setCard(Integer.parseInt(cardNumber));
		else{
			regHandler.s.setCard(0);
		}
		if (exM != -1) {
			regHandler.s.setExM(exM + 1);
		}
		else
			regHandler.s.setExM(0);
		if (exY != -1) {
			regHandler.s.setExY(exY + 2019);
		}
		else{
			regHandler.s.setExY(0);
		}
		if (!cvv.isEmpty()) {
			regHandler.s.setCvv(Integer.valueOf(cvv));
		}
		else {
			regHandler.s.setCvv(0);
		}

		Stage stage2 = (Stage) saveBtn.getScene().getWindow();
		stage2.close();
	}

	private void initializeFileds() {
		if (regHandler.s.getCard() != 0) 
			cardNumberField.setText(String.valueOf(regHandler.s.getCard()));
		
		if(regHandler.s.getExM() != 0)	
			_monthComboBox.getSelectionModel().select(regHandler.s.getExM() - 1);
		if(regHandler.s.getExY() != 0)
			_yearComboBox.getSelectionModel().select(regHandler.s.getExY() - 2019);
		if(regHandler.s.getCvv() != 0)
			cvvField.setText(String.valueOf(regHandler.s.getCvv()));
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		listMounth = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
				"12");
		listYear = FXCollections.observableArrayList("2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026",
				"2027", "2028", "2029", "2030");

		_yearComboBox.setItems(listYear);
		_monthComboBox.setItems(listMounth);

		initializeFileds();

	}

}
