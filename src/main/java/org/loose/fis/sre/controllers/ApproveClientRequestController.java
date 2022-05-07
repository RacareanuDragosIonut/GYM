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
public class ApproveClientRequestController implements Initializable {
    @FXML
    private Button approveclientbutton;
    @FXML
    private Button backbuttonclientrequest;
    @FXML
    private ListView<String>clientrequestslist;
    @FXML
    private Label approveclientrequestmessage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM clients_requests";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while(queryOutput.next()){
                String name = queryOutput.getString("name");
                String age = queryOutput.getString("age");

                String listOut = name+" "+age+" ani";
                clientrequestslist.getItems().add(listOut);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void backbuttonclientrequestonaction() throws IOException{
        Stage stage = (Stage) backbuttonclientrequest.getScene().getWindow();
        stage.close();
    }
}