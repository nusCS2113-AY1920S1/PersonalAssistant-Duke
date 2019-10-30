package duke.logic.command.sale;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.sale.Sale;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DATE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DESCRIPTION;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_IS_SPEND;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_REMARKS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_VALUE;
import static java.util.Objects.requireNonNull;

/**
 * A command to edit the details of an existing sale.
 */
public class EditSaleCommand extends SaleCommand {

    public static final String COMMAND_WORD = "edit";

    private static final String MESSAGE_COMMIT = "Edit sale";
    private static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Sale [%1$s]";

    private final Index index;
    private final SaleDescriptor saleDescriptor;

    public static final String AUTO_COMPLETE_INDICATOR = SaleCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        PREFIX_SALE_DESCRIPTION,
        PREFIX_SALE_VALUE,
        PREFIX_SALE_IS_SPEND,
        PREFIX_SALE_DATE,
        PREFIX_SALE_REMARKS
    };

    /**
     * Creates an EditSaleCommand to modify the details of an {@code Sale}.
     *
     * @param index               of the the sale in the filtered sale list
     * @param saleDescriptor details to edit the sale with
     */
    public EditSaleCommand(Index index, SaleDescriptor saleDescriptor) {
        requireAllNonNull(index, saleDescriptor);

        this.index = index;
        this.saleDescriptor = new SaleDescriptor(saleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Sale> lastShownList = model.getFilteredSaleList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Sale saleToEdit = lastShownList.get(index.getZeroBased());

        Sale editedSale = SaleCommandUtil.modifySale(saleToEdit, saleDescriptor);

        model.setSale(saleToEdit, editedSale);

        model.updateFilteredSaleList(Model.PREDICATE_SHOW_ALL_SALES);

        model.commit(MESSAGE_COMMIT);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedSale.getId()),
                CommandResult.DisplayedPage.SALE);
    }
}