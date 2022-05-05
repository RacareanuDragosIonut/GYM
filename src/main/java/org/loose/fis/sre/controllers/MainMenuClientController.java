package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuClientController {
    public void makeanappointmentButtonOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("makeanappointment.fxml"));
        Stage appointmentStage = new Stage();
        appointmentStage.setTitle("Make an appointment");
        appointmentStage.setScene(new Scene(root, 520, 400));
        appointmentStage.show();
    }
}
