package ducats;

import ducats.commands.CommandSyntaxMessage;
import ducats.components.Bar;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.SongList;

import java.util.ArrayList;
import java.util.Random;
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

    //@@author rohan-av
    /**
     * Returns a welcome message for duke.Duke.
     *
     * @return a welcome message for duke.Duke
     */
    String showWelcomeMessage() {
        String[] introMessages = {
            "What magic will you be creating today?",
            "Ready to make your magnum opus?",
            "Looking good, maestro!",
            "Let's go make music!",
            "What masterpiece are we working on today?",
            "Feeling creative, I see."
        };
        String logo = "______            _______  _______ _________ _______     \n"
                + "(  __  \\ |\\     /|(  ____ \\(  ___  )\\__   __/(  ____ \\  \n"
                + "| (  \\  )| )   ( || (    \\/| (   ) |   ) (   | (    \\/    \n"
                + "| |   ) || |   | || |      | (___) |   | |   | (_____        \n"
                + "| |   | || |   | || |      |  ___  |   | |   (_____  )       \n"
                + "| |   ) || |   | || |      | (   ) |   | |         ) |       \n"
                + "| (__/  )| (___) || (____/\\| )   ( |   | |   /\\____) |     \n"
                + "(______/ (_______)(_______/|/     \\|   )_(   \\_______) . \n\n";
        return wrap("Hello, this is\n" + logo + introMessages[(new Random()).nextInt(introMessages.length)]);
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
    String showError(DucatsException e) {
        return e.getMessage();
    }

    /**
     * Returns an appropriate message regarding whether the data file has been successfully indentified or, if absent,
     * created.
     *
     * @param fileCreated a boolean corresponding to whether a new file was created
     * @return the message to be displayed
     */
    static String showSaveStatus(boolean fileCreated) {
        if (fileCreated) {
            return "Data file for created songs not found. New file created.";
        } else {
            return "Data loaded successfully!";
        }
    }

    /**
     * Returns the String but wrapped in between two horizontal lines for enhanced
     * reading and display on the command line interface.
     *
     * @param content the String to be wrapped with horizontal lines
     * @return the wrapped String to be displayed
     */
    static String wrap(String content) {
        return ("\n_____________________________________________\n"
                + content
                + "\n_____________________________________________\n");
    }

    /**
     * Returns a String formatted for display that shows all the elements in the task list
     * due to the list command.
     *
     * @param list the list of Songs
     * @return the formatted String to be displayed
     */
    public String formatList(ArrayList<Song> list) {
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
     * Returns a string providing the user with feedback regarding which Song has been opened for editing.
     *
     * @param songList the list of Songs stored in Ducats
     * @return the formatted String to be displayed
     */
    public static String formatOpen(SongList songList, int songIndex) {
        if (songIndex != -1) {
            StringBuilder result = new StringBuilder();
            result.append("The following song has been opened: ");
            String songName = songList.getSongIndex(songList.getActiveIndex()).getName();
            result.append(songName);
            return wrap(result.toString());
        } else {
            return "Song not found! Please ensure that you've spelt it correctly.";
        }
    }

    //@@author
    /**
     * Returns a String formatted for display that indicates that a song has been deleted by
     * the delete command.
     *
     * @param songList the song list after deletion
     * @param deletedSong the song that was deleted
     * @return the formatted String to be displayed
     */
    public String formatDelete(SongList songList, Song deletedSong) {
        String word = (songList.getSize() == 1) ? "song" : "songs";
        String result = "Noted! I've removed this song:\n "
                + deletedSong.getName()
                + "\n"
                + "Now you have "
                + (songList.getSize())
                + " "
                + word
                + " in the SongList.";
        return wrap(result);
    }

    /**
     * Returns a String formatted for display that indicates that a bar has been deleted by
     * the deletebar command.
     *
     * @param song the song after deletion
     * @param barIndex the index of the bar that was deleted
     * @return the formatted String to be displayed
     */

    public String formatDeleteBar(Song song, int barIndex) {
        String word = (song.getBars().size() == 1) ? "bar" : "bars";
        String result = "Noted! I've removed bar: "
                + (barIndex)
                + "\n"
                + "Now you have "
                + (song.getBars().size())
                + " "
                + word
                + " in the song.";
        return wrap(result);
    }

    //@@author Samuel787
    /**
     * Returns a String formatted for display that shows all the groups available
     * in the current song.
     *
     * @param groups list of groups pertaining to that search from ListGroupCommand
     * @return the formatted String to be displayed
     */
    public String formatListGroups(ArrayList<Group> groups) {
        String result = "Here are the groups available: \n";
        int groupCount = 1;
        if (groups.size() == 0) {
            //no groups found
            result = "No groups are found for that search";
        } else {
            for (Group group : groups) {
                result += groupCount + ". " + group.getName() + "\n";
                groupCount++;
            }
        }
        return result;
    }

    //@@author
    /**
     * Returns a String formatted for display that the wrong command was type and telling them
     * the command was autocorrected.
     *
     * @param commandName this is the command name
     */

    public String autoCorrectMessage(String commandName) {

        String result = "The autocorrect believes that you have searched for "
                + commandName
                + " if not please search help for the right command";
        return wrap(result);
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
     * @param helpMessage the helpMessage user inputs
     * @return the formatted String to be displayed
     */
    public String formatHelp(String helpMessage) throws DucatsException {
        return wrap(CommandSyntaxMessage.getMessage(helpMessage));
    }

    /**
     * Returns a String that contains all the commands with their command name and format.
     * @return the formatted command syntax
     */
    public String formatHelp() {
        String output = CommandSyntaxMessage.getMessage();
        return wrap(CommandSyntaxMessage.getMessage());
    }

    /**
     * Returns a String formatted for display that shows a song in the song list due to the view command.
     * @param song the item that is to be displayed
     * @return the formatted String to be displayed
     */
    public String formatView(Song song) {
        return song.showSongChart();
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
     * Returns a String formatted for display that indicates that a duke.components.Bar object has been edited
     * by the edit command.
     *
     * @param oldBar the previous bar that was changed
     * @param newBar the new bar
     * @param song the item that was modified
     * @return the formatted String to be displayed
     */
    public String formatEdit(Bar oldBar, Bar newBar, Song song) {
        String result = "Got it. I've edited this bar:\n  "
                + oldBar.toString()
                + "\nNow you have "
                + newBar.toString()
                + " "
                + "in the song "
                + song.getName()
                + ".";
        return wrap(result);
    }

    /**
     * Returns a String formatted for display that indicates that a duke.components.Bar object has been inserted
     * by the insert command.
     *
     * @param list the song list
     * @param song the item that was modified
     * @return the formatted String to be displayed
     */
    public String formatInsertBar(ArrayList<Song> list, Bar bar, Song song) {
        String word = (list.size() == 1) ? "bar" : "bars";
        String result = "Got it. I've inserted this bar:\n  "
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
     * Returns a String formatted for display that indicates that two duke.components.Bar objects has been swapped
     * by the swap command.
     *
     * @param barOne the first bar to be swap
     * @param barTwo the second bar to be swapped with
     * @param song the item that was modified
     * @return the formatted String to be displayed
     */
    public String formatSwap(Bar barOne, Bar barTwo, Song song) {
        String result = "Got it. I've swap this bar:\n  "
                + barOne.toString()
                + "\n With this bar: "
                + barTwo.toString()
                + " "
                + "in the song "
                + song.getName()
                + ".";
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

    //@@author SalonetheGreat
    /**
     * Returns a message indicating that user cannot undo because it is the first version.
     * @return the formatted string to be displayed.
     */
    public String formatUndo() {
        String output = "This is the first version.\nYou cannot undo anymore.";
        return wrap(output);
    }

    //@@author SalonetheGreat
    /**
     * Returns a string indicating that the undo command has been successfully executed.
     * @param currentVersionIndex current version number, which is used to identify how many times can the user undo
     * @return a formatted string consisting of
     *         1. undo successfully
     *         2. number of undo times left
     */
    public String formatUndo(int currentVersionIndex) {
        String output;
        if (currentVersionIndex == 0) {
            output = "Undo successfully! You cannot undo anymore.";
        } else if (currentVersionIndex == 1) {
            output = "Undo successfully! You can undo for 1 more time.";
        } else {
            output = String.format("Undo successfully! You can undo for %d more times", currentVersionIndex).toString();
        }
        return wrap(output);
    }

    //@@author SalonetheGreat
    /**
     * Returns a message indicating that user cannot redo because it is the latest version.
     * @return the formatted string to be displayed.
     */
    public String formatRedo() {
        String output = "This is the latest version.\nYou cannot redo anymore.\n\n";
        return wrap(output);
    }

    //@@author SalonetheGreat
    /**
     * Returns a string indicating that the redo command has been successfully executed.
     * @param numOfRedoLeft number of redo times left, which is used to identify how many times can the user redo
     * @return a formatted string consisting of
     *         1. redo successfully
     *         2. number of redo times left
     */
    public String formatRedo(int numOfRedoLeft) {
        String output;
        if (numOfRedoLeft == 0) {
            output = "Redo successfully! You cannot redo anymore.";
        } else if (numOfRedoLeft == 1) {
            output = "Redo successfully! You can redo for 1 more time.";
        } else {
            output = String.format("Redo successfully! You can redo for %d more times.", numOfRedoLeft).toString();
        }
        return wrap(output);
    }

    //@@author rohan-av
    /**
     * Returns an output String that provides the user with feedback regarding the creation of a new Metronome.
     *
     * @param duration the duration of the metronome (in number of bars)
     * @param tempo the tempo of the metronome
     * @param timeSig the time signature of the metronome
     * @return the formatted String to be displayed
     */
    public String formatMetronome(int duration, int tempo, int[] timeSig) {
        StringBuilder result = new StringBuilder();
        result.append("Metronome initialized with a duration of ")
                .append(duration)
                .append(" bars, a tempo of ")
                .append(tempo)
                .append(" and a ")
                .append(timeSig[0])
                .append("/")
                .append(timeSig[1])
                .append(" time signature.");
        return wrap(result.toString());
    }
}
