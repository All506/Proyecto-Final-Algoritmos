package Controller;

import Domain.ListException;
import PDF.FilePDF;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juanp
 */
public class ReportsController implements Initializable {

    @FXML
    private Button reportFyP;
    @FXML
    private Button reportSearch;
    @FXML
    private Button reportRyS;
    @FXML
    private Label label1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Util.Utility.kindUser.equals("false")) {
            reportSearch.setVisible(false);
            label1.setVisible(false);
        }
    }

    @FXML
    private void reportFyP(ActionEvent event) {
        FilePDF pdf = new FilePDF();
        if (!Util.Utility.treeProducts.isEmpty()) {
            if (!pdf.exist("Products&Food")) {
                try {
                    try {
                        pdf.productsAndFood(Util.Utility.treeProducts, Util.Utility.treeFoods);
                    } catch (FileNotFoundException | BadElementException | list.ListException ex) {
                        Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (DocumentException | URISyntaxException | IOException | ListException ex) {

                }
                callConfirmation("The Products&Food Report has been create");
            } else {
                pdf.deleteFile("Products&Food");
                try {
                    try {
                        pdf.productsAndFood(Util.Utility.treeProducts, Util.Utility.treeFoods);
                    } catch (FileNotFoundException | BadElementException | list.ListException ex) {
                        Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (DocumentException | URISyntaxException | IOException | ListException ex) {
                    Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                callConfirmation("The Products&Food Report has been create");
            }
        } else {
            callAlert("The Products&Food Report cannot been create because the Graph is empty!!!");
        }

    }

    @FXML
    private void reportSearch(ActionEvent event) {
        FilePDF pdf = new FilePDF();
        if (!Util.Utility.lSearches.isEmpty()) {
            if (!pdf.exist("Searchs")) {
                try {
                    pdf.searchPDF(Util.Utility.lSearches);
                } catch (DocumentException | URISyntaxException | IOException ex) {
                    Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                callConfirmation("The Searchs Report has been create");
            } else {
                pdf.deleteFile("Searchs");
                try {
                    pdf.searchPDF(Util.Utility.lSearches);
                } catch (DocumentException | URISyntaxException | IOException ex) {
                    Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                callConfirmation("The Searchs Report has been create");
            }
        } else {
            callAlert("The Searchs Report cannot been create because the list is empty!!!");
        }

    }

    @FXML
    private void reportRyS(ActionEvent event) {
        FilePDF pdf = new FilePDF();
        if (!Util.Utility.gRestAndSuper.isEmpty()) {
            if (!pdf.exist("Restaurants&Supermarkets")) {
                try {
                    pdf.restaurantsAndSupermarket(Util.Utility.gRestAndSuper);
                } catch (DocumentException | URISyntaxException | IOException | ListException | list.ListException ex) {
                    Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                callConfirmation("The Restaurants&Supermarkets Report has been create");
            } else {
                pdf.deleteFile("Restaurants&Supermarkets");
                try {
                    pdf.restaurantsAndSupermarket(Util.Utility.gRestAndSuper);
                } catch (DocumentException | URISyntaxException | IOException | ListException | list.ListException ex) {
                    Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                callConfirmation("The Restaurants&Supermarkets Report has been create");
            }
        } else {
            callAlert("The Restaurants&Supermarkets Report cannot been create because the list is empty!!!");
        }

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
