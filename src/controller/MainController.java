package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        partTableView.setItems(Inventory.lookupPart(searchPartTextField.getText())); // refresh filtered table
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
        productTableView.setItems(Inventory.lookupProduct(searchProductTextField.getText())); // refresh filtered table
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
    void onActionModifyPart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            // use getController() to get access to an instance of ModifyPartController
            ModifyPartController MPartController = loader.getController();
            MPartController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            // Get event source from button
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show(); // use showAndWait() if you have multiple windows such as a dialog box
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select an item!");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }

    }

    /**
     * The Modify button opens the ModifyProduct stage.
     * The button responds to click events to load and display the ModifyProduct stage.
     *
     * @param event The event object generated after clicking the Modify button.
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            // use getController() to get access to an instance of ModifyProductController
            ModifyProductController MProductController = loader.getController();

            // WORK HERE:
            // NOTICE THAT ABOVE NEEDS A sendProduct() METHOD, BUT IN ORDER TO IMPLEMENT THIS METHOD
            // THE MODIFY PRODUCT CONTROLLER NEEDS TO BE ESTABLISHED WITH THIS METHOD. THE
            // MODIFY PRODUCT CONTROLLER IS DIFFERENT IN SOME WAYS THAN THE MODIFY PART
            // CONTROLLER, SO IT CAN'T SIMPLY BE COPY AND PASTED--ALTHOUGH SOME CAN.

            // Get event source from button
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            // Load resources from view directory
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("YOu need to select an item!");
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("Exception " + e);
        }

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
        ObservableList<Part> oList = Inventory.lookupPart(searchPartTextField.getText());
        partTableView.setItems(oList);

        // Highlight if only a single row is filtered
        if (oList.size() == 1) {
            partTableView.getSelectionModel().select(oList.get(0));
        } else {
            partTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void onKeyTypedSearchProductIdOrName(KeyEvent event) {
        ObservableList<Product> oList = Inventory.lookupProduct(searchProductTextField.getText());
        productTableView.setItems(oList);

        if (oList.size() == 1) {
            productTableView.getSelectionModel().select(oList.get(0));
        } else {
            productTableView.getSelectionModel().clearSelection();
        }
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

