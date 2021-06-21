package Objects;

/**
 *
 * @author Your Name <Sebastián Navarro Martínez>
 */
public class Product {

    private int ID;
    private String name;
    private double price;
    private int supermarketID;

    public static int consecutivo;

    public Product(int ID, String name, double price, int supermarketID) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.supermarketID = supermarketID;
    }

    public Product(String name, double price, int restaurantID) {
        this.name = name;
        this.price = price;
        this.supermarketID = restaurantID;
    }

    public static void setConsecuntivo(int size){
        consecutivo = size;
    }
    
    public void setIDConsecutivo(){
        this.ID = consecutivo;
        consecutivo++;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSupermarketID() {
        return supermarketID;
    }

    public void setSupermarketID(int supermarketID) {
        this.supermarketID = supermarketID;
    }

    @Override
    public String toString() {
        return "Product{" + "ID:" + ID + ", name:" + name + ", price:" + price + ", supermarketID:" + supermarketID + '}';
    }

    public String[] dataName() {
        String[] dataName = {"ID", "name", "price", "supermarketID"};
        return dataName;
    }

    public String[] data() {
        String[] data = {String.valueOf(ID), name, String.valueOf(price), String.valueOf(supermarketID)};
        return data;
    }

}//end class
