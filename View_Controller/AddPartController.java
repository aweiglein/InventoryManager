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
 * Controller for the 'Add Part' screen
 */
public class AddPartController implements Initializable {

    //Parts
    @FXML private TextField addPartIDField;
    @FXML private TextField addPartNameField;
    @FXML private TextField addPartInventoryField;
    @FXML private TextField addPartPriceField;
    @FXML private TextField addPartMaxField;
    @FXML private TextField addPartMinField;
    @FXML private TextField DynamicField;
    @FXML private Label DynamicLabel;

    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private Label errorLabel;
    @FXML private Button SaveButton;
    @FXML private Button CancelButton;

    private Inventory inv;

    /**
     * Class constructor
     * @param inv inventory of parts and products
     */
    public AddPartController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the controller class
     * @param url location used for the root object
     * @param rb resources used for the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inHouseRadio.setSelected(true);
        addPartIDField.setText(generatePartID());
        DynamicLabel.setText("Machine ID");
    }

    /**
     * Generates unique part ID by adding 1 to the largest part ID
     * @return part ID
     */
    private String generatePartID() {
        if (!inv.getAllParts().isEmpty()) {
            int i = 1;
            for (Part p : inv.getAllParts())
                if (p.getPartID() > i)
                    i = p.getPartID();
            return Integer.toString(i + 1);
        }
        else {
            return "1";
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
        if (addPartNameField.getText().trim().isEmpty()) {
            errorLabel.setText("Enter a name");
            return;
        }
        try {
            if (addPartInventoryField.getText().trim().isEmpty()
                    || Integer.parseInt(addPartInventoryField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter an inventory amount");
            return;
        }
        try {
            if (addPartPriceField.getText().trim().isEmpty()
                    || Double.parseDouble(addPartPriceField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a price");
            return;
        }
        try {
            if (addPartMinField.getText().trim().isEmpty()
                    || Integer.parseInt(addPartMinField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a min amount");
            return;
        }
        try {
            if (addPartMaxField.getText().trim().isEmpty()
                    || Integer.parseInt(addPartMaxField.getText().trim()) <= 0)
                throw new Exception();
        } catch (Exception e) {
            errorLabel.setText("Enter a max amount");
            return;
        }
        if (Integer.parseInt(addPartMinField.getText().trim()) > Integer.parseInt(addPartMaxField.getText().trim())) {
            errorLabel.setText("Min is greater than max");
            return;
        }
        if (Integer.parseInt(addPartInventoryField.getText().trim()) > Integer.parseInt(addPartMaxField.getText().trim())) {
            errorLabel.setText("Inventory is greater than max");
            return;
        }
        if (Integer.parseInt(addPartInventoryField.getText().trim()) < Integer.parseInt(addPartMinField.getText().trim())) {
            errorLabel.setText("Inventory is less than min");
            return;
        }
        try {
            if (inHouseRadio.isSelected() && (DynamicField.getText().trim().isEmpty()
                    || Integer.parseInt(DynamicField.getText().trim()) <= 0))
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
            InHouse part = new InHouse(Integer.parseInt(addPartIDField.getText().trim()), addPartNameField.getText().trim(), Double.parseDouble(addPartPriceField.getText().trim()), Integer.parseInt(addPartInventoryField.getText().trim()), Integer.parseInt(addPartMinField.getText().trim()), Integer.parseInt(addPartMaxField.getText().trim()), Integer.parseInt(DynamicField.getText().trim()));
            inv.addPart(part);
        }
        if (outsourcedRadio.isSelected()) {
            Outsourced part = new Outsourced(Integer.parseInt(addPartIDField.getText().trim()), addPartNameField.getText().trim(), Double.parseDouble(addPartPriceField.getText().trim()), Integer.parseInt(addPartInventoryField.getText().trim()), Integer.parseInt(addPartMinField.getText().trim()), Integer.parseInt(addPartMaxField.getText().trim()), DynamicField.getText().trim());
            inv.addPart(part);
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
