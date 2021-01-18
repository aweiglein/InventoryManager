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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

/**
 * Controller for the 'Modify Part' screen
 */
public class ModifyPartController implements Initializable {

    //Parts
    @FXML private TextField modPartIDField;
    @FXML private TextField modPartNameField;
    @FXML private TextField modPartInventoryField;
    @FXML private TextField modPartPriceField;
    @FXML private TextField modPartMaxField;
    @FXML private TextField modPartMinField;
    @FXML private TextField DynamicField;
    @FXML private Label DynamicLabel;

    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private Label errorLabel;
    @FXML private Button SaveButton;
    @FXML private Button CancelButton;

    private Inventory inv;
    private Part selectedPart;

    /**
     * Class constructor
     * @param inv inventory of parts and products
     */
    public ModifyPartController(Inventory inv, Part selectedPart) {
        this.inv = inv;
        this.selectedPart = selectedPart;
    }

    /**
     * Initializes the controller class
     * @param url location used for the root object
     * @param rb resources used for the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillData();
    }

    /**
     * Loads the form text fields with the variables of the selected part
     */
    private void fillData() {
        if (selectedPart instanceof InHouse) {
            InHouse inHousePart = (InHouse) selectedPart;
            inHouseRadio.setSelected(true);
            DynamicLabel.setText("Machine ID");
            this.modPartIDField.setText(Integer.toString(inHousePart.getPartID()));
            this.modPartNameField.setText(inHousePart.getName());
            this.modPartInventoryField.setText(Integer.toString(inHousePart.getStock()));
            this.modPartPriceField.setText(Double.toString(inHousePart.getPrice()));
            this.modPartMinField.setText(Integer.toString(inHousePart.getMin()));
            this.modPartMaxField.setText(Integer.toString(inHousePart.getMax()));
            this.DynamicField.setText(Integer.toString(inHousePart.getMachineID()));
        }
        if (selectedPart instanceof Outsourced) {
            Outsourced outsourcedPart = (Outsourced) selectedPart;
            outsourcedRadio.setSelected(true);
            DynamicLabel.setText("Company Name");
            this.modPartIDField.setText(Integer.toString(outsourcedPart.getPartID()));
            this.modPartNameField.setText(outsourcedPart.getName());
            this.modPartInventoryField.setText(Integer.toString(outsourcedPart.getStock()));
            this.modPartPriceField.setText(Double.toString(outsourcedPart.getPrice()));
            this.modPartMinField.setText(Integer.toString(outsourcedPart.getMin()));
            this.modPartMaxField.setText(Integer.toString(outsourcedPart.getMax()));
            this.DynamicField.setText(outsourcedPart.getCompanyName());
        }
    }

    /**
     * Changes label to machine ID
     * @param event mouse input In-House radio button is selected by user
     */
    @FXML private void selectInHouse(MouseEvent event) {
        DynamicLabel.setText("Machine ID");
    }

    /**
     * Changes label to company name
     * @param event mouse input outsourced radio button is selected by user
     */
    @FXML private void selectOutsourced(MouseEvent event) {
        DynamicLabel.setText("Company Name");
    }

    /**
     * Checks that the user input is valid
     * @param event mouse input when save button is clicked by user
     */
    @FXML private void checkInput(MouseEvent event) {
        if (modPartNameField.getText().trim().isEmpty()) {
            errorLabel.setText("Enter a name");
            return;
        }
        try {
            if (modPartInventoryField.getText().trim().isEmpty() || Integer.parseInt(modPartInventoryField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter an inventory amount");
            return;
        }
        try {
            if (modPartPriceField.getText().trim().isEmpty() || Double.parseDouble(modPartPriceField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a price");
            return;
        }
        try {
            if (modPartMinField.getText().trim().isEmpty() || Integer.parseInt(modPartMinField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a min amount");
            return;
        }
        try {
            if (modPartMaxField.getText().trim().isEmpty() || Integer.parseInt(modPartMaxField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a max amount");
            return;
        }
        if (Integer.parseInt(modPartMinField.getText().trim()) > Integer.parseInt(modPartMaxField.getText().trim())) {
            errorLabel.setText("Min is greater than max");
            return;
        }
        if (Integer.parseInt(modPartInventoryField.getText().trim()) > Integer.parseInt(modPartMaxField.getText().trim())) {
            errorLabel.setText("Inventory is greater than max");
            return;
        }
        if (Integer.parseInt(modPartInventoryField.getText().trim()) < Integer.parseInt(modPartMinField.getText().trim())) {
            errorLabel.setText("Inventory is less than min");
            return;
        }
        try {
            if (inHouseRadio.isSelected() && (DynamicField.getText().trim().isEmpty() || Integer.parseInt(DynamicField.getText().trim()) <= 0))
                throw new Exception();
        }
        catch(Exception e) {
            errorLabel.setText("Enter a Machine ID number");
            return;
        }
        if (outsourcedRadio.isSelected() && DynamicField.getText().trim().isEmpty()) {
            errorLabel.setText("Enter a company name");
            return;
        }
        savePart(event);
    }

    /**
     * Saves part to inventory
     * @param event mouse input when save button is clicked by user
     */
    private void savePart(MouseEvent event) {
        if (inHouseRadio.isSelected()) {
            InHouse part = new InHouse(Integer.parseInt(modPartIDField.getText().trim()), modPartNameField.getText().trim(), Double.parseDouble(modPartPriceField.getText().trim()), Integer.parseInt(modPartInventoryField.getText().trim()), Integer.parseInt(modPartMinField.getText().trim()), Integer.parseInt(modPartMaxField.getText().trim()), Integer.parseInt(DynamicField.getText().trim()));
            inv.updatePart(part);
        }
        if (outsourcedRadio.isSelected()) {
            Outsourced part = new Outsourced(Integer.parseInt(modPartIDField.getText().trim()), modPartNameField.getText().trim(), Double.parseDouble(modPartPriceField.getText().trim()), Integer.parseInt(modPartInventoryField.getText().trim()), Integer.parseInt(modPartMinField.getText().trim()), Integer.parseInt(modPartMaxField.getText().trim()), DynamicField.getText().trim());
            inv.updatePart(part);
        }
        returnToMain(event);
    }

    /**
     * Returns user to main screen
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
