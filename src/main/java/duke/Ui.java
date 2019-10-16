package duke;

import duke.components.Bar;
import duke.components.Song;
import duke.tasks.RecurringTask;
import duke.tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class used to read the input duke.commands (for the command line implementation of duke.Duke) and
 * return the messages to be displayed in duke.Duke as a response to the user's input messages.
 */
public class Ui {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Reads the input message of the user.
     *
     * @return the input message of the user
     */
    String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Returns a welcome message for duke.Duke.
     *
     * @return a welcome message for duke.Duke
     */
    String showWelcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        return "Hello from\n" + logo + wrap("What can I do for you?\n");
    }

    /**
     * Returns a final message from duke.Duke in the case of termination.
     *
     * @return a terminal message from duke.Duke.
     */
    public static String showByeMessage() {
        return wrap("Bye. Hope to see you again soon!");
    }

    /**
     * Returns the error message associated with the caught duke.DukeException.
     *
     * @param e the duke.DukeException that was caught
     * @return the error message associated with the duke.DukeException
     */
    String showError(DukeException e) {
        return e.getMessage();
    }

    /**
     * Returns the String but wrapped in between two horizontal lines for enhanced
     * reading and display on the command line interface.
     *
     * @param content the String to be wrapped with horizontal lines
     * @return the wrapped String to be displayed
     */
    static String wrap(String content) {
        return ("\n__________________________________\n"
                + content
                + "\n__________________________________\n");
    }

    /**
     * Returns a String formatted for display that shows the results of the find command.
     *
     * @param list the results of the find command
     * @return the formatted String to be displayed
     */
    public String formatFind(ArrayList<Task> list) {
        StringBuilder result = new StringBuilder();
        if (list.size() == 0) {
            result.append("No such results!");
        } else {
            result.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < list.size(); i++) {
                result.append(i + 1)
                        .append(". ")
                        .append(list.get(i).toString());
                if (i != list.size() - 1) {
                    result.append("\n");
                }
            }
        }
        return wrap(result.toString());
    }

    /**
     * Returns a String formatted for display that shows all the elements in the task list
     * due to the list command.
     *
     * @param list the task list
     * @return the formatted String to be displayed
     */
    public String formatList(ArrayList<Task> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(i + 1)
                    .append(". ")
                    .append(list.get(i).toString());
            if (i != list.size() - 1) {
                result.append("\n");
            }
        }
        if (list.size() == 0) {
            result.append("The list is empty!");
        }
        return wrap(result.toString());
    }

    /**
     * Returns a String formatted for display that indicates that a task has been marked as done
     * by the done command.
     *
     * @param list the task list
     * @param index the index of the duke.tasks.Task object that was marked as done
     * @return the formatted String to be displayed
     */
    public String formatDone(ArrayList<Task> list, int index) {
        String result;
        if (list.get(index - 1) instanceof RecurringTask) {
            result = "Nice! I've marked this task as done:\n "
                    + ((RecurringTask)list.get(index - 1)).toOldString()
                    + "\n";
        } else {
            result = "Nice! I've marked this task as done:\n "
                    + list.get(index - 1).toString()
                    + "\n";

        }
        return wrap(result);
    }

    /**
     * Returns a String formatted for display that indicates that a task has been deleted by
     * the done command.
     *
     * @param list the task list prior to deletion
     * @param index the index of the item that was deleted
     * @return the formatted String to be displayed
     */
    public String formatDelete(ArrayList<Task> list,ArrayList<Task> newList, int index) {
        String word = (list.size() == 2) ? "task" : "tasks";
        String result = "Noted! I've removed this task:\n "
                + list.get(index - 1).toString()
                + "\n"
                + "Now you have "
                + (newList.size())
                + " "
                + word
                + " in the list.";
        return wrap(result);
    }

    /**
     * Returns a String formatted for display that indicates that a duke.tasks.Task object has been added
     * by the add command.
     *
     * @param list the task list
     * @param task the item that was added to the task list
     * @return the formatted String to be displayed
     */
    public String formatAdd(ArrayList<Task> list, Task task) {
        String word = (list.size() == 1) ? "task" : "tasks";
        String result = "Got it. I've added this task:\n  "
                + task.toString()
                + "\nNow you have "
                + list.size()
                + " "
                + word
                + " in the list.";
        return wrap(result);
    }

    /**
     * Returns a String for the items that need to be reminded from a list.
     *
     * @param list the task list.
     * @return the formatted String to be displayed.
     */
    public String formatReminder(ArrayList<Task> list) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append("The following tasks are due in 3 hours or less!\n\n");
        for (int i = 0; i < list.size(); i++) {
            result.append(i + 1)
                    .append(". ")
                    .append(list.get(i).toString());
            if (i != list.size() - 1) {
                result.append("\n");
            }
        }
        return wrap(result.toString());

    }

    /**
     * Returns a String formatted for display that indicates that a duke.components.Song object has been added
     * by the new command.
     *
     * @param list the song list
     * @param song the item that was added to the song list
     * @return the formatted String to be displayed
     */
    public String formatNewSong(ArrayList<Song> list, Song song) {
        String word = (list.size() == 1) ? "song" : "songs";
        String result = "Got it. I've added this song:\n  "
                + song.getName()
                + "\nNow you have "
                + list.size()
                + " "
                + word
                + " in the list.";
        return wrap(result);
    }

    /**
     * Returns a String formatted for display that shows all the elements in the command list
     * due to the help command.
     * @param list the command list
     * @return the formatted String to be displayed
     */
    public String formatHelp(ArrayList<Song> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(i + 1)
                    .append(". ")
                    .append(list.get(i).toString());
            if (i != list.size() - 1) {
                result.append("\n");
            }
        }
        if (list.size() == 0) {
            result.append("The help list for that command is empty!");
        }
        return wrap(result.toString());
    }

    /**
     * Returns a String formatted for display that shows a song in the song list due to the view command.
     * @param song the item that is to be displayed
     * @return the formatted String to be displayed
     */
    public String formatView(Song song) {
        return song.getBars().toString();
    }

    /**
     * Returns a String formatted for display that indicates that a duke.components.Bar object has been added
     * by the addbar command.
     *
     * @param list the song list
     * @param song the item that was modified
     * @return the formatted String to be displayed
     */
    public String formatAddBar(ArrayList<Song> list, Bar bar, Song song) {
        String word = (list.size() == 1) ? "bar" : "bars";
        String result = "Got it. I've added this bar:\n  "
                + bar.toString()
                + "\nto "
                + song.getName()
                + "\nNow you have "
                + song.getBars().size()
                + " "
                + word
                + " in the song.";
        return wrap(result);
    }
    /**
     * Returns a String formatted for display that indicates that
     * a duke.components.AddOverlay object has been created
     * by the group command.
     * @param list array of song list
     * @param index this is the index of the bar being copied
     * @param song the song that is being copied to
     * @return the formatted String to be displayed
     */

    public String formatAddOverlay(ArrayList<Song> list, int index,Song song) {
        String result = "Got it. I've added this overlay:\n  "
                + "bar" + new Integer(index).toString() + "\nto "
                + song.getName();
        return wrap(result);
    }
    /**
     * Returns a String formatted for display that indicates that
     * a duke.components.Group object has been created
     * by the group command.
     * @param start starting bar number for the verse
     * @param end ending bar number for the verse
     * @param name name of the verse
     * @return the formatted String to be displayed
     */

    public String formatGroupBar(int start, int end, String name) {
        String result = "Got it. Successfully grouped bars "
                + start
                + " to "
                + end
                + " as "
                + name;
        return result;
    }

    /**
     * Returns a String formatted for display that indicates that
     * some bars or verse has been copied and pasted successfully to the
     * current track.
     * @param verseName name of the verse copied and pasted
     * @param copyStartNum starting bar number for to be copied (inclusive)
     * @param copyEndNum ending bar number to stop copying (exclusive)
     * @param pasteStartNum bar number on the track where the copied content is meant to be pasted
     * @param mode the mode number specifies the type of copy invoked.
     *             0: only the verse name is specified. If it is a valid verse name
     *             it will be added to the end of the current track.
     *             1: Copies the bar from a starting index to an ending index and adds
     *             these bars to the end of the current track.
     *             2: Pastes a verse at the specified starting index in the song
     *             3: Copies the bars between a starting index (inclusive) and ending index
     *             (exclusive) and inserted it into a specified index. If there are bars at this
     *             index, they will be pushed back by the number of bars copied to make space for
     *             the copied bars.
     *
     * @return the formatted String to be displayed
     */
    public String formatCopy(String verseName,
                             Integer copyStartNum,
                             Integer copyEndNum,
                             Integer pasteStartNum,
                             int mode) {
        String result;
        if (mode == 0) {
            result = "Got it. Successfully copied " + verseName + " to end of current track";
        } else if (mode == 1) {
            result = "Got it. Successfully copied bars from "
                    + copyStartNum
                    + " and "
                    + copyEndNum
                    + " to the end of the track";
        } else if (mode == 2) {
            result = "Got it. Successfully copied verse "
                     + verseName
                     + " to "
                     + pasteStartNum;
        } else if (mode == 3) {
            result = "Got it. Successfully copied bars from "
                     + copyStartNum
                     + " to "
                     + copyEndNum
                     + " and inserted them to "
                     + pasteStartNum;
        } else {
            result = "Nothing is done";
        }
        return result;
    }
}
