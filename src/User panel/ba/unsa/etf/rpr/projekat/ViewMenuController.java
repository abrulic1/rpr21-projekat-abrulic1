package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
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
    public ChoiceBox choiceBoxId;
    public TextField usernameId;
    public TextField passwordId;
    public Button submitButton;
    public ListView<String> listViewId;
    public Label totalNumberLabel;
    public Button checkReservationButton;
    public Button deleteItemBtn;
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
    DatabaseDAO dao = DatabaseDAO.getInstance();
    double total = 0;
    private String usrName;

//    public ViewMenuController(String username) throws SQLException {
//        usrName=username;
//    }


    public ViewMenuController() throws SQLException {
    }

    @FXML
    public void initialize() {
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
           // dao.addOnWishList(usrName, tableView.getSelectionModel().getSelectedItem().getName(), tableView.getSelectionModel().getSelectedItem().getPrice());
        } else if (tableView.getSelectionModel().getSelectedItem() != null && listViewId.getItems().contains(tableView.getSelectionModel().getSelectedItem().getName())) {
//           PopUpController kontroler = new PopUpController();
//            FXMLLoader  loader = new FXMLLoader(getClass().getResource("/fxml/user panel/nesto.fxml"));
//           Stage stage = new Stage();
//           loader.setController(kontroler);
//           Parent root = loader.load();
//           stage.setScene(new Scene(root));
//            PauseTransition s = new PauseTransition(Duration.seconds(3));
//            stage.showAndWait();
//            s.setOnFinished(f->{
//                stage.hide();
//            });
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
    }

    public void checkReservationAction(ActionEvent actionEvent) {
    }


    public void deleteFromWishlistAction(ActionEvent actionEvent) {
        if (listViewId.getSelectionModel().getSelectedItem() != null) {
            listViewId.getItems().remove(listViewId.getSelectionModel().getSelectedItem().toString());
            listViewId.refresh();
            //total = total -
            //da uzme cijenenu iz baze i oduzme
        }
    }


}
