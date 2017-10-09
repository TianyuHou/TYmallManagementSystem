package mall.assistant.deleteStaff;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mall.assistant.alertMaker.alertMaker;
import mall.assistant.database.DatabaseHandler;

public class deleteStaffController implements Initializable {
	
	@FXML
	private JFXTextField searchItem;
	
	@FXML
	private ListView<String> listItem;
	
	@FXML
	private JFXButton deleteItembtn, cancelben;
	
	@FXML
	private AnchorPane rootPane;
	
	DatabaseHandler databaseHandler;
	boolean isSuccess;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
	}
	
	@FXML
	private boolean searchItem() {
		ObservableList<String> listData = FXCollections.observableArrayList();
		String idName = searchItem.getText();
		String qu1= "SELECT * FROM STAFF WHERE staff_id = '"+idName+"' OR staff_name = '"+idName+"'";
		ResultSet rs = databaseHandler.execQuery(qu1);
		try {
			while(rs.next()) {
				String id = rs.getString("staff_id");
				String name = rs.getString("staff_name");
				String position = rs.getString("position");
				String mobile = rs.getString("mobile");
				String email = rs.getString("email");
				
				listData.add("Staff ID : "+id);
				listData.add("Staff Name : "+name);
				listData.add("Staff Position : "+position);
				listData.add("Staff Mobile : "+mobile);
				listData.add("Staff E-mail : "+email);
				isSuccess = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isSuccess) {
			listItem.getItems().setAll(listData);
			isSuccess = false;
			return true;
		}else {
			listData.add("");
			listData.add("");
			listData.add("");
			listData.add("\t\tNo Staff Found, Please Check your Enter!");
			listItem.getItems().setAll(listData);
			return false;
		}
	}
	
	@FXML
	private void deleteItem() {
		if(!searchItem()) {
			alertMaker.showErrorMessage(null, "No Staff has been Found, Delete Failed!");
		}else {
			String idName = searchItem.getText();
			String qu = "DELETE FROM STAFF WHERE staff_name = '"+idName+"' OR staff_id = '"+idName+"'";
			if(databaseHandler.execAction(qu)) {
				alertMaker.showSimpleAlert(null, "The Staff Info has been deleted, Delete Success!");
			}else {
				alertMaker.showErrorMessage(null, "Delete Failed!");
			}
		}
	}
	
	@FXML
	private void cancel() {
		Stage stage = (Stage)rootPane.getScene().getWindow();
		stage.close();
	}

}
