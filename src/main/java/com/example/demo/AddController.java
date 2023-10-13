package com.example.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class AddController{
    @FXML
    private TextField text_num, text_chair, text_posts, text_faculty, text_fio;
    private Stage dialog;
    private ObservableList<Teachers> teachers = FXCollections.observableArrayList();
    @FXML
    void add(ActionEvent event) {
        if(!text_num.getText().isEmpty() && !text_chair.getText().isEmpty() && !text_posts.getText().isEmpty() && !text_faculty.getText().isEmpty() && !text_fio.getText().isEmpty()){
            teachers.add(new Teachers(text_num.getText(),text_chair.getText(),text_posts.getText(),text_faculty.getText(),text_fio.getText()));
            dialog.close();}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialog);
            alert.setTitle("Пустое поле!");
            alert.setHeaderText("Одно или несколько полей пусты!");
            alert.showAndWait();
        }
    }
    @FXML
    void cancel(ActionEvent event) {dialog.close();}
    public void getdialog(Stage dialog)
    {this.dialog = dialog;}
    public void getagreements(ObservableList<Teachers> teachers) {this.teachers = teachers;}
}



