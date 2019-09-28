package compal.ui;


import compal.compal.Compal;
import compal.tasks.Task;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DailyCal {
    private Compal compal = new Compal();
    public int horizontalLineCounter = 0;
    Group groupRoot = new Group();
    Line[] horizontalLines = new Line[50];
    Line[] verticalLines = new Line[50];
    Text[] date = new Text[50];
    Text[] timeAM = new Text[50];
    Text[] timePM = new Text[50];
    double verticalYLayout = -17;
    double verticalXLayout = 180;
    double horizontalYLayout = 0;
    double horizontalXLayout = 100;
    private ScrollPane sp = new ScrollPane();
    private int[] Timing = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private double timeXLayout = 100;
    private double timeYLayout = -5;
    private int startMorningTime = 8;
    private int endNightTime = 19;

    public DailyCal() {
        /*sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        vbox.setPrefSize(633,500);*/
    }

    public ScrollPane init() {
        sp = buildTimeTable();

        return sp;
    }

    public ScrollPane buildTimeTable() {
        genDateSLot(0);
        genTimeSlot();
        genSquareTimeTable();
        sp.setContent(groupRoot);
        return sp;
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
                System.out.println("DEH TO" + arrList.get(i).getAllDetailsAsString());
                System.out.println(arrList.get(i).getStringTime());
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

        if (Timing[i] < startMorningTime || Timing[i] >= endNightTime) {
            return;
        } else if (Timing[i] < 12) {
            makeTimeAM(i);

            for (int x = temp; x < temp + 2; x++) {
                makeVertLines(x);
                horizontalLineCounter++;
                System.out.println(horizontalLineCounter);
            }
        } else {
            makeTimePM(i);
            for (int x = temp; x < temp + 2; x++) {
                makeVertLines(x);
                horizontalLineCounter++;
                System.out.println(horizontalLineCounter);
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