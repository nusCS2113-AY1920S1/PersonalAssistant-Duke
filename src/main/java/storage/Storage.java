package storage;

import dictionary.Bank;
import dictionary.TagBank;
import dictionary.Word;
import dictionary.WordBank;
import exception.WordAlreadyExistsException;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Iterator;
import java.util.Stack;

/**
 * Represents the object that reads and writes to the text files where data is stored.
 */
public class Storage {

    private static String FILE_PATH;
    private static String EXCEL_PATH;
    private File excelFile;


    /**
     * Creates new text file if no such file already exists and sets FILE_PATH to the absolute path of the text file.
     * Creates new excel file if no such file exists and sets EXCEL_PATH to the absolute path of excel file.
     */
    public Storage() {
        File currentDir = new File(System.getProperty("user.dir"));
        File filePath = new File(currentDir.toString() + "\\data");
        File dataText = new File(filePath, "wordup.txt");
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        File dataExcel = new File(filePath, "wordup.xlsx");
        if (!dataText.exists()) {
            try {
                dataText.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FILE_PATH = dataText.getAbsolutePath();
        EXCEL_PATH = dataExcel.getAbsolutePath();
        excelFile = new File(EXCEL_PATH);
    }

    /**
     * Converts all data from the text file in the displayOrder it is written in.
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
     * Updates a word in extracted file.
     * @param oldString value of old word
     * @param newString value of word after updated
     */
    public void updateFile(String oldString, String newString) {
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
        writeWordBankExcelFile(bank.getWordBankObject());
        writeTagBankExcelFile(bank.getTagBank());
    }

    /**
     * Loads data from excel file to Bank.
     * @return Bank object containing all data for dictionary
     */
    public Bank loadExcelFile() {
        Bank bank = new Bank();
        try {
            FileInputStream fileInputStream = new FileInputStream(excelFile);

            Workbook workbook = new XSSFWorkbook(fileInputStream);

            Sheet wordBankSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = wordBankSheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                bank.addWord(new
                        Word(cellIterator.next().getStringCellValue(), cellIterator.next().getStringCellValue())
                );
            }

            Sheet tagBankSheet = workbook.getSheetAt(1);
            Iterator<Row> rowIteratorTagBank = tagBankSheet.iterator();
            rowIteratorTagBank.next();
            while (rowIteratorTagBank.hasNext()) {
                Row row = rowIteratorTagBank.next();
                Iterator<Cell> cellIterator = row.cellIterator();
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
        } catch (WordAlreadyExistsException e) {
            System.out.println("Exists");
            e.showError();
        }
        return bank;
    }

    /**
     * Creates a new excel file wordup in data folder if it doesn't exist.
     */
    private void createExcelFile() {
        Workbook workbook = new XSSFWorkbook();

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

        Row headerRow;
        Cell cell;

        Sheet wordBankSheet = workbook.createSheet("WordBank");
        headerRow = wordBankSheet.createRow(0);

        cell = headerRow.createCell(0);
        cell.setCellValue("Word");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("Meaning");
        cell.setCellStyle(headerCellStyle);

        wordBankSheet.autoSizeColumn(0);
        wordBankSheet.autoSizeColumn(1);

        Sheet tagBankSheet = workbook.createSheet("TagBank");
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

    /**
     * Deletes multiple of redundant rows in WordBank sheet in excel file after DeleteCommand.
     * @param lastRow last row in excel file after deletion
     * @param lastRedundantRow last row in excel file before deletion
     */
    public void deleteRowsWordBankSheet(int lastRow, int lastRedundantRow) {
        try {
            FileInputStream fileInputStream = new FileInputStream(excelFile);
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            Sheet wordBankSheet = workbook.getSheetAt(0);

            for (int row = lastRow + 1; row <= lastRedundantRow; row++) {
                wordBankSheet.removeRow(wordBankSheet.getRow(row));
            }

            FileOutputStream fileOutputStream = new FileOutputStream(EXCEL_PATH);
            workbook.write(fileOutputStream);
            fileInputStream.close();
            fileOutputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes multiple of redundant rows in TagBank sheet in excel file after DeleteCommand.
     * @param lastRow last row in excel file after deletion
     * @param lastRedundantRow last row in excel file before deletion
     */
    public void deleteRowsTagBankSheet(int lastRow, int lastRedundantRow) {
        try {
            FileInputStream fileInputStream = new FileInputStream(excelFile);
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            Sheet tagBankSheet = workbook.getSheetAt(1);

            for (int row = lastRow + 1; row <= lastRedundantRow; row++) {
                tagBankSheet.removeRow(tagBankSheet.getRow(row));
            }

            FileOutputStream fileOutputStream = new FileOutputStream(EXCEL_PATH);
            workbook.write(fileOutputStream);
            fileInputStream.close();
            fileOutputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes data to TagBank sheet in excel file.
     * @param tagBank data to be written into sheet
     */
    public void writeTagBankExcelFile(TagBank tagBank) {
        FileInputStream fileInputStream;
        FileOutputStream fileOut;
        try {
            fileInputStream = new FileInputStream(excelFile);
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            Sheet sheet = workbook.getSheetAt(1);
            String[] allTags = tagBank.getAllTagsAsList();
            String[] allWordsOfTag;
            for (int i = 1; i <= allTags.length; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }

                Cell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }

                cell.setCellType(CellType.STRING);
                String tag = allTags[i - 1];
                cell.setCellValue(tag);

                allWordsOfTag = tagBank.getAllWordsOfTag(tag);

                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(String.join(", ", allWordsOfTag));
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            fileOut = new FileOutputStream(EXCEL_PATH);
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

    /**
     * Writes data to WordBank sheet of excel file.
     * @param wordBank data to be written to file
     */
    public void writeWordBankExcelFile(WordBank wordBank) {
        FileInputStream fileInputStream;
        FileOutputStream fileOut;
        try {
            fileInputStream = new FileInputStream(excelFile);
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Word[] allWords = wordBank.getAllWordsAsList();

            for (int i = 1; i <= allWords.length; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }

                Cell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }

                cell.setCellType(CellType.STRING);
                String word = allWords[i - 1].getWordString();

                cell.setCellValue(word);

                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }

                cell.setCellValue(allWords[i - 1].getMeaning());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            fileOut = new FileOutputStream(EXCEL_PATH);
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
}
