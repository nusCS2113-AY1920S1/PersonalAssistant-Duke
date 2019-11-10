package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeData;
import duke.data.DukeObject;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.data.Treatment;
import duke.exception.DukeException;

import java.util.List;

public class ImpressionMoveSpec extends ObjSpec {
    private static final ImpressionMoveSpec spec = new ImpressionMoveSpec();
    private Impression currImpression = null;
    private Impression newImpression = null;
    private DukeData moveData = null;
    private SearchResults dataResults = null;

    public static ImpressionMoveSpec getSpec() {
        return spec;
    }

    private ImpressionMoveSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t"),
                new Switch("impression", String.class, true, ArgLevel.REQUIRED, "im")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        currImpression = ImpressionUtils.getImpression(core);
        moveData = ImpressionUtils.getDataByIdx(cmd.getArg(), cmd.getSwitchVal("evidence"),
                cmd.getSwitchVal("treatment"), currImpression);
        if (moveData == null) {
            dataResults = ImpressionUtils.searchData(cmd.getArg(), cmd.getSwitchVal("evidence"),
                    cmd.getSwitchVal("treatment"), ImpressionUtils.getImpression(core));
            if (dataResults.getCount() == 0) {
                throw new DukeException("No results found for '" + dataResults.getName() + "'!");
            } else if (dataResults.getCount() == 1) {
                moveData = (DukeData) dataResults.getResult(0);
            }
        }

        SearchResults impressionResults = findImpression(core);
        if (impressionResults.getCount() == 0) {
            throw new DukeException("No results found for '" + impressionResults.getName() + "'!");
        } else if (impressionResults.getCount() == 1) {
            newImpression = (Impression) impressionResults.getResult(0);
        } else {
            core.uiContext.open(impressionResults);
        }

        if (moveData != null && newImpression != null) {
            executeWithObj(core, moveData);
        }
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        if (newImpression == null) { // impression has not been identified
            newImpression = (Impression) obj;
            if (moveData == null) {
                core.uiContext.open(dataResults);
                return;
            }
        } else if (moveData == null) {
            moveData = (DukeData) obj;
        }

        if (moveData instanceof Evidence) {
            Evidence evidence = (Evidence) moveData;
            newImpression.addNewEvidence(evidence);
            currImpression.deleteEvidence(evidence.getName());
        } else if (moveData instanceof Treatment) {
            Treatment treatment = (Treatment) moveData;
            newImpression.addNewTreatment(treatment);
            currImpression.deleteTreatment(treatment.getName());
        }
        moveData.setParent(newImpression);
        core.writeJsonFile();
        core.updateUi("'" + moveData.getName() + "' moved from '" + currImpression.getName() + "' to '"
                + newImpression.getName() + "'");
        newImpression = null;
        dataResults = null;
        moveData = null;
    }

    private SearchResults findImpression(DukeCore core) throws DukeException {
        SearchResults results;
        String targetImpressionName = cmd.getSwitchVal("impression");
        Patient patient = currImpression.getParent();
        if (targetImpressionName == null) {
            results = new SearchResults("Impressions of this Patient", patient.getImpressionList(),
                    patient);
        } else {
            results = patient.findImpressionsByName(targetImpressionName);
        }

        List<DukeObject> resultList = results.getSearchList();
        int currImpIdx = resultList.indexOf(currImpression);
        if (currImpIdx != -1) { // remove this impression from the list if present
            resultList.remove(currImpIdx);
            results = new SearchResults(results.getName(), resultList, results.getParent());
        }
        return results;
    }
}
