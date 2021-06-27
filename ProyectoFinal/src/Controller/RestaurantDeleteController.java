/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Graphs.GraphException;
import Objects.Restaurant;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import list.SinglyLinkedList;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class RestaurantDeleteController implements Initializable {

    @FXML
    private ComboBox<String> cmbRestaurant;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLocation;
    @FXML
    private Button btnDelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboBoxRestaurants();
        cmbRestaurant.getSelectionModel().select(0);
        cmbRestaurant.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadInfo();
            }
        });
        loadInfo();
    }

    @FXML
    private void btnDelete(ActionEvent event) throws GraphException, list.ListException {
        Restaurant rest = new Restaurant(0, txtName.getText(), txtLocation.getText());
        if (Util.Utility.gRestaurants.containsVertex(rest)) {
            Util.Utility.gRestaurants.removeVertex(rest);

            loadComboBoxRestaurants();
            cmbRestaurant.getSelectionModel().select(0);
            callAlert("Error", "Restaurant has been deleted");
        } else {
            callAlert("Error", "Restaurant is not registered");
        }

    }

    public void loadComboBoxRestaurants() {
        cmbRestaurant.getItems().clear(); //Se actualizan los datos que contiene el combobox
        //Para cargar un combobox
        if (Util.Utility.gRestaurants.isEmpty()) {
            callAlert("Error", "There are no restaurants registered");
        } else {
            SinglyLinkedList list = Util.Utility.gRestaurants.getAllItemsAsList();
            String temporal = "";

            try {
                for (int i = 1; i <= list.size(); i++) {
                    Restaurant res = (Restaurant) list.getNode(i).getData();
                    temporal = res.getName() + "-" + res.getLocation();
                    this.cmbRestaurant.getItems().add(temporal);
                }
            } catch (list.ListException ex) {
                Logger.getLogger(RestaurantDeleteController.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.cmbRestaurant.setValue(null);
        }

    }

    private void loadInfo() {
        String[] selection = cmbRestaurant.getValue().split("-");
        txtLocation.setText(selection[1]);
        txtName.setText(selection[0]);
    }

    @FXML
    private void cmbChanged(ActionEvent event) {

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
