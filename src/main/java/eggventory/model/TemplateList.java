package eggventory.model;

import eggventory.model.loans.Loan;
import eggventory.ui.TableStruct;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//@@author Deculsion
public class TemplateList {
    private static HashMap<String, Loan[]> templates = new HashMap<>();

    /**
     * Adds a specified template.
     * @param name Name of the template.
     * @param loans Array of Loan objects to be associated with the template.
     * @return Boolean representing whether the operation was a success.
     */
    public static boolean addTemplate(String name, Loan[] loans) {
        if (templateExists(name)) {
            return false;
        }

        templates.put(name, loans);
        return true;
    }

    /**
     * Deletes a specified template.
     * @param name Name of the template.
     * @return Boolean representing whether the operation was a success.
     */
    public static boolean deleteTemplate(String name) {
        if (!templateExists(name)) {
            return false;
        }

        templates.remove(name);

        return true;
    }

    /**
     * Returns all the loans of a template.
     * @param name Name of template.
     * @return An array of Loan objects of the template. Null if it doesn't exist.
     */
    public static Loan[] getTemplateLoans(String name) {
        if (!templateExists(name)) {
            return null;
        }
        return templates.get(name);
    }

    /**
     * Returns whether the template of a given name exists.
     * @param name Name of template to check for.
     */
    public static boolean templateExists(String name) {
        if (templates.containsKey(name)) {
            return true;
        }

        return false;
    }

    /**
     * Returns whether there are any templates stored.
     */
    public static boolean isEmpty() {
        return templates.isEmpty();
    }


    private static ArrayList<String> getNamesSorted() {
        ArrayList<String> names = new ArrayList<>();
        for (String name : templates.keySet()) {
            names.add(name);
        }

        Collections.sort(names);

        return names;
    }

    /**
     * Returns a string of all templates currently stored in alphabetical order.
     * @return A string representation of the templates.
     */
    public static String printTemplateNames() {
        ArrayList<String> names;
        StringBuilder sb = new StringBuilder();

        names = getNamesSorted();

        sb.append("Here are the names of all the templates you have");

        for (String name: names) {
            sb.append(name);
            sb.append("\n");
        }

        return sb.toString();
    }



    /**
     * Prints all loans of a template.
     * @param name Name of template to print.
     * @return A string of all the loans within the template.
     */
    public static String printTemplateLoans(String name) {
        if (!templateExists(name)) {
            return "The template does not exist!";
        }
        Loan[] loans = templates.get(name);
        StringBuilder sb = new StringBuilder();

        for (Loan loan: loans) {
            sb.append("StockCode: ").append(loan.getStockCode());
            sb.append(", Quantity: ").append(loan.getQuantity());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Gets the loans of a template as a TableStruct.
     * @param name Name of template.
     */
    public static TableStruct getTemplateLoanStruct(String name) {
        TableStruct dataTable = new TableStruct("Loans of template:" + name);
        dataTable.setTableColumns("Stock Code", "Quantity");

        ArrayList<ArrayList<String>> dataArr = new ArrayList<>();
        Loan[] loanArr = getTemplateLoans(name);

        for (Loan loan: loanArr) {
            dataArr.add(loan.getStockDataAsArray());
        }

        dataTable.setTableData(dataArr);

        return dataTable;
    }

    /**
     * Gets every template in a loan as a TableStruct.
     */
    public static TableStruct getAllTemplateLoanStruct() {
        TableStruct dataTable = new TableStruct("Template List");
        dataTable.setTableColumns("Name", "Stock Code", "Quantity");

        ArrayList<ArrayList<String>> dataArr = new ArrayList<>();

        for (String name : templates.keySet()) {
            ArrayList<String> template = getLoansArrayCompact(name);
            dataArr.add(template);
        }

        dataTable.setTableData(dataArr);

        return dataTable;
    }

    private static ArrayList<String> getLoansArrayCompact(String name) {
        ArrayList<String> template = new ArrayList<>();
        Loan[] loans = getTemplateLoans(name);

        StringBuilder stockcodeSB = new StringBuilder();
        StringBuilder quantitySB = new StringBuilder();

        for (Loan loan : loans) {
            stockcodeSB.append(loan.getStockCode()).append("\n");
            quantitySB.append(loan.getQuantity()).append("\n");
        }

        template.add(name);
        template.add(stockcodeSB.toString());
        template.add(quantitySB.toString());

        return template;
    }

    //@@author patwaririshab
    /**
     * Creates a string for saving templates to persistent storage.
     */
    public String saveTemplateListString() {
        StringBuilder saveTemplateListString = new StringBuilder();

        for (String name : templates.keySet()) {
            Loan[] loans = templates.get(name);
            StringBuilder sb = new StringBuilder();
            sb.append(name).append(" ");
            for (Loan loan: loans) {
                sb.append(" ").append(loan.getStockCode());
                sb.append(" ").append(loan.getQuantity());
            }
            saveTemplateListString.append(sb.toString()).append("\n");
        }
        return saveTemplateListString.toString();
    }
    //@@ author
}
