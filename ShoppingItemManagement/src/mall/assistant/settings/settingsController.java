package mall.assistant.settings;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mall.assistant.alertMaker.alertMaker;

public class settingsController implements Initializable{

	@FXML
	JFXTextField username, password, password2;
	
	@FXML
	JFXButton savebtn;
	
	@FXML
	AnchorPane rootPane;
	
	double initx,inity;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDefault();
	}
	
	@FXML
	private void save() {
		String uname = username.getText();
		String pword = password.getText();
		String pword2 = password2.getText();
		
		Preferences preferences = Preferences.getPreferences();
		if(pword.equals(pword2)) {
			preferences.setUsername(uname);
			preferences.setPassword(pword);
			Preferences.writeToGson(preferences);
		}else {
			alertMaker.showErrorMessage(null, "The second password you entered is not same as the first");
			return;
		}
	}
	
	@FXML
	private void initDefault() {
		username.setText("");
		password.setText("");
		password2.setText("");
	}
	
	@FXML
	private void close() {
		Stage stage = (Stage)savebtn.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void movePressed(MouseEvent evt) {
		initx = evt.getSceneX();
		inity = evt.getSceneY();
	}
	
	@FXML
	private void moveDragged(MouseEvent evt) {
		Stage stage = (Stage)rootPane.getScene().getWindow();
		stage.setX(evt.getScreenX()-initx);
		stage.setY(evt.getScreenY()-inity);
	}
	

}
