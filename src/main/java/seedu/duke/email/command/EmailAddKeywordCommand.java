package seedu.duke.email.command;

import org.json.JSONException;
import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailKeywordPairList;
import seedu.duke.email.EmailList;
import seedu.duke.email.entity.Email;
import seedu.duke.email.parser.EmailContentParseHelper;
import seedu.duke.email.storage.EmailKeywordPairStorage;
import seedu.duke.ui.UI;

import java.io.IOException;

public class EmailAddKeywordCommand extends Command {
    EmailKeywordPairList newKeywordPairList;

    public EmailAddKeywordCommand(EmailKeywordPairList newKeywordPairList) {
        this.newKeywordPairList = newKeywordPairList;
    }

    @Override
    public boolean execute(Model model) {
        EmailKeywordPairList oldKeywordPairList = model.getKeywordPairList();
        EmailList emailList = model.getEmailList();
        removeAllOldKeywords(emailList, oldKeywordPairList);
        model.setKeywordPairList(newKeywordPairList);
        addAllNewKeywords(emailList, newKeywordPairList);
        model.updateGuiEmailList();
        try {
            EmailKeywordPairStorage.saveKeywordPairList(newKeywordPairList);
        } catch (JSONException | IOException e) {
            UI.getInstance().showError("Keyword pairs save to file fails");
            return false;
        }
        responseMsg = "Keyword add successful!";
        UI.getInstance().showResponse(responseMsg);
        return true;
    }

    private void removeAllOldKeywords(EmailList emailList, EmailKeywordPairList oldKeywordPairList) {
        for (Email email : emailList) {
            EmailContentParseHelper.clearOldKeywordPairs(email, oldKeywordPairList);
        }
    }

    private void addAllNewKeywords(EmailList emailList, EmailKeywordPairList newKeywordPairList) {
        for (Email email : emailList) {
            EmailContentParseHelper.allKeywordInEmail(email);
        }
    }
}
