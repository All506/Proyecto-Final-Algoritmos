package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Domain.TreeException;
import Objects.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

        while (!Util.Utility.getTreeProducts().isEmpty() && Util.Utility.getTreeProducts().contains(textName.getText())) {
            System.out.println("remove");
            Util.Utility.getTreeProducts().remove(textName.getText());
        }
//        if (pTemp != null) {
//            //tourTreeRemove(Util.Utility.getTreeProducts().getRoot(), pTemp);
//        }
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
            System.out.println("No hay productos registrados");
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

    private void clean() {
        textId.setText("");
        textName.setText("");
        textPrice.setText("");
        textAreaSupermarkets.setText("");
    }

    @FXML
    private void loadData(ActionEvent event) {

        Product p = Util.Utility.getProductByName(comboProducts.getValue());//////REVISAR

        if (p != null) {
            clean();
            textId.setText(String.valueOf(p.getID()));
            textName.setText(p.getName());
            textPrice.setText(String.valueOf(p.getPrice()));
            //textAreaSupermarkets.setText(Util.Utility.getSupermarketById(String.valueOf(p.getSupermarketID())).getName());
            gridProduct.setVisible(true);
        } else {
            System.out.println("no hay");
        }
    }

}//end class
