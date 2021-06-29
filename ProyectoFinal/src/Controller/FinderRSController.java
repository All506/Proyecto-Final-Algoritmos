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
public class FinderRSController implements Initializable {

    @FXML
    private AnchorPane apRoot;
    @FXML
    private Button btnCenter;
    @FXML
    private Rectangle rectangleGraph;
  
    Button btn;

    float auxI;

    int hip;

    int edgesCounter = 0;

    Button btnArray[];

    RadioButton rdbArray[];

    int counter;

    Line lne;

    AdjacencyListGraph graph;
    
    public Text txtTitle;
    
    Place p;
    @FXML
    private AnchorPane apType;
    @FXML
    private Button btnFood;
    @FXML
    private Button btnProducts;
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        apType.setVisible(false);

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
        
        try {
            drawGraph(graph);
            
        } catch (ListException ex) {
            Logger.getLogger(FinderRSController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GraphException ex) {
            Logger.getLogger(FinderRSController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    AdjacencyListGraph aux;


    public void drawGraph(AdjacencyListGraph graph) throws ListException, GraphException {
        apRoot.setVisible(true);

        counter = 0;
        hip = 180;//longitud del centro a cada vertice

        btnArray = new Button[graph.size()];
        if (graph != null && !graph.isEmpty()) {
            drawVertices(graph);
        }

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
            
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    btn = (Button) event.getSource();
                    try {
                        buttonEvent();
                    } catch (ListException ex) {
                        Logger.getLogger(FinderRSController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

            if (i >= 0 && i < 90) {
                auxI = i;
                double x = Math.sin(Math.toRadians(auxI)) * hip;
                double y = Math.cos(Math.toRadians(auxI)) * hip;

                btn.setLayoutX(btnCenter.getLayoutX() + y * 1.5);
                btn.setLayoutY(btnCenter.getLayoutY() - x);
            }
            if (i >= 90 && i < 180) {
                auxI = i - 90;
                double x = Math.sin(Math.toRadians(auxI)) * hip;
                double y = Math.cos(Math.toRadians(auxI)) * hip;

                btn.setLayoutX(btnCenter.getLayoutX() - x * 1.5);
                btn.setLayoutY(btnCenter.getLayoutY() - y);
            }
            if (i >= 180 && i < 270) {
                auxI = i - 180;
                double x = Math.sin(Math.toRadians(auxI)) * hip;
                double y = Math.cos(Math.toRadians(auxI)) * hip;

                btn.setLayoutX(btnCenter.getLayoutX() - y * 1.5);
                btn.setLayoutY(btnCenter.getLayoutY() + x);
            }
            if (i >= 270 && i < 360) {
                auxI = i - 270;
                double x = Math.sin(Math.toRadians(auxI)) * hip;
                double y = Math.cos(Math.toRadians(auxI)) * hip;

                btn.setLayoutX(btnCenter.getLayoutX() + x * 1.5);
                btn.setLayoutY(btnCenter.getLayoutY() + y);
            }
        }
    }
    
    public void buttonEvent() throws ListException{
    
        
        for (int i = 0; i < graph.size() && p==null; i++) {
            if (Util.Utility.equals(graph.getVertexByIndex(i).data, btn.getId())) {
                 p =(Place) graph.getVertexByIndex(i).data;
            }
        }
        apType.toFront();
        apRoot.setOpacity(0.7);
        apType.setVisible(true);
        
    
    }

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

    private void btnRandomize(ActionEvent event) throws GraphException, ListException {
        randomizeEdges();
//        rdbEvent();
    }

    public void loadTable(TableView<String[]> tbl, String[][] stringMatrix) {

        tbl.getColumns().clear();

        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(stringMatrix));
        data.remove(0);//remove titles from data

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
        }
        tbl.setItems(data);
    }

    @FXML
    private void btnFood(ActionEvent event) {
        apType.setVisible(false);
        apRoot.setVisible(false);
    }

    @FXML
    private void btnProducts(ActionEvent event) {
        apType.setVisible(false);
        apRoot.setVisible(false);
    }
}
