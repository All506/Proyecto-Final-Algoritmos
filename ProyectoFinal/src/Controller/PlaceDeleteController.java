/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Graphs.AdjacencyListGraph;
import Graphs.GraphException;
import Objects.Place;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
        try {
            loadCombobox();
        } catch (ListException ex) {
            Logger.getLogger(PlaceDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void cmbPlaceId(ActionEvent event) throws ListException {
        if(!cmbPlaceId.getValue().isEmpty()){
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
        
        gPlace.removeVertex(aux);
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
    
}
