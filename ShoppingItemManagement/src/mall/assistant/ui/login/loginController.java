package mall.assistant.ui.login;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mall.assistant.settings.Preferences;

public class loginController implements Initializable{

	@FXML
	private JFXTextField username;
	
	@FXML
	private JFXPasswordField password;
	
	@FXML
	private JFXButton loginbtn;
	
	@FXML
	private Label notation;
	
	@FXML
	private FontAwesomeIconView minusbtn, closebtn;
	
	@FXML
	private AnchorPane root;
	
	private double initx;
	private double inity;
	
	Preferences preferences;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		notation.setVisible(false);
		preferences = Preferences.getPreferences();
	}
	
	@FXML
	private void login() {
		String uname = username.getText();
		String pword = DigestUtils.shaHex(password.getText());
		
		if(uname.equals(preferences.getUsername()) && pword.equals(preferences.getPassword())){
			close();
			loadMainWindow();
		}else {
			notation.setVisible(true);
		}
		
	}

	@FXML
	private void close() {
		((Stage)root.getScene().getWindow()).close();
	}

	@FXML
	private void loadMainWindow() {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mall/assistant/uiMainPage/MainPage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("mall/assistant/uiMainPage/MainUI.css").toExternalForm());
			Stage stage = new Stage(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.setTitle("    TY Mall Management System");
			stage.getIcons().add(new Image("/Image/windows.png"));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	private void minimizeBtnAction() {
		Stage stage = (Stage)root.getScene().getWindow();
		stage.setIconified(true);
	}
	
	@FXML
	private void movePressed(MouseEvent evt) {
//		Stage stage = (Stage)root.getScene().getWindow();
		initx = evt.getSceneX();
		inity = evt.getSceneY();
	}
	
	@FXML
	private void moveDragged(MouseEvent evt) {
		Stage stage = (Stage)root.getScene().getWindow();
		stage.setX(evt.getScreenX()-initx);
		stage.setY(evt.getScreenY()-inity);
	}
	
	/* @FXML
	    private void movePressed(MouseEvent evt){
//	        Stage stage = (Stage)frame.getScene().getWindow();
	        initx= evt.getSceneX();
	        inity = evt.getSceneY();
	    }
	    
	    @FXML
	    private void moveDragged(MouseEvent evt){
	        Stage stage = (Stage)root.getScene().getWindow();
	        stage.setX(evt.getScreenX()-initx);
	        stage.setY(evt.getScreenY()-inity);
	        
	    }*/
	
	
	

}
