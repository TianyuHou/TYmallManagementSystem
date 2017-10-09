package mall.assistant.addStaff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class addStaff extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddStaff.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("..\\addItem\\additem.css").toExternalForm());
			primaryStage.setTitle("    TY Mall Management System");
			primaryStage.getIcons().add(new Image("/Image/windows.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
