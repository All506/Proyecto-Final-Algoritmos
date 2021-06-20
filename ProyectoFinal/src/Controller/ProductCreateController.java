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
 * @author juanp
 */
public class ProductCreateController implements Initializable {

    @FXML
    private TextField textID;
    @FXML
    private ComboBox<String> comboSupermarket;
    @FXML
    private TextField textName;
    @FXML
    private TextField textPrice;
    @FXML
    private Button btnAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btnAdd(ActionEvent event) {
        
        
        
    }
    
    
    
}//end class
