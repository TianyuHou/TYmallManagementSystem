package mall.assistant.addItem;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mall.assistant.Item.Item;
import mall.assistant.alertMaker.alertMaker;
import mall.assistant.database.DatabaseHandler;
import mall.assistant.uiMainPage.MainUIController;

public class addItemController implements Initializable{

	@FXML
	private JFXTextField itemID, itemName, itemCategory, itemPrice, itemInventory;
	
	@FXML
	private JFXButton savebtn, cancelbtn;
	
	DatabaseHandler databaseHandler;
	
	@FXML
	private AnchorPane rootPane;
	
	private double initx,inity;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		
	}
	
	@FXML
	private void addItem(ActionEvent event) {
		String id = itemID.getText();
		String cid = itemCategory.getText();
		String name = itemName.getText();
		String priceStr = itemPrice.getText();
		String inventoryStr = itemInventory.getText();
		String category = null;
		
		if(id.isEmpty() || cid.isEmpty() || name.isEmpty() || priceStr.isEmpty() || inventoryStr.isEmpty()) {
			alertMaker.showErrorMessage("Error", "Please Enter in all fields");
			return;
		}
		double price = Double.parseDouble(itemPrice.getText());
		int inventory = Integer.parseInt(itemInventory.getText());
		
		String qu = "INSERT INTO ITEM VALUES ("
				   +"'"+id+"',"
				   +"'"+cid+"',"
				   +"'"+name+"',"
				   +price+","
				   +inventory+" )";
		
		
		System.out.println(qu);
		
		if(databaseHandler.execAction(qu)) {
			String qu1 = "SELECT * FROM ITEM AS I INNER JOIN CATEGORY AS C ON I.cid = C.category_id WHERE I.item_id='"+id+"'";
			ResultSet rs = databaseHandler.execQuery(qu1);
			try {
				while(rs.next()) {
					category = rs.getString("category_name");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			alertMaker.showSimpleAlert("Success", "Operation Success!");
			Item item = new Item(id,cid,category,name,price,inventory);
			MainUIController.list.add(item);
			Stage stage = (Stage)rootPane.getScene().getWindow();
			stage.close();
		}else {
			alertMaker.showErrorMessage("Failed", "Sorry, Operation Failed!");
		}
		
	}
	
	@FXML
	private void cancel(ActionEvent event) {
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
