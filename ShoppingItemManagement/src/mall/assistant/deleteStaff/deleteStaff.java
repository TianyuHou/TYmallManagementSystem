package mall.assistant.deleteStaff;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class deleteStaff extends Application{

	@Override
	public void start(Stage primaryStage){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("deleteStaff.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("..\\deleteItem\\deleteItem.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String args[]) {
		launch(args);
	}
	
}
