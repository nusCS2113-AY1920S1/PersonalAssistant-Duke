//@@author Xdecosee

package wallet.ui;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The HelpPrompt Class prompts user for the help section they want.
 */
public class HelpPrompt {

    public static final String MESSAGE_NOTE = "Note the following when reading help sections:";
    public static final String MESSAGE_INDICATOR_REQUIRED = "<> indicates required parameters for command";
    public static final String MESSAGE_INDICATOR_OPTIONAL = "[]  indicates optional parameters for command";
    public static final String MESSAGE_AVAILABLE_SECTIONS = "Need help? The following help sections are available:";
    public static final String MESSAGE_ACCESS_SECTION = "Key in help section number (enter 0 to exit):";
    public static final String MESSAGE_ERROR_ACCESS_SECTION = "Error! Input not a valid help section index";
    public static final String[] HELP_SECTIONS = {"General", "Expense", "Loans", "Contacts", "Command History"};

    /**
     * Prompt user for help section index they want.
     *
     * @param pathList list of available help sections
     * @return An integer indicating section index.
     */
    public int prompt(ArrayList<String[]> pathList) {

        int selection;
        int index = 1;

        System.out.println(MESSAGE_AVAILABLE_SECTIONS);

        for (String[] s : pathList) {
            System.out.println(index + "." + s[0]);
            index++;
        }

        System.out.println();
        System.out.println(MESSAGE_NOTE);
        System.out.println(MESSAGE_INDICATOR_REQUIRED);
        System.out.println(MESSAGE_INDICATOR_OPTIONAL);
        System.out.println();
        System.out.println(MESSAGE_ACCESS_SECTION);

        try {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            selection = Integer.parseInt(input);

        } catch (NumberFormatException e) {

            System.out.println(MESSAGE_ERROR_ACCESS_SECTION);
            selection = -1;
            return selection;

        }

        if (selection > HELP_SECTIONS.length || selection < 0) {

            System.out.println(MESSAGE_ERROR_ACCESS_SECTION);
            selection = -1;

        }

        return selection;

    }
}
