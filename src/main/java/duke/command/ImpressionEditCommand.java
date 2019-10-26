package duke.command;

import duke.DukeCore;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Observation;
import duke.data.Plan;
import duke.data.Result;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.Map;

public class ImpressionEditCommand extends DukeDataCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionEditSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String editType = uniqueDataType();
        boolean isAppending = false;
        if (isSwitchSet("append")) {
            isAppending = true;
        }

        if (editType == null) { // edit patients
            editImpression(getImpression(core), isAppending);
        } else {
            if (isSwitchSet("description")) {
                throw new DukeHelpException("Descriptions are only for impressions!", this);
            }

            // find DukeData
            // ...

            // process each switch entered
            for (Map.Entry<String, String> entry : getSwitchVals().entrySet()) {
                String switchName = entry.getKey();
                Integer entryInt = 0;
                if (getSwitchMap().get(switchName).type.equals(Integer.class)) {
                    entryInt = switchToInt(switchName);
                }

                if (switchName.equals(editType)) {
                    continue;
                }

                switch (editType) {
                case "plan":
                case "medicine":
                case "investigation":
                case "result":
                case "observation":
                default:
                    throw new DukeException("Invalid data type!");
                }

            }
        }
    }

    private void editImpression(Impression impression, boolean isAppending) {
        /*if (getSwitchVal("name") != null) {

        }*/
        String newDesc = getSwitchVal("description");
        if (newDesc != null) {
            impression.setDescription((isAppending) ? impression.getDescription() + newDesc : newDesc);
        }
    }
}
