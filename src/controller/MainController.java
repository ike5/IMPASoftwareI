package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class creates the Main controller.
 */
public class MainController implements Initializable {
    Stage stage;  // Stage required to display application.
    Parent scene; // Can have as many scenes as you want.

    @FXML
    private TextField searchPartTextField; // Search box for Parts TableView

    @FXML
    private TableView<Part> partTableView; // The Parts TableView

    @FXML
    private TableColumn<Part, Integer> partIdColumn; // Part ID column

    @FXML
    private TableColumn<Part, String> partNameColumn; // Part Name column

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn; // Part Inv column

    @FXML
    private TableColumn<Part, Double> partPricePerUnitColumn; // Part Price column

//    @FXML
//    private Button addPartButton;
//
//    @FXML
//    private Button modifyPartButton;
//
//    @FXML
//    private Button deletePartButton;

    @FXML
    private TextField searchProductTextField; // Search box for Product TableView

    @FXML
    private TableView<Part> productTableView; // The Product TableView

    @FXML
    private TableColumn<Part, Integer> productIdColumn; // Product ID column

    @FXML
    private TableColumn<Part, String> productNameColumn; // Product Name column

    @FXML
    private TableColumn<Part, Integer> productInventoryLevelColumn; // Product Inv column

    @FXML
    private TableColumn<Part, Double> productPricePerUnitColumn; // Product Price column

    /**
     * The Add button opens the AddPart stage.
     * The button responds to click events to load and display the AddPart stage.
     *
     * @param event The event object generated after clicking the Add button.
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        // Cast to let program know source is a button
        // then cast to let know window is a stage.
        // Get source of event and let handler know event type is a Button.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The Add button opens the AddProduct stage.
     * The button responds to click events to load and display the AddProduct stage.
     *
     * @param event The event object generated after clicking the Add button.
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        // Cast to let program know source is a button
        // then cast to let know window is a stage.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        System.out.println("Delete part button clicked!");
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        System.out.println("Delete product button clicked!");
    }

    /**
     * The Exit button closes the application.
     * The Exit button calls the exit() method to close the application.
     *
     * @param event The events object generated after clicking the Exit button.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        // Closes entire application
        System.exit(0);
    }

    /**
     * Button opens Modify Part window.
     *
     * @param event button click
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        // Cast to let program know source is a button
        // then cast to let know window is a stage.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button opens Modify Product window.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        // Cast to let program know source is a Button
        // then cast to let know window is a stage.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onKeyTypedSearchPartIdOrName(KeyEvent event) {
        partTableView.setItems(filterPart(searchPartTextField.getText()));
    }

    public boolean searchPart(int id) {
        // search method will set the id
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id)
                return true;
        }
        return false;
    }

    public boolean updatePart(int id, Part part) {
        int index = -1;
        for (Part p : Inventory.getAllParts()) {
            index++; // first time around index will be 0
            // if there's a match we will perform the update
            if (p.getId() == id) {
                Inventory.getAllParts().set(index, part); // set(value of index at match, part that wants to replace)
                return true;
            }
        }
        return false;
    }

    public boolean deletePart(int id) {
        for (Part p : Inventory.getAllParts()) {
            if (p.getId() == id) {
                return Inventory.getAllParts().remove(p);
            }
        }
        return false;
    }

    // method to return Part object from id

    /**
     * This method finds and returns a Part object.
     * Using id, this method iterates through Inventory and returns the first match.
     *
     * @param id The integer id of the Part object being requested.
     * @return Returns a Part object.
     */
    public Part selectPart(int id) {
        for (Part p : Inventory.getAllParts()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * This method filters searched items on a TableView.
     * This method is used to search a TableView object and return items matching parameters.
     *
     * @param name A String representing the name of a Part or ID.
     * @return Returns an ObservableList object of Part type containing all matches, or
     * the original ObservableList if no matches are found.
     */
    public ObservableList<Part> filterPart(String name) {
        int index = -1; // to see how many items are in the list

        // Clear list if not empty
        if (!Inventory.getAllFilteredParts().isEmpty()) {
            Inventory.getAllFilteredParts().clear();
        }

        // Any matching strings are put in a filtered ObservableList<Part>
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(name)) {
                Inventory.getAllFilteredParts().add(part);
                index++;
            }

            // If there is a single item, highlight it.
            // Otherwise clear any highlights.
            if (index == 0) {
                partTableView.getSelectionModel().select(part);
            } else {
                partTableView.getSelectionModel().clearSelection();
            }
        }

        // returns the original list if nothing returns filtered
        if (Inventory.getAllFilteredParts().isEmpty()) {
            return Inventory.getAllParts();
        } else {
            return Inventory.getAllFilteredParts();
        }
    }

    public boolean searchProduct(int id) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * This method initializes upon staging of window.
     * The method sets up the TableView and provides a way to link the columns to their
     * respective controllers.
     *
     * @param url            Unused parameter
     * @param resourceBundle Unused parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts()); // set up table view, let table know which objects will be working with.

        // filtered list
//        partTableView.setItems(filterPart("o"));

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // get id, and populate cell of ID column
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        // searching
//        if(searchPart(9)){
//            System.out.println("found...");
//        } else {
//            System.out.println("not found...");
//        }

        // updating
//        if (updatePart(55, new InHouse(9, "bakery", 355.55, 6, 1, 20, "tmahine323"))){
//            System.out.println("Update successful");
//        } else {
//            System.out.println("Update failed");
//        }

        // deleting
//        if(deletePart(3)){
//            System.out.println("Deleted!");
//        } else {
//            System.out.println("No match!");
//        }

        // highlight row
        // get reference to TableView object
//        partTableView.getSelectionModel().select(selectPart(3));
    }
}

