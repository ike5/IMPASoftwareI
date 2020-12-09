package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.lang.*;
import java.util.regex.Pattern;

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
    void onActionSavePart(ActionEvent event) {
        int inv = 0;
        int max = 0;
        int min = 0;
        double price = 0;
        String name = null;
        String companyNameOrMachineId = null;
        boolean clearToSave = true;
        String regexInt = "^\\d+";
        String regexDouble = "^\\d+(\\.\\d+)?";
        String regexWord = "^\\w+";
        StringBuilder errorMessages = new StringBuilder();


        try {
            int id = ++MainController.makePartId; // no check needed

            if (!(namePartTextField.getText().matches(regexWord))) {
                clearToSave = false;
                errorMessages.append("Name: enter a valid name\n");
                System.out.println("name called");
            } else {
                name = namePartTextField.getText();
            }

            if (!(invPartTextField.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Inventory: enter a valid integer\n");
                System.out.println("inv called");
            } else {
                inv = Integer.parseInt(invPartTextField.getText());
            }

            if (!(pricePartTextField.getText().matches(regexDouble))) {
                clearToSave = false;
                errorMessages.append("Price: enter a valid double\n");
                System.out.println("price called");
            } else {
                price = Double.parseDouble(pricePartTextField.getText());
            }

            if (!(maxPartTextField.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Max: enter a valid integer\n");
                System.out.println("max called");
            } else {
                max = Integer.parseInt(maxPartTextField.getText());
            }

            if (!(minPartTextField.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Min: enter a valid integer\n");
                System.out.println("Min called");
            } else {
                min = Integer.parseInt(minPartTextField.getText());
            }

            /*
             if InHouse radio button is selected and Machine ID is not
             an integer raise an error.
             */
            if (inHousePartRadioButton.isSelected()
                    && !(companyOrMachineIdPartTextField.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Machine ID: enter a valid integer\n");
                System.out.println("machine called");

                /*
                If Outsourced radio button is selected and Company name is
                empty raise an error.
                 */
            } else if (outsourcedPartRadioButton.isSelected()
                    && companyOrMachineIdPartTextField.getText().matches("")) {

                clearToSave = false;
                errorMessages.append("Company Name: enter a valid string\n");
                System.out.println("company called");
            } else {
                companyNameOrMachineId = companyOrMachineIdPartTextField.getText();
            }

            // REMOVE BEFORE PUBLISHING
            System.out.println("******************************");
            System.out.println(errorMessages);

            // Sets text of the error label
            errorLabel.setText(String.valueOf(errorMessages));

            /*
            Only once validation above passes will InHouse or Outsourced object
            be created, and stage will move to MainController.
             */
            if (clearToSave) {

                /*
                Check to see whether InHouse or Outsourced radio button is
                selected, then create the corresponding InHouse/Outsourced
                Part object.
                 */
                if (inHousePartRadioButton.isSelected()) {
                    Inventory.addPart(new InHouse(
                            id,
                            name,
                            price,
                            inv,
                            min,
                            max,
                            Integer.parseInt(companyNameOrMachineId))
                    );
                } else {
                    Inventory.addPart(new Outsourced(id, name, price, inv, min, max, companyNameOrMachineId));
                }

                // Set Stage
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (Exception e) { // catch any unusual errors
            System.out.println("Exception: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong!");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelPartCompanyOrMachineID.setText("Machine ID"); // Sets default label
    }


    /**
     * This method tests a String to see if it only contains digits.
     *
     * @param str The string being tested.
     * @return Returns true if only digits, returns false if contains
     * other characters or is null or is of length 0.
     */
    public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit); // (Character c) -> c.isDigit()
    }
}
