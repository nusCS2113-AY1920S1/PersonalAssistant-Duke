package helpertests;

import org.junit.jupiter.api.Test;
import util.uiformatter.CommandHelper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandHelperTest {
    private CommandHelper commandHelper = new CommandHelper();
    private ArrayList<String> simulatedOutput;
    private String[] expectedOutput;

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void gettingConsoleCommands_successfulExecution() {
        simulatedOutput = commandHelper.getCommandsForConsole();
        expectedOutput = new String[] {
            "List of commands available:",
            " - list",
            "Displays all existing projects.",
            "",
            " - create PROJECT_NAME",
            "Creates a project with the specified name.",
            "",
            " - delete PROJECT_INDEX",
            "Deletes specified project.",
            "",
            " - manage PROJECT_INDEX",
            "Selects the specified project to manage.",
            "",
            " - bye",
            "Saves your data and exits ArchDuke.",
            "",
            " - help",
            "Provides a list of all the commands available.",
        };
        assertArrayEquals(expectedOutput, simulatedOutput.toArray());
    }

    @Test
    void gettingProjectCommands_successfulExecution() {
        simulatedOutput = commandHelper.getCommandsForProject();
        expectedOutput = new String[] {"List of commands available:",
            " - view members",
            "Displays existing members in the project.",
            "",
            " - add member -n NAME [-i PHONE_NUMBER] [-e EMAIL_ADDRESS] [-r ROLE]",
            "Adds a new member to the project.",
            "",
            " - edit member INDEX [-n NAME] [-i PHONE_NUMBER] [-e EMAIL_ADDRESS]",
            "Edits the stated details in specified member.",
            "",
            " - role INDEX -n ROLE_NAME",
            "Give roles to specific members in a project.",
            "",
            " - delete member INDEX",
            "Deletes specified member from project.",
            "",
            " - view tasks [-MODIFIER]",
            "Displays existing tasks in sorted order specified by modifier. Default sorting is by priority.",
            "",
            " - add task -t TASK_NAME -p TASK_PRIORITY -c TASK_CREDIT [-d TASK_DUEDATE-(dd/mm/yyyy)] "
                    + "[-s STATE] [-r TASK_REQUIREMENT1] [-r TASK_REQUIREMENT2]",
            "Adds a new task to the project.",
            "",
            " - edit task TASK_INDEX [-t TASK_NAME] [-p TASK_PRIORITY] [-d TASK_DUEDATE] [-c TASK_CREDIT] "
                    + "[-s STATE]",
            "Edits the stated details in specified task.",
            "",
            " - delete task TASK_INDEX",
            "Deletes specified task from project.",
            "",
            " - view task requirements TASK_INDEX",
            "Displays specific task requirements of specified task.",
            "",
            " - edit task requirements TASK_INDEX [-r TASK_REQUIREMENT] "
                + "[-rm TASK_REQUIREMENT_INDEXES_TO_BE_REMOVED]",
            "Removes stated task requirements and add new ones.",
            "",
            " - view assignments -[MODIFIER]",
            "Displays tasks assigned to each member (-m) or members assigned to each task (-t).",
            "",
            " - assign task -i TASK_INDEX -to [MEMBER1_INDEX] [MEMBER2_INDEX] -rm [MEMBER3_INDEX]",
            "Assigns or unassigns specified tasks to specified members.",
            "",
            " - view reminder [-l LIST_NAME]",
            "Displays list of reminders.",
            "",
            " - add reminder -n reminder_NAME [-d TASK_DUEDATE-(dd/mm/yyyy) -l REMINDER_LIST_NAME]",
            "Adds a new reminder to the project.",
            "",
            " - edit reminder INDEX_NUMBER -n REMINDER_NAME [-d REMINDER_DUEDATE-(dd/mm/yyyy) "
                + "-l REMINDER_LIST_NAME]",
            "Edits existing reminders with stated details.",
            "",
            " - delete reminder INDEX_NUMBER",
            "Deletes specified reminders.",
            "",
            " - view credits",
            "Displays credits assigned to each member and the percentage of it that is done.",
            "",
            " - view",
            "Displays overview of current project.",
            "",
            " - exit",
            "Exits current project to manage other projects.",
            "",
            " - bye",
            "Exits program."};
        assertArrayEquals(expectedOutput, simulatedOutput.toArray());
    }
}
