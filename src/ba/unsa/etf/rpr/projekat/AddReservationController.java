package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddReservationController {
    public DatePicker datePickerId;
    public Button minusBtn;
    public Button plusBtn;
    public Label numberOfGuestsLabel;
    public Label descReservationLabel;
    public Button cancelBtn;
    public Button okBtn;
    public ChoiceBox<String> timeChoiceBox;
    DatabaseDAO dao = DatabaseDAO.getInstance();
    ArrayList<String> time = new ArrayList<>();

    public AddReservationController() throws SQLException {
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
    public void initialize(){
        datePickerId.setValue(LocalDate.now());
        timeChoiceBox.setItems(FXCollections.observableList(time));
        timeChoiceBox.getSelectionModel().selectFirst();
        datePickerId.setDayCellFactory(lambda->
                new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) { //Disable all dates after required date
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        if(item.isAfter(LocalDate.now().plusDays(30))){
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                });
//        LocalDate localDate = datePickerId.getValue();
//        LocalDate date = LocalDate.parse(localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//        datePickerId.setValue(localDate);


    }

    public void minusBtnAction(ActionEvent actionEvent) {
        String[] numbers = numberOfGuestsLabel.getText().split(" ");
        int number = Integer.parseInt(numbers[0]);
        if(number==1) return;
        numberOfGuestsLabel.setText(String.valueOf((number-1))+" Guests");
    }

    public void plusBtnAction(ActionEvent actionEvent) {
        String[] numbers = numberOfGuestsLabel.getText().split(" ");
        int number = Integer.parseInt(numbers[0]);
        numberOfGuestsLabel.setText(String.valueOf((number+1)) + " Guests");
    }

    public void cancelBtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void okBtnAction(ActionEvent actionEvent) {
        String[] time = timeChoiceBox.getSelectionModel().getSelectedItem().split(" ");
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        ArrayList<Reservation> r = null;

            r = dao.returnAllReservations(datePickerId.getValue(), time[0]);
        if(!r.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("not_correctly_fulfilled_infos"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
        else{
            String[] string = numberOfGuestsLabel.getText().split(" ");
                dao.addNewReservation(datePickerId.getValue(), timeChoiceBox.getSelectionModel().getSelectedItem(), Integer.parseInt(string[0]));
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            Stage s = (Stage) cancelBtn.getScene().getWindow();
            s.close();
        }
    }
}
