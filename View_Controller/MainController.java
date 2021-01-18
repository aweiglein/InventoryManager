package View_Controller;

import Model.*;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ResourceBundle;
import java.util.Optional;
import javafx.application.Platform;

/**
 * Runtime Error & fix: In my 'Attempt 1', I was getting a runtime error when the user clicked the modify product button
 * and the modify screen wouldn't launch. I found that I needed to add the if else conditional statement to get around the null
 * pointer when an item wasn't originally selected.
 *
 * Controller for the 'Main' screen
 */
public class MainController implements Initializable {
    //Parts
    @FXML private TextField partSearch;
    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private Button addPartButton;
    @FXML private Button modPartButton;
    @FXML private Button deletePartButton;
    //Products
    @FXML private TextField productSearch;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInventoryCol;
    @FXML private TableColumn<Product, Double> productPriceCol;
    @FXML private Button addProductButton;
    @FXML private Button modProductButton;
    @FXML private Button deleteProductButton;

    @FXML private Label errorLabel;
    @FXML private Button exitButton;

    private Inventory inv;
    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productsInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productsInventorySearch = FXCollections.observableArrayList();

    /**
     * Class constructor
     * @param inv the inventory of 'Parts' and 'Products'
     */
    public MainController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the controller class
     * @param url location used for the root object
     * @param rb resources used for the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartsTable();
        generateProductsTable();
    }

    /**
     * Generates parts table, loads inventory data
     */
    private void generatePartsTable() {
        partsInventory.setAll(inv.getAllParts());
        partTable.setItems(partsInventory);
        partTable.refresh();
    }

    /**
     * Generates products table, loads inventory data
     */
    private void generateProductsTable() {
        productsInventory.setAll(inv.getAllProducts());
        productTable.setItems(productsInventory);
        productTable.refresh();
    }

    /**
     * Searches for parts that contain the users text input, displays only the matching results in the parts table
     * @param event keyboard input when text is entered into the search field by user
     */
    @FXML private void searchForPart(KeyEvent event) {
        String search = partSearch.getText().trim().toLowerCase();

        if (!search.isEmpty()) {
            partsInventorySearch.clear();
            errorLabel.setText("");

            for (Part p : partsInventory) {
                if (p.getName().toLowerCase().contains(search) || Integer.toString(p.getPartID()).contains(search)) {
                    partsInventorySearch.add(p);
                }
            }
            if (partsInventorySearch.isEmpty())
                errorLabel.setText("No parts found");
            partTable.setItems(partsInventorySearch);
        }
        else {
            errorLabel.setText("");
            partTable.setItems(partsInventory);
        }
        partTable.refresh();
    }

    /**
     * Searches for products that contain the users text input, displays only the matching results in the products table
     * @param event keyboard input when text is entered into the search field by user
     */
    @FXML private void searchForProduct(KeyEvent event) {
        String search = productSearch.getText().trim().toLowerCase();

        if (!search.isEmpty()) {
            productsInventorySearch.clear();
            errorLabel.setText("");

            for (Product p : productsInventory) {
                if (p.getName().toLowerCase().contains(search) || Integer.toString(p.getProductID()).contains(search)) {
                    productsInventorySearch.add(p);
                }
            }
            if (productsInventorySearch.isEmpty())
                errorLabel.setText("No parts found");
            productTable.setItems(productsInventorySearch);
        }
        else {
            errorLabel.setText("");
            productTable.setItems(productsInventory);
        }
        productTable.refresh();
    }

    /**
     * Launches 'Add Part' screen
     * @param event mouse input when add button (parts) is clicked by user
     */
    @FXML private void addPart(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddPart.fxml"));
            AddPartController controller = new AddPartController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Launches 'Add Product' screen
     * @param event mouse input when add button (products) is clicked by user
     */
    @FXML private void addProduct(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddProduct.fxml"));
            AddProductController controller = new AddProductController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Launches 'Modify Part' screen
     * @param event mouse input when modify button (parts) is clicked by user
     */
    @FXML private void modifyPart(MouseEvent event) {
        try {
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            if (partsInventory.isEmpty()) {
                errorLabel.setText("No items in inventory");
                return;
            }
            else if (selectedPart == null) {
                errorLabel.setText("Select an item");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPart.fxml"));
            ModifyPartController controller = new ModifyPartController(inv, selectedPart);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Launches 'Modify Product' screen
     * @param event mouse input when modify button (products) is clicked by user
     */
    @FXML private void modifyProduct(MouseEvent event) {
        try {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (productsInventory.isEmpty()) {
                errorLabel.setText("No items in inventory");
                return;
            }
            else if (selectedProduct == null) {
                errorLabel.setText("Select an item");
                return;
            }
            else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
                ModifyProductController controller = new ModifyProductController(inv, selectedProduct);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes part from inventory
     * @param event mouse input when delete is clicked by user
     */
    @FXML private void deletePart(MouseEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (partsInventory.isEmpty()) {
            errorLabel.setText("No items in inventory");
            return;
        }
        else if (selectedPart == null) {
            errorLabel.setText("Select an item");
            return;
        }
        boolean confirm = confirmationWindow(selectedPart.getName());
        if (!confirm)
            return;

        inv.deletePart(selectedPart);
        partsInventory.remove(selectedPart);
        partTable.setItems(partsInventory);
        partTable.refresh();
    }

    /**
     * Deletes product from inventory
     * @param event mouse input when delete is clicked by user
     */
    @FXML private void deleteProduct(MouseEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (productsInventory.isEmpty()) {
            errorLabel.setText("No items in inventory");
            return;
        }
        else if (selectedProduct == null) {
            errorLabel.setText("Select an item");
            return;
        }
        boolean confirm = confirmationWindow(selectedProduct.getName());
        if (!confirm)
            return;

        inv.deleteProduct(selectedProduct);
        productsInventory.remove(selectedProduct);
        productTable.setItems(productsInventory);
        productTable.refresh();
    }

    /**
     * Clears error message
     */
    @FXML private void clearError() {
        errorLabel.setText("");
    }

    /**
     * Confirmation alert is displayed before deleting an item
     * @param name the part or product to be deleted
     * @return boolean value
     */
    private boolean confirmationWindow(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setContentText("Are you sure you want to delete " + name +"?");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Exits the program
     * @param event mouse input when Exit button is clicked by user
     */
    @FXML void exitProgram(MouseEvent event) {
        Platform.exit();
    }
}
