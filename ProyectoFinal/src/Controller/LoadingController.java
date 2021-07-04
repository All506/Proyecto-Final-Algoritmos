/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sebastián Navarro Martínez
 */
public class LoadingController implements Initializable {

    @FXML
    private Text txt1;
    @FXML
    private Text txt2;
    @FXML
    private ImageView imgSending;
    @FXML
    private ImageView imgSended;
    @FXML
    private Button btnClose;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imgSended.setVisible(false);
        btnClose.setVisible(false);
        Thread hilo = new Thread (){
                @Override
                public void run()
                {
                    try {
                        //Code here
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoadingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    imgSending.setVisible(false);
                    imgSended.setVisible(true);
                    txt1.setText("Email sent successfully.");
                    btnClose.setVisible(true);
                    txt2.setVisible(false);
                }
            };
            hilo.start();
    }    

    @FXML
    private void btnClose(ActionEvent event) {
         Stage stage = (Stage) this.btnClose.getScene().getWindow();
         stage.close();
    }
    
}
