package org.loose.fis.sre.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.model.Trainer;

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

    @FXML
    private Label trainerDetails;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM trainers";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()) {
                String listOut = queryOutput.getString("fullname");
                trainersList.getItems().add(listOut);
            }
            queryOutput = statement.executeQuery(connectQuery);
            ResultSet finalQueryOutput = queryOutput;
            trainersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                    try {
                        while (finalQueryOutput.next()) {
                            String fullname = finalQueryOutput.getString("fullname");
                            String type_of_training = finalQueryOutput.getString("type_of_training");
                            String price = finalQueryOutput.getString("price");
                            Trainer trainer = new Trainer(fullname, type_of_training, price);
                            if (trainer.getFullname().equals(trainersList.getSelectionModel().getSelectedItem())) {
                                String details = "Type of training: " + type_of_training + "\nPrice: " + price;
                                trainerDetails.setText(details);
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
