/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Graphs.AdjacencyListGraph;
import Graphs.GraphException;
import Objects.Place;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import list.ListException;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class PlaceCreateController implements Initializable {

    @FXML
    private TextField txfPlaceName;
    @FXML
    private Button btnSavePlace;
    @FXML
    private TextField txfPlaceId;
    
    AdjacencyListGraph gPlaces;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gPlaces = Util.Utility.getPlacesGraph();
        try {
            loadTextField();
        } catch (ListException ex) {
            Logger.getLogger(PlaceCreateController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void btnSavePlace(ActionEvent event) throws GraphException, ListException {
        if(txfPlaceName.getText().length()>4){
            gPlaces.addVertex(new Place(Integer.parseInt(txfPlaceId.getText()), txfPlaceName.getText()));
//            callAlert("Confirmation", "Place added successfully");  
            txfPlaceName.setText("");
            Util.Utility.lastIndexGPlace++;
            loadTextField();
        }else{
            callAlert("Error", "The name must contain at least 5 characters.");  
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
    
    public void loadTextField() throws ListException{
      
        if (!gPlaces.isEmpty()) {
            Place p = (Place) gPlaces.getVertexByIndex(gPlaces.size() - 1).data;
            txfPlaceId.setText(String.valueOf(Util.Utility.lastIndexGPlace));
        } else {
            txfPlaceId.setText("1");
        }

    }
    
}
