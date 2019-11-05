package duke.storage;

import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.parser.Convert;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;

public class OrderStorage extends Storage<Order> {
    /**
     * The constructor method for Storage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     */
    public OrderStorage(String fp) throws DukeException {
        super(fp);
        entries = load();
    }

    @Override
    public GenericList<Order> generate() throws DukeException {
        entries = new OrderList();
        for (String next : contentSoFar) {
            String[] words = next.split("\\|",3);
            if(words.length!=3) throw new DukeException("Error while reading from the order Storage");
            Date orderDate = Convert.stringToDate(words[1]);
            Order tmpOrder = new Order(orderDate);
            if (words[0].equals("1")) tmpOrder.markAsDone();
            String[] dishes = words[2].split("D\\|");
            for (String d: dishes) {
                if (d=="") continue;
                String[] nameAndAmount = d.split("\\|",3);
                if (nameAndAmount.length==1) continue;
                else {
                    try {
                        tmpOrder.addDish(nameAndAmount[0], Integer.parseInt(nameAndAmount[1]));
                    } catch (NumberFormatException e) {
                        e.getStackTrace();
                    }
                }
            }
            entries.addEntry(tmpOrder);
        }
        return entries;
    }

    @Override
    public GenericList<Order> getEntries() {
        return entries;
    }

}