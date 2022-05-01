package org.loose.fis.sre.controllers;

import com.sun.javafx.charts.Legend;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.loose.fis.sre.services.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox role;
    @FXML
    private Button backButton;
    @FXML
    private Label loginMessage;
    @FXML

    public void initialize() {
        role.getItems().addAll("Client", "Trainer");
        role.setValue("");
    }

    public void loginButtonOnAction() {
        try {
            UserService.loginUser(usernameField.getText(), passwordField.getText(), (String) role.getValue());
            loginMessage.setText("Login successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backButtonOnAction() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
