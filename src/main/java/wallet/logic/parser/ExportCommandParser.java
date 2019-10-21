package wallet.logic.parser;

import wallet.logic.LogicManager;
import wallet.logic.command.ExportCommand;
import wallet.model.record.Loan;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Returns an ExportCommand object.
     *
     * @param input User input of command.
     * @return An ExportCommand object.
     */
    @Override
    public ExportCommand parse(String input) {

        if ("loans".equals(input)) {
            List<String[]> data = parseLoan();
            parseLoan();
            return new ExportCommand(data);
        }

        return null;
    }

    private List<String[]> parseLoan() {

        ArrayList<Loan> loanList = LogicManager.getWallet().getLoanList().getLoanList();
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Budget", "$1000"});
        data.add(new String[]{"S/N", "Description", "Amount", "Created Date", "Name", "Phone", "Other Details", "Lend/Borrow", "Settled"});
        int index = 1;
        for (Loan l : loanList) {
            String indexOutput = Integer.toString(index);
            String description = l.getDescription();
            String amount = Double.toString(l.getAmount());
            String createdDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(l.getDate());
            String isLend = (l.getIsLend()) ? "lend" : "borrowed";
            String isSettled = (l.getIsSettled()) ? "yes" : "no";
            String personName = l.getPerson().getName();
            String personPhone = l.getPerson().getPhoneNum();
            if (personPhone == null) {
                personPhone = "";
            }
            String personDetail = l.getPerson().getDetail();
            if (personDetail == null) {
                personDetail = "";
            }
            data.add(new String[]{indexOutput, description, amount, createdDate, personName, personPhone, personDetail, isLend, isSettled});
            index++;
        }
        return data;
    }
}
