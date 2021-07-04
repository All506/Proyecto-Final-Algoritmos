/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Domain.CircularDoublyLinkList;
import Domain.CircularLinkList;
import Graphs.GraphException;
import Objects.Food;
import Objects.Place;
import Objects.Product;
import Objects.Security;
import Objects.Restaurant;
import Objects.Search;
import Objects.Supermarket;
import Security.AES;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import list.ListException;
import list.SinglyLinkedList;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alán
 */
public class FileXML {

    public void createXML(String objectName, String fileName) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(objectName);
            doc.appendChild(rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(/*address + "\\" + */fileName + ".xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) { //Se encarga de eliminar un archivo
        try {
            Files.delete(Paths.get(fileName + ".xml"));
        } catch (IOException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeXML(String FileName, String elementType, String[] dataName, String[] data) throws TransformerException, org.xml.sax.SAXException, IOException {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FileName));
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();

            Element ele = doc.createElement(elementType);
            rootElement.appendChild(ele);

            Attr attr = doc.createAttribute(dataName[0]);
            attr.setValue(data[0]);
            ele.setAttributeNode(attr);

            for (int i = 1; i < data.length; i++) {

                Element dato = doc.createElement(dataName[i]);

                dato.appendChild(doc.createTextNode(data[i]));

                ele.appendChild(dato);
            }
            //escribirmos el contenido en un archivo xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(FileName));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {

            pce.printStackTrace();

        }
    }

    public Boolean exist(String file) {
        File archive = new File(file);

        if (archive.exists()) {
            return true;
        }
        return false;
    }

    public CircularLinkList readXMLtoSecurityList() {

        CircularLinkList lSecurity = new CircularLinkList();

        try {
            File inputFile = new File("Security.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Security"); //Cabecera de objeto

            for (int indice = 0; indice < nList.getLength(); indice++) {
                Security sec = new Security("", "","");
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    sec.setUser(eElement.getAttribute("User"));
                    sec.setPassword(eElement.getElementsByTagName("Password").item(0).getTextContent());
                    sec.setKindUser(eElement.getElementsByTagName("KindUser").item(0).getTextContent());

                }
                AES deEnc = new AES();
                Security desUser = new Security(deEnc.deCrypt(sec.getUser(), "Proyecto"), deEnc.deCrypt(sec.getPassword(), "Proyecto"),
                deEnc.deCrypt(String.valueOf(sec.getKindUser()), "Proyecto"));
                lSecurity.add(desUser);
            }

            System.out.println("La lista en metodo decrypt es: " + lSecurity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lSecurity;
    }

    public SinglyLinkedList readXMLtoRestAndSuperList() {
        SinglyLinkedList lRestAndSupermarket = new SinglyLinkedList();

        if (exist("Restaurants.xml")) {

            try {
                File inputFile = new File("Restaurants.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("Restaurants"); //Cabecera de objeto

                for (int indice = 0; indice < nList.getLength(); indice++) {
                    Restaurant newRestaurant = new Restaurant(0, "", "");
                    Node nNode = nList.item(indice);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        newRestaurant.setID(Integer.valueOf(eElement.getAttribute("id")));
                        newRestaurant.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                        newRestaurant.setLocation(eElement.getElementsByTagName("location").item(0).getTextContent());
                    }
                    lRestAndSupermarket.add(newRestaurant);
                }

                nList = doc.getElementsByTagName("Supermarkets"); //Cabecera de objeto

                for (int indice = 0; indice < nList.getLength(); indice++) {
                    Supermarket newSuperMarket = new Supermarket(0, "", "");
                    Node nNode = nList.item(indice);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        newSuperMarket.setID(Integer.valueOf(eElement.getAttribute("id")));
                        newSuperMarket.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                        newSuperMarket.setLocation(eElement.getElementsByTagName("location").item(0).getTextContent());
                    }
                    lRestAndSupermarket.add(newSuperMarket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lRestAndSupermarket;
    }

    public SinglyLinkedList readXMLtoPlacesList() {
        SinglyLinkedList lPlaces = new SinglyLinkedList();

        if (exist("Places.xml")) {

            try {
                File inputFile = new File("Places.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("Places"); //Cabecera de objeto

                for (int indice = 0; indice < nList.getLength(); indice++) {
                    Place newPlace = new Place(0, "");
                    Node nNode = nList.item(indice);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        newPlace.setID(Integer.valueOf(eElement.getAttribute("id")));
                        newPlace.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    }
                    lPlaces.add(newPlace);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lPlaces;
    }

    public SinglyLinkedList readXMLtoProductList() {
        SinglyLinkedList lProducts = new SinglyLinkedList();

        if (exist("Products.xml")) {

            try {
                File inputFile = new File("Products.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("Products"); //Cabecera de objeto

                for (int indice = 0; indice < nList.getLength(); indice++) {
                    Product newProduct = new Product(0, "", 0, 0);
                    Node nNode = nList.item(indice);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        newProduct.setID(Integer.valueOf(eElement.getAttribute("ID")));
                        newProduct.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                        newProduct.setPrice(Double.valueOf(eElement.getElementsByTagName("price").item(0).getTextContent()));
                        newProduct.setSupermarketID(Integer.valueOf(eElement.getElementsByTagName("supermarketID").item(0).getTextContent()));
                    }
                    lProducts.add(newProduct);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lProducts;
    }

    private SinglyLinkedList readXMLtoFoodList() {
        SinglyLinkedList lFoods = new SinglyLinkedList();

        if (exist("Foods.xml")) {

            try {
                File inputFile = new File("Foods.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("Foods"); //Cabecera de objeto

                for (int indice = 0; indice < nList.getLength(); indice++) {
                    Food newFood = new Food(0, "", 0, 0);
                    Node nNode = nList.item(indice);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        newFood.setID(Integer.valueOf(eElement.getAttribute("id")));
                        newFood.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                        newFood.setPrice(Double.valueOf(eElement.getElementsByTagName("price").item(0).getTextContent()));
                        newFood.setRestaurantID(Integer.valueOf(eElement.getElementsByTagName("restaurantID").item(0).getTextContent()));
                    }
                    lFoods.add(newFood);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lFoods;
    }

    private CircularDoublyLinkList readXMLtoSearchList() {
        CircularDoublyLinkList lSearches = new CircularDoublyLinkList();

        if (exist("Searches.xml")) {

            try {
                File inputFile = new File("Searches.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("Searches"); //Cabecera de objeto

                for (int indice = 0; indice < nList.getLength(); indice++) {
                    Search newSearch = new Search(null, "", "", "", "");
                    Node nNode = nList.item(indice);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        //Se obtiene la fecha de la busqueda
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = format.parse(eElement.getAttribute("date"));
                        newSearch.setSearchDate(date);
                        newSearch.setLocation(eElement.getElementsByTagName("location").item(0).getTextContent());
                        newSearch.setProduct(eElement.getElementsByTagName("product").item(0).getTextContent());
                        newSearch.setSuggestions(eElement.getElementsByTagName("suggestions").item(0).getTextContent());
                        newSearch.setHour(eElement.getElementsByTagName("hour").item(0).getTextContent());
                    }
                    lSearches.add(newSearch);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lSearches;
    }

    public void loadFiles() throws ListException, GraphException, Domain.ListException {
        //Se carga la informacion de los restaurantes 
        if (readXMLtoRestAndSuperList().isEmpty()) {
            Util.Utility.lastIndexGRestAndSuper = 0; //Si no existe el documento, no se ha guardado ningun objeto
            System.out.println("El archivo de restaurantes no existe");
        } else {
            SinglyLinkedList lRestAndSuper = readXMLtoRestAndSuperList();
            for (int i = 1; i <= lRestAndSuper.size(); i++) {
                Util.Utility.gRestAndSuper.addVertex(lRestAndSuper.getNode(i).data);
                if (lRestAndSuper.getNode(i).data instanceof Restaurant) {
                    Restaurant temporalRest = (Restaurant) lRestAndSuper.getNode(i).data;
                    Util.Utility.lastIndexGRestAndSuper = temporalRest.getID(); //Se setea el valor del ultimo indice almacenado
                } else {
                    Supermarket temporalSupermarket = (Supermarket) lRestAndSuper.getNode(i).data;
                    Util.Utility.lastIndexGRestAndSuper = temporalSupermarket.getID(); //Se setea el valor del ultimo indice almacenado
                }
            }
        }
        Util.Utility.lastIndexGRestAndSuper++; //Se anhade uno mas para que el siguiente valor almacenado conicida

        //Se carga la informacion de los lugares
        if (readXMLtoPlacesList().isEmpty()) {
            Util.Utility.lastIndexGPlace = 0; //Si no existe el documento, no se ha guardado ningun objeto
            System.out.println("El archivo de lugares no existe");
        } else {
            SinglyLinkedList lPlaces = readXMLtoPlacesList();
            for (int i = 1; i <= lPlaces.size(); i++) {
                Util.Utility.gPlaces.addVertex(lPlaces.getNode(i).data);
                Place temporalPlaces = (Place) lPlaces.getNode(i).data;
                Util.Utility.lastIndexGPlace = temporalPlaces.getID(); //Se setea el valor del ultimo indice almacenado
            }
        }
        Util.Utility.lastIndexGPlace++; //Se anhade uno mas para que el siguiente valor almacenado conicida

        //Se carga la informacion de los productos
        if (readXMLtoProductList().isEmpty()) {
            System.out.println("El archivo de productos no existe");
        } else {
            SinglyLinkedList lProducts = readXMLtoProductList();
            for (int i = 1; i <= lProducts.size(); i++) {
                Util.Utility.treeProducts.add(lProducts.getNode(i).data);
            }
        }

        //Se carga la informacion de las comidas
        if (readXMLtoFoodList().isEmpty()) {
            System.out.println("El archivo de comidas no existe");
        } else {
            SinglyLinkedList lFoods = readXMLtoFoodList();
            for (int i = 1; i <= lFoods.size(); i++) {
                Util.Utility.treeFoods.add(lFoods.getNode(i).data);
            }
        }

        //Se carga la informacion de las busquedas
        if (readXMLtoSearchList().isEmpty()) {
            System.out.println("El archivo de búsquedas no existe");
        } else {
            CircularDoublyLinkList lSearches = readXMLtoSearchList();
            for (int i = 1; i <= lSearches.size(); i++) {
                Util.Utility.lSearches.add(lSearches.getNode(i).data);
            }
        }

        //System.out.println(Util.Utility.lSearches.toString());
    }

}
