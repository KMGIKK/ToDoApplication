package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane root;
    public TextField txtUserName;
    public TextField txtPassword;

    //750. to do form controller clz ekt use krnn variable dekk hdgmu static vlin. ethkot clz eke nmin access krnn plwnne obj hdnne nthuw
    public static String loginUserName;
    public static String loginUserID;

    //751. dn me dekt values set krmu result set ekn argen eka login methode ek athule liymu

    public void lblCreateNewAccountOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        //create new page eka load wenn adala implementation eka
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/CreateNewAccountForm.fxml"));
        Scene scene = new Scene(parent);
        //stage ekt knk hdgen eyata window ek casting krl gnnw window ekk wdhata
        Stage primaryStage = (Stage )root.getScene().getWindow();
        //primarysteage ekt scene ek set krmu
         primaryStage.setScene(scene);
         primaryStage.setTitle("Create New Account");
         primaryStage.centerOnScreen();
         //meka show krnn oni na moko stage eka show wela tyenne muldima


    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        login();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        login();
    }

    public void login(){
        //1. mulinm text tika gmu ui eke idl
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        //2. dn db eke connection ek hdgmu
       Connection connection = DBConnection.getInstance().getConnection();

       //3. dn db eke query ek run krmu
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("select * from user where user_name=? and password =?");
            //4. dn ? thn wlt variable set krmu
            preparedStatement.setObject(1,userName);
            preparedStatement.setObject(2,password);

            ResultSet resultSet= preparedStatement.executeQuery();

            //5 result set eke mna hri tyenm passwrd username hri ensa anth page ek load krnw
             if(resultSet.next()){

                 //752. ara uda hdgtta variable dekata value set krmu
                 loginUserName = resultSet.getString(2);
                 loginUserID = resultSet.getString(1); //753 dn me variable deka todoform controoleer ekedi use krmu

                 //5 ta adala kotasa
                 Parent parent = FXMLLoader.load(this.getClass().getResource("../view/ToDoForm.fxml"));
                 Scene scene= new Scene(parent);
                 Stage primaryStage = (Stage)root.getScene().getWindow();
                 primaryStage.setScene(scene);
                 primaryStage.centerOnScreen();


             }else{
                 //6 username passwrd saman une nttn me tik wenw
                 Alert alert = new Alert(Alert.AlertType.ERROR,"invalid user name or password");
                 alert.showAndWait();
                 txtUserName.clear();
                 txtPassword.clear();
                 txtUserName.requestFocus();
             }


        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
