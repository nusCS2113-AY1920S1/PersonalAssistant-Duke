package spinbox.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import spinbox.entities.Calendar;
import spinbox.entities.items.tasks.Task;
import spinbox.exceptions.DateFormatException;

import java.io.IOException;
import java.util.List;

public class CalendarMonthBox extends AnchorPane {
    @FXML
    private Label month;
    @FXML
    private Label year;
    @FXML
    private GridPane monthBox;

    private Calendar calendarMonth;


    CalendarMonthBox(String date, List<Pair<String, Task>> taskList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CalendarMonthBox.class.getResource("/view/CalendarMonthBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        calendarMonth = new Calendar(date);
        setHeadings(calendarMonth.getMonthString(), calendarMonth.getYearString());
        setMonthBox(taskList);
    }

    private void setHeadings(String month, String year) {
        this.month.setText(month);
        this.year.setText(year);
        this.month.setAlignment(Pos.CENTER);
        this.year.setAlignment(Pos.CENTER);
        this.month.setTextFill(Color.web("#FFFFFF"));
        this.year.setTextFill(Color.web("#FFFFFF"));
    }

    private void setMonthBox(List<Pair<String, Task>> taskList) {
        int i;
        int j;
        int day = calendarMonth.getStartDateDay();
        int lastDay = calendarMonth.getEndOfMonthDay();
        int dateCount = 1;
        List<Pair<Integer, List<Pair<String, Task>>>> taskInMonthBox;

        Label label;

        taskInMonthBox = calendarMonth.taskInCalendarByDayInMonth(taskList);
        for (i = 1; i < lastDay + 1; i++) {
            VBox temp = new VBox();
            label = new Label(" " + dateCount);
            temp.getChildren().add(label);
            int row = ((i + day - 2) / 7) + 1;
            int col = (i + day - 2) % 7;
            if (!taskInMonthBox.isEmpty()) {
                Pair<Integer, List<Pair<String, Task>>> pair = taskInMonthBox.get(i - 1);
                List<Pair<String, Task>> tasksOnDay = pair.getValue();
                for (Pair item : tasksOnDay) {
                    Task task = (Task) item.getValue();
                    String moduleCode = (String) item.getKey();
                    label = new Label(moduleCode + " " + task.getTaskType() + " : " + task.getName());
                    temp.getChildren().add(label);
                }
            }
            monthBox.add(temp, col, row);
            dateCount += 1;
        }

        monthBox.setGridLinesVisible(true);
    }
}
