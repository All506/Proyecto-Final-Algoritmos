/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Misc.ColorChangeCallback;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class SidePanelController implements Initializable {

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;

    private ColorChangeCallback callback;
    @FXML
    private JFXButton b4;
    @FXML
    private JFXButton b5;
    @FXML
    private JFXButton b6;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setCallback(ColorChangeCallback callback) {
        this.callback = callback;
    }

    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        /*switch (btn.getText()) {
            case "Color 1":
                callback.updateColor("#00FF00");
                break;
            case "Color 2":
                callback.updateColor("#0000FF");
                break;
            case "Color 3":
                callback.updateColor("#FF0000");
                break;
        }*/
        switch (btn.getId()) {
            case "b1":
                System.out.println("El primer boton fue cliqueado");
                callback.updateColor("Confirmation");
                break;
            case "b2":
                System.out.println("El segundo boton fue cliqueado");
                callback.updateColor("Error");
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
