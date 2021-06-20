package Main;

import MainMenu.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClassFX extends Application {
    
    public static Boolean isSplashLoaded = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/UILogIn.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Proyecto final");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
