package gazeeebo.commands.gpacalculator;

import gazeeebo.UI.Ui;

import java.util.ArrayList;
import java.util.Map;

public class DeleteGPACommand {
    /**
     * Delete the module from GPAList.
     *
     * @param ui the object that prints things to the user.
     * @param gpalist the object that deals stores semNumber, moduleCode, moduleCredits and GPA score.
     */
    public DeleteGPACommand(final Ui ui, final Map<String, ArrayList<GPACommand>> gpalist) {
        String moduleName = ui.fullCommand.split(" ")[1];
        for (String key: gpalist.keySet()) {
            for (int i = 0; i < gpalist.get(key).size(); i++) {
                if (ui.fullCommand.equals("delete")) {
                    System.out.print("Incorrect format: delete module\n");
                    break;
                } else if (moduleName.equals(gpalist.get(key).get(i).moduleCode)) {
                    if (gpalist.get(key).size() == 1) {
                        gpalist.remove(key);
                    } else {
                        gpalist.get(key).remove(i);
                    }
                    System.out.println("Successfully deleted: " + moduleName);
                    break;
                }
            }
        }
    }
}
