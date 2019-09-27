package owlmoney.logic.parser;

import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.parser.saving.ParseAddSaving;
import owlmoney.logic.parser.saving.ParseEditSaving;

class ParseType extends Parser {
    void parseData(String command, String data) throws ParserException {
        String type = parseFirstField(data);
        String rawData = removeFirstField(data, type);
        parseTypeMenu(command, type, rawData);
    }

    private void isDeleteProfile(String command) throws ParserException {
        if (command.equals("/delete")) {
            throw new ParserException("Profile cannot be deleted");
        }
    }

    private void parseTypeMenu(String command, String type, String rawData) throws ParserException {
        switch (type) {
        case "/profile":
            isDeleteProfile(command);
            System.out.println("You are at profile");
            System.out.println(rawData);
            break;
        case "/savings":
            System.out.println("You are at savings");
            System.out.println(rawData);
            if ("/add".equals(command)) {
                ParseAddSaving parseAddSaving = new ParseAddSaving(rawData);
                parseAddSaving.fillHashTable();
                parseAddSaving.checkParameter();
            } else if ("/edit".equals(command)) {
                ParseEditSaving parseEditSaving = new ParseEditSaving(rawData);
                parseEditSaving.fillHashTable();
                parseEditSaving.checkParameter();
            }

            break;
        case "/investment":
            System.out.println("You are at investment");
            System.out.println(rawData);
            break;
        case "/expenditure":
            System.out.println("You are at expenditure");
            System.out.println(rawData);

            String[] expenditureArguments = new String[] {"/amount", "/from", "/date", "/category", "/description"};
            ParseRawData anythingFirst = new ParseRawData();
            System.out.println(anythingFirst.extractParameter(rawData,"/amount", expenditureArguments));
            break;
        case "/card":
            System.out.println("You are at card");
            System.out.println(rawData);
            break;
        default:
            System.out.println("You entered an invalid type");
            break;
        }
    }
}
