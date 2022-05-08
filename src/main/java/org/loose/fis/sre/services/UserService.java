package org.loose.fis.sre.services;

import java.sql.*;
import java.util.*;

import org.loose.fis.sre.exceptions.RequestAlreadySent;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.exceptions.WrongPassword;
import org.loose.fis.sre.model.Trainer;
import org.loose.fis.sre.model.TrainerRequests;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.DatabaseConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {

    private static Statement statement;
    private static PreparedStatement preparedStatement = null;
    private static DatabaseConnection connectNow = new DatabaseConnection();
    private static Connection connection = connectNow.getConnection();

    private static final String DATABASE_URL = "jdbc:mysql://localhost/gymapp";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "AnisiaRosu12";
    private static final String INSERT_QUERY = "INSERT INTO user_account (username, password, firstname, lastname, age, phonenumber, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_QUERY_2 = "INSERT INTO trainerrequests (client_username, trainer) VALUES (?, ?)";


    private static String connectQuery = "SELECT username FROM user_account";

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static ResultSet queryOutput, resultSet;
    static {
        try {
            queryOutput = statement.executeQuery(connectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String username, String password, String firstname, String lastname, String age, String phonenumber, String role) throws UsernameAlreadyExistsException, SQLException {
        User user = new User(username, password, firstname, lastname, age, phonenumber, role);
        checkUserDoesNotAlreadyExist(user);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, firstname);
        preparedStatement.setString(4, lastname);
        preparedStatement.setString(5, age);
        preparedStatement.setString(6, phonenumber);
        preparedStatement.setString(7, role);
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    public static ArrayList<User> getAllUsers() {
        try {
            ArrayList<User> users = new ArrayList<>();
            statement.execute("SELECT * FROM user_account");
            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next())
                users.add(new User( resultSet.getString("username"), resultSet.getString("password"),
                        resultSet.getString("firstname"), resultSet.getString("lastname"),
                        resultSet.getString("age"), resultSet.getString("phonenumber"),
                        resultSet.getString("role")));

            return users.size() == 0 ? null : users;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void checkUserDoesNotAlreadyExist(User user) throws UsernameAlreadyExistsException {
        ArrayList<User> users = getAllUsers();
        /*if(users != null)
            for(var i : users)
                if(user.equals(i)) {
                    throw new UsernameAlreadyExistsException(user.getUsername());
                }*/
        if(users != null) {
            Iterator<User> it =  users.iterator();
            while(it.hasNext()) {
                User aux = it.next();
                if(aux.equals(user))
                    throw new UsernameAlreadyExistsException(user.getUsername());
            }
        }
    }

    public static void loginUser(String username, String password) throws SQLException,  WrongPassword, UsernameNotFound {

        String LOGIN_QUERY = "SELECT * FROM user_account WHERE username = ?";
        preparedStatement = connection.prepareStatement(LOGIN_QUERY);
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            throw new UsernameNotFound(username);

        LOGIN_QUERY = "SELECT * FROM user_account WHERE username = ? AND password = ?;";
        preparedStatement = connection.prepareStatement(LOGIN_QUERY);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            throw new WrongPassword(password);
    }

    public static int verifyRole(String username, String password, String role) {
        User user = new User(username, password, role);
        if(user.getRole().equals("Client"))
            return 1;
        else
            if(user.getRole().equals("Trainer"))
                return 2;
            return 0;
    }

    public static ArrayList<TrainerRequests> getAllRequests() {
        try {
            ArrayList<TrainerRequests> requests = new ArrayList<>();
            statement.execute("SELECT * FROM trainerrequests");
            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next())
                requests.add(new TrainerRequests( resultSet.getString("client_username"), resultSet.getString("trainer")));

            return requests.size() == 0 ? null : requests;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void checkRequestAlreadySent(String client_username, String trainer) throws SQLException, RequestAlreadySent {
        ArrayList<TrainerRequests> requests = getAllRequests();
        if(requests != null) {
            Iterator<TrainerRequests> it =  requests.iterator();
            while(it.hasNext()) {
                TrainerRequests aux = it.next();
                if(aux.getClient_username().equals(client_username)&&aux.getTrainer().equals(trainer))
                    throw new RequestAlreadySent(client_username);
            }
        }
    }

    public static void addRequest(String client_username, String trainer) throws SQLException, RequestAlreadySent {
        checkRequestAlreadySent(client_username,trainer);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        preparedStatement = connection.prepareStatement(INSERT_QUERY_2);
        preparedStatement.setString(1, client_username);
        preparedStatement.setString(2, trainer);
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }


}
