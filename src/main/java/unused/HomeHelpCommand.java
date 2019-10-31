package unused;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.exception.DukeException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* @@author JeremyKwok */
public class HomeHelpCommand extends ArgCommand {
    private final String filePath = "data" + File.separator + "helpDetails.json";

    @Override
    protected ArgSpec getSpec() {
        return HomeHelpSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        final String[] infoFields = {"command", "summary", "format", "switches", "info"};
        HashMap<String, HashMap<String, String>> helpDetails;
        helpDetails = core.storage.loadHelpHashMap(filePath);
        if (getSwitchVals().isEmpty()) {
            for (Map.Entry<String, HashMap<String, String>> mapElement : helpDetails.entrySet()) {
                HashMap<String, String> value = mapElement.getValue();
                StringBuilder helpInfo = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                    helpInfo.append(infoFields[i]).append(": ").append(value.get(infoFields[i])).append("\n");
                }
                core.updateUi(helpInfo.toString());
            }
        } else {
            for (Map.Entry<String, String> mapElement : getSwitchVals().entrySet()) {
                String key = mapElement.getKey();
                if (helpDetails.containsKey(key)) {
                    HashMap<String, String> value = helpDetails.get(key);
                    StringBuilder helpInfo = new StringBuilder();
                    for (String field : infoFields) {
                        if (value.containsKey(field)) {
                            helpInfo.append(field).append(": ").append(value.get(field)).append("\n");
                        }
                    }
                    core.updateUi(helpInfo.toString());
                }
            }
        }
    }
}
