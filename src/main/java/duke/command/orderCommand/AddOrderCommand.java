package duke.command.orderCommand;

import duke.command.dishesCommand.AddDishCommand;
import duke.command.ingredientCommand.AddCommand;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddOrderCommand extends AddCommand<Order> {

    private Order order;
    private String content;

    /**
     * The constructor method for AddOrderCommand.
     *
     * @param order : the {@link Order} to be added in the list
     */
    public AddOrderCommand(Order order, String command) {
        super(order);
        this.order = order;
        this.content = command;
    }

    /**
     * Public method used to add a new order in the orderList, and write it on the hard disc.
     *
     * @param orderList the {@link OrderList} to be expanded
     * @param ui       {@link Ui} used for printing the order output
     * @param storage  {@link Storage} writes in the file on the hard disc
     * @throws DukeException Error while adding the command to the duke.txt file
     */
    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {

        Map<Dish, Integer> dishList = parse();

        for (Map.Entry<Dish, Integer> entry : dishList.entrySet()) {
            Dish dish = entry.getKey();
            int amount = entry.getValue();
            order.addDish(dish, amount);
            new AddDishCommand(dish, amount);
        }

        orderList.addEntry(order);
        ui.showAddOrder(order.toString(), orderList.size());

        try {
            storage.addInFile(order.printInFile());

            //To do
            //how to read from order.txt
            //separate storage from task storage class

        } catch (IOException e) {
            throw new DukeException("Error while adding the command to the orders.txt file");
        }
    }

    public Map<Dish, Integer> parse() {
        String[] splitted = content.split(", ");
        Map<Dish, Integer> dishList = new HashMap<>();
        for (String s: splitted) {
            String dishName = s.split("\\*", 2)[0];
            Dish dishes = findDishes(dishName);
            int dishAmount = Integer.parseInt(s.split("\\*", 2)[1]);
            dishList.put(dishes, dishAmount);
        }
        return dishList;
    }

    public Dish findDishes(String dishName) {
        // to do
        return new Dish();
    }


}
