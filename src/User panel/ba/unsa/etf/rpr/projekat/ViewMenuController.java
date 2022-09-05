package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ViewMenuController {
    public Button viewAllBtn;
    public Button veganBtn;
    public Button vegetarianBtn;
    public Button signoutBtn;
    public TableView<MenuItem> tableView;
    public TableColumn<String, MenuItem> nameTbl;
    public TableColumn<Double, MenuItem> priceTable;
    public Button addToWishlistBtn;
    public TextField nameField;
    public TextField surnameField;
    public TextField emailField;
    public DatePicker datePickerId;
    public ChoiceBox<String> choiceBoxId;
    public TextField usernameId;
    public PasswordField passwordId;
    public Button submitButton;
    public ListView<String> listViewId;
    public Label totalNumberLabel;
    public Button checkReservationButton;
    public Button deleteItemBtn;
    public Button minusBtn;
    public Label numberOfGuestsLabel;
    public Button plusBtn;
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
    DatabaseDAO dao = DatabaseDAO.getInstance();
    double total = 0;
    public String usrName;
    private ArrayList<String> time = new ArrayList<>();

    public ViewMenuController(String username) throws SQLException {
        usrName=username;
        time.add("11:00 AM");
        time.add("12:00 AM");
        time.add("01:00 PM");
        time.add("02:00 PM");
        time.add("03:00 PM");
        time.add("04:00 PM");
        time.add("05:00 PM");
        time.add("06:00 PM");
        time.add("07:00 PM");
        time.add("08:00 PM");
        time.add("09:00 PM");
        time.add("10:00 PM");
    }


@FXML
    public void initialize() {
        addToWishlistBtn.wrapTextProperty().setValue(true);
        viewAllBtn.wrapTextProperty().setValue(true);
        veganBtn.wrapTextProperty().setValue(true);
        vegetarianBtn.wrapTextProperty().setValue(true);
        deleteItemBtn.wrapTextProperty().setValue(true);
        checkReservationButton.wrapTextProperty().setValue(true);
        submitButton.wrapTextProperty().setValue(true);
        listViewId.setItems(dao.getAllWishlistItems(usrName));
    System.out.println(dao.getAllWishlistItems(usrName).size());
    System.out.println(usrName);
        choiceBoxId.setItems(FXCollections.observableList(time));
        choiceBoxId.getSelectionModel().selectFirst();
        total=dao.returnTotalFromWishlist(usrName);
        totalNumberLabel.setText(String.valueOf(total));
        datePickerId.setDayCellFactory(lambda->
                new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        if(item.isAfter(LocalDate.now().plusDays(30))){
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                });
        datePickerId.setValue(LocalDate.now());
        nameTbl.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceTable.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.setItems(dao.returnAllMenuItems());
    }


    public void signoutAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) signoutBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        stage.setTitle(bundle.getString("login"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }

    public void addToWishlistAction(ActionEvent actionEvent) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() != null && !listViewId.getItems().contains(tableView.getSelectionModel().getSelectedItem().getName())) {
            listViewId.getItems().add(tableView.getSelectionModel().getSelectedItem().getName());
            listViewId.refresh();
            total = total + tableView.getSelectionModel().getSelectedItem().getPrice();
            totalNumberLabel.setText(String.valueOf(total));
            dao.addItemOnWishlist(usrName, tableView.getSelectionModel().getSelectedItem().getName(), tableView.getSelectionModel().getSelectedItem().getPrice());
        } else if (tableView.getSelectionModel().getSelectedItem() != null && listViewId.getItems().contains(tableView.getSelectionModel().getSelectedItem().getName())) {
           AlreadyAddedItemPopupController kontroler = new AlreadyAddedItemPopupController(tableView.getSelectionModel().getSelectedItem().getName());
            FXMLLoader  loader = new FXMLLoader(getClass().getResource("/fxml/user panel/alreadyAddedItem.fxml"), bundle);
           Stage stage = new Stage();
           loader.setController(kontroler);
           Parent root = loader.load();
           stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void viewAllAction(MouseEvent mouseEvent) {
        tableView.setItems(dao.returnAllMenuItems());
    }

    public void viewVeganAction(MouseEvent mouseEvent) {
        tableView.setItems(dao.returnAllVeganMenuItems());
    }

    public void viewVegetarianAction(MouseEvent mouseEvent) {
        tableView.setItems(dao.returnAllVegetarianMenuItems());
    }

    public void submitReservationAction(ActionEvent actionEvent) {
       User user = dao.getUser(usernameId.getText(), passwordId.getText());
        if(user==null){
            //nema takvih podataka, provjerite username i password
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("no_such_data"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
        else if(user!=null && usernameId.getText().equals(user.getUsername()) && nameField.getText().equals(user.getName()) &&
                surnameField.getText().equals(user.getSurname()) && usernameId.getText().equals(usrName) && emailField.getText().equals(user.getEmail()) && passwordId.getText().equals(user.getPassword())) {
           if (!dao.returnAllUsersReservation(usrName).isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("error"));
                alert.setHeaderText(bundle.getString("you_already_have_one_reservation"));
                alert.setContentText(bundle.getString("click_ok_try_again"));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("information"));
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("successful_reservation"));
               String[] string = numberOfGuestsLabel.getText().split(" ");
               dao.addNewReservation(datePickerId.getValue(), choiceBoxId.getSelectionModel().getSelectedItem(), Integer.parseInt(string[0]), user.getId(), user.getName(), user.getSurname());
                alert.showAndWait();
            }
        }
            else if(user!=null && !usernameId.getText().equals(user.getUsername()) || !nameField.getText().equals(user.getName()) ||
                !surnameField.getText().equals(user.getSurname()) || !usernameId.getText().equals(usrName) || !emailField.getText().equals(user.getEmail()) || !passwordId.getText().equals(user.getPassword())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("error"));
                    alert.setHeaderText(bundle.getString("check_your_data"));
                    alert.setContentText(bundle.getString("click_ok_try_again"));
                    alert.showAndWait();
                }
    }

    public void checkReservationAction(ActionEvent actionEvent) throws IOException, SQLException {
        if(!dao.returnAllUsersReservation(usrName).isEmpty()) {
            CheckReservationController kontroler = new CheckReservationController(usrName);
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user panel/check-reservation.fxml"), bundle);
            loader.setController(kontroler);
            Parent root = loader.load();
            stage.setTitle(bundle.getString("your_reservation"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("invalid_username_password"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
    }


    public void deleteFromWishlistAction(ActionEvent actionEvent) {
        if (listViewId.getSelectionModel().getSelectedItem() != null) {
            dao.deleteFromWishlist(usrName, listViewId.getSelectionModel().getSelectedItem());
            total=total-dao.getPriceOfMenuitem(listViewId.getSelectionModel().getSelectedItem());
            totalNumberLabel.setText(String.valueOf(total));
            listViewId.getItems().remove(listViewId.getSelectionModel().getSelectedItem().toString());
            listViewId.refresh();
        }
    }


    public void minusBtnAction(ActionEvent actionEvent) {
        String[] numbers = numberOfGuestsLabel.getText().split(" ");
        int number = Integer.parseInt(numbers[0]);
        if(number==1) return;
        numberOfGuestsLabel.setText(String.valueOf((number-1))+ " " +bundle.getString("guests"));
    }

    public void plusBtnAction(ActionEvent actionEvent) {
        String[] numbers = numberOfGuestsLabel.getText().split(" ");
        int number = Integer.parseInt(numbers[0]);
        numberOfGuestsLabel.setText(String.valueOf((number+1))+ " " +bundle.getString("guests"));
    }
}
