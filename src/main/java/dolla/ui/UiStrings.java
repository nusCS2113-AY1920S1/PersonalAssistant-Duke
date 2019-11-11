package dolla.ui;

public interface UiStrings {

    String TAB = "\t";

    String MSG_MODIFY = "\tPlease use the format 'modify [LIST NUM]' if you wish to modify it.";
    String EXISTING_RECORD_MSG = "\tOOPS! You already have the following ";
    String INVALID_AMOUNT_MSG = "\tOOPS! The amount you have entered is invalid.";
    String VALID_AMOUNT_MSG = "\tPlease key in a non-zero positive value with an appropriate "
                            + "decimal point that is less than 1,000,000.";
    String EXECUTE_SHORTCUT_MSG = "\tYou can execute 'shortcuts' to view your list of shortcuts!";
    String INVALID_DATE_MSG = "\tPlease use the format 'DD/MM/YYYY'!";
    String DATE_REQ_MSG = "\tPlease enter your new entry date in the format 'DD/MM/YYYY'";
    String INVALID_NUMBER_MSG = " is not a number. Please use a number instead!";
    String INVALID_COMMAND_MSG = "\tOOPS! The command is invalid. Please enter a valid command!";

    String CHANGE_MODE_MSG = "\tGot it! Your mode has been changed.";
    String NO_REMINDERS_MSG = "\tThere are no reminders :)";
    String EXIT_MSG = "\tBye. Hope to see you again soon!";

    String line = "\t____________________________________________________________";

    String dollaLogo =
             "\t /$$$$$$$            /$$ /$$  \n"
           + "\t| $$__  $$          | $$| $$   \n"
           + "\t| $$  \\ $$  /$$$$$$ | $$| $$  /$$$$$$ \n"
           + "\t| $$  | $$ /$$__  $$| $$| $$ |____  $$\n"
           + "\t| $$  | $$| $$  \\ $$| $$| $$  /$$$$$$$\n"
           + "\t| $$  | $$| $$  | $$| $$| $$ /$$__  $$\n"
           + "\t| $$$$$$$/|  $$$$$$/| $$| $$|  $$$$$$$\n"
           + "\t|_______/  \\______/ |__/|__/ \\_______/\n";

    String version = "\tVersion 1.4\n";

    String welcomeMsg =
             "\tHello from\n\n"
           + dollaLogo
           + version
           + line
           + "\n\tI help keep track of your finance!\n"
           + "\tWhat can I do for you today?";

    String dollaModeLogo =
             "\tYou are now in mode:\n\n"
           + "\t /$$$$$$$  /$$$$$$ /$$      /$$       /$$$$$$ \n"
           + "\t| $$__  $$/$$__  $| $$     | $$      /$$__  $$\n"
           + "\t| $$  \\ $| $$  \\ $| $$     | $$     | $$  \\ $$\n"
           + "\t| $$  | $| $$  | $| $$     | $$     | $$$$$$$$\n"
           + "\t| $$  | $| $$  | $| $$     | $$     | $$__  $$\n"
           + "\t| $$  | $| $$  | $| $$     | $$     | $$  | $$\n"
           + "\t| $$$$$$$|  $$$$$$| $$$$$$$| $$$$$$$| $$  | $$\n"
           + "\t|_______/ \\______/|________|________|__/  |__/";

    String entryModeLogo =
             "\tYou are now in mode:\n\n"
           + "\t /$$$$$$$$/$$   /$$/$$$$$$$$/$$$$$$$ /$$     /$$\n"
           + "\t| $$_____| $$$ | $|__  $$__| $$__  $|  $$   /$$/\n"
           + "\t| $$     | $$$$| $$  | $$  | $$  \\ $$\\  $$ /$$/ \n"
           + "\t| $$$$$  | $$ $$ $$  | $$  | $$$$$$$/ \\  $$$$/  \n"
           + "\t| $$__/  | $$  $$$$  | $$  | $$__  $$  \\  $$/   \n"
           + "\t| $$     | $$\\  $$$  | $$  | $$  \\ $$   | $$    \n"
           + "\t| $$$$$$$| $$ \\  $$  | $$  | $$  | $$   | $$    \n"
           + "\t|________|__/  \\__/  |__/  |__/  |__/   |__/      ";

    String limitModeLogo =
             "\tYou are now in mode:\n\n"
           + "\t /$$      /$$$$$$/$$      /$$/$$$$$$/$$$$$$$$\n"
           + "\t| $$     |_  $$_| $$$    /$$|_  $$_|__  $$__/\n"
           + "\t| $$       | $$ | $$$$  /$$$$ | $$    | $$   \n"
           + "\t| $$       | $$ | $$ $$/$$ $$ | $$    | $$   \n"
           + "\t| $$       | $$ | $$  $$$| $$ | $$    | $$   \n"
           + "\t| $$       | $$ | $$\\  $ | $$ | $$    | $$   \n"
           + "\t| $$$$$$$$/$$$$$| $$ \\/  | $$/$$$$$$  | $$   \n"
           + "\t|________|______|__/     |__|______/  |__/     ";

    String debtModeLogo =
             "\tYou are now in mode:\n\n"
           + "\t /$$$$$$$ /$$$$$$$$/$$$$$$$ /$$$$$$$$\n"
           + "\t| $$__  $| $$_____| $$__  $|__  $$__/\n"
           + "\t| $$  \\ $| $$     | $$  \\ $$  | $$   \n"
           + "\t| $$  | $| $$$$$  | $$$$$$$   | $$   \n"
           + "\t| $$  | $| $$__/  | $$__  $$  | $$   \n"
           + "\t| $$  | $| $$     | $$  \\ $$  | $$   \n"
           + "\t| $$$$$$$| $$$$$$$| $$$$$$$/  | $$   \n"
           + "\t|_______/|________|_______/   |__/     ";

    String shortcutModeLogo =
            "\tYou are now in mode:\n\n"
           + "\t  /$$$$$$ /$$   /$$ /$$$$$$ /$$$$$$$ /$$$$$$$$/$$$$$$ /$$   /$$/$$$$$$$$\n"
           + "\t /$$__  $| $$  | $$/$$__  $| $$__  $|__  $$__/$$__  $| $$  | $|__  $$__/\n"
           + "\t| $$  \\__| $$  | $| $$  \\ $| $$  \\ $$  | $$ | $$  \\__| $$  | $$  | $$   \n"
           + "\t|  $$$$$$| $$$$$$$| $$  | $| $$$$$$$/  | $$ | $$     | $$  | $$  | $$   \n"
           + "\t \\____  $| $$__  $| $$  | $| $$__  $$  | $$ | $$     | $$  | $$  | $$   \n"
           + "\t /$$  \\ $| $$  | $| $$  | $| $$  \\ $$  | $$ | $$    $| $$  | $$  | $$   \n"
           + "\t|  $$$$$$| $$  | $|  $$$$$$| $$  | $$  | $$ |  $$$$$$|  $$$$$$/  | $$   \n"
           + "\t \\______/|__/  |__/\\______/|__/  |__/  |__/  \\______/ \\______/   |__/     ";
}
