package org.loose.fis.sre.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.exceptions.WrongPassword;
import org.loose.fis.sre.model.Appointment;
import org.loose.fis.sre.model.TrainerRequests;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.GymClassesService;
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
public class MakeAnAppointmentControllerTest {
    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";
    public static final String FIRSTNAME = "password";
    public static final String LASTNAME = "password";
    public static final String AGE = "password";
    public static final String PHONENUMBER = "password";
    public static final String ROLE = "Client";

    @BeforeEach
    void setUp() throws SQLException, UsernameAlreadyExistsException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root", "AnisiaRosu12");
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM appointments");
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
        primaryStage.setTitle("Make An Appointment");
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }

    @Test
    @DisplayName("appointments table is empty")
    void testTableIsEmpty() {
        assertThat(GymClassesService.getAllAppointments()).isNull();
    }

    @Test
    @DisplayName("appointment successfully added to database && you can't make an appointment to the same class twice")
    void testMakeAnAppointment(FxRobot robot) throws SQLException, WrongPassword, UsernameNotFound {
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(ROLE);
        robot.clickOn("#loginButton");
        robot.clickOn("#makeanappointmentButton");
        robot.clickOn("#classesList");
        robot.clickOn("#makeanappointmentButton");

        assertThat(GymClassesService.getAllAppointments()).size().isNotNull();
        Appointment appointment = GymClassesService.getAllAppointments().get(0);
        Assertions.assertThat(appointment.getClient_username()).isEqualTo("user");
        assertThat(robot.lookup("#hours").queryLabeled()).hasText("Successful appointment!");

        robot.clickOn("#classesList");
        robot.clickOn("#makeanappointmentButton");
        assertThat(GymClassesService.getAllAppointments()).size().isEqualTo(1);
        assertThat(robot.lookup("#hours").queryLabeled()).hasText(
                String.format("You already made an appointment to this class!")
        );

    }
}
