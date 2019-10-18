package rims.core;

import rims.command.AddCommand;
import rims.command.CloseCommand;
import rims.command.Command;
import rims.command.DeleteCommand;
import rims.command.ListCommand;

public class Parser {
    Reader reader;
    Ui ui;
    ResourceList resources;

    public Parser(ResourceList resources, Ui ui) {
        this.resources = resources;
        reader = new Reader(resources, ui);
        this.ui = ui;
    }

    public Command parseInput(String input) {
        Command c = new ListCommand(); // temporary default until exception handling finished
        String[] words = input.split(" ");

        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        } else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        }

        else if (words[0].equals("list") && words.length > 1) {
            String param = ui.getInput();
            String paramType = words[1].substring(1);
            if (paramType.equals("date") || paramType.equals("room") || paramType.equals("item")) {
                c = new ListCommand(paramType, param);
            } else {
                ;
                // throw new RimException (invalid paramType)
            }
        } else if (words[0].equals("lend") && words.length == 1) {
            c = reader.ReadLoanCommand();
        } else if (words[0].equals("reserve") && words.length == 1) {
            c = reader.ReadReserveCommand();

        } else if (words[0].equals("return") && words.length == 1) {
            // ui.formattedPrint("1");
            // if(!resources.isEmpty()){
            //     ui.printArray(resources.generateBookedList(2));
            // }
            c = reader.ReadReturnCommand();
        } else if (words[0].equals("add")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String item = input.substring(itemIndex, qtyIndex);
                String qtyString = input.replaceFirst("add /item " + item + " /qty ", "");
                int qty = Integer.parseInt(qtyString);
                c = new AddCommand(item, qty);
            } else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                String room = input.substring(roomIndex);
                c = new AddCommand(room);
            } else {
                // throw new RimException
            }
        } else if (words[0].equals("delete")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String item = input.substring(itemIndex, qtyIndex);
                String qtyString = input.replaceFirst("delete /item " + item + " /qty ", "");
                int qty = Integer.parseInt(qtyString);
                c = new DeleteCommand(item, qty);
            } else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                String room = input.substring(roomIndex);
                c = new DeleteCommand(room);
            } else {
                // throw new RimException
            }
        } else {
            // throw an exception
            ;
        }
        return c;
    }
}