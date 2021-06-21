package Controller;

import Domain.BST;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author juanp
 */
public class ProductReadController implements Initializable {

    @FXML
    private TableColumn<String, String> columnId;
    @FXML
    private TableColumn<String, String> columnName;
    @FXML
    private TableColumn<String, String> columnPrice;
    @FXML
    private TableColumn<String, String> columnSupermarket;
    @FXML
    private TableView<String> tableProduct;

    //Temp Tree
    BST treeProducts = new BST();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cleanTable();
        
        treeProducts = Util.Utility.getTreeProducts();
        
        if (treeProducts.isEmpty()) {
            System.out.println("No hay productos registrados");
            //callAlert("alert", "Error", "There are no registered products");
        } else {
            loadTable();
        }
        
    }

    public void cleanTable() {
        //Reinicia valores de la tabla
        for (int i = 0; i <= this.tableProduct.getItems().size(); i++) {
            this.tableProduct.getItems().clear();
        }
    }

    private void loadTable() {//Llena tabla
        
    }

}//end class
