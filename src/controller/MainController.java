package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    Stage stage;
    Parent scene;

    @FXML
    private TextField searchPartTextField;

    @FXML
    private TableView<?> partTableView;

    @FXML
    private TableColumn<?, ?> partIdColumn;

    @FXML
    private TableColumn<?, ?> partNameColumn;

    @FXML
    private TableColumn<?, ?> partInventoryLevelColumn;

    @FXML
    private TableColumn<?, ?> partPricePerUnitColumn;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private TextField searchProductTextField;

    @FXML
    private TableView<?> productTableView;

    @FXML
    private TableColumn<?, ?> productIdColumn;

    @FXML
    private TableColumn<?, ?> productNameColumn;

    @FXML
    private TableColumn<?, ?> productInventoryLevelColumn;

    @FXML
    private TableColumn<?, ?> productPricePerUnitColumn;

    /*
    Will not be needing labels for buttons (I don't think).
    To prevent null pointer exceptions, remove these.

    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button exitButton;
    */

    /**
     * Button opens new Add Part window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}

