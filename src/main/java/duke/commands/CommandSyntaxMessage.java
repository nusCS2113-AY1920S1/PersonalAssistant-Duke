package duke.commands;

import duke.DukeException;

import java.util.HashMap;
import java.util.Map;

//@@ Sha Long

/**
 * CommandSyntaxMessage is a class that cannot be instantiated.
 * To add a new command format, just create a <code>String</code> which indicates the command format,
 * and add the command name and format into nameToSyntax HashMap.
 */
public abstract class CommandSyntaxMessage {
    private static String listSyntax = "list\n";
    private static String newSyntax = "new s/SONG_NAME [key:s/KEY](C) [time:n/TIME_SIG](4/4) [tempo:n/TEMPO](120)\n";
    private static String openSyntax = "open s/SONG_NAME\n";
    private static String viewSyntax = "view [n/BAR_NO](last bar - 1)\n";
    private static String addSyntax = "add s/NOTE\n";
    private static String addbarSyntax = "addbar s/NOTES [bar:n/BAR_NO_TO_ADD_AFTER](last bar)\n";
    private static String asciiSyntax = "ascii song song_name\n";
    // TODO: add overlay syntax
    private static String overlaySyntax = "To be implemented in version 2.0\n";
    private static String copySyntax = "copy start_num end_num\nFormat: copy start_num end_num insert_num\n";
    private static String groupSyntax = "group start_num end_num verse_name\n";
    private static String list_groupSyntax = "list_group\nFormat: list_group -starting_substring\n";
    private static String playSyntax = "play [n/STARTING_BAR_NO n/ENDING_BAR_NO]\n"
            + "Format: play s/SONG_NAME  (when no song has been opened)\n";
    // TODO: add close, clear, delete, exit syntax
    private static String closeSyntax = "To be implemented in version 2.0\n";
    private static String clearSyntax = "To be implemented in version 2.0\n";
    private static String deleteSyntax = "To be implemented in version 2.0\n";
    private static String exitSyntax = "To be implemented in version 2.0\n";
    private static String startHelpMessage = "Here are the commands in Ducats.\n";
    private static String endInstructionMessage =
            "Alternatively, you can use help [command] to see format for specific command.\n";

    //@@ Sha Long
    private static Map<String, String> nameToSyntax = new HashMap<String, String>() {
        {
            put("list", listSyntax);
            put("new", newSyntax);
            put("open", openSyntax);
            put("view", viewSyntax);
            put("add", addSyntax);
            put("addbar", addbarSyntax);
            put("overlay", overlaySyntax);
            put("copy", copySyntax);
            put("group", groupSyntax);
            put("ascii", asciiSyntax);
            put("list_group", list_groupSyntax);
            put("play", playSyntax);
            put("close", closeSyntax);
            put("clear", clearSyntax);
            put("delete", deleteSyntax);
            put("exit", exitSyntax);
        }
    };

    //@@ Sha Long
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

    //@@ Sha Long
    /**
     * The getMessage is to get the command and format of a specific command.
     * @param helpMessage the command to show
     * @return a string with name and format of the the input command
     * @throws DukeException when you cannot find the command, throw other DukeException
     */
    public static String getMessage(String helpMessage) throws DukeException {
        if (nameToSyntax.containsKey(helpMessage)) {
            StringBuilder output = new StringBuilder();
            output.append(helpMessage + "\nFormat: " + nameToSyntax.get(helpMessage));
            return output.toString();
        } else {
            throw new DukeException("", "Other");
        }
    }
}
