package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;

import java.util.ArrayList;
import java.util.Map;

public class findCAPCommand {
    /*Printing blank spaces*/
    private static final int BLANK_SPACING = 12;

    /**
     * Finds modules base on semNumber or moduleCode and list it out.
     *
     * @param ui        prints things to the user.
     * @param CAPList   deals stores semNumber, moduleCode, moduleCredits and CAP score.
     * @param lineBreak print out a separator to separate each line in the list.
     */
    public findCAPCommand(Ui ui, Map<String, ArrayList<CAPCommand>> CAPList, String lineBreak) {
        String findInput = ui.fullCommand.split(" ")[1];
        String toPrint = "";
        boolean isExist = false;
        for (String key : CAPList.keySet()) {
            for (int i = 0; i < CAPList.get(key).size(); i++) {
                if (key.equals(findInput)) {
                    int noBlankSpacing = BLANK_SPACING - CAPList.get(key).get(i).moduleCode.length();
                    toPrint += (key + "   | " + CAPList.get(key).get(i).moduleCode);
                    for (int j = 0; j < noBlankSpacing; j++) {
                        toPrint += (" ");
                    }
                    toPrint += ("| " + CAPList.get(key).get(i).moduleCredit + "  | " + CAPList.get(key).get(i).grade
                            + "\n" + lineBreak);
                    isExist = true;
                } else if (CAPList.get(key).get(i).moduleCode.equals(findInput)) {
                    int noBlankSpacing = BLANK_SPACING - CAPList.get(key).get(i).moduleCode.length();
                    toPrint += key + "   | " + CAPList.get(key).get(i).moduleCode;
                    for (int j = 0; j < noBlankSpacing; j++) {
                        toPrint += " ";
                    }
                    toPrint += "| " + CAPList.get(key).get(i).moduleCredit + "  | " + CAPList.get(key).get(i).grade
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
    }
}
