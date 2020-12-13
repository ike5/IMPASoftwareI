package controller;

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
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates the Main controller.
 */
public class MainController implements Initializable {
    private Stage stage;  // Stage required to display application.
    private Parent scene;
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
     * <p>
     * Logic issues: None. This method was easy to implement after watching
     * the online lectures.
     * <p>
     * Compatible features: A future version of this method could include
     * more visual feedback to the user that the button has been selected.
     * I would also like to implement a click sound.
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
     * <p>
     * Logic issue: None. I utilized a lot of the code from onActionModifyProduct()
     * in order to implement this.
     * <p>
     * Compatible features: The button could remain unavailable until an item
     * is selected. This would eliminate the NullPointerException every
     * time.
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
     * <p>
     * Logic issue: I experimented with how to trigger an exception whenever
     * no item was selected in the TableView while clicked. I originally
     * had lookup part in an 'if' statement, but decided against heavy
     * nesting and to use the compiler's NullPointerException to trigger
     * the error.
     * <p>
     * Compatible features: I could extend the function of the delete
     * button by making the button unavailable until an item is
     * selected in the TableView.
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
            System.out.println("Null pointer exception thrown");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

    /**
     * The Add button opens the AddProduct stage.
     * The button responds to click events to load and display the AddProduct stage.
     * <p>
     * Logic errors: There were no errors here. The casting from Button in order
     * to get the event source was tricky, especially when trying to cast to
     * a Stage object--it didn't make much sense to me early on in the
     * programming process. My thoughts were: if I am getting the source of
     * the button event object, it would only be the button. But since
     * the button is part of the stage, it only make sense that you can
     * use the .getScene() method and the .getWindow() method in order
     * to cast the entire stage to a Stage object.
     * <p>
     * Compatible features: This is a very generic window switching tool. I have
     * used it throughout this application already. Unlike how I have currently
     * laid out my application, with two separate fxml files for both the
     * AddPart and ModifyPart controllers, I could use this method with a
     * bit of tweaking to transfer different objects to the same fxml file
     * if needed.
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
     * <p>
     * Logic issue: The ModifyProduct scene was not loading. I first tried to copy
     * and paste code from the Animals activity that was part of the video
     * lectures, to no avail. In order to fix the problem of no screen showing
     * I found out that I needed to load from .getRoot(). This fixed the issue.
     * <p>
     * Compatible features: This method could be used to call the MainController
     * from any other controller. If I wanted to create an addPart button
     * directly from ModifyController, I could use this code to transfer
     * the existing Part object to the add screen.
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
     * <p>
     * Semantic issue: I had difficulty making sure that all associated parts
     * were taken into account and, if the product had an associated
     * part, would prevent the item from being deleted. After implementing
     * the if statement to check to see if the product had an associated
     * part, the program was still not showing the correct alert message.
     * Only after debugging the program and implementing print statements
     * in the console I could see that the second alert message was being
     * skipped because it had the same name as the first. I corrected the
     * issue by renaming the second alert message from alert to alert1.
     * <p>
     * Compatible features: This method can be used to implement the remove
     * associated parts with a few changes.
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

                // Checks if product has an associatedPart
                if (Inventory.lookupProduct(productTableView
                        .getSelectionModel().getSelectedItem().getId())
                        .getAllAssociatedParts().size() > 0) {

                    // Alert user that a part is still associated with the product
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Has part associated with it!\nPlease remove part then delete.");
                    alert1.setTitle("Error Dialog");
                    alert1.showAndWait();
                } else {
                    // Delete the product
                    Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem()); // deletes Product object
                    productTableView.setItems(Inventory.lookupProduct(searchProductTextField.getText())); // refresh filtered table
                }


            }
        } catch (NullPointerException e) {
            // Log to console
            System.out.println("Null pointer exception thrown");

            // Alert user to select a product
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

    /**
     * The Exit button closes the application.
     * The Exit button calls the exit() method to close the application.
     * <p>
     * Logic issue: I had originally created a way to exit the program
     * by calling the stage.close() method. This method was inefficient
     * and after learning to simply call System.exit(), I implemented
     * it since it could pass in a status code of 0 to be returned
     * to the console.
     * <p>
     * Compatible features: In future updates of this program, I could
     * implement this method within the Modify part and product windows
     * in order to create a 'save and close' feature.
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
     * <p>
     * Logic issue: I had trouble making the filter for both integers and
     * for strings. After watching several of the online resources on
     * creating a search TextField, I discovered that I could add all
     * searches into an ObservableList within this method itself and
     * utilize the lookup() Inventory method to return an observable
     * list of filtered parts.
     * <p>
     * Compatible features: If a updated version of this method were created,
     * I could create an UI message or alert that notifies the user when
     * a search comes up unsuccessful.
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
     * <p>
     * Logic issue: I had originally implemented a static ObservableList
     * in order to search and place all matching items into a temporary
     * list. Using a static field was unnecessary since I discovered that the
     * list will filter on each KeyEvent.
     * <p>
     * Compatible features: This method could be updated to include a filter
     * that searches only for those products that contain parts associated
     * with it.
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
     * <p>
     * Logic issue: I don't think I really understand how the PropertyValueFactory
     * string calls the items inside of an ObservableList.
     * <p>
     * Compatible features: If a lot of table items were to be used,
     * I would implement a visual to let the user know that the TableView
     * was being loaded.
     *
     * @param url            Unused parameter
     * @param resourceBundle Unused parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up table view, let table know which objects will be working with.
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        // Get id, and populate cell of ID column
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

