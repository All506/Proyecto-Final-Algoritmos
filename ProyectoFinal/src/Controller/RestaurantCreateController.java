package Controller;

import Graphs.GraphException;
import Objects.Place;
import Objects.Restaurant;
import Objects.Supermarket;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import list.SinglyLinkedList;

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
    @FXML
    private RadioButton radioRestaurant;
    @FXML
    private RadioButton radioSupermarket;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Cargar el comboBox con los lugares
        radioRestaurant.setSelected(true);
        if (Util.Utility.gPlaces.isEmpty()) {
            callAlert("There are no places to load");
        } else {
            SinglyLinkedList lPlaces = Util.Utility.gPlaces.getAllItemsAsList();
            try {
                for (int i = 1; i <= lPlaces.size(); i++) {
                    Place tempPlace = (Place) lPlaces.getNode(i).data;
                    cmbLocation.getItems().add(tempPlace.getName());
                }
                cmbLocation.getSelectionModel().select(0);
            } catch (ListException ex) {
                Logger.getLogger(RestaurantCreateController.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtId.setText(String.valueOf(Util.Utility.lastIndexGRestAndSuper));
        }

    }

    @FXML
    private void btnCreate(ActionEvent event) {
        try {
            if (txtId.getText().equals("") || cmbLocation.getValue().equals("") || txtName.getText().equals("")) {
                callAlert("There are empty spaces. Fill all blank spaces");
            } else {
                if (radioRestaurant.isSelected() == true) {
                    Restaurant rest = new Restaurant(Integer.valueOf(txtId.getText()), txtName.getText(), cmbLocation.getValue());
                    Util.Utility.gRestAndSuper.addVertex(rest);
                    Util.Utility.lastIndexGRestAndSuper++; //Se suma uno al ultimo indice
                    callConfirmation("Restaurant has been added");
                } else {
                    Supermarket supermarket = new Supermarket(Integer.valueOf(txtId.getText()), txtName.getText(), cmbLocation.getValue());
                    Util.Utility.gRestAndSuper.addVertex(supermarket);
                    Util.Utility.lastIndexGRestAndSuper++; //Se suma uno al ultimo indice
                    callConfirmation("Supermarket has been added");
                }
                cleanUI();
            }

        } catch (GraphException | ListException ex) {
            Logger.getLogger(RestaurantCreateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnClean(ActionEvent event) {
        cleanUI();
    }

    public void cleanUI() {
        cmbLocation.getSelectionModel().select(0);
        txtId.setText(String.valueOf(Util.Utility.lastIndexGRestAndSuper));
        txtName.setText("");
    }

    private void callAlert(String text) {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + "Error" + ".fxml"));
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

    private void callConfirmation(String text) {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + "Confirmation" + ".fxml"));
            Parent root1;
            root1 = (Parent) loader.load();
            //Se llama al controller de la nueva ventana abierta
            ConfirmationController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Confirmation");
            //Se le asigna la información a la controladora
            controller.fill(text);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void radioRestaurant(ActionEvent event) {
        radioSupermarket.setSelected(false);
    }

    @FXML
    private void radioSupermarket(ActionEvent event) {
        radioRestaurant.setSelected(false);
    }

}//end class
