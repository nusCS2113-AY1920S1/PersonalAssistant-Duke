
package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Adds a new module to the CAP list.
 */

public class AddCAPCommand {
    /**
     * Index of the grade.
     */
    private static final int GRADE_INDEX = 3;

    /**
     * Add a new module into GPAList.
     *
     * @param ui      prints things to the user.
     * @param caplist deals stores
     *                semNumber, moduleCode, moduleCredits and CAP score.
     */
    public AddCAPCommand(final Ui ui, final Map<String,
            ArrayList<CAPCommand>> caplist) {
        try {
            String toAdd = "";
            switch (ui.fullCommand.split(" ").length) {
                case 1:
                    System.out.print("Input in this format: "
                            + "semNumber,Module_Code,total_MC,CAP\n");
                    ui.readCommand();
                    toAdd = ui.fullCommand;
                    break;
                case 2:
                    toAdd = ui.fullCommand.split(" ")[1];
                    break;
                default:
                    throw new ArrayIndexOutOfBoundsException();
            }
            String[] splitAddInput = toAdd.split(",");
            String semNumber = splitAddInput[0];
            String moduleCode = splitAddInput[1];
            int moduleCredit = Integer.parseInt(splitAddInput[2]);
            String grade = splitAddInput[GRADE_INDEX];
            CAPCommand newCAP = new CAPCommand(moduleCode, moduleCredit, grade);
            if (caplist.containsKey(semNumber)) {
                caplist.get(semNumber).add(newCAP);
            } else {
                ArrayList<CAPCommand> semInfo = new ArrayList<>();
                semInfo.add(newCAP);
                caplist.put(semNumber, semInfo);
            }
            System.out.print("Successfully added: " + moduleCode + "\n");
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.print("Please Input in the correct format\n");
        }
    }
}
