package Util;

import Domain.BST;
import Domain.BTreeNode;
import Domain.CircularLinkList;
import Domain.ListException;
import Domain.TreeException;
import Graphs.AdjacencyListGraph;
import Graphs.EdgeWeight;
import Objects.Food;
import Objects.Place;
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
    public static AdjacencyListGraph gRestAndSuper = new AdjacencyListGraph(100);
    public static AdjacencyListGraph gPlaces = new AdjacencyListGraph(100);
    public static CircularLinkList lSearches = new CircularLinkList();
    public static String userName;
    public static BST treeProducts = new BST();
    public static BST treeFoods = new BST();
    //Last Indexes
    public static int lastIndexGRestAndSuper;
    public static int lastIndexGPlace;
    public static int lastIDFood;
    public static int lastIDProduct;

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

    public static boolean addFood(Food food) throws TreeException {
        boolean flag = false;
        if (treeFoods.isEmpty() || !treeFoods.contains(food)) {
            treeFoods.add(food);
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

    public static BST getTreeFoods() {
        return treeFoods;
    }

    //Devuelve el ultimo id del arbol de food
    public static int getLastIDFood() {
        return lastIDFood;
    }

    public static void setLastIDFood(int lastIDFood) {
        Utility.lastIDFood = lastIDFood;
    }

    public static int getLastIDProduct() {
        return lastIDProduct;
    }

    public static void setLastIDProduct(int lastIDProduct) {
        Utility.lastIDProduct = lastIDProduct;
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
//        System.out.println("Instancia de los objetos en el equals:" + a.getClass()+"-///-"+b.getClass()+"  \n ");
//        System.out.println("Objeto a  "+a.toString());
//        System.out.println("Objeto b  "+b.toString());
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
                return f1.getName().equalsIgnoreCase(f2.getName()) && f1.getID() == f2.getID() && f1.getRestaurantID() == f2.getRestaurantID();
            case "restaurant":
                Restaurant res1 = (Restaurant) a;
                Restaurant res2 = (Restaurant) b;
                return res1.getName().equals(res2.getName()) && res1.getLocation().equals(res2.getLocation());
            case "supermarket":
                Supermarket superm1 = (Supermarket) a;
                Supermarket superm2 = (Supermarket) b;
                return superm1.getName().equals(superm2.getName()) && superm1.getLocation().equals(superm2.getLocation());
            case "productRemove":
                Product p3 = (Product) a;
                String string = (String) b;
                return p3.getName().equalsIgnoreCase(string);
            case "foodRemove":
                Food food = (Food) a;
                String stringFood = (String) b;
                return food.getName().equalsIgnoreCase(stringFood);
            case "place":
                Place pla1 = (Place) a;
                Place pla2 = (Place) b;
                return pla1.getName().equalsIgnoreCase(pla2.getName());
            case "edgeWeight":
                EdgeWeight ew1 = (EdgeWeight) a;
                EdgeWeight ew2 = (EdgeWeight) b;
                //return ew1.getEdge().equals(ew2.getEdge());
                return equals(ew1.getEdge(), ew2.getEdge());

            default:
//                System.out.println("Objects");
                Object o1 = (Object) a;
                Object o2 = (Object) b;
                return o1.toString().equalsIgnoreCase(o2.toString());

        }

//        return false; //En cualquier otro caso retorna un false
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
        if (a instanceof Supermarket && b instanceof Supermarket) {
            return "supermarket";
        }
        if (a instanceof Place && b instanceof Place) {
            return "place";
        }
        if (a instanceof Product && b instanceof String) {
            return "productRemove";
        }
        if (a instanceof Food && b instanceof String) {
            return "foodRemove";
        }
        if (a instanceof Place && b instanceof String) {
            return "placeString";
        }
        if (a instanceof EdgeWeight && b instanceof EdgeWeight) {
            return "edgeWeight";
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
            case "productRemove":
                Product pr1 = (Product) a;
                String pr2 = (String) b;
                return pr1.getName().compareToIgnoreCase(pr2) < 0 || pr1.getName().equalsIgnoreCase(pr2);
            case "foodRemove":
                Food food = (Food) a;
                String stringFood = (String) b;
                return food.getName().compareToIgnoreCase(stringFood) < 0 || food.getName().equalsIgnoreCase(stringFood);

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
            case "productRemove":
                Product pr1 = (Product) a;
                String pr2 = (String) b;
                return pr1.getName().compareToIgnoreCase(pr2) > 0 || pr1.getName().equalsIgnoreCase(pr2);
            case "foodRemove":
                Food food = (Food) a;
                String stringFood = (String) b;
                return food.getName().compareToIgnoreCase(stringFood) > 0 || food.getName().equalsIgnoreCase(stringFood);
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

    public static Restaurant getRestaurantId(String restaurant) throws list.ListException {
        for (int i = 0; i < gRestAndSuper.size(); i++) {
            if (gRestAndSuper.getVertexByIndex(i).data instanceof Restaurant) {
                Restaurant restauran = (Restaurant) gRestAndSuper.getVertexByIndex(i).data;
                if (String.valueOf(restauran.getName()).equalsIgnoreCase(restaurant)) {
                    return restauran;
                }
            }
        }

        return null;
    }

    public static Restaurant getRestaurantId2(int restaurant) throws list.ListException {
        for (int i = 0; i < gRestAndSuper.size(); i++) {
            if (gRestAndSuper.getVertexByIndex(i).data instanceof Restaurant) {
                Restaurant restauran = (Restaurant) gRestAndSuper.getVertexByIndex(i).data;
                if (restauran.getID() == restaurant) {
                    return restauran;
                }
            }
        }
        return null;
    }

    public static void setTreeFoods(BST treeFoods) {
        Utility.treeFoods = treeFoods;
    }
    
    public static Supermarket getSupermarketId(String supermarket) throws list.ListException {
        for (int i = 0; i < gRestAndSuper.size(); i++) {
            if (gRestAndSuper.getVertexByIndex(i).data instanceof Supermarket) {
                Supermarket supermarket2 = (Supermarket) gRestAndSuper.getVertexByIndex(i).data;
                if (String.valueOf(supermarket2.getName()).equalsIgnoreCase(supermarket)) {
                    return supermarket2;
                }
            }
        }
        return null;
    }

    public static Supermarket getSupermarketId2(int supermarket) throws list.ListException {
        for (int i = 0; i < gRestAndSuper.size(); i++) {
            if (gRestAndSuper.getVertexByIndex(i).data instanceof Supermarket) {
                Supermarket supermarket2 = (Supermarket) gRestAndSuper.getVertexByIndex(i).data;
                if (supermarket2.getID() == supermarket) {
                    return supermarket2;
                }
            }
        }
        return null;
    }

    static Product pro;

    public static Product getProductByName(String product) {
        tourTree(treeProducts.getRoot(), product);
        return pro;
    }

    private static void tourTree(BTreeNode node, String product) {
        if (node != null) {
            Product p = (Product) node.data;
            if (p.getName().equalsIgnoreCase(product)) {
                pro = p;
            }
            tourTree(node.left, product);
            tourTree(node.right, product);
        }
    }

    static Food foo;

    public static Food getFoodByName(String foodName) {
        tourTreeFood(treeFoods.getRoot(), foodName);
        return foo;
    }

    private static void tourTreeFood(BTreeNode node, String foodName) {
        if (node != null) {
            Food f = (Food) node.data;
            if (f.getName().equalsIgnoreCase(foodName)) {
                foo = f;
            }
            tourTreeFood(node.left, foodName);
            tourTreeFood(node.right, foodName);
        }
    }

    public static int getProductID() {
        tourTreeProductID(treeProducts.getRoot());
        return lastIDProduct;
    }

    private static void tourTreeProductID(BTreeNode node) {
        if (node != null) {
            Product p = (Product) node.data;
            if (p.getID() > lastIDProduct) {
                setLastIDProduct(p.getID());
            }
            tourTreeProductID(node.left);
            tourTreeProductID(node.right);
        }
    }

    public static int getFoodID() {
        tourTreeFoodID(treeFoods.getRoot());
        return lastIDFood;
    }
    

    private static void tourTreeFoodID(BTreeNode node) {
        if (node != null) {
            Food f = (Food) node.data;
            if (f.getID() > lastIDFood) {
                setLastIDFood(f.getID());
            }
            tourTreeFoodID(node.left);
            tourTreeFoodID(node.right);
        }
    }

}//end class
