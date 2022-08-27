package ba.unsa.etf.rpr.projekat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RegisterController {
    public Button okBtn;
    public TextField nameFld;
    public PasswordField passwordFld;
    public RadioButton femaleBttn;
    public ToggleGroup pol;
    public RadioButton maleBttn;
    public Button cancelBtn;
    public TextField surnameFld;
    public TextField emailFld;
    public TextField nicknameFld;
    public DatePicker dobFld;
    public Button gbBtn;
    public Button baBtn;
    public Button deBtn;

    @FXML
    public void initialize(){
        femaleBttn.setSelected(true);
        femaleBttn.requestFocus();
        nameFld.textProperty().addListener((obs, old, n) -> {
            if (!nameFld.getText().trim().isEmpty()) {
                nameFld.getStyleClass().removeAll("IncorrectInput");
                nameFld.getStyleClass().add("CorrectInput");
            } else{
                nameFld.getStyleClass().removeAll("CorrectInput");
                nameFld.getStyleClass().add("IncorrectInput");
            }
        });

        surnameFld.textProperty().addListener((obs, old, n) -> {
            if (!surnameFld.getText().trim().isEmpty()) {
                surnameFld.getStyleClass().removeAll("IncorrectInput");
                surnameFld.getStyleClass().add("CorrectInput");
            } else{
                surnameFld.getStyleClass().removeAll("CorrectInput");
                surnameFld.getStyleClass().add("IncorrectInput");
            }
        });

        emailFld.textProperty().addListener((obs, old, n) -> {
            if (!emailFld.getText().trim().isEmpty()) {
                emailFld.getStyleClass().removeAll("IncorrectInput");
                emailFld.getStyleClass().add("CorrectInput");
            } else{
                emailFld.getStyleClass().removeAll("CorrectInput");
                emailFld.getStyleClass().add("IncorrectInput");
            }
        });

        passwordFld.textProperty().addListener((obs, old, n) -> {
            if (!passwordFld.getText().trim().isEmpty()) {
                passwordFld.getStyleClass().removeAll("IncorrectInput");
                passwordFld.getStyleClass().add("CorrectInput");
            } else{
                passwordFld.getStyleClass().removeAll("CorrectInput");
                passwordFld.getStyleClass().add("IncorrectInput");
            }
        });

        nicknameFld.textProperty().addListener((obs, old, n) -> {
            if (!nicknameFld.getText().trim().isEmpty()) {
                nicknameFld.getStyleClass().removeAll("IncorrectInput");
                nicknameFld.getStyleClass().add("CorrectInput");
            } else{
                nicknameFld.getStyleClass().removeAll("CorrectInput");
                nicknameFld.getStyleClass().add("IncorrectInput");
            }
        });




    }

    public void okBtnAction(ActionEvent actionEvent) throws IOException {
        if(nameFld.getText().trim().isEmpty() || surnameFld.getText().trim().isEmpty() || nicknameFld.getText().trim().isEmpty() ||
           emailFld.getText().trim().isEmpty() || passwordFld.getText().trim().isEmpty()){
            //fali i za dob
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to register");
            alert.setHeaderText("Your informations are not correctly fulfilled or some of field is empty");
            alert.setContentText("Click OK and try again");
            alert.showAndWait();
        }
        else{
            //dodati u bazu ove podatke
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfully registered");
            alert.setHeaderText("You are successfully registered! Please login now");
            alert.setContentText(null);
            Stage stage = (Stage) nameFld.getScene().getWindow();
            stage.close();
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/pocetni.fxml"));
            stage.setTitle("Login");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
    }
}
