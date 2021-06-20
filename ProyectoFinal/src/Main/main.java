/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Alán
 */
public class main extends Application {

    @Override
    public void start(Stage stage) {

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/UI/UILogIn.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu Laboratorio 10 C07870");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
