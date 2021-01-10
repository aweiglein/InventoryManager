package View_Controller;

import Model.Inventory;
import Model.Product;
import Model.Part;
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
 * Class controls Main Screen
 */
public class MainScreenController implements Initializable {

    @FXML private Button partSearchButton;
    @FXML private TextField partSearchField;
    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partIDcol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private Button productSearchButton;
    @FXML private TextField productSearchField;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productIDcol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInvCol;
    @FXML private TableColumn<Product, Double> productPriceCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generatePartsTable();
        generateProductsTable();
    }

    @FXML private void generatePartsTable() {
        partTable.setItems(Inventory.getAllParts());
        partIDcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.refresh();
    }

    @FXML private void generateProductsTable() {
        productTable.setItems(Inventory.getAllProducts());
        productIDcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.refresh();
    }

    @FXML void partAddHandler(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    @FXML void productAddHandler(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }

    @FXML void partDeleteHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Part partToDelete = partTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(partToDelete);
        }
    }

    @FXML void productDeleteHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Product productToDelete = productTable.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(productToDelete);
        }
    }

    @FXML void exitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Exit Inventory Program");
        alert.setContentText("Are you sure you want to exit this program?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML void partModifyHandler(ActionEvent event) throws IOException {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPart.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        ModifyPartController controller = loader.getController();
        controller.setPart(selectedPart);
        stage.setScene( new Scene(root));
        stage.show();
    }

    @FXML void productModifyHandler(ActionEvent event) throws IOException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
        Parent root = loader.load();

        ModifyProductController controller = loader.getController();
        controller.setProduct(selectedProduct);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML void partSearchHandler(ActionEvent event) {
        String searchedPart = partSearchField.getText();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().equals(searchedPart) || Integer.toString(part.getID()).equals(searchedPart)) {
                partTable.getSelectionModel().select(part);
            }
        }
    }

    @FXML void productSearchHandler(ActionEvent event) {
        String searchedProduct = productSearchField.getText();
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().equals(searchedProduct) || Integer.toString(product.getID()).equals(searchedProduct)) {
                productTable.getSelectionModel().select(product);
            }
        }
    }
}