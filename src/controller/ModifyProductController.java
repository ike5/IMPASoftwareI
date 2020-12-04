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

public class ModifyProductController {
    Stage stage;
    Parent scene;

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
    private TableView<?> modifyProductTableView1;

    @FXML
    private TableColumn<?, ?> modifyProductPartIdColumn;

    @FXML
    private TableColumn<?, ?> modifyProductPartNameColumn;

    @FXML
    private TableColumn<?, ?> modifyProductInvLevelColumn;

    @FXML
    private TableColumn<?, ?> modifyProductPriceColumn;

//    @FXML
//    private Button modifyProductAddButton;

    @FXML
    private TableView<?> modifyProductTableView2;

    @FXML
    private TableColumn<?, ?> modifyProductPartIdCol;

    @FXML
    private TableColumn<?, ?> modifyProductPartNameCol;

    @FXML
    private TableColumn<?, ?> modifyProductInvLevelCol;

    @FXML
    private TableColumn<?, ?> modifyProductPriceCol;

//    @FXML
//    private Button modifyProductRemoveAssButton;
//
//    @FXML
//    private Button modifyProductSaveButton;
//
//    @FXML
//    private Button modifyProductCancelButton;

    @FXML
    void onActionModifyProductAdd(ActionEvent event) {

    }

    /**
     * Button exits to Main window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionModifyProductCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyProductRemoveAss(ActionEvent event) {

    }

    @FXML
    void onActionModifyProductSave(ActionEvent event) {

    }
}
