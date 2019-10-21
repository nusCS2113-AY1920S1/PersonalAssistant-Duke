package rims.core;

import rims.command.CloseCommand;
import rims.command.Command;
import rims.command.HomeCommand;
import rims.command.ListCommand;
import rims.command.ReturnCommand;

public class Parser {
    Reader reader;
    Ui ui;
    ResourceList resources;
    ReservationList reservations;

    public Parser(ResourceList resources, ReservationList reservations, Ui ui) {
        this.resources = resources;
        reader = new Reader(resources, reservations, ui);
        this.ui = ui;
    }

    public Command parseInput(String input) {
        Command c = new HomeCommand(); // temporary default until exception handling finished
        String[] words = input.split(" ");

        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        } else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        } else if (input.equals("test") && words.length == 1) {
            c = new HomeCommand();
        } else if (words[0].equals("list") && words.length > 1) {
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
        } else if ((words[0].equals("cancel") || words[0].equals("return")) && words.length == 1) {
            c = reader.ReadReturnCommand();
        } else if (words[0].equals("add")) {
            c = reader.ReadAddCommand();
        } else if (words[0].equals("delete")) {
            c = reader.ReadDeleteCommand();
        } else {
            // throw new RimException
        }
        return c;
    }
}