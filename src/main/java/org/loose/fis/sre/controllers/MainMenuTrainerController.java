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


import java.io.IOException;


public class MainMenuTrainerController {

    @FXML
    public void manageclassesbuttononaction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("manageclasses.fxml"));

    }
}
