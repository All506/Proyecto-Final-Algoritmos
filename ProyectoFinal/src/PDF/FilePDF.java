package PDF;

import Domain.BST;
import Domain.BTreeNode;
import Domain.CircularLinkList;
import Domain.ListException;
import Objects.Product;
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
    public void productsAndFood(String fileName, BST treeProducts, BST treeFood) throws FileNotFoundException, DocumentException, BadElementException, URISyntaxException, IOException, ListException {
        FileOutputStream file = new FileOutputStream(fileName + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, file);

        //Instancia para poder hacer la img, importante poner la imagen en la carpeta img
        Image header = Image.getInstance("src/img/products&food.jpg");
        header.scaleToFit(250, 600);
        header.setAlignment(Chunk.ALIGN_CENTER);
        //Se abre el documento para poder escribir en el
        document.open();
        document.add(header);//Se agrega la img

        //PRODUCTS
        Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.add("\n\nRegistered Products\n\n");
        document.add(parrafo);

        //Tabla
        PdfPTable table = new PdfPTable(4);//Columnas y nombres
        table.addCell("Identificator");
        table.addCell("Name");
        table.addCell("$Price");
        table.addCell("Supermarket");

        CircularLinkList arrayListTemp = new CircularLinkList();
        CircularLinkList arrayList = tourTree(treeProducts.getRoot(), arrayListTemp);

        if (!arrayList.isEmpty()) {
            try {
                for (int i = 1; i <= arrayList.size(); i++) {
                    Product product = (Product) arrayList.getNode(i).data;
                    table.addCell(String.valueOf(product.getID()));
                    table.addCell(product.getName());
                    table.addCell(String.valueOf(product.getPrice()));
                    table.addCell(String.valueOf(product.getSupermarketID()));//Cambiar para que aparezca el supermarcado
                }
                document.add(table);//Agrega la tabla al documento

            } catch (ListException | DocumentException e) {
            }
        } else {
            Paragraph parrafoTemp = new Paragraph();
            parrafoTemp.setAlignment(Paragraph.ALIGN_CENTER);
            parrafoTemp.add("\n\nThere's no products registered\n\n");
            document.add(table);//Agrega la tabla al documento 
            document.add(parrafoTemp);
        }

        //FOOD
        Paragraph parrafo2 = new Paragraph();
        parrafo2.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo2.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo2.add("\n\nRegistered Food\n\n");
        document.add(parrafo2);

        //Tabla
        PdfPTable table2 = new PdfPTable(4);//Columnas y nombres
        table2.addCell("Identificator");
        table2.addCell("Name");
        table2.addCell("$Price");
        table2.addCell("Restaurant");

        CircularLinkList arrayListTemp2 = new CircularLinkList();
        CircularLinkList arrayList2 = tourTree(treeFood.getRoot(), arrayListTemp2);

        if (!arrayList2.isEmpty()) {
            try {
                for (int i = 1; i <= arrayList2.size(); i++) {
                    Product product = (Product) arrayList2.getNode(i).data;
                    table2.addCell(String.valueOf(product.getID()));
                    table2.addCell(product.getName());
                    table2.addCell(String.valueOf(product.getPrice()));
                    table2.addCell(String.valueOf(product.getSupermarketID()));//Cambiar para que aparezca el restaurante
                }
                document.add(table2);//Agrega la tabla al documento

            } catch (ListException | DocumentException e) {
            }
        } else {
            Paragraph parrafoTemp = new Paragraph();
            parrafoTemp.setAlignment(Paragraph.ALIGN_CENTER);
            parrafoTemp.add("\nThere's no food registered\n");
            document.add(table2);//Agrega la tabla al documento
            document.add(parrafoTemp);
        }

        //Importante cerrar el pdf
        document.close();
    }

    private CircularLinkList tourTree(BTreeNode node, CircularLinkList arrayList2) {
        if (node != null) {
            tourTree(node.left, arrayList2);
            Product p = (Product) node.data;
            arrayList2.add(p);
            tourTree(node.right, arrayList2);
        }
        return arrayList2;
    }

    //Genera el pdf y escribe lo que queremos
    public void restaurantsAndSupermarket(String fileName) throws FileNotFoundException, DocumentException, BadElementException, URISyntaxException, IOException, ListException {
        FileOutputStream file = new FileOutputStream(fileName + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, file);

        //Instancia para poder hacer la img, importante poner la imagen en la carpeta img
        Image header = Image.getInstance("src/img/restaurants&supermarkets.jpg");
        header.scaleToFit(250, 600);
        header.setAlignment(Chunk.ALIGN_CENTER);
        //Se abre el documento para poder escribir en el
        document.open();
        document.add(header);//Se agrega la img

        //SUPERMARKETS
        Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.add("\n\nRegistered Supermarkets\n\n");
        document.add(parrafo);

        //Tabla
        PdfPTable table = new PdfPTable(3);//Columnas y nombres
        table.addCell("Identificator");
        table.addCell("Name");
        table.addCell("Location");

//        CircularLinkList arrayListTemp = new CircularLinkList();
//        CircularLinkList arrayList = tourTree(treeProducts.getRoot(), arrayListTemp);
//        if (!arrayList.isEmpty()) {
//            try {
//                for (int i = 1; i <= arrayList.size(); i++) {
//                    Product product = (Product) arrayList.getNode(i).data;
//                    table.addCell(String.valueOf(product.getID()));
//                    table.addCell(product.getName());
//                    table.addCell(String.valueOf(product.getPrice()));
//                    table.addCell(String.valueOf(product.getSupermarketID()));//Cambiar para que aparezca el supermarcado
//                }
//                document.add(table);//Agrega la tabla al documento
//
//            } catch (ListException | DocumentException e) {
//            }
//        } else {
//            Paragraph parrafoTemp = new Paragraph();
//            parrafoTemp.setAlignment(Paragraph.ALIGN_CENTER);
//            parrafoTemp.add("\n\nThere's no products registered\n\n");
        document.add(table);//Agrega la tabla al documento 
//            document.add(parrafoTemp);
//        }
//
        //RESTAURANTS
        Paragraph parrafo2 = new Paragraph();
        parrafo2.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo2.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo2.add("\n\nRegistered Restaurants\n\n");
        document.add(parrafo2);

        //Tabla
        PdfPTable table2 = new PdfPTable(3);//Columnas y nombres
        table2.addCell("Identificator");
        table2.addCell("Name");
        table2.addCell("Location");
//
//        CircularLinkList arrayListTemp2 = new CircularLinkList();
//        CircularLinkList arrayList2 = tourTree(treeFood.getRoot(), arrayListTemp2);
//
//        if (!arrayList2.isEmpty()) {
//            try {
//                for (int i = 1; i <= arrayList2.size(); i++) {
//                    Product product = (Product) arrayList2.getNode(i).data;
//                    table2.addCell(String.valueOf(product.getID()));
//                    table2.addCell(product.getName());
//                    table2.addCell(String.valueOf(product.getPrice()));
//                    table2.addCell(String.valueOf(product.getSupermarketID()));//Cambiar para que aparezca el restaurante
//                }
//                document.add(table2);//Agrega la tabla al documento
//
//            } catch (ListException | DocumentException e) {
//            }
//        } else {
//            Paragraph parrafoTemp = new Paragraph();
//            parrafoTemp.setAlignment(Paragraph.ALIGN_CENTER);
//            parrafoTemp.add("\nThere's no food registered\n");
        document.add(table2);//Agrega la tabla al documento
//            document.add(parrafoTemp);
//        }

        //Importante cerrar el pdf
        document.close();
    }
    
}//end class
