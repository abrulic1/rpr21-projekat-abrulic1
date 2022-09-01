package ba.unsa.etf.rpr.projekat;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdministratorController {
    public TableColumn<Integer, User> idUserTblColumn;
    public TableColumn<String, User> nameUserTblColumn;
    public TableColumn<String, User> surnameUserTblColumn;
    public TableColumn<String, User> emailUserTblColumn;
    public TableColumn<String, User> usernameUserTblColumn;
    public TableColumn<String, User> passwordUserTblColumn;
    public TableColumn<String, User> genderUserTblColumn;
    public Button addUserBtn;
    public Button deleteUserBtn;
    public Button editUserBtn;
    public Button printUserBtn;
    public TableColumn<Integer, Reservation> idReservationTblColumn;
    public TableColumn<LocalDate, Reservation> dateReservationTblColumn;
    public TableColumn<String, Reservation> timeReservationTblColumn;
    public Button addReservationBtn;
    public Button deleteReservationBtn;
    public Button editReservationBtn;
    public Button printReservationBtn;
    public TableColumn<Integer, MenuItem> idMenuTblColumn;
    public TableColumn<String, MenuItem> nameMenuTblColumn;
    public TableColumn<Double, MenuItem> priceMenuTblColumn;
    public TableColumn<String, MenuItem> veganMenuTblColumn;
    public TableColumn<String, MenuItem> vegetarianMenuTblColumn;
    public Button addMenuBtn;
    public Button editMenuBtn;
    public Button deleteMenuBtn;
    public Button printMenuBtn;
    public Button signoutBtn;
    public TableView<User> usersTableView;
    public TableView<Reservation> reservationsTableView;
    public TableView<MenuItem> menuTableView;
    public TableColumn<Integer, Reservation> guestsReservationTable;
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());

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
        idReservationTblColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateReservationTblColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeReservationTblColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        guestsReservationTable.setCellValueFactory(new PropertyValueFactory<>("numberOfGuests"));
        reservationsTableView.setItems(dao.returnAllReservations());
        idMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        veganMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("vegan"));
        vegetarianMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));
        menuTableView.setItems(dao.returnAllMenuItems());

    }

    public void signOutBtnAction(ActionEvent actionEvent) throws IOException {
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

    public void editUserAction(ActionEvent actionEvent) throws IOException, SQLException {
        if(usersTableView.getSelectionModel().getSelectedItem()!=null) {
            User usr = usersTableView.getSelectionModel().getSelectedItem();
            EditUserController kontroler = new EditUserController(usr.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editUser-AdminPanel.fxml"), bundle);
            loader.setController(kontroler);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(bundle.getString("add_user")); //////////
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            kontroler.usernameFld.setText(usr.getUsername());
            kontroler.nameFld.setText(usr.getName());
            kontroler.surnameFld.setText(usr.getSurname());
            kontroler.emailFld.setText(usr.getEmail());
            kontroler.passwordFld.setText(usr.getPassword());
            Platform.runLater(() -> kontroler.passwordFld.setVisible(true));   //ne radi ovo..
            if(usr.getGender().equals("Female"))
                kontroler.femaleBtn.setSelected(true);
            else kontroler.maleBtn.setSelected(true);
            stage.showAndWait();
            usersTableView.getItems().clear();
            usersTableView.setItems(dao.returnAllUsers());
            usersTableView.refresh();
            /////////////////MOGU I SETONHIDING OVDJE UMJESTO OVE 3 LINIJE /////////////////////////
        }
    }

    public void addReservationAction(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Locale.setDefault(new Locale("en_US"));
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/addReservation-AdminPanel.fxml"), bundle);
        Scene scene = new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setTitle("Add Reservation");
       // stage.getIcons().add(new Image("/images/user-icon.png"));
        stage.setScene(scene);
        stage.toFront();
        stage.setResizable(false);
        stage.showAndWait();
         reservationsTableView.getItems().clear();
         reservationsTableView.setItems(dao.returnAllReservations());
         reservationsTableView.refresh();

    }

    public void deleteReservationAction(ActionEvent actionEvent) {
//        Reservation reservation = reservationsTableView.getSelectionModel().getSelectedItem();
//        if(reservation!=null){
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle(bundle.getString("confirmation"));
//           // alert.setHeaderText(bundle.getString("confirmation_text"));
//            //alert.setContentText(bundle.getString("confirmation_question"));
//            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(bundle.getString("cancel"));
//            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(bundle.getString("ok"));
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){
//                // ... user chose OK
//                dao.deleteReservation(reservation.getId());
//                reservationsTableView.getItems().clear();
//                reservationsTableView.setItems(dao.returnAllReservations());
//                reservationsTableView.refresh();
//            } else {
//                // ... user chose CANCEL or closed the dialog
//
//            }
//        }
    }

    public void editReservationAction(ActionEvent actionEvent) {
    }

    public void printReservationAction(ActionEvent actionEvent) {
    }

    public void addMenuAction(ActionEvent actionEvent) {
    }

    public void deleteMenuAction(ActionEvent actionEvent) {
    }

    public void editMenuAction(ActionEvent actionEvent) {
    }

    public void printMenuAction(ActionEvent actionEvent) {
    }
}
