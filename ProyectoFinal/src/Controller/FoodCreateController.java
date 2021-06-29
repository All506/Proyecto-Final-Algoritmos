/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.TreeException;
import Objects.Food;
import Objects.Restaurant;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import list.ListException;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class FoodCreateController implements Initializable {

    @FXML
    private TextField textID;
    @FXML
    private TextField textName;
    @FXML
    private Spinner<Integer> spinnerPrice;
    @FXML
    private Button btnAdd;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;

    RadioButton[] restaurant;
    int count = 0;
    SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        value.setValue(1);
        spinnerPrice.setValueFactory(value);

        if (!Util.Utility.getTreeFoods().isEmpty()) {
            textID.setText(String.valueOf(Util.Utility.getFoodID() + 1));
        } else {
            textID.setText(String.valueOf(1));
        }
        //Food Radio Buttons Array
        restaurant = new RadioButton[20];//CAMBIAR

        try {
            fillSupermarket();
        } catch (ListException ex) {
            Logger.getLogger(FoodCreateController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Cargar el combobox
        loadComboBoxCourse();

        //Mascaras
        maskText(textName);
    }

    @FXML
    private void btnAdd(ActionEvent event) throws TreeException, ListException {
        if (!textName.getText().equals("") && spinnerPrice.getValue() > 0) {
            for (int i = 0; restaurant[i] != null; i++) {
                Food food;
                if (restaurant[i].isSelected()) {
                    food = new Food(Integer.parseInt(textID.getText()), textName.getText(), spinnerPrice.getValue(), Util.Utility.getRestaurantId(restaurant[i].getText()).getID());
                    System.out.println(food.toString());
                    Util.Utility.addFood(food);
                }
            }
        } else {
            //Alert de que hay espacios vacios
            callAlert("Error", "The Name space is empty!!!");
            System.out.println("alert");
        }
        cleanDisplay();
    }

    //Carga el combo con los supermarcados
    public void loadComboBoxCourse() {

    }

    public void maskText(TextField txtField) {
        txtField.setOnKeyTyped((KeyEvent event) -> {
            if (!"0123456789".contains(event.getCharacter()) == false) {
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
            }
        });
    }

    public void cleanDisplay() {
        this.textName.setText("");
        value.setValue(1);
        if (!Util.Utility.getTreeFoods().isEmpty()) {
            textID.setText(String.valueOf(Util.Utility.getFoodID() + 1));
        } else {
            textID.setText(String.valueOf(1));
        }
        spinnerPrice.setValueFactory(value);
    }

    private void fillSupermarket() throws ListException {
        if (Util.Utility.gRestAndSuper.size() == 0) {
            Text empty = new Text("No restaurants added yet");
            anchorPane.getChildren().add(empty);
        } else {
            for (int i = 0; i < Util.Utility.gRestAndSuper.size(); i++) {
                if (Util.Utility.gRestAndSuper.getVertexByIndex(i).data instanceof Restaurant) {
                    Restaurant rest = (Restaurant) Util.Utility.gRestAndSuper.getVertexByIndex(i).data;
                    RadioButton radio = new RadioButton(rest.getName());
                    radio.setLayoutY(count * 20);
                    restaurant[count] = radio;
                    anchorPane.getChildren().add(radio);
                    count++;
                }

            }
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
}
