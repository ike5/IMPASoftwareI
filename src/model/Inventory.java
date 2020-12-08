package model;
// incomplete

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    // Provides the list of Part objects
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    // Provides the list of Product objects
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * This method adds a Part object to the allParts ObservableList array.
     * This method can be accessed from all packages.
     *
     * @param newPart The new Part object to be added to the ObservableList.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a Product object to the allProducts ObservableList array.
     * This method can be accessed from all packages.
     *
     * @param newProduct The new Product object to be added to the ObservableList.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method finds and returns a Part object.
     * Using id, this method iterates through Inventory and returns the first match.
     *
     * @param partId The integer id of the Part object being requested.
     * @return Returns a Part object, or null if not found.
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * This method finds and returns a Product object.
     * Using id, this method iterates through Inventory and returns the first match.
     *
     * @param productId The integer id of the Product object being requested.
     * @return Returns a Product object, or null if not found.
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Overloaded method
     * PLEASE REVIEW: I'm not sure what the purpose of this method is...
     *
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                return allParts;
            }
        }
        return null;
    }

    /**
     * PLEASE REVIEW: I'm not sure what the purpose of this method is...
     *
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                return allProducts;
            }
        }
        return null;
    }

    /**
     * This method updates a selected Part at a provided index.
     * The method is similar to the updatePart method offered in the MainController
     * class.
     *
     * @param index        The index for the Part that will be updated in the ObservableList.
     * @param selectedPart The selected Part replacing the part at the index requested.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method implements a new Product at a provided index.
     * The method is similar to the updateProduct method offered in
     * the MainController class.
     *
     * @param index      The index for the Product that will be updated in the ObservableList.
     * @param newProduct The new Product replacing the Product at the index requested.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * This method searches through Inventory and deletes a Part with a matching ID.
     * The method takes a Part as a parameter and deletes the matching
     * Part in Inventory.
     *
     * @param selectedPart The Part object being deleted.
     * @return Returns true if the Part object is removed from the
     * ObservableList, and returns false if item is not found.
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part p : Inventory.getAllParts()) {
            if (p.getId() == selectedPart.getId()) {
                return Inventory.getAllParts().remove(p);
            }
        }
        return false;
    }

    /**
     * This method searches through Inventory and deletes a Product with a matching ID.
     * The method takes a Product as a parameter and deletes the matching
     * Product in Inventory.
     *
     * @param selectedProduct The Product object being deleted.
     * @return Returns true if the Product object is removed from the
     * ObservableList, and returns false if item is not found.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == selectedProduct.getId()) {
                return Inventory.getAllProducts().remove(product);
            }
        }
        return false;
    }


    /**
     * This static method returns an ObservableList of all Part objects.
     * This method can be accessed from anywhere in the program to
     * make CRUD updates to the Inventory.
     *
     * @return Returns an ObservableList of all Part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /**
     * This static method returns an ObservableList of all Product objects.
     * This method can be accessed from anywhere in the program to
     * make CRUD updates to the Inventory.
     *
     * @return Returns an ObservableList of all Product objects.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}
