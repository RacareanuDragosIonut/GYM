package org.loose.fis.sre.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.model.GymMembership;
import org.loose.fis.sre.model.Trainer;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RequestGymMembershipController implements Initializable {

    GymMembership membership;

    @FXML
    private Button backButton;

    @FXML
    private Label membershipDetails;

    @FXML
    private ListView<String> membershipsList;

    @FXML
    private Label requestMessage;

    @FXML
    private Button sendRequestButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM memberships";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()) {
                String listOut = queryOutput.getString("membership_type");
                membershipsList.getItems().add(listOut);
            }

            queryOutput = statement.executeQuery(connectQuery);
            ResultSet finalQueryOutput = queryOutput;
            membershipsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                    try {
                        while (finalQueryOutput.next()) {
                            String membership_type = finalQueryOutput.getString("membership_type");
                            String hours = finalQueryOutput.getString("hours");
                            String price = finalQueryOutput.getString("price");
                            membership = new GymMembership(membership_type, hours, price);
                            if (membership.getMembership_type().equals(membershipsList.getSelectionModel().getSelectedItem())) {
                                String details = hours + "\nPrice: " + price;
                                membershipDetails.setText(details);
                                break;
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
