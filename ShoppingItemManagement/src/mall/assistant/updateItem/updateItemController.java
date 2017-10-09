package mall.assistant.updateItem;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mall.assistant.Item.Item;
import mall.assistant.alertMaker.alertMaker;
import mall.assistant.database.DatabaseHandler;
import mall.assistant.uiMainPage.MainUIController;

public class updateItemController implements Initializable{
	
	@FXML
	private JFXTextField itemID, itemName, itemPrice,itemCid,itemInventory , searchItem;
	
	@FXML
	private JFXButton updatebtn, cancelbtn;
	
	@FXML
	private AnchorPane rootPane;
	
	DatabaseHandler databaseHandler;
	
	private double initx,inity;
	
	private int index;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		databaseHandler = DatabaseHandler.getInstance();
	}
	
	public void searchItem(String id, String name, String cid,double price,int inventory,int index) {
		clear();
		itemID.setText(id);
		itemName.setText(name);
		itemCid.setText(cid);
		itemPrice.setText(String.valueOf(price));
		itemInventory.setText(String.valueOf(inventory));
		this.index = index;
	}
	
	
	@FXML
	private void update() {
		String id = itemID.getText();
		String name = itemName.getText();
		String cid = itemCid.getText();
		String price = itemPrice.getText();
		String inventory = itemInventory.getText();
		String category = null;
		
		if(id.isEmpty() || name.isEmpty() || cid.isEmpty() || price.isEmpty() || inventory.isEmpty()) {
			alertMaker.showErrorMessage(null, "Please Enter All The Field, Update Failed! ");
			return;
		}
		
		String qu = "UPDATE ITEM SET item_id = '"+id+"',"
								 +"item_name = '"+name+"',"
								 +"cid = '"+ cid+ "',"
								 +"price = "+Double.parseDouble(price)+","
								 +"inventory = "+Integer.parseInt(inventory)+" "
								 +"WHERE item_id = '"+id+"'";
		
		if(databaseHandler.execAction(qu)) {
			String qu1 = "SELECT * FROM CATEGORY WHERE category_id = '"+cid+"'";
			ResultSet rs = databaseHandler.execQuery(qu1);
			try {
				while(rs.next()) {
					category = rs.getString("category_name");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(category);
			alertMaker.showSimpleAlert(null, "Update Success");
			Item item = new Item(id, cid, category, name, Double.parseDouble(price), Integer.parseInt(inventory));
			MainUIController.list.set(index,item);
			Stage stage = (Stage)rootPane.getScene().getWindow();
			stage.close();
		}else {
			alertMaker.showErrorMessage(null, "No item has been changed, Update Failed");
		}
	}
	
	
	@FXML
	private void clear() {
		itemID.setText("");
		itemName.setText("");
		itemPrice.setText("");
		itemCid.setText("");
		itemInventory.setText("");
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
