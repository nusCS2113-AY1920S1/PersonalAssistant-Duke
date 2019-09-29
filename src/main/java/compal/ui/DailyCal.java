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

public class DailyCal {
    public int horizontalLineCounter = 0;
    double verticalYLayout = -17;
    double verticalXLayout = 180;
    double horizontalYLayout = 0;
    double horizontalXLayout = 100;
    private Compal compal = new Compal();
    private ScrollPane sp = new ScrollPane();
    private Group groupRoot = new Group();
    private Line[] horizontalLines = new Line[50];
    private Line[] verticalLines = new Line[50];
    private Text[] date = new Text[50];
    private Text[] timeAM = new Text[50];
    private Text[] timePM = new Text[50];
    private double timeXLayout = 100;
    private double timeYLayout = -5;


    private int[] Timing = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};

    private int startTime = 8;
    private int endTime = 19;
    private String pattern = "dd/MM/yyyy";
    private String todayDate = new SimpleDateFormat(pattern).format(new Date());

    public DailyCal() {
    }

    public ScrollPane init() {
        sp = buildTimeTable();
        return sp;
    }

    public ScrollPane buildTimeTable() {
        setTime();
        genDateSLot(0);
        genTimeSlot();
        sp.setContent(groupRoot);
        return sp;
    }

    void setTime() {
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        ArrayList<Task> arrList = compal.tasklist.arrlist;
        for (int i = 0; i < arrList.size(); i++) {
            if (arrList.get(i).getStringDate().equals(dateInString)) {
                int tempTime = Integer.parseInt(arrList.get(i).getStringStartTime().substring(0, 2));
                if (tempTime < startTime) {
                    startTime = tempTime;
                }
                if (tempTime > endTime) {
                    endTime = tempTime;
                }
            }
        }
    }

    public void genDateSLot(int numOfDays) {
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        date[numOfDays] = new Text();
        date[numOfDays].setText(dateInString);
        date[numOfDays].setY(timeYLayout);
        date[numOfDays].setX(timeXLayout);
        timeYLayout += 30;
        groupRoot.getChildren().add(date[numOfDays]);
    }

    public void genTimeSlot() {
        for (int i = 0; i < Timing.length; i++) {
            makeASlot(i);
        }
        makeHorizontalLines(horizontalLineCounter++);
        makeHeaderVerticalLines(0);
    }




    public void makeASlot(int i) {
        int temp = horizontalLineCounter;
        if (Timing[i] < startTime || Timing[i] > endTime) {
            return;
        } else if (Timing[i] < 12) {
            makeTimeAM(i);
            for (int x = temp; x < temp + 2; x++) {
                makeHorizontalLines(x);
                if (x == temp) {
                    drawScheduleSquare(Timing[i]);
                }
                horizontalLineCounter++;
            }
        } else {
            makeTimePM(i);
            for (int x = temp; x < temp + 2; x++) {
                makeHorizontalLines(x);
                if (x == temp) {
                    drawScheduleSquare(Timing[i]);
                }
                horizontalLineCounter++;
            }
        }
    }

    void drawScheduleSquare(int Time) {
        ArrayList<Task> arrList = compal.tasklist.arrlist;
        for (int i = 0; i < arrList.size(); i++) {
            if (arrList.get(i).getStringDate().equals(todayDate)) {
                if (Integer.parseInt(arrList.get(i).getStringStartTime().substring(0, 2)) == Time) {
                    int hour = arrList.get(i).getDurationHour();
                    int min = arrList.get(i).getDurationMinute();
                    if (hour == 0 && min == 0) {
                        continue;
                    }
                    String desc = arrList.get(i).getDescription();
                    //Drawing a Rectangle
                    double heightY = 1.7;
                    double heightYMin = heightY * min;
                    double heightYHour = 100 * hour;
                    Rectangle rectangle = new Rectangle(100, heightYHour + heightYMin);
                    rectangle.setFill(Color.RED);
                    final StackPane stack = new StackPane();
                    final Text text = new Text(desc);
                    stack.getChildren().addAll(rectangle, text);
                    stack.setLayoutX(horizontalXLayout+80);
                    stack.setLayoutY(horizontalYLayout);
                    groupRoot.getChildren().add(stack);
                }
            }
        }
    }

    public void makeTimeAM(int i) {
        timeAM[i] = new Text();
        String toStore = Timing[i] + ":00 am";
        timeAM[i].setText(toStore);
        timeAM[i].setY(timeYLayout);
        timeAM[i].setX(timeXLayout);
        timeYLayout += 100;
        groupRoot.getChildren().add(timeAM[i]);
    }

    public void makeTimePM(int i) {
        timePM[i] = new Text();
        String toStore = Timing[i] + ":00 PM";
        timePM[i].setText(toStore);
        timePM[i].setY(timeYLayout);
        timePM[i].setX(timeXLayout);
        timeYLayout += 100;
        groupRoot.getChildren().add(timePM[i]);
    }

    public void makeHorizontalLines(int i) {
        if (i == 0) {
            drawHeaderHorizontalLines(i);
        } else {
            drawHorizontalLines(i);
        }
    }

    public void drawHeaderHorizontalLines(int i) {
        horizontalLines[i] = new Line();
        horizontalLines[i].setStartX(0);
        horizontalLines[i].setStartY(0);
        horizontalLines[i].setEndX(600);
        horizontalLines[i].setEndY(0);
        horizontalLines[i].setLayoutY(horizontalYLayout);
        horizontalLines[i].setLayoutX(horizontalXLayout);
        groupRoot.getChildren().add(horizontalLines[i]);
        horizontalXLayout += 0;
        horizontalYLayout += 30;
    }

    public void drawHorizontalLines(int i) {
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


    public void makeHeaderVerticalLines(int i) {
        verticalLines[i] = new Line();
        verticalLines[i].setStartX(0);
        verticalLines[i].setStartY(0);
        verticalLines[i].setEndX(0);
        verticalLines[i].setEndY(horizontalYLayout);
        verticalLines[i].setLayoutX(verticalXLayout);
        verticalLines[i].setLayoutY(verticalYLayout);
        groupRoot.getChildren().add(verticalLines[i]);
    }


    public void makeVerticalLines() {
        double x = 100;
        double y = 0;
        for (int i = 0; i < 6; i++) {
            horizontalLines[i] = new Line();
            horizontalLines[i].setStartX(0);
            horizontalLines[i].setStartY(-15);
            horizontalLines[i].setEndX(0);
            horizontalLines[i].setEndY(455);
            horizontalLines[i].setLayoutX(x);
            horizontalLines[i].setLayoutY(y);
            x += 100;
            y = 0;
            groupRoot.getChildren().add(horizontalLines[i]);
        }
    }


}