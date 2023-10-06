package com.example.demo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    List<Teachers> TeachersList = Arrays.asList(new Teachers("КТиИБ", "ИСиПИ", "Щербаков С.М.", "Зав.Кафедры"),
            new Teachers("КТиИБ", "ИСиПИ", "Шкодина Т.А.", "Ст.Преподаватель"),
            new Teachers("КТиИБ", "ИСиПИ", "Фрид Л.М.", "Доцент Кафедры"),
            new Teachers("КТиИБ", "ИСиПИ", "Прохорова А.М.", "Ст.Преподаватель"),
            new Teachers("КТиИБ", "ИСиПИ", "Бекмурза В.Ю.", "Ст.Преподаватель"));

    ArrayList<Teachers> TeachArray = new ArrayList<>(TeachersList);

    @FXML
    private TableColumn<Teachers, String> chairs;

    @FXML
    private TableColumn<Teachers, String> faculties;

    @FXML
    private TableColumn<Teachers, String> posts;

    @FXML
    private TableView<Teachers> table;

    @FXML
    private TableColumn<Teachers, String> teachers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        faculties.setCellValueFactory(new PropertyValueFactory<Teachers, String>("faculties"));
        chairs.setCellValueFactory(new PropertyValueFactory<Teachers,String>("chairs"));
        teachers.setCellValueFactory(new PropertyValueFactory<Teachers,String>("teachers"));
        posts.setCellValueFactory(new PropertyValueFactory<Teachers,String>("posts"));

        table.setItems(FXCollections.observableList(TeachArray));
    }
}
