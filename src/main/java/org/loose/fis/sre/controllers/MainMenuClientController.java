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

    public void requestTrainerButtonOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("requesttrainer.fxml"));
        Stage requestTrainerStage = new Stage();
        requestTrainerStage.setTitle("Request a trainer");
        requestTrainerStage.setScene(new Scene(root, 520, 400));
        requestTrainerStage.show();
    }

    public void viewTrainersHistoryButtonOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("viewtrainershistory.fxml"));
        Stage trainersHistoryStage = new Stage();
        trainersHistoryStage.setTitle("View Trainers History");
        trainersHistoryStage.setScene(new Scene(root, 520, 400));
        trainersHistoryStage.show();
    }

    public void requestMembershipButtonOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("requestmembership.fxml"));
        Stage requestMembershipStage = new Stage();
        requestMembershipStage.setTitle("Request a gym membership");
        requestMembershipStage.setScene(new Scene(root, 520, 400));
        requestMembershipStage.show();
    }
}
