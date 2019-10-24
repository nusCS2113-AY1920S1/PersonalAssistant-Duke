package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HomeHelpCommand extends ArgCommand {
    private final String filePath = "data" + File.separator + "oldHelpDetails.json";


    @Override
    protected ArgSpec getSpec() {
        return HomeHelpSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        final String[] infoFields = {"command", "summary", "format", "switches", "info"};
        HashMap<String, HashMap<String,String>> helpDetails;
        helpDetails = core.storage.loadHelpHashMap(filePath);
        if (getSwitchVals().containsKey("all")) {
            for (Map.Entry<String, HashMap<String, String>> mapElement : helpDetails.entrySet()) {
                String key = mapElement.getKey();
                HashMap<String,String> value = mapElement.getValue();
                String helpInfo = "";
                for (int i = 0; i < 3; i++) {
                    helpInfo += infoFields[i] + ": " + value.get(infoFields[i]) + "\n";
                }
                core.ui.print(helpInfo);
            }
        } else {
            for (Map.Entry<String, String> mapElement : getSwitchVals().entrySet()) {
                String key = mapElement.getKey();
                if (helpDetails.containsKey(key)) {
                    HashMap<String,String> value = helpDetails.get(key);
                    String helpInfo = "";
                    for (String field : infoFields) {
                        if (value.containsKey(field)) {
                            helpInfo += field + ": " + value.get(field) + "\n";
                        }
                    }
                    core.ui.print(helpInfo);
                }
            }
        }
    }
}
