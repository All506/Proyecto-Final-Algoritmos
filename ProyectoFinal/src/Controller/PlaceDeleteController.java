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

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class PlaceDeleteController implements Initializable {

    @FXML
    private ComboBox<String> cmbPlaceId;
    @FXML
    private TextField txfPlaceName;
    @FXML
    private Button btnDeletePlace;
    
    AdjacencyListGraph gPlace;
    
    Place aux;
    @FXML
    private TextField txfPlaceId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gPlace = Util.Utility.getPlacesGraph();
        btnDeletePlace.setDisable(true);
        try {
            loadCombobox();
        } catch (ListException ex) {
            Logger.getLogger(PlaceDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void cmbPlaceId(ActionEvent event) throws ListException {
        if(cmbPlaceId.getValue()!=null){
            btnDeletePlace.setDisable(false);
            Place p = null;
            for (int i = 0; i < gPlace.size(); i++) {
                p=(Place)gPlace.getVertexByIndex(i).data;
                if(p.getName().equals(cmbPlaceId.getValue())){
                txfPlaceId.setText(String.valueOf(p.getID()));
                txfPlaceName.setText(p.getName());
                aux=p;
                }
            }
        }
    }

    @FXML
    private void btnDeletePlace(ActionEvent event) throws GraphException, ListException {
        
        btnDeletePlace.setDisable(true);
        txfPlaceId.setText("");
        txfPlaceName.setText("");
        
        AdjacencyListGraph rs = Util.Utility.gRestAndSuper;
        boolean canDelete  = true;
        for (int i = 0; i < rs.size(); i++) {
            if(rs.getVertexByIndex(i).data instanceof Restaurant){
                Restaurant r = (Restaurant) rs.getVertexByIndex(i).data;
                if(Util.Utility.equals(r.getLocation(), aux.getName())){
                    canDelete=false;
                }
            }else{
                Supermarket s = (Supermarket) rs.getVertexByIndex(i).data;
                if(Util.Utility.equals(s.getLocation(), aux.getName())){
                    canDelete=false;
                }
            }
        }
       
        if (canDelete) {
            gPlace.removeVertex(aux);
            callConfirmation("Confirmation", "Place deleted successfully");
        } else {
            callAlert("Error", "Can't delete this place");
        }
        
        cmbPlaceId.getItems().clear();
        loadCombobox();
         
        
    }
    
    public void loadCombobox() throws ListException{
    if(!gPlace.isEmpty())
        for (int i = 0; i < gPlace.size(); i++) {
            Place p = (Place) gPlace.getVertexByIndex(i).data;
            cmbPlaceId.getItems().add(p.getName());
        }
    
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
    
    private void callConfirmation(String fxmlName, String text) {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + fxmlName + ".fxml"));
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
    
}
