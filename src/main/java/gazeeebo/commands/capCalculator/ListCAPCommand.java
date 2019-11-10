//@@author JasonLeeWeiHern
package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;
import gazeeebo.parsers.CAPCommandParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * List out the modules in the semester
 * or list out all modules and the CAP score will be shown.
 */
public class ListCAPCommand {
    /**
     * For formatting the spacing between module name and vertical line.
     */
    private static final int BLANK_SPACING = 12;
    /**
     * Upper bound of the number of semesters.
     */
    private static final int UPPER_BOUNDARY = 8;
    /**
     * Lower bound of the number of semesters.
     */
    private static final int LOWER_BOUNDARY = 1;

    /**
     * list out the modules and
     * show the CAP of all the modules or a particular sem.
     *
     * @param ui        prints things to the user.
     * @param caplist   deals stores
     *                  semNumber, moduleCode, moduleCredits and CAP score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @throws IOException catch the error if the read file fails.
     */
    public ListCAPCommand(final Ui ui,
                          final Map<String,
                                  ArrayList<CAPCommandParser>> caplist,
                          final String lineBreak) throws IOException {
        try {
            CalculateCAPCommand calculatedCAP = new CalculateCAPCommand();
            String listWhat = "";
            double cap;
            switch (ui.fullCommand.split(" ").length) {
                case 1:
                    System.out.print("Which sem do you want to list? "
                            + "all,1,2,3,4,5,6,7,8\n");
                    ui.readCommand();
                    listWhat = ui.fullCommand;
                    break;
                case 2:
                    listWhat = ui.fullCommand.split(" ")[1];
                    break;
                default:
                    throw new ArrayIndexOutOfBoundsException();
            }
            if ("all".equals(listWhat)) {
                cap = calculatedCAP.calculateCAP(caplist);
                listAll(caplist, lineBreak, cap);
            } else if (Integer.parseInt(listWhat) <= UPPER_BOUNDARY
                    && Integer.parseInt(listWhat) >= LOWER_BOUNDARY) {
                cap = calculatedCAP.calculateCAPPerSem(caplist, listWhat);
                listSem(caplist, lineBreak, cap, listWhat);
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.print("Please Input in the correct format\n");
        }
    }

    /**
     * This method list out all the modules and show the accumulative cap.
     *
     * @param caplist   deals stores
     *                  semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @param cap       CAP of the modules.
     */
    private void listAll(final Map<String, ArrayList<CAPCommandParser>> caplist,
                         final String lineBreak, final double cap) {
        System.out.print("Sem | Module code | MC | CAP\n" + lineBreak);
        for (String key : caplist.keySet()) {
            for (int i = 0; i < caplist.get(key).size(); i++) {
                int noBlankSpacing = BLANK_SPACING
                        - caplist.get(key).get(i).moduleCode.length();
                System.out.print(key + "   | "
                        + caplist.get(key).get(i).moduleCode);
                for (int j = 0; j < noBlankSpacing; j++) {
                    System.out.print(" ");
                }
                System.out.print("| " + caplist.get(key).get(i).moduleCredit
                        + "  | " + caplist.get(key).get(i).grade
                        + "\n" + lineBreak);
            }
        }
        System.out.print("Total CAP: " + cap + "\n");
    }

    /**
     * This method list out the modules
     * and show the GPA of the a particular sem.
     *
     * @param caplist   deals stores
     *                  semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @param cap       CAP of the modules.
     * @param semNumber semester that user input
     */
    private void listSem(final Map<String, ArrayList<CAPCommandParser>> caplist,
                         final String lineBreak,
                         final double cap, final String semNumber) {
        try {
            System.out.print("Sem | Module code | MC | CAP\n" + lineBreak);
            boolean isEmpty = true;
            for (String key : caplist.keySet()) {
                for (int i = 0; i < caplist.get(key).size(); i++) {
                    if (key.equals(semNumber)) {
                        int noBlankSpacing = BLANK_SPACING
                                - caplist.get(key).get(i).moduleCode.length();
                        System.out.print(key + "   | "
                                + caplist.get(key).get(i).moduleCode);
                        for (int j = 0; j < noBlankSpacing; j++) {
                            System.out.print(" ");
                        }
                        System.out.print("| " + caplist.get(key).
                                get(i).moduleCredit
                                + "  | " + caplist.get(key).get(i).grade
                                + "\n" + lineBreak);
                        isEmpty = false;
                    }
                }
            }
            if (!isEmpty) {
                System.out.print("Sem " + semNumber + " CAP: " + cap + "\n");
            } else {
                System.out.print("No modules in this semester!\n");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Invalid semester number.\n");
        }
    }
}
