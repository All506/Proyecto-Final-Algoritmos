/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Objects.Search;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class SuggestionsController implements Initializable {

    @FXML
    private Text txtPlace;
    @FXML
    private Text txtProduct;
    @FXML
    private Text txtSuggestions;
    @FXML
    private Text txtDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Search search = (Search) Util.Utility.lSearches.getNode(Util.Utility.lSearches.size()).data;
            loadInfo(search);
        } catch (ListException ex) {
            Logger.getLogger(SuggestionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }    
    
    public void loadInfo(Search s){
    
        txtDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(s.getSearchDate())+"  "+s.getHour());
    
        txtPlace.setText(s.getLocation());
        
        txtProduct.setText(s.getProduct());
        
        String sug[] = s.getSuggestions().split("-");
        String suggest = "";
        for (int i = 0; i < sug.length; i++) {
            suggest+=sug[i]+"\n\n";
        }
    
        txtSuggestions.setText(suggest);
    }
    
}
