package org.loose.fis.sre.services;

import java.sql.*;
import java.util.*;

import org.loose.fis.sre.exceptions.Nospacesavailable;
import org.loose.fis.sre.model.Client;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.ClientAlreadyApproved;


public class ApproveClientRequestService {
    private static Statement statement;
    private static PreparedStatement preparedStatement = null;
    private static DatabaseConnection connectNow = new DatabaseConnection();
    private static Connection connection = connectNow.getConnection();

    private static final String DATABASE_URL = "jdbc:mysql://localhost/gymapp";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "douazecisiunu2121";
    private static final String INSERT_QUERY = "INSERT INTO clients(client_information) VALUES (?)";
    static ResultSet queryOutput, resultSet;
    private static String connectQuery = "SELECT * FROM clients";

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            queryOutput = statement.executeQuery(connectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Client> getAllClients() {
        try {
            ArrayList<Client> clients = new ArrayList<>();
            statement.execute("SELECT * FROM clients");
            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next())
                clients.add(new Client(resultSet.getString("client_information")));

            return clients.size() == 0 ? null : clients;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void checkClientAlreadyApproved(Client client) throws SQLException, ClientAlreadyApproved {
        ArrayList<Client> clients = getAllClients();
        if(clients != null) {
            Iterator<Client> it =  clients.iterator();
            while(it.hasNext()) {
                Client aux = it.next();
                if(aux.getclient_information().equals(client.getclient_information()))
                    throw new ClientAlreadyApproved();
            }
        }
    }
    public static void approveclient(String client_information) throws Nospacesavailable, SQLException,ClientAlreadyApproved {
        Client client = new Client(client_information);
        checkClientAlreadyApproved(client);
        checkNumberOfParticipants(client);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, client_information);
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }



    public static void checkNumberOfParticipants(Client client) throws Nospacesavailable, SQLException {
        preparedStatement = connection.prepareStatement(connectQuery);

        resultSet = preparedStatement.executeQuery();
        int s = 0;
        while (resultSet.next())
                s = s + 1;

        if(s>=10)
            throw new Nospacesavailable();
    }
}

