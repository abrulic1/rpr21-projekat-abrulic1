package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AlreadyAddedItemPopupController {
    public Label labelForMenuitem;
    private String n;

    public AlreadyAddedItemPopupController(String name) {
       n=name;
    }

    @FXML
    public void initialize(){
        labelForMenuitem.setText(n);
    }


}
