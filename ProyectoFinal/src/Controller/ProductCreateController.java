package Controller;

import Domain.TreeException;
import Objects.Product;
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
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author juanp
 */
public class ProductCreateController implements Initializable {

    @FXML
    private TextField textID;
    @FXML
    private ComboBox<String> comboSupermarket;
    @FXML
    private TextField textName;
    @FXML
    private Button btnAdd;
    @FXML
    private Spinner<Integer> spinnerPrice;

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
        
        //Cargar el combobox
        loadComboBoxCourse();

        //Mascaras
        maskText(textName);

    }

    @FXML
    private void btnAdd(ActionEvent event) throws TreeException {
        if (!textName.getText().equals("") && spinnerPrice.getValue() > 0) {
            Product product = new Product(Integer.parseInt(textID.getText()),textName.getText(), spinnerPrice.getValue(), 10/*Util.Utility.getSupermarketById(0).getID()*/);
            System.out.println(product.toString());
            Util.Utility.addProduct(product);
            System.out.println("agrego");
        } else {
            //Alert de que hay espacios vacios
            System.out.println("alert");
        }
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

}//end class
