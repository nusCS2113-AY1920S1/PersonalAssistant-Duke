package util.constant;

public class ConstantHelper {
    public static final String[]  HELLO_MESSAGE = {
        "=======================================================================================",
        "  :::.    :::::::..     .,-:::::  .::   .:: ::::::-.   ...    ::: :::  .   .,:::::: ",
        "  ;;`;;   ;;;;``;;;;  ,;;;'````'  ,;;   ;;, ;;,   `';, ;;     ;;; ;;; .;;,.;;;;'''' ",
        " ,[[ '[[,  [[[,/[[['  [[[        ,[[[,,,[[[ `[[     [[[['     [[[ [[[[[/'   [[cccc   ",
        "c$$$cc$$$c $$$$$$c    $$$        \"$$$\"\"\"$$$ $$,    $$$$      $$$_$$$$,     $$\"\"\"\"  ",
        " 888   888,888b \"88bo,`88bo,__,o, 888   \"88 888_,o8P'88    .d888\"888\"88o,  888oo,__",
        " YMM   \"\"` MMMM   \"W\"   \"YUMMMMMP\"MMM    YM MMMMP\"`   \"YmmMMMM\"\" MMM \"MMP\" \"\"\"\"YUMMM",
        "=======================================================================================",
        "\t Welcome to ArchDuke Project Manager!",
        "\t Enter 'help' for the list of commands."
    };

    public static final String HORILINE = "\t+----------------------------------------------------------------------+";

    public static final String INDENTATION = "\t";

    public static final String BORDER_CORNER = "+";

    public static final char HORI_BORDER_UNIT = '-';

    public static final String VERTI_BORDER_UNIT = "|";

    public static final int DEFAULT_HORI_BORDER_LENGTH = 70;

    public static final int DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST = 70;

    public static final String SPACING = "       ";

    public static final int LONGEST_WORD_LIMIT_BEFORE_BREAKING_WITH_HYPHEN = 25;

    public static final String ASSIGNMENT_INDEX_NUMBER_MARKER = "i";

    public static final String ASSIGNEE_MARKER = "to";

    public static final String UNASSIGNEE_MARKER = "rm";

    public static final int MEMBER_FLAG_LENGTH = 4; //"-to " or "-rm "

    public static final int TASK_FLAG_LENGTH = 3; //"-i "

    public static final String ALL_MARKER = "all";

    public static final String BLANK = "";

    public static final String BLANK_MEMBER_DETAILS = "--";

    public static final String NAME_FLAG = "-n";

    public static final String COMMAND_ASSIGN_TASK = "assign task ";

    public static final String COMMAND_VIEW_ASSIGNMENTS = "view assignments ";

    public static final String[] VIEW_ASSIGNMENTS_INSUFFICIENT_PARAMS_MESSAGE = new String[] {
            "Please input the parameters to view assignments:",
            "**\t-m for viewing by member, -t for viewing by task.",
            "**\t\"all\" to view all assignments + or enter selected task/member index numbers.",
            "You may refer to the user guide or enter \"help\" for the list of possible commands."
    };

    public static final String[] VIEW_ASSIGNMENTS_INVALID_FLAG_MESSAGE = new String[]
        {"Could not understand your command! Please use:",
         "**\t-m for viewing by member, -t for viewing by task.",};

    public static final String[] VIEW_ASSIGNMENTS_NO_TASKS_MESSAGE = new String[]
        {"No tasks in project yet.",
         "Please add tasks and assign them to members before using this command!"};

    public static final String[] VIEW_ASSIGNMENTS_NO_MEMBERS_MESSAGE = new String[]
        {"No members in project yet.",
         "Please add members and assign them tasks before using this command!"};

    public static final String COMMAND_VIEW_ASSIGNMENTS_MEMBER_FLAG = "-m";

    public static final String COMMAND_VIEW_ASSIGNMENTS_TASK_FLAG = "-t";

    public static final int VALID_VIEW_ASSIGNMENT_LENGTH = 20;

    public static final String PROJECT_COMMAND_BYE = "bye";

    public static final String PROJECT_COMMAND_CREATE = "create";

    public static final String PROJECT_COMMAND_LIST = "list";

    public static final String PROJECT_COMMAND_MANAGE = "manage";

    public static final String PROJECT_COMMAND_DELETE = "delete";

    public static final String PROJECT_COMMAND_HELP = "help";

    public static final String COMMAND_ADD_MEMBER = "add member ";

    public static final String COMMAND_EDIT_MEMBER = "edit member ";

    public static final String COMMAND_DELETE_MEMBER = "delete member ";

    public static final String COMMAND_EDIT_TASK = "edit task ";

    public static final String COMMAND_DELETE_TASK = "delete task ";

    public static final String COMMAND_EDIT_TASK_REQ = "edit task requirements ";

    public static final String COMMAND_VIEW_TASK_REQ = "view task requirements ";

    public static final String COMMAND_VIEW_TASKS = "view tasks ";

}
