package compal.tasks.AllRecurringTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import compal.main.Duke;

public class RecurTaskHandler {
    //***Class Properties/Variables***--------------------------------------------------------------------------------->

    public Duke duke; // using this to print on GUI

    //----------------------->

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Constructor.
     *
     */
    public RecurTaskHandler(Duke d) {
        this.duke = d;
    }

    //----------------------->

    /**
     * Handles the initialisation of multiple recurring tasks.
     *
     * @param recurt_input User input, containing description of task, date, and number of repetitions.
     * @param date Start date of the first task
     * @return A list of all the recurring tasks
     * @UsedIn TaskList.addTask
     */
    public ArrayList<RecurringTask> recurTaskPacker(String recurt_input, Date date) {
        String description = getDescription(recurt_input);
        int numReps = getRep(recurt_input);
        ArrayList<RecurringTask> recurTaskList = new ArrayList<RecurringTask>();
        for (int count = 0; count < numReps; count++) {
            RecurringTask recurTask = new RecurringTask(description, date);
            recurTaskList.add(recurTask);
            date = incrementDateByWeek(date);
        }
        return recurTaskList;
    }

    /**
     * Get description of the task. Different from TaskList.getDescription as it
     * does not include date of the task.
     *
     * @param input User input.
     * @return Description of the task without the date.
     * @UsedIn recurTaskPacker
     */
    public static String getDescription(String input) {
        int splitPoint = input.indexOf("/start");
        String description = input.substring(0, splitPoint);
        description.trim();
        return description;
    }

    /**
     * This function returns the number of repetitions of the recurring task in an integer form.
     *
     * @param input description string
     * @return The number of repetitions of the recurring task.
     * @UsedIn recurTaskPacker
     */
    public static int getRep(String input) {
        String repToken = "/rep";
        int splitPoint = input.indexOf(repToken);
        String repPart = input.substring(splitPoint);
        Scanner sc = new Scanner(repPart);
        sc.next(); // skip /rep word
        int repNum = sc.nextInt();
        return repNum;
    }

    /**
     * Increment the date by one week
     *
     * @param date The date to be incremented
     * @return The final incremented date.
     * @UsedIn recurTaskPacker
     */
    public static Date incrementDateByWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }
}
