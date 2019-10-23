package duke.command.orderCommand;

import duke.command.Cmd;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.parser.Convert;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Date;

public class FindOrderByDate extends Cmd<Order> {

    private Date toView;

    public FindOrderByDate(String command) {
        //expected to be "date xxxx"
        String date = command.split(" ")[1];
        Date toView = Convert.stringToDate(date);
        this.toView = toView;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        try {
            for (Order order : orderList.getAllEntries()) {
                if ((order.getDate()).equals(toView)) {
                    sb.append("\t ").append(i++).append(".").append(order.toString());
                    sb.append(System.lineSeparator());
                }
            }
            if (sb.length() == 0) {
                System.out.println("No matching date found! ");
            } else {
                System.out.println("\t Here are the orders on "+ toView);
            }
            sb.setLength(sb.length() - 1); // to remove the last new line
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
