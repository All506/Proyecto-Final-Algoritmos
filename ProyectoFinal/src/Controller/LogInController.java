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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        
    }    

    @FXML
    private void btnLogIn(ActionEvent event) {
        FileXML fXML = new FileXML();
        Security auxiliar = new Security("admin","prueba"); //En caso de que no haya ninguno registrado, admin-prueba se utilizarán para entrar 
        try {
            Util.Utility.addSecurity(auxiliar);
            
             if (txtUser.getText().equalsIgnoreCase("") || txtPassword.getText().equalsIgnoreCase("")){
            System.out.println("El usuario se encuentra vacio");
        } else { 
            if (!fXML.exist("Security.xml")){ //Si el archivo del login no existe
                   
                    Security user = new Security(txtUser.getText(),txtPassword.getText());

                    if (Util.Utility.lSecurity.contains(user)){
                        System.out.println("LogIn exitoso");
                    }
                    System.out.println(Util.Utility.lSecurity.toString());
                
            } else { //Si el documento sí existe
                CircularLinkList lDecrypt = fXML.readXMLtoSecurityList(); //Se obtiene la informacion del xml
                for (int i = 1; i < lDecrypt.size(); i++) { //Se envia la informacion que tenia el xml a la lista en utiltity
                    Util.Utility.lSecurity.add(lDecrypt.getNode(i));
                }
                
            }
        }
             
        } catch (ListException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    
}
