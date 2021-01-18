/**
 * @author Alyssa Weiglein
 * Inventory Management System
 */

package Main;

import Model.Inventory;
import Model.InHouse;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the 'Main' screen
 */
public class Main extends Application {

    /**
     * Launches Java application
     * @param args command line arguments
     */
    public static void main(String[] args) { launch(args); }

    /**
     * Loads the 'Main' screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Inventory inv = new Inventory();
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Main.fxml"));
        View_Controller.MainController controller = new View_Controller.MainController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Loads the test 'Inventory' data
     */
    public void addTestData(Inventory inv) {
        //In-House parts
        InHouse partA = new InHouse(1, "Part A", 3.99, 10, 5, 100, 101);
        InHouse partB = new InHouse(2, "Part B", 4.99, 20, 5, 100, 102);
        InHouse partC = new InHouse(3, "Part C", 5.99, 30, 5, 100, 103);
        Inventory.addPart(partA);
        Inventory.addPart(partB);
        Inventory.addPart(partC);
        //Outsourced parts
        Outsourced partD = new Outsourced(4, "Part D", 7.99, 20, 5, 100, "Part Co.");
        Outsourced partE = new Outsourced(5, "Part E", 8.99, 30, 5, 100, "Part Co.");
        Outsourced partF = new Outsourced(6, "Part F", 9.99, 10, 5, 100, "Part Co.");
        Inventory.addPart(partD);
        Inventory.addPart(partE);
        Inventory.addPart(partF);
        //Products
        Product productA = new Product(1, "Product A", 3.99, 10, 5, 100);
        Product productB = new Product(2, "Product B", 4.99, 20, 5, 100);
        Product productC = new Product(3, "Product C", 5.99, 30, 5, 100);
        Product productD = new Product(4, "Product D", 3.99, 10, 5, 100);
        Product productE = new Product(5, "Product E", 4.99, 20, 5, 100);
        Product productF = new Product(6, "Product F", 5.99, 30, 5, 100);
        Inventory.addProduct(productA);
        Inventory.addProduct(productB);
        Inventory.addProduct(productC);
        Inventory.addProduct(productD);
        Inventory.addProduct(productE);
        Inventory.addProduct(productF);
    }
}
