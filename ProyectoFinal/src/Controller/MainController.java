/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Main.MainClassFX;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import Misc.ChangeCallback;
import java.io.File;
import java.util.Calendar;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class MainController implements Initializable, ChangeCallback {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private AnchorPane root;
    @FXML
    private ScrollPane scpMenu;
    @FXML
    private BorderPane bpRoot;

    HamburgerBackArrowBasicTransition transition;

    boolean f = false;
    @FXML
    private Text txtWelcome;
    
    MediaView mediaView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String path = "src/Resources/backgroundVideo.mp4";
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //Instantiating MediaView class
        mediaView = new MediaView(mediaPlayer);

        mediaView.setPreserveRatio(true);
        mediaView.fitWidthProperty().bind(root.minHeightProperty());
        mediaView.fitHeightProperty().bind(root.minHeightProperty());
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        mediaPlayer.setAutoPlay(true);

        root.getChildren().add(mediaView);
        mediaView.toBack();

        //⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆
        setWelcome();
        if (!MainClassFX.isSplashLoaded) {
            loadSplashScreen();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/sidepanel.fxml"));
            AnchorPane box = loader.load();
            SidePanelController controller = loader.getController();
            controller.setCallback(this);
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isOpened()) {
                drawer.close();

                transition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (transition.getStatus().equals(Animation.Status.STOPPED)) {
                            drawer.toBack();
                            scpMenu.toFront();
                            mediaView.toBack();

                        }

                    }
                });

            } else {

                drawer.open();
                drawer.toFront();
                scpMenu.toBack();
                mediaView.toBack();

                transition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (transition.getStatus().equals(Animation.Status.STOPPED)) {
                            drawer.toFront();
                            scpMenu.toBack();
                            mediaView.toBack();
                        }

                    }
                });

            }
        });
        scpMenu.setVisible(false);
    }

    private void loadSplashScreen() {
        try {
            MainClassFX.isSplashLoaded = true;

            StackPane pane = FXMLLoader.load(getClass().getResource(("/UI/splash.fxml")));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.1), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.1), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/UI/main.fxml")));
                    root.getChildren().setAll(parentContent);
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateColor(String newColor) {
        loadPage(newColor);
    }

    public void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/UI/" + page + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        scpMenu.setContent(root);
    }

    public void setWelcome() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12) {
            txtWelcome.setText("Good Moorning, " + Util.Utility.userName);
        } else if (timeOfDay < 16) {
            txtWelcome.setText("Good afternoon, " + Util.Utility.userName);
        } else if (timeOfDay < 19) {
            txtWelcome.setText("Good evening, " + Util.Utility.userName);
        } else {
            txtWelcome.setText("Good night, " + Util.Utility.userName);
        }
    }

    @Override
    public void setPaneVisible(Boolean b) {
        transition.setRate(transition.getRate() * -1);
        transition.play();

        if (drawer.isOpened()) {
            drawer.close();

            transition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (transition.getStatus().equals(Animation.Status.STOPPED)) {
                        drawer.toBack();
                        scpMenu.toFront();
                        mediaView.toBack();
                    }
                }
            });

        } else {

            drawer.open();
            drawer.toFront();
            scpMenu.toBack();
            mediaView.toBack();

            transition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    if (transition.getStatus().equals(Animation.Status.STOPPED)) {
                        drawer.toFront();
                        scpMenu.toBack();
                        mediaView.toBack();
                    }

                }
            });

        }

        scpMenu.setVisible(true);
    }

}
