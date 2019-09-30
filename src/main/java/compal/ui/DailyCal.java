package compal.ui;

import compal.compal.Compal;
import compal.tasks.Task;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Create a timetable drawing onto DailyView scroll-pane within tab-pane.
 */

public class DailyCal {

    private Compal compal = new Compal();
    private ScrollPane sp = new ScrollPane();
    private Group groupRoot = new Group();
    private Line[] horizontalLines = new Line[50];
    private Line[] verticalLines = new Line[50];
    private Text[] timeAM = new Text[50];
    private Text[] timePM = new Text[50];

    private int[] Timing = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};

    private double colOneXLayout = 0;
    private double colOneYLayout = 0;
    private int horizontalLineCounter = 0;
    private double verticalYLayout = 0;
    private double verticalXLayout = 50;
    private double horizontalYLayout = 0;
    private double horizontalXLayout = 0;

    private double storedXAxis[][] = new double [25][10];
    private double storedYAxis[][] = new double [25][10];

    private int startTime = 8;
    private int endTime = 19;

    private String pattern = "dd/MM/yyyy";
    private String todayDate = new SimpleDateFormat(pattern).format(new Date());

    public DailyCal() {
    }

    /**
     * Initializer function to create final gui timetable
     *
     * @return scrollPane final object state
     */
    public ScrollPane init() {
        sp = buildTimeTable();
        return sp;
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
        for(int i = startTime;i < endTime;i++){
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
        ArrayList<Task> arrList = compal.tasklist.arrlist;
        for (Task task : arrList) {
            if (task.getStringDate().equals(todayDate)) {
                int tempTime = Integer.parseInt(task.getStringStartTime().substring(0, 2));
                if (tempTime < startTime) {
                    startTime = tempTime;
                }
                if (tempTime > endTime) {
                    endTime = tempTime;
                }
            }
        }
    }


    /**
     * Generate the date to the displayed on top left column.
     *
     * @return scrollPane final object state
     */
    private void genDateSLot() {
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        Text date = new Text();
        date.setText(dateInString);
        date.setY(colOneYLayout);
        date.setX(colOneXLayout);
        colOneYLayout+= 50;
        groupRoot.getChildren().add(date);
    }

    /**
     * Caller function to generate all horizontal and vertical lines
     * for each time slot for daily View.
     *
     * @return scrollPane final object state
     */
    private void genTimeSlot() {
        for (int i = 0; i < Timing.length; i++) {
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
        if (Timing[i] < startTime || Timing[i] > endTime) {
            return;
        } else if (Timing[i] < 12) {
            makeTimeAM(i);
            for (int x = temp; x < temp + 2; x++) {
                makeHorizontalLines(x);
                if (x == temp) {
                    storeScheduleAxis(Timing[i]);
                }
                horizontalLineCounter++;
            }
        } else {
            makeTimePM(i);
            for (int x = temp; x < temp + 2; x++) {
                makeHorizontalLines(x);
                if (x == temp) {
                    storeScheduleAxis(Timing[i]);
                }
                horizontalLineCounter++;
            }
        }
    }


    /**
     * Create a square block of schedule depending on the duration of the event.
     */
    private void drawScheduleSquare(int Time) {
        int tempCounter = 0;
        ArrayList<Task> arrList = compal.tasklist.arrlist;
        for (Task task : arrList) {
            if (task.getStringDate().equals(todayDate)) {
                if (Integer.parseInt(task.getStringStartTime().substring(0, 2)) == Time) {
                    int sTime = Integer.parseInt(task.getStringStartTime());
                    int eTime = Integer.parseInt(task.getStringEndTime());
                    int sHour = sTime % 100;
                    int sMin = sTime - sHour;
                    int eHour = eTime % 100;
                    int eMin = eTime - eHour;
                    System.out.println();
                    int hour =0 ;
                    int min =0;
                    if (eMin >= sMin) {
                        min = eMin - sMin;
                        hour = eHour - sHour;
                    } else {
                        eHour--;
                        min = eMin + 60 - sMin;
                        hour = eHour - sHour;
                    }

                    if (hour == 0 && min == 0) {
                        continue;
                    }
                    String desc = task.getDescription();
                    //Drawing a Rectangle
                    double heightY = 1.7;
                    double heightYMin = heightY * min;
                    double heightYHour = 100 * hour;
                    Rectangle rectangle = new Rectangle(100, heightYHour + heightYMin);
                    rectangle.setFill(Color.CADETBLUE);
                    rectangle.setStroke(Color.BLACK);
                    final StackPane stack = new StackPane();
                    final Text text = new Text(desc);
                    stack.getChildren().addAll(rectangle, text);
                    stack.setLayoutX(storedXAxis[Time][tempCounter]);
                    stack.setLayoutY(storedYAxis[Time][tempCounter]);
                    tempCounter++;
                    groupRoot.getChildren().add(stack);
                }
            }
        }
    }

    /**
     * Store schedule axis of current time.
     */
    private void storeScheduleAxis(int Time) {
        int tempCounter = 0;
        int moveRight = 50;
        ArrayList<Task> arrList = compal.tasklist.arrlist;
        for (Task task : arrList) {
            if (task.getStringDate().equals(todayDate)) {
                if (Integer.parseInt(task.getStringStartTime().substring(0, 2)) == Time) {
                    int startMin = Integer.parseInt(task.getStringStartTime().substring(2, 4));
                    int sTime = Integer.parseInt(task.getStringStartTime());
                    int eTime = Integer.parseInt(task.getStringEndTime());
                    int sHour = sTime % 100;
                    int sMin = sTime - sHour;
                    int eHour = eTime % 100;
                    int eMin = eTime - eHour;
                    int hour = 0 ;
                    int min = 0;
                    if (eMin >= sMin) {
                        min = eMin - sMin;
                        hour = eHour - sHour;
                    } else {
                        eHour--;
                        min = eMin + 60 - sMin;
                        hour = eHour - sHour;
                    }

                    if (hour == 0 && min == 0) {
                        continue;
                    }
                    double pxPerMin = (100.00/60.00);
                    double downPX = pxPerMin *startMin;
                    //Drawing a Rectangle
                    storedXAxis[Time][tempCounter] = horizontalXLayout + moveRight;
                    storedYAxis[Time][tempCounter] = horizontalYLayout + downPX;
                    moveRight+=100;
                    tempCounter+=1;
                }
            }
        }
    }

    /**
     * TBW
     * Create a square block of schedule depending on the duration of the event.
     *
     * @param Time of the event to draw.
     */


    /**
     * Set text to be displayed as AM
     *
     * @param time of the event.
     */

    private void makeTimeAM(int time) {
        timeAM[time] = new Text();
        String toStore = Timing[time] + ":00 am";
        timeAM[time].setText(toStore);
        timeAM[time].setY(colOneYLayout);
        timeAM[time].setX(colOneXLayout);
        colOneYLayout += 100;
        groupRoot.getChildren().add(timeAM[time]);
    }

    /**
     * Set text to be displayed as PM
     *
     * @param time of the event.
     */
    private void makeTimePM(int time) {
        timePM[time] = new Text();
        String toStore = Timing[time] + ":00 PM";
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