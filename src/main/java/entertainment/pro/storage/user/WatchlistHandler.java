package entertainment.pro.storage.user;

import entertainment.pro.model.Deadline;
import entertainment.pro.model.Tasks;
import entertainment.pro.model.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entertainment.pro.ui.MovieHandler;

public class WatchlistHandler {
    private static ArrayList<Tasks> watch = new ArrayList<>();
    protected static String space =  "    ";

    /**
     * adds a duke.task to the watchlist
     * @param t the duke.task list to store the movies
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
                if (((Deadline)(d1)).getDate().getEndDate() == null
                        || ((Deadline)(d2)).getDate().getEndDate() == null) {
                    return 0;
                }
                return ((Deadline)(d1)).getDate().getEndDate().compareTo(((Deadline)(d2)).getDate().getEndDate());
            } else if (d1 instanceof Period && d2 instanceof Period) {
                if (((Period)(d1)).getDate().getEndDate() == null || ((Period)(d2)).getDate().getEndDate() == null) {
                    return 0;
                }
                return ((Period)(d1)).getDate().getEndDate().compareTo(((Period)(d2)).getDate().getEndDate());
            } else if (d1 instanceof Period && d2 instanceof Deadline) {
                if (((Period)(d1)).getDate().getEndDate() == null || ((Deadline)(d2)).getDate().getEndDate() == null) {
                    return 0;
                }
                return ((Period)(d1)).getDate().getEndDate().compareTo(((Deadline)(d2)).getDate().getEndDate());
            } else if (d1 instanceof Deadline && d2 instanceof Period) {
                if (((Deadline)(d1)).getDate().getEndDate() == null || ((Period)(d2)).getDate().getEndDate() == null) {
                    return 0;
                }
                return ((Deadline)(d1)).getDate().getEndDate().compareTo(((Period)(d2)).getDate().getEndDate());
            }
            return 0;
        });
        return true;
    }

    /**
     * prints the entire watchlist of the user.
     * @param handle class to retrieve the duke.ui controller to display the list on the users view
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
        handle.setGeneralFeedbackText(result);
    }

    /**
     * marks the required duke.task as done and feedbacks it to the user
     * @param index index of the movie to mark as done
     * @param handle moviehandler class to print out the completed duke.task on the users view
     */
    public static void markIndexAsDone(int index, MovieHandler handle) {
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
            handle.setGeneralFeedbackText(result);
        } catch (IndexOutOfBoundsException e) {
            handle.setGeneralFeedbackText("Please enter a valid index that is not greater than the size of the watchlist!");
        }
    }

    /**
     * removes a particular duke.task from the watchlist by its name
     * @param movie name of the movie to be removed
     * @param handle moviehandler class to print out the completed duke.task on the users view
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
     * return a list of possible movie titles to remove from the watchlist.
     * @return ArrayList list of movie titles currently in the watchlist
     *
     */
    public static ArrayList<String> getTitles() {
        ArrayList<String> names = new ArrayList<>();
        for (Tasks i : watch) {
            names.add(i.getDescription());
        }
        return names;
    }


    /**
     * return a list of possible hints from watchlist for autocomplete.
     * @param keyword incomplete command of the user
     * @return ArrayList list of movie titles currently in the watchlist that could potentially be
     * what the user is trying to type.
     *
     */
    public static ArrayList<String> getWatchListHints(String keyword) {
        ArrayList<String> hints = new ArrayList<>();
        for (Tasks i : watch) {
            if (i.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                hints.add(i.getDescription());
            }

        }
        return hints;
    }

    /**
     * searches for a movie in the watchlist to mark as its corresponding task as done
     * @param movie: title of the movie to be searched
     * @param handle: UI controller to print text to the UI textfield itself
     * @return: a boolean to determine if the movie was found or not
     */
    public static boolean markMovieAsDone(String movie, MovieHandler handle) {
        int index = 0;
        String result = "I have marked this task as done: \n";
        for (Tasks task : watch) {
            if (task.getDescription().equals(movie)) {
                task.setDone(true);
                if (task.getType().equals("D")) {
                    result += space + index + 1 + ".[" + task.getType()
                            + "][" + task.getStatusIcon() + "] " + ((Deadline) task).toMessage();
                } else {
                    result += space + index + 1 + ".[" + task.getType()
                            + "][" + task.getStatusIcon() + "] " + ((Period) task).toMessage();
                }
                handle.setGeneralFeedbackText(result);
                return true;
            }
            index++;
        }
        return false;
    }

    /**
     * check if the watchlist class contains a movie
     * @param movie: name of the movie title to be searched
     * @return a boolean representing if a movie is contained in the array list or not
     */
    public static boolean contains(String movie) {
        for (Tasks i : watch) {
            if (i.getDescription().equals(movie)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  gets the size of the watchlist
     * @return an integer denoting the size of the watchlist
     */
    public static int getSize() {
        return watch.size();
    }

    /**
     * returns the watchlist for the user to view
     * @return watchlist for the user to view
     */
    public static ArrayList<Tasks> getWatchlist() {
        return watch;
    }
}
