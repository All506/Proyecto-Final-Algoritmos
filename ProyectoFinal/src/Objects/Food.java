/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Your Name <Sebastián Navarro Martínez>
 */
public class Food {
    private int ID;
    private String name;
    private double price;
    private int restaurantID;

    public Food(int ID, String name, double price, int restaurantID) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.restaurantID = restaurantID;
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

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    @Override
    public String toString() {
        return "Food{" + "ID: " + ID + ", name: " + name + ", price: " + price + ", restaurantID: " + restaurantID + '}';
    }


    
}
