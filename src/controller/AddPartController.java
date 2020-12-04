package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartController {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inhousePartRadioButton;

    @FXML
    private ToggleGroup sourceToggleGroup;

    @FXML
    private RadioButton outsourcedPartRadioButton;

    @FXML
    private TextField idAddPartTextField;

    @FXML
    private TextField namePartTextField;

    @FXML
    private TextField invPartTextField;

    @FXML
    private TextField pricePartTextField;

    @FXML
    private TextField maxPartTextField;

    @FXML
    private TextField machineIdPartTextField;

    @FXML
    private TextField minPartTextField;

//    @FXML
//    private Button savePartButton;
//
//    @FXML
//    private Button cancelPartButton;

    /**
     * Button exits to Main window.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancelPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionInhousePartRadio(ActionEvent event) {

    }

    @FXML
    void onActionInvPartTextField(ActionEvent event) {

    }

    @FXML
    void onActionMachineIdPartTextField(ActionEvent event) {

    }

    @FXML
    void onActionMaxPartTextField(ActionEvent event) {

    }

    @FXML
    void onActionMinPartTextField(ActionEvent event) {

    }

    @FXML
    void onActionNamePartTextField(ActionEvent event) {

    }

    @FXML
    void onActionOursourcedPartRadio(ActionEvent event) {

    }

    @FXML
    void onActionPricePartTextField(ActionEvent event) {

    }

    @FXML
    void onActionSavePart(ActionEvent event) {

    }

}
