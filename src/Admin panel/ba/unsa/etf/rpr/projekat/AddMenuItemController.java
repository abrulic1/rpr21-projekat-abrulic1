package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddMenuItemController {
    public Label nameMenuAlreadyUsed;
    public TextField nameField;
    public CheckBox veganId;
    public CheckBox vegetarianId;
    public Button cancelBtn;
    public Button okBtn;
    public Spinner priceField;
    DatabaseDAO dao = DatabaseDAO.getInstance();
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());

    public AddMenuItemController() throws SQLException {
    }

    @FXML
    public void initialize(){
        nameMenuAlreadyUsed.setText("");
        //usernameField validation
        nameField.textProperty().addListener((obs, old, n) -> {
            ArrayList<MenuItem> menuss = dao.returnAllMenuItems(nameField.getText());
            if (!nameField.getText().trim().isEmpty() && menuss.size()==0)
                nameMenuAlreadyUsed.setText("");
            else if (nameField.getText().trim().isEmpty() && menuss.size()==0)
                nameMenuAlreadyUsed.setText("");
            else
                nameMenuAlreadyUsed.setText(bundle.getString("taken_username"));
        });

    }


    public void cancelBtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    public void okBtnAction(ActionEvent actionEvent) {
        ArrayList<MenuItem> menuss = dao.returnAllMenuItems(nameField.getText());
        System.out.println(menuss.size());
        String stringPrize = priceField.getValue().toString();
        double prize = Double.parseDouble(stringPrize);
        if (nameField.getText().trim().isEmpty() || !menuss.isEmpty() || priceField.getValue().toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("not_correctly_fulfilled_infos"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
        else{
            dao.addNewMenuItem(nameField.getText(), prize, veganId.isSelected(), vegetarianId.isSelected());
            Stage s = (Stage) cancelBtn.getScene().getWindow();
            s.close();
    }
    }
}
