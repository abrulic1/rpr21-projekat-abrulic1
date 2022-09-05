package ba.unsa.etf.rpr.projekat;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {
    public Button registerBtn;
    public Button signinBtn;
    public Button gbBtn;
    public Button baBtn;
    public Button deBtn;
    public ToggleGroup choiceRole;
    public RadioButton User;
    public RadioButton Administrator;
    public TextField usernameField;
    public PasswordField passwordField;
    public Label loginLbl;
    public Label usernameLbl;
    public Label passwordLbl;
    public Label notHaveAccountLbl;
    public TextField passwordTextField;
    public ImageView imageview;

    DatabaseDAO dao = DatabaseDAO.getInstance();
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
    private boolean showPassword=false;

    public LoginController() throws SQLException {
    }


    @FXML
    public void initialize(){
        User.setSelected(true);
        passwordTextField.setEditable(false);
        passwordTextField.setVisible(false);
        Platform.runLater(()->usernameField.requestFocus());
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
    }



    public void registrationAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)registerBtn.getScene().getWindow();
        stage.close();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"), bundle);
        stage.setTitle(bundle.getString("register"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }


    public void RoleSignInAction(ActionEvent actionEvent) throws IOException, SQLException {
        User usr = dao.getUser(usernameField.getText(), passwordField.getText());
        Administrator admin = dao.getAdministrator(usernameField.getText(), passwordField.getText());

        if(usernameField.getText().trim().isEmpty() || passwordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("warning"));
            alert.setHeaderText(bundle.getString("type_data"));
            alert.setContentText(bundle.getString("try_again"));
            alert.showAndWait();
        }

       else if(usr==null && User.isSelected() || admin==null && Administrator.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("invalid_username_password"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }

         if(User.isSelected() && usr!=null){
            Stage stage = (Stage)registerBtn.getScene().getWindow();
            stage.close();
            ViewMenuController kontroler = new ViewMenuController(usernameField.getText());
             stage = new Stage();
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user panel/viewMenu.fxml"), bundle);
             loader.setController(kontroler);
             Parent root = loader.load();
             stage.setTitle(bundle.getString("user"));
             stage.getIcons().add(new Image("/images/add-user.png"));
             stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
             stage.setResizable(false);
             stage.showAndWait();
        }
        else if(Administrator.isSelected() && admin!=null){
            Stage stage = (Stage)registerBtn.getScene().getWindow();
            stage.close();
             AdministratorController kontroler = new AdministratorController();
             stage = new Stage();
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin panel/administrator-main.fxml"), bundle);
             loader.setController(kontroler);
             Parent root = loader.load();
             stage.setTitle(bundle.getString("administrator"));
             stage.getIcons().add(new Image("/images/admin.png"));
             stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
             stage.showAndWait();
        }
    }
    public void englishLanguageAction(ActionEvent actionEvent) {
        Locale locale = new Locale("en_US");
        Locale.setDefault(new Locale("en", "US"));
        bundle = ResourceBundle.getBundle("Translation_en_US", locale);
        setTranslation(bundle);

    }

    private void setTranslation(ResourceBundle bundle) {
        usernameLbl.setText(bundle.getString("username"));
        passwordLbl.setText(bundle.getString("password"));
        loginLbl.setText(bundle.getString("login"));
        User.setText(bundle.getString("user"));
        Administrator.setText(bundle.getString("administrator"));
        signinBtn.setText(bundle.getString("sign_in"));
        notHaveAccountLbl.setText(bundle.getString("Do_not_have_account?"));
        registerBtn.setText(bundle.getString("register"));
        Stage stage = (Stage) passwordLbl.getScene().getWindow();
        stage.setTitle(bundle.getString("login"));
    }

    public void bosnianLanguageAction(ActionEvent actionEvent) {
        Locale locale = new Locale("bs_BA");
        Locale.setDefault(new Locale("bs", "BA"));
        bundle = ResourceBundle.getBundle("Translation_bs_BA", locale);
        setTranslation(bundle);
    }

    public void germanLanguageAction(ActionEvent actionEvent) {
        Locale locale = new Locale("de_DE");
        Locale.setDefault(new Locale("de", "DE"));
        bundle = ResourceBundle.getBundle("Translation_de_DE", locale);
        setTranslation(bundle);
    }

    private void hidePassword(){
        imageview.setImage(new Image("/images/hide.png"));
        passwordTextField.setVisible(false);
        passwordTextField.setEditable(false);
        passwordField.setVisible(true);
        passwordField.setEditable(true);
        passwordField.setText(passwordTextField.getText());
    }
    private void showPassword(){
        imageview.setImage(new Image("/images/view.png"));

        passwordTextField.setEditable(true);
        passwordTextField.setVisible(true);
        passwordField.setVisible(false);
        passwordField.setEditable(false);
        passwordTextField.setText(passwordField.getText());
    }

    public void imageHideShowClick(MouseEvent mouseEvent) {
        showPassword=!showPassword;
        if(showPassword) showPassword();
        else hidePassword();
    }
}
