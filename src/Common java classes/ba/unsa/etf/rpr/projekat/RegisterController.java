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
    public PasswordField submitPasswordField;
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
    public Label submitPasswordLabel;
    public Label passwordLengthLabel2;

    DatabaseDAO dao = DatabaseDAO.getInstance();
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());

    public RegisterController() throws SQLException {
    }

    private boolean nameFieldValidation(){
        final boolean[] valid = {true};
        nameFld.textProperty().addListener((obs, old, n) -> {
            if (!nameFld.getText().trim().isEmpty()) {
                nameFld.getStyleClass().removeAll("IncorrectInput");
                nameFld.getStyleClass().add("CorrectInput");
            } else{
                nameFld.getStyleClass().removeAll("CorrectInput");
                nameFld.getStyleClass().add("IncorrectInput");
                valid[0] = false;
            }
        });
        return valid[0];
    }

    private boolean surnameFieldValidation(){
        final boolean[] valid = {true};
        surnameFld.textProperty().addListener((obs, old, n) -> {
            if (!surnameFld.getText().trim().isEmpty()) {
                surnameFld.getStyleClass().removeAll("IncorrectInput");
                surnameFld.getStyleClass().add("CorrectInput");
            } else{
                surnameFld.getStyleClass().removeAll("CorrectInput");
                surnameFld.getStyleClass().add("IncorrectInput");
                valid[0] =false;
            }
        });
        return valid[0];
    }

    private boolean emailFieldValidation(){
        final boolean[] valid = {true};
        emailFld.textProperty().addListener((obs, old, n) -> {
            if (!emailFld.getText().trim().isEmpty()) {
                emailFld.getStyleClass().removeAll("IncorrectInput");
                emailFld.getStyleClass().add("CorrectInput");
            } else{
                emailFld.getStyleClass().removeAll("CorrectInput");
                emailFld.getStyleClass().add("IncorrectInput");
                valid[0] =false;
            }
        });
        return valid[0];
    }

    private boolean passwordFieldValidation(){
        usernameLabelErrorText.setText("");
        final boolean[] valid = {true};
        passwordFld.textProperty().addListener((obs, old, n) -> {
            if (!passwordFld.getText().trim().isEmpty() && passwordFld.getText().length() >= 8) {
                passwordFld.getStyleClass().removeAll("IncorrectInput");
                passwordFld.getStyleClass().add("CorrectInput");
                passwordLengthLabel.setText("");
            } else if(passwordFld.getText().length() < 8 && !passwordFld.getText().isEmpty()){
                passwordFld.getStyleClass().removeAll("CorrectInput");
                passwordFld.getStyleClass().add("IncorrectInput");
                passwordLengthLabel.setText(bundle.getString("poor_password"));
                valid[0] =false;
            } else if (passwordFld.getText().trim().isEmpty()) {
                passwordFld.getStyleClass().removeAll("CorrectInput");
                passwordFld.getStyleClass().add("IncorrectInput");
                passwordLengthLabel.setText("");
                valid[0] =false;
            }
        });
        return valid[0];
    }

    private boolean submitPasswordValidation(){
        boolean[] valid ={true};
        submitPasswordField.textProperty().addListener((obs, old, n) -> {
            if (!submitPasswordField.getText().trim().isEmpty() && submitPasswordField.getText().equals(passwordFld.getText())) {
                submitPasswordField.getStyleClass().removeAll("IncorrectInput");
                submitPasswordField.getStyleClass().add("CorrectInput");
                passwordLengthLabel2.setText("");
                valid[0]=true;
            } else if(!submitPasswordField.getText().equals(passwordFld.getText())){
                submitPasswordField.getStyleClass().removeAll("CorrectInput");
                submitPasswordField.getStyleClass().add("IncorrectInput");
                passwordLengthLabel2.setText(bundle.getString("incorrect_password"));
                valid[0] =false;
            } else if(submitPasswordField.getText().isEmpty()){
                submitPasswordField.getStyleClass().removeAll("CorrectInput");
                submitPasswordField.getStyleClass().add("IncorrectInput");
                passwordLengthLabel2.setText("");
                valid[0] =false;
            }
        });
        return valid[0];
    }

    private boolean usernameFieldValidation(){
        boolean[] valid ={true};
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
                valid[0]=false;
            }
            else{
                usernameFld.getStyleClass().removeAll("CorrectInput");
                usernameFld.getStyleClass().add("IncorrectInput");
                usernameLabelErrorText.setText(bundle.getString("taken_username"));
                valid[0]=false;
            }
        });
        return valid[0];
    }

    @FXML
    public void initialize() throws IOException {
        femaleBttn.setSelected(true);
        nameFieldValidation();
        surnameFieldValidation();
        emailFieldValidation();
        passwordFieldValidation();
        usernameFieldValidation();
        submitPasswordValidation();
    }


    public void registerNowAction(ActionEvent actionEvent) throws IOException {
        ArrayList<User> users = dao.returnAllUsers(usernameFld.getText());
        ArrayList<Administrator> admins = dao.returnAllAdmins(usernameFld.getText());
        if(!nameFieldValidation() || !surnameFieldValidation() || !emailFieldValidation() || !passwordFieldValidation()
             || !submitPasswordValidation() || !passwordFld.getText().equals(submitPasswordField.getText()) || !users.isEmpty() || !admins.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("not_correctly_fulfilled_infos"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
            nameFieldValidation();
            surnameFieldValidation();
            emailFieldValidation();
            passwordFieldValidation();
            usernameFieldValidation();
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
            stage.setResizable(false);
            stage.show();
        }
    }

    public void signInLabelClicked(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) registerNowBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        stage.setTitle(bundle.getString("login"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }


    public void englishLanguageAction(ActionEvent actionEvent) {
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
        submitPasswordLabel.setText(bundle.getString("submit_password"));
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
        nameFld.requestFocus();
        Locale locale = new Locale("bs_BA");
        Locale.setDefault(new Locale("bs", "BA"));
        bundle = ResourceBundle.getBundle("Translation_bs_BA", locale);
        setTranslation(bundle);
    }

    public void germanLanguageAction(ActionEvent actionEvent) {
        nameFld.requestFocus();
        Locale locale = new Locale("de_DE");
        Locale.setDefault(new Locale("de", "DE"));
        bundle = ResourceBundle.getBundle("Translation_de_DE", locale);
        setTranslation(bundle);
    }
}
