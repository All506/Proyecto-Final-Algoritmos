package PDF;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilePDF {

    //Constructor
    public FilePDF() {

    }

    //Elimina el pdf
    public void deleteFile(String fileName) {
        try {
            Files.delete(Paths.get(fileName + ".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(FilePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Exist?
    public Boolean exist(String file) {
        File archive = new File(file + ".pdf");
        return archive.exists();
    }

    //Genera el pdf y escribe lo que queremos
    public void careerPDF(String fileName) throws FileNotFoundException, DocumentException, BadElementException, URISyntaxException, IOException {
        FileOutputStream file = new FileOutputStream(fileName + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, file);

        //Instancia para poder hacer la img, importante poner la imagen en la carpeta img
        Image header = Image.getInstance("src/img/logo-ucr.png");
        header.scaleToFit(150, 250);
        header.setAlignment(Chunk.ALIGN_CENTER);
        //Se abre el documento para poder escribir en el
        document.open();
        document.add(header);//Se agrega la img

        Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.add("\n\nRegistered Careers\n\n");
        document.add(parrafo);

        //Tabla
        PdfPTable table = new PdfPTable(2);//Columnas y nombres
        table.addCell("Identification");
        table.addCell("Description");

//        try {
//            for (int i = 1; i <= list.size(); i++) {
//                Career career = (Career) list.getNode(i).data;
//                table.addCell(String.valueOf(career.getId()));
//                table.addCell(career.getDescription());
//            }
//            document.add(table);//Agrega la tabla al documento
//
//        } catch (ListException | DocumentException e) {
//        }

        //Importante cerrar el pdf
        document.close();
    }

    //Genera el pdf y escribe lo que queremos
    public void studentPDF(String fileName) throws FileNotFoundException, DocumentException, BadElementException, URISyntaxException, IOException {
        FileOutputStream file = new FileOutputStream(fileName + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, file);

        //Instancia para poder hacer la img, importante poner la imagen en la carpeta img
        Image header = Image.getInstance("src/img/logo-ucr.png");
        header.scaleToFit(150, 250);
        header.setAlignment(Chunk.ALIGN_CENTER);
        //Se abre el documento para poder escribir en el
        document.open();
        document.add(header);//Se agrega la img

        Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.add("\n\nRegistered Students\n\n");
        document.add(parrafo);

//        try {
//            for (int i = 1; i <= list.size(); i++) {
//                Paragraph parrafo1 = new Paragraph();
//                Student student = (Student) list.getNode(i).data;
//                parrafo1.add("\nIdentification: " + String.valueOf(student.getId()));
//                parrafo1.add("\nStudent ID: " + student.getStudentID());
//                parrafo1.add("\nLast Name: " + student.getLastname());
//                parrafo1.add("\nFirst Name: " + student.getFirstname());
//                parrafo1.add("\nBirthday: " + Util.Utility.dateFormat(student.getBirthday2()));
//                parrafo1.add("\nPhone Number: " + student.getPhoneNumber());
//                parrafo1.add("\nEmail: " + student.getEmail());
//                parrafo1.add("\nAddress: " + student.getAddress());
//                parrafo1.add("\nCareer: " + Util.Utility.getCarrerByID(String.valueOf(student.getCareerID())).getDescription());
//                parrafo1.add("\n----------------------------------------");
//                document.add(parrafo1);
//            }
//
//        } catch (ListException | DocumentException e) {
//        }

        //Importante cerrar el pdf
        document.close();
    }

}//end class
