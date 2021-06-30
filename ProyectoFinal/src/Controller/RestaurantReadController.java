package Controller;

import Graphs.AdjacencyListGraph;
import Objects.Restaurant;
import Objects.Supermarket;
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
 * @author Sebastián Navarro Martínez
 */
public class RestaurantReadController implements Initializable {

    @FXML
    private TableView<List<String>> tableRestaurant;
    @FXML
    private TableColumn<List<String>, String> columnType;
    @FXML
    private TableColumn<List<String>, String> columnId;
    @FXML
    private TableColumn<List<String>, String> columnName;
    @FXML
    private TableColumn<List<String>, String> columnLocation;

    AdjacencyListGraph gRestAndSuper = new AdjacencyListGraph(100);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cleanTable();//Limpia la tabla

        gRestAndSuper = Util.Utility.gRestAndSuper;

        if (Util.Utility.gRestAndSuper.isEmpty()) {
            callAlert("There are no Restaurant(s) and Supermarket(s) registered");
        } else {
            try {
                loadTable();//Llena la tabla
            } catch (ListException ex) {

            }
        }
    }

    public void cleanTable() {
        //Reinicia valores de la tabla
        for (int i = 0; i <= this.tableRestaurant.getItems().size(); i++) {
            this.tableRestaurant.getItems().clear();
        }
    }

    private void loadTable() throws ListException {//Llena tabla
        columnType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(0));
            }
        });

        columnId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(1));
            }
        });

        columnName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(2));
            }
        });

        columnLocation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(3));
            }
        });

        tableRestaurant.setItems(getData());
    }

    public ObservableList<List<String>> getData() throws ListException {
        final ObservableList<List<String>> data = FXCollections.observableArrayList();

        List<String> arrayList = new ArrayList<>();

        arrayList = tourGraph(gRestAndSuper, data);

        return data;
    }

    private List<String> tourGraph(AdjacencyListGraph node, ObservableList<List<String>> data) throws ListException {
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < gRestAndSuper.size(); i++) {
            arrayList = new ArrayList<>();
            if (gRestAndSuper.getVertexByIndex(i).data instanceof Restaurant) {
                Restaurant restaurant = (Restaurant) gRestAndSuper.getVertexByIndex(i).data;
                arrayList.add("Restaurant");
                arrayList.add(String.valueOf(restaurant.getID()));
                arrayList.add(restaurant.getName());
                arrayList.add(restaurant.getLocation());
                data.add(arrayList);
            } else {
                Supermarket supermarket = (Supermarket) gRestAndSuper.getVertexByIndex(i).data;
                arrayList.add("Supermarket");
                arrayList.add(String.valueOf(supermarket.getID()));
                arrayList.add(supermarket.getName());
                arrayList.add(supermarket.getLocation());
                data.add(arrayList);
            }
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
