package mall.assistant.uiMainPage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mall.assistant.Item.Item;
import mall.assistant.alertMaker.alertMaker;
import mall.assistant.database.DatabaseHandler;
import mall.assistant.staff.Staff;
import mall.assistant.updateItem.updateItemController;
import mall.assistant.updateStaff.updateStaffController;

public class MainUIController implements Initializable {

	double initx, inity;

	DatabaseHandler databaseHandler;

	@FXML
	private JFXButton item_btn, staff_btn, profile_btn;

	@FXML
	private AnchorPane h_item, h_staff, h_profile;
	
	//Profile Page
	@FXML
	private JFXDatePicker datepicker;
	
	@FXML
	private JFXTimePicker timepicker;
	
	@FXML
	private JFXTextField condate, contime, conlocation;
	
	@FXML
	private JFXTextArea condescription;
	// Item Page

	@FXML
	private JFXTextField filter;

	@FXML
	private TableView<Item> tableview;

	@FXML
	private TableColumn<Item, String> tableid;

	@FXML
	private TableColumn<Item, String> tablepic;

	@FXML
	private TableColumn<Item, String> tablecategory;

	@FXML
	private TableColumn<Item, String> tablename;

	@FXML
	private TableColumn<Item, Integer> tableinventory;

	@FXML
	private TableColumn<Item, Double> tableprice;

	@FXML
	private AnchorPane root;

	@FXML
	private JFXTextField searchText;

	public static ObservableList<Item> list = FXCollections.observableArrayList();

	// Staff Page
	@FXML
	private JFXTextField filterStaff;

	@FXML
	private TableView<Staff> tableviewStaff;

	@FXML
	private TableColumn<Staff, String> tablestaffid;

	@FXML
	private TableColumn<Staff, String> tablestaffdep;

	@FXML
	private TableColumn<Staff, String> tablestaffname;

	@FXML
	private TableColumn<Staff, String> tablestaffposition;

	@FXML
	private TableColumn<Staff, String> tablestaffmobile;

	@FXML
	private TableColumn<Staff, String> tablestaffemail;

	@FXML
	private JFXTextField searchstaffText;

	public static ObservableList<Staff> stafflist = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//init datepicker;
		/*String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		datepicker.setValue(localDate);*/
		condate.setEditable(false);
		contime.setEditable(false);
		conlocation.setEditable(false);
		condescription.setEditable(false);
		datepicker.setValue(LocalDate.now());
		
		//init timepicker;
		timepicker.setValue(LocalTime.now());
		
		initCol();
		loadData();
		filter.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				filter(oldValue, newValue);
			}
		});

		searchText.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				showSearch(oldValue, newValue);
			}
		});

		initStaffCol();
		loadStaffData();
		
		searchstaffText.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				showStaffSearch(oldValue, newValue);
			}
		});
		
		filterStaff.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				filterStaff(oldValue, newValue);
			}
		});
	}
	
	private void initCol() {
		tableview.setPlaceholder(new Label("No Result To Display!"));
		tableid.setCellValueFactory(new PropertyValueFactory<>("id"));
		tablepic.setCellValueFactory(new PropertyValueFactory<>("cid"));
		tablecategory.setCellValueFactory(new PropertyValueFactory<>("category"));
		tablename.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableinventory.setCellValueFactory(new PropertyValueFactory<>("inventory"));
		tableprice.setCellValueFactory(new PropertyValueFactory<>("price"));
	}

	private void initStaffCol() {
		tableviewStaff.setPlaceholder(new Label("No Result To Display!"));
		tablestaffid.setCellValueFactory(new PropertyValueFactory<>("id"));
		tablestaffdep.setCellValueFactory(new PropertyValueFactory<>("department"));
		tablestaffname.setCellValueFactory(new PropertyValueFactory<>("name"));
		tablestaffposition.setCellValueFactory(new PropertyValueFactory<>("position"));
		tablestaffmobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		tablestaffemail.setCellValueFactory(new PropertyValueFactory<>("email"));
	}

	private void loadData() {
		databaseHandler = DatabaseHandler.getInstance();
		String qu = "SELECT * FROM ITEM i,CATEGORY c WHERE i.cid = c.category_id";
		ResultSet rs = databaseHandler.execQuery(qu);
		try {
			while (rs.next()) {
				String id = rs.getString("item_id");
				String pic = rs.getString("cid");
				String category = rs.getString("category_name");
				String name = rs.getString("item_name");
				double price = rs.getDouble("price");
				int inventory = rs.getInt("inventory");

				list.add(new Item(id, pic, category, name, price, inventory));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableview.getItems().setAll(list);
	}

	private void loadStaffData() {
		databaseHandler = DatabaseHandler.getInstance();
		String qu = "SELECT * FROM STAFF";
		ResultSet rs = databaseHandler.execQuery(qu);
		try {
			while (rs.next()) {
				String id = rs.getString("staff_id");
				String dep = rs.getString("department");
				String name = rs.getString("staff_name");
				String position = rs.getString("position");
				String mobile = rs.getString("mobile");
				String email = rs.getString("email");

				stafflist.add(new Staff(id, dep, name, position, mobile, email));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableviewStaff.getItems().setAll(stafflist);
	}

	/*
	 * @FXML private void loadDeleteItem() { loadWindow("\tDelete Item",
	 * "..\\deleteItem\\deleteItem.fxml", "..\\deleteItem\\deleteItem.css"); }
	 */

	@FXML
	private void loadSettings() {
		loadWindow("\tSettings", "mall/assistant/settings/settings.fxml", "mall/assistant/settings/settings.css");
	}

	@FXML
	private void deleteButtonClicked() {
		ObservableList<Item> productSelected, allProducts;
		allProducts = tableview.getItems();
		productSelected = tableview.getSelectionModel().getSelectedItems();
		int index = tableview.getSelectionModel().getSelectedIndex();

		if (index >= 0 && index < list.size()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Delete Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure want to delete this item?");

			Optional<ButtonType> response = alert.showAndWait();

			if (response.get() == ButtonType.OK) {
				for (Item i : productSelected) {
					String id = i.getId();
					String qu = "DELETE FROM ITEM WHERE item_id = '" + id + "'";
					if (databaseHandler.execAction(qu)) {
						alertMaker.showSimpleAlert(null, "Delete Success!");
					} else {
						alertMaker.showErrorMessage(null, "Dlete Failed!");
					}
					allProducts.remove(i);
					list.remove(i);
				}
			}
		} else {
			alertMaker.showSimpleAlert(null, "Please Select An Item To Delete!");
		}
	}

	@FXML
	private void loadDeleteStaff() {
		ObservableList<Staff> productSelected, allProducts;
		allProducts = tableviewStaff.getItems();
		productSelected = tableviewStaff.getSelectionModel().getSelectedItems();
		int index = tableviewStaff.getSelectionModel().getSelectedIndex();

		if (index >= 0 && index < stafflist.size()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Delete Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure want to delete this staff info?");

			Optional<ButtonType> response = alert.showAndWait();

			if (response.get() == ButtonType.OK) {
				for (Staff i : productSelected) {
					String id = i.getId();
					String qu = "DELETE FROM STAFF WHERE staff_id = '" + id + "'";
					if (databaseHandler.execAction(qu)) {
						alertMaker.showSimpleAlert(null, "Delete Success!");
					} else {
						alertMaker.showErrorMessage(null, "Dlete Failed!");
					}
					allProducts.remove(i);
					stafflist.remove(i);
				}
			}
		} else {
			alertMaker.showSimpleAlert(null, "Please Select An Item To Delete!");
		}
	}

	@FXML
	private void loadAddItem() {
		// loadWindow("\tAdd Item", "..\\addItem\\AddItem.fxml",
		// "..\\addItem\\additem.css");
		Item temp = list.size() > 0 ? list.get(list.size() - 1):null;
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getClassLoader().getResource("mall/assistant/addItem/AddItem.fxml"));
		try {
			Loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		Scene scene = new Scene(p);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("mall/assistant/addItem/additem.css").toExternalForm());

		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Add Item");
		stage.getIcons().add(new Image("/Image/windows.png"));
		stage.showAndWait();
		if (temp == null || !temp.getId().equals(list.get(list.size() - 1).getId())) {
			tableview.getItems().add(list.size() - 1, list.get(list.size() - 1));
		}
	}

	@FXML
	private void loadAddStaff() {
		Staff temp = stafflist.size()>0? stafflist.get(stafflist.size()-1) : null;
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getClassLoader().getResource("mall/assistant/addStaff/AddStaff.fxml"));
		try {
			Loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		Scene scene = new Scene(p);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("mall/assistant/addItem/additem.css").toExternalForm());

		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("\tAdd Staff");
		stage.getIcons().add(new Image("/Image/windows.png"));
		stage.showAndWait();
		if (temp == null || !temp.getId().equals(stafflist.get(stafflist.size() - 1).getId())) {
			tableviewStaff.getItems().add(stafflist.size() - 1, stafflist.get(stafflist.size() - 1));
		}
	}

	@FXML
	private void loadUpdateItem() {
		ObservableList<Item> productSelected = tableview.getSelectionModel().getSelectedItems();
		int index = tableview.getSelectionModel().getSelectedIndex();
		if (index >= 0 && index < list.size()) {
			String id = productSelected.get(0).getId();
			String name = productSelected.get(0).getName();
			String cid = productSelected.get(0).getCid();
			double price = productSelected.get(0).getPrice();
			int inventory = productSelected.get(0).getInventory();
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getClassLoader().getResource("mall/assistant/updateItem/updateItem.fxml"));
			try {
				Loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			updateItemController uic = Loader.getController();
			uic.searchItem(id, name, cid, price, inventory, index);
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			Scene scene = new Scene(p);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("mall/assistant/updateItem/updateItem.css").toExternalForm());

			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Update Item");
			stage.getIcons().add(new Image("/Image/windows.png"));
			stage.showAndWait();
			tableview.getItems().set(index, list.get(index));
		} else {
			alertMaker.showSimpleAlert(null, "Please Select An Item To Update!");
		}
	}

	@FXML
	private void loadUpdateStaff() {
		ObservableList<Staff> productSelected = tableviewStaff.getSelectionModel().getSelectedItems();
		int index = tableviewStaff.getSelectionModel().getSelectedIndex();
		if (index >= 0 && index < stafflist.size()) {
			String id = productSelected.get(0).getId();
			String dep = productSelected.get(0).getDepartment();
			String name = productSelected.get(0).getName();
			String position = productSelected.get(0).getPosition();
			String mobile = productSelected.get(0).getMobile();
			String email = productSelected.get(0).getEmail();
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getClassLoader().getResource("mall/assistant/updateStaff/updateStaff.fxml"));
			try {
				Loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			updateStaffController usc = Loader.getController();
			usc.searchStaff(id, dep, name, position, mobile, email, index);
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			Scene scene = new Scene(p);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("mall/assistant/updateStaff/updateItem.css").toExternalForm());

			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("\tUpdate Staff");
			stage.getIcons().add(new Image("/Image/windows.png"));
			stage.showAndWait();
			tableviewStaff.getItems().set(index, stafflist.get(index));
		} else {
			alertMaker.showSimpleAlert(null, "Please Select An Item To Update!");
		}
	}

	@FXML
	private void refreshTable() {
		tableview.getItems().clear();
		tableview.getItems().setAll(list);
	}

	@FXML
	private void refreshStaffTable() {
		tableviewStaff.getItems().clear();
		tableviewStaff.getItems().setAll(stafflist);
	}

	private void loadWindow(String title, String URL, String css) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(URL));
			Stage stage = new Stage(StageStyle.UNDECORATED);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getClassLoader().getResource(css).toExternalForm());
			stage.setScene(scene);
			stage.setTitle(title);
			stage.getIcons().add(new Image("/Image/windows.png"));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void load_Page(ActionEvent event) {
		if ((JFXButton) event.getTarget() == item_btn) {
			h_staff.setVisible(false);
			h_item.setVisible(true);
			h_profile.setVisible(false);
		} else if ((JFXButton) event.getTarget() == staff_btn) {
			h_item.setVisible(false);
			h_staff.setVisible(true);
			h_profile.setVisible(false);
		} else if ((JFXButton) event.getTarget() == profile_btn) {
			h_item.setVisible(false);
			h_staff.setVisible(false);
			h_profile.setVisible(true);
		}
	}

	private void showStaffSearch(String oldValue, String newValue) {
		ObservableList<Staff> tmpList = FXCollections.observableArrayList();
		if (newValue == null || newValue.length() < oldValue.length() || searchText == null) {
			tableviewStaff.getItems().setAll(stafflist);
		} else {
			newValue = newValue.toUpperCase();
			for (Staff staff : tableviewStaff.getItems()) {
				String name = staff.getName();
				String id = staff.getId();
				if (name.toUpperCase().contains(newValue) || id.toUpperCase().contains(newValue)) {
					tmpList.add(staff);
				}
			}
			tableviewStaff.getItems().setAll(tmpList);
		}
	}

	private void showSearch(String oldValue, String newValue) {
		ObservableList<Item> tmpList = FXCollections.observableArrayList();
		if (newValue == null || newValue.length() < oldValue.length() || searchText == null) {
			tableview.getItems().setAll(list);
		} else {
			newValue = newValue.toUpperCase();
			for (Item item : tableview.getItems()) {
				String name = item.getName();
				String id = item.getId();
				if (name.toUpperCase().contains(newValue) || id.toUpperCase().contains(newValue)) {
					tmpList.add(item);
				}
			}
			tableview.getItems().setAll(tmpList);
		}

		/*
		 * ObservableList<Item> templist = FXCollections.observableArrayList(); boolean
		 * isSuccess = false; String idName = searchText.getText(); String qu =
		 * "SELECT * FROM ITEM AS I INNER JOIN CATEGORY C ON I.cid = C.category_id WHERE I.item_id = '"
		 * + idName + "' OR I.item_name = '" + idName + "'"; ResultSet rs =
		 * databaseHandler.execQuery(qu); try { while (rs.next()) { String id =
		 * rs.getString("item_id"); String pic = rs.getString("cid"); String name =
		 * rs.getString("item_name"); String category = rs.getString("category_name");
		 * double price = rs.getDouble("price"); int inventory = rs.getInt("inventory");
		 * 
		 * Item item = new Item(id, pic, category, name, price, inventory);
		 * templist.add(item); isSuccess = true; } } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * tableview.getItems().clear(); if (isSuccess) {
		 * tableview.getItems().addAll(templist); isSuccess = false; }
		 */

	}

	/*
	 * public void filterStudentList(String oldValue, String newValue) {
	 * ObservableList<Student> filteredList = FXCollections.observableArrayList();
	 * if(filterInput == null || (newValue.length() < oldValue.length()) || newValue
	 * == null) { studentTable.setItems(observableStudentList); } else { newValue =
	 * newValue.toUpperCase(); for(Student students : studentTable.getItems()) {
	 * String filterFirstName = students.getFirstName(); String filterLastName =
	 * students.getLastName(); if(filterFirstName.toUpperCase().contains(newValue)
	 * || filterLastName.toUpperCase().contains(newValue)) {
	 * filteredList.add(students); } } studentTable.setItems(filteredList); } }
	 */
	
	
	private void filterStaff(String oldValue, String newValue) {
		ObservableList<Staff> filterList = FXCollections.observableArrayList();
		if (filter == null || (newValue.length() < oldValue.length()) || newValue == null) {
			tableviewStaff.setItems(stafflist);
		} else {
			newValue = newValue.toUpperCase();
			for (Staff staff : tableviewStaff.getItems()) {
				String department = staff.getDepartment();
				String position = staff.getPosition();
				if (department.toUpperCase().contains(newValue)|| position.toUpperCase().contains(newValue)) {
					filterList.add(staff);
				}
			}
			tableviewStaff.setItems(filterList);
		}
	}
	
	private void filter(String oldValue, String newValue) {
		ObservableList<Item> filterList = FXCollections.observableArrayList();
		if (filter == null || (newValue.length() < oldValue.length()) || newValue == null) {
			tableview.setItems(list);
		} else {
			newValue = newValue.toUpperCase();
			for (Item item : tableview.getItems()) {
				String category = item.getCategory();
				if (category.toUpperCase().contains(newValue)) {
					filterList.add(item);
				}
			}
			tableview.setItems(filterList);
		}
	}

	@FXML
	private void link() {
		try {
			Desktop.getDesktop().browse(new URI("https://tianyuhou.github.io/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void setEditable() {
		condate.setEditable(true);
		contime.setEditable(true);
		conlocation.setEditable(true);
		condescription.setEditable(true);
	}
	
	@FXML
	private void setUnEditable() {
		condate.setEditable(false);
		contime.setEditable(false);
		conlocation.setEditable(false);
		condescription.setEditable(false);
	}

	@FXML
	private void exit() {
		System.exit(0);
	}

	@FXML
	private void movePressed(MouseEvent evt) {
		initx = evt.getSceneX();
		inity = evt.getSceneY();
	}

	@FXML
	private void moveDragged(MouseEvent evt) {
		Stage stage = (Stage) root.getScene().getWindow();
		stage.setX(evt.getScreenX() - initx);
		stage.setY(evt.getScreenY() - inity);
	}

	@FXML
	private void minimizeWindow() {
		Stage stage = (Stage) root.getScene().getWindow();
		stage.setIconified(true);
	}

}
