package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.commons.Compal;

import java.util.ArrayList;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_MISSING_NUM;
import static compal.commons.Messages.MESSAGE_MISSING_SEARCH;
import static compal.commons.Messages.MESSAGE_INVALID_HELP_INPUT;


/**
 * Executes user input "help".
 */
public class HelpCommand extends Command implements CommandParser {
    public static final String TOKEN_SEARCH = "/search";
    public static final String TOKEN_NUM = "/num";
    public static final String  CLEAR_COMMAND = "1. clear";
    public static final String  BYE_COMMAND = "2. bye";
    public static final String  HELP_COMMAND = "3. help [/num <command id>|/search <key word>]";
    public static final String  LIST_COMMAND = "4. list";
    public static final String  FIND_COMMAND = "5. find <key word>";
    public static final String  DELETE_COMMAND = "6. delete <task id>";
    public static final String  DONE_COMMAND = "7. done <task id>";
    public static final String  SET_REMINDER_COMMAND = "8. set-reminder <task id>";
    public static final String  VIEW_COMMAND = "9. view /date <dd/mm/yyyy>";
    public static final String  DEADLINE_COMMAND = "10. deadline <description> [/priority high|medium|low]"
            + "\n /date <dd/mm/yyyy> /end <hhhh>\n";
    public static final String  EVENT_COMMAND = "11. event <description> [/priority high|medium|low]"
            + "\n /date <dd/mm/yyyy> /start <hhhh> /end <hhhh>\n";
    public static final String  RECUR_TASK_COMMAND = "12. recurtask <description> [/priority high|medium|low]"
            + "\n /date <dd/mm/yyyy> /start <hhhh> /end <hhhh>"
        + "\n /edate <hhhh>|/rep <repeat time>\n";
    public static final String  ACAD_COMMAND = "13. lec|tut|sec|lab <description> [/priority high|medium|low]"
            + "\n /date <dd/mm/yyyy>... /start <hhhh> /end <hhhh> /edate <dd/mm/yyyy>\n";

    private ArrayList<String> helpList;
    private ArrayList<String> detailList;

    /**
     * Constructs ByeCommand object.
     *
     * @param d Compal.
     */
    public HelpCommand(Compal d) {
        super(d);
        helpList = new ArrayList<>();
        helpList.add(CLEAR_COMMAND);
        helpList.add(BYE_COMMAND);
        helpList.add(HELP_COMMAND);
        helpList.add(LIST_COMMAND);
        helpList.add(FIND_COMMAND);
        helpList.add(DELETE_COMMAND);
        helpList.add(DONE_COMMAND);
        helpList.add(SET_REMINDER_COMMAND);
        helpList.add(VIEW_COMMAND);
        helpList.add(DEADLINE_COMMAND);
        helpList.add(EVENT_COMMAND);
        helpList.add(RECUR_TASK_COMMAND);
        helpList.add(ACAD_COMMAND);
    }

    /**
     * show the help guideline to the user.
     *
     * @param userIn Entire user input string.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        if (scanner.hasNext()) {
            String token = scanner.next();
            if (token.equals(TOKEN_NUM)) {
                if (scanner.hasNextInt()) {
                    int num = scanner.nextInt();
                    compal.ui.printg("This is command " + num + " :\n");
                    compal.ui.printg(helpList.get(num - 1));
                } else {
                    compal.ui.printg(MESSAGE_MISSING_NUM);
                    throw new Compal.DukeException(MESSAGE_MISSING_NUM);
                }
            } else if (token.equals(TOKEN_SEARCH)) {
                if (scanner.hasNext()) {
                    String searchTerm = scanner.next();
                    compal.ui.printg("Your search result for the keyword " + searchTerm + ": \n");
                    for (String command : helpList) {
                        if (command.contains(searchTerm)) {
                            compal.ui.printg(command);
                        }
                    }
                } else {
                    compal.ui.printg(MESSAGE_MISSING_SEARCH);
                    throw new Compal.DukeException(MESSAGE_MISSING_SEARCH);
                }
            } else {
                compal.ui.printg(MESSAGE_INVALID_HELP_INPUT);
                throw new Compal.DukeException(MESSAGE_INVALID_HELP_INPUT);
            }
        } else {
            for (String command: helpList) {
                compal.ui.printg(command);
            }
        }
    }
}
