package View_Controller;

import Model.Inventory;
import Model.InHouse;
import Model.Outsourced;
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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class controller modifies Parts
 */
public class ModifyPartController implements Initializable {
    private boolean isOutsourced = true;
    Part part;
    private int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public ModifyPartController(TextField modPartIDField, TextField modPartNameField, TextField modPartInvField, TextField modPartPriceField, TextField modPartDynamicField, TextField modPartMaxField, TextField modPartMinField, RadioButton inHouseRadio, ToggleGroup partOrigin, RadioButton outsourcedRadio, Button modPartSaveButton, Button modPartCancelButton, Label companyNameMachineID) {
        this.modPartIDField = modPartIDField;
        this.modPartNameField = modPartNameField;
        this.modPartInvField = modPartInvField;
        this.modPartPriceField = modPartPriceField;
        this.modPartDynamicField = modPartDynamicField;
        this.modPartMaxField = modPartMaxField;
        this.modPartMinField = modPartMinField;
        this.inHouseRadio = inHouseRadio;
        this.partOrigin = partOrigin;
        this.outsourcedRadio = outsourcedRadio;
        this.modPartSaveButton = modPartSaveButton;
        this.modPartCancelButton = modPartCancelButton;
        this.companyNameMachineID = companyNameMachineID;
    }

    @FXML private TextField modPartIDField;
    @FXML private TextField modPartNameField;
    @FXML private TextField modPartInvField;
    @FXML private TextField modPartPriceField;
    @FXML private TextField modPartDynamicField;
    @FXML private TextField modPartMaxField;
    @FXML private TextField modPartMinField;
    @FXML private RadioButton inHouseRadio;
    @FXML private ToggleGroup partOrigin;
    @FXML private RadioButton outsourcedRadio;
    @FXML private Button modPartSaveButton;
    @FXML private Button modPartCancelButton;
    @FXML private Label companyNameMachineID;

    @FXML void modPartCancelHandler(ActionEvent event) throws IOException
    {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene mainScene = new Scene(mainScreenParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    @FXML void inHouseHandler(ActionEvent event) {
        isOutsourced = false;
        companyNameMachineID.setText("Machine ID");
    }

    @FXML void outsourcedRadioHandler(ActionEvent event) {
        isOutsourced = true;
        companyNameMachineID.setText("Company Name");
    }

    @FXML public void setPart(Part part) {
        this.part = part;

        modPartIDField.setText(Integer.toString(part.getID()));
        modPartNameField.setText(part.getName());
        modPartInvField.setText(Integer.toString(part.getStock()));
        modPartPriceField.setText(Double.toString(part.getPrice()));
        modPartMaxField.setText(Integer.toString(part.getMax()));
        modPartMinField.setText(Integer.toString(part.getMin()));

        if (part instanceof InHouse) {
            inHouseRadio.fire();
            modPartDynamicField.setText(String.valueOf(((InHouse) part).getMachineID()));
        } else {
            outsourcedRadio.fire();
            modPartDynamicField.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }
    }

    @FXML void modPartSaveHandler(ActionEvent event) throws IOException {

        index = Inventory.getAllParts().indexOf(part);
        InHouse modifiedInhousePart = new InHouse(0, "", 0, 0,0,0,0);

        if (!isOutsourced) {

            if (isValid(modPartNameField.getText(), modPartPriceField.getText(), modPartInvField.getText(), modPartMinField.getText(), modPartMaxField.getText(), modPartDynamicField.getText())) {
                modifiedInhousePart.setID(Integer.parseInt(modPartIDField.getText()));

                if (!modPartNameField.getText().isEmpty()) {
                    modifiedInhousePart.setName(modPartNameField.getText());
                }
                if (!modPartPriceField.getText().isEmpty()) {
                    modifiedInhousePart.setPrice(Double.parseDouble(modPartPriceField.getText()));
                }
                if (!modPartInvField.getText().isEmpty()) {
                    modifiedInhousePart.setStock(Integer.parseInt(modPartInvField.getText()));
                }
                if (!modPartMinField.getText().isEmpty()) {
                    modifiedInhousePart.setMin(Integer.parseInt(modPartMinField.getText()));
                }
                if (!modPartMaxField.getText().isEmpty()) {
                    modifiedInhousePart.setMax(Integer.parseInt(modPartMaxField.getText()));
                }
                if (!modPartDynamicField.getText().isEmpty()) {
                    modifiedInhousePart.setMachineID(Integer.parseInt(modPartDynamicField.getText()));
                }

                Stage stage;
                Parent root;
                stage=(Stage) modPartSaveButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root =loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } else {

            Outsourced modifiedOutsourcedPart = new Outsourced(0, "", 0, 0,0,0,"");

            if (isValid(modPartNameField.getText(), modPartInvField.getText(), modPartPriceField.getText(), modPartMaxField.getText(), modPartMinField.getText(), modPartDynamicField.getText())) {
                modifiedOutsourcedPart.setID(Integer.parseInt(modPartIDField.getText()));

                if (!modPartNameField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setName(modPartNameField.getText());
                }
                if (!modPartPriceField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setPrice(Double.parseDouble(modPartPriceField.getText()));
                }
                if (!modPartInvField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setStock(Integer.parseInt(modPartInvField.getText()));
                }
                if (!modPartMinField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setMin(Integer.parseInt(modPartMinField.getText()));
                }
                if (!modPartMaxField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setMax(Integer.parseInt(modPartMaxField.getText()));
                }
                if (!modPartDynamicField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setCompanyName(modPartDynamicField.getText());
                }


                Stage stage;
                Parent root;
                stage=(Stage) modPartSaveButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root =loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public boolean isValid( String partName, String partStock, String partPrice, String partMax, String partMin, String partOrigin  ) {
        String errorMessage = "";
        Integer intMin = null, intMax = null;
        boolean isValid;

        if (partName == null || partName.isEmpty()) {
            errorMessage += ("Enter a Part Name\n");
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
            alert.setTitle("MODIFY PART ERROR");
            alert.setHeaderText("Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }

        return isValid;
    }
}
