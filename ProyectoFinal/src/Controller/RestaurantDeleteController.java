/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.TreeException;
import Graphs.AdjacencyListGraph;
import Graphs.GraphException;
import Objects.Restaurant;
import Objects.Supermarket;
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
import list.ListException;
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

    Object aux;

    AdjacencyListGraph gRestAndSuper = new AdjacencyListGraph(100);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Util.Utility.gRestAndSuper.isEmpty()) {
            loadComboBoxRestaurants();
            gRestAndSuper = Util.Utility.gRestAndSuper;
            cmbRestaurant.getSelectionModel().select(0);
            loadInfo();
        } else {
            callAlert("There are no Restaurant(s) and Supermarket(s) registered");
        }
    }

    @FXML
    private void btnDelete(ActionEvent event) throws GraphException, list.ListException, TreeException {
        boolean flag = false;
        if (Util.Utility.gRestAndSuper.isEmpty()) {
            callAlert("Graph is empty");
        } else {

            boolean canDelete = false;
            if (aux instanceof Restaurant) {
                Restaurant r = (Restaurant) aux;
                canDelete = Util.Utility.canDeleteRest(r);
            } else {
                Supermarket s = (Supermarket) aux;
                canDelete = Util.Utility.canDeleteSup(s);
            }

            if (canDelete) {
                for (int i = 0; i < Util.Utility.gRestAndSuper.size(); i++) {
                    if (Util.Utility.gRestAndSuper.containsVertex(aux)) {
                        Util.Utility.gRestAndSuper.removeVertex(aux);
                        flag = true;
                        if (aux instanceof Restaurant) {
                            callConfirmation("The Restaurant has been deleted");
                        } else {
                            callConfirmation("The Supermarket has been deleted");
                        }

                    }

                }
            }//REVISAR LA ALERT
            if (!flag) {
                if (aux instanceof Restaurant) {
                    callAlert("The Restaurant cannot been deleted");
                }else{
                    callAlert("The Supermarket cannot been deleted");
                }
            }
        }
        flag = false;
        gRestAndSuper = Util.Utility.gRestAndSuper;
        loadComboBoxRestaurants();
        cmbRestaurant.getSelectionModel().select(0);
        txtName.setText("");
        txtLocation.setText("");
    }

    public void loadComboBoxRestaurants() {
        cmbRestaurant.getItems().clear(); //Se actualizan los datos que contiene el combobox
        //Para cargar un combobox
        if (Util.Utility.gRestAndSuper.isEmpty()) {
            callAlert("There are no Restaurant(s) and Supermarket(s) registered");
        } else {
            SinglyLinkedList list = Util.Utility.gRestAndSuper.getAllItemsAsList();
            String temporal = "";
            try {
                for (int i = 1; i <= list.size(); i++) {
                    if (list.getNode(i).getData() instanceof Restaurant) {
                        Restaurant res = (Restaurant) list.getNode(i).getData();
                        temporal = res.getName() + "-" + res.getLocation();
                        this.cmbRestaurant.getItems().add(temporal);
                    } else {
                        Supermarket supermar = (Supermarket) list.getNode(i).getData();
                        temporal = supermar.getName() + "-" + supermar.getLocation();
                        this.cmbRestaurant.getItems().add(temporal);
                    }

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
    private void cmbRestaurant(ActionEvent event) throws ListException {
        if (cmbRestaurant.getValue() != null) {
            txtLocation.setText("");
            txtName.setText("");
            if (Util.Utility.gRestAndSuper.isEmpty()) {
                callAlert("There are no Restaurant(s) and Supermarket(s) registered");
            } else {
                SinglyLinkedList list = Util.Utility.gRestAndSuper.getAllItemsAsList();
                try {
                    for (int i = 1; i <= list.size(); i++) {
                        if (list.getNode(i).getData() instanceof Restaurant) {
                            Restaurant res = (Restaurant) list.getNode(i).getData();
                            String[] selection = cmbRestaurant.getValue().split("-");
                            if (res.getName().equalsIgnoreCase(selection[0])) {
                                aux = res;
                                txtLocation.setText(selection[1]);
                                txtName.setText(selection[0]);
                            }

                        } else {
                            Supermarket supermar = (Supermarket) list.getNode(i).getData();
                            String[] selection = cmbRestaurant.getValue().split("-");
                            if (supermar.getName().equalsIgnoreCase(selection[0])) {
                                aux = supermar;
                                txtLocation.setText(selection[1]);
                                txtName.setText(selection[0]);
                            }

                        }

                    }
                } catch (list.ListException ex) {

                }

            }
        }
    }

}//end class
