package org.loose.fis.sre.controllers;

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
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.UserService;

import java.io.IOException;
import java.sql.SQLException;


public class RegistrationController {

    private Stage window1;
    private Scene scene;
    private Parent root;

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
    public Button loginButton;

    /*public void loginButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }*/

    public void initialize() {
        role.getItems().addAll("Client", "Trainer");
        role.setValue("");
    }


    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), firstnameField.getText(), lastnameField.getText(),  ageField.getText(), phonenumberField.getText(), (String) role.getValue());
            registrationMessage.setText("Account created successfully!");
            loginButtonOnAction();
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        }  catch (SQLException e) {
            e.printStackTrace();
            registrationMessage.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginButtonOnAction() throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root, 520, 400));
            loginStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}