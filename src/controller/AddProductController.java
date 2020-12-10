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
    ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

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
    void onActionAddProductAddButton(ActionEvent event)  {
        try {
            // Trigger exception of no part selected
            Inventory.lookupPart(addProductTableView1.getSelectionModel().getSelectedItem().getId());

            // Put in temp tableview
            tempAssociatedParts.add(addProductTableView1.getSelectionModel().getSelectedItem());

            // Refresh temp tableview
            addProductTableView2.setItems(tempAssociatedParts);

        } catch (NullPointerException e) {
            System.out.println("Exception: " + e.getMessage());

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

    @FXML
    void onActionAddProductRemoveAss(ActionEvent event) {
        // This button removes highlighted row from bottom table
    }

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
            for( Part part : tempAssociatedParts){
                p.addAssociatedPart(part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        errorLabel.setText(String.valueOf(Main.errorMessages));
    }

    @FXML
    void onActionSearchAddProduct(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTableView1.setItems(Inventory.getAllParts());
        addProductTableView2.setItems(tempAssociatedParts);

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

