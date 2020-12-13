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
 * This class creates the ModifyProductController window.
 */
public class ModifyProductController implements Initializable {
    Stage stage; // Required in order to build the window
    Parent scene;
    // Temporary holding list for associated Parts
    ObservableList<Part> linkedParts = FXCollections.observableArrayList();

    @FXML
    private TextField idModifyProductTextField;

    @FXML
    private TextField nameModifyProductTextField;

    @FXML
    private TextField invModifyProductTextField;

    @FXML
    private TextField priceModifyProductTextField;

    @FXML
    private TextField maxModifyProductTextField;

    @FXML
    private TextField minModifyProductTextField;

    @FXML
    private TextField modifyProductSearchTextField;

    @FXML
    private TableView<Part> modifyProductTableView1;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdColumn;

    @FXML
    private TableColumn<Part, String> modifyProductPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> modifyProductInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> modifyProductPriceColumn;

    @FXML
    private TableView<Part> modifyProductTableView2;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdCol;

    @FXML
    private TableColumn<Part, String> modifyProductPartNameCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductInvLevelCol;

    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol;

    @FXML
    private Label addPartErrorLabel;

    @FXML
    private Label errorLabel;

    /**
     * This KeyEvent method responds to user input in the Search TextField.
     * <p>
     * Logic issues: I couldn't figure out why the list wouldn't populate even
     * though all logic was apparently correct, that is, until I realized that the
     * event object provided by a key event is not an ActionEvent, but a KeyEvent
     * object. I fixed the populating bug by renaming the parameter type from
     * ActionEvent to KeyEvent.
     * <p>
     * Compatible features: Extending the functionality of this method, I would
     * disable the Add button whenever the search results come up null.
     *
     * @param event The KeyEvent object generated when users type into the search
     *              TextField
     */
    @FXML
    void onKeyTypedSearchPart(KeyEvent event) {
        try {
            // Create a temporary filtered list of object matching search
            ObservableList<Part> partFilteredList = Inventory.lookupPart(modifyProductSearchTextField.getText());
            modifyProductTableView1.setItems(partFilteredList);

            // Highlight if only a single row is filtered
            if (partFilteredList.size() == 1) {
                modifyProductTableView1.getSelectionModel().select(partFilteredList.get(0));
            } else if (partFilteredList.size() == 0) {
                // Provide error message in UI if no results from search
                addPartErrorLabel.setText("No parts matching search field");
            } else {
                // Clear error message label if search field is simply blank
                addPartErrorLabel.setText("");
                modifyProductTableView1.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            // Log any unusual errors.
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method copies and pasts any selected item in TableView1 to
     * TableView2. Part objects that are added to TableView2 can then be
     * added to the object's associated parts list.
     * <p>
     * Logic issues: None.
     * <p>
     * Compatible features: Future versions of this ActionEvent method should
     * disable the Add button by default, until at least one item is selected--
     * that way we could eliminate the alert popup messages.
     *
     * @param event The ActionEvent object created when the user clicks the Add button
     */
    @FXML
    void onActionModifyProductAdd(ActionEvent event) {
        try {
            // Trigger exception if none selected
            Inventory.lookupPart(modifyProductTableView1.getSelectionModel().getSelectedItem().getId());

            // Add selected item to linked parts list
            linkedParts.add(modifyProductTableView1.getSelectionModel().getSelectedItem());
            modifyProductTableView2.setItems(linkedParts); // Refresh TableView
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an item.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

    /**
     * This ActionEvent method exits the user to the Main controller window.
     * <p>
     * Logic issues: The UI error message was showing an error dialog but
     * wasn't showing any option to click OK. I fixed this bug by changing
     * AlertType from ERROR to CONFIRMATION enumeration.
     * <p>
     * Compatible features: Instead of using a UI popup dialog to confirm with
     * the user that they would like to cancel, future versions of this method
     * could change the color of the cancel button to red, signaling to the
     * user that an action to exit will be taking place if they click again.
     *
     * @param event The ActionEvent object created when a user clicks the Cancel button
     * @throws IOException
     */
    @FXML
    void onActionModifyProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit?\nAll changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method removes associated parts from TableView2. The Remove Associated
     * Parts button first confirms whether the user would like to remove the associated
     * part, then, if no item is selected, alerts the user to make a selection
     * first.
     * <p>
     * Logic issues: None.
     * <p>
     * Compatible features: Instead of having the user wait for two separate
     * alert messages when no item is clicked, future versions of this app
     * could disable the Remove Associated Parts button if nothing is selected.
     *
     * @param event The ActionEvent object created when the user clicks the
     *              Remove Associated Parts button
     */
    @FXML
    void onActionModifyProductRemoveAssociation(ActionEvent event) {
        // Confirm with user that part is being unassociated
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Trigger exception if no part selected
                lookupPart(modifyProductTableView2.getSelectionModel().getSelectedItem().getId());

                // Remove selected part
                linkedParts.remove(modifyProductTableView2.getSelectionModel().getSelectedItem());
                modifyProductTableView2.setItems(linkedParts);
            } catch (NullPointerException e) {
                // Alert user if no part is selected
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please select a part.");
                alert2.setTitle("Error Dialog");
                alert2.showAndWait();
            }
        }
    }

    /**
     * This method saves any modifications made to the Product object.
     * This method updates the Product object within Inventory with another
     * Product at the same index.
     * <p>
     * Logic issues: Validation was a difficult part of implementing this method.
     * I had originally implemented the validate() method which contained
     * both the TextField validations and the RadioButton validations. When
     * I tried to utilize this method with the original validate() method
     * I could not separate the RadioButton fields from the TextFields
     * because it appeared that declaring them as null from within the method
     * would affect the Modify Part TextFields. Instead, I decided to make
     * the RadioButton validations separate, which fixed the issue by
     * allowing me to use only a single validate() method (as opposed to
     * validating both by validate() and validateRadioButtonAction() methods.
     * <p>
     * Compatible features: A future version of this program could include
     * highlighting all associated parts within TableView1 before confirming
     * to save the modified Product object.
     *
     * @param event The ActionEvent object created when the Save button is
     *              clicked
     * @throws IOException
     */
    @FXML
    void onActionModifyProductSave(ActionEvent event) throws IOException {
        Product oldProduct = Inventory.lookupProduct(Integer.parseInt(idModifyProductTextField.getText()));
        int oldProductIndex = Inventory.getAllProducts().indexOf(oldProduct);
        Product newProduct;


        // Validate TextFields
        if (Main.validate(
                nameModifyProductTextField,
                invModifyProductTextField,
                priceModifyProductTextField,
                maxModifyProductTextField,
                minModifyProductTextField
        )) { // If validates, creates object and replaces old.
            Inventory.updateProduct(oldProductIndex, newProduct = new Product(
                            Integer.parseInt(idModifyProductTextField.getText()),
                            nameModifyProductTextField.getText(),
                            Double.parseDouble(priceModifyProductTextField.getText()),
                            Integer.parseInt(invModifyProductTextField.getText()),
                            Integer.parseInt(minModifyProductTextField.getText()),
                            Integer.parseInt(maxModifyProductTextField.getText())
                    )
            );

            // Any linked parts are added to the new Product associatedParts.
            for (Part p : linkedParts) {
                newProduct.addAssociatedPart(p);
            }

            // Return user to Main controller window
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        // Inform user of any validation errors in UI
        errorLabel.setText(String.valueOf(Main.errorMessages));
    }

    /**
     * This helper method retrieves the Product object selected from the Main
     * Controller class's TableView. The method then populates and sets
     * the temporary associated products in TableView2.
     * <p>
     * Logic issues: I needed to figure out how to make sure that the Price
     * TextField displayed two significant figures after the decimal point
     * consistently. I fixed this issue by using String's format method. I
     * still don't have a viable solution to fix the formatting issue in
     * the other direction--the Products TableView in MainController still
     * shows Price as a single significant figure after the decimal point
     * for whole number products.
     * <p>
     * Compatible features: Future versions of this application might want
     * to disable or enable certain TextFields, in addition to the ID field,
     * if one were to ensure that the Price, for example, were not to change.
     *
     * @param product The Product object received from Main Controller's TableView
     */
    public void sendProduct(Product product) {
        idModifyProductTextField.setText(String.valueOf(product.getId()));
        nameModifyProductTextField.setText(product.getName());
        invModifyProductTextField.setText(String.valueOf(product.getStock()));
        priceModifyProductTextField.setText(String.format("%,.2f", product.getPrice()));
        maxModifyProductTextField.setText(String.valueOf(product.getMax()));
        minModifyProductTextField.setText(String.valueOf(product.getMin()));
        for (Part p : product.getAllAssociatedParts()) {
            linkedParts.add(p);
        }
    }

    /**
     * This helper method is primarily used to trigger an exception when a
     * Part is not selected in either TableView1 or TableView2.
     * <p>
     * Logic issues: None. I adapted this method from Inventory's lookupPart()
     * method by using linkedParts to represent a temporary ObservableList
     * in which I could check against associated parts.
     * <p>
     * Compatible features: A future version of this method could return
     * a boolean instead of a null to eliminate the reliance on try and catch
     * error checking in the parent method.
     *
     * @param partId The integer ID representing the selected Part
     * @return Returns the Part object if exists, otherwise returns null
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
     * This method initializes the two TableViews.
     * <p>
     * Logic issues: I struggled to figure out how to display prices with two
     * significant figures after the decimal point. Whenever a Part's price is
     * a whole number, only a single digit after the decimal point shows. I have
     * tried utilizing PropertyValueFactor methods, but have not found a way
     * that make sense to me yet.
     * <p>
     * Compatible features: The initialize method could also be used in future
     * versions of this application to create the temporary ObservableList used
     * to store values for TableView2.
     *
     * @param url            Unused parameter
     * @param resourceBundle Unused parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductTableView1.setItems(Inventory.getAllParts());

        modifyProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductTableView2.setItems(linkedParts);

        modifyProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
