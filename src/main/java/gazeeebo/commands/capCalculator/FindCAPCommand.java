package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;
import gazeeebo.parser.CAPCommandParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Find a module by semNumber or moduleCode.
 */
public class FindCAPCommand {
    /**
     * Printing blank spaces.
     */
    private static final int BLANK_SPACING = 12;

    /**
     * Finds modules base on moduleCode and list it out.
     *
     * @param ui        prints things to the user.
     * @param caplist   deals stores
     *                  semNumber, moduleCode, moduleCredits and CAP score.
     * @param lineBreak print out a separator to separate each line in the list.
     */
    public FindCAPCommand(final Ui ui,
                          final Map<String, ArrayList<CAPCommandParser>> caplist,
                          final String lineBreak) {
        try {
            String findInput = "";
            switch (ui.fullCommand.split(" ").length) {
                case (1):
                    System.out.print("Which modules do you want to find?\n");
                    ui.readCommand();
                    findInput = ui.fullCommand;
                    break;
                case (2):
                    findInput = ui.fullCommand.split(" ")[1];
                    break;
                default:
                    throw new ArrayIndexOutOfBoundsException();
            }
            String toPrint = "";
            boolean isExist = false;
            for (String key : caplist.keySet()) {
                for (int i = 0; i < caplist.get(key).size(); i++) {
                    if (caplist.get(key).get(i).
                            moduleCode.equals(findInput)) {
                        int noBlankSpacing = BLANK_SPACING
                                - caplist.get(key).get(i).moduleCode.length();
                        toPrint += key + "   | "
                                + caplist.get(key).get(i).moduleCode;
                        for (int j = 0; j < noBlankSpacing; j++) {
                            toPrint += " ";
                        }
                        toPrint += "| " + caplist.get(key).
                                get(i).moduleCredit + "  | "
                                + caplist.get(key).get(i).grade
                                + "\n" + lineBreak;
                        isExist = true;
                    }
                }
            }
            if (!isExist) {
                System.out.print(findInput + " is not found in the list.\n");
            } else {
                System.out.print("Sem | Module code | MC | CAP\n" + lineBreak);
                System.out.print(toPrint);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.print("Please Input in the correct format\n");
        }
    }
}
