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
    
//    public String[] dataName(){
//        String[] data = {"id","date","studentId","courseId","schedule"};
//        return data;
//    }
//    
//    public String[] getData(){
//        LocalDate dataFormat = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(this.date));
//        String[] data = {String.valueOf(id),dataFormat.toString(),this.studentID, this.courseID, this.schedule};
//        return data;
//    }
    
}//end class
