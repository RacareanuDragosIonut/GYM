package org.loose.fis.sre.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.loose.fis.sre.DatabaseConnection;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.IOException;
import org.loose.fis.sre.services.ApproveClientRequestService;
public class ViewClientHistoryController implements Initializable {
    @FXML
    private ListView<String> viewclienthistorylist;
    @FXML
    private Button backbuttonviewclienthistory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM clients";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {

                viewclienthistorylist.getItems().add(queryOutput.getString("client_information"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void backbuttonviewclienthistoryonaction()throws IOException{
        Stage stage = (Stage) backbuttonviewclienthistory.getScene().getWindow();
        stage.close();
    }
}
