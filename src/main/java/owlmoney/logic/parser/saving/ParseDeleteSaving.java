package owlmoney.logic.parser.saving;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import owlmoney.logic.command.bank.DeleteSavingsCommand;
import owlmoney.model.profile.Profile;

import owlmoney.logic.command.bank.AddSavingsCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseDeleteSaving extends ParseSaving {

    public ParseDeleteSaving(String data) {
        super(data);
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = savingsParameters.keySet().iterator();
        //this is temporary. need to amend
        //for now just checking the name field
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = savingsParameters.get(key);
            if (NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting savings account");
            }
        }
    }

    public void passToCommand(Profile profile) {
        DeleteSavingsCommand newDeleteSavingsCommand = new DeleteSavingsCommand(savingsParameters.get(NAME));
        newDeleteSavingsCommand.execute(profile);
    }

}
