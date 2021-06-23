package Controller;

import Domain.TreeException;
import Objects.Product;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juanp
 */
public class ProductCreateController implements Initializable {

    @FXML
    private TextField textID;
    @FXML
    private TextField textName;
    @FXML
    private Button btnAdd;
    @FXML
    private Spinner<Integer> spinnerPrice;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;

    RadioButton[] supermarket;
    int count = 0;
    
    SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        value.setValue(1);
        spinnerPrice.setValueFactory(value);

        try {
            textID.setText(String.valueOf(Util.Utility.getTreeProducts().size()));
        } catch (TreeException ex) {
            textID.setText(String.valueOf("0"));
        }

        //Supermarkets Radio Buttons
        supermarket = new RadioButton[20];//metodo donde llene el arreglo jeje
        fillSupermarket();

        //Cargar el combobox
        loadComboBoxCourse();

        //Mascaras
        maskText(textName);

    }

    @FXML
    private void btnAdd(ActionEvent event) throws TreeException {
        if (!textName.getText().equals("") && spinnerPrice.getValue() > 0) {
            for (int i = 0; supermarket[i] != null; i++) {
                Product product;
                if (supermarket[i].isSelected()) {
                    product = new Product(Integer.parseInt(textID.getText()), textName.getText(), spinnerPrice.getValue(), i/*Util.Utility.getSupermarketById(supermarket[i].getText()).getID()*/);
                    System.out.println(product.toString());
                    Util.Utility.addProduct(product);
                    //callAlert("Error", "The Product has been registered ");
                    System.out.println("agrego");
                }
            }

        } else {
            //Alert de que hay espacios vacios
            callAlert("Error", "The Name space is empty!!!");
            System.out.println("alert");
        }
        System.out.println(Util.Utility.getTreeProducts().toString());
        cleanDisplay();
    }

    //Carga el combo con los supermarcados
    public void loadComboBoxCourse() {

    }

    public void cleanDisplay() {
        this.textName.setText("");
        try {
            textID.setText(String.valueOf(Util.Utility.getTreeProducts().size()));
        } catch (TreeException ex) {
            textID.setText(String.valueOf("0"));
        }
        value.setValue(1);
        spinnerPrice.setValueFactory(value);
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

    private void fillSupermarket() {
        RadioButton radio = new RadioButton(String.valueOf("Fresh Market Cartago"));//38 caracteres
        System.out.println(count);
        radio.setLayoutY(count * 20);
        anchorPane.setPrefHeight(anchorPane.getHeight() + 20);
        supermarket[count] = radio;
        anchorPane.getChildren().add(radio);
        count++;

        RadioButton radio2 = new RadioButton(String.valueOf("Fresh Market San Jose"));//38 caracteres
        System.out.println(count);
        radio2.setLayoutY(count * 20);
        anchorPane.setPrefHeight(anchorPane.getHeight() + 20);
        supermarket[count] = radio2;
        anchorPane.getChildren().add(radio2);
        count++;
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

}//end class
