package org.loose.fis.sre.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.services.FileSystemService;
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
public class LoginControllerTest {
    public static final String USERNAMEC = "anisia12";
    public static final String PASSWORDC = "AnisiaRosu12";
    public static final String ROLEC = "Client";

    public static final String USERNAMET = "trainer";
    public static final String PASSWORDT = "trainer";
    public static final String ROLET = "Trainer";

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root", "AnisiaRosu12");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_account");
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
    void testLoginClient(FxRobot robot) {
        robot.clickOn("#usernameField");
        robot.write(USERNAMEC);
        robot.clickOn("#passwordField");
        robot.write(PASSWORDC);
        robot.clickOn("#role");
        robot.clickOn(ROLEC);
        robot.clickOn("#loginButton");

        assertThat(robot.lookup("#loginMessage").queryLabeled()).hasText("Login successfully!");
    }

    @Test
    void testLoginTrainer(FxRobot robot) {
        robot.clickOn("#usernameField");
        robot.write(USERNAMET);
        robot.clickOn("#passwordField");
        robot.write(PASSWORDT);
        robot.clickOn("#role");
        robot.clickOn(ROLET);
        robot.clickOn("#loginButton");

        assertThat(robot.lookup("#loginMessage").queryLabeled()).hasText("Login successfully!");
    }
}
