package rims.core;

import rims.command.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class Reader implements Read {
    private String item;
    private int qty;
    private int id;
    private String startDate;
    private String endDate;

    public Command ReadLoanCommand(Ui ui){
        ui.formattedPrint("Would you like to borrow an item or book a room");
        //input validation
        String choice=ui.getInput();

        if(choice.equals("room")){
            ui.formattedPrint("Enter Room name");
            item=ui.getInput();
            //check for item existence
            //return qty here to show user the quantity available
    
            //check for ID validity as an integer (for now)
            ui.formattedPrint("Enter your ID as a borrower");
            String tempId=ui.getInput();
            try{
                id=Integer.parseInt(tempId);
            }catch (NumberFormatException ex){
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }
            
            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate=ui.getInput();      
            
            Command c = new LoanCommand(item, id, endDate);
            return c;    
        }

        else if(choice.equals("item")){
            ui.formattedPrint("Enter Item name (use cancel to go back)");
            item=ui.getInput();
            //check for item existence
            //return qty here to show user the quantity available
    
            ui.formattedPrint("Enter quantity you wish to borrow");
            String tempQty=ui.getInput();

            //check for valid Integer input
            //Needs to be done: check for sufficient quantity
            try{
                qty=Integer.parseInt(tempQty);
            }catch (NumberFormatException ex){
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }
            //check if quantity is valid

            //check for ID validity as an integer (for now)
            ui.formattedPrint("Enter your ID as a borrower");
            String tempId=ui.getInput();
            try{
                id=Integer.parseInt(tempId);
            }catch (NumberFormatException ex){
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }

            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate=ui.getInput();

            Command c = new LoanCommand(item, qty, id, endDate);
            return c;  
        }
        else{
            Command c = new HomeCommand();
            return c;
        } 
    }

    public Command ReadReserveCommand(Ui ui){
        ui.formattedPrint("Would you like to reserve an item or book a room");
        //input validation
        String choice=ui.getInput();

        if(choice.equals("room")){
            ui.formattedPrint("Enter Room name");
            item=ui.getInput();
            //check for item existence
            //return qty here to show user the quantity available
    
            //check for ID validity as an integer (for now)
            ui.formattedPrint("Enter your ID as a borrower");
            String tempId=ui.getInput();
            try{
                id=Integer.parseInt(tempId);
            }catch (NumberFormatException ex){
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }

            ui.formattedPrint("Enter the date you wish to start using the resource in the following format: DD/MM/YYYY HHMM");
            startDate=ui.getInput();     

            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate=ui.getInput();      
            
            Command c = new ReserveCommand(item, id, startDate, endDate);
            return c;    
        }

        else if(choice.equals("item")){
            ui.formattedPrint("Enter Item name (use cancel to go back)");
            item=ui.getInput();
            //check for item existence
            //return qty here to show user the quantity available
    
            ui.formattedPrint("Enter quantity you wish to borrow");
            String tempQty=ui.getInput();

            //check for valid Integer input
            //Needs to be done: check for sufficient quantity
            try{
                qty=Integer.parseInt(tempQty);
            }catch (NumberFormatException ex){
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }
            //check if quantity is valid

            //check for ID validity as an integer (for now)
            ui.formattedPrint("Enter your ID as a borrower");
            String tempId=ui.getInput();
            try{
                id=Integer.parseInt(tempId);
            }catch (NumberFormatException ex){
                ui.ErrorPrint("Error encountered: Please enter an integer. We will redirect you to the home page");
                Command c = new HomeCommand();
                return c;
            }

            ui.formattedPrint("Enter the date you wish to start using the resource in the following format: DD/MM/YYYY HHMM");
            startDate=ui.getInput();     

            ui.formattedPrint("Enter the date you wish to return by in the following format: DD/MM/YYYY HHMM");
            endDate=ui.getInput();    

            Command c = new ReserveCommand(item, id, startDate, endDate);
            return c;  
        }
        else{
            Command c = new HomeCommand();
            return c;
        } 
    }
}