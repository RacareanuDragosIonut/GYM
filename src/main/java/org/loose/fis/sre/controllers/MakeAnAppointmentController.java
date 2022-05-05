package org.loose.fis.sre.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

import java.util.ResourceBundle;
import java.net.URL;

public class MakeAnAppointmentController implements Initializable {

    @FXML
    private  ListView<String> classesList;

    @FXML
    private Label hours;

    @FXML
    private Button makeanappointmentButton;

    String[] classes = {"Lower Body Class", "Upper Body Class", "Full Body Class", "Cycling Class"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classesList.getItems().addAll(classes);
        classesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                hours.setText(classesList.getSelectionModel().getSelectedItem());
            }
        });

    }

    public void makeanappointmentButtonOnAction() {

    }
}

