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

    DatabaseDAO dao = DatabaseDAO.getInstance();

    public RegisterController() throws SQLException {
    }


    @FXML
    public void initialize() throws IOException {
        femaleBttn.setSelected(true);
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
                passwordLengthLabel.setText("Password must be at least 8 characters length");
            } else {
                passwordFld.getStyleClass().removeAll("IncorrectInput");
                passwordFld.getStyleClass().add("CorrectInput");
                passwordLengthLabel.setText("");
            }
        });

        //usernameField validation
        usernameFld.textProperty().addListener((obs, old, n) -> {
            if (!usernameFld.getText().trim().isEmpty()) {
                usernameFld.getStyleClass().removeAll("IncorrectInput");
                usernameFld.getStyleClass().add("CorrectInput");
            } else{
                usernameFld.getStyleClass().removeAll("CorrectInput");
                usernameFld.getStyleClass().add("IncorrectInput");
            }
            //checking is username already taken
            ArrayList<User> users = dao.returnAllUsers(usernameFld.getText());
            ArrayList<Administrator> admins = dao.returnAllAdmins(usernameFld.getText());
            if(users.size()!=0 || admins.size()!=0){
                usernameFld.getStyleClass().removeAll("CorrectInput");
                usernameFld.getStyleClass().add("IncorrectInput");
                usernameLabelErrorText.setText("Username is already taken");
            }
            else{
                usernameFld.getStyleClass().removeAll("IncorrectInput");
                usernameFld.getStyleClass().add("CorrectInput");
                usernameLabelErrorText.setText("");
            }
        });
    }


    public void registerNowAction(ActionEvent actionEvent) throws IOException {
        ArrayList<User> users = dao.returnAllUsers(usernameFld.getText());
        ArrayList<Administrator> admins = dao.returnAllAdmins(usernameFld.getText());
        if(nameFld.getText().trim().isEmpty() || surnameFld.getText().trim().isEmpty() || usernameFld.getText().trim().isEmpty() ||
           emailFld.getText().trim().isEmpty() || passwordFld.getText().trim().isEmpty() || users.size()!=0 || admins.size()!=0 || passwordFld.getText().length() < 8){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to register");
            alert.setHeaderText("Your informations are not correctly fulfilled or some of field is empty");
            alert.setContentText("Click OK and try again");
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
            alert.setTitle("Successfully registered");
            alert.setHeaderText("You are successfully registered! Please login now");
            alert.setContentText(null);
            Stage stage = (Stage) nameFld.getScene().getWindow();
            stage.close();
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            stage.setTitle("Login");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }
    }

    public void signInLabelClicked(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) registerNowBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }


}
