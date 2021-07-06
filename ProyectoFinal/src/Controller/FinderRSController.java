/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.BST;
import Domain.BTreeNode;
import Domain.TreeException;
import Graphs.AdjacencyListGraph;
import Graphs.DijkstrasAlgorithm;
import Graphs.Graph;
import Graphs.GraphException;
import Objects.Food;
import Objects.Place;
import Objects.Product;
import Objects.Restaurant;
import Objects.Search;
import Objects.Supermarket;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import list.ListException;
import list.SinglyLinkedList;
import sun.util.calendar.Gregorian;


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
    @FXML
    private Button btnBack;
    @FXML
    private AnchorPane apFinal;
    @FXML
    private TableView<String[]> tblItems;
    @FXML
    private ComboBox<String> cmbItems;
    @FXML
    private Button btnConfirm;
    @FXML
    private BorderPane bpRoot;
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        apType.setVisible(false);
        apFinal.setVisible(false);
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
    private void btnFood(ActionEvent event) throws TreeException, ListException {
        apType.setVisible(false);
        apRoot.setVisible(false);
        apFinal.setVisible(true);
        treeAsArray(Util.Utility.treeFoods);
        loadComboBox();
    }

    @FXML
    private void btnProducts(ActionEvent event) throws TreeException, ListException {
        apType.setVisible(false);
        apRoot.setVisible(false);
        apFinal.setVisible(true);
        treeAsArray(Util.Utility.treeProducts);
        loadComboBox();
        
    }

    @FXML
    private void btnBack(ActionEvent event) {
        apRoot.setOpacity(1);
        apType.setVisible(false);
        
    }
    
    public void loadComboBox(){
      String matrix[][] = new String[itemsArray.length+1][2];
      matrix[0][0]="Name";
      matrix[0][1]="Price";
      
        for (int i = 0; i < itemsArray.length; i++) {
            if (itemsArray[i] instanceof Food) {
                Food f = (Food) itemsArray[i];
                if (!cmbItems.getItems().contains(f.getName())) {
                    cmbItems.getItems().add(f.getName());
                    matrix[i + 1][0] = f.getName();
                    matrix[i + 1][1] = String.valueOf(f.getPrice());
                }
            } else {
                Product p = (Product) itemsArray[i];
                if (!cmbItems.getItems().contains(p.getName())) {
                    cmbItems.getItems().add(p.getName());
                    matrix[i + 1][0] = p.getName();
                    matrix[i + 1][1] = String.valueOf(p.getPrice());
                }
          }
         
        }
        loadTable(tblItems, cleanMatrix(matrix));
    }
    
   
    int itemCounter;
    Object itemsArray[];
    public void treeAsArray(BST tree) throws TreeException {
        if(tree.isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        
        itemsArray = new Object[tree.size()];
        itemCounter = 0;
        treeAsArray(tree.getRoot());
        
    }
    
    //Tranversal tour: L-N-R
    private void treeAsArray(BTreeNode node){
        if(node!=null){
            treeAsArray(node.left);
            itemsArray[itemCounter++]=node.data;
            treeAsArray(node.right);
        }
        
    }
    
    
    
   public SinglyLinkedList getRestSupByItemName(String item) throws ListException{
       SinglyLinkedList list = new SinglyLinkedList();
       for (int i = 0; i < itemsArray.length; i++) {
           if(itemsArray[i] instanceof Food){
                Food f = (Food)itemsArray[i];
                if(f.getName().equals(item)){
                    Restaurant r =(Restaurant)getRestSupById(f.getRestaurantID());
                    if(r!=null){
                        list.add(r);
                    }
                }
           }else{
                Product p = (Product)itemsArray[i];
                 if(p.getName().equals(item)){
                    Supermarket s =(Supermarket)getRestSupById(p.getSupermarketID());
                    if(s!=null){
                        list.add(s);
                    }
                }
           }
       }

       return list;
   }
   
   public Object getRestSupById(int id) throws ListException{
   
       Graph g = Util.Utility.gRestAndSuper;
       
       for (int i = 0; i < g.size(); i++) {
           if(g.getVertexByIndex(i).data instanceof Restaurant){
               Restaurant r = (Restaurant) g.getVertexByIndex(i).data;
               if(r.getID()==id)
                   return r;
           }else{
               Supermarket s = (Supermarket) g.getVertexByIndex(i).data;
               if(s.getID()==id)
                   return s;
           }
       }
       
   return null;
   }

    @FXML
   public void btnConfirm(ActionEvent event) throws ListException, GraphException {
        
        if(cmbItems.getValue()==null){
            callAlert("You must select a product or food.");
        }
        else
        {

            java.util.Date d = java.sql.Date.valueOf(java.time.LocalDate.now());
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(d);
            
            
            
            SinglyLinkedList suggestions = nearestRestSup(cmbItems.getValue());
            Search s = new Search(
                    d,//Fecha
                    p.getName(),//Nombre del producto que se buscó
                    cmbItems.getValue(),//Nombre del producto
                    suggestions.getNode(1).data.toString() //Sugerencias
                    + (suggestions.size() >= 2 ? suggestions.getNode(2).data.toString() : "")//La aguega solo si existe
                    + (suggestions.size() >= 3 ? suggestions.getNode(3).data.toString() : ""),//La aguega solo si existe
                    LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()
            );
            
            Util.Utility.lSearches.add(s);
            
            
            loadPage("Suggestions");
        }


    }
    
    public void loadPage(String page){
        
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/UI/" + page + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bpRoot.setCenter(root);
    }
    
    public String[][] cleanMatrix(String matrix[][]){
    int counter= 0;
        for (int i = 0; i < matrix.length; i++) {
           if(matrix[i][0]!=null&&!matrix[i][0].equals("")){
               counter++;
           }    
        }
    
        String aux[][]= new String[counter][2];
        counter= 0;
        for (int i = 0; i < matrix.length; i++) {
           if(matrix[i][0]!=null&&!matrix[i][0].equals("")){
               aux[counter][0]=matrix[i][0];
               aux[counter++][1]=matrix[i][1];
           }    
        }
    
        return aux;
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
    
    public SinglyLinkedList nearestRestSup(String item) throws ListException, GraphException{
    String matrix[] = DijkstrasAlgorithm.getPath(graph, p);
    Arrays.sort(matrix);

    SinglyLinkedList listRestSup= getRestSupByItemName(item);

        
        
    SinglyLinkedList suggestions = new SinglyLinkedList();
    suggestions.add("Empty");
    
        for (int i = 0; i < matrix.length && suggestions.size()<4; i++) {
            String data[] = matrix[i].split("-");
            
            Place pl =(Place) graph.getVertexByIndex(Integer.parseInt(data[2])).data;
            for (int j = 1; j <= listRestSup.size() && suggestions.size()<4; j++) {
                if(listRestSup.getNode(j).data instanceof Restaurant){
                    Restaurant r = (Restaurant) listRestSup.getNode(j).data;
                    if(r.getLocation().equalsIgnoreCase(pl.getName())){
                    suggestions.add(suggestionAsString(r, data));
                    }
                }else{
                     Supermarket s = (Supermarket) listRestSup.getNode(j).data;
                    if(s.getLocation().equalsIgnoreCase(pl.getName())){
                    suggestions.add(suggestionAsString(s, data));
                    }
                }
            }
            
        }
        suggestions.removeFirst();
        System.out.println(suggestions);
        return suggestions;
    }
    
    public Place getPlaceByPosition(int index) throws ListException{
        AdjacencyListGraph g = Util.Utility.getPlacesGraph();
        
        Place place =(Place) g.getVertexByIndex(index).data;
        return place;
    }
    
    public String suggestionAsString(Object obj, String[] data) throws ListException{
        String sug=""; 
        if(obj instanceof Restaurant){
           Restaurant rest = (Restaurant) obj;
           sug="Restaurant \""+rest.getName()+ "\", located in "+rest.getLocation();
        }else{
           Supermarket sup = (Supermarket) obj; 
           sug="Supermarket \""+sup.getName()+ "\", located in "+sup.getLocation();
        }
        
        if(data.length==4){
            sug+=", is already located in your town.-";
        }
        if(data.length==5){
            sug+=". The distance from your actual location is "+data[0]+"Km.-";
        }
        if(data.length>5){
            sug+=". The distance from your actual location is "+data[0]+"Km passing through ";
            
            for (int  i = 4; i < data.length-1; i++) {
                
                Place pla = getPlaceByPosition(Integer.parseInt(data[i]));
                sug+= pla.getName();
                if(i<data.length-2){
                    sug+=" and ";
                }else{
                    sug+=".-";
                }
                
            }
        }
    
    return sug;
    }
    
    
}
