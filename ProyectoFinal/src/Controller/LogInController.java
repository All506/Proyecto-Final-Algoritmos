/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.ListException;
import Graphs.GraphException;
import Objects.Security;
import XML.FileXML;
import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class LogInController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogIn;
    @FXML
    private AnchorPane AnchorBackground;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String path = "src/Resources/Video" + Util.Utility.random(6) + ".mp4";
        Media media = new Media(new File(path).toURI().toString());
        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //Instantiating MediaView class
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setPreserveRatio(false);
        mediaView.fitWidthProperty().bind(AnchorBackground.widthProperty());
        mediaView.fitHeightProperty().bind(AnchorBackground.heightProperty());
        
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        mediaPlayer.setAutoPlay(true);

        AnchorBackground.getChildren().addAll(mediaView);

        try {
            FileXML fXML = new FileXML();
            CircularLinkList lUsers = new CircularLinkList();

            if (fXML.exist("Security.xml")) {

                lUsers = fXML.readXMLtoSecurityList();

                try {
                    for (int i = 1; i <= lUsers.size(); i++) {
                        Util.Utility.lSecurity.add((Security) lUsers.getNode(i).data);
                    }

                } catch (ListException ex) {
                    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Security auxiliar = new Security("admin", "prueba","true"); //En caso de que no haya ninguno registrado, admin-prueba se utilizarán para entrar
            Util.Utility.addSecurity(auxiliar);
           
        } catch (ListException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnLogIn(ActionEvent event) throws IOException, ListException, list.ListException, GraphException {
        FileXML fXML = new FileXML();
        if (txtUser.getText().equalsIgnoreCase("") || txtPassword.getText().equalsIgnoreCase("")) {
            callAlert("Error", "The User spacer is empty!!!");
           
        } else {
            Security logInUser = new Security(txtUser.getText(), txtPassword.getText(),"true");
            if (Util.Utility.lSecurity.contains(logInUser)) {
                int index = Util.Utility.lSecurity.indexOf(logInUser);
                Security secTemp = (Security)Util.Utility.lSecurity.getNode(index).data;
                Util.Utility.userName = txtUser.getText();
                Util.Utility.kindUser = secTemp.getKindUser();
                callMenu();
            } else {
                callAlert("Error", "Try again...");
              
            }
        }

    }

    private void callMenu() throws IOException, list.ListException, GraphException, ListException {
        FileXML fXML = new FileXML();
        fXML.loadFiles();
        Stage stage = (Stage) this.txtUser.getScene().getWindow();
        stage.close();
        //Se abre el nuevo stage
        //Se llama al controller de la nueva ventana abierta
        Parent root = FXMLLoader.load(getClass().getResource("/UI/main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.show();
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
