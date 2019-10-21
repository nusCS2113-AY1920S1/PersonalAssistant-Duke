package com.algosenpai.app.storage;

import com.algosenpai.app.stats.ChapterStat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class UserStorageParser {

    //The scanner which is used for reading in from the file.
    private Scanner inputScanner;

    /**
     * Initialises everything so that Scanner works.
     * @throws FileNotFoundException will never be thrown as the file would already be prepared.
     */
    public UserStorageParser() throws FileNotFoundException {
        String workingDir = System.getProperty("user.dir") + File.separator + "UserData.txt";
        FileReader reader = new FileReader(workingDir);
        this.inputScanner = new Scanner(reader);

    }

    /**
     * Responsible for parsing in the input for each chapter. The skips are used to remove unnecessary information
     * from each line of input. All the respective information needed for each ChapterStat is derived and constructed.
     *
     * @return the ChapterStat for each chapter.
     */
    public ChapterStat nextChapterStat() {
        inputScanner.skip("Chapter ");
        final int chapterNo = inputScanner.nextInt();
        inputScanner.skip(" : ");
        final String chapterName = inputScanner.nextLine();
        inputScanner.skip("Total Attempts made : ");
        final int attempts = Integer.parseInt(inputScanner.nextLine());
        inputScanner.skip("Total Questions answered : ");
        final int answered = Integer.parseInt(inputScanner.nextLine());
        inputScanner.skip("Total Questions Correct : ");
        final int correctAnswers = Integer.parseInt(inputScanner.nextLine());
        inputScanner.skip("Total Questions Wrong : ");
        final int wrongAnswers = Integer.parseInt(inputScanner.nextLine());
        inputScanner.skip("Percentage of Questions correct : ");
        final double percentage = Double.parseDouble(inputScanner.nextLine());
        inputScanner.skip("Comments : ");
        String comments = inputScanner.nextLine();
        // remove the quotes (1st and last character)
        comments = comments.substring(1,comments.length() - 1);
        return new ChapterStat(chapterName, chapterNo, attempts, answered,
                correctAnswers, wrongAnswers, percentage, comments);
    }

    /**
     * Checks if there is anything else to be read in the file.
     * @return true if there are somemore chapters to be read. False if otherwise.
     */
    public boolean hasMoreTokens() {
        return inputScanner.hasNext();
    }

    /**
     * Scans in the next line of String.
     * @return the String which has been scanned in by the Scanner.
     */
    public String nextLine() {
        return inputScanner.nextLine();
    }

}
