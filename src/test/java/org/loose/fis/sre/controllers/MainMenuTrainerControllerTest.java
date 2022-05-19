package org.loose.fis.sre.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@ExtendWith(ApplicationExtension.class)
public class MainMenuTrainerControllerTest {
    public static final String USERNAME = "user1";
    public static final String PASSWORD = "password1";
    public static final String FIRSTNAME = "password1";
    public static final String LASTNAME = "password1";
    public static final String AGE = "password1";
    public static final String PHONENUMBER = "password1";
    public static final String ROLE = "Trainer";
    @BeforeEach
    void setUp() throws SQLException, UsernameAlreadyExistsException {
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
    }
    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }
    @Test
    public void testMenu(FxRobot robot){
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#loginButton");

        robot.clickOn("#manageclassesbutton");
        robot.clickOn("#backbutton");

        robot.clickOn("#viewclassestodobutton");
        robot.clickOn("#backbuttonviewclassestodo");

        robot.clickOn("#viewclienthistorybutton");
        robot.clickOn("#backbuttonviewclienthistory");

        robot.clickOn("#approveclientrequestbutton");
        robot.clickOn("#backbuttonclientrequest");

        robot.clickOn("#approvemembershiprequest");
        robot.clickOn("#backbuttonapprovemembershiprequest");
    }

}
