package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.RequestAlreadySent;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class ViewTrainersHistoryControllerTest {
    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";
    public static final String FIRSTNAME = "password";
    public static final String LASTNAME = "password";
    public static final String AGE = "password";
    public static final String PHONENUMBER = "password";
    public static final String ROLE = "Client";
    public static final String TRAINER = "Trainer";

    @BeforeEach
    void setUp() throws SQLException, UsernameAlreadyExistsException, RequestAlreadySent {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root", "AnisiaRosu12");

        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM user_account");
        System.out.println(preparedStatement2);
        preparedStatement2.executeUpdate();

        UserService.addUser(USERNAME, PASSWORD, FIRSTNAME, LASTNAME, AGE, PHONENUMBER, ROLE);
        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM user_account");
        System.out.println(preparedStatement1);
        preparedStatement1.executeQuery();

        PreparedStatement preparedStatement3 = connection.prepareStatement("DELETE FROM trainerrequests");
        System.out.println(preparedStatement3);
        preparedStatement3.executeUpdate();

        UserService.addRequest(USERNAME, TRAINER);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM trainerrequests");
        System.out.println(preparedStatement);
        preparedStatement.executeQuery();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }

    @Test
    @DisplayName("view trainer history which contains only a trainer added in the setup method")
    void testViewTrainerHistory(FxRobot robot){
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#loginButton");
        robot.clickOn("#viewTrainersHistoryButton");
        assertThat(UserService.getAllRequests()).size().isNotNull();
        assertThat(UserService.getAllRequests()).size().isEqualTo(1);
    }
}
