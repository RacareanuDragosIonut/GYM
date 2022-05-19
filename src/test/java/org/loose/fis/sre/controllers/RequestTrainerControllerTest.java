package org.loose.fis.sre.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.exceptions.WrongPassword;
import org.loose.fis.sre.model.TrainerRequests;

import org.loose.fis.sre.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testfx.assertions.api.Assertions.assertThat;
@ExtendWith(ApplicationExtension.class)
public class RequestTrainerControllerTest {

    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";
    public static final String FIRSTNAME = "password";
    public static final String LASTNAME = "password";
    public static final String AGE = "password";
    public static final String PHONENUMBER = "password";
    public static final String ROLE = "Client";

    @BeforeEach
    void setUp() throws SQLException, UsernameAlreadyExistsException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root", "AnisiaRosu12");
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM trainerrequests");
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();

        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM user_account");
        System.out.println(preparedStatement2);
        preparedStatement2.executeUpdate();

        UserService.addUser(USERNAME, PASSWORD, FIRSTNAME, LASTNAME, AGE, PHONENUMBER, ROLE);
        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM user_account");
        System.out.println(preparedStatement1);
        preparedStatement1.executeQuery();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }

    @Test
    @DisplayName("trainerrequests table is empty")
    void testTableIsEmpty() {
        assertThat(UserService.getAllRequests()).isNull();
    }

    @Test
    @DisplayName("trainer request successfully added to database && you can't request a trainer that you already requested")
    void testSendTrainerRequest(FxRobot robot) throws SQLException, WrongPassword, UsernameNotFound {
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#loginButton");
        robot.clickOn("#requestTrainerButton");
        robot.clickOn("#trainersList");
        robot.clickOn("#sendRequestButton");

        assertThat(UserService.getAllRequests()).size().isNotNull();
        TrainerRequests trainer_request = UserService.getAllRequests().get(0);
        assertThat(trainer_request.getClient_username()).isEqualTo("user");
        assertThat(robot.lookup("#requestMessage").queryLabeled()).hasText("Request sent!");

        robot.clickOn("#trainersList");
        robot.clickOn("#sendRequestButton");
        assertThat(UserService.getAllRequests()).size().isEqualTo(1);
        assertThat(robot.lookup("#requestMessage").queryLabeled()).hasText(
                String.format("You already sent a request to this trainer")
        );

    }
}
