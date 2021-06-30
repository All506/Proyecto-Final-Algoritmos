package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Domain.TreeException;
import Objects.Food;
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
        boolean flag = false;
        while (!Util.Utility.getTreeFoods().isEmpty() && Util.Utility.getTreeFoods().contains(comboFood.getValue())) {
            Util.Utility.getTreeFoods().remove(comboFood.getValue());
            flag = true;
        }
        if (flag) {
            callConfirmation("The Food(s) has been deleted");
        }else{
            callAlert("Please check the empty space");
        }
        flag = false;
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
            callAlert("There are no Food(s) registered");
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
