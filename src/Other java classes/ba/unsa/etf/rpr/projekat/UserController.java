package ba.unsa.etf.rpr.projekat;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class UserController {
    public ImageView imageView;
    public Button viewMenuBtn;
    public Button reserveBtn;
    public Button signoutBtn;
    ArrayList<Image> images = new ArrayList<>();
    int counter=0;

    public UserController() {
        images.add(new Image("/images/slide1.jpg"));
        images.add(new Image("/images/slide2.jpg"));
        images.add(new Image("/images/slide3.jpg"));
        images.add(new Image("/images/slide4.jpg"));
        images.add(new Image("/images/slide5.jpg"));
    }

    @FXML
    public void initialize(){
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(3), lambda ->{
            imageView.setImage(images.get(counter));
            counter++;
            if(counter==5) counter=0;
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    public void viewMenuAction(ActionEvent actionEvent) {
    }

    public void reserveTableAction(ActionEvent actionEvent) {
    }

    public void signoutAction(ActionEvent actionEvent) {
    }
}
