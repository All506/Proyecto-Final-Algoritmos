package Util;

import Domain.BST;
import Domain.BTreeNode;
import Domain.CircularLinkList;
import Domain.ListException;
import Domain.TreeException;
import Graphs.AdjacencyListGraph;
import Objects.Food;
import Objects.Product;
import Objects.Restaurant;
import Objects.Security;
import Objects.Supermarket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static CircularLinkList lSecurity = new CircularLinkList();
    public static AdjacencyListGraph gRestaurants = new AdjacencyListGraph(10);
    public static AdjacencyListGraph gPlaces = new AdjacencyListGraph(100);
    public static String userName;
    public static BST treeProducts = new BST();

    //Last Indexes
    public static int lastIndexGRestaurant;

    //Métodos relacionados al manejo de las listas, arboles y grafos
    public static boolean addSecurity(Security sec) throws ListException {
        boolean flag = false;
        if (lSecurity.isEmpty() || !lSecurity.contains(sec)) {
            lSecurity.add(sec);
            flag = true;
        }
        return flag;
    }

    public static boolean addProduct(Product product) throws TreeException {
        boolean flag = false;
        if (treeProducts.isEmpty() || !treeProducts.contains(product)) {
            treeProducts.add(product);
            flag = true;
        }
        return flag;
    }

    //Metodos para devolver listas
    public static CircularLinkList getListSecurity() {
        return lSecurity;
    }

    public static AdjacencyListGraph getPlacesGraph() {
        return gPlaces;
    }

    //Devuelve arbol de productos
    public static BST getTreeProducts() {
        return treeProducts;
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
                Security sec1 = (Security) a;
                Security sec2 = (Security) b;
                return sec1.getPassword().equals(sec2.getPassword()) && sec1.getUser().equals(sec2.getUser());
            case "product":
                Product p1 = (Product) a;
                Product p2 = (Product) b;
                return p1.getName().equalsIgnoreCase(p2.getName()) && p1.getID() == p2.getID() && p1.getSupermarketID() == p2.getSupermarketID();
            case "food":
                Food f1 = (Food) a;
                Food f2 = (Food) b;
                return f1.getID() == f2.getID() ? f1.getRestaurantID() < f2.getRestaurantID() : f1.getID() < f2.getID();
            case "restaurant":
                Restaurant res1 = (Restaurant) a;
                Restaurant res2 = (Restaurant) b;
                return res1.getName().equals(res2.getName()) && res1.getLocation().equals(res2.getLocation());
            case "productRemove":
                Product p3 = (Product) a;
                String string = (String) b;
                return p3.getName().equals(string);
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
        if (a instanceof Security && b instanceof Security) {
            return "security";
        }
        if (a instanceof Product && b instanceof Product) {
            return "product";
        }
        if (a instanceof Food && b instanceof Food) {
            return "food";
        }
        if (a instanceof Restaurant && b instanceof Restaurant) {
            return "restaurant";
        }
        if (a instanceof Product && b instanceof String) {
            return "productRemove";
        }
        return "unknown";
    }

    public static boolean lessT(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "integer":
                Integer x = (Integer) a;
                Integer y = (Integer) b;
                return x < y;
            case "string":
                String s1 = (String) a;
                String s2 = (String) b;
                return s1.compareTo(s2) < 0;
            case "character":
                Character c1 = (Character) a;
                Character c2 = (Character) b;
                return c1.compareTo(c2) < 0;
            case "product":
                Product p1 = (Product) a;
                Product p2 = (Product) b;
                return p1.getID() == p2.getID() ? p1.getSupermarketID() < p2.getSupermarketID() : p1.getID() < p2.getID();
            case "food":
                Food f1 = (Food) a;
                Food f2 = (Food) b;
                return f1.getID() == f2.getID() ? f1.getRestaurantID() < f2.getRestaurantID() : f1.getID() < f2.getID();
        }
        return false; //en cualquier otro caso
    }

    public static boolean greaterT(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "integer":
                Integer x = (Integer) a;
                Integer y = (Integer) b;
                return x > y;
            case "string":
                String s1 = (String) a;
                String s2 = (String) b;
                return s1.compareTo(s2) > 0;
            case "character":
                Character c1 = (Character) a;
                Character c2 = (Character) b;
                return c1.compareTo(c2) > 0;
            case "product":
                Product p1 = (Product) a;
                Product p2 = (Product) b;
                return p1.getID() == p2.getID() ? p1.getSupermarketID() > p2.getSupermarketID() : p1.getID() > p2.getID();
            case "food":
                Food f1 = (Food) a;
                Food f2 = (Food) b;
                return f1.getID() == f2.getID() ? f1.getRestaurantID() > f2.getRestaurantID() : f1.getID() > f2.getID();
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

    public static Supermarket getSupermarketById(String superMarket) {
        Supermarket super1 = new Supermarket(0, "Fresh Market", "Cartago");
//        for (int i = 1; i <= lStudent.size(); i++) {
//            Student s = (Student) lStudent.getNode(i).data;
//            if (String.valueOf(s.getId()).equals(id)) {
//                return s;
//            }
//        }
        return super1;
    }

    Product p;
    public static Product getProductByName(String product) {
        return tourTree(treeProducts.getRoot(), product);
        
    }

    private static Product tourTree(BTreeNode node, String product) {
        if (node != null) {
            Product p = (Product) node.data;
            if (p.getName().equalsIgnoreCase(product)) {
                return p;
            }
            tourTree(node.left, product);
            tourTree(node.right, product);
            
            
        }
        return null;
    }

}//end class
