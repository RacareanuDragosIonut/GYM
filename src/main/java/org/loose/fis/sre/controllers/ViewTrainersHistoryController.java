package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.loose.fis.sre.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewTrainersHistoryController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private ListView<String> history;

    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM trainerrequests";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()) {
                if(LoginController.user.getUsername().equals(queryOutput.getString("client_username"))) {
                    String listOut = queryOutput.getString("trainer");
                    history.getItems().add(listOut);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}