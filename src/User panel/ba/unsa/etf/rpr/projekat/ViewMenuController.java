package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ViewMenuController {
    public ImageView imageView;
    public Button viewAllBtn;
    public ListView listView;
    public Button veganBtn;
    public Button vegetarianBtn;
    public Button signoutBtn;
    ResourceBundle bundle = ResourceBundle.getBundle("Translation_" + Locale.getDefault().toString());
    DatabaseDAO dao = DatabaseDAO.getInstance();


    public ViewMenuController() throws SQLException {

    }

    @FXML
    public void initialize(){
        listView.setItems(dao.returnAllMenuItems());
    }


    public void signoutAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) signoutBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        stage.setTitle(bundle.getString("login"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }

    public void viewAllAction(MouseEvent mouseEvent) {
        listView.setItems(dao.returnAllMenuItems());
    }

    public void viewVeganAction(MouseEvent mouseEvent) {
        listView.setItems(dao.returnAllVeganMenuItems());
    }

    public void viewVegetarianAction(MouseEvent mouseEvent) {
        listView.setItems(dao.returnAllVegetarianMenuItems());
    }
}
