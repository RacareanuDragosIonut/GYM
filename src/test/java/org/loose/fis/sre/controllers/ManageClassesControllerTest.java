package org.loose.fis.sre.controllers;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.DayandHourTaken;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.ClassService;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.controllers.RegistrationController;
import org.loose.fis.sre.services.UserService;
import org.loose.fis.sre.controllers.LoginController;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;
@ExtendWith(ApplicationExtension.class)
public class ManageClassesControllerTest {
    public static final String USERNAME = "user2";
    public static final String PASSWORD = "password2";
    public static final String FIRSTNAME = "password2";
    public static final String LASTNAME = "password2";
    public static final String AGE = "password2";
    public static final String PHONENUMBER = "password2";
    public static final String ROLE = "Trainer";
    @BeforeEach
    void setUp() throws SQLException, UsernameAlreadyExistsException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root", "douazecisiunu2121");

        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM user_account");
        System.out.println(preparedStatement2);
        preparedStatement2.executeUpdate();

        UserService.addUser(USERNAME, PASSWORD, FIRSTNAME, LASTNAME, AGE, PHONENUMBER, ROLE);
        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM user_account");
        System.out.println(preparedStatement1);
        preparedStatement1.executeQuery();

        PreparedStatement preparedStatement3 = connection.prepareStatement("DELETE FROM classes");
        System.out.println(preparedStatement3);
        preparedStatement3.executeUpdate();


    }
    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }
    @Test
    @DisplayName("classes table is empty")
    void testTableIsEmpty() {
        assertThat(ClassService.getAllClasses()).isNull();
    }
    @Test
    @DisplayName("adding a class and class with the same hour and day can not be added")
    void addclass(FxRobot robot){
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#loginButton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Upper Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("12");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#addbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("Added Class");
        assertThat(ClassService.getAllClasses()).isNotNull();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
        robot.clickOn("#backbutton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Lower Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("12");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#addbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("A class with the day Tuesday and the hour 12 already exists");
        assertThat(ClassService.getAllClasses()).isNotNull();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
    }
    @Test
    @DisplayName("adding a class,then editing it and then a class with a different schedule can not be edited ")
    void editclass(FxRobot robot){
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#loginButton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Upper Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("12");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#addbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("Added Class");
        assertThat(ClassService.getAllClasses()).isNotNull();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
        robot.clickOn("#backbutton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Lower Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("12");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#editbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("Edited Class");
        assertThat(ClassService.getAllClasses()).isNotNull();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
        robot.clickOn("#backbutton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Upper Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("13");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#editbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("A class with the day Tuesday and the hour 13 does not exist");
        assertThat(ClassService.getAllClasses()).isNotNull();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
    }
    @Test
    @DisplayName("adding a class the cancelling it and then class that needs to be canceled does not exist ")
    void cancelclass(FxRobot robot){
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#loginButton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Upper Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("12");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#addbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("Added Class");
        assertThat(ClassService.getAllClasses()).isNotNull();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
        robot.clickOn("#backbutton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Upper Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("12");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#cancelbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("Canceled Class");
        robot.clickOn("#backbutton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Upper Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("12");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#addbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("Added Class");
        assertThat(ClassService.getAllClasses()).isNotNull();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
        robot.clickOn("#backbutton");
        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#typefield");
        robot.write("Upper Body Class");
        robot.clickOn("#dayfield");
        robot.write("Tuesday");
        robot.clickOn("#hourfield");
        robot.write("13");
        robot.clickOn("#nrclientsfield");
        robot.write("0");
        robot.clickOn("#cancelbutton");
        assertThat(robot.lookup("#addeditcancelmessage").queryLabeled()).hasText("The class does not exist");
        assertThat(ClassService.getAllClasses()).isNotNull();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
    }

}
