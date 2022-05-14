package org.loose.fis.sre.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.loose.fis.sre.DatabaseConnection;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.IOException;
public class ViewClassestoDoController implements Initializable {
    @FXML
    private ListView<String> viewclassestodolist;
    @FXML
    private Button backbuttonviewclassestodo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = "SELECT * FROM classes";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {
                String type=queryOutput.getString("type");
                String day=queryOutput.getString("day");
                String hour=queryOutput.getString("hour");
                String listout=type+" "+day+" at "+hour;
                viewclassestodolist.getItems().add(listout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void backbuttonviewclassestodoonaction()throws IOException{
        Stage stage = (Stage) backbuttonviewclassestodo.getScene().getWindow();
        stage.close();
    }
}
