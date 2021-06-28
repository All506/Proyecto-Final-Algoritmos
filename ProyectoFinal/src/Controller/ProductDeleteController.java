package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Domain.ListException;
import Domain.TreeException;
import Objects.Product;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author juanp
 */
public class ProductDeleteController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textName;
    @FXML
    private TextField textPrice;
    @FXML
    private TextArea textAreaSupermarkets;
    @FXML
    private Button btnRemove;
    @FXML
    private ComboBox<String> comboProducts;
    @FXML
    private GridPane gridProduct;

    Product pTemp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboProducts();
    }

    @FXML
    private void btnRemove(ActionEvent event) throws TreeException {
        System.out.println("remove");
        while (Util.Utility.getTreeProducts().contains(textName.getText())) {            
            Util.Utility.getTreeProducts().remove(textName.getText());
        }
//        if (pTemp != null) {
//            //tourTreeRemove(Util.Utility.getTreeProducts().getRoot(), pTemp);
//        }
        comboProducts.getItems().clear();
        loadComboProducts();
    }

    private void tourTreeRemove(BTreeNode node, Product p) throws TreeException {
        if (node != null) {
            tourTreeRemove(node.left, p);
            tourTreeRemove(node.right, p);
            Product p2 = (Product) node.data;
            if (p.getID() == p2.getID()) {
                Util.Utility.getTreeProducts().remove(p);
            }
        }
    }

    

    private void loadComboProducts() {
        BST treeProductsTemp = new BST();
        treeProductsTemp = Util.Utility.getTreeProducts();
        if (!treeProductsTemp.isEmpty()) {
            tourTree(treeProductsTemp.getRoot(), comboProducts);
            this.comboProducts.setValue(null);
        } else {
            System.out.println("No hay productos registrados");
        }
    }

    private void tourTree(BTreeNode node, ComboBox<String> comboProducts) {
        if (node != null) {
            tourTree(node.left, comboProducts);
            Product p = (Product) node.data;
            comboProducts.getItems().add(p.getName());
            tourTree(node.right, comboProducts);
        }
    }

    private void clean() {
        textId.setText("");
        textName.setText("");
        textPrice.setText("");
        textAreaSupermarkets.setText("");
    }
    
    @FXML
    private void loadData(ActionEvent event) {
        Product p = Util.Utility.getProductByName(comboProducts.getValue());//////REVISAR
        pTemp = p;
        if (pTemp != null) {
            clean();
            textId.setText(String.valueOf(p.getID()));
            textName.setText(p.getName());
            textPrice.setText(String.valueOf(p.getPrice()));
            textAreaSupermarkets.setText(Util.Utility.getSupermarketById(String.valueOf(p.getSupermarketID())).getName());
            gridProduct.setVisible(true);
        } else {
            System.out.println("no hay");
        }
    }

}//end class
