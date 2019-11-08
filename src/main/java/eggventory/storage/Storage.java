package eggventory.storage;

import eggventory.commons.enums.CommandType;
import eggventory.logic.commands.Command;
import eggventory.logic.commands.add.AddStockTypeCommand;
import eggventory.logic.commands.add.AddPersonCommand;
import eggventory.logic.commands.add.AddTemplateCommand;
import eggventory.logic.commands.add.AddStockCommand;
import eggventory.logic.commands.add.AddLoanCommand;
import eggventory.logic.parsers.ParseAdd;
import eggventory.model.LoanList;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.TemplateList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

//@@author patwaririshab
/**
 * Handles reading and writing the stockList to file.
 */
public class Storage {
    private String stockFilePath;
    private String stockTypesFilePath;
    private String loanListFilePath;
    private String personListFilePath;
    private String templateListFilePath;

    /**
     * Initialises storage object.
     * @param stockFilePath path to saved_stocks.csv.
     * @param stockTypesFilePath path to saved_stocktypes.csv.
     * @param loanListFilePath  path to saved_loanist.csv.
     * @param personListFilePath path to saved_personlist.csv.
     * @param templateListFilePath path to saved_templatelist.txt.
     */
    public Storage(String stockFilePath, String stockTypesFilePath, String loanListFilePath,
                   String personListFilePath, String templateListFilePath) {
        this.stockFilePath = stockFilePath;
        this.stockTypesFilePath = stockTypesFilePath;
        this.loanListFilePath = loanListFilePath;
        this.personListFilePath = personListFilePath;
        this.templateListFilePath = templateListFilePath;
    }

    /**
     * Converts save file details into a StockList object.
     */
    public StockList load() {
        StockList savedList = new StockList();

        if (Files.notExists(Paths.get(stockFilePath)) || Files.notExists(Paths.get(stockTypesFilePath))
                || Files.notExists(Paths.get(loanListFilePath))) {
            try {
                Files.createDirectory(Paths.get("data/"));
            } catch (IOException e) {
                System.out.println("Unknown IO error when creating 'data/' folder.");
            }
        }
        File f = new File(stockTypesFilePath);
        File f2 = new File(stockFilePath);

        try {
            Scanner s = new Scanner(f); //Create a Scanner to read saved_stocktypes file.
            Scanner s2 = new Scanner(f2); //Create a Scanner to read the saved_stocks file.


            while (s.hasNext()) {
                String itemRaw = s.nextLine();
                AddStockTypeCommand loadStockTypes = new AddStockTypeCommand(CommandType.ADD, itemRaw);
                loadStockTypes.execute(savedList);
            }
            while (s2.hasNext()) {
                String itemRaw = s2.nextLine();
                String[] item = itemRaw.split(",", 0);

                AddStockCommand loadStocks = new AddStockCommand(CommandType.ADD, item[0], item[1],
                        Integer.parseInt(item[2]), item[3], Integer.parseInt(item[4]));

                loadStocks.execute(savedList);
            }


        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        } catch (Exception e) {
            System.out.println("Save file cannot be read. Please fix it manually or use a new list.");
        }
        return savedList; //Returns a StockList.
    }

    //@@author patwaririshab
    /**
     * Converts savefile into a LoanList object.
     */
    public PersonList loadPersonList() {
        PersonList savedPersonList = new PersonList();
        File f4 = new File(personListFilePath);
        try {
            Scanner s4 = new Scanner(f4); //Create a Scanner using LoanListFilePath as source
            while (s4.hasNext()) {
                String itemRaw = s4.nextLine();
                String[] item = itemRaw.split(",", 0);
                AddPersonCommand addPersons = new AddPersonCommand(CommandType.ADD, item[0], item[1]);
                addPersons.executeLoadPersonList(savedPersonList);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        } catch (Exception e) {
            System.out.println("Save file cannot be read. Please fix it manually or use a new list.");
        }
        return savedPersonList;
    }

    /**
     * Converts savefile into a LoanList object.
     */
    public LoanList loadLoanList() {
        LoanList savedLoanList = new LoanList();
        File f5 = new File(loanListFilePath);
        try {
            Scanner s5 = new Scanner(f5); //Create a Scanner using LoanListFilePath as source
            while (s5.hasNext()) {
                String itemRaw = s5.nextLine();
                String[] item = itemRaw.split(",", 0);
                AddLoanCommand addLoans = new AddLoanCommand(CommandType.ADD, item[0], item[1],
                        Integer.parseInt(item[2]));
                addLoans.executeLoadLoanList(savedLoanList);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        } catch (Exception e) {
            System.out.println("Save file cannot be read. Please fix it manually or use a new list.");
        }
        return savedLoanList;
    }

    /**
     * Converts savefile into a TemplateList object.
     */
    public TemplateList loadTemplateList() {
        TemplateList savedTemplateList = new TemplateList();
        File f3 = new File(templateListFilePath);
        try {
            Scanner s3 = new Scanner(f3); //Create a Scanner using LoanListFilePath as source
            while (s3.hasNext()) {
                String itemRaw = s3.nextLine();
                AddTemplateCommand addTemplate = ((AddTemplateCommand) new ParseAdd().processAddTemplate(itemRaw));
                addTemplate.executeSaveTemplateList(savedTemplateList);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        } catch (Exception e) {
            System.out.println("Save file cannot be read. Please fix it manually or use a new list.");
        }
        return savedTemplateList;
    }


    private void writeToFile(String textToAdd, String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.print(textToAdd);
        pw.flush();
        pw.close();
    }

    //@@author cyanoei
    /**
     * Saves existing StockType to a text file.
     */
    public void save(StockList stockList) {
        String stocksToSave = stockList.saveDetailsString();
        String stockTypesToSave = stockList.saveStockTypesString();

        try {
            writeToFile(stocksToSave, stockFilePath);
            writeToFile(stockTypesToSave, stockTypesFilePath);
        } catch (IOException e) {
            System.out.println("Something went wrong saving the file :(");
        }
    }

    /**
     * Saves existing StockList, StockType, LoanList, PersonList into a text file.
     */
    public void save(StockList stockList, LoanList loanList, PersonList personList, TemplateList templateList) {
        String stocksToSave = stockList.saveDetailsString();
        String stockTypesToSave = stockList.saveStockTypesString();
        String loanListToSave = loanList.saveLoanListString();
        String personListToSave = personList.savePersonListString();
        String templateListToSave = templateList.saveTemplateListString();

        try {
            writeToFile(stocksToSave, stockFilePath);
            writeToFile(stockTypesToSave, stockTypesFilePath);
            writeToFile(loanListToSave, loanListFilePath);
            writeToFile(personListToSave, personListFilePath);
            writeToFile(templateListToSave, templateListFilePath);
        } catch (IOException e) {
            System.out.println("Something went wrong saving the file :(");
        }
    }
    //@@author
}
