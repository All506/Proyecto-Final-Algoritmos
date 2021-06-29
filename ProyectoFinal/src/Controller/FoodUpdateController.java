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
public class FoodUpdateController implements Initializable {

    @FXML
    private ComboBox<?> cmbFood;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }    

    @FXML
    private void btnUpdate(ActionEvent event) {
    }
    
}//end class
