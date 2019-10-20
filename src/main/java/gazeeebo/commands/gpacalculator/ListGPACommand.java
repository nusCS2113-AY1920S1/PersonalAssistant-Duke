package gazeeebo.commands.gpacalculator;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ListGPACommand {
    static final int BLANK_SPACING = 12;

    /**
     * list out the modules and show the GPA of the sem(s).
     *
     * @param ui         the object that prints things to the user.
     * @param gpalist    the object that deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak the object that print out a separator to separate each line in the list.
     * @param cap        the object is the GPA.
     * @throws IOException catch the error if the read file fails.
     */
    public ListGPACommand(final Ui ui, final Map<String, ArrayList<GPACommand>> gpalist, final String lineBreak, double cap) throws IOException {
        CalculateGPACommand calculatedGPA = new CalculateGPACommand();
        System.out.println("Which sem do you want to list? all,1,2,3,4,5,6,7,8");
        ui.readCommand();
        if (ui.fullCommand.equals("all")) {
            cap = calculatedGPA.GPACalculator(gpalist);
            listallcommand(gpalist, lineBreak, cap);
        } else {
            cap = calculatedGPA.gpacalculatorpersem(gpalist, ui.fullCommand);
            ListSemCommand(gpalist, ui, lineBreak, cap);
        }
    }

    /**
     * This method list out all the modules and show the accumulative cap.
     *
     * @param gpalist    the object that deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak the object that print out a separator to separate each line in the list.
     * @param cap        the object is the GPA.
     */
    public void listallcommand(final Map<String, ArrayList<GPACommand>> gpalist, final String lineBreak, final double cap) {
        System.out.print("Sem | Module code | MC | GPA\n" + lineBreak);
        for (String key : gpalist.keySet()) {
            for(int i = 0; i < gpalist.get(key).size(); i++) {
                int noBlankSpacing = BLANK_SPACING - gpalist.get(key).get(i).moduleCode.length();
                System.out.print(key + "   | " + gpalist.get(key).get(i).moduleCode);
                for (int j = 0; j < noBlankSpacing; j++) {
                    System.out.print(" ");
                }
                System.out.print("| " + gpalist.get(key).get(i).moduleCredit + "  | " + gpalist.get(key).get(i).score
                        + "\n" + lineBreak);
            }
        }
        System.out.println("Total GPA: " + cap);
    }

    /**
     * This method list out the modules and show the GPA of the a particular sem.
     *
     * @param ui         the object that prints things to the user
     * @param gpalist    the object that deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak the object that print out a separator to separate each line in the list.
     * @param cap        the object is the GPA.
     */
    public void ListSemCommand(final Map<String, ArrayList<GPACommand>> gpalist, final Ui ui, final String lineBreak, double cap) {
        System.out.print("Sem | Module code | MC | GPA\n" + lineBreak);
        for (String key : gpalist.keySet()) {
            for(int i = 0; i < gpalist.get(key).size(); i++) {
                if (key.equals(ui.fullCommand)) {
                    int noBlankSpacing = BLANK_SPACING - gpalist.get(key).get(i).moduleCode.length();
                    System.out.print(key + "   | " + gpalist.get(key).get(i).moduleCode);
                    for (int j = 0; j < noBlankSpacing; j++) {
                        System.out.print(" ");
                    }
                    System.out.print("| " + gpalist.get(key).get(i).moduleCredit + "  | " + gpalist.get(key).get(i).score
                            + "\n" + lineBreak);
                }
            }
        }
        System.out.println("Sem " + ui.fullCommand + " GPA: " + cap);
    }
}
