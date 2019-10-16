package duke.orderCommand;

import duke.exception.DukeException;
import duke.order.Order;
import duke.order.OrderList;
import duke.parser.Convert;
import duke.storage.Storage;
import duke.task.Dish;
import duke.ui.Ui;

import java.util.Date;

/**
 * operation of starting a new order
 */
public class StartNewOrder extends OrderCommand{

    @Override
    public void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException {

        printStartMsg(ui);
        String preOrderDate = PreOrderDate(ui);
        printMenu();

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
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }

        String customerName = "";
        if (preOrderDate!="") { customerName = getCustomerName(ui); }

        Order newOrder = new Order();
        printConfirmMsg(newOrder, preOrderDate, customerName);

        //To do...
        //Add customer name in order info (if it is a pre-order).
        //Will do as long as dish class is implemented

        orderList.addOrder(newOrder);

        //To do...
        //Add dishes one by one into the current order
    }

    public void printConfirmMsg(Order newOrder, String preOrderDate, String customerName) {
        if (preOrderDate=="") {
            System.out.println("Order received. Start preparing now...");
        } else {
            newOrder.setNewDate(preOrderDate);
            newOrder.setNewName(customerName);
            System.out.println("Order received. Please come at "+preOrderDate+" on time."); }
    }

    public String getCustomerName(Ui ui) {
        System.out.println("What name is the reservation under, sir/madam?");
        String name = ui.readCommand();
        return name;
    }

    public void printStartMsg(Ui ui) {
        System.out.println("Hello! This is restaurant SUPERDELIOUS! What can I help you?");
        String command = ui.readCommand();

        // Not finished this part yet
        switch (command){
            case "start ordering": return;
            default: return;
        }

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
        System.out.println("==============================================");
    }

    public String PreOrderDate(Ui ui) {
        System.out.println("Reservation or not [Y/N]?");
        boolean isPreOrder = (ui.readCommand().toLowerCase() == "y")? true:false;
        if (isPreOrder) {
            System.out.println("Please specify the serving date:");
            String date = ui.readCommand();
            return date;
        } else {return "";}

    }
    public void showOrderContent(String[] commandList) throws DukeException {
        //commandList contains the dishes number and dishes amount
        int total = 0;
        int cnt = 0;
        for (String s: commandList) {
            String[] command = s.split("\\*", 2);
            String DishName = getDishName(command[0]);
            String DishAmount = command[1];
            cnt += Integer.parseInt(DishAmount);
            total += Integer.parseInt(DishAmount) * getDishPrice(command[0]);
            System.out.println(DishAmount+" "+DishName);
        }
        System.out.println("Total: "+cnt+" dishes, $"+total);
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

}
