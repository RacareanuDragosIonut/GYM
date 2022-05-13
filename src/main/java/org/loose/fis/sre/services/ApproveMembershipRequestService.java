package org.loose.fis.sre.services;
import java.sql.*;
import java.util.*;

import org.loose.fis.sre.exceptions.Nospacesavailable;
import org.loose.fis.sre.model.Client;
import org.loose.fis.sre.model.MembershipModel;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.TooYoung;
public class ApproveMembershipRequestService {
    private static Statement statement;
    private static PreparedStatement preparedStatement = null;
    private static DatabaseConnection connectNow = new DatabaseConnection();
    private static Connection connection = connectNow.getConnection();

    private static final String DATABASE_URL = "jdbc:mysql://localhost/gymapp";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "douazecisiunu2121";
    private static final String INSERT_QUERY = "INSERT INTO memberships_approved(membership_type,client_name,client_age) VALUES (?,?,?)";
    static ResultSet queryOutput, resultSet;
    private static String connectQuery = "SELECT * FROM memberships_approved";
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
    public static void approvemembership(String membership_type,String client_name,String client_age) throws TooYoung, SQLException {
        MembershipModel membership_request = new MembershipModel(membership_type,client_name,client_age);
        checkifTooYoung(membership_request);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, membership_type);
        preparedStatement.setString(2,client_name);
        preparedStatement.setString(3,client_age);
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }
    public static void checkifTooYoung(MembershipModel membership_request) throws TooYoung,SQLException{
        if(!membership_request.getmembership_type().equals("Pool Membership")&&Integer.valueOf(membership_request.getclient_age())<15)
            throw new TooYoung();
    }

}
