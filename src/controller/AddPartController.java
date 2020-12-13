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

/**
 * This class creates the Add Part controller.
 */
public class AddPartController implements Initializable {
    private Stage stage; // Required for displaying application
    private Parent scene;

    @FXML
    private RadioButton inHousePartRadioButton;

    @FXML
    private RadioButton outsourcedPartRadioButton;

    @FXML
    private Label labelPartCompanyOrMachineID;

    @FXML
    private Label errorLabel;

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
     * A Button that exits to Main window.
     * This method cancels any add part action, and returns the user
     * to the main controller window.
     * <p>
     * Logic issues: None. I added an alert button much later in the programming
     * process and modified the stage to first pass a test inside an if statement.
     * <p>
     * Compatible features: In a future version of this project I could utilize
     * the same alert feature and append it to the add button--that way a
     * confirmation will be displayed to he user in a popup UI dialog whenever
     * a save will be committed.
     *
     * @param event The button click event object.
     * @throws IOException
     */
    @FXML
    void onActionCancelPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit and\ndiscard changes?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This RadioButton ActionEvent changes the Company Name
     * Label to Machine ID.
     * <p>
     * Logic issues: In combination with the validateRadioButtonAction()
     * static method, after deciding how to validate the adjacent
     * TextField, I decided to leave it up to the RadioButton and
     * not use the label to validate.
     * <p>
     * Compatible features: I would like to create a popup in future
     * versions letting users know that their Company Name to
     * Machine ID will create an error if the values are not Integers.
     *
     * @param event The RadioButton ActionEvent
     */
    @FXML
    void onActionInHousePartRadio(ActionEvent event) {
        labelPartCompanyOrMachineID.setText("Machine ID");
    }

    /**
     * This RadioButton ActionEvent changes the Machine ID
     * Label to Company Name.
     * <p>
     * Logic issues: In combination with the validateRadioButtonAction()
     * static method, after deciding how to validate the adjacent
     * TextField, I decided to leave it up to the RadioButton and
     * not use the label to validate.
     * <p>
     * Compatible features: I would like to create a popup in future
     * versions letting users know that their Company Name to
     * Machine ID will create an error if the values are not Integers.
     *
     * @param event The RadioButton ActionEvent
     */
    @FXML
    void onActionOutsourcedPartRadio(ActionEvent event) {
        labelPartCompanyOrMachineID.setText("Company Name");
    }

    /**
     * This method saves the values in the TextFields into a Part
     * object. This method carries out TextField and RadioButton
     * validation before instantiating a Part object.
     * <p>
     * Logic issues: Trying to validate both the TextFields and the
     * RadioButton selections was difficult. I had originally put the
     * entire validation within this method, but realized that I
     * would need to replicate the code for other stages' validation.
     * I then separated the validation into two separate static
     * methods inside the Main.java class.
     * One particularly difficult bug was having Main.validate() return
     * true and continue to the second step in validating the
     * RadioButton selection values. Using the && operator only
     * evaluated Main.validate(), and did not continue to the
     * second step of validation. To fix this issue, I learned to
     * use the bitwise operator: & in order for both validations
     * to be evaluated by the compiler.
     * <p>
     * Compatible features: I would think that an upgraded version
     * of this method would update the UI during errors. The current
     * version only makes use of a StringBuilder object to pass
     * error validation messages to the UI. Future versions of this
     * method should return a validation object instead of a boolean
     * object.
     *
     * @param event The ActionEvent object created when the save button is
     *              triggered.
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        // After validation passes, InHouse or Outsourced object
        // be created, and stage will move to MainController.
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
            // Check to see whether InHouse or Outsourced radio button is
            // selected, then create the corresponding InHouse or Outsourced
            // Part object.
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
        // Display any errors in UI
        errorLabel.setText(String.valueOf(Main.errorMessages));
    }

    /**
     * This method is the first method called when the AddPartController executes.
     * The method initially set the default label for the Machine ID and Company
     * Name to Machine ID.
     *
     *
     * @param url       Unused
     * @param resourceBundle Unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelPartCompanyOrMachineID.setText("Machine ID"); // Sets default label
    }
}

