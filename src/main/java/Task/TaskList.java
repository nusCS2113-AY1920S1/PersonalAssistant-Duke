package Task;

import Data.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * TaskList handles all the operations Duke uses
 */
public class TaskList {
    private static ArrayList<item> list = new ArrayList<>();

    /**
     * This method loads all the ArrayList items from the previous load file into the current ArrayList
     */
    public static void addAllList () {
        try {
            list.addAll(Objects.requireNonNull(Storage.loadFile()));
        }
        catch (NullPointerException e) {
            System.out.println("No previous list");
        }
    }

    /**
     * This method adds tasks to the list of tasks in duke
     *
     * @param i This is the first parameter, it takes the newly created Deadline/ToDo/Event
     * @param type This is the second parameter, defines the type of task that has been created
     */
    public static void addTask(item i, String type) {
        list.add(i);
        System.out.println("Got it. I've added this task:\n " +
                list.get(list.size() - 1).toString()+"\n" +
                "Now you have " + (list.size()) + " tasks in the list.");
        Data.Storage.saveFile(type, i, i.getDate());
    }

    /**
     * This function prints out the complete list of tasks in the ArrayList
     */
    public static void getList() {
        int count = 1;
        for (item i: list) {
            System.out.println(count++ +"."+ i.toString());
        }
    }

    /**
     * This function changes the status of a task from incomplete to complete
     *
     * @param index This is the index location of the task to be changed in the ArrayList
     */
    public static void doneTask (int index) {
        list.get(index).changeStatus();
        System.out.println("Nice! I've marked this task as done:\n " +
                list.get(index).toString());
        Storage.updateFile(list);
    }

    /**
     * This function deletes the task from the list of tasks.
     * After deleting the function will print out what was deleted and the number of remaining
     * tasks left in the list.
     *
     * @param index This is the index location of the task to be deleted in the ArrayList
     * @throws IndexOutOfBoundsException e
     */
    public static void deleteTask(int index) {
        System.out.println("Noted. I've removed this task:\n " + list.get(index).toString());
        System.out.println("Now you have " + (list.size() - 1) + " tasks in the list.");
        try {
            list.remove(index);
            Storage.updateFile(list);
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Opps! Sorry that item does not exist!");
        }
    }

    /**
     * This function locates all tasks that contain a keyword as defined by the user
     *
     * @param word This parameter is the defined key word that must be searched for
     */
    public static void findTask (String word) {
        int cnt = 1;
        for (item i : list) {
            if (i.getInfo().contains(word)) {
                if (cnt == 1)
                    System.out.println("Here are the matching tasks in your list:");
                System.out.println(cnt++ + ". " + i.toString());
            }
        }

        if (cnt == 1) {
            System.out.println("Sorry, there are no tasks matching your search");
        }
    }

    /**
     * This function takes in an integer number adds its correct number ordinal to the number
     *
     * @param num This parameter is the number taken
     * @return String of the input number with the ordinal attached to the end of the number
     */
    public static String numOrdinal (int num) {
        String[] suffix = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (num % 100) {
            case 11:
            case 12:
            case 13:
                return num + "th";
            default:
                return num + suffix[num % 10];
        }
    }

    /**
     * This function takes specific date format dd/mm/yyyy hhmm and turns it into a string phrase
     *
     * @param date The date taken in by the function
     * @return The date that has been converted into a string phrase, if in incorrect format return original date
     * @throws StringIndexOutOfBoundsException e
     * @throws ArrayIndexOutOfBoundsException e
     */
    public static String dateConvert (String date) {
        String[] months = {"January", "February", "March",
                "April", "May", "June", "July",
                "August", "September","October",
                "November", "December"};

        try {
            String[] words = date.split("[/| ]"); // split based on space and /
            System.out.println(words[3]);
            int hour = Integer.parseInt(words[3].substring(0,2));
            int min = Integer.parseInt(words[3].substring(2));
            String mm = (hour > 12) ? "pm" : "am";
            String wordMin = words[3].substring(2);
            String d1 =  numOrdinal(Integer.parseInt(words[0])) + " of " + months[Integer.parseInt(words[1]) - 1] + " " +
                    words[2] + ", " + (hour % 12) + ((min == 0) ? "": ("."+ wordMin)) + mm;
            //2nd of December 2019, 6pm
            return d1;
        }
        catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
            return date;
        }
    }

}
