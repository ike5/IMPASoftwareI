package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds a Part object to an ObservableList array.
     *
     * @param part
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }


    /**
     * Returns an ObservableList of Part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /**
     * Adds a Product object to an ObservableList array.
     *
     * @param product
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }


    /**
     * Returns an ObservableList of Product objects.
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
