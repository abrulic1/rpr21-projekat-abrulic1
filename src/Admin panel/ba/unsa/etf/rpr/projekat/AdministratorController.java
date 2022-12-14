package ba.unsa.etf.rpr.projekat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public Label totalNumberId;
    public Label totalReservations;
    public Label totalMenus;
    public TableColumn<Integer, Reservation> guestIdTbl;
    public TableColumn<String, Reservation> guestNameTbl;
    public TableColumn<String, Reservation> guestSurnameTbl;


    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());

    DatabaseDAO dao = DatabaseDAO.getInstance();

    public AdministratorController() throws SQLException {
    }

    @FXML
    public void initialize(){
        printReservationBtn.wrapTextProperty().setValue(true);
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
        guestIdTbl.setCellValueFactory(new PropertyValueFactory<>("userId"));
        guestNameTbl.setCellValueFactory(new PropertyValueFactory<>("guest_name"));
       guestSurnameTbl.setCellValueFactory(new PropertyValueFactory<>("guest_surname"));
        reservationsTableView.setItems(dao.returnAllReservations());
        idMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        veganMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("vegan"));
        vegetarianMenuTblColumn.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));
        menuTableView.setItems(dao.returnAllMenuItems());
        totalNumberId.setText(String.valueOf(dao.returnAllUsers().size()));
        totalReservations.setText(String.valueOf(dao.returnAllReservations().size()));
    }

    public void signOutBtnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) signoutBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        stage.setTitle(bundle.getString("login"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }

    public void addUserBtnAction(ActionEvent actionEvent) throws IOException, SQLException {
        AddUserController kontroler = new AddUserController();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(kontroler);
        loader = new FXMLLoader(getClass().getResource("/fxml/admin panel/addUser.fxml"), bundle);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("add_user"));
        stage.getIcons().add(new Image("/images/add-user.png"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.showAndWait();
        usersTableView.getItems().clear();
        usersTableView.setItems(dao.returnAllUsers());
        usersTableView.refresh();
        totalNumberId.setText(String.valueOf(dao.returnAllUsers().size()));
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
                totalNumberId.setText(String.valueOf(dao.returnAllUsers().size()));
            } else {
                // ... user chose CANCEL or closed the dialog

            }
        }

    }

    public void editUserAction(ActionEvent actionEvent) throws IOException, SQLException {
        if(usersTableView.getSelectionModel().getSelectedItem()!=null) {
            User usr = usersTableView.getSelectionModel().getSelectedItem();
            EditUserController kontroler = new EditUserController(usr.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin panel/editUser.fxml"), bundle);
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
            Platform.runLater(() -> kontroler.passwordFld.setVisible(true));
            if(usr.getGender().equals("Female"))
                kontroler.femaleBtn.setSelected(true);
            else kontroler.maleBtn.setSelected(true);
            stage.setResizable(false);
            stage.showAndWait();
            usersTableView.getItems().clear();
            usersTableView.setItems(dao.returnAllUsers());
            usersTableView.refresh();
        }
    }

    public void addReservationAction(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Locale.setDefault(new Locale("en_US"));
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/admin panel/addReservation.fxml"), bundle);
        Scene scene = new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("addReservation"));
        stage.getIcons().add(new Image("/images/addreservation.png"));
        stage.setScene(scene);
        stage.toFront();
        stage.setResizable(false);
        stage.showAndWait();
         reservationsTableView.getItems().clear();
         reservationsTableView.setItems(dao.returnAllReservations());
         reservationsTableView.refresh();
        totalReservations.setText(String.valueOf(dao.returnAllReservations().size()));
    }

    public void deleteReservationAction(ActionEvent actionEvent) {
        Reservation reservation = reservationsTableView.getSelectionModel().getSelectedItem();
        if(reservation!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(bundle.getString("confirmation"));
           // alert.setHeaderText(bundle.getString("confirmation_text"));
            //alert.setContentText(bundle.getString("confirmation_question"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(bundle.getString("cancel"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(bundle.getString("ok"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                dao.deleteReservation(reservation.getId());
                reservationsTableView.getItems().clear();
                reservationsTableView.setItems(dao.returnAllReservations());
                reservationsTableView.refresh();
                totalReservations.setText(String.valueOf(dao.returnAllReservations().size()));
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    public void editReservationAction(ActionEvent actionEvent) throws IOException, SQLException {
        Reservation reservation = reservationsTableView.getSelectionModel().getSelectedItem();
        if(reservationsTableView.getSelectionModel().getSelectedItem()!=null){
            Reservation reservations = reservationsTableView.getSelectionModel().getSelectedItem();
            EditReservationController kontroler = new EditReservationController(reservation.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin panel/editReservation.fxml"), bundle);
            loader.setController(kontroler);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(bundle.getString("edit_reservation")); //////////
            stage.getIcons().add(new Image("/images/addreservation.png"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            kontroler.datePickerId.setValue(reservation.getDate());
            kontroler.timeChoiceBox.getSelectionModel().select(reservation.getTime());
            kontroler.numberOfGuestsLabel.setText(reservation.getNumberOfGuests() + " Guests");
            kontroler.choiceUserBox.setValue(dao.returnAllUsersById(reservationsTableView.getSelectionModel().getSelectedItem().getUserId()));
            stage.setResizable(false);
            stage.showAndWait();
            reservationsTableView.getItems().clear();
            reservationsTableView.setItems(dao.returnAllReservations());
            reservationsTableView.refresh();
        }

    }

    public void printReservationAction(ActionEvent actionEvent) {
        try {
            new PrintReport().showReport(dao.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void addMenuAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/admin panel/addMenuItem.fxml"), bundle);
        Scene scene = new Scene(loader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("addMenuItem"));
        stage.getIcons().add(new Image("/images/addreservation.png"));
        stage.setScene(scene);
        stage.toFront();
        stage.setResizable(false);
        stage.showAndWait();
        menuTableView.getItems().clear();
        menuTableView.setItems(FXCollections.observableList(dao.returnAllMenuItems()));
        menuTableView.refresh();
    }

    public void deleteMenuAction(ActionEvent actionEvent) {
        MenuItem menuitem = menuTableView.getSelectionModel().getSelectedItem();
        if(menuitem!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(bundle.getString("confirmation"));
            // alert.setHeaderText(bundle.getString("confirmation_text"));
            //alert.setContentText(bundle.getString("confirmation_question"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(bundle.getString("cancel"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(bundle.getString("ok"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                dao.deleteMenuItem(menuitem.getId());
                menuTableView.getItems().clear();
                menuTableView.setItems(dao.returnAllMenuItems());
                menuTableView.refresh();
                totalMenus.setText(String.valueOf(dao.returnAllMenuItems().size()));
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    public void editMenuAction(ActionEvent actionEvent) throws IOException, SQLException {
        if(menuTableView.getSelectionModel().getSelectedItem()!=null){
            MenuItem menuitem = menuTableView.getSelectionModel().getSelectedItem();
            EditMenuItemController kontroler = new EditMenuItemController(menuitem.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin panel/editMenuItem.fxml"), bundle);
            loader.setController(kontroler);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(bundle.getString("edit_menuitem")); //////////
            //stage.getIcons().add(new Image("/images/addreservation.png"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            kontroler.nameField.setText(menuitem.getName());
            //kontroler.priceField.setValueFactory();
            if(menuitem.getVegan().equals("no"))
                kontroler.veganId.setSelected(false);
            else kontroler.veganId.setSelected(true);
            if(menuitem.getVegetarian().equals("no"))
                kontroler.vegetarianId.setSelected(false);
            else kontroler.vegetarianId.setSelected(true);
            stage.setResizable(false);
            stage.showAndWait();
            menuTableView.getItems().clear();
            menuTableView.setItems(dao.returnAllMenuItems());
            menuTableView.refresh();
        }
    }

    public void printMenuAction(ActionEvent actionEvent) {
        try {
            new PrintReport().showReportForMenu(dao.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }


}
