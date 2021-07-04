/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Graphs.AdjacencyListGraph;
import Graphs.GraphException;
import Objects.Place;
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
 * @author Sebastián Navarro Martínez
 */
public class RestaurantUpdateController implements Initializable {

    @FXML
    private ComboBox<String> cmbRestaurant;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnUpdate;
    @FXML
    private ComboBox<String> cmbLocation;

    Object aux;

    AdjacencyListGraph gRestAndSuper = new AdjacencyListGraph(100);

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

    }

    private void loadInfo() {
        if (cmbRestaurant.getValue() != null) {
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
                                cmbLocation.setValue(selection[1]);
                                txtName.setText(selection[0]);
                            }

                        } else {
                            Supermarket supermar = (Supermarket) list.getNode(i).getData();
                            String[] selection = cmbRestaurant.getValue().split("-");
                            if (supermar.getName().equalsIgnoreCase(selection[0])) {
                                aux = supermar;
                                cmbLocation.setValue(selection[1]);
                                txtName.setText(selection[0]);
                            }

                        }

                    }
                } catch (list.ListException ex) {

                }

            }
        }
    }

    @FXML
    private void btnUpdate(ActionEvent event) throws ListException, GraphException {
        if (aux instanceof Restaurant) {//Restaurant
            
            //Instancio el elemento a borrar,recuperandolo de un metodo que devuelve un Objecto Restaurant
            
            String[] selection = cmbRestaurant.getValue().split("-");
            Restaurant rTemp = new Restaurant(0, selection[0], selection[1]);
            
            Restaurant rDelete = Util.Utility.getRestaurantId(rTemp.getName());
            
            System.out.println(rDelete.toString());
            
            //Instancio el nuevo Objecto
            Restaurant rNew = new Restaurant(rDelete.getID(), txtName.getText(), cmbLocation.getValue());
            
            System.out.println(rNew.toString());
            
            //Remuevo el viejo
            Util.Utility.gRestAndSuper.removeVertex(rDelete);
            
            //Agrego el nuevo
            Util.Utility.gRestAndSuper.addVertex(rNew);
            loadInfo();
            callConfirmation("Restaurant successfully updated");
            
        }else{//Supermarket
            
            String[] selection = cmbRestaurant.getValue().split("-");
            Supermarket sTemp = new Supermarket(0, selection[0], selection[1]);
            
            //Instancio el elemento a borrar,recuperandolo de un metodo que devuelve un Objecto Restaurant
            Supermarket sDelete = Util.Utility.getSupermarketId(sTemp.getName());
            
            //Instancio el nuevo Objecto
            Supermarket sNew = new Supermarket(sDelete.getID(), txtName.getText(), cmbLocation.getValue());
            
            //Remuevo el viejo
            Util.Utility.gRestAndSuper.removeVertex(sDelete);
            
            //Agrego el nuevo
            Util.Utility.gRestAndSuper.addVertex(sNew);
            loadInfo();
            callConfirmation("Supermarket successfully updated");
            
        }
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
    private void cmbRestaurant(ActionEvent event) {
        if (cmbRestaurant.getValue() != null) {
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
                                cmbLocation.setValue(selection[1]);
                                txtName.setText(selection[0]);
                            }

                        } else {
                            Supermarket supermar = (Supermarket) list.getNode(i).getData();
                            String[] selection = cmbRestaurant.getValue().split("-");
                            if (supermar.getName().equalsIgnoreCase(selection[0])) {
                                aux = supermar;
                                cmbLocation.setValue(selection[1]);
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
