package storage;

import dictionary.Word;
import dictionary.Bank;

import exception.WordAlreadyExistException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;

import java.util.Iterator;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Represents the object that reads and writes to the text files where data is stored.
 */
public class Storage {

    private static String FILE_PATH;
    private static final String EXCEL_PATH = "data/wordup.xlsx";

    public Storage() {
    }

    public Storage(String filePath) {
        FILE_PATH = filePath;
    }

    /**
     * Converts all data from text file in storage to list of words.
     * @return an arraylist containing all words in dictionary ordered by ALPHABET
     */
    public TreeMap<String, Word> loadFile() {
        File file = new File(FILE_PATH);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            TreeMap<String, Word> wordBank = new TreeMap<>();
            String line = br.readLine();
            while (line != null) {
                // get data from storage
                // parse the line first
                if (line.equals("")) {
                    line = br.readLine();
                    continue;
                }
                String[] parsedWordAndMeaning = line.split(":");
                Word word = new Word(parsedWordAndMeaning[0].trim(), parsedWordAndMeaning[1].trim());
                wordBank.put(word.getWord(), word);
                line = br.readLine();
            }
            return wordBank;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Converts all data from the text file in the order it is written in.
     * Stack structure used because the first words to be extracted are the last ones added to stack.
     * @return a stack containing all input words ordered by SEQUENCE OF ENTRY
     */
    public Stack<Word> loadHistoryFromFile() {
        File file = new File(FILE_PATH);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            Stack<Word> wordHistory = new Stack<>();
            String line = br.readLine();
            while (line != null) {
                // get data from storage
                // parse the line first
                if (line.equals("")) {
                    line = br.readLine();
                    continue;
                }
                String[] parsedWordAndMeaning = line.split(":");
                Word word = new Word(parsedWordAndMeaning[0].trim(), parsedWordAndMeaning[1].trim());
                wordHistory.add(word);
                line = br.readLine();
            }
            return wordHistory;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Writes data to an extracted file.
     * @param s new word to be added
     * @param append return true if the file can be appended
     */
    public void writeFile(String s, boolean append) {
        File file = new File(FILE_PATH);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);
            bw.write(s);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Deletes an item from file.
     * @param oldString a string to be deleted
     */
    public void deleteFromFile(String oldString) {
        File file = new File(FILE_PATH);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String oldContent = "";
            String line = br.readLine();

            while ((line != null) && (!line.equals("\n"))) {
                oldContent = oldContent + line + System.lineSeparator();
                line = br.readLine();
            }
            oldContent = oldContent.substring(0, oldContent.length() - 1);
            String newContent = oldContent.replace(oldString, "").trim();
            Storage writer = new Storage();
            writer.writeFile(newContent,false);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates a word in extracted file.
     * @param oldString value of old word
     * @param newString value of word after updated
     */
    public void editFromFile(String oldString,String newString) {
        File file = new File(FILE_PATH);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String oldContent = "";
            String line = br.readLine();

            while ((line != null) && (!line.equals("\n"))) {
                oldContent = oldContent + line + System.lineSeparator();
                line = br.readLine();
            }
            oldContent = oldContent.substring(0, oldContent.length() - 1);
            String newContent = oldContent.replace(oldString, newString).trim();
            Storage writer = new Storage();
            writer.writeFile(newContent,false);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes the data to excel file.
     * @param bank represents the data bank
     */
    public void writeExcelFile(Bank bank) {
        FileInputStream fileInputStream;
        FileOutputStream fileOut;
        try {
            fileInputStream = new FileInputStream(new File(EXCEL_PATH));
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            Sheet sheet;
            Row row;
            Cell cell;
            String word, tag;

            sheet = workbook.getSheetAt(0);
            Word[] allWords = bank.getAllWords();

            for (int i = 1; i <= allWords.length; i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }

                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }

                cell.setCellType(CellType.STRING);
                word = allWords[i - 1].getWord();

                cell.setCellValue(word);

                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }

                cell.setCellValue(allWords[i - 1].getMeaning());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            sheet = workbook.getSheetAt(1);
            String[] allTags = bank.getAllTags();
            String[] allWordsOfTag;
            for (int i = 1; i <= allTags.length; i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }

                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }

                cell.setCellType(CellType.STRING);
                tag = allTags[i - 1];
                cell.setCellValue(tag);

                allWordsOfTag = bank.getAllWordsOfTag(tag);

                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(String.join(", ", allWordsOfTag));
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            fileOut = new FileOutputStream("data/wordup.xlsx");
            workbook.write(fileOut);
            fileInputStream.close();
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            createExcelFile();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public Bank loadExcelFile() {
        Bank bank = new Bank();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(EXCEL_PATH));

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet;
            Iterator<Row> rowIterator;
            Iterator<Cell> cellIterator;
            Row row;

            sheet = workbook.getSheetAt(0);
            rowIterator = sheet.iterator();
            rowIterator.next();
            while(rowIterator.hasNext()) {
                row = rowIterator.next();
                cellIterator = row.cellIterator();

                bank.addWord(new
                        Word(cellIterator.next().getStringCellValue(), cellIterator.next().getStringCellValue())
                );
            }

            sheet = workbook.getSheetAt(1);
            rowIterator = sheet.iterator();
            rowIterator.next();
            while(rowIterator.hasNext()) {
                row = rowIterator.next();
                cellIterator = row.cellIterator();
                String tag = cellIterator.next().getStringCellValue();
                String[] allWords = cellIterator.next().getStringCellValue().split(", ");

                for (int i = 0; i < allWords.length; i++) {
                    bank.addTagToWord(allWords[i], tag);
                }
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            createExcelFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WordAlreadyExistException e) {
            e.showError();
        }
        return bank;
    }

    /**
     * Creates a new excel file wordup in data folder if it doesn't exist.
     */
    private void createExcelFile() {
        Workbook workbook = new XSSFWorkbook();

        Sheet wordBankSheet = workbook.createSheet("WordBank");
        Sheet tagBankSheet = workbook.createSheet("TagBank");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

        Row headerRow;
        Cell cell;

        headerRow = wordBankSheet.createRow(0);

        cell = headerRow.createCell(0);
        cell.setCellValue("Word");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("Meaning");
        cell.setCellStyle(headerCellStyle);

        wordBankSheet.autoSizeColumn(0);
        wordBankSheet.autoSizeColumn(1);

        headerRow = tagBankSheet.createRow(0);

        cell = headerRow.createCell(0);
        cell.setCellValue("Tag");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("Words");
        cell.setCellStyle(headerCellStyle);

        tagBankSheet.autoSizeColumn(0);
        tagBankSheet.autoSizeColumn(1);

        try {
            FileOutputStream fileOut = new FileOutputStream(EXCEL_PATH);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}