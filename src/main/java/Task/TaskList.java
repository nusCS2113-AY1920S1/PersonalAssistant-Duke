package Task;

import Data.*;
import java.util.ArrayList;
import java.util.Objects;


public class TaskList {
    private static ArrayList<item> list = new ArrayList<>();

    public static void addAllList () {
        try { //load previous file
            list.addAll(Objects.requireNonNull(Storage.loadFile()));
        }
        catch (NullPointerException e) {
            System.out.println("No previous list");
        }
    }

    public static void addTask(item i, String type) {
        list.add(i);
        System.out.println("Got it. I've added this task:\n " +
                list.get(list.size() - 1).toString()+"\n" +
                "Now you have " + (list.size()) + " tasks in the list.");
        Data.Storage.saveFile(type, i, i.getDate());
    }

    public static void getList() {
        int count = 1;
        for (item i: list) {
            System.out.println(count++ +"."+ i.toString());
        }
    }

    public static void doneTask (int index) {
        list.get(index).changeStatus();
        System.out.println("Nice! I've marked this task as done:\n " +
                list.get(index).toString());
        Storage.updateFile(list);
    }

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
        catch (StringIndexOutOfBoundsException e) {
            return date;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return date;
        }
    }

}
