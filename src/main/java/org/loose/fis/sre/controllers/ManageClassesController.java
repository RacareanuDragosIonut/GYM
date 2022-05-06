package org.loose.fis.sre.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.loose.fis.sre.exceptions.Classdoesnotexist;
import org.loose.fis.sre.exceptions.Classwiththatscheduledoesnotexist;
import org.loose.fis.sre.exceptions.DayandHourTaken;

import java.io.IOException;
import java.sql.SQLException;
import javafx.stage.Stage;
import org.loose.fis.sre.services.ClassService;
public class ManageClassesController {
    private Stage window1;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField typefield;
    @FXML
   private TextField dayfield;

    @FXML
    private TextField hourfield;

    @FXML
    private TextField nrclientsfield;
    @FXML
    public Button backbutton;
    @FXML
    private Label addeditcancelmessage;
    @FXML
     public Button addbutton;
    @FXML
    public Button editbutton;
    @FXML
    public Button cancelbutton;
    public void handleaddaction(){
        try{
            ClassService.addClass(typefield.getText(),dayfield.getText(),hourfield.getText(),Integer.parseInt(nrclientsfield.getText()) );
            addeditcancelmessage.setText("Added Class");
        }catch(DayandHourTaken| SQLException e){
            addeditcancelmessage.setText(e.getMessage());
        }
    }
     public void handleeditaction(){
         try{
             ClassService.editClass(typefield.getText(),dayfield.getText(),hourfield.getText(),Integer.parseInt(nrclientsfield.getText()) );
             addeditcancelmessage.setText("Edited Class");
         }catch(Classwiththatscheduledoesnotexist| SQLException e){
             addeditcancelmessage.setText(e.getMessage());
         }
     }
    public void handlecancelaction(){
        try{
            ClassService.cancelClass(typefield.getText(),dayfield.getText(),hourfield.getText(),Integer.parseInt(nrclientsfield.getText()) );
            addeditcancelmessage.setText("Canceled Class");
        }catch(Classdoesnotexist| SQLException e){
            addeditcancelmessage.setText(e.getMessage());
        }
    }
    public void backbuttononaction() throws IOException {
        Stage stage = (Stage) backbutton.getScene().getWindow();
        stage.close();
    }
}
