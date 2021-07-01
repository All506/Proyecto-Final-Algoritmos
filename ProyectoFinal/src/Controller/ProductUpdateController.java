package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Domain.TreeException;
import Objects.Food;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juanp
 */
public class ProductUpdateController implements Initializable {

    @FXML
    private ComboBox<String> cmbProducts;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbProducts.getItems().clear();
        maskNumbers(txtPrice);
        loadComboProduct();
    }    

    @FXML
    private void btnUpdate(ActionEvent event) throws TreeException {
        if (this.txtName.getText().equals("") || this.txtPrice.getText().equals("") || this.cmbProducts.getValue().equals("")) {
            callAlert("Error", "Fill all the information to continue");
        } else {
            //Generate a temporal tree
            BST tempTree = Util.Utility.getTreeProducts();
            
            //Call the food to delete
            Product productToDelete = Util.Utility.getProductByName(this.cmbProducts.getValue());
            
            //Create new food to replace (Basically the old but upgrated)
            Product updatedProduct = new Product(productToDelete.getID(),this.txtName.getText(),
            Double.valueOf(this.txtPrice.getText()),productToDelete.getSupermarketID());
            
            //Remove the old one, add the updated
            tempTree.remove(productToDelete);
            tempTree.add(updatedProduct);
            
            //Replace the original tree for the temporal and reload the combobox
            Util.Utility.setTreeFoods(tempTree);
            loadComboProduct();
            callConfirmation("Confirmation", "Product successfully updated");
            
        }
    }
    
     private void loadComboProduct() {
        BST treeProductsTemp = new BST();
        treeProductsTemp = Util.Utility.getTreeProducts();
        if (!treeProductsTemp.isEmpty()) {
            tourTree(treeProductsTemp.getRoot(), cmbProducts);
            this.cmbProducts.setValue(null);
        } else {
            callInformation("Information", "No products to show");
        }
    }

    private void tourTree(BTreeNode node, ComboBox<String> cmbProducts) {
        if (node != null) {
            tourTree(node.left, cmbProducts);
            tourTree(node.right, cmbProducts);
            Product product = (Product) node.data;
            if (!cmbProducts.getItems().contains(product.getName())) {
                cmbProducts.getItems().add(product.getName());
            }
        }
    }
    
    private void callConfirmation(String fxmlName, String text) {
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
    
    private void callInformation(String fxmlName, String text) {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + fxmlName + ".fxml"));
            Parent root1;
            root1 = (Parent) loader.load();
            //Se llama al controller de la nueva ventana abierta
            InformationController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Information");
            //Se le asigna la información a la controladora
            controller.fill(text);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void maskNumbers(TextField txtField) {
        txtField.setOnKeyTyped((KeyEvent event) -> {
            if ("0123456789".contains(event.getCharacter()) == false) {
                event.consume();
            }
            if (event.getCharacter().trim().length() == 0) {
                if (txtField.getText().length() == 6) {
                    txtField.positionCaret(txtField.getText().length());
                }
            } else {
                if (txtField.getText().length() == 4) {
                    txtField.positionCaret(txtField.getText().length());
                }
                if (txtField.getText().length() >= 7) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void cmbProducts(ActionEvent event) {
        Product product = Util.Utility.getProductByName(cmbProducts.getValue());
        txtName.setText(product.getName());
        txtPrice.setText(String.valueOf(product.getPrice()));
    }
}//end class
