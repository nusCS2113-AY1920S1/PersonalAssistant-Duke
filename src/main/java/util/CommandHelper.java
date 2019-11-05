package util;

import java.util.ArrayList;

//@@author seanlimhx
public class CommandHelper {

    /**
     * Get list of commands to be printed to user at Console stage.
     * @return ArrayList of Strings containing information of commands at Console stage.
     */
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

    /**
     * Get list of commands to be printed to user at Project stage.
     * @return ArrayList of Strings containing information of commands at Project 4stage.
     */
    public ArrayList<String> getCommandsForProject() {
        ArrayList<String> helpList = new ArrayList<>();
        helpList.add("List of commands available:");
        helpList.add(" - view members");
        helpList.add("");
        helpList.add(" - add member -n NAME [-i PHONE_NUMBER] [-e EMAIL_ADDRESS] [-r ROLE]");
        helpList.add("");
        helpList.add(" - edit member INDEX [-n NAME] [-i PHONE_NUMBER] [-e EMAIL_ADDRESS]");
        helpList.add("");
        helpList.add(" - role INDEX -n ROLE_NAME");
        helpList.add("");
        helpList.add(" - delete member INDEX");
        helpList.add("");
        helpList.add(" - view tasks [/MODIFIER]");
        helpList.add("");
        helpList.add(" - add task -t TASK_NAME -p TASK_PRIORITY -c TASK_CREDIT [-d TASK_DUEDATE-(dd/mm/yyyy)] "
                + "[-s STATE] [-r TASK_REQUIREMENT1] [-r TASK_REQUIREMENT2]");
        helpList.add("");
        helpList.add(" - edit task TASK_INDEX [-t TASK_NAME] [-p TASK_PRIORITY] [-d TASK_DUEDATE] [-c TASK_CREDIT] "
                + "[-s STATE]");
        helpList.add("");
        helpList.add(" - delete task TASK_INDEX");
        helpList.add("");
        helpList.add(" - view task requirements TASK_INDEX");
        helpList.add("");
        helpList.add(" - edit task requirements TASK_INDEX [-r TASK_REQUIREMENT] "
                + "[-rm TASK_REQUIREMENT_INDEXES_TO_BE_REMOVED]");
        helpList.add("");
        helpList.add(" - view assignments /MODIFIER");
        helpList.add("");
        helpList.add(" - assign task -i TASK_INDEX -to [MEMBER1_INDEX] [MEMBER2_INDEX] -rm [MEMBER3_INDEX]");
        helpList.add("");
        helpList.add(" - view reminder [-l LIST_NAME]");
        helpList.add("");
        helpList.add(" - add reminder -n reminder_NAME [-d TASK_DUEDATE-(dd/mm/yyyy) -l REMINDER_LIST_NAME]");
        helpList.add("");
        helpList.add(" - edit reminder INDEX_NUMBER -n REMINDER_NAME [-d REMINDER_DUEDATE-(dd/mm/yyyy) "
                + "-l REMINDER_LIST_NAME]");
        helpList.add("");
        helpList.add(" - delete reminder INDEX_NUMBER");
        helpList.add("");
        helpList.add(" - view credits");
        helpList.add("");
        helpList.add(" - view");
        helpList.add("");
        helpList.add(" - exit");
        helpList.add("");
        helpList.add(" - bye");

        return helpList;
    }
}
