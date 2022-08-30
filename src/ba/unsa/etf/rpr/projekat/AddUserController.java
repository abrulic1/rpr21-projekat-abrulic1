package ba.unsa.etf.rpr.projekat;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddUserController {

    public TextField nameFld;
    public TextField surnameFld;
    public TextField emailFld;
    public TextField usernameFld;
    public PasswordField passwordFld;
    public RadioButton femaleBtn;
    public ToggleGroup polGroup;
    public RadioButton maleBtn;
    public Button cancelBtn;
    public Button okBtn;
    public Label usernameLabelErrorText;
    public Label passwordLengthLabel;
    DatabaseDAO dao = DatabaseDAO.getInstance();
    public User user=null;

    public AddUserController() throws SQLException {
    }


    @FXML
    public void initialize() {
        femaleBtn.setSelected(true);
        femaleBtn.requestFocus();
        Platform.runLater(() -> cancelBtn.requestFocus());
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
        Stage stage = (Stage) usernameFld.getScene().getWindow();
        stage.close();
    }


    public void okBtnAction(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        ArrayList<User> users = dao.returnAllUsers(usernameFld.getText());
        ArrayList<Administrator> admins = dao.returnAllAdmins(usernameFld.getText());
        if(nameFld.getText().trim().isEmpty() || surnameFld.getText().trim().isEmpty() || usernameFld.getText().trim().isEmpty() ||
                emailFld.getText().trim().isEmpty() || passwordFld.getText().trim().isEmpty() || users.size()!=0 || admins.size()!=0 || passwordFld.getText().length() < 8){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("not_correctly_fulfilled_infos"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
        else{
            if(maleBtn.isSelected())
            user = dao.addNewUser(nameFld.getText(), surnameFld.getText(), emailFld.getText(), usernameFld.getText(), passwordFld.getText(), maleBtn.getText());
            else
                user = dao.addNewUser(nameFld.getText(), surnameFld.getText(), emailFld.getText(), usernameFld.getText(), passwordFld.getText(), femaleBtn.getText());

            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            Stage s = (Stage) cancelBtn.getScene().getWindow();
            s.close();
        }
    }

public User getUser() {
    return user;
}

}