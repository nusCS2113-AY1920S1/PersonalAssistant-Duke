package owlmoney.logic.parser;

import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.parser.saving.ParseAddSaving;
import owlmoney.logic.parser.saving.ParseDeleteSaving;
import owlmoney.logic.parser.saving.ParseEditSaving;
import owlmoney.model.profile.Profile;

class ParseType extends Parser {
    //added profile
    void parseData(String command, String data, Profile profile) throws ParserException {
        String type = parseFirstField(data);
        String rawData = removeFirstField(data, type);
        parseTypeMenu(command, type, rawData, profile);
    }

    private void isDeleteProfile(String command) throws ParserException {
        if (command.equals("/delete")) {
            throw new ParserException("Profile cannot be deleted");
        }
    }
    //added profile to pass to command
    private void parseTypeMenu(String command, String type, String rawData, Profile profile) throws ParserException {
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
                parseAddSaving.passToCommand(profile); //placeholder name to run the command
            } else if ("/edit".equals(command)) {
                ParseEditSaving parseEditSaving = new ParseEditSaving(rawData);
                parseEditSaving.fillHashTable();
                parseEditSaving.checkParameter();
            } else if ("/delete".equals(command)) {
                ParseDeleteSaving parseDeleteSaving = new ParseDeleteSaving(rawData);
                parseDeleteSaving.fillHashTable();
                parseDeleteSaving.checkParameter();
                parseDeleteSaving.passToCommand(profile);
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
