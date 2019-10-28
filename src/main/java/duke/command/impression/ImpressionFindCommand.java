package duke.command.impression;


import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.data.Impression;
import duke.exception.DukeException;

import java.util.ArrayList;

public class ImpressionFindCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionFindSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String searchTerm = getArg();
        String findStr = "Here are the objects that contain '" + getArg() + "':\n";
        ArrayList<DukeData> searchResult = new ArrayList<>();
        Impression impression = (Impression) core.uiContext.getObject();
        if (getSwitchVals().isEmpty()) {
            searchResult = impression.find(searchTerm);
        } else {
            if (getSwitchVals().containsKey("evidence")) {
                searchResult.addAll(impression.findEvidences(searchTerm));
            }
            if (getSwitchVals().containsKey("treatment")) {
                searchResult.addAll(impression.findTreatments(searchTerm));
            }
        }

        StringBuilder information = new StringBuilder("");

        for (int i = 0; i < searchResult.size(); i++) {
            information.append(i + 1).append(". ");
            information.append(searchResult.get(i).getName()).append(System.lineSeparator());
        }
        core.ui.print(findStr + information.toString());
    }
}
