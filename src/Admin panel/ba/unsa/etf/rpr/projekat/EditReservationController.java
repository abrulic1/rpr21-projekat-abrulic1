package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditReservationController {

    public Button okBtn;
    public Button cancelBtn;
    public ChoiceBox<User> choiceUserBox;
    public DatePicker datePickerId;
    public ChoiceBox<String> timeChoiceBox;
    public Button minusBtn;
    public Label numberOfGuestsLabel;
    public Button plusBtn;
    int editReservationId;
    DatabaseDAO dao = DatabaseDAO.getInstance();
    ArrayList<String> time=new ArrayList<>();
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());

    public EditReservationController(int id) throws SQLException {
        editReservationId=id;
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
        choiceUserBox.setItems(dao.returnAllUsers());
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
//        datePicker.setConverter(new StringConverter<LocalDate>() {
//            @Override
//            public String toString(LocalDate localDate) {
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                return df.format(localDate);
//            }
//
//            @Override
//            public LocalDate fromString(String s) {
//                return null;
//            }
//        });

    }

    public void minusBtnAction(ActionEvent actionEvent) {
        String[] numbers = numberOfGuestsLabel.getText().split(" ");
        int number = Integer.parseInt(numbers[0]);
        if(number==1) return;
        numberOfGuestsLabel.setText(String.valueOf((number-1)) + " " +bundle.getString("guests"));
    }

    public void plusBtnAction(ActionEvent actionEvent) {
        String[] numbers = numberOfGuestsLabel.getText().split(" ");
        int number = Integer.parseInt(numbers[0]);
        if(number==1) return;
        numberOfGuestsLabel.setText(String.valueOf((number+1))+ " " +bundle.getString("guests"));
    }

    public void cancelBtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void okBtnAction(ActionEvent actionEvent) {
       // String[] time = choiceTime.getSelectionModel().getSelectedItem().split(" ");
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
//        ArrayList<Reservation> r = null;
//
//        r = dao.returnAllReservations(datePickerId.getValue(), timeChoiceBox.getSelectionModel().getSelectedItem());
//        if(!r.isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle(bundle.getString("error"));
//            alert.setHeaderText(bundle.getString("not_correctly_fulfilled_infos"));
//            alert.setContentText(bundle.getString("click_ok_try_again"));
//            alert.showAndWait();
//        }
      //  else{
            String[] string = numberOfGuestsLabel.getText().split(" ");
            dao.editReservation(datePickerId.getValue(), timeChoiceBox.getSelectionModel().getSelectedItem(), Integer.parseInt(string[0]), editReservationId, choiceUserBox.getSelectionModel().getSelectedItem().getId(), choiceUserBox.getSelectionModel().getSelectedItem().getName(), choiceUserBox.getSelectionModel().getSelectedItem().getSurname());
            Stage s = (Stage) cancelBtn.getScene().getWindow();
            s.close();
        //}
    }
}
