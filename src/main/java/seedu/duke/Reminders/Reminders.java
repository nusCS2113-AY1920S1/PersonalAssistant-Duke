package seedu.duke.Reminders;

import seedu.duke.task.Deadline;
import seedu.duke.task.Task;
import seedu.duke.task.Event;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Display reminders list when the app first start
 * Will need to type command "remind" to bring up reminders after that
 */
public class Reminders {
    /*The different list for different types of reminders*/
    private ArrayList<Task> LastDayList = new ArrayList<>();
    private ArrayList<Task> LastThirtyList = new ArrayList<>();
    private ArrayList<Task> OverdueList = new ArrayList<>();
    private String filePath = "data/duke.txt";
    /*Get the current date and time*/
    private Calendar currentDate = Calendar.getInstance();
    private int currentYear = currentDate.get(Calendar.YEAR);
    private int currentMonth = currentDate.get(Calendar.MONTH) + 1;
    private int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
    private int currentHour = currentDate.get(Calendar.HOUR_OF_DAY) * 100;
    private int currentMinute = currentDate.get(Calendar.MINUTE);
    private int currentTime = currentHour + currentMinute;
    public Reminders(){}

    /**
     * Creates a reminder list when it is the last day for the tasks/task to be done base on real actual time
     *
     * @return an ArrayList of type Task
     */
    public ArrayList<Task> LastDay(){
        try{
            Scanner duke_txt = new Scanner(new File(this.filePath));
            while (duke_txt.hasNextLine()) {
                // splits line input based on |
                String task_string[] = duke_txt.nextLine().split("\\|");
                //compare time and date with current time and date
                if (task_string[0].equals("D") && !task_string[1].equals("1")) {
                    String date_string[] = task_string[3].split("/| ");
                    int day = Integer.parseInt(date_string[0]);
                    int month = Integer.parseInt(date_string[1]);
                    int year = Integer.parseInt(date_string[2]);
                    int time = Integer.parseInt(date_string[3]);
                    if((currentYear - year == 0) && (currentMonth - month == 0) && (currentDay - day == 0) && (time - currentTime > 30 && time - currentTime <= 2359)){
                        LastDayList.add(new Deadline(task_string[2], task_string[3]));
                    }
                }
                else if(task_string[0].equals("E") && !task_string[1].equals("1")){
                    String date_string[] = task_string[3].split("/| ");
                    int day = Integer.parseInt(date_string[0]);
                    int month = Integer.parseInt(date_string[1]);
                    int year = Integer.parseInt(date_string[2]);
                    int time = Integer.parseInt(date_string[3]);
                    if((currentYear - year == 0) && (currentMonth - month == 0) && (currentDay - day == 0) && (time - currentTime > 30 && time - currentTime <= 2359)){
                        LastDayList.add(new Event(task_string[2], task_string[3]));
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
        }
        return LastDayList;
    }

    /**
     * Creates a reminder list when there is only 30 mins or less left for the tasks/task to be done based on the real actual time
     *
     * @return an ArrayList of type Task
     */
    public ArrayList<Task>LastThirty(){
        try{
            Scanner duke_txt = new Scanner(new File(this.filePath));
            while (duke_txt.hasNextLine()) {
                // splits line input based on |
                String task_string[] = duke_txt.nextLine().split("\\|");
                //compare time and date with current time and date
                if (task_string[0].equals("D") && !task_string[1].equals("1")) {
                    String date_string[] = task_string[3].split("/| ");
                    int day = Integer.parseInt(date_string[0]);
                    int month = Integer.parseInt(date_string[1]);
                    int year = Integer.parseInt(date_string[2]);
                    int time = Integer.parseInt(date_string[3]);
                    if((currentYear - year == 0) && (currentMonth - month == 0) && (currentDay - day == 0) && (time - currentTime > 0 && time - currentTime <= 30)){
                        LastThirtyList.add(new Deadline(task_string[2], task_string[3]));
                    }
                }
                else if(task_string[0].equals("E") && !task_string[1].equals("1")){
                    String date_string[] = task_string[3].split("/| ");
                    int day = Integer.parseInt(date_string[0]);
                    int month = Integer.parseInt(date_string[1]);
                    int year = Integer.parseInt(date_string[2]);
                    int time = Integer.parseInt(date_string[3]);
                    if((currentYear - year == 0) && (currentMonth - month == 0) && (currentDay - day == 0) && (time - currentTime > 0 && time - currentTime <= 30)){
                        LastThirtyList.add(new Event(task_string[2], task_string[3]));
                    }
                }
            }
        }
        catch (FileNotFoundException e) {

        }
        return LastThirtyList;
    }
    /**
     * Creates a reminder list over overdue tasks/task
     *
     * @return an ArrayList of type Task
     */
    public ArrayList<Task> OverDue(){
        try {
            Scanner duke_txt = new Scanner(new File(this.filePath));
            while (duke_txt.hasNextLine()) {
                // splits line input based on |
                String task_string[] = duke_txt.nextLine().split("\\|");
                if (task_string[0].equals("D") && !task_string[1].equals("1")){
                    String date_string[] = task_string[3].split("/| ");
                    int day = Integer.parseInt(date_string[0]);
                    int month = Integer.parseInt(date_string[1]);
                    int year = Integer.parseInt(date_string[2]);
                    int time = Integer.parseInt(date_string[3]);
                    if (year - currentYear < 0) {
                        OverdueList.add(new Deadline(task_string[2], task_string[3]));
                    }
                    else if(year - currentYear == 0 && month - currentMonth < 0){
                        OverdueList.add(new Deadline(task_string[2], task_string[3]));
                    }
                    else if(year - currentYear == 0 && month - currentMonth == 0 && day - currentDay < 0){
                        OverdueList.add(new Deadline(task_string[2], task_string[3]));
                    }
                    else if(year - currentYear == 0 && month - currentMonth == 0 && day - currentDay == 0 && time - currentTime <= 0) {
                        OverdueList.add(new Deadline(task_string[2], task_string[3]));
                    }
                }
                else if (task_string[0].equals("E") && !task_string[1].equals("1")){
                    String date_string[] = task_string[3].split("/| ");
                    int day = Integer.parseInt(date_string[0]);
                    int month = Integer.parseInt(date_string[1]);
                    int year = Integer.parseInt(date_string[2]);
                    int time = Integer.parseInt(date_string[3]);
                    if (year - currentYear < 0) {
                        OverdueList.add(new Event(task_string[2], task_string[3]));
                    }
                    else if(year - currentYear == 0 && month - currentMonth < 0){
                        OverdueList.add(new Event(task_string[2], task_string[3]));
                    }
                    else if(year - currentYear == 0 && month - currentMonth == 0 && day - currentDay < 0){
                        OverdueList.add(new Event(task_string[2], task_string[3]));
                    }
                    else if(year - currentYear == 0 && month - currentMonth == 0 && day - currentDay == 0 && time - currentTime <= 0) {
                        OverdueList.add(new Event(task_string[2], task_string[3]));
                    }
                }
            }
        }
        catch (FileNotFoundException e) { }
        return OverdueList;
    }

    /**
     * Display the list of different reminders
     * If there is no reminders, no list will be displayed
     */
    public void displayReminder(){
        if(!LastDayList.isEmpty()){
            System.out.println("\t_____________________________________");
            System.out.println("\tREMINDER!!! Last 24 hours or less to finish task/tasks!");
            for(int i = 0; i < LastDayList.size(); i += 1){
                System.out.println("\t" + (i + 1) + "." + LastDayList.get(i));
            }
            System.out.println("\t_____________________________________");
        }
        else if(LastDayList.isEmpty()){
            System.out.println("\t_____________________________________");
            System.out.println("\tNo tasks/task due in 24 hours!");
            System.out.println("\t_____________________________________");
        }
        if(!LastThirtyList.isEmpty()){
            System.out.println("\t_____________________________________");
            System.out.println("\tREMINDER!!! Last 30 mins or less to finish task/tasks!");
            for(int i = 0; i < LastThirtyList.size(); i += 1){
                System.out.println("\t" + (i + 1) + "." + LastThirtyList.get(i));
            }
            System.out.println("\t_____________________________________");
        }
        else if(LastThirtyList.isEmpty()){
            System.out.println("\t_____________________________________");
            System.out.println("\tNo tasks/task due in 30 mins!");
            System.out.println("\t_____________________________________");
        }
        if(!OverdueList.isEmpty()){
            System.out.println("\t_____________________________________");
            System.out.println("\tTask/Tasks here is/are overdue!!!");
            for(int i = 0; i < OverdueList.size(); i += 1){
                System.out.println("\t" + (i + 1) + "." + OverdueList.get(i));
            }
            System.out.println("\t_____________________________________");
        }
        else if(OverdueList.isEmpty()){
            System.out.println("\t_____________________________________");
            System.out.println("\tNo overdue tasks!");
            System.out.println("\t_____________________________________");
        }
    }

    /**
     * Creates the different types of reminders
     */
    public void runAll(){
        LastDay();
        LastThirty();
        OverDue();
    }
}

