package duke.commands;

import duke.DukeException;

import java.util.HashMap;
import java.util.Map;

public final class CommandSyntaxMessage {
    private static String listSyntax = "list\n";
    private static String newSyntax = "new s/SONG_NAME [key:s/KEY](C) [time:n/TIME_SIG](4/4) [tempo:n/TEMPO](120)\n";
    private static String openSyntax = "open s/SONG_NAME\n";
    private static String viewSyntax = "view [n/BAR_NO](last bar - 1)\n";
    private static String addSyntax = "add s/NOTE\n";
    private static String addbarSyntax = "addbar s/NOTES [bar:n/BAR_NO_TO_ADD_AFTER](last bar)\n";
    // TODO: add overlay syntax
    private static String overlaySyntax = "To be implemented in version 2.0\n";
    private static String copySyntax = "copy start_num end_num\nFormat: copy start_num end_num insert_num\n";
    private static String groupSyntax = "group start_num end_num verse_name\n";
    private static String list_groupSyntax = "list_group\nFormat: list_group -starting_substring\n";
    private static String playSyntax = "play [n/STARTING_BAR_NO n/ENDING_BAR_NO]\n" +
            "Format: play s/SONG_NAME  (when no song has been opened)\n";
    // TODO: add close, clear, delete, exit syntax
    private static String closeSyntax = "To be implemented in version 2.0\n";
    private static String clearSyntax = "To be implemented in version 2.0\n";
    private static String deleteSyntax = "To be implemented in version 2.0\n";
    private static String exitSyntax = "To be implemented in version 2.0\n";

    private static Map<String, String> nameToSyntax = new HashMap<String, String>() {{
        put("list", listSyntax);
        put("new", newSyntax);
        put("open", openSyntax);
        put("view", viewSyntax);
        put("add", addSyntax);
        put("addbar", addbarSyntax);
        put("overlay", overlaySyntax);
        put("copy", copySyntax);
        put("group", groupSyntax);
        put("list_group", list_groupSyntax);
        put("play", playSyntax);
        put("close", closeSyntax);
        put("clear", clearSyntax);
        put("delete", deleteSyntax);
        put("exit", exitSyntax);
    }};

    public static String getMessage () {
        StringBuilder output = new StringBuilder();
        for (Map.Entry<String, String> entry : nameToSyntax.entrySet()) {
            output.append(entry.getKey() + "\nFormat: " + entry.getValue());
        }
        return output.toString();
    }

    public static String getMessage (String helpMessage) throws DukeException {
        if (nameToSyntax.containsKey(helpMessage)) {
            StringBuilder output = new StringBuilder();
            output.append(helpMessage + "Format: " + nameToSyntax.get(helpMessage));
            return output.toString();
        } else
            throw new DukeException("", "Other");
    }
}
