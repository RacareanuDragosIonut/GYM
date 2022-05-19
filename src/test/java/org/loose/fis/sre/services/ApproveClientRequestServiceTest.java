package org.loose.fis.sre.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.loose.fis.sre.DatabaseConnection;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

import org.loose.fis.sre.model.Class;
import org.loose.fis.sre.model.Client;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.ClientAlreadyApproved;
import org.loose.fis.sre.exceptions.Nospacesavailable;
public class ApproveClientRequestServiceTest {
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
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clients");
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each");
    }

    @Test
    @DisplayName("Client Table is empty")
    void testTableisInitialisedandNoClientisPersisted() {
        assertThat(ApproveClientRequestService.getAllClients()).isNull();
    }

    @Test
    @DisplayName("Client information is successfully added to the table")
    void testClientInformationisAddedToTheTable() throws SQLException, ClientAlreadyApproved, Nospacesavailable {
        ApproveClientRequestService.approveclient("Racareanu 20 ani");
        assertThat(ApproveClientRequestService.getAllClients()).size().isEqualTo(1);
        Client client = ApproveClientRequestService.getAllClients().get(0);
        assertThat(client).isNotNull();
        assertThat(client.getclient_information()).isEqualTo("Racareanu 20 ani");

    }
    @Test
    @DisplayName("Client Request can not be approved twice")
    void testClientAlreadyApproved()throws SQLException,ClientAlreadyApproved,Nospacesavailable{
        ApproveClientRequestService.approveclient("Racareanu 20 ani");
        assertThrows(ClientAlreadyApproved.class,()->{
            ApproveClientRequestService.approveclient("Racareanu 20 ani");
        });
    }
    @Test
    @DisplayName("There are no more spaces available for the client request to be approved")
    void testNoSpacesAvailable()throws SQLException,ClientAlreadyApproved,Nospacesavailable{
        assertThat(ApproveClientRequestService.getAllClients()).isNull();
        ApproveClientRequestService.approveclient("Racareanu 20 ani");
        ApproveClientRequestService.approveclient("Raducan 14 ani");
        ApproveClientRequestService.approveclient("Vlad 22 ani");
        ApproveClientRequestService.approveclient("Predoi 25 ani");
        ApproveClientRequestService.approveclient("Dumitru 23 ani");
        ApproveClientRequestService.approveclient("Toporan 28 ani");
        ApproveClientRequestService.approveclient("Nicolae 19 ani");
        ApproveClientRequestService.approveclient("Rosu 25 ani");
        ApproveClientRequestService.approveclient("Grigorie 20 ani");
        ApproveClientRequestService.approveclient("Predescu 18 ani");
        assertThrows(Nospacesavailable.class,()->{
            ApproveClientRequestService.approveclient("Popescu 27 ani");
        });
    }
}
