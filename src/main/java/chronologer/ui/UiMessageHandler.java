package chronologer.ui;

/**
 * Handles the pre-processing of strings before passing it to the GUI.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class UiMessageHandler {
    private static final String CHRONOLOGER_WELCOME_MESSAGE = "Hello! I'm Chronologer, your task manager!";
    private static final String CHRONOLOGER_GOODBYE_MESSAGE = "Goodbye! Have a pleasant day!";
    enum Manual {
        GREETING("Hi, this is the Chronologer manual!\n"),
        DATE_FORMAT("For all commands with date-time, the format is - dd/MM/yyyy HHmm\n\n"),
        TODO("1. Todo - todo<space>your task description eg. todo borrow books\n"),
        DEADLINE("2. Deadline - deadline<space>your deadline description<space>/by<space> date-time\n"),
        EVENT("3. Event - event<space>your event description<space>/at<space> date-time-date-time\n"),
        ASSIGNMENT("4. Assignments - assignment<space>/m<space>module code<space>/by<space>date-time\n"),
        LECTURE("5. Lectures - lecture<space>/m<space>module code<space>/at<space>day of week<space>"
                + "start time-end time\n"),
        TUTORIAL("6. Tutorials - tutorial<space>/m<space>module code<space>/at<space>day of week<space>"
                + "start time-end time\n"),
        EXAM("7. Exams - exam<space>/m<space>module code<space>/at<space>date-time-date-time\n"),
        LIST("8. To list out all your tasks simply enter list\n"),
        DONE("9. Done - done<space> index of the task as listed\n"),
        FIND("10. Find - find<space>any word in the task\n"),
        DELETE("11. Delete - delete<space> index of task as listed\n"),
        SEARCH("12. Search - search<space>duration of task in hours\n"),
        SCHEDULE("13. Schedule - schedule<space>index of task to be scheduled<space>/by<space>"
                + "index of event to be done by OR a raw date-time input\n"
                + "eg. schedule 5 /by 4 OR schedule 5 /by 05/01/2015 0900\n"),
        COMMENT("14. Comment - comment<space>index of task as listed<space>your comment\n"),
        LOCATION("15. Location - location<space>index of task as listed<space>your location\n"),
        UNDO_REDO("16. Undo/Redo - undo or redo changes to your tasks\n"),
        THEME("17. Theme - theme<space>dark or light\n"),
        STORE("18. Store/Restore - store or restore<space> index of storage\n"),
        EXIT("19. To exit, enter bye\n\n"),
        USER_GUIDE("20. If still unclear, enter manual to see our user guide!");

        private String instruction;

        Manual(String instruction) {
            this.instruction = instruction;
        }

        String getInstruction() {
            return instruction;
        }
    }

    public static String outputForGUI = null;

    static String printGreeting() {
        return CHRONOLOGER_WELCOME_MESSAGE;
    }

    /**
     * Provides the correct message type to be printed.
     */
    public static void outputMessage(String outputForUser) {
        outputForGUI = outputForUser;
    }

    /**
     * Prints the user manual.
     */
    public static String printManual() {
        StringBuilder manualOutput = new StringBuilder();

        for (Manual manual : Manual.values()) {
            manualOutput.append(manual.getInstruction());
        }
        return manualOutput.toString();
    }
}
