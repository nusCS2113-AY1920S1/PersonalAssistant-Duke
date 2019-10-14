package wallet.ui;

import wallet.ui.Ui;

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
     * @return An integer indicating section index.
     */
    public int prompt() {

        int selection;
        int index = 1;
        Ui tempInterface = new Ui();

        System.out.println(MESSAGE_AVAILABLE_SECTIONS);

        for (String s : HELP_SECTIONS) {
            System.out.println(index + "." + s);
            index++;
        }

        System.out.println();
        System.out.println(MESSAGE_NOTE);
        System.out.println(MESSAGE_INDICATOR_REQUIRED);
        System.out.println(MESSAGE_INDICATOR_OPTIONAL);
        System.out.println();
        System.out.println(MESSAGE_ACCESS_SECTION);

        try {

            selection = Integer.parseInt(tempInterface.readLine());

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
