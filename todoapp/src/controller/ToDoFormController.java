package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ToDoFormController {
    public Label lblID;
    public Label lblTitle;
    public AnchorPane root;
    public AnchorPane subRoot;

    //754. page eka load weddima wenn oni dewal me injitialize kiyla methode ekk hdala ekedi krmu
    public void initialize(){
        //755. dn login form controoler eke tyen user name ekai id ekai meke variable athult dagmu
        String id = LoginFormController.loginUserID;
        String  userName= LoginFormController.loginUserName;

        //756. dn e variable dek use krl ui eke display krmu
        lblTitle.setText("Hi "+userName+" welcome...");
        lblID.setText(id);

        //762. mulinm load weddi new to do add krna kotasa penne nathi krmu..add new btn ek click krm wtrk pena wdht hdmu
        //anchor pane ek athule textfield ekai bttn ekai dapu nis dekt wen wenm visibility ek ain krnn oni na anchor pane ekt wtrk ain krm athi
        // enisa anchrpane ekt id ekk dagen emu.
        subRoot.setVisible(false);
        //763. add new to do click krm visible krnn oni e tika addtodo ekt on action ekk dala eke asse true krmu mek.


    }
    //757. log out ektth db connection oni nthi nsa e kotasath iwara karn imu. ekt scene builder ekt ghn logout btn ekt on actio ekk dgen emu
    public void btnLogOutOnAction(ActionEvent actionEvent) {
        //758. kelinm log out wenne na. btn ek ebuwm ahnn oni are you sure kiyl. yes dunnoth logot krmu. no dunnth mkth krnn oni na
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to log out?", ButtonType.YES,ButtonType.NO);

        //759yesda nod denne kiyala variable ekkt dagmu. ekat alert.showAndWait ghl altr+enter denn
        Optional<ButtonType> buttonType = alert.showAndWait();

        //760. dn yes dunnoth logout krmu. parent hduwm string ekk path eka en nisa warning karai. e nsa try catch ekk dann
        if(buttonType.get().equals(ButtonType.YES)){
            try {
                //761. yes dunnm login page ek load krmu
                Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) root.getScene().getWindow();// methna error ekk enw. ape anchor pane ekt id ek dann root kiyl ethkot hri
                primaryStage.setScene(scene);
                primaryStage.setTitle("Login Form");
                primaryStage.centerOnScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }


    public void btnAddNewToDoOnAction(ActionEvent actionEvent) {
        ///764. me btn ek click krm anchor pane ek visible krmu
        subRoot.setVisible(true);
    }
}
