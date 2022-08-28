package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
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
    public TextField usernameField;
    public PasswordField passwordField;

    DatabaseDAO dao = DatabaseDAO.getInstance();

    public PocetniController() throws SQLException {
    }


    @FXML
    public void initialize(){
        User.setSelected(true);
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
        User usr = dao.getUser(usernameField.getText(), passwordField.getText());
        Administrator admin = dao.getAdministrator(usernameField.getText(), passwordField.getText());

        if(usernameField.getText().trim().isEmpty() || passwordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You have to write your username and password");
            alert.setContentText("Fill in the fields and try again");
            alert.showAndWait();
        }

       else if(usr==null && User.isSelected() || admin==null && Administrator.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username of password!");
            alert.setContentText("Click OK and try again");
            alert.showAndWait();
        }

         if(User.isSelected() && usr!=null){
            Stage stage = (Stage)registerBtn.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/user.fxml"));
            stage.setTitle("Registration");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }
        else if(Administrator.isSelected() && admin!=null){
            Stage stage = (Stage)registerBtn.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/administrator.fxml"));
            stage.setTitle("Registration");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }
    }

}
