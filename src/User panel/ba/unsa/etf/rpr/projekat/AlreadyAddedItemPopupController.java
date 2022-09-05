package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlreadyAddedItemPopupController {
    public Label labelForMenuitem;
    public Button closeBtn;
    private String n;

    public AlreadyAddedItemPopupController(String name) {
       n=name;
    }

    @FXML
    public void initialize(){

    }


    public void closebtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
