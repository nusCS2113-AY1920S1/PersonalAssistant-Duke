package FrontEnd;

import UserCode.Tasks.Task;
import UserCode.Tasks.TaskList;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameConsole {
    private static final String TOP_BORDER = "._______________________________________________________________________________________________________.\n";
    private static final String MENU_PROMPT_AND_CODE_TITLE = "|-----------------"+ AsciiColours.CYAN + "<MENU>" + AsciiColours.SANE + " for instruction list or settings---------------|---------"+ AsciiColours.CYAN + "<USER CODE>" + AsciiColours.SANE + "-----------|\n";
    private static final String BOX_BOTTOM_BORDER = "|_______________________________________________________________________|_______________________________|\n";
    private static final String BOX_TOP_BORDER = "|_______________________________________________________________________|_______________________________|\n"; //"|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|\n";
    private static final String CODE_TITLE_FILLER = "|                               |\n";
    private static final String CODE_BODY_FILLER = "                               |";
    private static final String EMPTY_STAGE_LINE = "                                                       ";
    private static final String LEFT_PANEL_BOTTOM_BORDER = "|_______________";
    private static final String LEFT_PANEL_TOP_BORDER = "|_______________";//"|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾";
    private static final String ASSETS_TITLE = "|---" + AsciiColours.CYAN + "<ASSETS>" + AsciiColours.SANE + "----";
    private static final String ASSETS_BODY_FILLER = "|               ";
    private static final String BOTTOM_BORDER = "|_______________|_______________________________________________________|_______________________________|\n";
    private static String horizontalPanel(String title, String content, int totalSpace) {
        String blankspace = "";
        for (int i = 0; i < totalSpace - title.length() - content.length(); i ++) {
            blankspace += " ";
        }
        return title + content + blankspace;
    }
    private static ArrayList<String> codePanel(TaskList tasks) {
        ArrayList<String> userCode = new ArrayList<String>();
        Integer taskCount = 0;
        for (Task t: tasks) {
            taskCount ++;
            userCode.add(horizontalPanel(taskCount.toString() + ". ", t.toString(), 31) + "|");
        }
        for (int i = 0; i < 18 - taskCount; i ++) {
            userCode.add(CODE_BODY_FILLER);
        }
        return userCode;
    }
//    private ArrayList<String> fillOptionStage() {
//        ArrayList<String> taskOptions;
//        ArrayList<String> conditionOptions;
//        ArrayList<String> actionOptions;
//    }
    private static ArrayList<String> fillDummyStage() {
        ArrayList<String> dummyStage = new ArrayList<>();
        for (int i = 0; i < 18; i ++) {
            dummyStage.add("|" + AsciiColours.BACKGROUND_WHITE + AsciiColours.BLACK + EMPTY_STAGE_LINE + AsciiColours.SANE + "|");
        }
        return dummyStage;
    }
    private static ArrayList<Pair<String, Integer>> fillDummyAsset() {
        ArrayList<Pair<String, Integer>> assets = new ArrayList<>();
        assets.add(new Pair<String, Integer>("Seeds", 0));
        assets.add(new Pair<String, Integer>("Wheat", 0));
        return assets;
    }
    public static String content(ArrayList<String> stage) { //does not include story
        StringBuilder output = new StringBuilder();
        String objective = "";
        String location = "WheatFarm"; //can change to enum
        int level = 1;
        int day = 1;
        int gold = 100;
        TaskList tasks = new TaskList();
        ArrayList<String> userCode = codePanel(tasks);
        ArrayList<Pair<String, Integer>> assets = fillDummyAsset();
//        ArrayList<String> stage = fillDummyStage(); //18 rows x 57 columns
        output.append(AsciiColours.SANE + TOP_BORDER);
        output.append("|" + AsciiColours.RED + horizontalPanel("OBJECTIVE:", objective, 71) + AsciiColours.SANE).append(CODE_TITLE_FILLER);
        output.append(BOX_BOTTOM_BORDER);
        output.append(MENU_PROMPT_AND_CODE_TITLE);
        output.append(BOX_TOP_BORDER);
        output.append("|" + AsciiColours.BLUE + horizontalPanel("Level: ", Integer.toString(level), 15) + AsciiColours.SANE).append(stage.get(0)).append(userCode.get(0)).append("\n");
        output.append("|" + AsciiColours.MAGENTA + horizontalPanel("Day:   ", Integer.toString(day), 15) + AsciiColours.SANE).append(stage.get(1)).append(userCode.get(1)).append("\n");
        output.append("|" + AsciiColours.YELLOW + horizontalPanel("Gold:", Integer.toString(gold), 15) + AsciiColours.SANE).append(stage.get(2)).append(userCode.get(2)).append("\n");
        output.append(LEFT_PANEL_TOP_BORDER).append(stage.get(3)).append(userCode.get(3)).append("\n");
        output.append(AsciiColours.GREEN + horizontalPanel("|@", location, 16)+ AsciiColours.SANE).append(stage.get(4)).append(userCode.get(4)).append("\n");
        output.append(LEFT_PANEL_BOTTOM_BORDER).append(stage.get(5)).append(userCode.get(5)).append("\n");
        output.append(ASSETS_TITLE).append(stage.get(6)).append(userCode.get(6)).append("\n");
        for (int i = 7; i < 18; i ++) {
            if (assets.size() > i - 7) {
                output.append(horizontalPanel("|" + assets.get(i - 7).getKey() + ": ", assets.get(i - 7).getValue().toString(), 16)).append(stage.get(i)).append(userCode.get(i)).append("\n");
            } else {
                output.append(ASSETS_BODY_FILLER).append(stage.get(i)).append(userCode.get(i)).append("\n");
            }
        }
        output.append(BOTTOM_BORDER);
        StringBuilder output2 = new StringBuilder();
        for (int i = 0; i < output.length(); i ++) {
            if (output.charAt(i) == '\n') {
                output2.append(AsciiColours.WHITE + AsciiColours.BACKGROUND_BLACK + "\n" + AsciiColours.SANE);
            } else {
                output2.append(output.charAt(i));
            }
        }
        return output2.toString() + AsciiColours.WHITE + AsciiColours.BACKGROUND_BLACK;
    }
}
