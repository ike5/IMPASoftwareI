package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField idModifyProductTextField;

    @FXML
    private TextField nameModifyProductTextField;

    @FXML
    private TextField invModifyProductTextField;

    @FXML
    private TextField priceModifyProductTextField;

    @FXML
    private TextField maxModifyProductTextField;

    @FXML
    private TextField minModifyProductTextField;

    @FXML
    private TextField modifyProductSearchTextField;

    @FXML
    private TableView<Part> modifyProductTableView1;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdColumn;

    @FXML
    private TableColumn<Part, String> modifyProductPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> modifyProductInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> modifyProductPriceColumn;

//    @FXML
//    private Button modifyProductAddButton;

    @FXML
    private TableView<Part> modifyProductTableView2;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdCol;

    @FXML
    private TableColumn<Part, String> modifyProductPartNameCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductInvLevelCol;

    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol;

//    @FXML
//    private Button modifyProductRemoveAssButton;
//
//    @FXML
//    private Button modifyProductSaveButton;
//
//    @FXML
//    private Button modifyProductCancelButton;

    @FXML
    void onActionModifyProductAdd(ActionEvent event) {

    }

    /**
     * Button exits to Main window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionModifyProductCancel(ActionEvent event) throws IOException {
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
    void onActionModifyProductRemoveAss(ActionEvent event) {

    }

    @FXML
    void onActionModifyProductSave(ActionEvent event) {
        Product oldProduct = Inventory.lookupProduct(Integer.parseInt(idModifyProductTextField.getText()));
        int oldProductIndex = Inventory.getAllProducts().indexOf(oldProduct);

        try{
            Inventory.updateProduct(oldProductIndex, new Product(
                            Integer.parseInt(idModifyProductTextField.getText()),
                            nameModifyProductTextField.getText(),
                            Double.parseDouble(priceModifyProductTextField.getText()),
                            Integer.parseInt(invModifyProductTextField.getText()),
                            Integer.parseInt(minModifyProductTextField.getText()),
                            Integer.parseInt(maxModifyProductTextField.getText())
                    )
            );

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Number needs to be an integer!");
            alert.showAndWait();
        } catch (IOException e){
            System.out.println("Exception: " + e.getMessage());
        }



    }

    public void sendProduct(Product product) {
        idModifyProductTextField.setText(String.valueOf(product.getId()));
        nameModifyProductTextField.setText(product.getName());
        invModifyProductTextField.setText(String.valueOf(product.getStock()));
        priceModifyProductTextField.setText(String.valueOf(product.getPrice()));
        maxModifyProductTextField.setText(String.valueOf(product.getMax()));
        minModifyProductTextField.setText(String.valueOf(product.getMin()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
