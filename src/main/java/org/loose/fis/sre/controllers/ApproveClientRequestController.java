package org.loose.fis.sre.controllers;
import javafx.fxml.Initializable;
import org.loose.fis.sre.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;
public class ApproveClientRequestController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM clients_requests";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}