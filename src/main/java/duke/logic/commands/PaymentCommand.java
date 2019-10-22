package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.user.User;
import duke.storage.Storage;
import duke.ui.Ui;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class PaymentCommand extends Command{
    private BigDecimal paymentAmount;

    public PaymentCommand(String paymentAmountString, String date) throws DukeException {
        this.paymentAmount = new BigDecimal(paymentAmountString);
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException("Unable to parse input" + date + "as a date.");
        }
        this.currentDate = dateFormat.format(parsedDate);
    }

    public PaymentCommand(String paymentAmountString) {
        this.paymentAmount = new BigDecimal(paymentAmountString);
    }

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in) throws DukeException {

    }
}
