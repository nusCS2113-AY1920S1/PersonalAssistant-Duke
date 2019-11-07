package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;
import gazeeebo.parsers.CAPCommandParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Deletes a module from the CAP list.
 */
public class DeleteCAPCommand {
    /**
     * Delete the module from CAPList.
     *
     * @param ui      prints to the user.
     * @param caplist deals stores
     *                semNumber, moduleCode, moduleCredits and GPA score.
     */
    public DeleteCAPCommand(final Ui ui,
                            final Map<String, ArrayList<CAPCommandParser>> caplist) {
        try {
            String moduleName = "";
            switch (ui.fullCommand.split(" ").length) {
                case 1:
                    System.out.print("Which module do you want to delete?\n");
                    ui.readCommand();
                    moduleName = ui.fullCommand;
                    break;
                case 2:
                    moduleName = ui.fullCommand.split(" ")[1];
                    break;
                default:
                    throw new ArrayIndexOutOfBoundsException();
            }
            boolean haveDeleted = false;
            for (String key : caplist.keySet()) {
                /*Only one element in the array */
                if (moduleName.equals(caplist.get(key).get(0).moduleCode)
                        && (caplist.get(key).size() == 1)) {
                    caplist.remove(key); //Remove the entire key
                    haveDeleted = true;
                    break;
                } else {
                    for (int i = 0; i < caplist.get(key).size(); i++) {
                        if (moduleName.equals(caplist.
                                get(key).get(i).moduleCode)) {
                            caplist.get(key).remove(i);
                            haveDeleted = true;
                            break;
                        }
                    }
                }
            }
            if (!haveDeleted) {
                System.out.print(ui.fullCommand.split(" ")[1]
                        + " is not found in the list.\n");
            } else {
                System.out.print("Successfully deleted: " + moduleName + "\n");
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.print("Please Input in the correct format\n");
        }

    }
}
