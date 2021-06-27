/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cmbPlaceId(ActionEvent event) {
    }

    @FXML
    private void btnDeletePlace(ActionEvent event) {
    }
    
}
