package com.example.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableColumn<Teachers, String> column_chair, column_posts, column_faculty, column_fio, column_num;

    @FXML
    private Label l_chair, l_posts, l_faculty, l_fio, l_id;

    @FXML
    private TableView<Teachers> table_view;

    ObservableList<Teachers> teachers = FXCollections.observableArrayList(new Teachers("1", "КТиИБ", "ИСиПИ", "Щербаков С.М.", "Зав.Кафедры"),
            new Teachers("2","КТиИБ", "ИСиПИ", "Шкодина Т.А.", "Ст.Преподаватель"),
            new Teachers("3","КТиИБ", "ИСиПИ", "Фрид Л.М.", "Доцент Кафедры"),
            new Teachers("4","КТиИБ", "ИСиПИ", "Прохорова А.М.", "Ст.Преподаватель"),
            new Teachers("5","КТиИБ", "ИСиПИ", "Бекмурза В.Ю.", "Ст.Преподаватель"));

    @FXML
    void add(ActionEvent event) throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("AddView.fxml"));
        dialog.setScene(new Scene(loader.load(),600, 400));
        AddController controller = loader.getController();
        controller.getDialog(dialog);
        controller.getTeachers(teachers);
        dialog.showAndWait();
        table_view.setItems(FXCollections.observableList(teachers));
    }

    @FXML
    void delete(ActionEvent event) {
        if (table_view.getSelectionModel().getSelectedItem()!=null) {
            teachers.remove(table_view.getSelectionModel().getSelectedItem());
            table_view.setItems(FXCollections.observableList(teachers));
            l_id.setText("Преподаватель №");
            l_faculty.setText("Факультет: ");
            l_chair.setText("Кафедра: ");
            l_fio.setText("ФИО: ");
            l_posts.setText("Должность: ");
        }
    }

    @FXML
    void edit(ActionEvent event) throws IOException {
        if (table_view.getSelectionModel().getSelectedItem()!=null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("EditView.fxml"));
            stage.setScene(new Scene(loader.load(),600, 400));
            EditController controller = loader.getController();
            controller.getDialog(stage);
            int id = teachers.indexOf(table_view.getSelectionModel().getSelectedItem());
            controller.getTeachers(teachers.get(id));
            stage.showAndWait();
            table_view.setItems(teachers);
            table_view.getSelectionModel().clearSelection();
            table_view.getSelectionModel().select(id);
            table_view.refresh();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column_num.setCellValueFactory(new PropertyValueFactory<Teachers, String>("id"));
        column_faculty.setCellValueFactory(new PropertyValueFactory<Teachers, String>("faculties"));
        column_chair.setCellValueFactory(new PropertyValueFactory<Teachers,String>("chairs"));
        column_fio.setCellValueFactory(new PropertyValueFactory<Teachers,String>("fio"));
        column_posts.setCellValueFactory(new PropertyValueFactory<Teachers,String>("posts"));

        table_view.setItems(teachers);
        table_view.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Teachers teachers = table_view.getSelectionModel().getSelectedItem();
                l_id.setText("Преподаватель №"+teachers.getId());
                l_faculty.setText("Факультет: "+teachers.getFaculties());
                l_chair.setText("Кафедра: "+teachers.getChairs());
                l_fio.setText("ФИО: "+teachers.getFio());
                l_posts.setText("Должность: "+teachers.getPosts());
            }
        });
    }
}
