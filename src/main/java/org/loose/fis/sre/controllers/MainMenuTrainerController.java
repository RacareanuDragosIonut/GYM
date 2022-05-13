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
        Stage manageclassesStage = new Stage();
        manageclassesStage.setTitle("Manage Classes");
        manageclassesStage.setScene(new Scene(root, 600, 400));
        manageclassesStage.show();
    }
    @FXML
    public void approveclientrequestbuttononaction() throws IOException{
        Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("approveclientrequest.fxml"));
        Stage approveclientrequestStage=new Stage();
        approveclientrequestStage.setTitle("Approve Client Request");
        approveclientrequestStage.setScene(new Scene(root,600,400));
        approveclientrequestStage.show();
    }
    @FXML
    public void viewclienthistorybuttononaction() throws IOException{
        Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("viewclienthistory.fxml"));
        Stage viewclienthistoryStage=new Stage();
        viewclienthistoryStage.setTitle("View Client History");
        viewclienthistoryStage.setScene(new Scene(root,600,400));
        viewclienthistoryStage.show();
    }
    @FXML
    public void viewclassestodobuttononaction() throws IOException{
        Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("viewclassestodo.fxml"));
        Stage viewclassestodoStage=new Stage();
        viewclassestodoStage.setTitle("View Classes to Do");
        viewclassestodoStage.setScene(new Scene(root,600,400));
        viewclassestodoStage.show();
    }
    @FXML
    public void approvemembershiprequestbuttononaction()throws IOException{
        Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("approvemembershiprequest.fxml"));
        Stage approvemembershiprequestStage=new Stage();
        approvemembershiprequestStage.setTitle("Approve Membership Request");
        approvemembershiprequestStage.setScene(new Scene(root,600,400));
        approvemembershiprequestStage.show();
    }
}
