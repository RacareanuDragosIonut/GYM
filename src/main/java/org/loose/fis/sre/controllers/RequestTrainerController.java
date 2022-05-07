package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.loose.fis.sre.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;

public class RequestTrainerController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Label requestMessage;

    @FXML
    private Button sendRequestButton;

    @FXML
    private ListView<String> trainersList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM trainers";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()) {
                String fullname = queryOutput.getString("fullname");
                //String type_of_training = queryOutput.getString("type_of_training");
                //String price = queryOutput.getString("price");
                String listOut = fullname; //+ ": " + day + " la ora " + hour;
                trainersList.getItems().add(listOut);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
