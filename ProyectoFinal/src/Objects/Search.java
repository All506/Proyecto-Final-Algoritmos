package Objects;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author juanp
 */
public class Search {
  
    //Atributos
    private Date searchDate;
    private String location;
    private String product;
    private String suggestions;

    public Search(Date searchDate, String location, String product, String suggestions) {
        this.searchDate = searchDate;
        this.location = location;
        this.product = product;
        this.suggestions = suggestions;
    }

    public Search() {
    
    }

    
    
    public String[] dataName() {
        String[] dataName = {"date", "location", "product", "suggestions"};
        return dataName;
    }

    public String[] data() {

        String[] data = {new SimpleDateFormat("dd-MM-yyyy").format(searchDate), location, product, suggestions};
        return data;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }
    
}//end class
