package model;
// incomplete

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // my own fields
    private static ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();


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

    public static Part lookupPart(int partId) {
        for (Part part : allParts){
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
   }

    public static Product lookupProduct(int productId){
        for (Product product : allProducts){
            if(product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        for(Part part : allParts){
            if(part.getName().contains(partName)){
                return allParts;
            }
        }
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName){
        for(Product product : allProducts){
            if(product.getName().contains(productName)){
                return allProducts;
            }
        }
        return null;
    }

    public static void updatePart(int index, Part selectedPart) {
        
    }

    public static void updateProduct(int index, Product newProduct) {

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


    // My own methods for filtering
    public static ObservableList<Part> getAllFilteredParts() {
        return allFilteredParts;
    }
}
