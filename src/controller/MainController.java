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
import java.text.NumberFormat;
import java.util.Optional;
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
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an item.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
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
        try {
            // Trigger exception of no part selected
            Inventory.lookupPart(partTableView.getSelectionModel().getSelectedItem().getId());

            // Confirm with user to delete part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete part?");
            Optional<ButtonType> result = alert.showAndWait();

            // Checks whether OK button pressed then deletes part
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem()); // deletes Part object
                partTableView.setItems(Inventory.lookupPart(searchPartTextField.getText())); // refresh filtered table
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
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
            MProductController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

            // Get event source from button
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an item.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("Exception " + e);
        }

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
        try {
            // Trigger exception if no product selected
            Inventory.lookupProduct(productTableView.getSelectionModel().getSelectedItem().getId());

            // Confirm with user to delete part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete product?");
            Optional<ButtonType> result = alert.showAndWait();

            // Checks wither OK button pressed then deletes product
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem()); // deletes Product object
                productTableView.setItems(Inventory.lookupProduct(searchProductTextField.getText())); // refresh filtered table
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

    /**
     * The Exit button closes the application.
     * The Exit button calls the exit() method to close the application.
     *
     * @param event The event object generated after clicking the Exit button.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit application?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
            System.exit(0); // Closes application
    }

    /**
     * This method responds upon key entry in the Part search field.
     * The method updates the Part TableView with filtered Part objects
     * as a result of the search text.
     *
     * @param event The event object generated after text is entered in the search field.
     */
    @FXML
    void onKeyTypedSearchPartIdOrName(KeyEvent event) {
        ObservableList<Part> partFilteredList = Inventory.lookupPart(searchPartTextField.getText());
        partTableView.setItems(partFilteredList);

        // Highlight if only a single row is filtered
        if (partFilteredList.size() == 1) {
            partTableView.getSelectionModel().select(partFilteredList.get(0));
        } else {
            partTableView.getSelectionModel().clearSelection();
        }
    }

    /**
     * This method responds upon key entry in the Product search field.
     * The method updates the Product TableView with filtered Product objects
     * as a result of the search text.
     *
     * @param event The event object generated after text is entered in the search field.
     */
    @FXML
    void onKeyTypedSearchProductIdOrName(KeyEvent event) {
        ObservableList<Product> productFilteredList = Inventory.lookupProduct(searchProductTextField.getText());
        productTableView.setItems(productFilteredList);

        if (productFilteredList.size() == 1) {
            productTableView.getSelectionModel().select(productFilteredList.get(0));
        } else {
            productTableView.getSelectionModel().clearSelection();
        }
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


//        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
//        partPricePerUnitColumn.setCellFactory(partDoubleTableColumn -> {
//
//        });
    }
}

