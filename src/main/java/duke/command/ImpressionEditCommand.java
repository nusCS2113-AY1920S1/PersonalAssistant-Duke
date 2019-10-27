package duke.command;

import duke.DukeCore;
import duke.data.DukeData;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Plan;
import duke.data.Result;
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImpressionEditCommand extends DukeDataCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionEditSpec.getSpec();
    }

    // TODO: refactor, this method is a behemoth
    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String editType = uniqueDataType();
        DukeData editData;
        boolean isAppending = false;
        if (isSwitchSet("append")) {
            isAppending = true;
        }

        if (editType == null) { // edit impression
            editImpression(getImpression(core), isAppending);
        } else {
            if (isSwitchSet("description")) {
                throw new DukeHelpException("Descriptions are only for impressions!", this);
            }

            // find DukeData of editType
            // TODO: proper search with disambiguation
            // TODO: select by index

            String editDataName = getSwitchVal(editType);
            List<DukeData> searchResults = getImpression(core).find(editDataName);
            switch (editType) {
            case "plan":
                editData = findDataOfClass(searchResults, Plan.class);
                break;
            case "medicine":
                editData = findDataOfClass(searchResults, Medicine.class);
                break;
            case "investigation":
                editData = findDataOfClass(searchResults, Investigation.class);
                break;
            case "result":
                editData = findDataOfClass(searchResults, Result.class);
                break;
            case "observation":
                editData = findDataOfClass(searchResults, Observation.class);
                break;
            default:
                throw new DukeException("Invalid data type!");
            }

            if (editData == null) {
                throw new DukeHelpException("I can't find a " + editType + " item named '" + editDataName + "'!", this);
            }

            // process status
            switch (editType) {
            case "plan":
                editStatus(editData, Plan.getStatusArr());
                break;
            case "medicine":
                editStatus(editData, Medicine.getStatusArr());
                break;
            case "investigation":
                editStatus(editData, Investigation.getStatusArr());
                break;
            default:
                break;
            }

            // process priority
            String priorityStr = getSwitchVal("priority");
            if (priorityStr != null) {
                editData.setPriority(switchToInt("priority"));
            }

            // process summary
            String summaryStr = getSwitchVal("summary");
            if (summaryStr != null) {
                editData.setSummary((isAppending) ? editData.getSummary() + summaryStr : summaryStr);
            }

            // process remaining switches entered
            for (Map.Entry<String, String> entry : getSwitchVals().entrySet()) {
                String switchName = entry.getKey();
                String entryStr = entry.getValue();
                Integer entryInt = 0;
                if (getSwitchMap().get(switchName).type.equals(Integer.class)) {
                    entryInt = switchToInt(switchName);
                }

                // ignore switches that are already processed/don't need processing
                if (switchName.equals(editType) || switchName.equals("status") || switchName.equals("summary")) {
                    continue;
                }

                // TODO: create missing methods in model
                // TODO: deal with null
                if (switchName.equals("name")) {
                    //editData.setName((isAppending) ? editData.getName() + entryStr : entryStr);
                } else {
                    switch (editType) {
                    case "medicine":
                        Medicine med = (Medicine) editData;
                        switch (switchName) {
                        case "dose":
                            med.setDose((isAppending) ? med.getDose() + entryStr : entryStr);
                            break;
                        case "date":
                            //med.setStartDate((isAppending) ? med.getStartDate() + entryStr : entryStr);
                            break;
                        case "duration":
                            med.setDuration((isAppending) ? med.getDuration() + entryStr : entryStr);
                            break;
                        default:
                            throw new DukeHelpException("Medicine plans do not have this property: '"
                            + entryStr + "'", this);
                        }
                        break;
                    case "observation":
                        Observation obsv = (Observation) editData;
                        if (isSwitchSet("objective") && isSwitchSet("subjective")) {
                            throw new DukeHelpException("I don't know if you want this observation to be objective"
                            + "or subjective!", this);
                        } else if (isSwitchSet("objective")) {
                            obsv.setObjective(true);
                        } else if (isSwitchSet("subjective")) {
                            obsv.setObjective(false);
                        }
                        break;
                    case "plan": //fallthrough
                    case "result": //fallthrough
                    case "investigation": //all switches should already be handled
                        break;
                    default:
                        throw new DukeException("Invalid data type found when making edits!");
                    }
                }

            }
        }
        core.writeJsonFile();
    }

    private void editStatus(DukeData editData, List<String> statusList) throws DukeHelpException {
        assert (editData instanceof Treatment);
        String statusStr = getSwitchVal("status");
        if (statusStr != null) {
            Treatment treatment = (Treatment) editData;
            treatment.setStatusIdx(processStatus(statusStr, statusList));
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

    // this is a terrible method, kill it and replace it with proper disambiguation as soon as possible
    private DukeData findDataOfClass(List<DukeData> searchResults, Class dataClass) {
        for (DukeData result : searchResults) {
            if (result.getClass() == dataClass) {
                return result;
            }
        }
        return null;
    }
}
