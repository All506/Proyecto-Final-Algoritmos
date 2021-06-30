package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Objects.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import list.ListException;

/**
 * FXML Controller class
 *
 * @author juanp
 */
public class ProductReadController implements Initializable {

    @FXML
    private TableColumn<List<String>, String> columnId;
    @FXML
    private TableColumn<List<String>, String> columnName;
    @FXML
    private TableColumn<List<String>, String> columnPrice;
    @FXML
    private TableColumn<List<String>, String> columnSupermarket;
    @FXML
    private TableView<List<String>> tableProduct;

    //Temp Tree
    BST treeProducts = new BST();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cleanTable();//Limpia la tabla

        treeProducts = Util.Utility.getTreeProducts();//Carga el arbol temporal


        if (treeProducts.isEmpty()) {
            callAlert("There are no Product(s) registered");
        } else {
            try {
                loadTable();//Llena la tabla
            } catch (ListException ex) {
                
            }
        }

    }

    public void cleanTable() {
        //Reinicia valores de la tabla
        for (int i = 0; i <= this.tableProduct.getItems().size(); i++) {
            this.tableProduct.getItems().clear();
        }
    }

    private void loadTable() throws ListException {//Llena tabla
        columnId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(0));
            }
        });

        columnName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(1));
            }
        });

        columnPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(2));
            }
        });

        columnSupermarket.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(3));
            }
        });
        
        tableProduct.setItems(getData());
    }

    public ObservableList<List<String>> getData() throws ListException {
        final ObservableList<List<String>> data = FXCollections.observableArrayList();

        List<String> arrayList = new ArrayList<>();
        
        arrayList = tourTree(Util.Utility.getTreeProducts().getRoot(),data);
        System.out.println(arrayList.toString());

        return data;
    }

    private List<String> tourTree(BTreeNode node, ObservableList<List<String>> data) throws ListException {
        List<String> arrayList = new ArrayList<>();
        if (node != null) {
            tourTree(node.left,data);
            Product p = (Product) node.data;
            arrayList.add(String.valueOf(p.getID()));
            arrayList.add(String.valueOf(p.getName()));
            arrayList.add(String.valueOf(p.getPrice()));
            arrayList.add(String.valueOf(Util.Utility.getSupermarketId2(p.getSupermarketID()).getName()));
            //arrayList.add(String.valueOf(p.getSupermarketID()));
            data.add(arrayList);
            tourTree(node.right,data);
        }
        return arrayList;
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
    
}//end class
