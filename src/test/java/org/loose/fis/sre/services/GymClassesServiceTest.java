package org.loose.fis.sre.services;

import org.junit.jupiter.api.*;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.AppointmentAlreadyMade;
import org.loose.fis.sre.exceptions.ClassFull;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.Appointment;
import org.loose.fis.sre.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GymClassesServiceTest {
    private static final String CLIENT = "client";
    private static final String GYMCLASS = "gymclass";

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }

    @BeforeEach
    void setUp() throws Exception {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root", "AnisiaRosu12");
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM appointments");
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each");
    }

    @Test
    @DisplayName("Database is initialized, and there are no appointments in the table")
    void testTableIsInitializedAndNoAppointmentIsPersisted() {
        assertThat(GymClassesService.getAllAppointments()).isNull();
    }

    @Test
    @DisplayName("Appointment is successfully persisted to Database")
    void testAppointmentIsAddedToDatabase() throws SQLException, ClassFull, AppointmentAlreadyMade {
        GymClassesService.addAppointment(CLIENT, GYMCLASS);
        assertThat(GymClassesService.getAllAppointments()).isNotEmpty();
        assertThat(GymClassesService.getAllAppointments()).size().isEqualTo(1);
        Appointment appointment = GymClassesService.getAllAppointments().get(0);
        assertThat(appointment).isNotNull();
        assertThat(appointment.getClass_name()).isEqualTo(GYMCLASS);
        assertThat(appointment.getClient_username()).isEqualTo(CLIENT);
    }

    @Test
    @DisplayName("An appointment from the same user can not be added twice")
    void testAppointmentAlreadyMade() {
        assertThrows(AppointmentAlreadyMade.class, () -> {
            GymClassesService.addAppointment(CLIENT, GYMCLASS);
            GymClassesService.addAppointment(CLIENT, GYMCLASS);
        });
    }

    @Test
    @DisplayName("You can not make an appointment to a specific class if there are already 30 appointments made to that class")
    void testClassFull() throws ClassFull, AppointmentAlreadyMade, SQLException {
        int i;
        String XCLIENT;
        for(i = 1; i <= 30; i++) {
            XCLIENT = CLIENT + i;
            GymClassesService.addAppointment(XCLIENT, GYMCLASS);
        }
        assertThrows(ClassFull.class, () -> GymClassesService.addAppointment(CLIENT, GYMCLASS));
    }
}