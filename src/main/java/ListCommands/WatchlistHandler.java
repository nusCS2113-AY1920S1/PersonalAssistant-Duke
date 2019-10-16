package ListCommands;

import task.Deadline;
import task.Tasks;
import task.Period;
import java.util.ArrayList;
import MovieUI.MovieHandler;

public class WatchlistHandler {
    private static ArrayList<Tasks> watch;
    protected static String space =  "    ";
    public WatchlistHandler() {
        this.watch = new ArrayList<>();
    }
    public static void add(Tasks t) {
        watch.add(t);
    }
    public static void print_list(MovieHandler handle) {
        String result = "";
        result += "Here are the tasks in your list: \n";
        int index = 1;
        for (Tasks i: watch) {
            String message = "";
            switch (i.getType()) {
                case "D":
                    message = ((Deadline)(i)).toMessage();
                    break;
                case "P":
                    message = ((Period)(i)).toMessage();
                    break;
                default:
                    break;
            }
           result += space + index + ".[" + i.getType()
            + "][" + i.getStatusIcon() + "] " + message + "\n";
            index++;
        }
        handle.setFeedbackText(result);
    }
    public static void markAsDone(int index) {
        index--;
        watch.get(index).setDone(true);
    }
}
