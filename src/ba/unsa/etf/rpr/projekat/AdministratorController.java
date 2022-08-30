package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdministratorController {
    public TableColumn idUserTblColumn;
    public TableColumn nameUserTblColumn;
    public TableColumn surnameUserTblColumn;
    public TableColumn emailUserTblColumn;
    public TableColumn usernameUserTblColumn;
    public TableColumn passwordUserTblColumn;
    public TableColumn genderUserTblColumn;
    public Button addUserBtn;
    public Button deleteUserBtn;
    public Button editUserBtn;
    public Button printUserBtn;
    public TableColumn idReservationTblColumn;
    public TableColumn codeReservationTblColumn;
    public TableColumn timeReservationTblColumn;
    public TableColumn nogReservationTblColumn;
    public Button addReservationBtn;
    public Button deleteReservationBtn;
    public Button editReservationBtn;
    public Button printReservationBtn;
    public TableColumn idMenuTblColumn;
    public TableColumn nameMenuTblColumn;
    public TableColumn priceMenuTblColumn;
    public TableColumn veganMenuTblColumn;
    public TableColumn vegetarianMenuTblColumn;
    public Button addMenuBtn;
    public Button modifyMenuBtn;
    public Button deleteMenuBtn;
    public Button printMenuBtn;
    public Button signoutBtn;
    public TableView usersTableView;
    public TableView reservationsTableView;
    public TableView menuTableView;

    DatabaseDAO dao = DatabaseDAO.getInstance();

    public AdministratorController() throws SQLException {
    }

    @FXML
    public void initialize(){
        idUserTblColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameUserTblColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameUserTblColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        emailUserTblColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameUserTblColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordUserTblColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        genderUserTblColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        usersTableView.setItems(dao.returnAllUsers());
    }

    public void signOutBtnAction(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        Stage stage = (Stage) signoutBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        stage.setTitle(bundle.getString("login"));
        //kako podesiti da se vrati al na popunjene podatke??
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }

    public void addUserBtnAction(ActionEvent actionEvent) throws IOException, SQLException {
        AddUserController kontroler = new AddUserController();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(kontroler);
        loader = new FXMLLoader(getClass().getResource("/fxml/addUser-AdminPanel.fxml"), bundle);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("add_user"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.showAndWait();
        usersTableView.getItems().clear();
        usersTableView.setItems(dao.returnAllUsers());
        usersTableView.refresh();
    }

    public void deleteUserAction(ActionEvent actionEvent) {
        User usr = (User) usersTableView.getSelectionModel().getSelectedItem();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        if(usr!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(bundle.getString("confirmation"));
            alert.setHeaderText(bundle.getString("confirmation_text"));
            alert.setContentText(bundle.getString("confirmation_question"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(bundle.getString("cancel"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(bundle.getString("ok"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                dao.deleteUser(usr.getId());
                usersTableView.getItems().clear();
                usersTableView.setItems(dao.returnAllUsers());
                usersTableView.refresh();
            } else {
                // ... user chose CANCEL or closed the dialog

            }
        }

    }
}
