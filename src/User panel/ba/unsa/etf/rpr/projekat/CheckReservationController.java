package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class CheckReservationController {
    public Button closeBtn;
    public Label dateLabel;
    public Label timeLabel;
    public Label clickHereLabel;
    public String username;
    DatabaseDAO dao = DatabaseDAO.getInstance();
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());

    public CheckReservationController(String usrName) throws SQLException {
        username=usrName;
    }

    @FXML
    public void initialize(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String datum = df.format(dao.returnUserReservation(username).getDate());
        dateLabel.setText(datum);
        timeLabel.setText(dao.returnUserReservation(username).getTime());
    }

    public void clickHereAction(MouseEvent mouseEvent) {
        if(dao.returnUserReservation(username).getDate().isEqual(LocalDate.now().plusDays(1))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText("nIJEMMOGUCE 24H PRIJE REZERVACIJU OTKAZATI");
            alert.setContentText(bundle.getString("click_ok_try_again"));
            alert.showAndWait();
        }
        else{
            dao.deleteReservationFromDatabase(username);
            //information dialog
        }
    }

    public void closeBtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
