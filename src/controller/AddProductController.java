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

public class AddProductController {
    Stage stage;
    Parent scene;

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
    private TableView<?> addProductTableView1;

    @FXML
    private TableColumn<?, ?> addProdPartIDColumn;

    @FXML
    private TableColumn<?, ?> addProductPartNameColumn;

    @FXML
    private TableColumn<?, ?> addProductInvLevelColumn;

    @FXML
    private TableColumn<?, ?> addProductPriceColumn;

//    @FXML
//    private Button addProductAddButton;

    @FXML
    private TableView<?> addProductTableView2;

    @FXML
    private TableColumn<?, ?> addProductPartIDCol;

    @FXML
    private TableColumn<?, ?> addProductPartNameCol;

    @FXML
    private TableColumn<?, ?> addProductInvLevelCol;

    @FXML
    private TableColumn<?, ?> addProductPriceCol;


    // Not necessary to reference buttons
//    @FXML
//    private Button addProductRemoveAssButton;
//
//    @FXML
//    private Button addProductSaveButton;
//
//    @FXML
//    private Button addProductCancelButton;

    @FXML
    void onActionAddProductAddButton(ActionEvent event) throws IOException {
        // This button works to put one row from this table into the table below
    }

    /**
     * Button returns to Main window.
     *
     * @param event button click
     */
    @FXML
    void onActionAddProductCancelButton(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddProductRemoveAss(ActionEvent event) {
        // This button removes highlighted row from bottom table
    }

    @FXML
    void onActionAddProductSaveButton(ActionEvent event) {

    }

    @FXML
    void onActionInvAddProduct(ActionEvent event) {

    }

    @FXML
    void onActionMaxAddProduct(ActionEvent event) {

    }

    @FXML
    void onActionMinAddProduct(ActionEvent event) {

    }

    @FXML
    void onActionNameAddProduct(ActionEvent event) {

    }

    @FXML
    void onActionPriceAddProduct(ActionEvent event) {

    }

    @FXML
    void onActionSearchAddProduct(ActionEvent event) {

    }

}

