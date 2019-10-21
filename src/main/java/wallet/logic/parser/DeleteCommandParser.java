package wallet.logic.parser;

import wallet.logic.LogicManager;
import wallet.logic.command.DeleteCommand;
import wallet.model.record.Loan;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * The DeleteCommandParser Class converts user String to
 * appropriate parameters.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    public static final String MESSAGE_ERROR_DELETE_CONTACT = "There are loans using this contact. Unable to delete!";

    /**
     * Changes user input String to appropriate parameters
     * and returns a DeleteCommand object.
     *
     * @param input User input of command.
     * @return A DeleteCommand object.
     * @throws ParseException ParseException.
     */
    @Override
    public DeleteCommand parse(String input) throws ParseException {
        String[] arguments = input.split(" ", 2);
        int id = Integer.parseInt(arguments[1]);
        switch (arguments[0]) {

        case "contact":
            if (parseContact(id)) {
                return null;
            }
            return new DeleteCommand(arguments[0], id);

        default:
            return new DeleteCommand(arguments[0], id);
        }
    }

    private Boolean parseContact(int id) {

        ArrayList<Loan> loanList = LogicManager.getWallet().getLoanList().getLoanList();
        for (Loan l : loanList) {
            if (l.getPerson().getId() == id) {
                System.out.println(MESSAGE_ERROR_DELETE_CONTACT);
                return true;
            }
        }
        return false;
    }
}
