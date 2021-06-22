/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.ListException;
import Objects.Security;
import XML.FileXML;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class LogInController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogIn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FileXML fXML = new FileXML();
            CircularLinkList lUsers = new CircularLinkList();
            
            if (fXML.exist("Security.xml")) {
                
                lUsers = fXML.readXMLtoSecurityList();
                
                try {
                    for (int i = 1; i <= lUsers.size(); i++) {
                        Util.Utility.lSecurity.add((Security)lUsers.getNode(i).data);
                    }
                    
                } catch (ListException ex) {
                    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Security auxiliar = new Security("admin", "prueba"); //En caso de que no haya ninguno registrado, admin-prueba se utilizarán para entrar
            Util.Utility.addSecurity(auxiliar);
            System.out.println("La lista contiene: " + Util.Utility.lSecurity.toString());
        } catch (ListException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnLogIn(ActionEvent event) throws IOException, ListException {
        FileXML fXML = new FileXML();

        System.out.println("La lista contiene: " + Util.Utility.lSecurity.toString());
        
        if (txtUser.getText().equalsIgnoreCase("") || txtPassword.getText().equalsIgnoreCase("")) {
            System.out.println("El usuario se encuentra vacio");
        } else {
            Security logInUser = new Security(txtUser.getText(), txtPassword.getText());
            if (Util.Utility.lSecurity.contains(logInUser)) {
                Util.Utility.userName = txtUser.getText();
                callMenu();
            } else {
                System.out.println("Intente nuevamente");
            }
        }
        
    }

    private void callMenu() throws IOException {
        Stage stage = (Stage) this.txtUser.getScene().getWindow();
        stage.close();
        //Se abre el nuevo stage
        //Se llama al controller de la nueva ventana abierta
        Parent root = FXMLLoader.load(getClass().getResource("/UI/main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.show();
    }

}
