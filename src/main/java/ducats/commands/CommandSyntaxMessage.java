package ducats.commands;

import ducats.DucatsException;

import java.util.HashMap;
import java.util.Map;

//@@author SalonetheGreat
/**
 * CommandSyntaxMessage is a class that cannot be instantiated.
 * To add a new command format, just create a <code>String</code> which indicates the command format,
 * and add the command name and format into nameToSyntax HashMap.
 */
public abstract class CommandSyntaxMessage {
    private static final String listSyntax = "list\n";
    private static final String newSyntax =
            "new s/SONG_NAME [key:s/KEY](C) [time:n/TIME_SIG](4/4) [tempo:n/TEMPO](120)\n";
    private static final String deleteSyntax = "delete song:n/SONG_NUM OR delete song:s/SONG_NAME\n";
    private static final String openSyntax = "open s/SONG_NAME\n";
    private static final String viewSyntax = "view [n/BAR_NO](last bar - 1)\n";
    private static final String addSyntax = "add s/NOTE\n";
    private static final String addbarSyntax = "addbar s/NOTES [bar:n/BAR_NO_TO_ADD_AFTER](last bar)\n";
    private static final String deletebarSyntax = "deletebar bar:n/BAR_NUM\n";
    private static final String insertbarSyntax = "insertbar bar:n/BAR_NUM s/NOTES\n";
    private static final String editbarSyntax = "editbar bar:n/BAR_NUM s/NOTES\n";
    private static final String swapbarSyntax = "swapbar bar:n/BAR_NUM bar:n/BAR_NUM\n";
    // TODO: group copy
    private static final String groupSyntax = "group n/START_NUM n/END_NUM s/GROUP_NAME\n";
    private static final String copySyntax = "copy s/GROUP_NAME\n"
            + "Format: copy n/START_NUM n/END_NUM\n"
            + "Format: copy s/GROUP_NAME n/INSERT_INDEX\n"
            + "Format: copy n/START_NUM n/END_NUM n/INSERT_INDEX\n";
    // TODO: overlay overlay_group_group overlay_bar_group overlay_bar_song
    private static final String overlaySyntax = "overlay n/BAR_NUM1 n/BAR_NUM2\n"
            + "Format: overlay n/BAR_NUM1 n/BAR_NUM2 repeat\n";
    private static final String overlay_group_groupSyntax = "overlay_group_group n/SONG1 n/GROUP1 n/SONG2 n/GROUP2\n"
            + "Format: overlay_group_group n/SONG1 n/GROUP1 n/SONG2 n/GROUP2 repeat\n";
    private static final String overlay_bar_groupSyntax = "overlay_bar_group n/BAR n/GROUP\n"
            + "Format: overlay_bar_group n/BAR n/GROUP repeat\n";
    private static final String overlay_bar_songSyntax = "overlay_bar_song n/SONG1 n/BAR1 n/SONG2 n/BAR2\n"
            + "Format: overlay_bar_song n/SONG1 n/BAR1 n/SONG2 n/BAR2 repeat\n";
    private static final String list_groupSyntax = "list_group\n"
            + "Format: list_group s/STARTING_SUBSTRING\n";
    private static final String asciiSyntax = "ascii song s/SONG_NAME\n";
    private static final String undoSyntax = "undo\n";
    private static final String redoSyntax = "redo\n";
    private static final String metronomeSyntax = "metronome n/DURATION_IN_NO_OF_BARS n/TEMP0 s/TIME_SIG\n";
    private static final String exitSyntax = "bye\n";
    private static final String playSyntax = "play [n/STARTING_BAR_NO n/ENDING_BAR_NO]\n"
            + "Format: play s/SONG_NAME  (when no song has been opened)\n";
    // TODO: close clear delete exit
    private static final String closeSyntax = "To be implemented in version 2.0\n";
    private static final String clearSyntax = "To be implemented in version 2.0\n";
    private static final String startHelpMessage = "Here are the commands in Ducats.\n";
    private static final String endInstructionMessage =
            "Alternatively, you can use help [command] to see format for a specific command.\n";

    //@@author SalonetheGreat
    private static Map<String, String> nameToSyntax = new HashMap<String, String>() {
        {
            put("list", listSyntax);
            put("new", newSyntax);
            put("delete", deleteSyntax);
            put("open", openSyntax);
            put("view", viewSyntax);
            put("add", addSyntax);
            put("addbar", addbarSyntax);
            put("deletebar", deletebarSyntax);
            put("insertbar", insertbarSyntax);
            put("editbar", editbarSyntax);
            put("swapbar", swapbarSyntax);
            put("group", groupSyntax);
            put("copy", copySyntax);
            put("overlay", overlaySyntax);
            put("overlay_group_group", overlay_group_groupSyntax);
            put("overlay_bar_group", overlay_bar_groupSyntax);
            put("overlay_bar_song", overlay_bar_songSyntax);
            put("list_group", list_groupSyntax);
            put("ascii", asciiSyntax);
            put("undo", undoSyntax);
            put("redo", redoSyntax);
            put("metronome", metronomeSyntax);
            put("play", playSyntax);
            put("close", closeSyntax);
            put("clear", clearSyntax);
            put("exit", exitSyntax);
        }
    };

    //@@author SalonetheGreat
    /**
     * The function is to get ALL the commands including their name and format in a single String.
     * @return a string with all the formats
     */
    public static String getMessage() {
        StringBuilder output = new StringBuilder();
        output.append(startHelpMessage);
        int i = 0;
        for (Map.Entry<String, String> entry : nameToSyntax.entrySet()) {
            output.append((++i) + "." + entry.getKey() + "\nFormat: " + entry.getValue() + "\n");
        }
        output.append(endInstructionMessage);
        return output.toString();
    }

    //@@author SalonetheGreat
    /**
     * The getMessage is to get the command and format of a specific command.
     * @param helpMessage the command to show
     * @return a string with name and format of the the input command
     * @throws DucatsException when you cannot find the command, throw other DukeException
     */
    public static String getMessage(String helpMessage) throws DucatsException {
        if (nameToSyntax.containsKey(helpMessage)) {
            return helpMessage + "\nFormat: " + nameToSyntax.get(helpMessage);
        } else {
            throw new DucatsException("", "Other");
        }
    }
}
