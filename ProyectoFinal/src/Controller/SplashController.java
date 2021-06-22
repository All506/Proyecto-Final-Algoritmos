package Controller;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SplashController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private Text idTextSplash;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12) {
            idTextSplash.setText("Good Moorning, " + Util.Utility.userName);
        } else if (timeOfDay < 16) {
            idTextSplash.setText("Good afternoon, " + Util.Utility.userName);
        } else if (timeOfDay < 19) {
            idTextSplash.setText("Good evening, " + Util.Utility.userName);
        } else {
            idTextSplash.setText("Good night, " + Util.Utility.userName);
        }
    }

}
