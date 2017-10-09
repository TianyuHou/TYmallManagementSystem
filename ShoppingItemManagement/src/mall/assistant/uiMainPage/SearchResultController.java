package mall.assistant.uiMainPage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mall.assistant.database.DatabaseHandler;

public class SearchResultController implements Initializable{
	
	@FXML
	private JFXButton searchClosebtn;
	
	@FXML
	private AnchorPane SearchResult;
	
	@FXML
	private Label itemid;
	@FXML
	private Label itemname;
	@FXML
	private Label itemcategory;
	@FXML
	private Label itemprice;
	@FXML
	private Label iteminventory;
	@FXML
	private Label searchresultlabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void closeWindow() {
		Stage stage = (Stage)SearchResult.getScene().getWindow();
		stage.close();
	}
	
	
	public void search(String str,DatabaseHandler databaseHandler) {
		boolean isSuccess = false;
		String idName = str;
		String qu = "SELECT * FROM ITEM WHERE item_id ='"+idName+"' OR item_name ='"+idName+"'";
		ResultSet rs = databaseHandler.execQuery(qu);
		try {
			while(rs.next()) {
				itemid.setText(rs.getString("item_id"));
				itemname.setText(rs.getString("item_name"));
				String qu1 = "SELECT category_name FROM CATEGORY WHERE category_id = '"+rs.getString("cid")+"'";
				ResultSet rsc = databaseHandler.execQuery(qu1);
				while(rsc.next()) {
					itemcategory.setText(rsc.getString("category_name"));
				}
				itemprice.setText(String.valueOf(rs.getDouble("price")));
				iteminventory.setText(String.valueOf(rs.getInt("inventory")));
				isSuccess = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isSuccess) {
			searchresultlabel.setText("Search Success");
			isSuccess = false;
		}else {
			searchresultlabel.setText("No Item Found");
		}
	}

}
