/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Graphs.GraphException;
import Objects.Restaurant;
import list.ListException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class RestaurantCreateController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    private TextField txtLocation;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnClean;
    @FXML
    private ComboBox<String> cmbLocation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Cargar el comboBox con los lugares
        cmbLocation.getSelectionModel().select("Cartago");
        cmbLocation.getItems().add("Cartago");
        cmbLocation.getItems().add("San Jose");
        cmbLocation.getItems().add("Alajuela");
        
    }

    @FXML
    private void btnCreate(ActionEvent event) {
        try {
            if (txtId.getText().equals("") || cmbLocation.getValue().equals("") || txtName.getText().equals("")) {
                callAlert("Error", "There are empty spaces. Fill all blank spaces");
            } else {
                Restaurant rest = new Restaurant(Integer.valueOf(txtId.getText()), txtName.getText(), cmbLocation.get);
                Util.Utility.gRestaurants.addVertex(rest);
                callAlert("Confirmation", "Restaurant has been added");
                cleanUI();
            }

        } catch (GraphException ex) {
            Logger.getLogger(RestaurantCreateController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(RestaurantCreateController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    @FXML
    private void btnClean(ActionEvent event) {
        cleanUI();
    }

    public void cleanUI() {
        cmbLocation.getSelectionModel().select("Cartago");
        txtId.setText(""); //Llamar al util.utility.lastIdRestaurants
        txtName.setText("");
    }

    private void callAlert(String fxmlName, String text) {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + fxmlName + ".fxml"));
            Parent root1;
            root1 = (Parent) loader.load();
            //Se llama al controller de la nueva ventana abierta
            ErrorController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Alert");
            //Se le asigna la información a la controladora
            controller.fill(text);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
