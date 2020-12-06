package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds a Part object to an ObservableList array.
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a Product object to an ObservableList array.
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

//    public static Part lookupPart(int partId) {
//        return allParts.contains();
//   }

//    public static Product lookupProduct(int productId){
//        return
//    }

//    public static ObservableList<Part> lookupPart(String partName) {
//        return
//    }

//    public static ObservableList<Product> lookupProduct(){
//        return
//    }

    public static void updatePart(int index, Part selectedPart){

    }

    public static void updateProduct(int index, Product newProeuct){

    }

//    public static boolean deletePart(Part selectedPart){
//        return false;
//    }

//    public static boolean deleteProduct(Product selectedProduct){
//        return false;
//    }

    /**
     * Returns an ObservableList of Part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /**
     * Returns an ObservableList of Product objects.
     *
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}
