package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Domain.TreeException;
import Objects.Product;
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
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juanp
 */
public class ProductDeleteController implements Initializable {

    @FXML
    private Button btnRemove;
    @FXML
    private ComboBox<String> comboProducts;

    Product pTemp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboProducts();
    }

    @FXML
    private void btnRemove(ActionEvent event) throws TreeException {
        boolean flag = false;
        while (!Util.Utility.getTreeProducts().isEmpty() && Util.Utility.getTreeProducts().contains(comboProducts.getValue())) {
            Util.Utility.getTreeProducts().remove(comboProducts.getValue());
            flag = true;
        }
        if (flag) {
            callConfirmation("The Product(s) has been deleted");
        }else{
            callAlert("Please check the empty space");
        }
        flag = false;
        comboProducts.getItems().clear();
        loadComboProducts();
    }

    private void loadComboProducts() {
        BST treeProductsTemp = new BST();
        treeProductsTemp = Util.Utility.getTreeProducts();
        if (!treeProductsTemp.isEmpty()) {
            tourTree(treeProductsTemp.getRoot(), comboProducts);
            this.comboProducts.setValue(null);
        } else {
            callAlert("There are no Product(s) registered");
        }
    }

    private void tourTree(BTreeNode node, ComboBox<String> comboProducts) {
        if (node != null) {
            tourTree(node.left, comboProducts);
            tourTree(node.right, comboProducts);
            Product p = (Product) node.data;
            if (!comboProducts.getItems().contains(p.getName())) {
                comboProducts.getItems().add(p.getName());
            }

        }
    }

    @FXML
    private void loadData(ActionEvent event) {
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
