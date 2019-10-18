package rims.core;

import rims.command.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class Parser {
    Reader reader = new Reader();
    private Ui ui; 

    public Parser (Ui ui){
        this.ui=ui;
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

        else if (words[0].equals("listByDate")) {
            Scanner myObj = new Scanner(System.in);
            String day = myObj.nextLine();
                c = new ListByDateCommand(day);
        }
        else if (words[0].equals("listByItem")) {
            Scanner myObj = new Scanner(System.in);
            String itemName = myObj.nextLine();
            c = new ListByItemCommand(itemName);
        }
        else if (words[0].equals("listByRoom")) {
            Scanner myObj = new Scanner(System.in);
            String roomName = myObj.nextLine();
            c = new ListByRoomCommand(roomName);
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