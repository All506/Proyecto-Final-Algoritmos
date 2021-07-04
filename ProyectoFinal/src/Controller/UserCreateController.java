/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Objects.Security;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class UserCreateController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtPassword2;
    @FXML
    private Button btnCreate;
    @FXML
    private RadioButton radioUser;
    @FXML
    private RadioButton radioAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        radioAdmin.setSelected(true);
    }    

    @FXML
    private void btnCreate(ActionEvent event) {
        if (txtUser.getText().equals("") || txtPassword.getText().equals("") || txtPassword2.getText().equals("")){ //Si hay espacios vacios
            callAlert("Error", "There are empty spaces!!!");
            //System.out.println("HAY ESPACIOS VACIOS");
        } else {
            if (txtPassword.getText().equals(txtPassword2.getText())){   
                try {
                    Security newUser = new Security(txtUser.getText(),txtPassword.getText(),getKindUser());
                    if (Util.Utility.addSecurity(newUser)){ //Cambiar para que solo chequee por nombre de usuario en utitlty
                        callNotification("Confirmation", "User has been registered");
                        //System.out.println("El usuario ha sido añadido");
                        System.out.println(Util.Utility.lSecurity.toString());
                        txtUser.setText("");
                        txtPassword.setText("");
                        txtPassword2.setText("");
                    } else {
                        callAlert("Error", "User already exists. Try again with new one...");
                        //System.out.println("El usuario ya existe. Intente con uno nuevo");
                    }
                } catch (ListException ex) {
                    Logger.getLogger(UserCreateController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                callAlert("Error", "Passwords are not the same!!!");
                //System.out.println("Las contraseñas no coinciden");
            }
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
    
        private void callNotification(String fxmlName, String text) {
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

    @FXML
    private void radioUser(ActionEvent event) {
        radioAdmin.setSelected(false);
    }

    @FXML
    private void radioAdmin(ActionEvent event) {
        radioUser.setSelected(false);
    }
    
    private String getKindUser(){
        //El usuario administrador se almacenará como true
        //El usuario normal se almacenará como false
        String kindUser = "true";
        if (radioUser.isSelected() == true){
            kindUser = "false";
        }
        return kindUser;
    }
}
