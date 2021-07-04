/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Objects.Search;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class SuggestionsController implements Initializable {

    @FXML
    private Text txtPlace;
    @FXML
    private Text txtProduct;
    @FXML
    private Text txtSuggestions;
    @FXML
    private Text txtDate;
    @FXML
    private Button btnSendByEmail;
    @FXML
    private Button btnBackToMenu;
    @FXML
    private TextField txfEmail;
    @FXML
    private Button btnSend;
    @FXML
    private Button btnBack;
    @FXML
    private AnchorPane apEmail;

    Search searchTemp;

    boolean flag = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apEmail.setVisible(false);
        try {
            Search search = (Search) Util.Utility.lSearches.getNode(Util.Utility.lSearches.size()).data;
            searchTemp = search;
            loadInfo(search);
        } catch (ListException ex) {
            Logger.getLogger(SuggestionsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadInfo(Search s) {

        txtDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(s.getSearchDate()) + "  " + s.getHour());

        txtPlace.setText(s.getLocation());

        txtProduct.setText(s.getProduct());

        String sug[] = s.getSuggestions().split("-");
        String suggest = "";
        for (int i = 0; i < sug.length; i++) {
            suggest += sug[i] + "\n";
        }

        txtSuggestions.setText(suggest);
    }

    @FXML
    private void btnSendByEmail(ActionEvent event) {
        apEmail.setVisible(true);
    }

    @FXML
    private void btnBackToMenu(ActionEvent event) {
    }

    @FXML
    private void btnSend(ActionEvent event) throws Exception {
        if (!txfEmail.equals("")) {
            if (Util.Utility.emailChecker(txfEmail.getText())) {
                Stage stage = callSending();

                Thread hilo = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Mail.Mail.sendEmail(txfEmail.getText(), searchTemp);
                            txfEmail.setText("");
                            apEmail.setVisible(false);
                            flag = true;
                        } catch (Exception ex) {
                            Logger.getLogger(SuggestionsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
                hilo.start();
                

                //hilo.join();
            } else {
                callAlert("The email space is wrong, you need the domain to be @gmail.com");
            }
        } else {
            callAlert("The email space is empty!!");
        }
    }

    @FXML
    private void btnBack(ActionEvent event) {
        apEmail.setVisible(false);
    }

    public Stage callSending() {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + "Loading" + ".fxml"));
            Parent root1;
            root1 = (Parent) loader.load();
            //Se llama al controller de la nueva ventana abierta
            LoadingController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Alert");
            //Se le asigna la información a la controladora
//            controller.fill(text);
            stage.setScene(new Scene(root1));
            stage.show();
            return stage;
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void callAlert(String text) {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + "Error" + ".fxml"));
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

    private void callConfirmation(String text) {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + "Confirmation" + ".fxml"));
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

}//end class
