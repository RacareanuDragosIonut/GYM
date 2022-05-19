package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.MembershipRequests;
import org.loose.fis.sre.model.TrainerRequests;
import org.loose.fis.sre.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

public class UserServiceTest {
    public static final String CLIENT = "Client";
    public static final String THIS_USERNAME_DOESNT_EXIST = "login";
    public static final String WRONG_PASSWORD = "Client1";
    public static final String TRAINER = "Trainer";
    public static final String MEMBERSHIP = "Membership";

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
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user_account");
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();

        PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM trainerrequests");
        preparedStatement1.executeUpdate();

        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM membershiprequests");
        preparedStatement2.executeUpdate();
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each");
    }

    @Test
    @DisplayName("Database is initialized, and there are no users in the table")
    void testTableIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.getAllUsers()).isNull();
    }

    @Test
    @DisplayName("User is successfully persisted to Database")
    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException, SQLException {
        UserService.addUser(CLIENT, CLIENT, CLIENT, CLIENT, CLIENT, CLIENT, CLIENT);
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(CLIENT);
        assertThat(user.getPassword()).isEqualTo(CLIENT);
        assertThat(user.getFirstname()).isEqualTo(CLIENT);
        assertThat(user.getLastname()).isEqualTo(CLIENT);
        assertThat(user.getAge()).isEqualTo(CLIENT);
        assertThat(user.getPhonenumber()).isEqualTo(CLIENT);
        assertThat(user.getRole()).isEqualTo(CLIENT);
    }

    @Test
    @DisplayName("User can not be added twice")
    void testUserCanNotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(CLIENT, CLIENT, CLIENT, CLIENT, CLIENT, CLIENT, CLIENT);
            UserService.addUser(CLIENT, CLIENT, CLIENT, CLIENT, CLIENT, CLIENT, CLIENT);
        });
    }

    @Test
    @DisplayName("An account with this username doesn't exist")
    void testUsernameNotFound() {
        assertThrows(UsernameNotFound.class, () -> {
            UserService.loginUser(THIS_USERNAME_DOESNT_EXIST, CLIENT);
        });
    }

    @Test
    @DisplayName("The password is wrong")
    void testWrongPassword() throws UsernameAlreadyExistsException, SQLException {
        UserService.addUser(CLIENT, CLIENT, CLIENT, CLIENT, CLIENT, CLIENT, CLIENT);
        assertThrows(WrongPassword.class, () -> {
            UserService.loginUser(CLIENT, WRONG_PASSWORD);
        });
    }

    @Test
    @DisplayName("Database is initialized, and there are no trainer requests in the table")
    void testTableIsInitializedAndNoTrainerRequestArePersisted() {
        assertThat(UserService.getAllRequests()).isNull();
    }

    @Test
    @DisplayName("Trainer Request is successfully persisted to Database")
    void testTrainerRequestIsAddedToDatabase() throws SQLException, RequestAlreadySent {
        UserService.addRequest(CLIENT, TRAINER);
        assertThat(UserService.getAllRequests()).isNotEmpty();
        assertThat(UserService.getAllRequests()).size().isEqualTo(1);
        TrainerRequests trainer_request = UserService.getAllRequests().get(0);
        assertThat(trainer_request).isNotNull();
        assertThat(trainer_request.getClient_username()).isEqualTo(CLIENT);
        assertThat(trainer_request.getTrainer()).isEqualTo(TRAINER);
    }

    @Test
    @DisplayName("Trainer Request already sent")
    void testTrainerRequestAlreadySent() {
        assertThrows(RequestAlreadySent.class, () -> {
            UserService.addRequest(CLIENT, TRAINER);
            UserService.addRequest(CLIENT, TRAINER);
        });
    }

    @Test
    @DisplayName("Database is initialized, and there are no membership requests in the table")
    void testTableIsInitializedAndNoMembershipRequestArePersisted() {
        assertThat(UserService.getAllMembershipRequests()).isNull();
    }

    @Test
    @DisplayName("Membership Request is successfully persisted to Database")
    void testMembershipRequestIsAddedToDatabase() throws SQLException, MembershipRequestAlreadySent {
        UserService.addMembershipRequest(CLIENT, MEMBERSHIP);
        assertThat(UserService.getAllMembershipRequests()).isNotEmpty();
        assertThat(UserService.getAllMembershipRequests()).size().isEqualTo(1);
        MembershipRequests membership_request = UserService.getAllMembershipRequests().get(0);
        assertThat(membership_request).isNotNull();
        assertThat(membership_request.getClient_username()).isEqualTo(CLIENT);
        assertThat(membership_request.getMembership_type()).isEqualTo(MEMBERSHIP);
    }

    @Test
    @DisplayName("Membership Request already sent")
    void testMembershipRequestAlreadySent() {
        assertThrows(MembershipRequestAlreadySent.class, () -> {
            UserService.addMembershipRequest(CLIENT, MEMBERSHIP);
            UserService.addMembershipRequest(CLIENT, MEMBERSHIP);
        });
    }
}
