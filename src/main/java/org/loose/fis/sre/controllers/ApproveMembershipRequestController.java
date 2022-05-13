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
public class ApproveMembershipRequestController implements Initializable {
    @FXML
    private Button approvemembershiprequestbutton;
    @FXML
    private Button backbuttonapprovemembershiprequest;
    @FXML
    private ListView<String> membershiprequestslist;
    @FXML
    private Label approvemembershiprequestmessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = "SELECT * FROM membership_requests";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {
                String membership_type = queryOutput.getString("membership_type");
                String client_name= queryOutput.getString("client_name");
                String client_age=queryOutput.getString("client_age");
                String listOut = membership_type + "-" + client_name + "-"+client_age;
                membershiprequestslist.getItems().add(listOut);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void backbuttonapprovemembershiprequestonaction()throws IOException{
        Stage stage = (Stage) backbuttonapprovemembershiprequest.getScene().getWindow();
        stage.close();
    }
    public void approvemembershiprequestbuttononaction(){

    }
}
