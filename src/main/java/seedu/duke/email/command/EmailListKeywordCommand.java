package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailKeywordPairList;
import seedu.duke.email.entity.KeywordPair;
import seedu.duke.ui.UI;


public class EmailListKeywordCommand extends Command {

    @Override
    public boolean execute(Model model) {
        responseMsg = "Here is your keyword list: " + System.lineSeparator();
        EmailKeywordPairList keywordPairList = model.getKeywordPairList();
        int i = 0;
        for (KeywordPair keywordPair : keywordPairList) {
            i++;
            responseMsg += i + ". ";
            responseMsg += keywordPair.getKeyword() + System.lineSeparator();
            responseMsg += "Expressions: " + keywordPair.getExpressions() + System.lineSeparator();
        }
        UI.getInstance().showResponse(responseMsg);
        return true;
    }
}
