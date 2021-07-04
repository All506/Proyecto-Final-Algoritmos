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
import PDF.FilePDF;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
    @FXML
    private Text txtButtons;
    @FXML
    private JFXButton b51;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        apB1.setVisible(false);
        showButtons(false);

        //Se setea el menu con respecto al tipo de usuario
        if (Util.Utility.kindUser.equalsIgnoreCase("false")) {
            b1.setVisible(false);
            b3.setVisible(false);
            b2.setVisible(false);
            b4.setVisible(false);
            b5.setVisible(false);
        }

    }

    public void setCallback(ChangeCallback callback) {
        this.callback = callback;
    }

    @FXML
    private void changeColor(ActionEvent event) {
        showButtons(false);
        JFXButton btn = (JFXButton) event.getSource();
        switch (btn.getId()) {

            case "b1":
                showButtons(true);
                txtButtons.setText("Restaurants and Supermarkets");
                apB1.setVisible(true);
                module = "Restaurants";
                break;
            case "b2":
                showButtons(true);
                txtButtons.setText("Foods");
                apB1.setVisible(true);
                module = "Food";
                break;
            case "b3":
                showButtons(true);
                txtButtons.setText("Products");
                apB1.setVisible(true);
                module = "Products";
                break;
            case "b4":
                btnNewRestSup.setVisible(true);
                btnCancelRestSup.setVisible(true);
                txtButtons.setText("Users");
                apB1.setVisible(true);
                module = "Users";
                break;
            case "b5":
                btnNewRestSup.setVisible(true);
                btnDisplayRestSup.setVisible(true);
                btnRemoveRestSup.setVisible(true);
                btnCancelRestSup.setVisible(true);
                txtButtons.setText("Places");
                apB1.setVisible(true);
                module = "Places";
                break;
            case "b6":
                showButtons(false);
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("FinderR&S");
                break;
            case "b51":
                showButtons(false);
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("Reports");
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) { //al cliquear en el boton salir

        try {
            Util.SaveData dataSaver = new Util.SaveData();
            dataSaver.saveData();
            deletePdf();//elimina los pdf si existen para que no se enreden los datos del sistema 
            System.exit(0);
        } catch (ListException | TransformerException | SAXException | IOException | list.ListException ex) {
            Logger.getLogger(SidePanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deletePdf() {
        FilePDF pdf = new FilePDF();
        if (pdf.exist("Restaurants&Supermarkets")) {
            pdf.deleteFile("Restaurants&Supermarkets");
        }

        if (pdf.exist("Searchs")) {
            pdf.deleteFile("Searchs");
        }

        if (pdf.exist("Products&Food")) {
            pdf.deleteFile("Products&Food");
        }
    }

    @FXML
    private void btnNewRestSup(ActionEvent event) {
        apB1.setVisible(false);
        switch (module) {

            case "Restaurants":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("RestaurantCreate");
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
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("PlaceCreate");
                break;
            case "Market":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("FinderR&S");
                break;
        }
    }

    @FXML
    private void btnModifyRestSup(ActionEvent event) {
        apB1.setVisible(false);
        switch (module) {

            case "Restaurants":
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("RestaurantUpdate");
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
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("RestaurantDelete");
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
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("PlaceDelete");
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
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("RestaurantRead");
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
                callback.setPaneVisible(Boolean.TRUE);
                callback.updateColor("PlaceRead");
                break;
            case "Market":

                break;
        }

    }

    @FXML
    private void btnCancelRestSup(ActionEvent event) {
        apB1.setVisible(false);
    }

    public void showButtons(boolean b) {
        btnCancelRestSup.setVisible(b);
        btnDisplayRestSup.setVisible(b);
        btnNewRestSup.setVisible(b);
        btnModifyRestSup.setVisible(b);
        btnRemoveRestSup.setVisible(b);
        txtButtons.setText("");
    }

}
