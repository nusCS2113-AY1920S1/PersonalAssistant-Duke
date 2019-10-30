package duke.logic.command.sale;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.sale.Sale;

import java.util.Calendar;
import java.util.Date;

import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DATE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DESCRIPTION;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_IS_SPEND;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_REMARKS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_VALUE;
import static java.util.Objects.requireNonNull;

/**
 * A command to add a sale to BakingHome.
 */
public class AddSaleCommand extends SaleCommand {

    public static final String COMMAND_WORD = "add";
    private static final String MESSAGE_COMMIT = "Add sale";
    private static final String MESSAGE_SUCCESS = "New sale added [Sale ID: %s]";

    private static final String DEFAULT_DESCRIPTION = "N/A";
    private static final double DEFAULT_VALUE = 0.0;
    private static final boolean DEFAULT_TRUTH = false;
    private static final Date DEFAULT_SALE_DATE = Calendar.getInstance().getTime();
    private static final String DEFAULT_REMARKS = "N/A";

    private final SaleDescriptor addSaleDescriptor;

    public static final String AUTO_COMPLETE_INDICATOR = SaleCommand.COMMAND_WORD + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        PREFIX_SALE_DESCRIPTION,
        PREFIX_SALE_VALUE,
        PREFIX_SALE_IS_SPEND,
        PREFIX_SALE_DATE,
        PREFIX_SALE_REMARKS
    };

    /**
     * Creates an AddSaleCommand to add the specified {@code Sale}.
     *
     * @param addSaleDescriptor details of the sale to add
     */
    public AddSaleCommand(SaleDescriptor addSaleDescriptor) {
        requireNonNull(addSaleDescriptor);
        this.addSaleDescriptor = addSaleDescriptor;
    }

    /**
     * Executes the add order command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model) throws CommandException {

        Sale toAdd = createSale(addSaleDescriptor);
        model.addSale(toAdd);
        model.commit(MESSAGE_COMMIT);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getId()), CommandResult.DisplayedPage.SALE);
    }

    private Sale createSale(SaleDescriptor saleDescriptor) {
        String description = saleDescriptor.getDescription().orElse(DEFAULT_DESCRIPTION);
        double value = saleDescriptor.getValue().orElse(DEFAULT_VALUE);
        boolean isSpend = saleDescriptor.isSpend().orElse(DEFAULT_TRUTH);
        Date saleDate = saleDescriptor.getSaleDate().orElse(DEFAULT_SALE_DATE);
        String remarks = saleDescriptor.getRemarks().orElse(DEFAULT_REMARKS);
        return new Sale(description, value, isSpend, saleDate, remarks);
    }
}