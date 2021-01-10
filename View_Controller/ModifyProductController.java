package View_Controller;

import Model.Inventory;
import Model.Product;
import Model.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class controller modifies Products
 */
public class ModifyProductController implements Initializable {

    Product product;
    private int index;

    @Override public void initialize(URL location, ResourceBundle resources) {
        generateUnassociatedPartsTable();
    }

    public ModifyProductController() {
    }

    @FXML private TextField modProductIDField;
    @FXML private TextField modProductNameField;
    @FXML private TextField modProductInvField;
    @FXML private TextField modProductPriceField;
    @FXML private TextField modProductMaxField;
    @FXML private TextField modProductMinField;
    @FXML private Button modProductSaveButton;
    @FXML private Button modProductCancelButton;
    @FXML private TableView<Part> addPartsTable;
    @FXML private TableColumn<Part, Integer> modProductAddPartsTableIDCol;
    @FXML private TableColumn<Part, String> modProductAddPartsTableNameCol;
    @FXML private TableColumn<Part, Integer> modProductAddPartsTableInvCol;
    @FXML private TableColumn<Part, Double> modProductAddPartsTablePriceCol;
    @FXML private Button modProductSearchButton;
    @FXML private TextField modProductSearchField;
    @FXML private Button modProductAddButton;
    @FXML private TableView<Part> deletePartFromProductTable;
    @FXML private TableColumn<Part, Integer> modProductRemovePartsTableIDCol;
    @FXML private TableColumn<Part, String> modProductRemovePartsTableNameCol;
    @FXML private TableColumn<Part, Integer> modProductRemovePartsTableInvCol;
    @FXML private TableColumn<Part, Double> modProductRemovePartsTablePriceCol;
    @FXML private Button modProductRemoveButton;

    private void generateAssociatedPartsTable() {
        if (!product.getAllAssociatedParts().isEmpty()) {
            deletePartFromProductTable.setItems(product.getAllAssociatedParts());
            modProductRemovePartsTableIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            modProductRemovePartsTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            modProductRemovePartsTableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            modProductRemovePartsTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            deletePartFromProductTable.refresh();
        }
    }

    private void generateUnassociatedPartsTable() {
        addPartsTable.setItems(Inventory.getAllParts());
        modProductAddPartsTableIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        modProductAddPartsTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProductAddPartsTableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProductAddPartsTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartsTable.refresh();
    }

    @FXML void modProductAddHandler(ActionEvent event) {
        Part selectedPart = addPartsTable.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(selectedPart);
        generateAssociatedPartsTable();
    }

    @FXML void modProductCancelHandler(ActionEvent event) throws IOException {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene mainScene = new Scene(mainScreenParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    @FXML void modProductRemoveHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Remove Part");
        alert.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part partToDelete = deletePartFromProductTable.getSelectionModel().getSelectedItem();
            product.deleteAssociatedPart(partToDelete);
            generateAssociatedPartsTable();
        }
    }

    public void setProduct(Product product) {
        this.product = product;

        modProductIDField.setText(Integer.toString(product.getID()));
        modProductNameField.setText(product.getName());
        modProductInvField.setText(Integer.toString(product.getStock()));
        modProductPriceField.setText(Double.toString(product.getPrice()));
        modProductMaxField.setText(Integer.toString(product.getMax()));
        modProductMinField.setText(Integer.toString(product.getMax()));

        product.getAllAssociatedParts();
        generateAssociatedPartsTable();
    }

    @FXML void modProductSaveHandler(ActionEvent event) throws IOException {

        index = Inventory.getAllProducts().indexOf(product);

        Product modifiedProduct = product;

        if (inventoryIsValid(modProductInvField.getText(), modProductMinField.getText(), modProductMaxField.getText())) {
            modifiedProduct.setID(Integer.parseInt(modProductIDField.getText()));
            if (!modProductNameField.getText().isEmpty()) {
                modifiedProduct.setName(modProductNameField.getText());
            }
            if (!modProductPriceField.getText().isEmpty()) {
                modifiedProduct.setPrice(Double.parseDouble(modProductPriceField.getText()));
            }
            if (!modProductInvField.getText().isEmpty()) {
                modifiedProduct.setStock(Integer.parseInt(modProductInvField.getText()));
            }
            if (!modProductMinField.getText().isEmpty()) {
                modifiedProduct.setMin(Integer.parseInt(modProductMinField.getText()));
            }
            if (!modProductMaxField.getText().isEmpty()) {
                modifiedProduct.setMax(Integer.parseInt(modProductMaxField.getText()));
            }

            modifiedProduct.getAllAssociatedParts();
            Inventory.updateProduct(index, modifiedProduct);

            Stage stage;
            Parent root;
            stage=(Stage) modProductSaveButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML void modProductSearchHandler(ActionEvent event) {
        String searchedPart = modProductSearchField.getText();
        ObservableList foundParts = Inventory.lookupPart(searchedPart);
        addPartsTable.setItems(foundParts);
    }

    public boolean inventoryIsValid(String productStock, String productMin, String productMax) {
        String errorMessage = "";
        Integer intMin = null, intMax = null;
        boolean inventoryIsValid;

        try {
            intMin =  Integer.parseInt(productMin);
        } catch (NumberFormatException e) {
            errorMessage += ("Min must be a number\n");
        }

        try {
            intMax = Integer.parseInt(productMax);
        } catch (NumberFormatException e) {
            errorMessage += ("Max must be a number\n");
        }

        try {
            if(intMin > intMax) {
                errorMessage += ("Min must be less than max\n");
            }
        } catch (NullPointerException e) {
            errorMessage += ("Enter a min and max\n");
        }

        try {
            int intInv = Integer.parseInt(productStock);

            if (intMax != null && intMin != null) {
                if(intInv < intMin || intInv > intMax) {
                    errorMessage += ("Inv must be between min and max\n");
                }
            } else {
                errorMessage += ("Enter an Inv amount\n");
            }
        } catch (NumberFormatException e) {
            errorMessage += ("Inv must be a number\n");
        }

        if (errorMessage.isEmpty()) {
            inventoryIsValid = true;
        } else {
            inventoryIsValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("MODIFY PRODUCT ERROR");
            alert.setHeaderText("Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        return inventoryIsValid;
    }
}


