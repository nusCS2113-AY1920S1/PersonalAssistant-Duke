package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailKeywordPairList;
import seedu.duke.email.EmailList;
import seedu.duke.email.entity.Email;
import seedu.duke.email.parser.EmailContentParseHelper;

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
