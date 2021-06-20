/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import Misc.ChangeCallback;

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

    private ChangeCallback callback;
    @FXML
    private JFXButton b4;
    @FXML
    private JFXButton b5;
    @FXML
    private JFXButton b6;
    @FXML
    private AnchorPane apB1;
    @FXML
    private JFXButton btnNewRestSup;
    @FXML
    private JFXButton btnModifyRestSup;
    @FXML
    private JFXButton btnRemoveRestSup;
    @FXML
    private JFXButton btnDisplayRestSup;
    @FXML
    private JFXButton btnCancelRestSup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setCallback(ChangeCallback callback) {
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
                apB1.setVisible(true);
//                callback.updateColor("Confirmation");
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

    @FXML
    private void btnNewRestSup(ActionEvent event) {
    }

    @FXML
    private void btnModifyRestSup(ActionEvent event) {
    }

    @FXML
    private void btnRemoveRestSup(ActionEvent event) {
    }

    @FXML
    private void btnDisplayRestSup(ActionEvent event) {
    }

    @FXML
    private void btnCancelRestSup(ActionEvent event) {
        apB1.setVisible(false);
    }

}
