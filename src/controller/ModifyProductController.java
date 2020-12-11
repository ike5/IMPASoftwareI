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

public class ModifyProductController implements Initializable {
    Stage stage;
    Parent scene;
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
     * I couldn't figure out why the list wouldn't populate even
     * though I had the logic correct until I realized that the
     * event object provided by a key event is not an ActionEvent,
     * but a KeyEvent object. Extending the functionality, I would
     * disable the add button when there are no results from the
     * search.
     *
     * @param event
     */
    @FXML
    void onKeyTypedSearchPart(KeyEvent event) {
        try {
            ObservableList<Part> partFilteredList = Inventory.lookupPart(modifyProductSearchTextField.getText());
            modifyProductTableView1.setItems(partFilteredList);

            // Highlight if only a single row is filtered
            if (partFilteredList.size() == 1) {
                modifyProductTableView1.getSelectionModel().select(partFilteredList.get(0));
            } else if (partFilteredList.size() == 0) {
                addPartErrorLabel.setText("No parts matching search field");
            } else {
                addPartErrorLabel.setText("");
                modifyProductTableView1.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
     * Button exits to Main window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionModifyProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?\nAll changes will be lost!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionModifyProductRemoveAssociation(ActionEvent event) {
        try {
            // Trigger exception if no part selected
            lookupPart(modifyProductTableView2.getSelectionModel().getSelectedItem().getId());

            linkedParts.remove(modifyProductTableView2.getSelectionModel().getSelectedItem());
            modifyProductTableView2.setItems(linkedParts);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }


    @FXML
    void onActionModifyProductSave(ActionEvent event) throws IOException {
        Product oldProduct = Inventory.lookupProduct(Integer.parseInt(idModifyProductTextField.getText()));
        int oldProductIndex = Inventory.getAllProducts().indexOf(oldProduct);
        Product newProduct;


        // NEEDS VALIDATION
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

            // Any linked parts are added to newProduct's associatedParts ObservableList.
            for (Part p : linkedParts) {
                newProduct.addAssociatedPart(p);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        errorLabel.setText(String.valueOf(Main.errorMessages));

    }

    public void sendProduct(Product product) {
        idModifyProductTextField.setText(String.valueOf(product.getId()));
        nameModifyProductTextField.setText(product.getName());
        invModifyProductTextField.setText(String.valueOf(product.getStock()));
        priceModifyProductTextField.setText(String.valueOf(product.getPrice()));
        maxModifyProductTextField.setText(String.valueOf(product.getMax()));
        minModifyProductTextField.setText(String.valueOf(product.getMin()));
        for (Part p : product.getAllAssociatedParts()) {
            linkedParts.add(p);
        }
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
