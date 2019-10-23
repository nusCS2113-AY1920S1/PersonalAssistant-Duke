package duke.command.orderCommand;

import duke.command.Cmd;
import duke.command.ingredientCommand.AddCommand;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

public class AddOrder extends Cmd<Order> {

    private Order order;

    /**
     * The constructor method (1) for AddCommand.
     */
    public AddOrder() {
        this.order = new Order();
    }

    /**
     * The constructor method (2) for AddCommand.
     *
     * @param order : the {@link Order} to be added in the list
     */
    public AddOrder(Order order) {
        this.order = order;
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

        printStartMsg(ui);
        String orderDate = getOrderDate(ui);
        printMenu();

        order = new Order();
        String[] orders = new String[0];
        boolean isConfirmed = false;
        while (!isConfirmed) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                String[] split = fullCommand.split(", ");
                System.out.println("You have ordered:");
                showOrderContent(split);
                System.out.println("Confirm [Y/N]?");
                isConfirmed = (ui.readCommand().toLowerCase()=="y")? true: false;
                orders = split;
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }

        String customerName = "";
        if (orderDate!="") { customerName = getCustomerName(ui); }

        printConfirmMsg(order, orderDate, customerName);

        //Add dishes into order
        for (String s : orders) {
            String[] Dish = s.split("\\*", 2);
            //order.addDish(Integer.parseInt(Dish[0]), Integer.parseInt(Dish[1]));
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
    } //category


    public void printStartMsg(Ui ui) {
        System.out.println("Hello! Welcome to restaurant SUPERDELIOUS!");
    }

    public void printMenu() {
        System.out.println("Start ordering...");
        System.out.println("Here is the menu. What would you like to order?");
        System.out.println("==============================================");
        System.out.println("            Today's Menu                      ");
        System.out.println("[1] Beef Noodle           $5.50\n" +
                "[2] Pork Dumplings        $4.50\n" +
                "[3] Chili Crab            $8.00\n" +
                "[4] Cereal Prawn          $6.50\n" +
                "[5] Laksa                 $4.50\n" +
                "[6] Chicken Rice          $4.00\n" +
                "[7] Seasonal Vegetables   $3.00");

        //To do
        //Print out menu info by iteration rather than manually printing out
        //rewritten this part after menu/dish part is done

        System.out.println("==============================================");
    }

    public void printConfirmMsg(Order order, String preOrderDate, String customerName) throws DukeException {
        if (preOrderDate=="") {
            System.out.println("Order received. Start preparing now...");
        } else {
            order.setDate(preOrderDate);
            System.out.println("Order received. Please come at "+preOrderDate+" on time."); }
    }

    public String getOrderDate(Ui ui) {
        System.out.println("Reservation or not [Y/N]?");
        boolean isPreOrder = (ui.readCommand().toLowerCase() == "y")? true:false;
        if (isPreOrder) {
            System.out.println("Please specify the serving date:");
            String date = ui.readCommand();
            return date;
        } else {return "";}

    }

    public String getCustomerName(Ui ui) {
        System.out.println("What name is the reservation under, sir/madam?");
        String name = ui.readCommand();
        return name;
    }

    public String getDishName(String dishNb) throws DukeException {
        try {
            switch (dishNb) {
                case "[1]": return "Beef Noodle";
                case "[2]": return "Pork Dumplings";
                case "[3]": return "Chili Crab";
                case "[4]": return "Cereal Prawn";
                case "[5]": return "Laksa";
                case "[6]": return "Chicken Rice";
                case "[7]": return "Seasonal Vegetables";
                default:
                    throw new DukeException("No corresponding dishes in today's menu!");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return "";
    }

    public double getDishPrice(String dishNb) throws DukeException {
        try {
            switch (dishNb) {
                case "[1]": return 5.5;
                case "[2]": return 4.5;
                case "[3]": return 8.0;
                case "[4]": return 6.5;
                case "[5]": return 4.5;
                case "[6]": return 4.0;
                case "[7]": return 3.0;
                default:
                    throw new DukeException("No corresponding dishes in today's menu!");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return 0;
    }

    public void showOrderContent(String[] commandList) throws DukeException {
        //commandList contains the dishes number and dishes amount
        int total = 0;
        int cnt = 0;
        for (String s: commandList) {
            String[] Dish = s.split("\\*", 2);
            String DishName = getDishName(Dish[0]);
            String DishAmount = Dish[1];
            cnt += Integer.parseInt(DishAmount);
            total += Integer.parseInt(DishAmount) * getDishPrice(Dish[0]);
            System.out.println(DishAmount+" "+DishName);
        }
        System.out.println("Total: "+cnt+" dishes, $"+total);
    }

}
