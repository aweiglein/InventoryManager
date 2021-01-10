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
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Class controller adds Products
 */
public class AddProductController implements Initializable {
    Product newProduct = new Product(0,"",0,0,0,0);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateUnassociatedPartsTable();
        generateAssociatedPartsTable();
    }

    public AddProductController() { }

    @FXML private Button addProductSaveButton;
    @FXML private TextField addProductNameField;
    @FXML private TextField addProductInvField;
    @FXML private TextField addProductPriceField;
    @FXML private TextField addProductMaxField;
    @FXML private TextField addProductMinField;
    @FXML private TableView<Part> addProductAddPartsTable;
    @FXML private TableColumn<Part, Integer> addProductAddPartsTableIDCol;
    @FXML private TableColumn<Part, String> addProductAddPartsTableNameCol;
    @FXML private TableColumn<Part, Integer> addProductAddPartsTableInvCol;
    @FXML private TableColumn<Part, Double> addProductAddPartsTablePriceCol;
    @FXML private TextField addProductSearchField;
    @FXML private TableView<Part> addProductRemovePartsTable;
    @FXML private TableColumn<Part, Integer> addProductRemovePartsTableIDCol;
    @FXML private TableColumn<Part, String> addProductRemovePartsTableNameCol;
    @FXML private TableColumn<Part, Integer> addProductRemovePartsTableInvCol;
    @FXML private TableColumn<Part, Double> addProductRemovePartsTablePriceCol;

    @FXML private void generateAssociatedPartsTable() {
        if (!newProduct.getAllAssociatedParts().isEmpty()) {
            addProductRemovePartsTable.setItems(newProduct.getAllAssociatedParts());
            addProductRemovePartsTableIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            addProductRemovePartsTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            addProductRemovePartsTableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            addProductRemovePartsTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            addProductRemovePartsTable.refresh();
        }
    }

    @FXML private void generateUnassociatedPartsTable() {
        addProductAddPartsTable.setItems(Inventory.getAllParts());
        addProductAddPartsTableIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        addProductAddPartsTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAddPartsTableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAddPartsTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductAddPartsTable.refresh();
    }

    @FXML void addProductAddHandler(ActionEvent event) {
        Part selectedPart = addProductAddPartsTable.getSelectionModel().getSelectedItem();

        newProduct.addAssociatedPart(selectedPart);
        generateAssociatedPartsTable();
        addProductAddPartsTable.refresh();
    }

    @FXML void addProductCancelHandler(ActionEvent event) throws IOException
    {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene mainScene = new Scene(mainScreenParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }


    @FXML void addProductRemoveHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Remove Part");
        alert.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part selectedPart = addProductRemovePartsTable.getSelectionModel().getSelectedItem();
            newProduct.deleteAssociatedPart(selectedPart);
            addProductAddPartsTable.refresh();
        }
    }

    @FXML void addProductSaveHandler(ActionEvent event) throws IOException {
        Random rand = new Random();
        int id = rand.nextInt(30);

        if (inventoryIsValid(addProductInvField.getText(), addProductMinField.getText(), addProductMaxField.getText())) {
            newProduct.setID(id);
            if (!addProductNameField.getText().isEmpty()) {
                newProduct.setName(addProductNameField.getText());
            }
            if (!addProductPriceField.getText().isEmpty()) {
                newProduct.setPrice(Double.parseDouble(addProductPriceField.getText()));
            }
            if (!addProductInvField.getText().isEmpty()) {
                newProduct.setStock(Integer.parseInt(addProductInvField.getText()));
            }
            if (!addProductMinField.getText().isEmpty()) {
                newProduct.setMin(Integer.parseInt(addProductMinField.getText()));
            }
            if (!addProductMaxField.getText().isEmpty()) {
                newProduct.setMax(Integer.parseInt(addProductMaxField.getText()));
            }

            Stage stage;
            Parent root;
            stage=(Stage) addProductSaveButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML void addProductSearchHandler(ActionEvent event) {
        String searchedPart = addProductSearchField.getText();
        ObservableList foundParts = Inventory.lookupPart(searchedPart);
        addProductAddPartsTable.setItems(foundParts);
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
            alert.setTitle("ADD PRODUCT ERROR");
            alert.setHeaderText("Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        return inventoryIsValid;
    }
}