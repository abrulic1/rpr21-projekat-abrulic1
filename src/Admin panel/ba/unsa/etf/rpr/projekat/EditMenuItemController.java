package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class EditMenuItemController {


    public TextField nameField;
    public CheckBox veganId;
    public CheckBox vegetarianId;
    public Button cancelBtn;
    public Button okBtn;
    public Label nameMenuAlreadyUsed;
    public Spinner priceField;
    DatabaseDAO dao = DatabaseDAO.getInstance();
    private int idEdit;

    public EditMenuItemController(int id) throws SQLException {
        idEdit=id;
    }

    public void cancelBtnAction(ActionEvent actionEvent) {
        Stage s = (Stage) nameField.getScene().getWindow();
        s.close();
    }

    public void okBtnAction(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        ArrayList<MenuItem> r = null;

        r = dao.returnAllMenuItems(nameField.getText());
        if(!r.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("not_correctly_fulfilled_infos"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
        else{
            dao.editMenuitem(nameField.getText(), priceField.getValue().toString(),veganId.isSelected(), vegetarianId.isSelected(), idEdit);
            Stage s = (Stage) cancelBtn.getScene().getWindow();
            s.close();
        }
    }
}
