package ba.unsa.etf.rpr.projekat;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class UserController {
    public ImageView imageView;
    public Button viewMenuBtn;
    public Button reserveBtn;
    public Button signoutBtn;
    public BorderPane borderpane;
    public Label titleLabel;
    ArrayList<Image> images = new ArrayList<>();
    int counter=0;
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());

    public UserController() {
        images.add(new Image("/images/slide1.jpg"));
        images.add(new Image("/images/slide2.jpg"));
        images.add(new Image("/images/slide3.jpg"));
        images.add(new Image("/images/slide4.jpg"));
        images.add(new Image("/images/slide5.jpg"));
    }

    @FXML
    public void initialize(){
        imageView.setPreserveRatio(false);
        imageView.fitWidthProperty().bind(borderpane.widthProperty());
        imageView.fitHeightProperty().bind(borderpane.heightProperty());
        slideshow();
    }

    public void slideshow(){
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(3), lambda ->{
            imageView.setImage(images.get(counter));
            counter++;
            if(counter==5) counter=0;
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    public void viewMenuAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)imageView.getScene().getWindow();
        stage.close();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/user panel/viewMenu.fxml"), bundle);
        stage.setTitle(bundle.getString("view_our_menu"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void reserveTableAction(ActionEvent actionEvent) {

    }

    public void signoutAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) signoutBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        stage.setTitle(bundle.getString("login"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }
}
