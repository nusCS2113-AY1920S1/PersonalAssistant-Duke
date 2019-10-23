package dictionary;

import exception.NoWordFoundException;
import storage.Storage;

public class Bank {

    protected WordBank wordBank;
    protected WordCount wordCount;

    public Bank() {}

    public Bank(Storage storage) {
        wordBank = new WordBank(storage);
        wordCount = new WordCount(wordBank);
    }

    public void addWord(Word word) {

    }

    public void deleteWord(Word word) throws NoWordFoundException {

    }
}
