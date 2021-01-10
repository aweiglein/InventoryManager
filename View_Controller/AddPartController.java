package View_Controller;

import Model.Inventory;
import Model.InHouse;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Class controller adds Parts
 */
public class AddPartController implements Initializable {
    Inventory inventory;
    private boolean isOutsourced = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public AddPartController() {
    }

    public AddPartController(Inventory inventory) { this.inventory = inventory; }

    @FXML private TextField addPartNameField;
    @FXML private TextField addPartInvField;
    @FXML private TextField addPartPriceField;
    @FXML private TextField addPartDynamicField;
    @FXML private TextField addPartMaxField;
    @FXML private TextField addPartMinField;
    @FXML private Label addPartCompanyMachineID;
    @FXML private Button addPartSaveButton;
    @FXML private Button addPartCancelButton;

    @FXML void inHouseHandler(ActionEvent event) {
        isOutsourced = false;
        addPartCompanyMachineID.setText("Machine ID");
    }

    @FXML void outsourcedRadioHandler(ActionEvent event) {
        isOutsourced = true;
        addPartCompanyMachineID.setText("Company Name");
    }

    @FXML void addPartSaveHandler(ActionEvent event) throws IOException {

        Random rand = new Random();
        int id = rand.nextInt(30);

        if (!isOutsourced) {

            InHouse newInhousePart = new InHouse(0, "", 0, 0, 0, 0, 0);

            if (isValid(addPartNameField.getText(), addPartInvField.getText(), addPartPriceField.getText(), addPartMaxField.getText(), addPartMinField.getText(), addPartDynamicField.getText())) {
                newInhousePart.setID(id);

                if (!addPartNameField.getText().isEmpty()) {
                    newInhousePart.setName(addPartNameField.getText());
                }
                if (!addPartInvField.getText().isEmpty()) {
                    newInhousePart.setStock(Integer.parseInt(addPartInvField.getText()));
                }
                if (!addPartPriceField.getText().isEmpty()) {
                    newInhousePart.setPrice(Double.parseDouble(addPartPriceField.getText()));
                }
                if (!addPartMaxField.getText().isEmpty()) {
                    newInhousePart.setMax(Integer.parseInt(addPartMaxField.getText()));
                }
                if (!addPartMinField.getText().isEmpty()) {
                    newInhousePart.setMin(Integer.parseInt(addPartMinField.getText()));
                }
                if (!addPartDynamicField.getText().isEmpty()) {
                    newInhousePart.setMachineID(Integer.parseInt(addPartDynamicField.getText()));
                }

                Stage stage;
                Parent root;
                stage=(Stage) addPartSaveButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root =loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } else {

            Outsourced newOutsourcedPart = new Outsourced(0, "", 0, 0, 0, 0, "");

            if (isValid(addPartNameField.getText(), addPartInvField.getText(), addPartPriceField.getText(), addPartMaxField.getText(), addPartMinField.getText(), addPartDynamicField.getText())) {

                newOutsourcedPart.setID(id);
                if (!addPartNameField.getText().isEmpty()) {
                    newOutsourcedPart.setName(addPartNameField.getText());
                }
                if (!addPartPriceField.getText().isEmpty()) {
                    newOutsourcedPart.setPrice(Double.parseDouble(addPartPriceField.getText()));
                }
                if (!addPartInvField.getText().isEmpty()) {
                    newOutsourcedPart.setStock(Integer.parseInt(addPartInvField.getText()));
                }
                if (!addPartMinField.getText().isEmpty()) {
                    newOutsourcedPart.setMin(Integer.parseInt(addPartMinField.getText()));
                }
                if (!addPartMaxField.getText().isEmpty()) {
                    newOutsourcedPart.setMax(Integer.parseInt(addPartMaxField.getText()));
                }
                if (!addPartDynamicField.getText().isEmpty()) {
                    newOutsourcedPart.setCompanyName(addPartDynamicField.getText());
                }

                Stage stage;
                Parent root;
                stage=(Stage) addPartSaveButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root =loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML void addPartCancelHandler(ActionEvent event) throws IOException
    {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene mainScene = new Scene(mainScreenParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    public boolean isValid( String partName, String partStock, String partPrice, String partMax, String partMin, String partOrigin  ) {
        String errorMessage = "";
        Integer intMin = null, intMax = null;
        boolean isValid;

        if (partName == null || partName.isEmpty()) {
            errorMessage += ("Enter a part name\n");
        }

        try {
            intMin =  Integer.parseInt(partMin);
        } catch (NumberFormatException e) {
            errorMessage += ("Min must be a number\n");
        }

        try {
            intMax = Integer.parseInt(partMax);
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
            int intInv = Integer.parseInt(partStock);

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

        try {
            double price = Double.parseDouble(partPrice);
            if (price < 0) {
                errorMessage += ("Price cannot be less than 0.00");
            }
        } catch (NumberFormatException e) {
            errorMessage += ("Enter a price\n");
        }

        if (!isOutsourced) {
            if (!partOrigin.isEmpty()) {
                try {
                    Integer.parseInt(partOrigin);
                } catch (NumberFormatException e) {
                    errorMessage += ("Machine ID must be a number");
                }
            } else {
                errorMessage += ("Enter a Machine ID\n");
            }
        } else {
            if (partOrigin.isEmpty()) {
                errorMessage += ("Enter a Company Name\n");
            }
        }

        if (errorMessage.isEmpty()) {
            isValid = true;
        } else {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ADD PART ERROR");
            alert.setHeaderText("Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }

        return isValid;
    }
}