package duke.GUI;


import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ViewModules {
    public ViewModules() {
        //do nothing
    }

    public static GridPane timeTable () {
        GridPane timetable = new GridPane();
        timetable.setGridLinesVisible(true);
        int numCols = 8;
        int numRows = 11;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            timetable.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            timetable.getRowConstraints().add(rowConst);
        }
        String[] days = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < days.length; i++) {
            timetable.add(new Label(days[i]), i + 1, 0);
        }
        String[] periods = new String[] {"8am-9am", "9am-10am", "10am-11am", "11am-12pm", "12pm-1pm", "1pm-2pm", "2pm-3pm", "3pm-4pm", "4pm-5pm", "5pm-6pm"};
        for (int i = 0; i < periods.length; i++) {
            timetable.add(new Label(periods[i]), 0, i + 1);
        }
        return timetable;
    }

}
