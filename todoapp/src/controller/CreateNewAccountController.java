package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CreateNewAccountController {
    public PasswordField txtPassword;
    public PasswordField txtConfirmPassword;
    public Label lblPasswordNotMatched01;
    public Label lblPasswordNotMatched02;
    public TextField txtUserName;
    public TextField txtEmail;
    public Button btnRegister;
    public Label lblID;
    public AnchorPane root;


    //7.label deka page eka load weddima newei penne error ekk awama wtrai e nsa label dek page eka load weddi penne nathi
    //wenna hadamu. load weddima deyak kargnn oni nsa initialize method eka use krnnoni

    public void initialize(){
        //8. load weddima penna oni nathi nisa visibility eka false krmu java wala implement krl tyena method ekk use krn
        //lblPasswordNotMatched01.setVisible(false);
        //lblPasswordNotMatched02.setVisible(false);
        //14. uda tyen code dek comment kra bczz method ekk hdl boiler plate code adukrgnn plwn nsa. dn e method ek cll krmu
        //false thamai ywnn oni method ekt
        setVisibility(false);

        //50. mekedi mulinm load weddi textfield tika diasable krmu
        //txtUserName.setDisable(true);
        //txtEmail.setDisable(true);
        //txtPassword.setDisable(true);
        //txtConfirmPassword.setDisable(true);
        //btnRegister.setDisable(true);

        //53. uda code eka dapu method ek cll krmu
        setDissableCommon(true);
    }

    public void txtConfirmPasswordOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        //11. meka mulinm wage kerenn oni deyk register method eka cll krmu
        register();
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        //12.register method ek cll krmu. mekt mulinm wage kerenna oni deyk
        register();
    }

    //1. btn click eke ha confirm paswrd ekedi enter krm kiyn dekedim meka validate wenn oni nsa wenma method ekk liyanawa
    public void register() throws SQLException, IOException {
        //2. mekedi mulinm text box wala tyen value aran refernce ekkt dagnwa
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        //3.dn match krmu. match krnn java walama tyen method ekk use krnw. ehem method ekk use krn plwn mokd me string
        // hadnne double qutation athule newei nsa string pool eke newei me variable hdenne. string pool eke hadunanm e string equalda blnn ba
        if(password.equals(confirmPassword)){
            //4.true unoth color eka aya transparen krnw
            //txtPassword.setStyle("-fx-border-color: transparent");
            //txtConfirmPassword.setStyle("-fx-border-color: transparent");
            //19.   18 kiypu hethuwamai
            setBorderColor("transparent");

            //10. label deka hide karmu aya hari nsa
            //lblPasswordNotMatched01.setVisible(false);
            //lblPasswordNotMatched02.setVisible(false);
            //15. 14kiyapu wistremai
            setVisibility(false);

            //102. ui eken values tik methnt gmu
            String id= lblID.getText();
            String userName=txtUserName.getText();
            String email = txtEmail.getText();
            //103.passwrd ek klin gtt nsa mthndi gnn oni na


            //100 db ekt data ywnn mulinm db connction ek hdgmu
            Connection connection=DBConnection.getInstance().getConnection();
            //101sql query ek liymu. insert krn ekk nisa sreate statemnt ek nathuw preparestatement thmai use krnne
            PreparedStatement preparedStatement= connection.prepareStatement("insert into user values (?,?,?,?)");
            //104. sql injection kru. that mean ? ta ui eken pass krgtt value tika set krmu
            preparedStatement.setObject(1,id); //palweni? ta id kiyn eke value ek assign wenn
            preparedStatement.setObject(2,userName);
            preparedStatement.setObject(3,email);
            preparedStatement.setObject(4,confirmPassword);
             //105. execute krmu eken en value ek int ekkt assgn krgmu
            int i= preparedStatement.executeUpdate();

            //106. hariyt db ekt add unanm successful alert ekk damu
            if (i != 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"success....");
                alert.showAndWait();

                //107successfulnm login page ek redirect krmu
                Parent parent= FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
                Scene scene =new Scene(parent);
                //stage ekk hdgmu ekt anchorpane ekt root kiyl id ekk dagen emu
                Stage primaryStage=(Stage) root.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Login TO - Do");
                primaryStage.centerOnScreen();


            }else{
                Alert alert= new Alert(Alert.AlertType.ERROR,"Error");
                alert.showAndWait();
            }



        }else{
            //5. false unoth color eka red krnw
           //txtPassword.setStyle("-fx-border-color: red");
            //txtConfirmPassword.setStyle("-fx-border-color: red");
            //18. uda code dekm method ekkt dapu nsa method ek cll krmu adala pata deelaa
            setBorderColor("red");  //string ekk nsa "" dala yawann


            //9.label deka visible krmu
            //lblPasswordNotMatched01.setVisible(true);
            //lblPasswordNotMatched02.setVisible(true);
            //16. 14 kiypu reason ekmai
            setVisibility(true);


            //6.confirm passwrd ekt focus ek hdnw
            txtConfirmPassword.requestFocus();


        }



    }

    //13.     lblPasswordNotMatched01.setVisible(false);
    //        lblPasswordNotMatched02.setVisible(false);
    //   me code hamathanama tyen nisa ekatath method ekk hadamu
    public void setVisibility(boolean isVisible){
        lblPasswordNotMatched01.setVisible(isVisible);
        lblPasswordNotMatched02.setVisible(isVisible);

    }


    //17. color ekth hamathanama ekma code eka nisa ektth method ekk hdmu boiler plates code adukrgnn
    public void setBorderColor(String color){
        txtPassword.setStyle("-fx-border-color: " +color);  //color ek stingwalata harwagnn + ekk dala conatenate krgnnw
        txtConfirmPassword.setStyle("-fx-border-color: " +color);
    }

    public void btnAddNewUserOnAction(ActionEvent actionEvent) {
        //51. disable krpu tik mekedi enabble krmu ekata true false krmu
        //txtUserName.setDisable(false);
        //txtEmail.setDisable(false);
        //txtPassword.setDisable(false);
        //txtConfirmPassword.setDisable(false);
        //btnRegister.setDisable(false);

        setDissableCommon(false);
        txtUserName.requestFocus();

        Connection connection = DBConnection.getInstance().getConnection();
        //System.out.println(connection);
        //74 sout ek comment ekk kra bcz s out ek dmme connection ek hryt hdgttd kiyl blnn wtrai. dn id eka generate wen methode
        //ek cll krmu
        autoGenerateID();
    }

    //52. boilar plate code adukrnn e disable krpu ek wenma method ekk hdmu
    public void setDissableCommon(boolean isDissable){
        txtUserName.setDisable(isDissable);
        txtEmail.setDisable(isDissable);
        txtPassword.setDisable(isDissable);
        txtConfirmPassword.setDisable(isDissable);
        btnRegister.setDisable(isDissable);
    }



    //73. mekedi id ek generate krgnn wenm methode ekk hdgmu
    public void autoGenerateID(){
        //75 mulinmdatabae connection ek hdgmu
        Connection connection = DBConnection.getInstance().getConnection();
        //76 dn meke idal sqql query ekk execute krwnn statement ekk create krgmu
        //mekedi error ekk pennai try catch ekk dgen handle krgnn ek

        try {
            Statement statement = connection.createStatement();
            //77. dn sql query ek run krwmu eken en resut ekth gmu
            ResultSet resultSet= statement.executeQuery("select id from user order by id desc limit 1");
            //78. resultset eke result ekk tyed blmu
            boolean isExist = resultSet.next();
            //79. resultset eke result ekk tyenm e id ek gmu. moko ektne ekk ekthu krl ilga kenage id ek hdgnne
            if(isExist){
                //80. id ek string ekk wdht arn variable ekkt dgnnw
                String oldID=resultSet.getString(1);
                //81.u ek nathuw integers wtrk gmu e id ekn
                oldID = oldID.substring(1,oldID.length());
                //82. dn id ek integer krgmu
               int intID= Integer.parseInt(oldID);
               //83.id ekt ekk ekthu krl new userge id ek hdgmu
                intID=intID+1;
                //84. algorithm ek liymu
                if(intID<10){
                    lblID.setText("U00"+intID);
                }else if(intID<100){
                    lblID.setText("U0"+intID);
                }else {
                    lblID.setText("u"+intID);
                }

            }
            //85 mek mekt adala if ek liyddim liynn. user knk table eke nttn ekiynne db eke user table ekt dana mulma userwanm me de wenn.
            else{
                lblID.setText("U001");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
