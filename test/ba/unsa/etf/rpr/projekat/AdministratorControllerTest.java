package ba.unsa.etf.rpr.projekat;

import com.sun.source.tree.AssertTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class AdministratorControllerTest {
    DatabaseDAO dao;
    {
        try {
            dao = DatabaseDAO.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    Stage theStage;
    AdministratorController kontroler;

    @Start
    public void start(Stage stage) throws SQLException, IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation", new Locale("bs_BA"));
        Locale.setDefault(new Locale("en", "US"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin panel/administrator-main.fxml"), bundle);
        kontroler = new AdministratorController();
        loader.setController(kontroler);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @BeforeEach
    public void createDatabase() throws SQLException {
        dao.createBase();
    }

    @AfterEach
    public void close(FxRobot robot) {
        if (robot.lookup("#cancelBtn").tryQuery().isPresent())
            robot.clickOn("#cancelBtn");
    }


    @Test
    void addUserBtnAction(FxRobot robot) {
        robot.clickOn("#tabUsers");
        robot.clickOn("#addUserBtn");

        robot.lookup("#nameFld").tryQuery().isPresent();

        robot.clickOn("#nameFld");
        robot.write("Novi");

        robot.clickOn("#surnameFld");
        robot.write("Novic");

        robot.clickOn("#emailFld");
        robot.write("novic@gmail.com");

        robot.clickOn("#usernameFld");
        robot.write("novic");

        robot.clickOn("#passwordFld");
        robot.write("novicnovic");

        robot.clickOn("#okBtn");

        assertEquals(3, dao.returnAllUsers().size());

        boolean added=false;
        if(!dao.returnAllUsers("novic").isEmpty())
            added=true;
            assertTrue(added);
    }

    @Test
    void deleteUserAction(FxRobot robot) {
        robot.clickOn("#tabUsers");
        robot.clickOn("Ana");
        robot.clickOn("#deleteUserBtn");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        assertEquals(1, dao.returnAllUsers().size());


        boolean deleted=false;
        if(dao.returnAllUsers("ana").isEmpty())
            deleted=true;
        assertTrue(deleted);
    }


    @Test
    void editUserAction(FxRobot robot) {
        robot.clickOn("#tabUsers");
        robot.clickOn("Ana");
        robot.clickOn("#editUserBtn");
        robot.lookup("#usernameFld").tryQuery().isPresent();
        robot.clickOn("#usernameFld");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("noviusername");
        robot.clickOn("#passwordFld");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("novipassword");
        robot.clickOn("#okBtn");
        boolean edited=false;
        if(dao.returnAllUsers("ana").isEmpty())
            edited=true;
        assertTrue(edited);

        if(dao.returnAllUsers("noviusername").isEmpty())
            edited=true;

        assertTrue(edited);
    }

    @Test
    void addReservationAction(FxRobot robot) {
        robot.clickOn("#tabReservations");
        robot.clickOn("#addReservationBtn");
        robot.clickOn("#datePickerId");
        robot.write(LocalDate.now().plusDays(2).toString());

        robot.clickOn("#timeChoiceBox");
        robot.clickOn("04:00 PM");

        robot.clickOn("#plusBtn");
        robot.clickOn("#plusBtn");

        robot.clickOn("#choiceUserBox");
        robot.clickOn("1 Neko Nekic");

        robot.clickOn("#okBtn");

        assertEquals(1, dao.returnAllReservations().size());
    }

    @Test
    void deleteReservationAction(FxRobot robot) {
        robot.clickOn("#tabReservations");
        robot.clickOn("03:00 PM");
        robot.clickOn("#deleteReservationBtn");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        assertEquals(1, dao.returnAllReservations().size());
    }

    @Test
    void addMenuAction(FxRobot robot) {
        robot.clickOn("#tabMenu");
        robot.clickOn("#addMenuBtn");
          robot.lookup("#nameField").tryQuery().isPresent();
          robot.clickOn("#nameField");
        robot.write("NewMeal");
        robot.clickOn("#priceField");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("11.5");
        robot.clickOn("#veganId");
        robot.clickOn("#okBtn");
        assertEquals(7, dao.returnAllMenuItems().size());
    }

    @Test
    void deleteMenuAction(FxRobot robot) {
        robot.clickOn("#tabMenu");
        robot.clickOn("cevapi");
        robot.clickOn("#deleteMenuBtn");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        assertEquals(7, dao.returnAllMenuItems().size());
    }

    @Test
    void editMenuAction(FxRobot robot) {
        robot.clickOn("#tabMenu");
        robot.clickOn("cevapi");
        robot.clickOn("#editMenuBtn");

        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#nameField");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("NewCevapcici");
        robot.clickOn("#okBtn");
        boolean edited = false;
        if(dao.returnAllMenuItems("cevapi").isEmpty()) edited=true;
        if(dao.returnAllMenuItems("NewCevapcici").isEmpty())edited=false;
        assertTrue(edited);
    }
}