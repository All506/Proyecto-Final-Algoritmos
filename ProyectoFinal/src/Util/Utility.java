/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Domain.BST;
import Domain.CircularLinkList;
import Domain.ListException;
import Domain.TreeException;
import Objects.Product;
import Objects.Security;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utility {
    
    public static CircularLinkList lSecurity = new CircularLinkList();

    public static BST treeProducts = new BST();
    //Métodos relacionados al manejo de las listas
    
    public static boolean addSecurity(Security sec) throws ListException{
        boolean flag = false;
        if (lSecurity.isEmpty() || !lSecurity.contains(sec)){
            lSecurity.add(sec);
            flag = true;
        }
        return flag;
    }
    
    public static boolean addProduct(Product product) throws TreeException{
        boolean flag = false;
        if (treeProducts.isEmpty() || !treeProducts.contains(product)){
            treeProducts.add(product);
            flag = true;
        }
        return flag;
    }
    
    //UTILIDAD 
    public static int random() {
        return 1 + (int) Math.floor(Math.random() * 99);
    }

    public static int random(int bound) {
        //return 1+random.nextInt(bound);
        return 1 + (int) Math.floor(Math.random() * bound);
    }

    public static String format(double value) {
        return new DecimalFormat("###,###,###,###.##")
                .format(value);
    }

    public static String hourFormat(int value) {
        return new DecimalFormat("00")
                .format(value);
    }

    public static String $format(double value) {
        return new DecimalFormat("$###,###,###,###.##")
                .format(value);
    }

    public static String perFormat(double value) {
        //#,##0.00 '%'
        return new DecimalFormat("#,##0.00'%'")
                .format(value);
    }

    public static boolean equals(Object a, Object b) { //Objeto de lista, objeto buscado
        switch (instanceOf(a, b)) {
            case "integer":
                Integer x = (Integer) a;
                Integer y = (Integer) b;
                return x.equals(y); //Devuelve un booleano comparando los enteros
            case "string":
                String s1 = (String) a;
                String s2 = (String) b;
                return s1.equalsIgnoreCase(s2); //Devuelve un booleano comparando los String
            case "security":
                Security sec1 = (Security)a;
                Security sec2 = (Security)b;
                return sec1.getPassword().equals(sec2.getPassword()) && sec1.getUser().equals(sec2.getUser());
        }
        return false; //En cualquier otro caso retorna un false
    }

    private static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) { //Es a un entero?
            return "integer";
        }
        if (a instanceof String && b instanceof String) { //Es a un entero?
            return "string";
        }
        if (a instanceof Security && b instanceof Security){
            return "security";
        }
        return "unknown";
    }
    
    public static boolean lessT(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "integer":
               Integer x =(Integer)a; Integer y = (Integer)b;
               return x<y;
            case "string":
                String s1 =(String)a; String s2 = (String)b;
               return s1.compareTo(s2)<0;
            case "character":
                Character c1=(Character)a; Character c2=(Character)b;
                return c1.compareTo(c2)<0;
        }
        return false; //en cualquier otro caso
    }
     

    public static boolean greaterT(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "integer":
               Integer x =(Integer)a; Integer y = (Integer)b;
               return x>y;
            case "string":
                String s1 =(String)a; String s2 = (String)b;
               return s1.compareTo(s2)>0;
            case "character":
                Character c1=(Character)a; Character c2=(Character)b;
                return c1.compareTo(c2)>0;
        }
        return false; //en cualquier otro caso
    }

    public static String dateFormat(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy")
                .format(date);
    }

    public static boolean emailChecker(String email) {
        //Just send the email and return a boolean if it matches the mail format
        //Nobody knows how the hell the pattern works but it works so...

        //Patrón del correo      
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com");
        Matcher coincidence = pattern.matcher(email);
        return coincidence.find();
    }

    public static String getIDofString(String s) {

        int i = 0;
        String x = "";
        while (!("-").contains("" + s.charAt(i))) {
            x += s.charAt(i);
            i++;
        }

        return x;
    }
}
