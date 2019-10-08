package compal.ui;

import compal.commons.Compal;
import compal.model.tasks.Task;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Create a timetable drawing onto DailyView scroll-pane within tab-pane.
 */

public class DailyCal {

    String dateToDisplay;
    private Compal compal = new Compal();
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
    private double verticalYLayout = 0;
    private double verticalXLayout = 100;
    private double horizontalYLayout = 0;
    private double horizontalXLayout = 0;
    private double[][] storedXAxis = new double[25][5];
    private double[][] storedYAxis = new double[25][5];
    private int startTime = 8;
    private int endTime = 19;
    private ArrayList<Task> tempList = compal.tasklist.arrlist;
    private ArrayList<Task> arrList = new ArrayList<>();


    public DailyCal() {
    }

    /**
     * Initializer function to create final gui timetable.
     *
     * @return scrollPane final object state
     */
    public ScrollPane init(String givenDate) {
        dateToDisplay = givenDate;
        createDailyArrayList();
        sp = buildTimeTable();
        return sp;
    }

    /**
     * Create an array list of type task of that specific day.
     * Sorted by starting time.
     */
    private void createDailyArrayList() {
        Comparator<Task> compareByStartTime = Comparator.comparing(Task::getStringStartTime);
        for (Task t : tempList) {
            if (t.getStringDate().equals(dateToDisplay)) {
                arrList.add(t);
            }
        }
        Collections.sort(arrList, compareByStartTime);
    }

    /**
     * Call the require functions to create final state of timetable.
     *
     * @return scrollPane final object state
     */
    private ScrollPane buildTimeTable() {
        setTime();
        genDateSLot();
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
     *
     * @return scrollPane final object state
     */
    private void setTime() {
        for (Task task : arrList) {

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
     *
     * @return scrollPane final object state
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
     *
     * @return scrollPane final object state
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
     * Create a square block of schedule depending on the duration of the event.
     */
    private void drawScheduleSquare(int currentTime) {
        int eventCounter = 0;
        int hourInMin = 60;
        double pixelBlock = 100;
        for (Task task : arrList) {
            if (eventCounter < 5) {
                if (Integer.parseInt(task.getStringStartTime().substring(0, 2)) == currentTime) {
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
                    String desc = task.getDescription();
                    //Drawing a Rectangle
                    double heightY = 1.7;
                    double heightYMin = heightY * totalMin;
                    double heightYHour = pixelBlock * totalHour;
                    Rectangle rectangle = new Rectangle(pixelBlock, heightYHour + heightYMin);
                    rectangle.setFill(colorFill(task));
                    rectangle.setStroke(Color.BLACK);
                    final StackPane stack = new StackPane();
                    final Text text = new Text(desc);
                    text.setFont(Font.font("Georgia Italic", 12));
                    stack.getChildren().addAll(rectangle, text);
                    stack.setLayoutX(storedXAxis[currentTime][eventCounter]);
                    stack.setLayoutY(storedYAxis[currentTime][eventCounter]);
                    eventCounter++;
                    groupRoot.getChildren().add(stack);
                }
            }
        }
    }

    /**
     * Set color of rectangle to depending on the scenario below.
     */
    private Color colorFill(Task t) {
        if (t.getisDone().equals("true")) {
            return Color.DARKSEAGREEN;
        } else if (t.getSymbol().equals("LECT")) {
            return Color.GOLDENROD;
        } else if (t.getSymbol().equals("TUT")) {
            return Color.DEEPPINK;
        } else if (t.getSymbol().equals("SECT")) {
            return Color.VIOLET;
        } else if (t.getSymbol().equals("LAB")) {
            return Color.INDIANRED;
        } else if (t.getSymbol().equals("RT")) {
            return Color.LEMONCHIFFON;
        } else if (t.getSymbol().equals("E")) {
            return Color.CADETBLUE;
        }
        return Color.BLUE;
    }

    /**
     * Set stroke fill of rectangle to the color depending priority ranking.
     */


    /**
     * Store schedule axis of current time.
     */
    private void storeScheduleAxis(int currentTime) {
        int eventCounter = 0;
        int moveRight = 100;
        double prevX = 0;
        double prevY = 0;
        double pixelBlock = 100.00;
        int hourInMin = 60;
        for (Task task : arrList) {
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
                double pxPerMin = (pixelBlock / Double.valueOf(hourInMin));
                double downPX = pxPerMin * startMin;
                //store a Rectangle location.
                if (prevX == 0 && prevY == 0) {
                    storedXAxis[currentTime][eventCounter] += horizontalXLayout + moveRight;
                    storedYAxis[currentTime][eventCounter] += horizontalYLayout + downPX - 50;
                    prevX = horizontalXLayout + moveRight;
                    prevY = horizontalYLayout + downPX;
                    moveRight += 100;
                } else {
                    storedXAxis[currentTime][eventCounter] += prevX + moveRight;
                    storedYAxis[currentTime][eventCounter] += prevY + downPX - 50;
                    prevX = horizontalXLayout + moveRight;
                    prevY = horizontalYLayout + downPX;
                    moveRight += 100;
                }

                int futureEndTime = Integer.parseInt(task.getStringEndTime().substring(0, 2));
                for (int futureTime = currentTime + 1; futureTime < futureEndTime; futureTime++) {
                    storedXAxis[futureTime][eventCounter] += 100;
                }

                eventCounter += 1;
            }
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
        verticalLines[0].setLayoutX(verticalXLayout);
        verticalLines[0].setLayoutY(verticalYLayout);
        groupRoot.getChildren().add(verticalLines[0]);
    }


}