package mall.assistant.updateStaff;

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
import mall.assistant.database.DatabaseHandler;
import mall.assistant.staff.Staff;
import mall.assistant.uiMainPage.MainUIController;

public class updateStaffController implements Initializable{
	
	@FXML
	private JFXTextField staffid,staffdep,staffname, staffposition,staffmobile,staffemail, searchItem;
	
	@FXML
	private JFXButton updatebtn, cancelbtn;
	
	@FXML
	private AnchorPane rootPane;
	
	DatabaseHandler databaseHandler;
	
	double initx,inity;
	
	int index;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		databaseHandler = DatabaseHandler.getInstance();
	}
	
	public void searchStaff(String id, String dep, String name, String position, String mobile, String email,int index) {
		staffid.setText(id);
		staffdep.setText(dep);
		staffname.setText(name);
		staffposition.setText(position);
		staffmobile.setText(mobile);
		staffemail.setText(email);
		this.index=index;
	}

	@FXML
	private void update() {
		String id = staffid.getText();
		String dep = staffdep.getText();
		String name = staffname.getText();
		String position = staffposition.getText();
		String mobile = staffmobile.getText();
		String email = staffemail.getText();
		
		if(id.isEmpty() || dep.isEmpty() ||name.isEmpty() || position.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
			alertMaker.showErrorMessage(null, "Please Enter All The Field, Update Failed! ");
			return;
		}
		
		String qu = "UPDATE STAFF SET staff_id = '"+id+"',"
								 +"department = '"+dep+"',"
								 +"staff_name = '"+name+"',"
								 +"position = '"+ position+ "',"
								 +"mobile = '"+mobile+"',"
								 +"email = '"+email+"' "
								 +"WHERE staff_id = '"+id+"'";
		if(databaseHandler.execAction(qu)) {
			alertMaker.showSimpleAlert(null, "Update Success");
			Staff staff = new Staff(id, name, dep, position, mobile, email);
			MainUIController.stafflist.set(index,staff);
			Stage stage = (Stage)rootPane.getScene().getWindow();
			stage.close();
		}else {
			alertMaker.showErrorMessage(null, "No Staff has been changed, Update Failed");
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
