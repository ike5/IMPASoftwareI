package defaultpackage;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * This is the main class that launches the app.
 */
public class Main extends Application {

    /**
     * This method displays the root stage.
     * The first stage displayed is Main.
     *
     * @param primaryStage The first stage created when application starts.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml")); // Load Main fxml
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // Running a static initializer
    static {

    }

    /**
     * This is the main method.
     * Part objects are created and added to Inventory class before launching the application.
     *
     * @param args The first argument for main method.
     */
    public static void main(String[] args) {
        // Create default Part objects
        InHouse inHouse1 = new InHouse(++MainController.makePartId, "Flash drive", 5.99, 5, 1, 10, 234224);
        InHouse inHouse2 = new InHouse(++MainController.makePartId, "Converter", 7.33, 2, 1, 10, 234848);
        InHouse inHouse3 = new InHouse(++MainController.makePartId, "USB wire", 2.99, 4, 1, 16, 2342342);
        Outsourced outsourced1 = new Outsourced(++MainController.makePartId, "Led lights", 0.05, 40, 5, 100, "LinZhou company");
        Outsourced outsourced2 = new Outsourced(++MainController.makePartId, "Plastic covers", 6.95, 2, 1, 5, "ShenZhou Ltd.");
        Outsourced outsourced3 = new Outsourced(++MainController.makePartId, "Bottles", 2.95, 4, 1, 15, "American bottle company");

        // Add Part objects to Inventory.
        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(inHouse3);
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);
        Inventory.addPart(outsourced3);

        Product product1 = new Product(++MainController.makeProductId, "Bobby's dorm", 200.99, 5, 1, 8);
        Product product2 = new Product(++MainController.makeProductId, "Sandy's room", 300.11, 10, 1, 12);
        Product product3 = new Product(++MainController.makeProductId, "Billy's stuff", 20.95, 300, 1, 400);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        // Launch application after Inventory has been populated.
        launch(args);
    }

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
        String regexInt = "^-?\\d+";
        String regexDouble = "^-?\\d+(\\.\\d+)?";
        String regexWord = "^\\w+( \\w+)*";
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

            // Max logic checking
            if (vMax < 0) {
                clearToSave = false;
                errorMessages.append("Max: value must be greater than 0\n");
            } else if (vMax < vMin) {
                clearToSave = false;
                errorMessages.append("Max: value must be greater than or equal to Min\n");
            }

            // Min logic checking
            if (vMin < 0) {
                clearToSave = false;
                errorMessages.append("Min: value must be greater than 0\n");
            } else if (vMin > vMax) {
                clearToSave = false;
                errorMessages.append("Min: value must be less than or equal to Max\n");
            }

            // Inventory logic checking
            if (vInv > vMax || vInv < vMin) {
                clearToSave = false;
                errorMessages.append("Inv: value must be between Max and Min\n");
            } else if (vInv < 0){
                clearToSave = false;
                errorMessages.append("Inv: value must be greater than or equal to 0\n");
            }

            // Price logic checking
            if(vPrice < 0){
                clearToSave = false;
                errorMessages.append("Price: value must be greater than 0\n");
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
                    && !(machineIdOrCompany.getText().matches(regexWord))) {

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

