package gazeeebo.commands.gpacalculator;

import gazeeebo.UI.Ui;

import java.util.Map;

public class FindGPACommand {
    public FindGPACommand(final Ui ui, final Map<String, GPACommand> GPAList, final String LINE_BREAK) {
        System.out.println("Module code | MC | GPA\n" + LINE_BREAK);
        for(String key:GPAList.keySet()) {
            if(key.equals(ui.fullCommand.split(" ")[1])) {
                System.out.println(key + "      | " + GPAList.get(key).moduleCredit + "  | " + GPAList.get(key).score);
                System.out.println(LINE_BREAK);
            }
        }
    }
}
