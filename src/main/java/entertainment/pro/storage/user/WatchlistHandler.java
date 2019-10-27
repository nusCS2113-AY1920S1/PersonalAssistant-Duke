package entertainment.pro.storage.user;

import entertainment.pro.model.Deadline;
import entertainment.pro.model.Tasks;
import entertainment.pro.model.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entertainment.pro.ui.MovieHandler;

public class WatchlistHandler {
    private static ArrayList<Tasks> watch;
    protected static String space =  "    ";

    public WatchlistHandler() {
        this.watch = new ArrayList<>();
    }

    /**
     * adds a duke.task to the watchlist
     * @param t: the duke.task list to store the movies
     */
    public static boolean add(Tasks t) {
        for (Tasks i : watch) {
            if (i.getDescription().equals(t.getDescription())) {
                return false;
            }
        }
        watch.add(t);
        Collections.sort(watch, (d1, d2) -> {
            if (d1 instanceof Deadline && d2 instanceof Deadline) {
                if (((Deadline)(d1)).getDate().getEndDate() == null || ((Deadline)(d2)).getDate().getEndDate() == null)
                    return 0;
                return ((Deadline)(d1)).getDate().getEndDate().compareTo(((Deadline)(d2)).getDate().getEndDate());
            } else if (d1 instanceof Period && d2 instanceof Period) {
                if (((Period)(d1)).getDate().getEndDate() == null || ((Period)(d2)).getDate().getEndDate() == null)
                    return 0;
                return ((Period)(d1)).getDate().getEndDate().compareTo(((Period)(d2)).getDate().getEndDate());
            } else if (d1 instanceof Period && d2 instanceof Deadline) {
                if (((Period)(d1)).getDate().getEndDate() == null || ((Deadline)(d2)).getDate().getEndDate() == null)
                    return 0;
                return ((Period)(d1)).getDate().getEndDate().compareTo(((Deadline)(d2)).getDate().getEndDate());
            } else if (d1 instanceof Deadline && d2 instanceof Period) {
                if (((Deadline)(d1)).getDate().getEndDate() == null || ((Period)(d2)).getDate().getEndDate() == null)
                    return 0;
                return ((Deadline)(d1)).getDate().getEndDate().compareTo(((Period)(d2)).getDate().getEndDate());
            }
            return 0;
        });
        return true;
    }

    /**
     * prints the entire watchlist of the user
     * @param handle: class to retrieve the duke.ui controller to display the list on the users view
     */
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

    /**
     * marks the required duke.task as done and feedbacks it to the user
     * @param index: index of the movie to mark as done
     * @param handle: moviehandler class to print out the completed duke.task on the users view
     */
    public static void markAsDone(int index, MovieHandler handle) {
        try {
            String result = "I've marked this duke.task as Done: \n";
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

    /**
     * removes a particular duke.task from the watchlist by its name
     * @param movie: name of the movie to be removed
     * @param handle: moviehandler class to print out the completed duke.task on the users view
     * @return boolean: returns whether the movie was present in the watchlist or not
     */
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

    /**
     * return a list of possible movie titles to remove from the watchlist
     * @return ArrayList<String>: list of movie titles currently in the watchlist
     *
     */
    public static ArrayList<String> getTitles() {
        ArrayList<String> names = new ArrayList<>();
        for (Tasks i : watch) {
            names.add(i.getDescription());
        }
        return names;
    }
}
