package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton modifyPartInHouseRButton;

    @FXML
    private RadioButton modifyPartOutsourcedRButton;

    @FXML
    private TextField idModifyPartTextField;

    @FXML
    private TextField nameModifyPartTextfield;

    @FXML
    private TextField invModifyPartTextField;

    @FXML
    private TextField priceModifyPartTextField;

    @FXML
    private TextField maxModifyPartTextField;

    @FXML
    private TextField machineIdModifyPartTextField;

    @FXML
    private TextField minModifyPartTextField;

//    @FXML
//    private Button modifyPartSaveButton;
//
//    @FXML
//    private Button modifyPartCancelButton;

    @FXML
    void modifyPartInHouseRButton(ActionEvent event) {

    }

    /**
     * Button exits to Main window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionModifyPartCancelButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyPartOutsourcedRButton(ActionEvent event) {

    }

    @FXML
    void onActionModifyPartSaveButton(ActionEvent event) {

    }
}
