package rims.core;

import rims.command.*;
import rims.exception.*;
import rims.resource.*;


import java.util.*;
import java.io.*;
import java.text.*;

public class Parser {
    public Command parseInput(String input) {
        Command c = new ListCommand(); // temporary default until exception handling finished
        String[] words = input.split(" ");
        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        }
        else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        }
        else if (words[0].equals("lend")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String item = input.substring(itemIndex, qtyIndex);
                int idIndex = input.indexOf(" /id");
                int qty = Integer.parseInt(input.substring(qtyIndex + 6, idIndex));
                int byIndex = input.indexOf(" /by");
                int id = Integer.parseInt(input.substring(idIndex + 5, byIndex));
                input = input.replaceFirst("lend /item " + item + " /qty " + qty + " /id " + id + " /by ", "");
                c = new LoanCommand(item, qty, id, input);
            }
            else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                int idIndex = input.indexOf(" /id");
                String room = input.substring(roomIndex, idIndex);
                int byIndex = input.indexOf(" /by");
                int id = Integer.parseInt(input.substring(idIndex + 5, byIndex));
                input = input.replaceFirst("lend /room " + room + " /id " + id + " /by ", "");
                c = new LoanCommand(room, id, input);
            }
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
            }
            else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                int idIndex = input.indexOf(" /id");
                String room = input.substring(roomIndex, idIndex);
                input = input.replaceFirst("return /room " + room + " /id ", "");
                int id = Integer.parseInt(input);
                c = new ReturnCommand(room, id);
            }
        }
        else {
            // throw an exception
            ;
        }
        return c;
    }
}