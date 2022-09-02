package ba.unsa.etf.rpr.projekat;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

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
    public void initialize() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        //passwordField validation
        passwordFld.textProperty().addListener((obs, old, n) -> {
            if (passwordFld.getText().length() < 8)
                passwordLengthLabel.setText(bundle.getString("poor_password"));
            else
                passwordLengthLabel.setText("");
        });
        //usernameField validation
        usernameFld.textProperty().addListener((obs, old, n) -> {
            ArrayList<User> users = dao.returnAllUsers(usernameFld.getText());
            ArrayList<Administrator> admins = dao.returnAllAdmins(usernameFld.getText());
            if (!usernameFld.getText().trim().isEmpty() && users.size()==0 && admins.size()==0)
                usernameLabelErrorText.setText("");
            else if (usernameFld.getText().trim().isEmpty() && users.size()==0 && admins.size()==0)
                usernameLabelErrorText.setText("");
            else
                usernameLabelErrorText.setText(bundle.getString("taken_username"));
        });
    }


    public void cancelBtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) passwordFld.getScene().getWindow();
        stage.close();
    }

    public void okBtnAction(ActionEvent actionEvent) {
        String pol = "Male";
        if(femaleBtn.isSelected()) pol = "Female";
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        ArrayList<User> users = dao.returnAllUsers(usernameFld.getText());
        ArrayList<Administrator> admins = dao.returnAllAdmins(usernameFld.getText());
        if(!users.isEmpty() || !admins.isEmpty() || passwordFld.getText().length() < 8){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("not_correctly_fulfilled_infos"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
        dao.editUser(nameFld.getText(), surnameFld.getText(), emailFld.getText(), usernameFld.getText(), passwordFld.getText(), pol, idEditUser);
        Stage stage = (Stage) femaleBtn.getScene().getWindow();
        stage.close();
    }
}
