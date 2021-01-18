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

/**
 * Controller for the 'Add Product' screen
 */
public class AddProductController implements Initializable {

    //Products
    @FXML private TextField addProductIDField;
    @FXML private TextField addProductNameField;
    @FXML private TextField addProductInventoryField;
    @FXML private TextField addProductPriceField;
    @FXML private TextField addProductMaxField;
    @FXML private TextField addProductMinField;
    //Parts
    @FXML private TextField partSearch;
    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    //Associated Parts
    @FXML private Button addAssociatedPartButton;
    @FXML private Button removeAssociatedPartButton;
    @FXML private TableView<Part> associatedPartTable;
    @FXML private TableColumn<Part, Integer> associatedPartIDCol;
    @FXML private TableColumn<Part, String> associatedPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryCol;
    @FXML private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML private Label errorLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Inventory inv;
    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartsInventory = FXCollections.observableArrayList();

    /**
     * Class constructor
     * @param inv inventory of parts and products
     */
    public AddProductController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the controller class
     * @param url location used for the root object
     * @param rb resources used for the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProductIDField.setText(generateProductID());
        generatePartsTable();
    }

    /**
     * Generates the parts table view
     */
    private void generatePartsTable() {
        partsInventory.setAll(inv.getAllParts());
        partTable.setItems(partsInventory);
        partTable.refresh();
    }

    /**
     * Generates unique product ID by adding 1 to the largest Part ID
     * @return product ID
     */
    private String generateProductID() {
        if (!inv.getAllProducts().isEmpty()) {
            int i = 1;
            for (Product p : inv.getAllProducts())
                if (p.getProductID() > i)
                    i = p.getProductID();
            return Integer.toString(i + 1);
        }
        else {
            return "1";
        }
    }


    /**
     * Searches for parts that contain the users text input, displays only the matching results in the parts table
     * @param event keyboard input when text is entered into the search field by user
     */
    @FXML private void searchForPart(KeyEvent event) {
        if (!partSearch.getText().trim().isEmpty()) {
            partsInventorySearch.clear();
            errorLabel.setText("");
            for (Part p : partsInventory) {
                if (p.getName().toLowerCase().contains(partSearch.getText().trim()))
                    partsInventorySearch.add(p);
                else if (Integer.toString(p.getPartID()).contains(partSearch.getText().trim()))
                    partsInventorySearch.add(p);
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
     * Adds associated part to selected product
     * @param event mouse input when add associated part button is clicked by user
     */
    @FXML void addAssociatedPart(MouseEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (partsInventory.isEmpty()) {
            errorLabel.setText("No items available to add");
            return;
        }
        else if (selectedPart == null) {
            errorLabel.setText("Select an item");
            return;
        }

        associatedPartsInventory.add(selectedPart);
        associatedPartTable.setItems(associatedPartsInventory);
        associatedPartTable.refresh();
        partsInventory.remove(selectedPart);
        partTable.setItems(partsInventory);
        partTable.refresh();
        partSearch.setText("");
    }

    /**
     * Removes associated part from selected product
     * @param event mouse input when remove associated part button is clicked by user
     */
    @FXML void removeAssociatedPart(MouseEvent event) {
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        if (associatedPartsInventory.isEmpty()) {
            errorLabel.setText("No items to remove");
            return;
        }
        else if (selectedPart == null) {
            errorLabel.setText("Select an item");
            return;
        }

        partsInventory.add(selectedPart);
        partTable.setItems(partsInventory);
        partTable.refresh();
        associatedPartsInventory.remove(selectedPart);
        associatedPartTable.setItems(associatedPartsInventory);
        associatedPartTable.refresh();
        partSearch.setText("");
    }

    /**
     * Clears error message
     */
    @FXML private void clearError() {
        errorLabel.setText("");
    }

    /**
     * Checks that the user input is valid
     * @param event mouse input when save button is clicked by user
     */
    @FXML private void checkInput(MouseEvent event) {
        if (addProductNameField.getText().trim().isEmpty()) {
            errorLabel.setText("Enter a name");
            return;
        }
        try {
            if (addProductInventoryField.getText().trim().isEmpty() || Integer.parseInt(addProductInventoryField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter an inventory amount");
            return;
        }
        try {
            if (addProductPriceField.getText().trim().isEmpty() || Double.parseDouble(addProductPriceField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a price");
            return;
        }
        try {
            if (addProductMinField.getText().trim().isEmpty() || Integer.parseInt(addProductMinField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a min amount");
            return;
        }
        try {
            if (addProductMaxField.getText().trim().isEmpty() || Integer.parseInt(addProductMaxField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a max amount");
            return;
        }
        if (Integer.parseInt(addProductMinField.getText().trim()) > Integer.parseInt(addProductMaxField.getText().trim())) {
            errorLabel.setText("Min is greater than max");
            return;
        }
        if (Integer.parseInt(addProductInventoryField.getText().trim()) > Integer.parseInt(addProductMaxField.getText().trim())) {
            errorLabel.setText("Inventory is greater than max");
            return;
        }
        if (Integer.parseInt(addProductInventoryField.getText().trim()) < Integer.parseInt(addProductMinField.getText().trim())) {
            errorLabel.setText("Inventory is less than min");
            return;
        }
        saveProduct(event);
    }

    /**
     * Saves product to inventory
     * @param event mouse input when save button is clicked by user
     */
    private void saveProduct(MouseEvent event) {
        Product product = new Product(Integer.parseInt(addProductIDField.getText().trim()), addProductNameField.getText().trim(), Double.parseDouble(addProductPriceField.getText().trim()), Integer.parseInt(addProductInventoryField.getText().trim()), Integer.parseInt(addProductMinField.getText().trim()), Integer.parseInt(addProductMaxField.getText().trim()));
        for (Part p : associatedPartsInventory)
            product.addAssociatedPart(p);
        inv.addProduct(product);

        returnToMain(event);
    }

    /**
     * Returns user to 'Main Screen'
     * @param event mouse input returns user to main screen
     */
    @FXML private void returnToMain(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Main.fxml"));
            MainController controller = new MainController(inv);
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
}
