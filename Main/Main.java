package Main;

import Model.Inventory;
import Model.InHouse;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @author Alyssa Weiglein
 * For a future update, I would update the program to require a check if parts are being used in products before being allowed to delete them.
 */

/**
 * Main class starts application
 */
public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void init() {
        System.out.println("Opening Inventory Management System");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Inventory inventory = new Inventory();
        addTestData(inventory);

        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Method creates test data
     */
    public void addTestData(Inventory inventory) {
        //In-house parts
        InHouse partA = new InHouse(1, "Part A", 3.99, 10, 5, 100, 101);
        InHouse partB = new InHouse(2, "Part B", 4.99, 20, 5, 100, 102);
        InHouse partC = new InHouse(3, "Part C", 5.99, 30, 5, 100, 103);
        Inventory.addPart(partA);
        Inventory.addPart(partB);
        Inventory.addPart(partC);
        //Outsourced parts
        Outsourced partD = new Outsourced(4, "Part D", 6.99, 10, 5, 100, "Part Co.");
        Outsourced partE = new Outsourced(5, "Part E", 7.99, 20, 5, 100, "Part Co.");
        Outsourced partF = new Outsourced(6, "Part F", 8.99, 30, 5, 100, "Part Co.");
        Outsourced partG = new Outsourced(7, "Part G", 9.99, 10, 5, 100, "Part Co.");
        Outsourced partH = new Outsourced(8, "Part H", 10.99, 20, 5, 100, "Part Co.");
        Inventory.addPart(partD);
        Inventory.addPart(partE);
        Inventory.addPart(partF);
        Inventory.addPart(partG);
        Inventory.addPart(partH);
        //Products
        Product productA = new Product(1, "Product A", 3.99, 10, 5, 100);
        Product productB = new Product(2, "Product B", 4.99, 20, 5, 100);
        Product productC = new Product(3, "Product C", 5.99, 30, 5, 100);
        Inventory.addProduct(productA);
        Inventory.addProduct(productB);
        Inventory.addProduct(productC);
    }
}
