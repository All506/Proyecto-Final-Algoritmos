package Objects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import list.SinglyLinkedList;

/**
 *
 * @author juanp
 */
public class Search {
  
    //Atributos
    private Date searchDate;

    public Search(Date searchDate) {
        this.searchDate = searchDate;
    }

    public Search() {
        
        
        
    }

    public Search(Date d, String value, SinglyLinkedList suggestions) {
        
        
        
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }
    
    public String[] dataName(){
        String[] data = {"date"};
        return data;
    }
    
    public String[] getData(){
        //LocalDate dataFormat = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(this.date));
        String[] data = {searchDate.toString()};
        return data;
    }
    
}//end class
