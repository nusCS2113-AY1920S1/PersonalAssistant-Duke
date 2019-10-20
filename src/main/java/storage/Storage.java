package storage;

import dictionary.Word;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Represents the object that reads and writes to the text files where data is stored.
 */
public class Storage {

    private static String FILE_PATH;

    public Storage() {
    }

    public Storage(String filePath) {
        FILE_PATH = filePath;
    }

    /**
     * Converts all data from text file in storage to list of words.
     * @return an arraylist containing all words in dictionary
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
     * Loads history from the history file.
     * @return a stack containing all input words
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

}
