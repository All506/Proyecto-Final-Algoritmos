/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * @author Your Name <Sebastián Navarro Martínez>
 */
public class Product {

    private int ID;
    private String name;
    private double price;
    private int restaurantID;

    public static int consecutivo;

    public Product(int ID, String name, double price, int restaurantID) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.restaurantID = restaurantID;
    }

    public Product(String name, double price, int restaurantID) {
        this.name = name;
        this.price = price;
        this.restaurantID = restaurantID;
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

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    @Override
    public String toString() {
        return "Product{" + "ID:" + ID + ", name:" + name + ", price:" + price + ", restaurantID:" + restaurantID + '}';
    }

    public String[] dataName() {
        String[] dataName = {"ID", "name", "price", "restaurantID"};
        return dataName;
    }

    public String[] data() {
        String[] data = {String.valueOf(ID), name, String.valueOf(price), String.valueOf(restaurantID)};
        return data;
    }

}//end class
