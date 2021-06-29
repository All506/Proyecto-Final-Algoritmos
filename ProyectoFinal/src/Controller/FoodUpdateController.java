package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Objects.Food;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import list.ListException;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class FoodUpdateController implements Initializable {

    @FXML
    private ComboBox<String> cmbFood;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnUpdate;
    @FXML
    private Spinner<Integer> spinnerPrice;

    SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFood.getItems().clear();
        loadComboFood();
    }

    @FXML
    private void btnUpdate(ActionEvent event) {

    }

    @FXML
    private void cmbFood(ActionEvent event) {
        Food food = Util.Utility.getFoodByName(cmbFood.getValue());
        System.out.println(food.toString());
        txtName.setText(food.getName());
        value.setValue(1);
        spinnerPrice.setValueFactory(value);
    }
 
    private void loadComboFood() {
        BST treeFoodTemp = new BST();
        treeFoodTemp = Util.Utility.getTreeFoods();
        if (!treeFoodTemp.isEmpty()) {
            tourTree(treeFoodTemp.getRoot(), cmbFood);
            this.cmbFood.setValue(null);
        } else {
            System.out.println("No hay productos registrados");
        }
    }

    private void tourTree(BTreeNode node, ComboBox<String> cmbFood) {
        if (node != null) {
            tourTree(node.left, cmbFood);
            tourTree(node.right, cmbFood);
            Food food = (Food) node.data;
            if (!cmbFood.getItems().contains(food.getName())) {
                cmbFood.getItems().add(food.getName());
            }
        }
    }

}//end class
