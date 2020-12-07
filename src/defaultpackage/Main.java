package defaultpackage;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        Product product1 = new Product(++MainController.makeProductId, "Bobby's dorm", 200.99, 5, 1,8);
        Product product2 = new Product(++MainController.makeProductId, "Sandy's room", 300.11, 10, 1, 12);
        Product product3 = new Product(++MainController.makeProductId, "Billy's stuff", 20.95, 300, 1, 400);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        // Launch application after Inventory has been populated.
        launch(args);
    }
}
