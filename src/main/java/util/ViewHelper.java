package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static util.constant.ConstantHelper.HORI_BORDER_UNIT;
import static util.constant.ConstantHelper.BORDER_CORNER;
import static util.constant.ConstantHelper.VERTI_BORDER_UNIT;

public class ViewHelper {

    /**
     * Method to print a table containing multiple tables within itself.
     * @param toPrintAll ArrayList with each element being an ArrayList containing details for a table.
     * @param tableWidth Width of the table.
     * @param numOfTableColumns Number of columns of tables.
     * @param overallTitle Header for the overall table.
     * @return String array containing the entire table content to be printed.
     */
    public String[] consolePrintMultipleTables(ArrayList<ArrayList<String>> toPrintAll, int tableWidth,
                                               int numOfTableColumns, String overallTitle) {

        ArrayList<ArrayList<String>> columnsOfTableContent = new ArrayList<>();
        for (int i = 0; i < numOfTableColumns; i++) {
            columnsOfTableContent.add(new ArrayList<>());
        }
        // Each element in columnsOfTableContent is an ArrayList containing content for that column of tables
        int individualTableWidth = ((tableWidth - (numOfTableColumns + 1)) / numOfTableColumns) - 2;
        while (toPrintAll.size() != 0) {
            for (int i = 0; i < numOfTableColumns; i++) {
                if (toPrintAll.size() == 0) {
                    break;
                }
                ArrayList<ArrayList<String>> tableToBePrinted
                        = new ArrayList<>(Collections.singleton(toPrintAll.get(0)));
                String[] individualTableContent = consolePrintTable(tableToBePrinted, individualTableWidth);
                int columnToBeAdded = findColumnWithMinNumOfRows(columnsOfTableContent);
                columnsOfTableContent.get(columnToBeAdded).addAll(Arrays.asList(individualTableContent));
                toPrintAll.remove(0);
            }
        }
        int maxNumOfRows = findMaxNumOfRows(columnsOfTableContent);

        // can possibly refactor into new method addRemainingSpacesToShorterColumns()
        for (int i = 0; i < numOfTableColumns; i++) {
            while (columnsOfTableContent.get(i).size() != maxNumOfRows) {
                columnsOfTableContent.get(i).add(getRemainingSpaces(individualTableWidth + 2));
            }
        }

        ArrayList<String> overallTableContent = new ArrayList<>();
        overallTableContent.add(overallTitle);
        for (int i = 0; i < maxNumOfRows; i++) {
            StringBuilder line = new StringBuilder();
            for (ArrayList<String> strings : columnsOfTableContent) {
                line.append(" ");
                line.append(strings.get(i));
            }
            line.append(" ");
            overallTableContent.add(line.toString());
        }
        ArrayList<ArrayList<String>> overallTableContentDummy = new ArrayList<>();
        overallTableContentDummy.add(overallTableContent);
        return consolePrintTable(overallTableContentDummy, tableWidth);
    }

    private int findMaxNumOfRows(ArrayList<ArrayList<String>> columnsOfTableContent) {
        int maxNumOfRows = 0;
        for (ArrayList<String> strings : columnsOfTableContent) {
            if (strings.size() > maxNumOfRows) {
                maxNumOfRows = strings.size();
            }
        }
        return maxNumOfRows;
    }

    private int findColumnWithMinNumOfRows(ArrayList<ArrayList<String>> columnsOfTableContent) {
        int minNumOfRows = Integer.MAX_VALUE;
        int indexOfColumnWithMinNumOfRows = -1;
        for (int i = 0; i < columnsOfTableContent.size(); i++) {
            if (columnsOfTableContent.get(i).size() < minNumOfRows) {
                minNumOfRows = columnsOfTableContent.get(i).size();
                indexOfColumnWithMinNumOfRows = i;
            }
        }
        return indexOfColumnWithMinNumOfRows;
    }

    /**
     * Returns a String array that contains input in table form.
     * @param toPrintAll ArrayList with each element fitting into one table, and each element consists of an
     *                   ArrayList of Strings containing the lines to be printed in a table
     * @param tableWidth Desired width of the table.
     * @return A String array that contains input in table form.
     */
    public String[] consolePrintTable(ArrayList<ArrayList<String>> toPrintAll, int tableWidth) {
        ArrayList<String> tableContent = new ArrayList<>();
        for (ArrayList<String> toPrint : toPrintAll) {
            tableContent.add(consolePrintTableHoriBorder(tableWidth));
            boolean hasPrintedTableHeader = false;
            for (String s : toPrint) {
                if (s.length() <= tableWidth) {
                    String line = VERTI_BORDER_UNIT + s
                            + getRemainingSpaces(tableWidth - s.length())
                            + VERTI_BORDER_UNIT;
                    tableContent.add(line);
                } else {
                    String[] splitStrings = getArrayOfSplitStrings(s, tableWidth);
                    for (String s1 : splitStrings) {
                        String line = VERTI_BORDER_UNIT + s1
                                + getRemainingSpaces(tableWidth - s1.length())
                                + VERTI_BORDER_UNIT;
                        tableContent.add(line);
                    }
                }
                if (!hasPrintedTableHeader) {
                    tableContent.add(consolePrintTableHoriBorder(tableWidth));
                    hasPrintedTableHeader = true;
                }
            }
            tableContent.add(consolePrintTableHoriBorder(tableWidth));
        }

        return tableContent.toArray(new String[0]);
    }

    /**
     * Splits a long String into an array of smaller Strings to fit the table display.
     * indexOfStringSplitStart refers to the index of the first char of the split string.
     * indexOfStringSplitEnd refers to the index after the index of the last char of the split string.
     * @param toPrint String to be printed in table form.
     * @param tableWidth Width of table.
     * @return array of Strings to be printed line by line to fit the table width requirement.
     */
    public String[] getArrayOfSplitStrings(String toPrint, int tableWidth) {
        ArrayList<String> splitStrings = new ArrayList<>();
        int indexOfStringSplitStart = 0;
        int indexOfStringSplitEnd = tableWidth;
        boolean isLastLine = false;
        while (!isLastLine) {
            if (toPrint.substring(indexOfStringSplitStart, indexOfStringSplitEnd).contains(" ")) {
                while (toPrint.charAt(indexOfStringSplitEnd - 1) != ' ') {
                    indexOfStringSplitEnd--;
                }
                splitStrings.add(toPrint.substring(indexOfStringSplitStart, indexOfStringSplitEnd));
                indexOfStringSplitStart = indexOfStringSplitEnd;
                indexOfStringSplitEnd += tableWidth;

            } else {
                //if a single word without space is longer than tableWidth
                while (toPrint.charAt(indexOfStringSplitEnd - 1) != ' ') {
                    indexOfStringSplitEnd++;
                    if (indexOfStringSplitEnd == toPrint.length()) {
                        break;
                    }
                }
                int numOfLines = (indexOfStringSplitEnd - indexOfStringSplitStart) / (tableWidth - 1);
                for (int i = 1; i <= numOfLines; i++) {
                    String wordSegment = toPrint.substring(indexOfStringSplitStart,
                            indexOfStringSplitStart + tableWidth - 1) + "-";
                    splitStrings.add(wordSegment);
                    indexOfStringSplitStart += (tableWidth - 1);
                }
                indexOfStringSplitEnd = indexOfStringSplitStart + tableWidth;
            }
            if (indexOfStringSplitEnd >= toPrint.length()) {
                splitStrings.add(toPrint.substring(indexOfStringSplitStart));
                isLastLine = true;
            }
        }
        return splitStrings.toArray(new String[0]);
    }

    /**
     * Returns a String of the number of spaces needed to complete the table.
     * @param numOfRemainingSpaces number of spaces needed.
     * @return String containing indicated number of spaces.
     */
    public String getRemainingSpaces(int numOfRemainingSpaces) {
        if (numOfRemainingSpaces == 0) {
            return "";
        } else {
            char[] remainingSpaces = new char[numOfRemainingSpaces];
            for (int i = 0; i < numOfRemainingSpaces; i++) {
                remainingSpaces[i] = ' ';
            }
            return new String(remainingSpaces);
        }
    }

    /**
     * Returns an indented horizontal border of a defined length with border corners (length not inclusive of corners).
     * @param borderLength Length of border excluding corners.
     * @return A String containing an indented horizontal border of a defined length with border corners.
     */
    public String consolePrintTableHoriBorder(int borderLength) {
        char[] border = new char[borderLength];
        for (int i = 0; i < borderLength; i++) {
            border[i] = HORI_BORDER_UNIT;
        }
        String borderString = new String(border);
        return BORDER_CORNER + borderString + BORDER_CORNER;
    }
}
