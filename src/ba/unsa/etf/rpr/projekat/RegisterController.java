package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RegisterController {
    public TextField nameFld;
    public PasswordField passwordFld;
    public RadioButton femaleBttn;
    public ToggleGroup pol;
    public RadioButton maleBttn;
    public TextField surnameFld;
    public TextField emailFld;
    public TextField usernameFld;
    public Button gbBtn;
    public Button baBtn;
    public Button deBtn;
    public Button registerNowBtn;
    public Label signInLabel;
    public Label usernameLabelErrorText;
    public Label passwordLengthLabel;
    public Label nameLbl;
    public Label registerLbl;
    public Label surnameLbl;
    public Label emailLbl;
    public Label usernameLbl;
    public Label passwordLbl;
    public Label haveAnAccountLbl;

    DatabaseDAO dao = DatabaseDAO.getInstance();

    public RegisterController() throws SQLException {
    }


    @FXML
    public void initialize() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        femaleBttn.setSelected(true);
        if(usernameFld.getText().trim().isEmpty()) usernameLabelErrorText.setText("");
        if(passwordFld.getText().isEmpty()) passwordLengthLabel.setText("");
        //nameField validation
        nameFld.textProperty().addListener((obs, old, n) -> {
            if (!nameFld.getText().trim().isEmpty()) {
                nameFld.getStyleClass().removeAll("IncorrectInput");
                nameFld.getStyleClass().add("CorrectInput");
            } else{
                nameFld.getStyleClass().removeAll("CorrectInput");
                nameFld.getStyleClass().add("IncorrectInput");
            }
        });

        //surnameField validation
        surnameFld.textProperty().addListener((obs, old, n) -> {
            if (!surnameFld.getText().trim().isEmpty()) {
                surnameFld.getStyleClass().removeAll("IncorrectInput");
                surnameFld.getStyleClass().add("CorrectInput");
            } else{
                surnameFld.getStyleClass().removeAll("CorrectInput");
                surnameFld.getStyleClass().add("IncorrectInput");
            }
        });

        //emailField validation
        emailFld.textProperty().addListener((obs, old, n) -> {
            if (!emailFld.getText().trim().isEmpty()) {
                emailFld.getStyleClass().removeAll("IncorrectInput");
                emailFld.getStyleClass().add("CorrectInput");
            } else{
                emailFld.getStyleClass().removeAll("CorrectInput");
                emailFld.getStyleClass().add("IncorrectInput");
            }
        });

        //passwordField validation
        passwordFld.textProperty().addListener((obs, old, n) -> {
            if (!passwordFld.getText().trim().isEmpty()) {
                passwordFld.getStyleClass().removeAll("IncorrectInput");
                passwordFld.getStyleClass().add("CorrectInput");
            } else{
                passwordFld.getStyleClass().removeAll("CorrectInput");
                passwordFld.getStyleClass().add("IncorrectInput");
            }

            if (passwordFld.getText().length() < 8) {
                passwordFld.getStyleClass().removeAll("CorrectInput");
                passwordFld.getStyleClass().add("IncorrectInput");
                passwordLengthLabel.setText(bundle.getString("poor_password"));
            } else {
                passwordFld.getStyleClass().removeAll("IncorrectInput");
                passwordFld.getStyleClass().add("CorrectInput");
                passwordLengthLabel.setText("");
            }
        });

        //usernameField validation
        usernameFld.textProperty().addListener((obs, old, n) -> {
            ArrayList<User> users = dao.returnAllUsers(usernameFld.getText());
            ArrayList<Administrator> admins = dao.returnAllAdmins(usernameFld.getText());
            if (!usernameFld.getText().trim().isEmpty() && users.size()==0 && admins.size()==0) {
                usernameFld.getStyleClass().removeAll("IncorrectInput");
                usernameFld.getStyleClass().add("CorrectInput");
                usernameLabelErrorText.setText("");
            } else if (usernameFld.getText().trim().isEmpty() && users.size()==0 && admins.size()==0){
                usernameFld.getStyleClass().removeAll("CorrectInput");
                usernameFld.getStyleClass().add("IncorrectInput");
                usernameLabelErrorText.setText("");
            }
            else{
                usernameFld.getStyleClass().removeAll("CorrectInput");
                usernameFld.getStyleClass().add("IncorrectInput");
                usernameLabelErrorText.setText(bundle.getString("taken_username"));
            }
        });
    }


    public void registerNowAction(ActionEvent actionEvent) throws IOException {
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
            if(nameFld.getText().trim().isEmpty()) {
                nameFld.getStyleClass().removeAll("CorrectInput");
                nameFld.getStyleClass().add("IncorrectInput");
            }
            if(surnameFld.getText().trim().isEmpty()) {
                surnameFld.getStyleClass().removeAll("CorrectInput");
                surnameFld.getStyleClass().add("IncorrectInput");
            }
            if(passwordFld.getText().trim().isEmpty()) {
                passwordFld.getStyleClass().removeAll("CorrectInput");
                passwordFld.getStyleClass().add("IncorrectInput");
            }
            if(emailFld.getText().trim().isEmpty()) {
                emailFld.getStyleClass().removeAll("CorrectInput");
                emailFld.getStyleClass().add("IncorrectInput");
            }
            if(usernameFld.getText().trim().isEmpty()) {
                usernameFld.getStyleClass().removeAll("CorrectInput");
                usernameFld.getStyleClass().add("IncorrectInput");
            }
        }
        else{
            if(maleBttn.isSelected())
            dao.addNewUser(nameFld.getText(), surnameFld.getText(), emailFld.getText(), usernameFld.getText(), passwordFld.getText(), maleBttn.getText());
            else
                dao.addNewUser(nameFld.getText(), surnameFld.getText(), emailFld.getText(), usernameFld.getText(), passwordFld.getText(), femaleBttn.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("successfully_registered_title"));
            alert.setHeaderText(bundle.getString("successfully_registered"));
            alert.setContentText(null);
            Stage stage = (Stage) nameFld.getScene().getWindow();
            stage.close();
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
            stage.setTitle(bundle.getString("login"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }
    }

    public void signInLabelClicked(MouseEvent mouseEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        Stage stage = (Stage) registerNowBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        stage.setTitle(bundle.getString("login"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }


    public void englishLanguageAction(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        nameFld.requestFocus();
        usernameLabelErrorText.setText("");
        Locale locale = new Locale("en_US");
        Locale.setDefault(new Locale("en", "US"));
        bundle = ResourceBundle.getBundle("Translation_en_US", locale);
        setTranslation(bundle);

    }

    private void setTranslation(ResourceBundle bundle) {
        registerLbl.setText(bundle.getString("register"));
        nameLbl.setText(bundle.getString("name"));
        surnameLbl.setText(bundle.getString("surname"));
        emailLbl.setText(bundle.getString("email"));
        usernameLbl.setText(bundle.getString("username"));
        passwordLbl.setText(bundle.getString("password"));
        femaleBttn.setText(bundle.getString("female"));
        maleBttn.setText(bundle.getString("male"));
        registerNowBtn.setText(bundle.getString("register_now"));
        haveAnAccountLbl.setText(bundle.getString("Already_have_an_account?"));
        signInLabel.setText(bundle.getString("sign_in"));
        if(usernameFld.getText().trim().isEmpty()) usernameLabelErrorText.setText("");
        else usernameLabelErrorText.setText(bundle.getString("taken_username"));
        if(passwordFld.getText().isEmpty()) passwordLengthLabel.setText("");
        else passwordLengthLabel.setText(bundle.getString("poor_password"));
        Stage stage = (Stage) passwordFld.getScene().getWindow();
        stage.setTitle(bundle.getString("register"));
    }

    public void bosnianLanguageAction(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        nameFld.requestFocus();
        Locale locale = new Locale("bs_BA");
        Locale.setDefault(new Locale("bs", "BA"));
        bundle = ResourceBundle.getBundle("Translation_bs_BA", locale);
        setTranslation(bundle);
    }

    public void germanLanguageAction(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        nameFld.requestFocus();
        Locale locale = new Locale("de_DE");
        Locale.setDefault(new Locale("de", "DE"));
        bundle = ResourceBundle.getBundle("Translation_de_DE", locale);
        setTranslation(bundle);
    }
}
