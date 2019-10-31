package util;

import java.util.ArrayList;

public class CommandHelper {

    public ArrayList<String> getCommandsForConsole() {
        ArrayList<String> helpList = new ArrayList<>();
        helpList.add("List of commands available:");
        helpList.add(" - list");
        helpList.add("Displays all existing projects.");
        helpList.add("");
        helpList.add(" - create PROJECT_NAME");
        helpList.add("Creates a project with the specified name.");
        helpList.add("");
        helpList.add(" - delete PROJECT_INDEX");
        helpList.add("Deletes specified project.");
        helpList.add("");
        helpList.add(" - manage PROJECT_INDEX");
        helpList.add("Selects the specified project to manage.");
        helpList.add("");
        helpList.add(" - bye");
        helpList.add("Saves your data and exits ArchDuke.");
        helpList.add("");
        helpList.add(" - help");
        helpList.add("Provides a list of all the commands available.");
        return helpList;
    }
}
