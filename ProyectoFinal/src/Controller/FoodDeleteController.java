package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Domain.TreeException;
import Objects.Food;
import Objects.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class FoodDeleteController implements Initializable {

    @FXML
    private ComboBox<String> comboFood;
    @FXML
    private Button btnRemove;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboFood.getItems().clear();
        loadComboFood();
    }

    @FXML
    private void btnRemove(ActionEvent event) throws TreeException {
        System.out.println("hola");
        while (!Util.Utility.getTreeFoods().isEmpty() && Util.Utility.getTreeFoods().contains(comboFood.getValue())) {
            System.out.println("remove");
            Util.Utility.getTreeFoods().remove(comboFood.getValue());
        }
        comboFood.getItems().clear();
        loadComboFood();
    }

    private void loadComboFood() {
        BST treeFoodTemp = new BST();
        treeFoodTemp = Util.Utility.getTreeFoods();
        if (!treeFoodTemp.isEmpty()) {
            tourTree(treeFoodTemp.getRoot(), comboFood);
            this.comboFood.setValue(null);
        } else {
            System.out.println("No hay productos registrados");
        }
    }

    private void tourTree(BTreeNode node, ComboBox<String> comboFood) {
        if (node != null) {
            tourTree(node.left, comboFood);
            tourTree(node.right, comboFood);
            Food food = (Food) node.data;
            if (!comboFood.getItems().contains(food.getName())) {
                comboFood.getItems().add(food.getName());
            }

        }
    }

}//end class
