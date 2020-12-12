package controller;

import defaultpackage.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.util.Optional;

/**
 * This class creates the ModifyPartController window.
 */
public class ModifyPartController {
    Stage stage; // Required in order to build window
    Parent scene;
    StringBuilder messages = new StringBuilder();

    @FXML
    private RadioButton modifyPartInHouseRButton;

    @FXML
    private RadioButton modifyPartOutsourcedRButton;

    @FXML
    private TextField idModifyPartTextField;

    @FXML
    private TextField nameModifyPartTextField;

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

    @FXML
    private Label modifyPartMachineOrCompanyLabel;

    @FXML
    private Label errorLabel;

    /**
     * This method triggers the InHouse RadioButton ActionEvent
     * and sets the Label value to Machine ID.
     * <p>
     * Logic issues: None.
     * <p>
     * Compatible features: This feature can be used to make
     * the save button disabled and ensure that values in the
     * Machine ID TextField are integers only.
     *
     * @param event The ActionEvent triggered by clicking on
     *              the InHouse RadioButton.
     */
    @FXML
    void modifyPartInHouseRButton(ActionEvent event) {
        modifyPartMachineOrCompanyLabel.setText("Machine ID");
    }

    /**
     * This method triggers the Outsourced RadioButton ActionEvent
     * and sets the Label value to Company Name.
     * <p>
     * Logic issues: None.
     * <p>
     * Compatible features: This feature can be used to make
     * the save button disabled and sure that values in the
     * Company Name TextField match all character values, with
     * the exception of ending with a space or new line character.
     *
     * @param event The ActionEvent triggered by clicking
     *              on the Outsourced RadioButton.
     */
    @FXML
    void onActionModifyPartOutsourcedRButton(ActionEvent event) {
        modifyPartMachineOrCompanyLabel.setText("Company Name");
    }

    /**
     * This button exits to the Main controller window.
     * An alert is called in order to let the user know that exiting
     * will discard all changes. If the user selects OK, the if
     * statement is executed and the stage changes.
     * <p>
     * Logic issues: None. However, I experimented with a try and
     * catch statement, but it did not make catching and alerting
     * the user any easier.
     * <p>
     * Compatible features: This cancel button is generic but can also
     * be updated to not prompt the user and simply exit if there are no
     * values currently inputted in the TextFields, or if there are no
     * added associated Parts.
     *
     * @param event The ActionEvent object created by clicking the Cancel button.
     * @throws IOException
     */
    @FXML
    void onActionModifyPartCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and discard changes?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method saves any modified changes to the Part object selected.
     * This method updates the previous part by removing it from Inventory
     * and replacing at that index a new Part object containing the original
     * and any changed fields.
     * <p>
     * Logic issues: Validation was a difficult part of creating this method. I had
     * previously made all validation within this method, but since the code was being
     * replicated in other methods I decided to provide validation through outside
     * static methods instead. Additionally, I was having trouble letting the user
     * know of any RadioButton errors, because after the first validate() method
     * was executed, if there were any errors it would skip over the second validation
     * and not let the user know of RadioButton validation errors until the end. I
     * fixed this issue by replacing the AND '&&' operator with the bitwise AND '&'
     * operator, thus forcing both methods to execute and return their boolean
     * values.
     * <p>
     * Compatible features: Future versions of this method could disable the save
     * button if there are any real-time validation errors with either the
     * validate() method or the validateRadioButtonAction() method.
     *
     * @param event The ActionEvent triggered when clicking the Save button.
     * @throws IOException
     */
    @FXML
    void onActionModifyPartSaveButton(ActionEvent event) throws IOException {
        // Get old Part object in order to find its index
        Part oldPart = Inventory.lookupPart(Integer.parseInt(idModifyPartTextField.getText()));
        int oldPartIndex = Inventory.getAllParts().indexOf(oldPart);

        // Validate
        if (Main.validate(
                nameModifyPartTextField,
                invModifyPartTextField,
                priceModifyPartTextField,
                maxModifyPartTextField,
                minModifyPartTextField
        ) & Main.validateRadioButtonAction(
                machineIdModifyPartTextField,
                modifyPartInHouseRButton,
                modifyPartOutsourcedRButton)) {
            // Is called if InHouse RadioButton is selected
            if (modifyPartInHouseRButton.isSelected()) {
                // Replaces old Part object
                Inventory.updatePart(
                        oldPartIndex,
                        new InHouse(
                                Integer.parseInt(idModifyPartTextField.getText()),
                                nameModifyPartTextField.getText(),
                                Double.parseDouble(priceModifyPartTextField.getText()),
                                Integer.parseInt(invModifyPartTextField.getText()),
                                Integer.parseInt(minModifyPartTextField.getText()),
                                Integer.parseInt(maxModifyPartTextField.getText()),
                                Integer.parseInt(machineIdModifyPartTextField.getText())
                        )
                );
            } else {
                // Is called if Outsourced RadioButton is selected
                // Replaces old Part object
                Inventory.updatePart(
                        oldPartIndex,
                        new Outsourced(
                                Integer.parseInt(idModifyPartTextField.getText()),
                                nameModifyPartTextField.getText(),
                                Double.parseDouble(priceModifyPartTextField.getText()),
                                Integer.parseInt(invModifyPartTextField.getText()),
                                Integer.parseInt(minModifyPartTextField.getText()),
                                Integer.parseInt(maxModifyPartTextField.getText()),
                                machineIdModifyPartTextField.getText()
                        )
                );
            }

            // Returns user back to Main Controller window
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        // Sets any errors to display in the UI
        errorLabel.setText(String.valueOf(Main.errorMessages));
    }

    /**
     * This helper method allows the Main Controller to send TableView
     * information to the Modify Part Controller. This method helps by
     * populating all TextFields and RadioButton as specified by the
     * Part object received. The user only then needs to worry about
     * making any modifications to the TextFields and click Save.
     * <p>
     * Logic issues: Ensuring that the TextView for Price showed a Double
     * value when receiving two significant figures after the decimal
     * proved difficult. I tried using the initialize method with a
     * PropertyValueFactory, but all my efforts to set the TableView
     * itself to two decimal places did not work. I made a quick fix
     * to this issue by formatting the Price string itself to two
     * decimal places. Unfortunately, this does not help with populating
     * the MainController TableViews, which I am still having trouble
     * with properly setting two decimal values.
     * <p>
     * Compatible features: This method could further be improved by
     * highlighting all imported values a different shade of red or
     * by highlighting all changes made in a different shade of red
     * in order to let the user know whether any changes have been
     * made.
     *
     * @param part The selected Part imported that populates the TextFields
     */
    public void sendPart(Part part) {
        // Populate all TextFields with received Part object values
        idModifyPartTextField.setText(String.valueOf(part.getId()));
        nameModifyPartTextField.setText(part.getName());
        invModifyPartTextField.setText(String.valueOf(part.getStock()));
        priceModifyPartTextField.setText(String.format("%,.2f", part.getPrice()));
        maxModifyPartTextField.setText(String.valueOf(part.getMax()));
        minModifyPartTextField.setText(String.valueOf(part.getMin()));

        // Select InHouse RadioButton if the part is an InHouse object
        if (part instanceof InHouse) {
            modifyPartInHouseRButton.setSelected(true);
            modifyPartOutsourcedRButton.setSelected(false);
            modifyPartMachineOrCompanyLabel.setText("Machine ID");
            // Cast to temporarily get access to subclass methods
            machineIdModifyPartTextField.setText(String.valueOf(((InHouse) part).getMachineId()));
        }

        // Select Outsourced RadioButton if the part is an Outsourced object
        if (part instanceof Outsourced) {
            modifyPartOutsourcedRButton.setSelected(true);
            modifyPartInHouseRButton.setSelected(false);
            modifyPartMachineOrCompanyLabel.setText("Company Name");
            // Cast to temporarily get access to subclass methods
            machineIdModifyPartTextField.setText(((Outsourced) part).getCompanyName());
        }
    }
}
