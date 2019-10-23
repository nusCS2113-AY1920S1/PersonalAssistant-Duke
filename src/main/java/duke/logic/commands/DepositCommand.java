package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.model.user.User;
import duke.storage.Storage;
import duke.ui.Ui;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class DepositCommand extends Command{
    private BigDecimal depositAmount;

    public DepositCommand(String depositAmountString, String date) throws DukeException {
        this.depositAmount = new BigDecimal(depositAmountString);
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException("Unable to parse input" + date + "as a date.");
        }
        this.currentDate = dateFormat.format(parsedDate);

    }

    public DepositCommand(String depositAmountString) {
        this.depositAmount = new BigDecimal(depositAmountString);
    }

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in, TransactionList transactions) throws DukeException {
    }
}
