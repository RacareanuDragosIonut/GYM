package org.loose.fis.sre.services;
import java.sql.*;
import java.util.*;

import org.loose.fis.sre.exceptions.Nospacesavailable;
import org.loose.fis.sre.model.Client;
import org.loose.fis.sre.model.MembershipModel;
import org.loose.fis.sre.DatabaseConnection;
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

}
