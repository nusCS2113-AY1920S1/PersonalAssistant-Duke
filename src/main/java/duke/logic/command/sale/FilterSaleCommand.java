package duke.logic.command.sale;


import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import javafx.util.Pair;

import java.util.Date;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_FROM;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_TO;
import static java.util.Objects.requireNonNull;

public class FilterSaleCommand extends SaleCommand {
    public static final String COMMAND_WORD = "filter";

    private static final String MESSAGE_COMMIT = "Filter sale";
    private static final String MESSAGE_FILTER_SUCCESS = "Showing between ";

    public static final String AUTO_COMPLETE_INDICATOR = SaleCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        PREFIX_SALE_FROM,
        PREFIX_SALE_TO
    };

    private final Pair<Date, Date> fromToDates;

    public FilterSaleCommand(Pair<Date, Date> pairDate) {
        requireAllNonNull(pairDate);
        this.fromToDates = pairDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredSaleList(model.getSalesBetween(this.fromToDates.getKey(), this.fromToDates.getValue()));

        model.commit(MESSAGE_COMMIT);

        return new CommandResult(MESSAGE_FILTER_SUCCESS
                + fromToDates.getKey().toString()
                + " and "
                + fromToDates.getValue().toString(),
                CommandResult.DisplayedPage.SALE);
    }
}
