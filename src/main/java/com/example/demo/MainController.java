package com.example.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.io.IOException;
import java.util.*;
import java.util.prefs.Preferences;

public class MainController implements Initializable {

    @FXML
    private TableColumn<Teachers, String> column_chair, column_posts, column_faculty, column_fio, column_num;

    @FXML
    private Label l_chair, l_posts, l_faculty, l_fio, l_id;

    @FXML
    private TableView<Teachers> table_view;
    private Stage stage;
    ObservableList<Teachers> teachers = FXCollections.observableArrayList(
            new Teachers("1", "КТиИБ", "ИСиПИ", "Щербаков С.М.", "Зав.Кафедры"),
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

    public void getStage(Stage stage)
    {this.stage = stage;}

    public File getTeachersFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            stage.setTitle("University - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            stage.setTitle("University");
        }
    }

    public void loadPersonDataFromFile(File file) {
        try {

            JAXBContext context = JAXBContext
                    .newInstance(TeachersListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            // Чтение XML из файла и демаршализация.
            TeachersListWrapper wrapper = (TeachersListWrapper) um.unmarshal(file);

            teachers = FXCollections.observableList(wrapper.getTeachers());
            table_view.setItems(teachers);

            // Сохраняем путь к файлу в реестре.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не получилось открыть файл");
            alert.setContentText("Не получилось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TeachersListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об преподавателях.
            TeachersListWrapper wrapper = new TeachersListWrapper();
            wrapper.setTeachers(teachers);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не получилось открыть файл");
            alert.setContentText("Не получилось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    @FXML
    private void new_file(ActionEvent event) {
        teachers.clear();
        table_view.setItems(teachers);
        setPersonFilePath(null);
    }

    @FXML
    private void open_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */
    @FXML
    private void save_file(ActionEvent event) {
        File personFile = getTeachersFilePath();
        if (personFile != null) {
            savePersonDataToFile(personFile);
        } else {
            save_as_file(event);
        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл, куда будут сохранены данные
     */
    @FXML
    private void save_as_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            savePersonDataToFile(file);
        }
    }

    /**
     * Открывает диалоговое окно about.
     */
    @FXML
    private void help_about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Teachers");
        alert.setHeaderText("О нас");
        alert.setContentText("Автор: Лушников Даниил \nИСТ-331");

        alert.showAndWait();
    }

    /**
     * Закрывает приложение.
     */
    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
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
