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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private TextField txtPrice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFood.getItems().clear();
        maskNumbers(txtPrice);
        loadComboFood();
    }

    @FXML
    private void btnUpdate(ActionEvent event) throws TreeException {
        if (this.txtName.getText().equals("") || this.txtPrice.getText().equals("") || this.cmbFood.getValue().equals("")) {
            callAlert("Error", "Fill all the information to continue");
        } else {
            //Generate a temporal tree
            BST tempTree = Util.Utility.getTreeFoods();
            
            //Call the food to delete
            Food foodToDelete = Util.Utility.getFoodByName(this.cmbFood.getValue());
            
            //Create new food to replace (Basically the old but upgrated)
            Food updatedFood = new Food(foodToDelete.getID(),this.txtName.getText(),
            Double.valueOf(this.txtPrice.getText()),foodToDelete.getRestaurantID());
            
            //Remove the old one, add the updated
            tempTree.remove(foodToDelete);
            tempTree.add(updatedFood);
            
            //Replace the original tree for the temporal and reload the combobox
            Util.Utility.setTreeFoods(tempTree);
            loadComboFood();
            System.out.println("SI SE PUDO CRACK");
            callConfirmation("Confirmation", "Food successfully updated");
            
        }
    }

    @FXML
    private void cmbFood(ActionEvent event) {
        Food food = Util.Utility.getFoodByName(cmbFood.getValue());
        txtName.setText(food.getName());
        txtPrice.setText(String.valueOf(food.getPrice()));
    }

    private void loadComboFood() {
        BST treeFoodTemp = new BST();
        treeFoodTemp = Util.Utility.getTreeFoods();
        if (!treeFoodTemp.isEmpty()) {
            tourTree(treeFoodTemp.getRoot(), cmbFood);
            this.cmbFood.setValue(null);
        } else {
            callInformation("Information", "No foods to show");
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
    
}//end class
