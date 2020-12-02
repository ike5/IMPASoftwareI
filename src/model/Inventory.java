package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory extends Part{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public Inventory(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }
}
