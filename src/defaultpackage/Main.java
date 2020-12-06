package defaultpackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        /*
        Default Parts added
         */
        // Make sure parts objects are created before any screens are loaded up
        InHouse inHouse1 = new InHouse(1, "Flash drive", 5.99,5,1,10,234224);
        InHouse inHouse2 = new InHouse(2, "Converter", 7.33, 2,1, 10, 234848);
        InHouse inHouse3 = new InHouse(3,"USB wire", 2.99,4,1,16,2342342);
        Outsourced outsourced1 = new Outsourced(4,"Led lights", 0.05, 40, 5, 100, "LinZhou company");
        Outsourced outsourced2 = new Outsourced(5, "Plastic covers", 6.95, 2,1,5,"ShenZhou Ltd.");
        Outsourced outsourced3 = new Outsourced(6, "Bottles", 2.95, 4, 1, 15, "American bottle company");

        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(inHouse3);
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);
        Inventory.addPart(outsourced3);


        launch(args);
    }
}
