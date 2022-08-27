package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PocetniController {

    public Button registerBtn;
    public Button signinBtn;
    public ButtonBar languageBtn;
    public Button gbBtn;
    public Button baBtn;
    public Button deBtn;
    public ToggleGroup choiceRole;
    public RadioButton User;
    public RadioButton Administrator;


    @FXML
    public void initialize(){
        User.setSelected(true);
       // User.requestFocus();
    }

    public void registrationAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)registerBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        stage.setTitle("Registration");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();



    }

    public void RoleSignInAction(ActionEvent actionEvent) throws IOException {
        if(!choiceRole.getSelectedToggle().isSelected()) return;
        else if(User.isSelected()){
            Stage stage = (Stage)registerBtn.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/user.fxml"));
            stage.setTitle("Registration");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }
        else{
            Stage stage = (Stage)registerBtn.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/administrator.fxml"));
            stage.setTitle("Registration");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }

    }
}
