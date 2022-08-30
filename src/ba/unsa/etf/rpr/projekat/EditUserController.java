package ba.unsa.etf.rpr.projekat;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class EditUserController {
    public Label editLbl;
    public Label nameLbl;
    public Label surnameLbl;
    public TextField nameFld;
    public PasswordField passwordFld;
    public Label emailLbl;
    public Label usernameLbl;
    public Label passwordLbl;
    public TextField surnameFld;
    public TextField emailFld;
    public Label usernameLabelErrorText;
    public TextField usernameFld;
    public Label passwordLengthLabel;
    public Button cancelBtn;
    public Button okBtn;
    public RadioButton maleBtn;
    public ToggleGroup polGroup;
    public RadioButton femaleBtn;
    User user = null;
    public int idEditUser;
    DatabaseDAO dao = DatabaseDAO.getInstance();

    public EditUserController(int id) throws SQLException {
        idEditUser=id;
    }

    @FXML
    public void initialize(){
    }


    public void cancelBtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) passwordFld.getScene().getWindow();
        stage.close();
    }

    public void okBtnAction(ActionEvent actionEvent) {
        String pol = "Male";
        if(femaleBtn.isSelected()) pol = "Female";
        dao.editUser(nameFld.getText(), surnameFld.getText(), emailFld.getText(), usernameFld.getText(), passwordFld.getText(), pol, idEditUser);
        Stage stage = (Stage) femaleBtn.getScene().getWindow();
        stage.close();
    }
}
