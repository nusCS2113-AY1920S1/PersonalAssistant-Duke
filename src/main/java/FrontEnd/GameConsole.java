package FrontEnd;

import Farmio.Farmer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 load new frame and new Farmio with delay
 */
public class GameConsole {
    private static final int LEVEL_SECTION_WIDTH = 10;
    private static final int DAY_SECTION_WIDTH = 8;
    private static final int LOCATION_SECTION_WIDTH = 20;
    private static final int FRAME_SECTION_HEIGHT = 18;
    private static final int FRAME_SECTION_WIDTH = 55;
    private static final int ASSET_SECTION_Y_POSITION_WRT_FRAME = 7;
    private static final int USER_CODE_SECTION_WIDTH = 31;
    private static final int FULL_CONSOLE_HEIGHT = 22;
    private static final int FULL_CONSOLE_WIDTH = 103;
    private static final int LEFT_COLUMN_SECTION_WIDTH = 15;
    private static String MARKED_GOAL_SYMBOL = "[X]";
    private static String UNMARKED_GOAL_SYMBOL = "[ ]";

    private static final String TOP_BORDER = "."+ "_".repeat(FULL_CONSOLE_WIDTH) +".\n";
    private static final String GOAL_AND_CODE_TITLE = "|"+"----" + AsciiColours.RED + "<GOALS>"
            + AsciiColours.SANE+ "----"+"|"+" ".repeat(FRAME_SECTION_WIDTH) +"|"+ "-".repeat(12)
            + AsciiColours.CYAN + "<CODE>" + AsciiColours.SANE + "-".repeat(13)+"|\n";
    private static final String BOX_BOTTOM_BORDER = "|" + "_".repeat(LEFT_COLUMN_SECTION_WIDTH)
            + "|" + "_".repeat(FRAME_SECTION_WIDTH) +"|" + "_".repeat(9)+"|"+"_".repeat(21)+"|\n";
    private static final String ASSETS_TITLE = "---" + AsciiColours.YELLOW + "<ASSETS>" + AsciiColours.SANE + "----";
    private static final String BOTTOM_BORDER = "|"+"_".repeat(LEFT_COLUMN_SECTION_WIDTH)+"|"
            + "_".repeat(FRAME_SECTION_WIDTH)+"|" + "_".repeat(USER_CODE_SECTION_WIDTH)+"|\n";
    private static final String MENU_TITLE = "-".repeat(8) + AsciiColours.HIGH_INTENSITY + "<MENU>"
            + AsciiColours.SANE +" for instruction list or settings" + "-".repeat(8);

    private static String horizontalPanel(String title, String content, int totalSpace) {
        return title + content + " ".repeat(totalSpace - title.length() - content.length());
    }

    static String content(ArrayList<String> stage, Farmer farmer, Map<String, Integer> Goals) {
        StringBuilder output = new StringBuilder();
        String location = farmer.getLocation();
        double level = farmer.getLevel();
        int day = farmer.getDay();
        ArrayList<String> userCode = farmer.getTasks().toStringArray();
        ArrayList<String> assets = formatAssets(farmer.getAssets(), Goals);
        ArrayList<String> goals;
        if (level == 1.1) {
            goals = tutorial1_1Goals(location);
        } else {
            goals = formatGoals(Goals, farmer.getAssets());
        }
        userCode = formatAndHighlightCode(userCode, farmer.getCurrentTask(), farmer.isHasfailedCurrentTask());
        output.append(AsciiColours.SANE).append(TOP_BORDER);
        output.append("|   " + AsciiColours.BLUE).append(horizontalPanel("Level: ", Double.toString(level), LEVEL_SECTION_WIDTH)).append(AsciiColours.SANE).append("  |");
        output.append(MENU_TITLE);
        output.append("|" + AsciiColours.MAGENTA).append(horizontalPanel("Day: ", Integer.toString(day), DAY_SECTION_WIDTH)).append(AsciiColours.SANE).append(" ");
        output.append("| " + AsciiColours.GREEN).append(horizontalPanel("Location: ", location, LOCATION_SECTION_WIDTH)).append(AsciiColours.SANE);
        output.append("|\n");
        output.append(BOX_BOTTOM_BORDER);
        output.append(GOAL_AND_CODE_TITLE);
        for (int i = 0; i < FRAME_SECTION_HEIGHT; i ++) {
            if (i < ASSET_SECTION_Y_POSITION_WRT_FRAME) {
                output.append("|").append(goals.get(i)).append(stage.get(i)).append(userCode.get(i)).append("\n");
            } else if (i == ASSET_SECTION_Y_POSITION_WRT_FRAME) {
                output.append("|").append(ASSETS_TITLE).append(stage.get(i)).append(userCode.get(i)).append("\n");
            } else {
                output.append("|").append(assets.get(i - ASSET_SECTION_Y_POSITION_WRT_FRAME - 1)).append(stage.get(i)).append(userCode.get(i)).append("\n");
            }
        }
        output.append(BOTTOM_BORDER);
        return output.toString() + AsciiColours.WHITE + AsciiColours.BACKGROUND_BLACK;
    }
    private static ArrayList<String> formatAndHighlightCode(ArrayList<String> userCode, int currentTask, boolean hasFailedCurrentTask) {
        ArrayList<String> userCodeOutput = new ArrayList<>();
        while (userCode.size() < FRAME_SECTION_HEIGHT){
            userCode.add("");
        }
        int i = 0;
        for (String s: userCode) {
            if (i == currentTask && !hasFailedCurrentTask) {
                userCodeOutput.add(AsciiColours.HIGHLIGHT+ horizontalPanel("", s, USER_CODE_SECTION_WIDTH) + AsciiColours.SANE + "|");
            } else if (i == currentTask){
                userCodeOutput.add(AsciiColours.ERROR + horizontalPanel("", s, USER_CODE_SECTION_WIDTH) + AsciiColours.SANE + "|");
            } else {
                userCodeOutput.add(horizontalPanel("", s, USER_CODE_SECTION_WIDTH) + "|");
            }
            i ++;
        }
        return userCodeOutput;
    }

    static String blankConsole(ArrayList<String> stage) {
        StringBuilder output = new StringBuilder();
        output.append(AsciiColours.SANE).append(TOP_BORDER);
        for (int i = 0; i < FULL_CONSOLE_HEIGHT; i ++) {
            output.append(stage.get(i)).append("\n");
        }
        return output.toString();
    }
    private static ArrayList<String> tutorial1_1Goals(String location) {
        ArrayList<String> goals = new ArrayList<>();
        if (location.equals("Market")) {
            goals.add(AsciiColours.DONE + "Location:Market" + AsciiColours.SANE);
        } else {
            goals.add(AsciiColours.HIGH_INTENSITY + "Location:Market" + AsciiColours.SANE);
        }
        while(goals.size() < ASSET_SECTION_Y_POSITION_WRT_FRAME) {
            goals.add(" ".repeat(LEFT_COLUMN_SECTION_WIDTH));
        }
        return goals;
    }
    private static ArrayList<String> formatGoals(Map<String, Integer> goals, Map<String, Integer> assets) {
        ArrayList<String> formattedGoals = new ArrayList<>();
        Set< Map.Entry< String,Integer> > goalSet = goals.entrySet();
        for (Map.Entry< String,Integer> goal:goalSet) {
            String s = goal.getKey() + ": " +  goal.getValue();
            if (goal.getKey().equals("Location") && goal.getValue().equals(assets.get(goal.getKey()))) {
                s = AsciiColours.HIGH_INTENSITY + "Location:Market" + AsciiColours.SANE;
                formattedGoals.add(s);
            } else if(goal.getKey().equals("Location")) {
                s = AsciiColours.DONE + "Location:Market" + AsciiColours.SANE;
                formattedGoals.add(s);
            } else if (assets.containsKey(goal.getKey()) && goal.getValue() <= assets.get(goal.getKey())
                    && goal.getValue() > 0) {
                formattedGoals.add(0, AsciiColours.DONE + s
                        + " ".repeat(LEFT_COLUMN_SECTION_WIDTH - s.length() - MARKED_GOAL_SYMBOL.length()) + MARKED_GOAL_SYMBOL + AsciiColours.SANE);
            } else if ((assets.containsKey(goal.getKey())&& goal.getValue() > 0)){
                formattedGoals.add(AsciiColours.HIGH_INTENSITY + s
                        + " ".repeat(LEFT_COLUMN_SECTION_WIDTH - s.length() - UNMARKED_GOAL_SYMBOL.length()) + UNMARKED_GOAL_SYMBOL + AsciiColours.SANE);
            }
        }
        while (formattedGoals.size() < ASSET_SECTION_Y_POSITION_WRT_FRAME) {
            formattedGoals.add(" ".repeat(LEFT_COLUMN_SECTION_WIDTH));
        }
        return formattedGoals;
    }

    private static ArrayList<String> formatAssets(Map<String, Integer> assets, Map<String, Integer> goals) {
        ArrayList<String> formattedAssets = new ArrayList<>();
        Set< Map.Entry< String,Integer> > assetSet = assets.entrySet();
        int border = 0;
        for (Map.Entry< String,Integer> asset:assetSet) {
            String s = asset.getKey() + ": " +  asset.getValue();
            String toAdd = s  + " ".repeat(LEFT_COLUMN_SECTION_WIDTH - s.length());
            if ((!goals.containsKey(asset.getKey()) || goals.get(asset.getKey()) <= 0)) {
                formattedAssets.add(toAdd);
            } else if (asset.getValue() <= goals.get(asset.getKey())) {
                formattedAssets.add(border, toAdd);
            } else {
                formattedAssets.add(0,toAdd);
                border ++;
            }
        }
        while (formattedAssets.size() < FRAME_SECTION_HEIGHT - ASSET_SECTION_Y_POSITION_WRT_FRAME) {
            formattedAssets.add(" ".repeat(LEFT_COLUMN_SECTION_WIDTH));
        }
        return formattedAssets;
    }
}