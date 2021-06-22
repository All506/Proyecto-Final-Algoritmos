/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Domain.CircularLinkList;
import Domain.ListException;
import Objects.Security;
import Security.AES;
import XML.FileXML;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Alán
 */
public class SaveData {

    public static CircularLinkList lSecurity = new CircularLinkList();

    public void saveData() throws ListException, TransformerException, SAXException, IOException {

        lSecurity = Util.Utility.lSecurity;
        FileXML fXML = new FileXML();

        if (!fXML.exist("Security.xml")) { //Si el archivo no existe
            fXML.createXML("SecurityXML", "Security");
            if (!Util.Utility.getListSecurity().isEmpty()) {
                writeSecurity();
            }
        } else {
            fXML.deleteFile("Security");
            fXML.createXML("SecurityXML", "Security");
            if (!Util.Utility.getListSecurity().isEmpty()) {
                writeSecurity();
            }
        }
    }

    public void writeSecurity() throws ListException, TransformerException, SAXException, IOException {

        FileXML fXML = new FileXML();

        if (Util.Utility.getListSecurity().isEmpty()) {
            if (fXML.exist("Security.xml")) {
                fXML.deleteFile("Security");
            }
        } else {
            AES encrypt = new AES();
            for (int i = 1; i <= lSecurity.size(); i++) {
                Security tempSec = (Security) lSecurity.getNode(i).data;
                Security encSec = new Security(encrypt.encrypt(tempSec.getUser(), "Proyecto"), encrypt.encrypt(tempSec.getPassword(), "Proyecto"));
                fXML.writeXML("Security.xml", "Security", encSec.getDataName(), encSec.getData());
            }
        }
    }
    
}
