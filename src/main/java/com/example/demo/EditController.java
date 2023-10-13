package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {
    @FXML

    private TextField text_num, text_chair, text_posts, text_faculty, text_fio;

    private Stage dialog;

    private Teachers teachers;

    @FXML
    void edit(ActionEvent event) {
        if (!text_num.getText().isEmpty() && !text_faculty.getText().isEmpty() && !text_chair.getText().isEmpty() && !text_fio.getText().isEmpty() && !text_posts.getText().isEmpty()){
            teachers.setId(text_num.getText());
            teachers.setFaculties(text_faculty.getText());
            teachers.setChairs(text_chair.getText());
            teachers.setFio(text_fio.getText());
            teachers.setPosts(text_posts.getText());
            dialog.close();}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialog);
            alert.setTitle("Пустое поле!");
            alert.setHeaderText("Одно или несколько полей пусты");
            alert.showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {dialog.close();}
    public void getdialog(Stage dialogStage) {this.dialog = dialogStage;}
    public void getagreeemet(Teachers teachers) {
        text_num.setText(teachers.getId());
        text_faculty.setText(teachers.getFaculties());
        text_chair.setText(teachers.getChairs());
        text_fio.setText(teachers.getFio());
        text_posts.setText(teachers.getPosts());
        this.teachers = teachers;}

}
