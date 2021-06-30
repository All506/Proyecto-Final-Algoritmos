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
    private Button btnAdd;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;

    RadioButton[] restaurant;
    int count = 0;
    @FXML
    private TextField txtFieldPrice;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Initialize the improved awesome Sebas' spinner
        //=========================================================================================
        maskNumbers(txtFieldPrice);
        this.txtFieldPrice.setText("500");
        this.btnUp.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) + 500));
        this.btnDown.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) - 500));
        //=========================================================================================

        if (!Util.Utility.getTreeFoods().isEmpty()) {
            textID.setText(String.valueOf(Util.Utility.getFoodID() + 1));
        } else {
            textID.setText(String.valueOf(1));
        }

        try {
            //Food Radio Buttons Array
            restaurant = new RadioButton[Util.Utility.gRestAndSuper.size()];//CAMBIAR
        } catch (ListException ex) {

        }

        try {
            fillSupermarket();
        } catch (ListException ex) {
            Logger.getLogger(FoodCreateController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Mascaras
        maskText(textName);
    }

    @FXML
    private void btnAdd(ActionEvent event) throws TreeException, ListException {
        boolean flag = false;
        if (!this.txtFieldPrice.getText().equals("")) {
            if (!textName.getText().equals("") && Integer.parseInt(this.txtFieldPrice.getText()) > 0) {
                for (int i = 0; restaurant[i] != null; i++) {
                    Food food;
                    if (restaurant[i].isSelected()) {
                        food = new Food(Integer.parseInt(textID.getText()), textName.getText(), Integer.parseInt(this.txtFieldPrice.getText()), Util.Utility.getRestaurantId(restaurant[i].getText()).getID());
                        Util.Utility.addFood(food);
                        flag = true;
                    }
                }
                if (flag) {
                    callConfirmation("The Food(s) has been registered");
                    cleanDisplay();
                }else{
                    callAlert("Choose a restaurant to continue");
                }

            } else {
                callAlert("Fill all the spaces to continue");
            }
            flag = false;
        } else {
            callAlert("Fill all the spaces to continue");
        }
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
                if(txtField.getText().length() >= 11){
                    event.consume();
                }
            }
        });
    }

    public void cleanDisplay() {
        this.textName.setText("");
        if (!Util.Utility.getTreeFoods().isEmpty()) {
            textID.setText(String.valueOf(Util.Utility.getFoodID() + 1));
        } else {
            textID.setText(String.valueOf(1));
        }
        this.txtFieldPrice.setText("500");
        this.btnUp.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) + 500));
        this.btnDown.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) - 500));
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

    public boolean checkRadioButtons(RadioButton[] rbuttonArray){
        for (int i = 0; i < rbuttonArray.length; i++) {
            if(rbuttonArray[i].isSelected()){
                return true;
            }
        }
        return false;
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

    @FXML
    private void btnUp(ActionEvent event) {
        this.txtFieldPrice.setText(this.btnUp.getText());

        this.btnUp.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) + 500));

        if (Integer.parseInt(this.txtFieldPrice.getText()) < 500) {
            this.btnDown.setText("0");
        } else {
            this.btnDown.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) - 500));
        }
    }

    @FXML
    private void txtFieldPrice(KeyEvent event) {
        if (!this.txtFieldPrice.getText().equals("") || Integer.parseInt(this.txtFieldPrice.getText())<1000000) {
            this.btnUp.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) + 500));

            if (Integer.parseInt(this.txtFieldPrice.getText()) < 500) {
                this.btnDown.setText("0");
            } else {
                this.btnDown.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) - 500));
            }
        }
    }

    @FXML
    private void btnDown(ActionEvent event){
        this.txtFieldPrice.setText(this.btnDown.getText());

        this.btnUp.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) + 500));

        if (Integer.parseInt(this.txtFieldPrice.getText()) < 500) {
            this.btnDown.setText("0");
        } else {
            this.btnDown.setText(String.valueOf(Integer.parseInt(this.txtFieldPrice.getText()) - 500));
        }
    }

}//end class
