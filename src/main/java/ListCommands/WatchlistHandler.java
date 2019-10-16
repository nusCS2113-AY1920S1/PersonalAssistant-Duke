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

    public static void markAsDone(int index, MovieHandler handle) {
        try {
            String result = "I've marked this task as Done: \n";
            index--;
            Tasks t = watch.get(index);
            watch.get(index).setDone(true);
            if (t.getType().equals("D")) {
                result += space + index + 1 + ".[" + t.getType()
                        + "][" + t.getStatusIcon() + "] " + ((Deadline) t).toMessage();
            } else {
                result += space + index + 1 + ".[" + t.getType()
                        + "][" + t.getStatusIcon() + "] " + ((Period) t).toMessage();
            }
            handle.setFeedbackText(result);
        } catch (IndexOutOfBoundsException e) {
            handle.setFeedbackText("Please enter a valid index that is not greater than the size of the watchlist!");
        }
    }
    public static boolean removeFromWatchlist(String movie, MovieHandler handle) {
        movie = movie.toLowerCase();
        int index = -1;
        for (int i = 0; i < watch.size(); i++) {
            if (watch.get(i).getDescription().equals(movie)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            watch.remove(index);
            return true;
        }
        return false;
    }
}
