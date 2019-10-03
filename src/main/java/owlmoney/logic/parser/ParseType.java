package owlmoney.logic.parser;

import owlmoney.logic.command.OwlMoneyCommand;
import owlmoney.logic.command.PlaceHolderEmptyCommand;
import owlmoney.logic.command.bank.ListSavingsCommand;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.parser.expenditure.ParseAddExpenditure;
import owlmoney.logic.parser.expenditure.ParseDeleteExpenditure;
import owlmoney.logic.parser.expenditure.ParseListExpenditure;
import owlmoney.logic.parser.saving.ParseAddSaving;
import owlmoney.logic.parser.saving.ParseDeleteSaving;
import owlmoney.logic.parser.saving.ParseEditSaving;

class ParseType extends Parser {
    //added profile
    OwlMoneyCommand parseData(String command, String data) throws ParserException {
        String type = parseFirstField(data);
        String rawData;
        if(command.equals("/list")) {
            rawData = removeListFirstField(data, type);
        } else {
            rawData = removeFirstField(data, type);
        }
        return parseTypeMenu(command, type, rawData);
    }

    private void isDeleteProfile(String command) throws ParserException {
        if (command.equals("/delete")) {
            throw new ParserException("Profile cannot be deleted");
        }
    }
    //added profile to pass to command
    private OwlMoneyCommand parseTypeMenu(String command, String type, String rawData) throws ParserException {
        switch (type) {
        case "/profile":
            isDeleteProfile(command);
            System.out.println("You are at profile");
            System.out.println(rawData);
            return new PlaceHolderEmptyCommand();
        case "/savings":
            System.out.println("You are at savings");
            System.out.println(rawData);
            if ("/add".equals(command)) {
                ParseAddSaving parseAddSaving = new ParseAddSaving(rawData);
                parseAddSaving.fillHashTable();
                parseAddSaving.checkParameter();
                return parseAddSaving.getCommand(); //placeholder name to run the command
            } else if ("/edit".equals(command)) {
                ParseEditSaving parseEditSaving = new ParseEditSaving(rawData);
                parseEditSaving.fillHashTable();
                parseEditSaving.checkParameter();
                return new PlaceHolderEmptyCommand();
            } else if ("/delete".equals(command)) {
                ParseDeleteSaving parseDeleteSaving = new ParseDeleteSaving(rawData);
                parseDeleteSaving.fillHashTable();
                parseDeleteSaving.checkParameter();
                return parseDeleteSaving.getCommand();
            } else if ("/list".equals(command)) {
                return new ListSavingsCommand();
            }
            return new PlaceHolderEmptyCommand();
        case "/investment":
            System.out.println("You are at investment");
            System.out.println(rawData);
            return new PlaceHolderEmptyCommand();
        case "/expenditure":
            System.out.println("You are at expenditure");
            System.out.println(rawData);
            if ("/add".equals(command)) {
                ParseAddExpenditure addExp = new ParseAddExpenditure(rawData);
                addExp.fillHashTable();
                addExp.checkParameter();
                return addExp.getCommand();
            } else if ("/list".equals(command)) {
                ParseListExpenditure listExp = new ParseListExpenditure(rawData);
                listExp.fillHashTable();
                listExp.checkParameter();
                return listExp.getCommand();
            } else if ("/delete".equals(command)) {
                ParseDeleteExpenditure deleteExp = new ParseDeleteExpenditure(rawData);
                deleteExp.fillHashTable();
                deleteExp.checkParameter();
                return deleteExp.getCommand();
            }
            return new PlaceHolderEmptyCommand();
            /*
            String[] expenditureArguments = new String[] {"/amount", "/from", "/date", "/category", "/description"};
            ParseRawData anythingFirst = new ParseRawData();
            System.out.println(anythingFirst.extractParameter(rawData,"/amount", expenditureArguments));
            break;
            */
        case "/card":
            System.out.println("You are at card");
            System.out.println(rawData);
            return new PlaceHolderEmptyCommand();
        default:
            throw new ParserException("You entered an invalid type");
        }
    }
}
