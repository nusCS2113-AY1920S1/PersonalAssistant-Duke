package gazeeebo.commands.gpacalculator;

import gazeeebo.UI.Ui;

import java.util.ArrayList;
import java.util.Map;

public class FindGPACommand {
    /*To make the vertical line after module code uniform */
    static final int BLANK_SPACING = 12;

    /**
     * Finds modules base on semNumber or moduleCode and list it out.
     *
     * @param ui         the object that prints things to the user.
     * @param gpalist    the object that deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak the object that print out a separator to separate each line in the list.
     */
    public FindGPACommand(final Ui ui, final Map<String, ArrayList<GPACommand>> gpalist, final String lineBreak) {
        System.out.print("Sem | Module code | MC | GPA\n" + lineBreak);
        String findInput = ui.fullCommand.split(" ")[1];
        boolean isExist = false;
        for (String key : gpalist.keySet()) {
            for(int i = 0; i < gpalist.get(key).size(); i++) {
                if (key.equals(findInput)) {
                    int noBlankSpacing = BLANK_SPACING - gpalist.get(key).get(i).moduleCode.length();
                    System.out.print(key + "   | " + gpalist.get(key).get(i).moduleCode);
                    for (int j = 0; j < noBlankSpacing; j++) {
                        System.out.print(" ");
                    }
                    System.out.print("| " + gpalist.get(key).get(i).moduleCredit + "  | " + gpalist.get(key).get(i).grade
                            + "\n" + lineBreak);
                    isExist = true;
                } else if (gpalist.get(key).get(i).moduleCode.equals(findInput)) {
                    int noBlankSpacing = BLANK_SPACING - gpalist.get(key).get(i).moduleCode.length();
                    System.out.print(key + "   | " + gpalist.get(key).get(i).moduleCode);
                    for (int j = 0; j < noBlankSpacing; j++) {
                        System.out.print(" ");
                    }
                    System.out.print("| " + gpalist.get(key).get(i).moduleCredit + "  | " + gpalist.get(key).get(i).grade
                            + "\n" + lineBreak);
                    isExist = true;
                }
            }
        }
        if(!isExist) {
            System.out.println(findInput + " is not found in the list.");
        }
    }
}
