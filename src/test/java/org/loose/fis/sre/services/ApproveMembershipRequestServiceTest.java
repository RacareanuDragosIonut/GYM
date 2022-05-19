package org.loose.fis.sre.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.loose.fis.sre.DatabaseConnection;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

import org.loose.fis.sre.exceptions.ClientAlreadyApproved;
import org.loose.fis.sre.exceptions.Nospacesavailable;
import org.loose.fis.sre.model.Client;
import org.loose.fis.sre.model.MembershipModel;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.TooYoung;
public class ApproveMembershipRequestServiceTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root", "douazecisiunu2121");
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM memberships_approved");
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each");
    }

    @Test
    @DisplayName("Approved Membership Table is empty")
    void testTableisInitialisedandNoApprovedMembershipisPersisted() {
        assertThat(ApproveMembershipRequestService.getAllApprovedMemberships()).isNull();
    }

    @Test
    @DisplayName("Approved Membership is successfully added to the table")
    void testApprovedMembershipisAddedToTheTable() throws SQLException, TooYoung {
        ApproveMembershipRequestService.approvemembership("Good Morning Membership", "Racareanu", "20");
        assertThat(ApproveMembershipRequestService.getAllApprovedMemberships()).size().isEqualTo(1);
        MembershipModel membership = ApproveMembershipRequestService.getAllApprovedMemberships().get(0);
        assertThat(membership).isNotNull();
        assertThat(membership.getmembership_type()).isEqualTo("Good Morning Membership");
        assertThat(membership.getclient_name()).isEqualTo("Racareanu");
        assertThat(membership.getclient_age()).isEqualTo("20");

    }

    @Test
    @DisplayName("Client is under 15 years old and he chose a membership different than the Pool Membership so his membership request can't be approved")
    void testTooYoungException() throws SQLException, TooYoung {
        assertThrows(TooYoung.class,()->{
            ApproveMembershipRequestService.approvemembership("Good Morning Membership", "Predescu", "14");
        });

    }

    @Test
    @DisplayName("Client is under 15 years old but he chose Pool Membership so his membership request will be approved")
    void testApprovedMembershipisAddedToTheTableSpecialSituation() throws SQLException, TooYoung {
        ApproveMembershipRequestService.approvemembership("Pool Membership", "Popescu", "14");
        assertThat(ApproveMembershipRequestService.getAllApprovedMemberships()).size().isEqualTo(1);
        MembershipModel membership = ApproveMembershipRequestService.getAllApprovedMemberships().get(0);
        assertThat(membership).isNotNull();
        assertThat(membership.getmembership_type()).isEqualTo("Pool Membership");
        assertThat(membership.getclient_name()).isEqualTo("Popescu");
        assertThat(membership.getclient_age()).isEqualTo("14");
    }
}
