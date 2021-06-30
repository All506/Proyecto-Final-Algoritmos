package Objects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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
