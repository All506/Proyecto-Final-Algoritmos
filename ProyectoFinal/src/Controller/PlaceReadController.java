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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import list.ListException;
import queue.QueueException;
import stack.StackException;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class PlaceReadController implements Initializable {

    Button btn;

    float auxI;

    int hip;

//    int n;

    int edgesCounter = 0;

    Button btnArray[];

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
  
    private Button btnRemoveRandom;
    @FXML
    private ImageView imageMap;
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        clean();
        
        
        graph = Util.Utility.getPlacesGraph();
        //********************************************************
        try {
            graph.addVertex(new Place(1, "Cartago"));
            graph.addVertex(new Place(2, "Turrialba"));
            graph.addVertex(new Place(3, "Alajuela"));
            graph.addVertex(new Place(4, "Paraíso"));
            graph.addVertex(new Place(5, "Cervantes"));
            graph.addVertex(new Place(6, "Guapiles"));
            randomizeEdges();
            drawGraph(graph);
            
        } catch (GraphException | ListException ex) {
            Logger.getLogger(PlaceReadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //********************************************************
        
        rectangleGraph.setOnMouseMoved(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (graph != null && !graph.isEmpty())
                        try {
                    counter = 0;
                    apRoot.getChildren().clear();
                    btnArray = new Button[10];
                    drawVertices(graph);
                    drawEdges(graph);
                    txtTitle = new Text(100, 100, "");
                    apRoot.getChildren().add(rectangleGraph);
                    apRoot.getChildren().add(imageMap);
                    rectangleGraph.toBack();
                    imageMap.toBack();
                    apRoot.getChildren().add(txtTitle);

                } catch (GraphException | ListException ex) {
                    Logger.getLogger(PlaceReadController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
        
    }
    
    public void drawGraph(AdjacencyListGraph graph) throws ListException, GraphException{
     apRoot.getChildren().clear();
     apRoot.setVisible(true);

        counter = 0;
        hip = 180;//longitud del centro a cada vertice

        btnArray = new Button[graph.size()];
        if (graph != null && !graph.isEmpty()) {
            drawVertices(graph);
        }
//        boolean hasEdges = false;
//        for (int i = 0; i < graph.size(); i++) {
//            for (int j = 0; j < graph.size(); j++) {
//                if (graph.containsEdge(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data)) {
//                    hasEdges = true;
//                }
//            }
//        }

//        if (graph != null && !graph.isEmpty()) {
            drawEdges(graph);
//        }

        txtTitle = new Text("");
        apRoot.getChildren().add(rectangleGraph);
        apRoot.getChildren().add(imageMap);
        rectangleGraph.toBack();
        imageMap.toBack();
        apRoot.getChildren().add(txtTitle);
        

    }

    public void drawVertices(Graph graph) throws ListException {//Dibuja los vertices
        

        for (float i = 0; i < 360f - 0.1; i += 360f / graph.size()) {

            btn = new Button();
            btnArray[counter] = btn;
//            btn.setId(String.valueOf(i));
            
            apRoot.getChildren().add(btn);
            Place p = (Place)graph.getVertexByIndex(counter).data;
            btn.setId(p.toString());
            btn.setText(p.getName());
            counter++;

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

    Line line = new Line();

    public void drawEdges(AdjacencyListGraph graph) throws GraphException, ListException {//Dibuja las aristas
        
        edgesCounter = 0;
        int i;
        int j;

        for (i = 0; i < graph.size(); i++) {
            for (j = 0; j < graph.size(); j++) {
                if (btnArray[j].getId()!= btnArray[i].getId()) {
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
                        lne.setOpacity(0.5);

                        lne.setId("Weight: " + String.valueOf(graph.getWeight(btnArray[i].getId(), btnArray[j].getId())));

                        lne.setOnMouseMoved(new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                                line.setStroke(Paint.valueOf("#666666"));

                                line = (Line) event.getSource();
                                line.setStroke(Paint.valueOf("#C0392B"));
                                txtTitle.setText(line.getId());
                                Bounds boundsInScreen = rectangleGraph.localToScreen(rectangleGraph.getBoundsInLocal());
                                Point punto = MouseInfo.getPointerInfo().getLocation();
                                double x = punto.x - boundsInScreen.getMinX();
                                double y = punto.y - boundsInScreen.getMinY();
                                txtTitle.setLayoutX(x - 130);
                                txtTitle.setLayoutY(y - 130);
                                txtTitle.setFill(Paint.valueOf("white"));
                                txtTitle.setStyle("-fx-font-family: Century Gothic;"
                                        + " -fx-font-size: 20pt;");

                            }
                        });

                    }

                } else {
                    //Arista hacia si mismo
                    if (graph.containsEdge(btnArray[i].getId(), btnArray[j].getId())) {
                        drawItSelfEdge(btnArray[i]);
                    }
                }
            }
        }
        edgesCounter = edgesCounter / 2;

    }

    Line a1;
    Line a2;

    private void drawArrow(Button a, Button b) {//Dibuja las flechas, para los grafos dirigidos
        double node1X = 15 + a.getLayoutX();
        double node1Y = 15 + a.getLayoutY();

//*****************
        double node2X = 15 + b.getLayoutX() + ((a.getLayoutX() - b.getLayoutX()) / (10));
        double node2Y = 15 + b.getLayoutY() + ((a.getLayoutY() - b.getLayoutY()) / (10));
//*****************

        double arrowAngle = Math.toRadians(45.0);
        double arrowLength = 10.0;
        double dx = node1X - node2X;
        double dy = node1Y - node2Y;
        double angle = Math.atan2(dy, dx);
        double x1 = Math.cos(angle + arrowAngle) * arrowLength + (node2X);
        double y1 = Math.sin(angle + arrowAngle) * arrowLength + (node2Y);

        double x2 = Math.cos(angle - arrowAngle) * arrowLength + (node2X);
        double y2 = Math.sin(angle - arrowAngle) * arrowLength + (node2Y);
        a1 = new Line(node2X, node2Y, x1, y1);
        a2 = new Line(node2X, node2Y, x2, y2);
        apRoot.getChildren().add(a1);
        apRoot.getChildren().add(a2);

    }

    public void drawSingleEdge(Button a, Button b) {//Dibuja una arista entre dos botones

        lne = new Line();

        lne.startXProperty().bind(a.layoutXProperty().add(a.getBoundsInParent().getWidth() / 2.0));
        lne.startYProperty().bind(a.layoutYProperty().add(a.getBoundsInParent().getHeight() / 2.0));

        lne.endXProperty().bind(b.layoutXProperty().add(b.getBoundsInParent().getWidth() / 2.0));
        lne.endYProperty().bind(b.layoutYProperty().add(b.getBoundsInParent().getHeight() / 2.0));

        apRoot.getChildren().addAll(lne);
    }

    public void drawItSelfEdge(Button btn) {//Dibuja una arista hacia el mismo vertice

        Button btn2 = new Button();

        btn2.setPrefSize(50, 50);
//    btn2.setStyle("-fx-border-color: black");
        btn2.setStyle("-fx-background-color: #6cbc88");

        Pane pane = new AnchorPane();
        Circle circ = new Circle(20);
        circ.setLayoutX(20);
        circ.setLayoutY(20);
        circ.setFill(Paint.valueOf("#6cbc88"));
        circ.setStrokeWidth(5);
        circ.setStroke(Paint.valueOf("Black"));
        pane.getChildren().add(circ);

        Rectangle rec = new Rectangle(20, 15);
        rec.setLayoutX(10);
        rec.setLayoutY(29);
        rec.setFill(Paint.valueOf("#6cbc88"));
        rec.setStroke(Paint.valueOf("#6cbc88"));
        pane.getChildren().add(rec);

        Line line = new Line();
        line.setStrokeWidth(5);
        pane.getChildren().add(line);
        line.setLayoutX(30);
        line.setLayoutY(37);
        line.toFront();
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(10);
        line.setEndY(3);

        Line line2 = new Line();
        line2.setStrokeWidth(5);
        pane.getChildren().add(line2);
        line2.setLayoutX(30);
        line2.setLayoutY(37);
        line2.toFront();
        line2.setStartX(0);
        line2.setStartY(0);
        line2.setEndX(0);
        line2.setEndY(-11);

        btn2.setGraphic(pane);
        apRoot.getChildren().add(btn2);
        btn2.setRotate(90 - (Double.parseDouble(btn.getId())));
        btn2.toBack();
        btn2.setLayoutX((btn.getLayoutX() - ((btnCenter.getLayoutX() - btn.getLayoutX())) / 7) - 17);
        btn2.setLayoutY((btn.getLayoutY() - ((btnCenter.getLayoutY() - btn.getLayoutY())) / 7) - 10);

    }


    private void btnAdd(ActionEvent event) throws GraphException, ListException {
        clean();
       
        
       
        graph = null;
        apRoot.getChildren().clear();
        apRoot.getChildren().add(rectangleGraph);
        apRoot.setVisible(true);
       
    }

    private void btnRemove(ActionEvent event) throws GraphException, ListException {
       
        clean();
        
        
        graph = null;
        btnRemoveRandom.setVisible(true);
        
        apRoot.getChildren().clear();
        apRoot.getChildren().add(rectangleGraph);
        apRoot.setVisible(true);
       
         }

    private void randomizeEdges() throws GraphException, ListException {

        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.size(); j++) {
               
                    if (!graph.containsEdge(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data) && !(graph.getVertexByIndex(i).data.equals(graph.getVertexByIndex(j).data))) {
                        graph.addEdge(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data);
                        graph.addWeight(graph.getVertexByIndex(i).data, graph.getVertexByIndex(j).data, Util.Utility.random());
                    }
                
            }
        }

    }

   

    private void btnDFS(ActionEvent event) throws GraphException, StackException, ListException, QueueException {
        
        if (graph != null && !graph.isEmpty()) {

            String s = graph.dfs();

            String dfs[] = s.split(" - ");

            Button b = new Button();
            for (int i = 0; i < dfs.length; i++) {

                for (int j = 0; j < btnArray.length; j++) {
                    if (btnArray[j] != null && btnArray[j].getId().equals(dfs[i])) {
                        b = btnArray[j];
                    }
                }

                Circle cirR = new Circle(20, Color.DARKCYAN);
                apRoot.getChildren().add(cirR);
                cirR.setLayoutX(b.getLayoutX() + 20);
                cirR.setLayoutY(b.getLayoutY() + 40);
                cirR.setOpacity(0.9);

                Text txtR = new Text(String.valueOf(i + 1));
                apRoot.getChildren().add(txtR);
                txtR.setLayoutX(b.getLayoutX() + 12);
                txtR.setLayoutY(b.getLayoutY() + 50);
                txtR.setFill(Color.LIGHTCYAN);
                txtR.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 17));
                b.toFront();

            }
        }
    }

    private void btnBFS(ActionEvent event) throws GraphException, StackException, ListException, QueueException {
        
        if (graph != null && !graph.isEmpty()) {

            String s = graph.bfs();

            String bfs[] = s.split(" - ");

            Button b = new Button();
            for (int i = 0; i < bfs.length; i++) {

                for (int j = 0; j < btnArray.length; j++) {
                    if (btnArray[j] != null && btnArray[j].getId().equals(bfs[i])) {
                        b = btnArray[j];
                    }
                }

                Circle cirR = new Circle(20, Color.DARKCYAN);
                apRoot.getChildren().add(cirR);
                cirR.setLayoutX(b.getLayoutX() + 20);
                cirR.setLayoutY(b.getLayoutY() + 40);
                cirR.setOpacity(0.9);

                Text txtR = new Text(String.valueOf(i + 1));
                apRoot.getChildren().add(txtR);
                txtR.setLayoutX(b.getLayoutX() + 12);
                txtR.setLayoutY(b.getLayoutY() + 50);
                txtR.setFill(Color.LIGHTCYAN);
                txtR.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 17));
                b.toFront();

            }

        }
    }

 
    

  

    public void clean() {
       
       
        apRoot.setVisible(false);
   

    }

    
}
