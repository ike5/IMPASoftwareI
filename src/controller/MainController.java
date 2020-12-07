package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class creates the Main controller.
 */
public class MainController implements Initializable {
    Stage stage;  // Stage required to display application.
    Parent scene; // Can have as many scenes as you want.
    public static int makePartId; // Provides a unique ID for parts among all packages.
    public static int makeProductId; // Provides a unique ID for products among all packages.
    private static ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allFilteredProducts = FXCollections.observableArrayList();

    @FXML
    private TextField searchPartTextField; // Search box for Parts TableView

    @FXML
    private TableView<Part> partTableView; // The Parts TableView

    @FXML
    private TableColumn<Part, Integer> partIdColumn; // Part ID column

    @FXML
    private TableColumn<Part, String> partNameColumn; // Part Name column

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn; // Part Inv column

    @FXML
    private TableColumn<Part, Double> partPricePerUnitColumn; // Part Price column

    @FXML
    private TextField searchProductTextField; // Search box for Product TableView

    @FXML
    private TableView<Product> productTableView; // The Product TableView

    @FXML
    private TableColumn<Product, Integer> productIdColumn; // Product ID column

    @FXML
    private TableColumn<Product, String> productNameColumn; // Product Name column

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn; // Product Inv column

    @FXML
    private TableColumn<Product, Double> productPricePerUnitColumn; // Product Price column

    /**
     * The Add button opens the AddPart stage.
     * The button responds to click events to load and display the AddPart stage.
     *
     * @param event The event object generated after clicking the Add button.
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        // Get event source from button
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The Add button opens the AddProduct stage.
     * The button responds to click events to load and display the AddProduct stage.
     *
     * @param event The event object generated after clicking the Add button.
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        // Get event source from button
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The Delete button removes a selected Part object from Inventory.
     * Note that deleting from either the filtered or non-filtered list
     * refreshes the TableView with the correct Inventory Parts.
     *
     * @param event The event object generated after clicking the Delete button.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem()); // deletes Part object
        partTableView.setItems(filterPart(searchPartTextField.getText())); // refresh filtered table
    }

    /**
     * The Delete button removes a selected Product object from Inventory.
     * Note that deleting from either the filtered or non-filtered list
     * refreshes the TableView with the correct Inventory Products.
     *
     * @param event The event object generated after clicking the Delete button.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        System.out.println("Delete product button clicked!");
        Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem()); // deletes Product object
        productTableView.setItems(filterProduct(searchProductTextField.getText())); // refresh filtered table
    }

    /**
     * The Exit button closes the application.
     * The Exit button calls the exit() method to close the application.
     *
     * @param event The event object generated after clicking the Exit button.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0); // Closes application
    }

    /**
     * The Modify button opens the ModifyPart stage.
     * The button responds to click events to load and display the ModifyPart stage.
     *
     * @param event The event object generated after clicking the Modify button.
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        // Get event source from button
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The Modify button opens the ModifyProduct stage.
     * The button responds to click events to load and display the ModifyProduct stage.
     *
     * @param event The event object generated after clicking the Modify button.
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        // Get event source from button
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method responds upon key entry in the search field.
     * The method updates the Part TableView with filtered Part objects
     * as a result of the search text.
     *
     * @param event The event object generated after text is entered in the search field.
     */
    @FXML
    void onKeyTypedSearchPartIdOrName(KeyEvent event) {
        partTableView.setItems(filterPart(searchPartTextField.getText()));
    }

    @FXML
    void onKeyTypedSearchProductIdOrName(KeyEvent event) {
        productTableView.setItems(filterProduct(searchProductTextField.getText()));
    }

    /**
     * This method is used to see whether a Part object exists in Inventory.
     * The method iterates through Inventory and stops when a match is found.
     *
     * @param id The id of a Part object.
     * @return Returns true if a Part object is in Inventory, and false if not found.
     */
    public boolean searchPart(int id) {
        // search method will set the id
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id)
                return true;
        }
        return false;
    }

//    /**
//     * This method takes in a Part ID and replaces it with a new Part object.
//     * The method searches through Inventory to find the matching Part,
//     * then replaces it with a new Part object as provided by the parameter.
//     *
//     * @param id   The ID of the Part object to be replaced.
//     * @param part The replacing Part object.
//     * @return Returns true if Part ID matches: the Part is then replaced.
//     * Returns false if not match found and no changes are made.
//     */
//    public boolean updatePart(int id, Part part) {
//        int index = -1;
//        for (Part p : Inventory.getAllParts()) {
//            index++; // first time around index will be 0
//            // if there's a match we will perform the update
//            if (p.getId() == id) {
//                Inventory.getAllParts().set(index, part); // set(value of index at match, part that wants to replace)
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * This method filters searched items on a TableView with Part objects.
     * This method is used to search a Part object by name and return matching items.
     *
     * @param name A String representing the name of a Part or ID.
     * @return Returns all filtered Parts in an ObservableList<Part> if the
     * filtered parts list is not empty. Returns original ObservableList<Part>
     * if there are no filtered Parts.
     */
    public ObservableList<Part> filterPart(String name) {
        // to see how many items are in the list
        int index = -1;

        // Clears list if not empty
        if (!getAllFilteredParts().isEmpty())
            getAllFilteredParts().clear();

        // Matching strings added to ObservableList<Part>
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(name)) {
                getAllFilteredParts().add(part);
                index++;
            }
            // Highlight if single item listed
            if (index == 0) {
                partTableView.getSelectionModel().select(part);
            } else {
                // Remove highlights if more than one item listed
                partTableView.getSelectionModel().clearSelection();
            }
        }
        // Returns original list if nothing filtered
        if (getAllFilteredParts().isEmpty()) {
            return Inventory.getAllParts();
        } else {
            return getAllFilteredParts();
        }
    }

    /**
     * This method filters searched items on a TableView with Product objects.
     * This method is used to search a Product object by name and return matching items.
     *
     * @param name A String representing the name of a Product or ID.
     * @return Returns all filtered Products in an ObservableList<Product> if the
     * filtered products list is not empty. Returns original ObservableList<Product>
     * if there are no filtered Products.
     */
    public ObservableList<Product> filterProduct(String name) {
        // to see how many items are in the list
        int index = -1;

        // Clears list if not empty
        if (!getAllFilteredProducts().isEmpty())
            getAllFilteredProducts().clear();

        // Matching strings are added to ObservableList<Product>
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().contains(name)) {
                getAllFilteredProducts().add(product);
                index++;
            }
            // Highlight if single item listed
            if (index == 0) {
                productTableView.getSelectionModel().select(product);
            } else {
                productTableView.getSelectionModel().clearSelection();
            }
        }
        // Returns original list if nothing filtered
        if (getAllFilteredProducts().isEmpty()) {
            return Inventory.getAllProducts();
        } else {
            return getAllFilteredProducts();
        }
    }

    /**
     * This method searches Inventory by Product ID.
     * The method lets user know if a Product exists.
     *
     * @param id The Product ID being searched for.
     * @return Returns true if the Product ID matches one in Inventory,
     * and returns false if no match is found.
     */
    public boolean searchProduct(int id) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * This method returns any filtered parts.
     * Items can be added to and removed via this method by obtaining
     * the ObservableList for the parts filtered.
     *
     * @return Returns an ObservableList with any items added to filtered parts.
     */
    public static ObservableList<Part> getAllFilteredParts() {
        return allFilteredParts;
    }

    /**
     * This method returns any filtered products.
     * Items can be added to and removed via this method by obtaining
     * the ObservableList for the products filtered.
     *
     * @return Returns an ObservableList with any items added to filtered products.
     */
    public static ObservableList<Product> getAllFilteredProducts() {
        return allFilteredProducts;
    }

    /**
     * This method initializes upon staging of window.
     * The method sets up the TableView and provides a way to link the columns to their
     * respective controllers.
     *
     * @param url            Unused parameter
     * @param resourceBundle Unused parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set up table view, let table know which objects will be working with.
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        // get id, and populate cell of ID column
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

