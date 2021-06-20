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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Your Name <Sebastián Navarro Martínez>
 */
public class ErrorController implements Initializable {

    @FXML
    private Text txtContent;
    @FXML
    private Button btnContinue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnContinue(ActionEvent event) {
        Stage stage = (Stage) this.btnContinue.getScene().getWindow();
        stage.close();
    }
    
    public void fill(String content){
        this.txtContent.setText(content);
    }
    
}
