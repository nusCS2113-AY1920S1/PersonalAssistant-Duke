package duke.gui;

import duke.exceptions.DukeException;
import duke.tasks.Meal;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//@@author koushireo-unused

public class GUI extends Application {
    private static HashMap<String, Integer> weight;
    private static HashMap<String, ArrayList<Meal>> mealList;
    private static String startDate;
    private static String endDate;
    private static String type;
    private Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    public void setWeight(HashMap<String, Integer> weight) {
        this.weight = weight;
    }

    public void setMealList(HashMap<String, ArrayList<Meal>> mealList) {
        this.mealList = mealList;
    }

    public void setStartDate(String date) {
        this.startDate = date;
    }

    public void setEndDate(String date) {
        this.endDate = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void start(Stage stage) throws DukeException {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("calorie");

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        XYChart.Series dataSeries2 = new XYChart.Series();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(this.startDate, formatter);
        LocalDate endDate = LocalDate.parse(this.endDate, formatter);
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(this.startDate);
            cal.setTime(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i <= days; i += 1) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String current = dateFormat.format(cal.getTime());
            System.out.println(current);
            if (this.type.equals("weight")) {
                if (weight.containsKey(current)) {
                    System.out.println(weight.get(current));
                    dataSeries1.getData().add(new XYChart.Data(current, weight.get(current)));
                    dataSeries2.getData().add(new XYChart.Data(current, 100));
                } else {
                    System.out.println(0);
                    dataSeries1.getData().add(new XYChart.Data(current, 0));
                    dataSeries2.getData().add(new XYChart.Data(current, 100));
                }
            } else {
                int value = 0;
                if (!mealList.containsKey(current)){
                    dataSeries1.getData().add(new XYChart.Data(current, value));
                }
                else {
                    ArrayList<Meal> currentMealInDay = mealList.get(current);
                    for (int j = 0; j < currentMealInDay.size(); j += 1) {
                        HashMap<String, Integer> currentMeal = currentMealInDay.get(j).getNutritionalValue();
                        if (currentMeal.containsKey(this.type)) {
                            value += currentMeal.get(this.type);
                        }
                    }
                    System.out.println(value);
                    dataSeries1.getData().add(new XYChart.Data(current, value));
                }
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        lineChart.getData().add(dataSeries1);
        lineChart.getData().add(dataSeries2);
        HBox hbox = new HBox(lineChart);

        Scene scene = new Scene(hbox,500,500);
        stage.setTitle("duke");
        stage.setScene(scene);
        stage.show();
    }
}
