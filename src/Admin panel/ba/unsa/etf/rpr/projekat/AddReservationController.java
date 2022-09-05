package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.swing.*;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddReservationController {
    public DatePicker datePickerId;
    public Button minusBtn;
    public Button plusBtn;
    public Label numberOfGuestsLabel;
    public Button cancelBtn;
    public Button okBtn;
    public ChoiceBox<String> timeChoiceBox;
    public ChoiceBox<User> choiceUserBox;
    DatabaseDAO dao = DatabaseDAO.getInstance();
    ArrayList<String> time = new ArrayList<>();
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());

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
        timeChoiceBox.setItems(FXCollections.observableList(time));
        timeChoiceBox.getSelectionModel().selectFirst();
        choiceUserBox.setItems(dao.returnAllUsers());
        datePickerId.setDayCellFactory(lambda->
                new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now().plusDays(1))) { //Disable all dates after required date
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        if(item.isAfter(LocalDate.now().plusDays(30))){
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                });

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

    public void cancelBtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void okBtnAction(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        if(choiceUserBox.getSelectionModel().getSelectedItem()==null|| timeChoiceBox.getSelectionModel().getSelectedItem()==null|| datePickerId.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("invalid_data"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
        else if(!dao.returnAllUsersReservation(choiceUserBox.getSelectionModel().getSelectedItem().getId()).isEmpty()){
               Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("you_already_have_one_reservation"));
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
           }
           else {
               String[] string = numberOfGuestsLabel.getText().split(" ");
               int usersId = choiceUserBox.getSelectionModel().getSelectedItem().getId();
               dao.addNewReservation(datePickerId.getValue(), timeChoiceBox.getSelectionModel().getSelectedItem(), Integer.parseInt(string[0]), choiceUserBox.getSelectionModel().getSelectedItem().getId(), choiceUserBox.getSelectionModel().getSelectedItem().getName(), choiceUserBox.getSelectionModel().getSelectedItem().getSurname());
               Stage s = (Stage) cancelBtn.getScene().getWindow();
               s.close();
           }
    }
}
