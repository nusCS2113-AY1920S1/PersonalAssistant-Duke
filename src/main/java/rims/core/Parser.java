package rims.core;

import rims.command.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class Parser {
    Reader reader;
    Ui ui;

    public Parser(Ui ui) {
        reader = new Reader();
        this.ui = ui;
    }

    public Command parseInput(String input) {
        Command c = new ListCommand(); // temporary default until exception handling finished
        String[] words = input.split(" ");

        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        }
        else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        }

        else if (words[0].equals("list") && words.length > 1) {
            String param = ui.getInput();
            String paramType = words[1].substring(1);
            if (paramType.equals("date") || paramType.equals("room") || paramType.equals("item")) {
                c = new ListCommand(paramType, param);
            }
            else {
                ;
                // throw new RimException (invalid paramType)
            }
        }
        else if (words[0].equals("lend") && words.length == 1) {
            c=reader.ReadLoanCommand(ui);        
        } 
        else if (words[0].equals("reserve") && words.length == 1) {
            c=reader.ReadReserveCommand(ui);

        }
        else if (words[0].equals("return")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String item = input.substring(itemIndex, qtyIndex);
                int idIndex = input.indexOf(" /id");
                int qty = Integer.parseInt(input.substring(qtyIndex + 6, idIndex));
                input = input.replaceFirst("return /item " + item + " /qty " + qty + " /id ", "");
                int id = Integer.parseInt(input);
                c = new ReturnCommand(item, qty, id);
            } else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                int idIndex = input.indexOf(" /id");
                String room = input.substring(roomIndex, idIndex);
                input = input.replaceFirst("return /room " + room + " /id ", "");
                int id = Integer.parseInt(input);
                c = new ReturnCommand(room, id);
            }

        }
        else if (words[0].equals("add")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String item = input.substring(itemIndex, qtyIndex);
                String qtyString = input.replaceFirst("add /item " + item + " /qty ", "");
                int qty = Integer.parseInt(qtyString);
                c = new AddCommand(item, qty);
            }
            else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                String room = input.substring(roomIndex);
                c = new AddCommand(room);
            }
            else {
                //throw new RimException
            }
        }
        else if (words[0].equals("delete")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String item = input.substring(itemIndex, qtyIndex);
                String qtyString = input.replaceFirst("delete /item " + item + " /qty ", "");
                int qty = Integer.parseInt(qtyString);
                c = new DeleteCommand(item, qty);
            }
            else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                String room = input.substring(roomIndex);
                c = new DeleteCommand(room);
            }
            else {
                //throw new RimException
            }
        }
        else {
            // throw an exception
            ;
        }
        return c;
    }
}