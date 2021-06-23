/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import Misc.ChangeCallback;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class SidePanelController implements Initializable {

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;

    private ChangeCallback callback;
    @FXML
    private JFXButton b4;
    @FXML
    private JFXButton b5;
    @FXML
    private JFXButton b6;
    @FXML
    private AnchorPane apB1;
    @FXML
    private JFXButton btnNewRestSup;
    @FXML
    private JFXButton btnModifyRestSup;
    @FXML
    private JFXButton btnRemoveRestSup;
    @FXML
    private JFXButton btnDisplayRestSup;
    @FXML
    private JFXButton btnCancelRestSup;
    
    String module;
    
    MainController mc = new MainController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setCallback(ChangeCallback callback) {
        this.callback = callback;
    }

    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        switch (btn.getId()) {
            
            case "b1":
                apB1.setVisible(true);
                module="Restaurants";
                break;
            case "b2":
                apB1.setVisible(true);
                module="Food";
                break;
            case "b3":
                apB1.setVisible(true);
                module="Products";
                break;
            case "b4":
                apB1.setVisible(true);
                module="Users";
                break;
            case "b5":
                apB1.setVisible(true);
                module="Places";
                break;
            case "b6":
                apB1.setVisible(true);
                module="Market";
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) { //al cliquear en el boton salir
        
        try {
            Util.SaveData dataSaver = new Util.SaveData();
            dataSaver.saveData();
            System.exit(0);
        } catch (ListException ex) {
            Logger.getLogger(SidePanelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(SidePanelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(SidePanelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SidePanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void btnNewRestSup(ActionEvent event) {
        apB1.setVisible(false);
        switch (module) {
            
            case "Restaurants":
               
                
                break;
            case "Food":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("FoodCreate");
                break;
            case "Products":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("ProductCreate");
                break;
            case "Users":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("UserCreate");
                break;
            case "Places":
               
              
                break;
            case "Market":
               
              
                break;
        }
    }

    @FXML
    private void btnModifyRestSup(ActionEvent event) {
         apB1.setVisible(false);
         switch (module) {
            
            case "Restaurants":
               
                
                break;
            case "Food":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("FoodUpdate");
                break;
            case "Products":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("ProductUpdate");
                break;
            case "Users":
                
               
                break;
            case "Places":
               
              
                break;
            case "Market":
               
              
                break;
        }
    }

    @FXML
    private void btnRemoveRestSup(ActionEvent event) {
         apB1.setVisible(false);
         switch (module) {
            
            case "Restaurants":
               
                
                break;
            case "Food":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("FoodDelete");
                break;
            case "Products":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("ProductDelete");
                break;
            case "Users":
                
               
                break;
            case "Places":
               
              
                break;
            case "Market":
               
              
                break;
        }
        
    }

    @FXML
    private void btnDisplayRestSup(ActionEvent event) {
        apB1.setVisible(false);
         switch (module) {
            
            case "Restaurants":
               
                
                break;
            case "Food":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("FoodRead");
                break;
            case "Products":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("ProductRead");
                break;
            case "Users":
                
               
                break;
            case "Places":
               
              
                break;
            case "Market":
               
              
                break;
        }
        
    }

    @FXML
    private void btnCancelRestSup(ActionEvent event) {
        apB1.setVisible(false);
    }
    
    

}
