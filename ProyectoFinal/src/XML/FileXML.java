/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Domain.CircularLinkList;
import Objects.Security;
import Security.AES;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
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

            NodeList nList = doc.getElementsByTagName("User");

            for (int indice = 0; indice < nList.getLength(); indice++) {
                Security sec = new Security("", "");
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    sec.setUser(eElement.getAttribute("user"));
                    sec.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());

                }
                AES deEnc = new AES();
                Security desUser = new Security(deEnc.deCrypt(sec.getUser(), "Proyecto"), deEnc.deCrypt(sec.getPassword(), "Proyecto"));
                lSecurity.add(desUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lSecurity;
    }
}