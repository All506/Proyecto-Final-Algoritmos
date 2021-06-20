package SidePanel;

import com.jfoenix.controls.JFXButton;
import MainMenu.ColorChangeCallback;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SidePanelController implements Initializable {

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;

    private ColorChangeCallback callback;
    @FXML
    private JFXButton b4;
    @FXML
    private JFXButton b5;
    @FXML
    private JFXButton b6;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setCallback(ColorChangeCallback callback) {
        this.callback = callback;
    }

    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        switch (btn.getText()) {
            case "Color 1":
                callback.updateColor("#00FF00");
                break;
            case "Color 2":
                callback.updateColor("#0000FF");
                break;
            case "Color 3":
                callback.updateColor("#FF0000");
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
