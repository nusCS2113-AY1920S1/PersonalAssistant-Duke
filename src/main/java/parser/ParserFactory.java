package parser;

public class ParserFactory {
    public Parse getParser(String userInput) {
        String command = userInput.split("\\s+", 2)[0].trim();

        switch(command){
            case "todo":

        }
        return null;
    }
}
