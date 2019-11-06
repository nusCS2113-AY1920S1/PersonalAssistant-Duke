package duke.logic;

import duke.commons.core.LogsCenter;
import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.ExitCommand;
import duke.logic.command.RedoCommand;
import duke.logic.command.UndoCommand;
import duke.logic.command.exceptions.CommandException;
import duke.logic.command.inventory.AddInventoryCommand;
import duke.logic.command.inventory.ClearInventoryCommand;
import duke.logic.command.inventory.DeleteInventoryCommand;
import duke.logic.command.inventory.EditInventoryCommand;
import duke.logic.command.inventory.InventoryCommand;
import duke.logic.command.order.AddOrderCommand;
import duke.logic.command.order.CompleteOrderCommand;
import duke.logic.command.order.DeleteOrderCommand;
import duke.logic.command.order.EditOrderCommand;
import duke.logic.command.order.OrderCommand;
import duke.logic.command.order.ShowOrderCommand;
import duke.logic.command.order.SortOrderCommand;
import duke.logic.command.product.AddProductCommand;
import duke.logic.command.product.EditProductCommand;
import duke.logic.command.product.ListProductCommand;
import duke.logic.command.product.ProductCommand;
import duke.logic.command.product.SearchProductCommand;
import duke.logic.command.product.ShowProductCommand;
import duke.logic.command.product.SortProductCommand;
import duke.logic.command.sale.AddSaleCommand;
import duke.logic.command.sale.DeleteSaleCommand;
import duke.logic.command.sale.EditSaleCommand;
import duke.logic.command.sale.FilterSaleCommand;
import duke.logic.command.sale.SaleCommand;
import duke.logic.command.sale.ShowSaleCommand;
import duke.logic.command.shopping.AddShoppingCommand;
import duke.logic.command.shopping.BuyShoppingCommand;
import duke.logic.command.shopping.ClearShoppingCommand;
import duke.logic.command.shopping.DeleteShoppingCommand;
import duke.logic.command.shopping.EditShoppingCommand;
import duke.logic.command.shopping.ShoppingCommand;
import duke.logic.command.shortcut.ExecuteShortcutCommand;
import duke.logic.command.shortcut.SetShortcutCommand;
import duke.logic.parser.commons.AutoCompleter;
import duke.logic.parser.commons.BakingHomeParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Order;
import duke.model.product.Product;
import duke.model.sale.Sale;
import duke.storage.BakingHomeStorage;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.logging.Logger;

public class LogicManager implements Logic {
    private final Model model;
    private final BakingHomeStorage storage;
    private final BakingHomeParser bakingHomeParser;
    private final AutoCompleter autoCompleter;

    private static LogicManager theLogicManager = null;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates a logic manager.
     */
    private LogicManager(Model model, BakingHomeStorage storage) {
        this.model = model;
        this.storage = storage;
        this.bakingHomeParser = new BakingHomeParser();
        this.autoCompleter = new AutoCompleter();
        addCommandsToAutoComplete();
    }

    public static LogicManager getInstance(Model model, BakingHomeStorage storage) {
        if (theLogicManager == null) {
            theLogicManager = new LogicManager(model, storage);
        }
        return theLogicManager;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        CommandResult commandResult;
        Command command = bakingHomeParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveBakingHome(model.getBakingHome());
        } catch (IOException ioe) {
            logger.severe(ioe.getMessage());
            throw new CommandException(ioe.getMessage(), ioe);
        }

        return commandResult;
    }

    @Override
    public Boolean isAutoCompletable(AutoCompleter.UserInputState currentState) {
        return autoCompleter.isAutoCompletable(currentState);
    }

    @Override
    public AutoCompleter.UserInputState complete() {
        return autoCompleter.complete();
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return model.getFilteredOrderList();
    }

    @Override
    public ObservableList<Sale> getFilteredSaleList() {
        return model.getFilteredSaleList();
    }

    @Override
    public ObservableList<Product> getFilteredProductList() {
        model.updateFilteredProductList(Model.PREDICATE_SHOW_ACTIVE_PRODUCTS);
        return model.getFilteredProductList();
    }

    @Override
    public ObservableList<Item<Ingredient>> getFilteredInventoryList() {
        return model.getFilteredInventoryList();
    }

    @Override
    public ObservableList<Item<Ingredient>> getFilteredShoppingList() {
        return model.getFilteredShoppingList();
    }

    private void addCommandsToAutoComplete() {
        //Order commands
        autoCompleter.addCommandClass(OrderCommand.class);
        autoCompleter.addCommandClass(SortOrderCommand.class);
        autoCompleter.addCommandClass(AddOrderCommand.class);
        autoCompleter.addCommandClass(DeleteOrderCommand.class);
        autoCompleter.addCommandClass(EditOrderCommand.class);
        autoCompleter.addCommandClass(CompleteOrderCommand.class);
        autoCompleter.addCommandClass(ShowOrderCommand.class);

        //Shortcut commands
        autoCompleter.addCommandClass(SetShortcutCommand.class);
        autoCompleter.addCommandClass(ExecuteShortcutCommand.class);

        //Sale commands
        autoCompleter.addCommandClass(SaleCommand.class);
        autoCompleter.addCommandClass(AddSaleCommand.class);
        autoCompleter.addCommandClass(DeleteSaleCommand.class);
        autoCompleter.addCommandClass(EditSaleCommand.class);
        autoCompleter.addCommandClass(FilterSaleCommand.class);
        autoCompleter.addCommandClass(ShowSaleCommand.class);

        //Product commands
        autoCompleter.addCommandClass(ProductCommand.class);
        autoCompleter.addCommandClass(AddProductCommand.class);
        autoCompleter.addCommandClass(EditProductCommand.class);
        autoCompleter.addCommandClass(ShowProductCommand.class);
        autoCompleter.addCommandClass(ListProductCommand.class);
        autoCompleter.addCommandClass(SearchProductCommand.class);
        autoCompleter.addCommandClass(SortProductCommand.class);

        //Inventory commands
        autoCompleter.addCommandClass(InventoryCommand.class);
        autoCompleter.addCommandClass(AddInventoryCommand.class);
        autoCompleter.addCommandClass(EditInventoryCommand.class);
        autoCompleter.addCommandClass(DeleteInventoryCommand.class);
        autoCompleter.addCommandClass(ClearInventoryCommand.class);
        autoCompleter.addCommandClass(ShoppingCommand.class);
        autoCompleter.addCommandClass(AddShoppingCommand.class);
        autoCompleter.addCommandClass(EditShoppingCommand.class);
        autoCompleter.addCommandClass(DeleteShoppingCommand.class);
        autoCompleter.addCommandClass(ClearShoppingCommand.class);
        autoCompleter.addCommandClass(BuyShoppingCommand.class);

        //Undo and Redo
        autoCompleter.addCommandClass(UndoCommand.class);
        autoCompleter.addCommandClass(RedoCommand.class);

        //Exit command
        autoCompleter.addCommandClass(ExitCommand.class);

    }
}
