package compal.ui;

import compal.model.tasks.Task;
import compal.storage.TaskStorageManager;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

//@@author SholihinK

/**
 * Create a timetable drawing onto DailyView scroll-pane within tab-pane.
 */

class DailyCalUi {

    private TaskStorageManager taskStorageManager;

    private String dateToDisplay;
    private boolean[][] canStore = new boolean[25][5];
    private ScrollPane sp = new ScrollPane();
    private Group groupRoot = new Group();
    private Line[] horizontalLines = new Line[50];
    private Line[] verticalLines = new Line[50];
    private Text[] timeAM = new Text[50];
    private Text[] timePM = new Text[50];
    private int[] clockTime = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private double colOneXLayout = 25;
    private double colOneYLayout = -25;
    private int horizontalLineCounter = 0;
    private double horizontalYLayout = 0;
    private double horizontalXLayout = 0;
    private double[][] storedXAxis = new double[25][5];
    private double[][] storedYAxis = new double[25][5];
    private int startTime = 8;
    private int endTime = 17;
    private ArrayList<Task> tempOriginalList;
    private ArrayList<Task> dailyCalArrayList = new ArrayList<>();
    private ArrayList<Task> deadlineArrayList = new ArrayList<>();
    private String type = "";


    DailyCalUi() {
        this.taskStorageManager = new TaskStorageManager();
    }

    /**
     * Initializer function set canStore array to true state.
     */
    private static void setTrue(boolean[][] array) {
        for (boolean[] row : array) {
            Arrays.fill(row, true);
        }
    }

    /**
     * Initializer function to create final gui timetable.
     *
     * @return scrollPane final object state
     */
    ScrollPane init(String givenDate, String type) {
        this.type = type;
        tempOriginalList = taskStorageManager.loadData();
        setTrue(canStore);
        dateToDisplay = givenDate;
        createDailyArrayList();
        sp = buildTimeTable();

        return sp;
    }

    /**
     * Create an array list of type task of that specific day.
     * Sorted by priority scoring and then time..
     * Only display non-deadline events.
     */
    private void createDailyArrayList() {

        for (Task t : tempOriginalList) {
            if (t.getStringMainDate().equals(dateToDisplay)) {
                if (t.getSymbol().equals("D") && !t.getisDone()) {
                    deadlineArrayList.add(t);
                } else if ((t.getSymbol().equals("E") && !t.getisDone())) {
                    if (!t.getStringMainDate().equals(t.getStringTrailingDate())) {
                        t.setEndTime("2359");
                    }
                    dailyCalArrayList.add(t);
                }
            } else if (t.getStringTrailingDate().equals(dateToDisplay)) {
                if ((t.getSymbol().equals("E") && !t.getisDone())) {
                    t.setStartTime("0000");
                    dailyCalArrayList.add(t);
                }
            }
        }
        if ("E".equals(type)) {
            deadlineArrayList.clear();
        } else if ("D".equals(type)) {
            dailyCalArrayList.clear();
        }

    }

    private void buildDeadline() {
        colOneYLayout -= 50;
        int plus = 75;
        Text header = new Text();
        header.setText("Due today:");
        header.setY(colOneYLayout + 50);
        header.setX(colOneXLayout);
        groupRoot.getChildren().add(header);

        int counter = 0;
        for (Task t : deadlineArrayList) {
            if (counter == 6) {
                break;
            }
            Rectangle rectangle = new Rectangle(100, 50);
            rectangle.setFill(Color.ROSYBROWN);
            rectangle.setStroke(Color.BLACK);

            final StackPane stack = new StackPane();

            String tempText = t.getDescription();
            String tempId = String.valueOf(t.getId());

            if (tempText.length() > 15) {
                tempText = tempText.substring(0, 12) + "...";
            }

            final Text text = new Text("Due: " + t.getStringEndTime() + "\nID: [" + tempId + "]\n " + tempText);
            text.setFont(Font.font("Georgia Italic", 12));
            text.setTextAlignment(TextAlignment.CENTER);
            stack.getChildren().addAll(rectangle, text);
            stack.setLayoutY(colOneYLayout + 25);
            stack.setLayoutX(colOneXLayout + plus);


            groupRoot.getChildren().add(stack);
            plus += 100;
            counter += 1;
        }
        colOneYLayout += 100;

    }


    /**
     * Call the require functions to create final state of timetable.
     *
     * @return scrollPane final object state
     */
    private ScrollPane buildTimeTable() {
        setTime();
        genDateSLot();
        buildDeadline();
        genTimeSlot();

        for (int i = startTime; i < endTime; i++) {
            drawScheduleSquare(i);
        }
        sp.setContent(groupRoot);
        return sp;
    }

    /**
     * Check through the daily taskList to check if there's any event that starts before 8am or ends after 7pm
     * If there is, set startTime or EndTime to the detected time.
     */
    private void setTime() {
        for (Task task : dailyCalArrayList) {

            int tempStartTime = Integer.parseInt(task.getStringStartTime().substring(0, 2));
            if (tempStartTime < startTime) {
                startTime = tempStartTime;
            }

            int tempEndTime = Integer.parseInt(task.getStringEndTime().substring(0, 2));
            if (tempEndTime > endTime) {
                endTime = tempEndTime;
            }
        }

    }


    /**
     * Generate the date to the displayed on top left column.
     */
    private void genDateSLot() {
        Text date = new Text();
        date.setText(dateToDisplay);
        date.setY(colOneYLayout);
        date.setX(colOneXLayout);
        colOneYLayout += 50;
        groupRoot.getChildren().add(date);
    }

    /**
     * Caller function to generate all horizontal and vertical lines
     * for each time slot for daily View.
     */
    private void genTimeSlot() {
        for (int i = 0; i < clockTime.length; i++) {
            makeASlot(i);
        }
        makeHorizontalLines(horizontalLineCounter++);
        makeHeaderVerticalLines();
    }

    /**
     * Caller function to generate a slot for each time range and fill the slot.
     * With the logic below
     * 1. Generate only the the detected time range.
     * 2. If time is AM or PM, display the time as e.g. 09:00 AM.
     * 3. Draw the square for that slot.
     * 4. If that time has an event, draw the square for the total event.
     */
    private void makeASlot(int i) {
        int temp = horizontalLineCounter;
        if (clockTime[i] < startTime || clockTime[i] > endTime) {
            return;
        } else if (clockTime[i] < 12) {
            makeTimeAM(i);
            for (int x = temp; x < temp + 2; x++) {
                makeHorizontalLines(x);
                if (x == temp) {
                    storeScheduleAxis(clockTime[i]);
                }
                horizontalLineCounter++;
            }
        } else {
            makeTimePM(i);
            for (int x = temp; x < temp + 2; x++) {
                makeHorizontalLines(x);
                if (x == temp) {
                    storeScheduleAxis(clockTime[i]);
                }
                horizontalLineCounter++;
            }
        }
    }


    /**
     * Create title for schedule depending on type of task Type.
     *
     * @return Final title to be display for each block on GUI
     */
    private String createTitle(Task t) {
        String blockTitle = "";

        /*if (t.getDescription().matches("(?i:.*lec.*)")) {
            blockTitle = "[Lecture]\n";
        } else if (t.getDescription().matches("(?i:.*tut.*)")) {
            blockTitle = "[Tut]\n";
        } else if (t.getDescription().matches("(?i:.*sect.*)")) {
            blockTitle = "[Sect]\n";
        } else if (t.getDescription().matches("(?i:.*lab.*)")) {
            blockTitle = "[Lab]\n";
        } else if (t.getDescription().matches("(?i:.*rt.*)")) {
            blockTitle = "[RT]\n";
        } else if (t.getSymbol().equals("E")) {
            blockTitle = "[Event]\n";
        }*/

        /*if (t.getPriority().equals(Task.Priority.high)) {
            blockTitle += "[Priority: High]\n";
        } else if (t.getPriority().equals(Task.Priority.medium)) {
            blockTitle += "[Priority: Medium]\n";
        } else {
            blockTitle += "[Priority: Low]\n";
        }*/

        blockTitle += "Time: " + t.getStringStartTime() + " - " + t.getStringEndTime() + "\n";
        blockTitle += "ID: [" + t.getId() + "]\n";

        String tempText = t.getDescription();
        if (tempText.length() > 15) {
            tempText = tempText.substring(0, 12) + "...";
        }
        blockTitle += tempText;
        return blockTitle;
    }

    /**
     * Create a square block of schedule depending on the duration of the event.
     */
    private void drawScheduleSquare(int currentTime) {
        int eventCounter = 0;
        int hourInMin = 60;
        double pixelBlock = 100;
        for (Task task : dailyCalArrayList) {
            if (eventCounter < 5 && Integer.parseInt(task.getStringStartTime().substring(0, 2)) == currentTime) {
                int startHour = Integer.parseInt(task.getStringStartTime().substring(0, 2));
                int startMin = Integer.parseInt(task.getStringStartTime().substring(2, 4));
                int endHour = Integer.parseInt(task.getStringEndTime().substring(0, 2));
                int endMin = Integer.parseInt(task.getStringEndTime().substring(2, 4));

                int totalHour;
                int totalMin;
                if (endMin >= startMin) {
                    totalMin = endMin - startMin;
                    totalHour = endHour - startHour;
                } else {
                    endHour--;
                    totalMin = endMin + hourInMin - startMin;
                    totalHour = endHour - startHour;
                }

                if (totalHour == 0 && totalMin == 0) {
                    continue;
                }

                //Drawing a Rectangle
                double heightY = 1.7;
                double heightYMin = heightY * totalMin;
                double heightYHour = pixelBlock * totalHour;
                double combineY = heightYHour + heightYMin;
                if (combineY < 50) {
                    combineY = 50;
                }

                Rectangle rectangle = new Rectangle(pixelBlock, combineY);
                rectangle.setFill(colorFill(task));
                rectangle.setStroke(Color.BLACK);

                final StackPane stack = new StackPane();
                final Text text = new Text(createTitle(task));
                text.setFont(Font.font("Georgia Italic", 12));
                text.setTextAlignment(TextAlignment.CENTER);

                stack.getChildren().addAll(rectangle, text);
                while (storedXAxis[currentTime][eventCounter] == 0) {
                    eventCounter++;
                }
                stack.setLayoutX(storedXAxis[currentTime][eventCounter]);
                stack.setLayoutY(50 + storedYAxis[currentTime][eventCounter]);

                groupRoot.getChildren().add(stack);
                eventCounter++;
            }
        }
    }

    /**
     * Set color of rectangle to depending on the scenario below.
     */
    private Color colorFill(Task t) {

        if (t.getDescription().matches("(?i:.*lec.*)")) {
            return Color.GOLDENROD;
        } else if (t.getDescription().matches("(?i:.*tut.*)")) {
            return Color.DEEPPINK;
        } else if (t.getDescription().matches("(?i:.*sect.*)")) {
            return Color.VIOLET;
        } else if (t.getDescription().matches("(?i:.*lab.*)")) {
            return Color.INDIANRED;
        } else if (t.getPriority().equals(Task.Priority.medium)) {
            return Color.TOMATO;
        } else if (t.getPriority().equals(Task.Priority.high)) {
            return Color.RED;
        }
        return Color.LIGHTSALMON;
    }


    /**
     * Store schedule axis of current time.
     */
    private void storeScheduleAxis(int currentTime) {
        int eventCounter = 0;
        double pixelBlock = 100.00;
        int hourInMin = 60;
        for (Task task : dailyCalArrayList) {
            if (Integer.parseInt(task.getStringStartTime().substring(0, 2)) == currentTime && eventCounter < 5) {
                int startHour = Integer.parseInt(task.getStringStartTime().substring(0, 2));
                int startMin = Integer.parseInt(task.getStringStartTime().substring(2, 4));
                int endHour = Integer.parseInt(task.getStringEndTime().substring(0, 2));
                int endMin = Integer.parseInt(task.getStringEndTime().substring(2, 4));
                int totalHour;
                int totalMin;
                if (endMin >= startMin) {
                    totalMin = endMin - startMin;
                    totalHour = endHour - startHour;
                } else {
                    endHour--;
                    totalMin = endMin + hourInMin - startMin;
                    totalHour = endHour - startHour;
                }

                if (totalHour == 0 && totalMin == 0) {
                    continue;
                }
                double pxPerMin = (pixelBlock / (double) hourInMin);
                double downPX = pxPerMin * startMin;

                while (!canStore[currentTime][eventCounter]) {
                    eventCounter++;
                }

                if (canStore[currentTime][eventCounter]) {
                    double layoutX = getEventLayoutX(eventCounter);
                    storedXAxis[currentTime][eventCounter] = layoutX;
                    storedYAxis[currentTime][eventCounter] = horizontalYLayout + downPX - 50;
                    int futureEndTime = Integer.parseInt(task.getStringEndTime().substring(0, 2));
                    for (int futureTime = currentTime; futureTime < futureEndTime; futureTime++) {
                        canStore[futureTime][eventCounter] = false;
                    }
                }
                eventCounter += 1;
            }
        }
    }

    /**
     * Get coordinates depending on which event it's being slotted into.
     *
     * @return integer coordinates of X-axis.
     */
    private int getEventLayoutX(int eventCounter) {

        switch (eventCounter) {
        case 1:
            return 200;
        case 2:
            return 300;
        case 3:
            return 400;
        case 4:
            return 500;
        default:
            return 100;
        }
    }

    /**
     * Set text to be displayed as AM.
     *
     * @param time of the event.
     */
    private void makeTimeAM(int time) {
        timeAM[time] = new Text();
        String toStore = clockTime[time] + ":00 am";
        timeAM[time].setText(toStore);
        timeAM[time].setY(colOneYLayout);
        timeAM[time].setX(colOneXLayout);
        colOneYLayout += 100;
        groupRoot.getChildren().add(timeAM[time]);
    }

    /**
     * Set text to be displayed as PM.
     *
     * @param time of the event.
     */
    private void makeTimePM(int time) {
        timePM[time] = new Text();
        String toStore = clockTime[time] + ":00 PM";
        timePM[time].setText(toStore);
        timePM[time].setY(colOneYLayout);
        timePM[time].setX(colOneXLayout);
        colOneYLayout += 100;
        groupRoot.getChildren().add(timePM[time]);
    }

    /**
     * Call function to draw horizontal Lines.
     */
    private void makeHorizontalLines(int i) {
        drawHorizontalLines(i);
    }


    /**
     * Draw horizontal Lines.
     */
    private void drawHorizontalLines(int i) {
        horizontalLines[i] = new Line();
        horizontalLines[i].setStartX(0);
        horizontalLines[i].setStartY(0);
        horizontalLines[i].setEndX(600);
        horizontalLines[i].setEndY(0);
        horizontalLines[i].setLayoutX(horizontalXLayout);
        horizontalLines[i].setLayoutY(horizontalYLayout);
        horizontalXLayout += 0;
        horizontalYLayout += 50;
        groupRoot.getChildren().add(horizontalLines[i]);
    }


    /**
     * Draw vertical Lines.
     */
    private void makeHeaderVerticalLines() {
        verticalLines[0] = new Line();
        verticalLines[0].setStartX(0);
        verticalLines[0].setStartY(0);
        verticalLines[0].setEndX(0);
        verticalLines[0].setEndY(horizontalYLayout);
        double verticalXLayout = 100;
        verticalLines[0].setLayoutX(verticalXLayout);
        double verticalYLayout = 0;
        verticalLines[0].setLayoutY(verticalYLayout);
        groupRoot.getChildren().add(verticalLines[0]);
    }


}