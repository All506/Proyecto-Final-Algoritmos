package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Objects.Food;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import list.ListException;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class FoodReadController implements Initializable {

    @FXML
    private TableView<List<String>> tableFood;
    @FXML
    private TableColumn<List<String>, String> columnId;
    @FXML
    private TableColumn<List<String>, String> columnName;
    @FXML
    private TableColumn<List<String>, String> columnPrice;
    @FXML
    private TableColumn<List<String>, String> columnSupermarket;

    //Temp Tree
    BST treeFood = new BST();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cleanTable();//Limpia la tabla

        treeFood = Util.Utility.getTreeFoods();//Carga el arbol temporal

        if (treeFood.isEmpty()) {
            System.out.println("No hay comidas registradas");
            //callAlert("alert", "Error", "There are no registered products");
        } else {
            try {
                loadTable();//Llena la tabla
            } catch (ListException ex) {
                
                
            }
        }
    }

    public void cleanTable() {
        //Reinicia valores de la tabla
        for (int i = 0; i <= this.tableFood.getItems().size(); i++) {
            this.tableFood.getItems().clear();
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

        tableFood.setItems(getData());
    }

    public ObservableList<List<String>> getData() throws ListException {
        final ObservableList<List<String>> data = FXCollections.observableArrayList();

        List<String> arrayList = new ArrayList<>();

        arrayList = tourTree(Util.Utility.getTreeFoods().getRoot(), data);

        return data;
    }

    private List<String> tourTree(BTreeNode node, ObservableList<List<String>> data) throws ListException {
        List<String> arrayList = new ArrayList<>();
        if (node != null) {
            tourTree(node.left, data);
            Food f = (Food) node.data;
            arrayList.add(String.valueOf(f.getID()));
            arrayList.add(String.valueOf(f.getName()));
            arrayList.add(String.valueOf(f.getPrice()));
            arrayList.add(String.valueOf(Util.Utility.getRestaurantId2(f.getRestaurantID()).getName()));
//            arrayList.add(String.valueOf(f.getRestaurantID()));
            data.add(arrayList);
            tourTree(node.right, data);
        }
        return arrayList;
    }

}//end class
