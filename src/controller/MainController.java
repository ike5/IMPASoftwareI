package controller;

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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    Stage stage;  // Every application needs a stage.
    Parent scene; // Can have as many scenes as you want.

    @FXML
    private TextField searchPartTextField;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdColumn; // second column, specify wrapper class data type

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;

    @FXML
    private TableColumn<Part, Double> partPricePerUnitColumn;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private TextField searchProductTextField;

    @FXML
    private TableView<Part> productTableView;

    @FXML
    private TableColumn<Part, Integer> productIdColumn;

    @FXML
    private TableColumn<Part, String> productNameColumn;

    @FXML
    private TableColumn<Part, Integer> productInventoryLevelColumn;

    @FXML
    private TableColumn<Part, Double> productPricePerUnitColumn;


    /**
     * Button opens new Add Part window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        // Cast to let program know source is a button
        // then cast to let know window is a stage.
        // Get source of event and let handler know event type is a Button.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button opens new Add Product window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        // Cast to let program know source is a button
        // then cast to let know window is a stage.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        System.out.println("Delete part button clicked!");
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        System.out.println("Delete product button clicked!");
    }

    /**
     * Button exits application.
     *
     * @param event button click
     */
    @FXML
    void onActionExit(ActionEvent event) {
        // Closes entire application
        System.exit(0);
    }

    /**
     * Button opens Modify Part window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        // Cast to let program know source is a button
        // then cast to let know window is a stage.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button opens Modify Product window.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        // Cast to let program know source is a Button
        // then cast to let know window is a stage.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts()); // set up table view, let table know which objects will be working with.

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // get id, and populate cell of ID column
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

