package duke.logic;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.command.order.AddOrderCommand;
import duke.logic.command.order.CompleteOrderCommand;
import duke.logic.command.order.DeleteOrderCommand;
import duke.logic.command.order.EditOrderCommand;
import duke.logic.command.order.OrderCommand;
import duke.logic.command.product.EditProductCommand;
import duke.logic.command.product.ProductCommand;
import duke.logic.parser.commons.AutoCompleter;
import duke.logic.parser.commons.BakingHomeParser;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import duke.model.ReadOnlyBakingHome;
import duke.model.commons.Ingredient;
import duke.model.order.Order;
import duke.model.product.Product;
import duke.storage.Storage;
import javafx.collections.ObservableList;

public class LogicManager implements Logic {
    private final Model model;
    private final Storage storage;
    private final BakingHomeParser bakingHomeParser;
    private final AutoCompleter autocompleter;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.bakingHomeParser = new BakingHomeParser();
        this.autocompleter = new AutoCompleter();
        addFieldsToAutoComplete();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult commandResult;
        Command command = bakingHomeParser.parseCommand(commandText);
        commandResult = command.execute(model);

//        try {
//            storage.saveAddressBook(model.getAddressBook());
//        } catch (IOException ioe) {
//            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
//        }

        return commandResult;
    }

    @Override
    public AutoCompleter.UserInputState getAutoCompletion(String commandText, int caretPosition) {
        return autocompleter.getAutoCompletion(new AutoCompleter.UserInputState(commandText, caretPosition));
    }

    @Override
    public ReadOnlyBakingHome getBakingHome() {
        //TODO: Clear this
        return null;
    }


    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return model.getFilteredOrderList();
    }

    @Override
    public ObservableList<Product> getFilteredProductList() {
        model.updateFilteredProductList(Model.PREDICATE_SHOW_ACTIVE_PRODUCTS);
        return model.getFilteredProductList();
    }

    @Override
    public ObservableList<Ingredient> getFilteredInventoryList() {
        return model.getFilteredInventoryList();
    }

    private void addFieldsToAutoComplete() {
        autocompleter.addCommand(OrderCommand.class);
        autocompleter.addCommand(AddOrderCommand.class);
        autocompleter.addCommand(DeleteOrderCommand.class);
        autocompleter.addCommand(EditOrderCommand.class);
        autocompleter.addCommand(CompleteOrderCommand.class);
        autocompleter.addCommand(ProductCommand.class);
        autocompleter.addCommand(AddOrderCommand.class);
        autocompleter.addCommand(EditProductCommand.class);

        autocompleter.addPrefix(CliSyntax.PREFIX_CUSTOMER_CONTACT);
        autocompleter.addPrefix(CliSyntax.PREFIX_CUSTOMER_NAME);
        autocompleter.addPrefix(CliSyntax.PREFIX_ORDER_DEADLINE);
        autocompleter.addPrefix(CliSyntax.PREFIX_ORDER_INDEX);
        autocompleter.addPrefix(CliSyntax.PREFIX_ORDER_ITEM);
        autocompleter.addPrefix(CliSyntax.PREFIX_ORDER_STATUS);
        autocompleter.addPrefix(CliSyntax.PREFIX_ORDER_REMARKS);
        autocompleter.addPrefix(CliSyntax.PREFIX_ORDER_TOTAL);
        autocompleter.addPrefix(CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST);
        autocompleter.addPrefix(CliSyntax.PREFIX_PRODUCT_INDEX);
        autocompleter.addPrefix(CliSyntax.PREFIX_PRODUCT_INGREDIENT);
        autocompleter.addPrefix(CliSyntax.PREFIX_PRODUCT_NAME);
        autocompleter.addPrefix(CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE);
        autocompleter.addPrefix(CliSyntax.PREFIX_PRODUCT_STATUS);
    }
}
