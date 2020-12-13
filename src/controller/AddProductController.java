package controller;

import defaultpackage.Main;
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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates the AddProductController.
 */
public class AddProductController implements Initializable {
    private Stage stage; // Stage required to display application
    private Parent scene;
    // List to temporarily hold associated parts until committed to
    // the associatedProduct() method in the Product object.
    private ObservableList<Part> linkedParts = FXCollections.observableArrayList();

    @FXML
    private TextField nameAddProductTextField;

    @FXML
    private TextField invAddProductTextField;

    @FXML
    private TextField priceAddProductTextField;

    @FXML
    private TextField maxAddProductTextField;

    @FXML
    private TextField minAddProductTextField;

    @FXML
    private TextField searchAddProductTextField;

    @FXML
    private TableView<Part> addProductTableView1;

    @FXML
    private TableColumn<Part, Integer> addProdPartIDColumn;

    @FXML
    private TableColumn<Part, String> addProductPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> addProductInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> addProductPriceColumn;

    @FXML
    private TableView<Part> addProductTableView2;

    @FXML
    private TableColumn<Part, Integer> addProductPartIDCol;

    @FXML
    private TableColumn<Part, String> addProductPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addProductInvLevelCol;

    @FXML
    private TableColumn<Part, Double> addProductPriceCol;

    @FXML
    private Label errorLabel;

    @FXML
    private Label addPartErrorLabel;

    /**
     * This method utilizes a search TextField and displays items
     * matching the query.
     * <p>
     * Logic issues: The search feature was not working because I had initially
     * set the event type as Action event. After receiving an argument type
     * mismatch I fixed the issues by changing the even type from ActionEvent to
     * KeyEvent.
     * <p>
     * Compatible features: This method can be reversed in order to search
     * for items NOT part of the searched string.
     *
     * @param event A KeyEvent object.
     */
    @FXML
    void onKeyTypedSearchPart(KeyEvent event) {
        // Call lookupPart and return a filtered list.
        ObservableList<Part> partFilteredList = Inventory.lookupPart(
                searchAddProductTextField.getText());
        // Set table with filtered list of searched items.
        addProductTableView1.setItems(partFilteredList);

        // Highlight if only a single row is filtered
        if (partFilteredList.size() == 1) {
            addProductTableView1.getSelectionModel().select(partFilteredList.get(0));
        } else if (partFilteredList.size() == 0) {
            addPartErrorLabel.setText("No parts matching search field");
        } else {
            addPartErrorLabel.setText("");
            addProductTableView1.getSelectionModel().clearSelection();
        }
    }

    /**
     * This method adds items from the first TableView to the second
     * TableView and, when saved, becomes part of the Product's
     * associated Parts ObservableList.
     * <p>
     * Logic errors: None. However, I had set this method to receive
     * a static ObservableList filter method. I discarded this method
     * and replaced it with a simpler Inventory.lookupPart(). In
     * addition, the lookupPart() method triggers an exception when
     * a NullPointerException is thrown (when nothing is selected).
     * <p>
     * Compatible features: The Inventory.lookup() triggered exception
     * can be modified to hint at selecting the first item in the
     * first TableView.
     *
     * @param event The ActionEvent object when the Add button is clicked.
     */
    @FXML
    void onActionAddProductAddButton(ActionEvent event) {
        try {
            // Trigger exception of no part selected
            Inventory.lookupPart(addProductTableView1.getSelectionModel().getSelectedItem().getId());

            // Add selected item to linked parts list
            linkedParts.add(addProductTableView1.getSelectionModel().getSelectedItem());
            addProductTableView2.setItems(linkedParts); // Refresh TableView
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an item.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

    /**
     * Button returns to Main window.
     * This button alerts the user before returning the user to the main
     * window upon clicking.
     * <p>
     * Compatible features: The cancel button can be used to clear the TextField
     * items after alerting the user that all fields will be discarded.
     *
     * @param event The ActionEvent button click
     */
    @FXML
    void onActionAddProductCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and discard changes?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method removes any associated Parts to the Product.
     * The Product may or may not include associated Parts, and this
     * method validates whether a part has been selected before removing it.
     * <p>
     * Logic issues: I needed to figure out how to trigger an exception, and I couldn't
     * use the lookupPart() method from Inventory because it did not reference
     * the temporary list of associated parts. I created a helper method in
     * AddProductController called lookupPart to trigger this exception, which
     * fixed the issue.
     * <p>
     * Compatible features: A future version of this ActionEvent should simply
     * make the Remove Association button disabled by default until an item
     * in the list is selected.
     *
     * @param event The ActionEvent object created when button clicked
     */
    @FXML
    void onActionAddProductRemoveAssociation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Trigger exception if no part selected
                lookupPart(addProductTableView2.getSelectionModel().getSelectedItem().getId());

                linkedParts.remove(addProductTableView2.getSelectionModel().getSelectedItem()); // deletes Part object
                addProductTableView2.setItems(linkedParts); // refresh filtered table
            } catch (NullPointerException e) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please select a part.");
                alert2.setTitle("Error Dialog");
                alert2.showAndWait();
            }
        }
    }

    /**
     * This method compiles the descriptive TextFields for the new Product
     * object and saves it. This method first validates all TextFields,
     * automatically creates an ID field, and instantiates a new Product
     * object. Lastly, it adds it to the Inventory ObservableList.
     * <p>
     * Logic issues: I had to decided on creating the Product object before
     * saving the class so that I can add associated parts. Instead of the first
     * action (to first create a Product object) I decided to create the
     * Product object last because it would be easier to associate parts this way.
     * <p>
     * Compatible features: Future updates to this app might want to use the
     * logic in here in a separate helper method.
     *
     * @param event The ActionEvent object created by the Save button
     * @throws IOException
     */
    @FXML
    void onActionAddProductSaveButton(ActionEvent event) throws IOException {
        // Validate all TextFields
        if (Main.validate(
                nameAddProductTextField,
                invAddProductTextField,
                priceAddProductTextField,
                maxAddProductTextField,
                minAddProductTextField
        )) {
            // Automatically create a product ID then assign to a variable
            // for later use.
            int id = ++MainController.makeProductId;

            // Create Product object and add to Inventory ObservableList
            Inventory.addProduct(new Product(
                    id,
                    nameAddProductTextField.getText(),
                    Double.parseDouble(priceAddProductTextField.getText()),
                    Integer.parseInt(invAddProductTextField.getText()),
                    Integer.parseInt(minAddProductTextField.getText()),
                    Integer.parseInt(maxAddProductTextField.getText())
            ));

            // Iterate through all linked parts and add them to the Product
            // object's associated list.
            Product p = Inventory.lookupProduct(id);
            for (Part part : linkedParts) {
                p.addAssociatedPart(part);
            }

            // Go to Main page
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        // Log any errors to user's UI
        errorLabel.setText(String.valueOf(Main.errorMessages));
    }


    /**
     * This helper method is used to trigger an exception when no items are
     * selected in a list. This method, when not receiving a partId value
     * helps to throw a NullPointerException by returning a null value if
     * no list item is selected.
     * <p>
     * Logic issues: I made this method out of trying to make sure that a
     * NullPointerException was thrown whenever onActionAddProductRemoveAssociation
     * was called. I originally had a static ObservableList that I was
     * referencing in place of linkedParts, but changed this method when
     * I changed the temporary associated list of part to linkedParts.
     * The return values stayed the same.
     * <p>
     * Compatible features: While this method is primarily a helper to
     * indicate if and when a NullPointerException should be throw, this
     * method could also be used to populate the empty TextFields in a
     * modify part controller.
     *
     * @param partId The integer ID of the part being searched for
     * @return Returns the Part object if found, or returns Null if no
     * ID matches.
     */
    private Part lookupPart(int partId) {
        for (int i = 0; i < linkedParts.size(); i++) {
            Part p = linkedParts.get(i);

            if (p.getId() == linkedParts.get(i).getId()) {
                return p;
            }
        }
        return null;
    }

    /**
     * This initialize method is similar to the initialize method of the MainController
     * in that it helps set up the columns in TableView1 and TableView2.
     * The values of TableView1 is simply a list of Inventory Parts, while
     * the values of TableView2 is conditional on any associated parts in
     * the Product object being created. Initially this should be empty, as
     * the Product is being added within the Add Product Controller.
     * <p>
     * Logic issues: The Price field displays only a single significant figure
     * after the decimal place whenever a 0 is added. I have tried multiple
     * methods of modifying the PropertyValueFactory to no avail.
     * <p>
     * Compatible features: Any additional filtered lists could be populated
     * within the same TableViews.
     *
     * @param url            Unused parameter
     * @param resourceBundle Unused parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTableView1.setItems(Inventory.getAllParts());
        addProductTableView2.setItems(linkedParts);

        addProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProdPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

