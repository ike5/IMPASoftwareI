package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ModifyProductController {
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

    @FXML
    private Button modifyProductAddButton;

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

    @FXML
    private Button modifyProductRemoveAssButton;

    @FXML
    private Button modifyProductSaveButton;

    @FXML
    private Button modifyProductCancelButton;

    @FXML
    void onActionModifyProductAdd(ActionEvent event) {

    }

    @FXML
    void onActionModifyProductCancel(ActionEvent event) {

    }

    @FXML
    void onActionModifyProductRemoveAss(ActionEvent event) {

    }

    @FXML
    void onActionModifyProductSave(ActionEvent event) {

    }
}
