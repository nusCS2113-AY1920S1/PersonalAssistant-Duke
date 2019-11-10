package moomoo.task;

public class Cow {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BLACK = "\u001B[30m";
    final String fullBlock = "\u2588" + "\u2588";
    final String lineOne = "        ";
    final String lineTwo = "      ";
    final String lineThree = "    ";
    final String lineFour = "  ";
    private String happyCow = "";
    private String sadCow = "";
    
    String generateHappyCow() {
        String model = lineOne + ANSI_BLUE; //line 1
        for (int i = 0; i < 27; i += 1) {
            model += fullBlock;
        }
        
        
        model += "\n" + lineTwo; // line 2
        for (int i = 0; i < 29; i += 1) {
            model += fullBlock;
        }
        
        
        model += "\n" + lineThree; //line 3
        for (int i = 0; i < 13; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLUE + fullBlock + fullBlock;
        model += ANSI_BLACK;
        for (int i = 0; i < 3; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLUE + fullBlock + fullBlock;
        model += ANSI_BLACK;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLUE;
        for (int i = 0; i < 3; i += 1) {
            model += fullBlock;
        }
        
        
        model += "\n" + lineFour; //line 4
        for (int i = 0; i < 14; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 3; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 3; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLUE;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
        
        
        model += "\n"; //line 5
        for (int i = 0; i < 9; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK;
        for (int i = 0; i < 10; i += 1) {
            model += fullBlock;
        }
        model += ANSI_WHITE;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLUE;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        
        
        model += "\n"; //line 6
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_WHITE;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 7; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
        
    
        model += "\n"; //line 7
        for (int i = 0; i < 7; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
        
        
        model += "\n"; //line 8
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 9; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
    
        model += "\n"; //line 9
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK;
        for (int i = 0; i < 3; i += 1) {
            model += fullBlock;
        }
        model += ANSI_WHITE;
        for (int i = 0; i < 2; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_YELLOW;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
    
    
        model += "\n"; //line 10
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 3; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK;
        for (int i = 0; i < 5; i += 1) {
            model += fullBlock;
        }
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_YELLOW + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_YELLOW + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_YELLOW + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 7; i += 1) {
            model += fullBlock;
        }
    
    
        model += "\n"; //line 11
        for (int i = 0; i < 5; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_YELLOW;
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 7; i += 1) {
            model += fullBlock;
        }
    
    
        model += "\n"; //line 12
        for (int i = 0; i < 5; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE;
        for (int i = 0; i < 10; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_YELLOW;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
        
        
        model += "\n"; //line 13
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock;
        model += ANSI_YELLOW + fullBlock + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock;
        model += ANSI_WHITE + fullBlock + fullBlock + fullBlock;
        model += ANSI_BLACK;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLUE;
        for (int i = 0; i < 9; i += 1) {
            model += fullBlock;
        }
    
    
        model += "\n"; //line 14
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_YELLOW + fullBlock + fullBlock + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK;
        for (int i = 0; i < 3; i += 1) {
            model += fullBlock;
        }
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 12; i += 1) {
            model += fullBlock;
        }
        
    
        model += "\n"; //line 15
        for (int i = 0; i < 2; i += 1) {
            model += fullBlock;
        }
        model += ANSI_RED + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 5; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_WHITE + fullBlock;
        model += ANSI_BLACK + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
        model += ANSI_RED + fullBlock;
        model += ANSI_BLUE;
        for (int i = 0; i < 3; i += 1) {
            model += fullBlock;
        }
        
        
        model += "\n" + lineFour; //line 16
        model += ANSI_GREEN;
        for (int i = 0; i < 5; i += 1) {
            model += fullBlock;
        }
        model += ANSI_RED + fullBlock;
        model += ANSI_GREEN + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock + fullBlock;
        model += ANSI_GREEN + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock + fullBlock;
        model += ANSI_GREEN + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock + fullBlock;
        model += ANSI_GREEN + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock + fullBlock;
        model += ANSI_GREEN;
        for (int i = 0; i < 11; i += 1) {
            model += fullBlock;
        }
    
    
        model += "\n" + lineThree; //line 17
        model += ANSI_GREEN;
        for (int i = 0; i < 6; i += 1) {
            model += fullBlock;
        }
        model += ANSI_BLACK + fullBlock + fullBlock + fullBlock;
        model += ANSI_GREEN + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock + fullBlock;
        model += ANSI_GREEN + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock + fullBlock;
        model += ANSI_GREEN + fullBlock;
        model += ANSI_BLACK + fullBlock + fullBlock + fullBlock;
        model += ANSI_GREEN;
        for (int i = 0; i < 5; i += 1) {
            model += fullBlock;
        }
        model += ANSI_RED + fullBlock;
        model += ANSI_GREEN;
        for (int i = 0; i < 4; i += 1) {
            model += fullBlock;
        }
    
    
        model += "\n" + lineTwo; //line 18
        model += ANSI_GREEN + fullBlock;
        model += ANSI_RED + fullBlock;
        model += ANSI_GREEN;
        for (int i = 0; i < 8; i += 1) {
            model += fullBlock;
        }
        model += ANSI_RED + fullBlock;
        model += ANSI_GREEN;
        for (int i = 0; i < 10; i += 1) {
            model += fullBlock;
        }
        model += ANSI_RED + fullBlock;
        model += ANSI_GREEN;
        for (int i = 0; i < 7; i += 1) {
            model += fullBlock;
        }
        model += ANSI_RESET;
        
        return model;
    }
    
    public Cow() {
        happyCow = generateHappyCow();
    }
    
    public String getHappyCow() {
        return happyCow;
    }
}
