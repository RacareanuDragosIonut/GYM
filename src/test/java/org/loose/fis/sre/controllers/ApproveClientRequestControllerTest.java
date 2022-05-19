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
import org.loose.fis.sre.model.Client;

import org.loose.fis.sre.services.ApproveClientRequestService;
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
public class ApproveClientRequestControllerTest {
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
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clients");
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
    @DisplayName("clients table is empty")
    void testTableIsEmpty() {
        assertThat(ApproveClientRequestService.getAllClients()).isNull();
    }
    @Test
    @DisplayName("client approved successfully and you can't approve a client request that you already approved")
    void testApproveClientRequest(FxRobot robot) throws SQLException, WrongPassword, UsernameNotFound {
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#loginButton");
        robot.clickOn("#approveclientrequestbutton");
        robot.clickOn("#clientrequestslist");
        robot.clickOn("#approveclientbutton");

        assertThat(ApproveClientRequestService.getAllClients()).size().isEqualTo(1);

        assertThat(robot.lookup("#approveclientrequestmessage").queryLabeled()).hasText("Approved Request");

        robot.clickOn("#clientrequestslist");
        robot.clickOn("#approveclientbutton");
        assertThat(ApproveClientRequestService.getAllClients()).size().isEqualTo(1);
        assertThat(robot.lookup("#approveclientrequestmessage").queryLabeled()).hasText("Client Already Approved");

    }
}
