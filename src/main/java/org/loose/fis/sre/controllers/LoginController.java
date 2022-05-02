package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.exceptions.WrongPassword;
import org.loose.fis.sre.services.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    private Stage window1;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox role;
    @FXML
    public Button backButton;
    @FXML
    private Label loginMessage;
    @FXML
    private Button loginButton;

    /*public LoginController(PasswordField passwordField) {
        this.passwordField = passwordField;
    }*/

    @FXML

    public void initialize() {
        role.getItems().addAll("Client", "Trainer");
        role.setValue("");
    }

    public void loginButtonOnAction() {
        try {
            UserService.loginUser(usernameField.getText(), passwordField.getText()/*, (String) role.getValue()*/);
            loginMessage.setText("Login successfully!");
        } catch (WrongPassword | UsernameNotFound | SQLException e ) {
            loginMessage.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void backButtonOnAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
