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
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.User;
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
public class RegistrationControllerTest {

    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";
    public static final String FIRSTNAME = "password";
    public static final String LASTNAME = "password";
    public static final String AGE = "password";
    public static final String PHONENUMBER = "password";
    public static final String ROLE = "Client";


    @BeforeEach
    void setUp() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root", "AnisiaRosu12");
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user_account");
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        primaryStage.setTitle("Registration Example");
        primaryStage.setScene(new Scene(root, 580, 415));
        primaryStage.show();
    }

    @Test
    @DisplayName("user_account table is empty")
    void testTableIsEmpty() {
        assertThat(UserService.getAllUsers()).isNull();
    }

    @Test
    @DisplayName("account successfully added to database && an account with the same username can't be added twice")
    void testRegistration(FxRobot robot) {
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#firstnameField");
        robot.write(FIRSTNAME);
        robot.clickOn("#lastnameField");
        robot.write(LASTNAME);
        robot.clickOn("#ageField");
        robot.write(AGE);
        robot.clickOn("#phonenumberField");
        robot.write(PHONENUMBER);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#registerButton");

        assertThat(UserService.getAllUsers()).size().isNotNull();
        User user = UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getFirstname()).isEqualTo("password");
        assertThat(user.getLastname()).isEqualTo("password");
        assertThat(user.getAge()).isEqualTo("password");
        assertThat(user.getPhonenumber()).isEqualTo("password");
        assertThat(user.getRole()).isEqualTo("Client");

        robot.clickOn("#backButton");
        assertThat(robot.lookup("#registrationMessage").queryLabeled()).hasText("Account created successfully!");

        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessage").queryLabeled()).hasText(
                String.format("An account with the username %s already exists!", USERNAME)
        );

    }
}
