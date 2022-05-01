package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.UserService;

import java.sql.SQLException;


public class RegistrationController {

    public Button registerButton;
    @FXML
    private Label registrationMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField phonenumberField;
    @FXML
    private ChoiceBox role;
    @FXML
    private Button loginButton;

    public void loginButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    public void initialize() {
        role.getItems().addAll("Client", "Trainer");
        role.setValue("");
    }


    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), firstnameField.getText(), lastnameField.getText(),  ageField.getText(), phonenumberField.getText(), (String) role.getValue());
            registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException | SQLException e) {
            registrationMessage.setText(e.getMessage());
        }
    }
}

