package mall.assistant.addStaff;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mall.assistant.alertMaker.alertMaker;
import mall.assistant.database.DatabaseHandler;
import mall.assistant.staff.Staff;
import mall.assistant.uiMainPage.MainUIController;

public class addStaffController implements Initializable{

	@FXML
	private JFXTextField staffID, staffDep,staffName, staffPosition, mobile, email;
	
	@FXML
	private JFXButton savebtn, cancelbtn;
	
	@FXML
	private AnchorPane rootPane;
	
	private double initx,inity;
	
	DatabaseHandler databaseHandler;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		databaseHandler = DatabaseHandler.getInstance();
	}
	
	@FXML
	private void addStaff(ActionEvent event) {
		String id = staffID.getText();
		String dep = staffDep.getText();
		String name = staffName.getText();
		String mob = mobile.getText();
		String mail = email.getText();
		String pos = staffPosition.getText();
		if(id.isEmpty()||dep.isEmpty() ||name.isEmpty()||mob.isEmpty()||mail.isEmpty()||pos.isEmpty()) {
			alertMaker.showErrorMessage("Error", "Please enter all the fields!");
			return;
		}
		
		String qu = "INSERT INTO STAFF VALUES ("
				  +"'"+id+"',"
				  +"'"+dep+"',"
				  +"'"+name+"',"
				  +"'"+pos+"',"
				  +"'"+mob+"',"
				  +"'"+mail+"')";
		
		System.out.println(qu);
		if(databaseHandler.execAction(qu)) {
			alertMaker.showSimpleAlert("Success", "Operations Success!");
			Staff staff = new Staff(id, name, dep, pos, mob, mail); 
			MainUIController.stafflist.add(staff);
			Stage stage = (Stage)rootPane.getScene().getWindow();
			stage.close();
			return;
		}else {
			alertMaker.showErrorMessage("Failed", "Operations Failed");
			return;
		}
	}
	
	@FXML
	private void cancel() {
		Stage stage = (Stage)rootPane.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void minimizeWindow() {
		Stage stage = (Stage)rootPane.getScene().getWindow();
		stage.setIconified(true);
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
	
	@FXML
	private void exit() {
		Stage stage = (Stage)rootPane.getScene().getWindow();
		stage.close();
	}
	
}
