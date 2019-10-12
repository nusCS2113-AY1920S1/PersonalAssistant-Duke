package duke.logic;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.BakingHomeParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import duke.model.ReadOnlyBakingHome;
import duke.model.commons.Ingredient;
import duke.model.order.Order;
import duke.model.sale.Sale;
import duke.model.product.Product;
import duke.storage.Storage;
import javafx.collections.ObservableList;

public class LogicManager implements Logic {
    private final Model model;
    private final Storage storage;
    private final BakingHomeParser bakingHomeParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.bakingHomeParser = new BakingHomeParser();
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
    public ReadOnlyBakingHome getReadingHome() {
        return null;
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
        return model.getFilteredProductList();
    }

    @Override
    public ObservableList<Ingredient> getFilteredInventoryList() {
        return model.getFilteredInventoryList();
    }
}
