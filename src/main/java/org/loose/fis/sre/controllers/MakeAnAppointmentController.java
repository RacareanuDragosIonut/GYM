package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.exceptions.ClassFull;
import org.loose.fis.sre.services.GymClassesService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;

public class MakeAnAppointmentController implements Initializable {

    @FXML
    private  ListView<String> classesList;

    @FXML
    private Label hours;

    @FXML
    private Button makeanappointmentButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM gym_classes";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while(queryOutput.next()){
                String gym_class = queryOutput.getString("gym_class_name");
                String hour = queryOutput.getString("beggining_hour");
                String day = queryOutput.getString("day");
                String listOut = gym_class + ": " + day + " la ora " + hour;
                classesList.getItems().add(listOut);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*classesList.getItems().addAll(classes);
        classesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                hours.setText(classesList.getSelectionModel().getSelectedItem());
            }
        });*/

    }

    public void makeanappointmentButtonOnAction() throws ClassFull, SQLException {
        try {
            GymClassesService.addAppointment(classesList.getSelectionModel().getSelectedItem());
            hours.setText("Successful appointment!");
        }catch (ClassFull e){
            hours.setText(e.getMessage());
        }

    }
}

