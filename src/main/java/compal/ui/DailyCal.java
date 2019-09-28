package compal.ui;


import compal.compal.Compal;
import compal.tasks.Task;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class DailyCal {
    private Compal compal = new Compal();
    public int horizontalLineCounter = 0;
    private ScrollPane sp = new ScrollPane();
    private Group groupRoot = new Group();
    private Line[] horizontalLines = new Line[50];
    private Line[] verticalLines = new Line[50];
    private Text[] date = new Text[50];
    private Text[] timeAM = new Text[50];
    private Text[] timePM = new Text[50];

    double verticalYLayout = -17;
    double verticalXLayout = 180;
    double horizontalYLayout = 0;
    double horizontalXLayout = 100;
    private double timeXLayout = 100;
    private double timeYLayout = -5;


    private int[] Timing = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};

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
        setStartTime();
        genDateSLot(0);
        genTimeSlot();
        genSquareTimeTable();
        sp.setContent(groupRoot);
        return sp;
    }

    void setStartTime(){
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        ArrayList<Task> arrList = compal.tasklist.arrlist;
        for(int i = 0; i < arrList.size();i++){
            if(arrList.get(i).getStringDate().equals(dateInString)){
                int tempStarTime = Integer.parseInt(arrList.get(i).getStringTime().substring(0,2));
                if(tempStarTime < startTime){
                    startTime = tempStarTime;
                }
            }
        }
    }

    void drawScheduleSquare(int Time){
        ArrayList<Task> arrList = compal.tasklist.arrlist;

        for(int i = 0; i < arrList.size();i++){
            if(arrList.get(i).getStringDate().equals(todayDate)){
                if(Integer.parseInt(arrList.get(i).getStringTime().substring(0,2)) == Time){
                    System.out.println(Time);

                    int hour = arrList.get(i).getDurationHour();
                    int min = arrList.get(i).getDurationMinute();

                    String desc = arrList.get(i).getDescription();
                    //Drawing a Rectangle
                    double heightY = 1;
                    double heightYMin= heightY * min;
                    double heightYHour = 100 * hour;


                    Rectangle rectangle= new Rectangle(100,heightYHour+heightYMin);
                    rectangle.setFill(Color.RED);
                    final StackPane stack = new StackPane();
                    final Text text = new Text (desc);
                    stack.getChildren().addAll(rectangle, text);
                    stack.setLayoutX(horizontalXLayout+80);
                    stack.setLayoutY(horizontalYLayout);
                    groupRoot.getChildren().add(stack);
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

    public void genSquareTimeTable() {
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        ArrayList<Task> arrList = compal.tasklist.arrlist;
        for(int i = 0; i < arrList.size();i++){
            if(arrList.get(i).getStringDate().equals(dateInString)){
            }
        }

    }

    public void genTimeSlot() {
        for (int i = 0; i < Timing.length; i++) {
            makeASlot(i);
        }
        makeHeaderVerticalLines(0);
    }

    public void makeASlot(int i) {
        int temp = horizontalLineCounter;
        if (Timing[i] < startTime || Timing[i] >= endTime) {
            return;
        } else if (Timing[i] < 12) {
            makeTimeAM(i);
            drawScheduleSquare(Timing[i]);
            for (int x = temp; x < temp + 2; x++) {
                makeVertLines(x);
                horizontalLineCounter++;
            }
        } else {
            makeTimePM(i);
            drawScheduleSquare(Timing[i]);
            for (int x = temp; x < temp + 2; x++) {
                makeVertLines(x);
                horizontalLineCounter++;
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

    public void makeVertLines(int i) {
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

        verticalXLayout += 0;
        verticalYLayout += 0;
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