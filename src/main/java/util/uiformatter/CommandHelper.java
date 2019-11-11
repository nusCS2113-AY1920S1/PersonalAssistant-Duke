package util.uiformatter;

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
        helpList.add("Displays existing members in the project.");
        helpList.add("");
        helpList.add(" - add member -n NAME [-i PHONE_NUMBER] [-e EMAIL_ADDRESS] [-r ROLE]");
        helpList.add("Adds a new member to the project.");
        helpList.add("");
        helpList.add(" - edit member INDEX [-n NAME] [-i PHONE_NUMBER] [-e EMAIL_ADDRESS] [-r ROLE]");
        helpList.add("Edits the stated details in specified member.");
        helpList.add("");
        helpList.add(" - role INDEX -n ROLE_NAME");
        helpList.add("Give roles to specific members in a project.");
        helpList.add("");
        helpList.add(" - delete member INDEX");
        helpList.add("Deletes specified member from project.");
        helpList.add("");
        helpList.add(" - view tasks [-MODIFIER]");
        helpList.add("Displays existing tasks in sorted order specified by modifier. Default sorting is by priority.");
        helpList.add("");
        helpList.add(" - add task -t TASK_NAME -p TASK_PRIORITY -c TASK_CREDIT [-d TASK_DUEDATE-(dd/mm/yyyy)] "
                + "[-s STATE] [-r TASK_REQUIREMENT1] [-r TASK_REQUIREMENT2]");
        helpList.add("Adds a new task to the project.");
        helpList.add("");
        helpList.add(" - edit task TASK_INDEX [-t TASK_NAME] [-p TASK_PRIORITY] [-d TASK_DUEDATE] [-c TASK_CREDIT] "
                + "[-s STATE]");
        helpList.add("Edits the stated details in specified task.");
        helpList.add("");
        helpList.add(" - delete task TASK_INDEX");
        helpList.add("Deletes specified task from project.");
        helpList.add("");
        helpList.add(" - view task requirements TASK_INDEX");
        helpList.add("Displays specific task requirements of specified task.");
        helpList.add("");
        helpList.add(" - edit task requirements TASK_INDEX [-r TASK_REQUIREMENT] "
                + "[-rm TASK_REQUIREMENT_INDEXES_TO_BE_REMOVED]");
        helpList.add("Removes stated task requirements and add new ones.");
        helpList.add("");
        helpList.add(" - view assignments -[MODIFIER]");
        helpList.add("Displays tasks assigned to each member (-m) or members assigned to each task (-t).");
        helpList.add("");
        helpList.add(" - assign task -i TASK_INDEX -to [MEMBER1_INDEX] [MEMBER2_INDEX] -rm [MEMBER3_INDEX]");
        helpList.add("Assigns or unassigns specified tasks to specified members.");
        helpList.add("");
        helpList.add(" - view reminder [-l LIST_NAME]");
        helpList.add("Displays list of reminders.");
        helpList.add("");
        helpList.add(" - add reminder -n reminder_NAME [-d TASK_DUEDATE-(dd/mm/yyyy) -l REMINDER_LIST_NAME]");
        helpList.add("Adds a new reminder to the project.");
        helpList.add("");
        helpList.add(" - edit reminder INDEX_NUMBER -n REMINDER_NAME [-d REMINDER_DUEDATE-(dd/mm/yyyy) "
                + "-l REMINDER_LIST_NAME]");
        helpList.add("Edits existing reminders with stated details.");
        helpList.add("");
        helpList.add(" - delete reminder INDEX_NUMBER");
        helpList.add("Deletes specified reminders.");
        helpList.add("");
        helpList.add(" - view credits");
        helpList.add("Displays credits assigned to each member and the percentage of it that is done.");
        helpList.add("");
        helpList.add(" - view");
        helpList.add("Displays overview of current project.");
        helpList.add("");
        helpList.add(" - exit");
        helpList.add("Exits current project to manage other projects.");
        helpList.add("");
        helpList.add(" - bye");
        helpList.add("Saves your data and exits ArchDuke.");
        helpList.add("");
        helpList.add(" - help");
        helpList.add("Provides a list of all the commands available.");

        return helpList;
    }
}
