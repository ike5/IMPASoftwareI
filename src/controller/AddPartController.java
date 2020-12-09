package controller;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?\nAll changes will be lost!");
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
        if (validate(
                namePartTextField,
                invPartTextField,
                pricePartTextField,
                maxPartTextField,
                minPartTextField,
                companyOrMachineIdPartTextField,
                inHousePartRadioButton,
                outsourcedPartRadioButton,
                errorLabel)) {

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
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelPartCompanyOrMachineID.setText("Machine ID"); // Sets default label
    }

//    /**
//     * This method tests a String to see if it only contains digits.
//     *
//     * @param str The string being tested.
//     * @return Returns true if only digits, returns false if contains
//     * other characters or is null or is of length 0.
//     */
//    public static boolean isNumeric(final String str) {
//        if (str == null || str.length() == 0) {
//            return false;
//        }
//        return str.chars().allMatch(Character::isDigit); // (Character c) -> c.isDigit()
//    }

    /**
     * This method validates input and sets any error labels.
     * The method takes in various TextFields, RadioButtons, and
     * a Label, and returns a boolean value if all fields are
     * validated to their respective types.
     *
     * @param name               The Name TextField.
     * @param stock              The Inv TextField.
     * @param price              The Price TextField.
     * @param max                The Max TextField.
     * @param min                The Min TextField.
     * @param machineIdOrCompany The companyOrMachineId TextField.
     * @param inHouse            The inHouse RadioButton.
     * @param outsourced         The outsourced RadioButton.
     * @param errorLabel         The errorLabel Label.
     * @return Returns true if all fields validate, and false if
     * any fields does not correspond to its type.
     */
    public static boolean validate(
            TextField name,
            TextField stock,
            TextField price,
            TextField max,
            TextField min,
            TextField machineIdOrCompany,
            RadioButton inHouse,
            RadioButton outsourced,
            Label errorLabel) {
        int vInv = 0;
        int vMax = 0;
        int vMin = 0;
        double vPrice = 0;
        String vName = null;
        String companyNameOrMachineId = null;
        String regexInt = "^\\d+";
        String regexDouble = "^\\d+(\\.\\d+)?";
        String regexWord = "^\\w+";
        StringBuilder errorMessages = new StringBuilder();
        boolean clearToSave = true;

        try {
            int id = ++MainController.makePartId; // no check needed

            if (!(name.getText().matches(regexWord))) {
                clearToSave = false;
                errorMessages.append("Name: enter a valid name\n");
                System.out.println("name called");
            } else {
                vName = name.getText();
            }

            if (!(stock.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Inventory: enter a valid integer\n");
                System.out.println("inv called");
            } else {
                vInv = Integer.parseInt(stock.getText());
            }

            if (!(price.getText().matches(regexDouble))) {
                clearToSave = false;
                errorMessages.append("Price: enter a valid double\n");
                System.out.println("price called");
            } else {
                vPrice = Double.parseDouble(price.getText());
            }

            if (!(max.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Max: enter a valid integer\n");
                System.out.println("max called");
            } else {
                vMax = Integer.parseInt(max.getText());
            }

            if (!(min.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Min: enter a valid integer\n");
                System.out.println("Min called");
            } else {
                vMin = Integer.parseInt(min.getText());
            }

            /*
             if InHouse radio button is selected and Machine ID is not
             an integer raise an error.
             */
            if (inHouse.isSelected()
                    && !(machineIdOrCompany.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Machine ID: enter a valid integer\n");
                System.out.println("machine called");

                /*
                If Outsourced radio button is selected and Company name is
                empty raise an error.
                 */
            } else if (outsourced.isSelected()
                    && machineIdOrCompany.getText().matches("")) {

                clearToSave = false;
                errorMessages.append("Company Name: enter a valid string\n");
                System.out.println("company called");
            } else {
                companyNameOrMachineId = machineIdOrCompany.getText();
            }

            // REMOVE BEFORE PUBLISHING
            System.out.println("******************************");
            System.out.println(errorMessages);

            // Sets text of the error label
            errorLabel.setText(String.valueOf(errorMessages));


        } catch (Exception e) { // catch any unusual errors
            System.out.println("Exception: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong!");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
        return clearToSave;
    }
}
