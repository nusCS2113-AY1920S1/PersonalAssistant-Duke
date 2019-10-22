package command;

import dictionary.WordBank;
import exception.WordBankNotEnoughForQuizException;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generate a 4-option MCQ. User should answer by index of options, namely 1, 2, 3, 4.
 */
public class QuizCommand extends Command {
    public String question;
    public String answer;
    public String[] options;
    protected Random random;
    protected ArrayList<Integer> randomNumbers;
    public int optionSequence;

    /**
     * Creates a quiz command with a random integer for the asked word,
     * options are the words to be chosen, randomNumbers is a list to store quiz.
     */
    public QuizCommand() {
        this.random = new Random();
        this.options = new String[4];
        this.randomNumbers  = new ArrayList<>();
    }

    /**
     * Checks if the number already exists in the randomized quiz.
     * @param r randomized integer
     * @return true if r already exists in the list
     */
    private boolean isDuplicate(int r) {
        for (int i = 0; i < randomNumbers.size(); i++) {
            if (randomNumbers.get(i).equals(r)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Creates a quiz with 4 options 1, 2, 3, 4 for user.
     * @param wordBank word bank to contain all the words
     * @throws WordBankNotEnoughForQuizException if word bank have fewer than 4
     *     words, it cannot generate the quiz
     */
    public void generateQuiz(WordBank wordBank) throws WordBankNotEnoughForQuizException {
        int sizeOfWordBank = wordBank.getWordBank().size();
        if (sizeOfWordBank < 4) {
            throw new WordBankNotEnoughForQuizException();
        }
        Object[] wordBankWord = wordBank.getWordBank().keySet().toArray();

        while (randomNumbers.size() < 4) {
            int r = random.nextInt(sizeOfWordBank);
            if (!isDuplicate(r)) {
                randomNumbers.add(r);
            }
        }
        this.question = toString().valueOf(wordBankWord[randomNumbers.get(0)]);
        this.answer = toString().valueOf(wordBank.getWordBank().get(question).getMeaning());
        this.options[0] = answer;
        for (int i = 0; i < 3; i++) {
            String tempWord = toString().valueOf(wordBankWord[randomNumbers.get(i + 1)]);
            this.options[i + 1] = wordBank.getWordBank().get(tempWord).getMeaning();
        }

        optionSequence = random.nextInt(4);
        return this.question+": "+this.answer;
    }

}
