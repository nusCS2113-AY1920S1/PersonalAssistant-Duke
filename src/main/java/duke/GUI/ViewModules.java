package duke.GUI;

import duke.Duke;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * This class creates all user interface modules.
 * Every component in the UI exists as a method.
 */
public class ViewModules {

    /**
     * Creates the time table of the scheduled events for the week.
     * For use in the main menu
     * @return GridPane of the generated table
     */
    public static GridPane timeTable() {
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
        timetable.setLayoutX(350);
        timetable.setLayoutY(125);
        timetable.setStyle("-fx-pref-height: 450px; -fx-pref-width: 850px; -fx-background-color: lavender;");
        return timetable;
    }

    /**
     * Method creates the layout for the Schedule UI
     * @return Pane of the layoutSchedule
     */
    public static Pane layoutSchedule() {
        Pane layoutSchedule = new Pane();

        //create the buttons
        Button buttonMenu = new Button();
        buttonMenu.setText("Menu");
        Button buttonStudents = new Button();
        buttonStudents.setText("Manage Students");
        Button buttonTraining = new Button();
        buttonTraining.setText("Manage Training Programmes");

        //add handler functions
        buttonMenu.setOnAction(e -> Duke.window.setScene(new Scene(layoutHome(), 1280, 720)));
        buttonStudents.setOnAction(e -> Duke.window.setScene(new Scene(layoutStudents(), 1280, 720)));
        buttonTraining.setOnAction(e -> Duke.window.setScene(new Scene(layoutTraining(), 1280, 720)));

        //sample text area
        TextArea textArea = new TextArea();
        textArea.setText("Set Schedule here");
        VBox vbox = new VBox(textArea);

        //Set coordinates of all buttons
        buttonStudents.setLayoutX(100);
        buttonStudents.setLayoutY(300);
        buttonTraining.setLayoutX(100);
        buttonTraining.setLayoutY(450);
        buttonMenu.setLayoutX(20);
        buttonMenu.setLayoutY(20);
        vbox.setLayoutX(500);
        vbox.setLayoutY(200);

        //change button style
        buttonStudents.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonTraining.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonMenu.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

        layoutSchedule.getChildren().addAll(buttonStudents, buttonTraining, buttonMenu, vbox);

        return layoutSchedule;
    }

    /**
     * Method creates the layout for the Students UI
     * @return Pane of the layoutStudents
     */
    public static Pane layoutStudents() {
        Pane layoutStudents = new Pane();

        //create the buttons
        Button buttonMenu = new Button();
        buttonMenu.setText("Menu");
        Button buttonSchedule = new Button();
        buttonSchedule.setText("Manage Schedule");
        Button buttonTraining = new Button();
        buttonTraining.setText("Manage Training Programmes");

        //add handler functions
        buttonMenu.setOnAction(e -> Duke.window.setScene(new Scene(layoutHome(), 1280, 720)));
        buttonSchedule.setOnAction(e -> Duke.window.setScene(new Scene(layoutSchedule(), 1280, 720)));
        buttonTraining.setOnAction(e -> Duke.window.setScene(new Scene(layoutTraining(), 1280, 720)));

        //sample text area
        TextArea textArea = new TextArea();
        textArea.setText("Set Students here");
        VBox vbox = new VBox(textArea);

        //Set coordinates of all buttons
        buttonSchedule.setLayoutX(100);
        buttonSchedule.setLayoutY(150);
        buttonTraining.setLayoutX(100);
        buttonTraining.setLayoutY(450);
        buttonMenu.setLayoutX(20);
        buttonMenu.setLayoutY(20);
        vbox.setLayoutX(500);
        vbox.setLayoutY(200);

        //change button style
        buttonSchedule.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonTraining.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonMenu.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

        layoutStudents.getChildren().addAll(buttonSchedule, buttonTraining, buttonMenu, vbox);

        return layoutStudents;
    }

    /**
     * Method creates the layout for the Training UI
     * @return Pane of the layoutTraining
     */
    public static Pane layoutTraining() {
        Pane layoutTraining = new Pane();

        //create the buttons
        Button buttonMenu = new Button();
        buttonMenu.setText("Menu");
        Button buttonSchedule = new Button();
        buttonSchedule.setText("Manage Schedule");
        Button buttonStudents = new Button();
        buttonStudents.setText("Manage Students");

        //add handler functions
        buttonMenu.setOnAction(e -> Duke.window.setScene(new Scene(layoutHome(), 1280, 720)));
        buttonSchedule.setOnAction(e -> Duke.window.setScene(new Scene(layoutSchedule(), 1280, 720)));
        buttonStudents.setOnAction(e -> Duke.window.setScene(new Scene(layoutStudents(), 1280, 720)));

        //sample text area
        TextArea textArea = new TextArea();
        textArea.setText("Set Training here");
        VBox vbox = new VBox(textArea);

        //Set coordinates of all buttons
        buttonSchedule.setLayoutX(100);
        buttonSchedule.setLayoutY(150);
        buttonStudents.setLayoutX(100);
        buttonStudents.setLayoutY(300);
        buttonMenu.setLayoutX(20);
        buttonMenu.setLayoutY(20);
        vbox.setLayoutX(500);
        vbox.setLayoutY(200);

        //change button style
        buttonSchedule.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonStudents.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonMenu.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

        layoutTraining.getChildren().addAll(buttonSchedule, buttonStudents, buttonMenu, vbox);

        return layoutTraining;
    }

    /**
     * Method creates the layout for the main menu UI
     * @return Pane of the layoutHome
     */
    public static Pane layoutHome() {
        Pane layoutHome = new Pane();

        //create the buttons
        Button buttonSchedule = new Button();
        buttonSchedule.setText("Manage Schedule");
        Button buttonStudents = new Button();
        buttonStudents.setText("Manage Students");
        Button buttonTraining = new Button();
        buttonTraining.setText("Manage Training Programmes");

        //add handler functions
        buttonSchedule.setOnAction(e -> Duke.window.setScene(new Scene(layoutSchedule(), 1280, 720)));
        buttonStudents.setOnAction(e -> Duke.window.setScene(new Scene(layoutStudents(), 1280, 720)));
        buttonTraining.setOnAction(e -> Duke.window.setScene(new Scene(layoutTraining(), 1280, 720)));

        //Create the timetable of the week
        GridPane timetable = timeTable();

        //Set coordinates of all buttons
        buttonSchedule.setLayoutX(100);
        buttonSchedule.setLayoutY(150);
        buttonStudents.setLayoutX(100);
        buttonStudents.setLayoutY(300);
        buttonTraining.setLayoutX(100);
        buttonTraining.setLayoutY(450);

        //change button style
        buttonSchedule.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonStudents.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonTraining.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

        layoutHome.getChildren().addAll(buttonSchedule, buttonStudents, buttonTraining, timetable);

        return layoutHome;
    }

}
