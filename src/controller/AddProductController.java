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

public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;
    ObservableList<Part> linkedParts = FXCollections.observableArrayList();

    @FXML
    private TextField idAddProductTextField;

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
     * Why is the logic in this not the same as the MainController class? I did
     * everything exactly as I did in MainController but I'm getting an exception
     * for argument type mismatch.
     * <p>
     * Solution: Changed ActionEvent to KeyEvent.
     *
     * @param event A KeyEvent object.
     */
    @FXML
    void onKeyTypedSearchPart(KeyEvent event) {
        try {
            ObservableList<Part> partFilteredList = Inventory.lookupPart(searchAddProductTextField.getText());
            addProductTableView1.setItems(partFilteredList);

            // Highlight if only a single row is filtered
            if (partFilteredList.size() == 1) {
                addProductTableView1.getSelectionModel().select(partFilteredList.get(0));
            } else if (partFilteredList.size() == 0){
                addPartErrorLabel.setText("No parts matching search field");
            } else {
                addPartErrorLabel.setText("");
                addProductTableView1.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Logic errors: Same as previous.
     *
     * @param event
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
     *
     * @param event button click
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
     * Logic: I need to figure out how to trigger an exception, and I couldn't
     * use the lookupPart method from Inventory because it did not reference
     * the temporary list of associated parts. I created a helper method in
     * AddProductController called lookupPart to trigger this exception.
     *
     * @param event
     */
    @FXML
    void onActionAddProductRemoveAssociation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove part?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
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
     * Logic errors: Should I create the Product object before saving the class
     * so that I can add associated parts? I decided to leave it for the last
     * action and use a temporary associated parts ObservableList object and to
     * add the associated parts to Product right before saving.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddProductSaveButton(ActionEvent event) throws IOException {
        if (Main.validate(
                nameAddProductTextField,
                invAddProductTextField,
                priceAddProductTextField,
                maxAddProductTextField,
                minAddProductTextField
        )) {
            int id = ++MainController.makeProductId;
            Inventory.addProduct(new Product(
                    id,
                    nameAddProductTextField.getText(),
                    Double.parseDouble(priceAddProductTextField.getText()),
                    Integer.parseInt(invAddProductTextField.getText()),
                    Integer.parseInt(minAddProductTextField.getText()),
                    Integer.parseInt(maxAddProductTextField.getText())
            ));

            // ITERATE THROUGH ALL tempAssociatedParts AND ADD THEM TO THIS
            // PRODUCT OBJECT.
            Product p = Inventory.lookupProduct(id);
            for (Part part : linkedParts) {
                p.addAssociatedPart(part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        errorLabel.setText(String.valueOf(Main.errorMessages));
    }


    private Part lookupPart(int partId) {
        for (int i = 0; i < linkedParts.size(); i++) {
            Part p = linkedParts.get(i);

            if (p.getId() == linkedParts.get(i).getId()) {
                return p;
            }
        }
        return null;
    }

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

