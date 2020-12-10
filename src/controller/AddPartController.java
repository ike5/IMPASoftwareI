package controller;

import defaultpackage.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.lang.*;

public class AddPartController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHousePartRadioButton;

    @FXML
    private ToggleGroup sourceToggleGroup;

    @FXML
    private RadioButton outsourcedPartRadioButton;

    @FXML
    private Label labelPartCompanyOrMachineID;

    @FXML
    private Label errorLabel;

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
    private TextField companyOrMachineIdPartTextField;

    @FXML
    private TextField minPartTextField;

    /**
     * Button exits to Main window.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancelPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit and\ndiscard changes?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionInHousePartRadio(ActionEvent event) {
        labelPartCompanyOrMachineID.setText("Machine ID");
    }

    @FXML
    void onActionOutsourcedPartRadio(ActionEvent event) {
        labelPartCompanyOrMachineID.setText("Company Name");
    }

    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
         /*
        Only once validation passes will InHouse or Outsourced object
        be created, and stage will move to MainController.
         */
        if (Main.validate(
                namePartTextField,
                invPartTextField,
                pricePartTextField,
                maxPartTextField,
                minPartTextField
        ) & Main.validateRadioButtonAction( // use bitwise operator for forced evaluation
                companyOrMachineIdPartTextField,
                inHousePartRadioButton,
                outsourcedPartRadioButton
        )) {
                /*
            Check to see whether InHouse or Outsourced radio button is
            selected, then create the corresponding InHouse/Outsourced
            Part object.
             */
            if (inHousePartRadioButton.isSelected()) {
                Inventory.addPart(new InHouse(
                        ++MainController.makePartId,
                        namePartTextField.getText(),
                        Double.parseDouble(pricePartTextField.getText()),
                        Integer.parseInt(invPartTextField.getText()),
                        Integer.parseInt(minPartTextField.getText()),
                        Integer.parseInt(maxPartTextField.getText()),
                        Integer.parseInt(companyOrMachineIdPartTextField.getText()))
                );
            } else {
                Inventory.addPart(new Outsourced(
                        ++MainController.makePartId,
                        namePartTextField.getText(),
                        Double.parseDouble(pricePartTextField.getText()),
                        Integer.parseInt(invPartTextField.getText()),
                        Integer.parseInt(minPartTextField.getText()),
                        Integer.parseInt(maxPartTextField.getText()),
                        companyOrMachineIdPartTextField.getText())
                );
            }

            // Set stage
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        errorLabel.setText(String.valueOf(Main.errorMessages));
    }

//    public static boolean validateRadioButtonAction(
//            TextField machineIdOrCompany,
//            RadioButton inHouse,
//            RadioButton outsourced
//
//    ) {
//        boolean clearToSave = true;
//        String companyNameOrMachineId = null;
//        String regexInt = "^-?\\d+";
//        String regexWord = "^\\w+( \\w+)*";
//
//              /*
//             if InHouse radio button is selected and Machine ID is not
//             an integer raise an error.
//             */
//        if (inHouse.isSelected()
//                && !(machineIdOrCompany.getText().matches(regexInt))) {
//            clearToSave = false;
//            Main.errorMessages.append("Machine ID: enter a valid integer\n");
//
//                /*
//                If Outsourced radio button is selected and Company name is
//                empty raise an error.
//                 */
//        } else if (outsourced.isSelected()
//                && !(machineIdOrCompany.getText().matches(regexWord))) {
//
//            clearToSave = false;
//            Main.errorMessages.append("Company Name: enter a valid string\n");
//        } else {
//            companyNameOrMachineId = machineIdOrCompany.getText();
//        }
//
//        System.out.println(Main.errorMessages);
//        return clearToSave;
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelPartCompanyOrMachineID.setText("Machine ID"); // Sets default label
    }
}

