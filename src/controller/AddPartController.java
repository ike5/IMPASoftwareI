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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?\nAll changes will be lost!");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionInhousePartRadio(ActionEvent event) {
        labelPartCompanyOrMachineID.setText("Machine ID");
    }

    @FXML
    void onActionOursourcedPartRadio(ActionEvent event) {
        labelPartCompanyOrMachineID.setText("Company Name");
    }

    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = ++MainController.makePartId;
            int inv = Integer.parseInt(invPartTextField.getText());
            int max = Integer.parseInt(maxPartTextField.getText());
            int min = Integer.parseInt(minPartTextField.getText());
            double price = Double.parseDouble(pricePartTextField.getText());
            String name = namePartTextField.getText();
            String companyNameOrMachineId = companyOrMachineIdPartTextField.getText();


            // select correct radio button
            // then pass to ObservableList<Part> object
            if (inHousePartRadioButton.isSelected()) {
                try { // check if digit
                    Inventory.addPart(new InHouse(id, name, price, inv, min, max, Integer.parseInt(companyNameOrMachineId)));
                } catch (NumberFormatException e) {
                    System.out.println("Is a digit: " + isNumeric(companyNameOrMachineId));
                }
            } else {
                Inventory.addPart(new Outsourced(id, name, price, inv, min, max, companyNameOrMachineId));
            }


            // Set Stage
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelPartCompanyOrMachineID.setText("Machine ID"); // Default label
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
