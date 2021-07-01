/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Graphs.AdjacencyListGraph;
import Graphs.Graph;
import Graphs.GraphException;
import Objects.Place;
import java.awt.MouseInfo;
import java.awt.Point;
import static java.awt.SystemColor.control;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import list.ListException;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class PlaceReadController implements Initializable {

    Button btn;

    float auxI;

    int hip;

    int edgesCounter = 0;

    Button btnArray[];

    RadioButton rdbArray[];

    int counter;

    Line lne;
    @FXML
    private Rectangle rectangleGraph;

    AdjacencyListGraph graph;
    @FXML
    private Button btnCenter;
    @FXML
    private AnchorPane apRoot;

    public Text txtTitle;
    @FXML
    private ScrollPane scrRadioButtons;
    @FXML
    private TableView<String[]> tblPlaces;
    @FXML
    private Button btnRandomize;
    @FXML
    private RadioButton rdbButton;
    @FXML
    private VBox vbxRadioButtons;
    @FXML
    private ImageView animation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animation.setVisible(true);
        apRoot.setVisible(false);
        scrRadioButtons.setStyle("-fx-control-inner-background: transparent;");

        graph = Util.Utility.getPlacesGraph();

        try {
            for (int i = 0; i < graph.size(); i++) {
                for (int j = 0; j < graph.size(); j++) {
                    if (!graph.containsEdge(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data)) {
                        if (!Util.Utility.equals(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data)) {
                            graph.addEdge(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data);
                            graph.addWeight(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data, 15 + Util.Utility.random(40));
                        }
                    }
                }
            }

        } catch (GraphException | ListException ex) {
            Logger.getLogger(PlaceReadController.class.getName()).log(Level.SEVERE, null, ex);
        }

        rectangleGraph.setOnMouseMoved(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (graph != null && !graph.isEmpty())
                        try {

                    apRoot.getChildren().clear();

                    drawGraph(aux);
                    txtTitle = new Text(100, 100, "");

                    apRoot.getChildren().add(txtTitle);

                } catch (GraphException | ListException ex) {
                    Logger.getLogger(PlaceReadController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        try {
            loadRadioButtons();
        } catch (ListException ex) {
            Logger.getLogger(PlaceReadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    AdjacencyListGraph aux;

    public void loadRadioButtons() throws ListException {

        rdbArray = new RadioButton[graph.size()];
        vbxRadioButtons.getChildren().clear();
        for (int i = 0; i < graph.size(); i++) {
            Place p = (Place) graph.getVertexByIndex(i).data;
            rdbButton = new RadioButton(p.getName());
            rdbButton.setId(p.toString());
            vbxRadioButtons.getChildren().add(rdbButton);
            rdbButton.getStyleClass().add("button");
            rdbButton.setPrefWidth(209);
            rdbArray[i] = rdbButton;
            rdbButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    rdbButton = (RadioButton) event.getSource();
                    rdbEvent();
                    animation.setVisible(false);

                }
            });
        }

    }

    public void rdbEvent() {

        try {
            String[][] matrix = new String[graph.size() * graph.size()][2];
            matrix[0][1] = "Distance";
            matrix[0][0] = "Path";
            int arrowCounter = 1;

            aux = new AdjacencyListGraph(graph.size());

            for (int j = 0; j < graph.size(); j++) {
                if (rdbArray != null) {
                    if (rdbArray[j].isSelected()) {
                        Place p = (Place) graph.getVertexByIndex(j).data;
                        aux.addVertex(p);

                    }
                }
            }

            String edges = "";
            for (int j = 0; j < aux.size(); j++) {
                for (int k = 0; k < aux.size(); k++) {
                    if (graph.containsEdge(aux.getVertexByIndex(j).data, graph.getVertexByIndex(k).data)) {
                        Place p1 = (Place) aux.getVertexByIndex(j).data;
                        Place p2 = (Place) aux.getVertexByIndex(k).data;
                        if (!edges.contains((p2.getName() + "-" + p1.getName()))) {
                            if (!Util.Utility.equals(p1, p2)) {
                                edges += p1.getName() + "-" + p2.getName() + "//";
                                matrix[arrowCounter][0] = p1.getName() + "-" + p2.getName();
                                matrix[arrowCounter++][1] = graph.getWeight(p1, p2) + " Km";
                                aux.addEdge(p1, p2);
                                aux.addWeight(p1, p2, graph.getWeight(p1, p2));
                            }
                        }
                    }
                }
            }
            loadTable(tblPlaces, matrix);

            drawGraph(aux);
        } catch (ListException | GraphException ex) {
            Logger.getLogger(PlaceReadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void drawGraph(AdjacencyListGraph graph) throws ListException, GraphException {
        apRoot.getChildren().clear();
        apRoot.setVisible(true);

        counter = 0;
        hip = 160;//longitud del centro a cada vertice

        btnArray = new Button[graph.size()];
        if (graph != null && !graph.isEmpty()) {
            drawVertices(graph);
        }

        drawEdges(graph);

        txtTitle = new Text("");
        apRoot.getChildren().add(rectangleGraph);

        rectangleGraph.toBack();

        apRoot.getChildren().add(txtTitle);

    }

    public void drawVertices(Graph graph) throws ListException {//Dibuja los vertices

        for (float i = 0; i < 360f - 0.1; i += 360f / graph.size()) {

            btn = new Button();
            btnArray[counter] = btn;
            apRoot.getChildren().add(btn);
            Place p = (Place) graph.getVertexByIndex(counter).data;
            btn.setId(p.toString());
            btn.setText(p.getName());
            counter++;

            if (i >= 0 && i < 90) {
                auxI = i;
                double x = Math.sin(Math.toRadians(auxI)) * hip;
                double y = Math.cos(Math.toRadians(auxI)) * hip;

                btn.setLayoutX(btnCenter.getLayoutX() + y * 1.7);
                btn.setLayoutY(btnCenter.getLayoutY() - x);
            }
            if (i >= 90 && i < 180) {
                auxI = i - 90;
                double x = Math.sin(Math.toRadians(auxI)) * hip;
                double y = Math.cos(Math.toRadians(auxI)) * hip;

                btn.setLayoutX(btnCenter.getLayoutX() - x * 1.7);
                btn.setLayoutY(btnCenter.getLayoutY() - y);
            }
            if (i >= 180 && i < 270) {
                auxI = i - 180;
                double x = Math.sin(Math.toRadians(auxI)) * hip;
                double y = Math.cos(Math.toRadians(auxI)) * hip;

                btn.setLayoutX(btnCenter.getLayoutX() - y * 1.7);
                btn.setLayoutY(btnCenter.getLayoutY() + x);
            }
            if (i >= 270 && i < 360) {
                auxI = i - 270;
                double x = Math.sin(Math.toRadians(auxI)) * hip;
                double y = Math.cos(Math.toRadians(auxI)) * hip;

                btn.setLayoutX(btnCenter.getLayoutX() + x * 1.7);
                btn.setLayoutY(btnCenter.getLayoutY() + y);
            }
        }
    }

    Line line = new Line();

    public void drawEdges(AdjacencyListGraph graph) throws GraphException, ListException {//Dibuja las aristas

        edgesCounter = 0;
        int i;
        int j;
        for (i = 0; i < graph.size(); i++) {
            for (j = 0; j < graph.size(); j++) {
                if (btnArray[j].getId() != btnArray[i].getId()) {
                    //Arista hacia otro vertice    
                    if (graph.containsEdge(btnArray[i].getId(), btnArray[j].getId())) {
                        edgesCounter++;

                        lne = new Line();

                        apRoot.getChildren().add(lne);
                        lne.setLayoutX(btnArray[j].getLayoutX());
                        lne.setLayoutY(btnArray[j].getLayoutY());
                        lne.toBack();
                        lne.setStartX(15);
                        lne.setStartY(15);
                        lne.setEndX((btnArray[i]).getLayoutX() - btnArray[j].getLayoutX() + 15);
                        lne.setEndY((btnArray[i]).getLayoutY() - btnArray[j].getLayoutY() + 15);
                        lne.setStrokeWidth(8);
                        lne.setStroke(Paint.valueOf("#666666"));
                        lne.setOpacity(0.4);
                        lne.setId(String.valueOf(graph.getWeight(btnArray[i].getId(), btnArray[j].getId())) + " Km");
                        lne.setOnMouseMoved(new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                                line.setStroke(Paint.valueOf("#666666"));

                                line = (Line) event.getSource();
                                line.setStroke(Paint.valueOf("#008080"));
                                line.setOpacity(0.9);
                                txtTitle.setText(line.getId());
                                Bounds boundsInScreen = rectangleGraph.localToScreen(rectangleGraph.getBoundsInLocal());
                                Point punto = MouseInfo.getPointerInfo().getLocation();
                                double x = punto.x - boundsInScreen.getMinX();
                                double y = punto.y - boundsInScreen.getMinY();
                                txtTitle.setLayoutX(x - 130);
                                txtTitle.setLayoutY(y - 130);
                                txtTitle.setFill(Paint.valueOf("#000035"));
                                txtTitle.setFont(Font.font("Century Gothic", FontWeight.EXTRA_BOLD, 25));
                            }
                        });

                    }

                }
            }
        }
        edgesCounter = edgesCounter / 2;
    }

    Line a1;
    Line a2;

    private void randomizeEdges() throws GraphException, ListException {

        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.size(); j++) {

                if (!(graph.getVertexByIndex(i).data.equals(graph.getVertexByIndex(j).data))) {
                    graph.addEdge(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data);
                    graph.addWeight(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data, 15 + Util.Utility.random(40));
                }
            }
        }

    }

    @FXML
    private void btnRandomize(ActionEvent event) throws GraphException, ListException {
        randomizeEdges();
        rdbEvent();
    }

    public void loadTable(TableView<String[]> tbl, String[][] stringMatrix) {

        stringMatrix = cleanMatrix(stringMatrix);

        tbl.getColumns().clear();

        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(stringMatrix));
        data.remove(0);//remove titles from data
        int x = 130;
        for (int i = 0; i < stringMatrix[0].length; i++) {
            TableColumn tc = new TableColumn(stringMatrix[0][i]);
            tc.setEditable(false);
            tc.setSortable(false);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });

            tbl.getColumns().add(tc);
            tc.setPrefWidth(x);
            x = 87;

        }
        tbl.setItems(data);
    }

    public String[][] cleanMatrix(String matrix[][]) {
        int counter = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] != null && !matrix[i][0].equals("")) {
                counter++;
            }
        }

        String aux[][] = new String[counter][2];
        counter = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] != null && !matrix[i][0].equals("")) {
                aux[counter][0] = matrix[i][0];
                aux[counter++][1] = matrix[i][1];
            }
        }

        return aux;
    }

}
