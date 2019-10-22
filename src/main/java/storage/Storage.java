package storage;

import dictionary.Word;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
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
     * @return an arraylist containing all words in dictionary ordered by ALPHABET
     */
    public TreeMap<String, Word> loadFile() {
        File file = new File(FILE_PATH);
        FileReader fr = null;
        BufferedReader br = null;
        int differentiator=-1; //let 0 belong to existence of tags, 1 belong to existence of synonyms
        HashSet<String> temp=new HashSet<>();
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
                String[] parsedWordAndMeaning = line.split(":"); //split into mainWord and meaning+tags+synonyms
                /**Note that not all words will have tags, but most words will have synonyms
                 * so we check for synonym first
                 */
                String[] parsedWord_Meaning_Synonym = parsedWordAndMeaning[1].split("<s>",2);
                if(parsedWord_Meaning_Synonym.length==2){ //synonyms exist in the textfile
                    differentiator=1; //flag for parsing through Storage
                    String[] separateSynonyms = parsedWord_Meaning_Synonym[1].split(" ");
                    for(int i=0;i<separateSynonyms.length-1;i++){
                        temp.add(separateSynonyms[i].trim());
                    }
                }
                if(differentiator==1){ //existence of Synonyms after Word:meaning
                    Word word = new Word(parsedWordAndMeaning[0].trim(),parsedWordAndMeaning[1].trim(),temp,1);
                    wordBank.put(word.getWord(), word);
                }
                else if(differentiator == 0){ //existence of Synonyms after Word:meaning -- no synonyms
                    //do something here
                }
                else { //just word and meaning
                    Word word = new Word(parsedWordAndMeaning[0].trim(), parsedWordAndMeaning[1].trim());
                    wordBank.put(word.getWord(), word);
                }
                //wordBank.put(word.getWord(), word);
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

}
