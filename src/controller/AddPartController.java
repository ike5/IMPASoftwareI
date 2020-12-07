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
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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
    void onActionSavePart(ActionEvent event) throws IOException{

        int id = ++MainController.makePartId;
        String name = namePartTextField.getText();
        int inv = Integer.parseInt(invPartTextField.getText());
        double price = Double.parseDouble(pricePartTextField.getText());
        int max = Integer.parseInt(maxPartTextField.getText());
        int min = Integer.parseInt(minPartTextField.getText());
        String companyNameOrMachineId = companyOrMachineIdPartTextField.getText();
        int convert;



        // select correct radio button
        // then pass to ObservableList<Part> object
        if (inHousePartRadioButton.isSelected()) {
            try{
//                if(isNumeric(companyNameOrMachineId));

                convert = Integer.parseInt(companyNameOrMachineId);
                Inventory.addPart(new InHouse(id, name, price, inv, min, max, convert));
            } catch (NumberFormatException e){
                System.out.println("Not a number");
            }
        } else {
            Inventory.addPart(new Outsourced(id, name, price, inv, min, max, companyNameOrMachineId));
        }



        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelPartCompanyOrMachineID.setText("Machine ID"); // Default label
    }

    public static boolean isNumeric(final String str){
        if(str == null || str.length() == 0){
            return false;
        }
        return str.chars().allMatch(Character::isDigit); // (Character c) -> c.isDigit()
    }
}
