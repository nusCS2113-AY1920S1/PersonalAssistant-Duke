package JavaFx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import Tasks.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class TimetableView extends TableView implements Initializable {
    @FXML
    private TableView<Timetable> timetableView;
    @FXML
    private TableColumn<Timetable, String> timeColumn;
    @FXML
    private TableColumn<Timetable, String> descriptionColumn;
    @FXML
    private TableColumn<Timetable, String> priorityColumn;
    @FXML
    private ScrollPane scrollPane;

    private ArrayList<Task> taskList;
    private String time;
    private String description;
    private String priority;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        try {
            timetableView.setItems(getTimetable());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private TimetableView(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    private ObservableList<Timetable> getTimetable() throws ParseException {
        ObservableList<Timetable> timetables = FXCollections.observableArrayList();
        for(Task task : taskList) {
            if(task.toString().contains("[E]")) {
                String event = task.toString();
                DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                DateFormat timeFormat= new SimpleDateFormat("HH:mm");
                Date date = dateFormat.parse(event.substring(event.indexOf("at:") + 4, event.indexOf(')')));
                time = timeFormat.format(date);
                description = task.getDescription();
                priority = "low";
                timetables.add(new Timetable(time, description, priority));
            }
        }
        return timetables;
    }

    public static TimetableView getTimetableView(ArrayList<Task> taskList) {
        var timetable = new TimetableView(taskList);
        return timetable;
    }
}