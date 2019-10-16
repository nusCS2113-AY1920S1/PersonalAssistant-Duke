package duke.orderCommand;

import duke.exception.DukeException;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.task.Dish;
import duke.ui.Ui;

/**
 *
 */
public class StartNewOrder extends OrderCommand{

    @Override
    public void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException {
        printStartMsg();
        boolean isConfirmed = false;
        while (!isConfirmed) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                String[] splitted = fullCommand.split(", ");
                System.out.println("You have ordered:");
                showOrderContent(splitted);
                System.out.println("Confirm [Y/N]?");
                isConfirmed = (ui.readCommand().toLowerCase()=="y")? true: false;
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
        printConfirmMsg();
    }

    public void printStartMsg() {
        System.out.println("Start ordering...");
        System.out.println("Here is the menu. What would you like to eat?");
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

    public void printConfirmMsg() {
        System.out.println("Order received. Start preparing now...");
    }

    public void showOrderContent(String[] commandList) throws DukeException {
        //commandList contains the dishes number and dishes amount
        int total = 0;
        int cnt = 0;
        for (String s: commandList) {
            cnt++;
            String[] command = s.split("\\*", 2);
            String DishName = getDishName(command[0]);
            String DishAmount = command[1];
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
