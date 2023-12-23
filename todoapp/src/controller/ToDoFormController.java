package controller;

import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tm.ToDoTM;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class ToDoFormController {
    public Label lblID;
    public Label lblTitle;
    public AnchorPane root;
    public AnchorPane subRoot;
    public TextField txtNewToDo;
    public ListView<ToDoTM> listToDo;
    public Button btnDelete;
    public Button btnUpdate;
    public TextField txtSelectedToDo;


    //754. page eka load weddima wenn oni dewal me injitialize kiyla methode ekk hdala ekedi krmu
    public void initialize(){
        //755. dn login form controoler eke tyen user name ekai id ekai meke variable athult dagmu
        String id = LoginFormController.loginUserID;
        String  userName= LoginFormController.loginUserName;
        loadList();


        //756. dn e variable dek use krl ui eke display krmu
        lblTitle.setText("Hi "+userName+" welcome...");
        lblID.setText(id);

        //762. mulinm load weddi new to do add krna kotasa penne nathi krmu..add new btn ek click krm wtrk pena wdht hdmu
        //anchor pane ek athule textfield ekai bttn ekai dapu nis dekt wen wenm visibility ek ain krnn oni na anchor pane ekt wtrk ain krm athi
        // enisa anchrpane ekt id ekk dagen emu.
        subRoot.setVisible(false);
        //763. add new to do click krm visible krnn oni e tika addtodo ekt on action ekk dala eke asse true krmu mek.


       // txtSelectedToDo.setDisable(true);
       // btnDelete.setDisable(true);
       // btnUpdate.setDisable(true);
        setDisableCommon(true);

        listToDo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoTM>() {
            @Override
            public void changed(ObservableValue<? extends ToDoTM> observable, ToDoTM oldValue, ToDoTM newValue) {
               // txtSelectedToDo.setDisable(false);
                // btnDelete.setDisable(false);
                 // btnUpdate.setDisable(false);
                setDisableCommon(false);
                subRoot.setVisible(false);

                ToDoTM selectedItem = listToDo.getSelectionModel().getSelectedItem();
                if (selectedItem==null){
                    return;
                }
                txtSelectedToDo.requestFocus();
                txtSelectedToDo.setText(selectedItem.getDescription());
                txtSelectedToDo.requestFocus();

            }
        });


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
        txtNewToDo.requestFocus();

        listToDo.getSelectionModel().clearSelection();
        setDisableCommon(true);
        subRoot.setVisible(true);
        //listToDo.getSelectionModel().clearSelection();
        txtSelectedToDo.clear();

    }

    public void txtNewToDoOnAction(ActionEvent actionEvent) {
    }

    public void btnAddToListOnAction(ActionEvent actionEvent) {
        String id=autoGenererateID();
        String description=txtNewToDo.getText();
        String user_id=lblID.getText();

        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("insert into todo values (?,?,?)");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,description);
            preparedStatement.setObject(3,user_id);

            preparedStatement.executeUpdate();
            txtNewToDo.clear();
            subRoot.setVisible(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadList();
    }

    public String autoGenererateID(){
        //75 mulinmdatabae connection ek hdgmu
        Connection connection = DBConnection.getInstance().getConnection();
        //76 dn meke idal sqql query ekk execute krwnn statement ekk create krgmu
        //mekedi error ekk pennai try catch ekk dgen handle krgnn ek

        String id=null;

        try {
            Statement statement = connection.createStatement();
            //77. dn sql query ek run krwmu eken en resut ekth gmu
            ResultSet resultSet= statement.executeQuery("select id from todo order by id desc limit 1");
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
                    id="T00"+intID;
                }else if(intID<100){
                    id="T0"+intID;
                }else {
                    id="T"+intID;
                }

            }
            //85 mek mekt adala if ek liyddim liynn. user knk table eke nttn ekiynne db eke user table ekt dana mulma userwanm me de wenn.
            else{
                id="T001";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
return id;
    }

    public void loadList(){
        ObservableList<ToDoTM> items=listToDo.getItems();
        items.clear();
        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from todo where user_id = ?");
            preparedStatement.setObject(1,LoginFormController.loginUserID);
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){
                String id= resultSet.getString(1);
                String description=resultSet.getString(2);
                String user_id = resultSet.getString(3);

                ToDoTM toDoTM= new ToDoTM(id,description,user_id);
                items.add(toDoTM);
            }
            listToDo.refresh();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public  void setDisableCommon(boolean isDisable){
        txtSelectedToDo.setDisable(isDisable);
        btnDelete.setDisable(isDisable);
        btnUpdate.setDisable(isDisable);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String text = txtSelectedToDo.getText();
        ToDoTM selectedItem =listToDo.getSelectionModel().getSelectedItem();

        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("UPDATE todo set description = ? where id=?");
            preparedStatement.setObject(1,text);
            preparedStatement.setObject(2,selectedItem.getId());
            preparedStatement.executeUpdate();

            loadList();
            setDisableCommon(true);
            txtSelectedToDo.clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this to do?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType =alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)) {
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from todo where id=?");
                preparedStatement.setObject(1, listToDo.getSelectionModel().getSelectedItem().getId());

                preparedStatement.executeUpdate();

                loadList();
                setDisableCommon(true);
                txtSelectedToDo.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
