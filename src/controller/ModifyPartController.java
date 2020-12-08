package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {
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

    @FXML
    private Label modifyPartMachineOrCompanyLabel;

//    @FXML
//    private Button modifyPartSaveButton;
//
//    @FXML
//    private Button modifyPartCancelButton;

    @FXML
    void modifyPartInHouseRButton(ActionEvent event) {
        modifyPartMachineOrCompanyLabel.setText("Machine ID");
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
        modifyPartMachineOrCompanyLabel.setText("Company Name");
    }

    @FXML
    void onActionModifyPartSaveButton(ActionEvent event) throws IOException {
        Part oldPart = Inventory.lookupPart(Integer.parseInt(idModifyPartTextField.getText()));
        int oldPartIndex = Inventory.getAllParts().indexOf(oldPart);

        if (modifyPartInHouseRButton.isSelected()) {
            System.out.println("Calling InHouse Inventory updatePart");
            Inventory.updatePart(
                    oldPartIndex,
                    new InHouse(
                            Integer.parseInt(idModifyPartTextField.getText()),
                            nameModifyPartTextfield.getText(),
                            Double.parseDouble(priceModifyPartTextField.getText()),
                            Integer.parseInt(invModifyPartTextField.getText()),
                            Integer.parseInt(minModifyPartTextField.getText()),
                            Integer.parseInt(maxModifyPartTextField.getText()),
                            Integer.parseInt(machineIdModifyPartTextField.getText())
                    )
            );
        } else {
            System.out.println("Calling Outsourced Inventory updatePart");
            Inventory.updatePart(
                    oldPartIndex,
                    new Outsourced(
                            Integer.parseInt(idModifyPartTextField.getText()),
                            nameModifyPartTextfield.getText(),
                            Double.parseDouble(priceModifyPartTextField.getText()),
                            Integer.parseInt(invModifyPartTextField.getText()),
                            Integer.parseInt(minModifyPartTextField.getText()),
                            Integer.parseInt(maxModifyPartTextField.getText()),
                            machineIdModifyPartTextField.getText()
                    )
            );
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Populates the fields for whatever selected Part in MainController.
     * Uses the instanceof operator to determine how to properly set up
     * the radiobutton.
     *
     * @param part The selected Part to be modified.
     */
    public void sendPart(Part part) {
        idModifyPartTextField.setText(String.valueOf(part.getId()));
        nameModifyPartTextfield.setText(part.getName());
        invModifyPartTextField.setText(String.valueOf(part.getStock()));
        priceModifyPartTextField.setText(String.valueOf(part.getPrice()));
        maxModifyPartTextField.setText(String.valueOf(part.getMax()));
        minModifyPartTextField.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            modifyPartInHouseRButton.setSelected(true);
            modifyPartOutsourcedRButton.setSelected(false);
            modifyPartMachineOrCompanyLabel.setText("Machine ID");
            machineIdModifyPartTextField.setText(String.valueOf(((InHouse) part).getMachineId()));
        }

        if (part instanceof Outsourced) {
            modifyPartOutsourcedRButton.setSelected(true);
            modifyPartInHouseRButton.setSelected(false);
            modifyPartMachineOrCompanyLabel.setText("Company Name");
            machineIdModifyPartTextField.setText(((Outsourced) part).getCompanyName());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // note a lot is already initialized with the sendPart() method
    }
}
