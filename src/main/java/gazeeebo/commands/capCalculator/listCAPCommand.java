package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class listCAPCommand {
    private static final int BLANK_SPACING = 12;

    /**
     * list out the modules and show the CAP of all the modules or a particular sem.
     *
     * @param ui        prints things to the user.
     * @param CAPList   deals stores semNumber, moduleCode, moduleCredits and CAP score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @throws IOException catch the error if the read file fails.
     */
    public listCAPCommand(Ui ui, Map<String, ArrayList<CAPCommand>> CAPList, String lineBreak) throws IOException {
        double cap;
        calculateCAPCommand calculatedGPA = new calculateCAPCommand();
        System.out.print("Which sem do you want to list? all,1,2,3,4,5,6,7,8\n");
        ui.readCommand();
        if (ui.fullCommand.equals("all")) {
            cap = calculatedGPA.CAPCalculator(CAPList);
            listAll(CAPList, lineBreak, cap);
        } else {
            cap = calculatedGPA.CAPCalculatorPerSem(CAPList, ui.fullCommand);
            ListSem(CAPList, ui, lineBreak, cap);
        }
    }

    /**
     * This method list out all the modules and show the accumulative cap.
     *
     * @param CAPList   deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @param cap       CAP of the modules.
     */
    public void listAll(final Map<String, ArrayList<CAPCommand>> CAPList, final String lineBreak, final double cap) {
        System.out.print("Sem | Module code | MC | CAP\n" + lineBreak);
        for (String key : CAPList.keySet()) {
            for (int i = 0; i < CAPList.get(key).size(); i++) {
                int noBlankSpacing = BLANK_SPACING - CAPList.get(key).get(i).moduleCode.length();
                System.out.print(key + "   | " + CAPList.get(key).get(i).moduleCode);
                for (int j = 0; j < noBlankSpacing; j++) {
                    System.out.print(" ");
                }
                System.out.print("| " + CAPList.get(key).get(i).moduleCredit + "  | " + CAPList.get(key).get(i).grade
                        + "\n" + lineBreak);
            }
        }
        System.out.print("Total CAP: " + cap + "\n");
    }

    /**
     * This method list out the modules and show the GPA of the a particular sem.
     *
     * @param ui        prints things to the user
     * @param CAPList   deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @param cap       CAP of the modules.
     */
    public void ListSem(Map<String, ArrayList<CAPCommand>> CAPList, Ui ui, String lineBreak, double cap) {
        System.out.print("Sem | Module code | MC | CAP\n" + lineBreak);
        for (String key : CAPList.keySet()) {
            for (int i = 0; i < CAPList.get(key).size(); i++) {
                if (key.equals(ui.fullCommand)) {
                    int noBlankSpacing = BLANK_SPACING - CAPList.get(key).get(i).moduleCode.length();
                    System.out.print(key + "   | " + CAPList.get(key).get(i).moduleCode);
                    for (int j = 0; j < noBlankSpacing; j++) {
                        System.out.print(" ");
                    }
                    System.out.print("| " + CAPList.get(key).get(i).moduleCredit + "  | " + CAPList.get(key).get(i).grade
                            + "\n" + lineBreak);
                }
            }
        }
        System.out.print("Sem " + ui.fullCommand + " CAP: " + cap + "\n");
    }
}
