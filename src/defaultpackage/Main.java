package defaultpackage;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * This is the main class that launches the app. Static public methods
 * are included here.
 */
public class Main extends Application {
    public static StringBuilder errorMessages = new StringBuilder();

    /**
     * This method displays the primary stage.
     * The first stage is called by the main() method after
     * launch() is called.
     * <p>
     * Logic issue: There are several ways to instantiate the root resource.
     * While I did not have a difficult time setting this method, it was a
     * break trying to understand how the application could start without
     * having the containers immediately set up as we learned in the textbook.
     * <p>
     * Compatible feature: Maybe in a future version of this application,
     * more than a single window can be instantiated, and possibly have
     * the Parts TableView and the Products TableView in separate scenes.
     *
     * @param primaryStage The first stage created when application starts.
     * @throws Exception The required exception for all JavaFX projects.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml")); // Load Main fxml
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This is the main method. Default Part and Product objects are created
     * and added to the Inventory class before launching the application.
     * <p>
     * Logic issue: I had previously created objects whose name arguments
     * included apostrophes. The errors did not show up on the TableViews
     * so I discovered the validation error much later into the programming
     * of this application. I found out there were errors in validation when
     * the Name field started returning errors for the default objects. I
     * solved this by ensuring that apostrophes are included characters
     * to validate against.
     * <p>
     * Compatible feature: In an updated version of this application, I could
     * instantiate the Product objects with associated parts from here.
     *
     * @param args The first argument for main method.
     */
    public static void main(String[] args) {
        // Create default Part objects
        InHouse inHouse1 = new InHouse(++MainController.makePartId, "Power Supply Module", 5.99, 5, 1, 10, 111211);
        InHouse inHouse2 = new InHouse(++MainController.makePartId, "Jumper Wire", 2.99, 2, 1, 10, 111212);
        InHouse inHouse3 = new InHouse(++MainController.makePartId, "Active Buzzer", 7.25, 4, 1, 16, 111213);
        Outsourced outsourced1 = new Outsourced(++MainController.makePartId, "Precision Potentiometer", 0.95, 40, 5, 100, "ShenZhou Ltd.");
        Outsourced outsourced2 = new Outsourced(++MainController.makePartId, "IC 4N35", 6.95, 1, 1, 5, "ShenZhou Ltd.");
        Outsourced outsourced3 = new Outsourced(++MainController.makePartId, "IC 74HC595", 2.95, 4, 1, 15, "American Chips Co.");

        // Add Part objects to Inventory
        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(inHouse3);
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);
        Inventory.addPart(outsourced3);

        // Create default Products objects
        Product product1 = new Product(++MainController.makeProductId, "Raspberry Pi", 13.59, 2, 1, 8);
        Product product2 = new Product(++MainController.makeProductId, "Freenove Ultimate Starter Kit", 44.95, 10, 1, 12);
        Product product3 = new Product(++MainController.makeProductId, "REXQualis Electronics Component Fun Kit", 15.99, 7, 1, 50);

        // Add Product objects to Inventory
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
     * <p>
     * Logic issue: I struggled to ensure that I could provide the
     * controllers with a way to return a boolean variable in combination
     * with showing which particular fields contained errors. The
     * original version allowed the user to pass in a StringBuilder
     * argument and append all the errors into this single variable.
     * Unfortunately, using a single variable left out validation
     * from the InHouse and Outsourced RadioButton, which I had separated
     * into another static method. To fix this issue of being able to
     * let the user know which specific fields contained errors, I
     * decided to let the controller using this method to call the
     * errorMessages String after this method executes, thereby
     * adding up both the TextFields and, if needed, RadioButton errors
     * in a single string to display to the user.
     * <p>
     * Compatible feature: An updated version would likely split this
     * method to validate specifically the name TextField separately,
     * so that if I wanted to validate user input in the Search field
     * I could simply call this single method.
     *
     * @param name  The Name TextField
     * @param stock The Inv TextField
     * @param price The Price TextField
     * @param max   The Max TextField
     * @param min   The Min TextField
     * @return Returns true if all fields validate, and false if
     * any fields does not correspond to its type.
     */
    public static boolean validate(
            TextField name,
            TextField stock,
            TextField price,
            TextField max,
            TextField min) {

        // Initialize variables
        int vInv = 0;
        int vMax = 0;
        int vMin = 0;
        double vPrice = 0;
        String regexInt = "^-?\\d+";
        String regexDouble = "^-?\\d+(\\.\\d+)?";
        String regexWord = "^\\w+(.*\\w+)*";

        boolean clearToSave = true;

        // Clear errorMessages StringBuilder
        errorMessages.delete(0, errorMessages.length());

        try {
            // Test name TextField
            if (!(name.getText().matches(regexWord))) {
                clearToSave = false;
                errorMessages.append("Name: enter a valid name\n");
            }

            // Test stock TextField for Integer
            if (!(stock.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Inv: enter a valid integer\n");
            } else {
                vInv = Integer.parseInt(stock.getText());
            }

            // Test price TextField for Double
            if (!(price.getText().matches(regexDouble))) {
                clearToSave = false;
                errorMessages.append("Price: enter a valid double\n");
            } else {
                vPrice = Double.parseDouble(price.getText());
            }

            // Test max TextField for Integer
            if (!(max.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Max: enter a valid integer\n");
            } else {
                vMax = Integer.parseInt(max.getText());
            }

            // Test min TextField for Integer
            if (!(min.getText().matches(regexInt))) {
                clearToSave = false;
                errorMessages.append("Min: enter a valid integer\n");
            } else {
                vMin = Integer.parseInt(min.getText());
            }

            // Max logic checking
            if (vMax < 0) {
                clearToSave = false;
                errorMessages.append("Max: must be greater than 0\n");
            } else if (vMax < vMin) {
                clearToSave = false;
                errorMessages.append("Max: must be greater than or equal to Min\n");
            }

            // Min logic checking
            if (vMin < 0) {
                clearToSave = false;
                errorMessages.append("Min: must be greater than 0\n");
            } else if (vMin > vMax) {
                clearToSave = false;
                errorMessages.append("Min: must be less than or equal to Max\n");
            }

            // Inventory logic checking, must be between Max and Min.
            if (vInv > vMax || vInv < vMin) {
                clearToSave = false;
                errorMessages.append("Inv: must be between Max and Min\n");
            } else if (vInv < 0) {
                clearToSave = false;
                errorMessages.append("Inv: must be greater than or equal to 0\n");
            }

            // Price logic checking must be greater than zero.
            if (vPrice < 0) {
                clearToSave = false;
                errorMessages.append("Price: must be greater than 0\n");
            }

            // Log to console
            System.out.println(errorMessages);
        } catch (Exception e) { // Catches any unusual errors.
            System.out.println("Exception: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }

        return clearToSave;
    }


    /**
     * This method validates the InHouse and Outsourced RadioButton TextFields.
     * When InHouse RadioButton is selected, the TextField should only have
     * Integer type input, and when the Outsourced RadioButton is selected,
     * the TextField should allow any combination of characters.
     * <p>
     * Logic issue: This method was originally part of the validate() method
     * above. I separated the RadioButton validation into its own method because
     * in order to use the validate() method provided above in the ModifyProduct
     * controllers, I needed to find a way to not use RadioButton parameters.
     * So, instead, I separated the validations so that I could reuse the
     * validate() method only for ModifyProduct and AddProduct controllers.
     * <p>
     * Compatible feature: In a future version of this application, I could provide
     * two RadioButton options to the ModifyProduct and AddProduct controllers, and
     * use this method (with some name changes) to provide validation for it.
     *
     * @param machineIdOrCompany The machine ID or company name String
     * @param inHouse            The InHouse RadioButton
     * @param outsourced         The Outsourced RadioButton
     * @return This method returns true if the TextFields representing the
     * RadioButton selections are validated as a String or as an Integer, and
     * will return false if the selected RadioButton values do not match
     * their expected value.
     */
    public static boolean validateRadioButtonAction(
            TextField machineIdOrCompany,
            RadioButton inHouse,
            RadioButton outsourced) {

        // Initialize variables
        boolean clearToSave = true;
        String regexInt = "^-?\\d+";
        String regexWord = "^\\w+(.*\\w+)*.?";

        // If InHouse radio button is selected and the Machine ID is not
        // an integer, raise an error.
        if (inHouse.isSelected() && !(machineIdOrCompany.getText().matches(regexInt))) {
            clearToSave = false;
            Main.errorMessages.append("Machine ID: enter a valid integer\n");

            // If Outsourced radio button is selected and the Company name is
            // empty, raise an error.
        } else if (outsourced.isSelected() && !(machineIdOrCompany.getText().matches(regexWord))) {
            clearToSave = false;
            Main.errorMessages.append("Company Name: enter a valid string\n");
        }

        // Log errorMessages to console
        System.out.println(Main.errorMessages);

        return clearToSave;
    }
}

