package owlmoney.model.card;

import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.UUID;

import owlmoney.model.card.exception.CardException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

/**
 * Provides a layer of abstraction for the ArrayList that stores credit cards.
 */
public class CardList {
    private ArrayList<Card> cardLists;
    private static final int ONE_INDEX = 1;
    private static final boolean ISMULTIPLE = true;
    private static final boolean ISSINGLE = false;
    private static final int ISZERO = 0;
    private static final int MAX_CARD_LIMIT = 10;
    private Storage storage;

    /**
     * Creates an arrayList of Cards.
     * @param storage for importing and exporting purposes.
     */
    public CardList(Storage storage) {
        cardLists = new ArrayList<Card>();
        this.storage = storage;
    }

    /**
     * Adds an instance of card into the CardList.
     *
     * @param newCard a new card object to be added.
     * @param ui      required for printing.
     * @throws CardException If duplicate credit card name found.
     */
    public void cardListAddCard(Card newCard, Ui ui) throws CardException {
        if (cardExists(newCard.getName())) {
            throw new CardException("There is already a credit card with the name " + newCard.getName());
        }
        if (cardLists.size() >= MAX_CARD_LIMIT) {
            throw new CardException("The maximum limit of 10 credit cards has been reached.");
        }
        cardLists.add(newCard);
        ui.printMessage("Added a new card with the below details: ");
        printOneCard(ONE_INDEX, newCard, ISSINGLE, ui);
    }

    /**
     * Deletes an instance of a card from the CardList.
     *
     * @param name name of the card to be deleted.
     * @param ui   required for printing.
     * @throws CardException If CardList is empty or card to be deleted do not exist.
     */
    public void cardListDeleteCard(String name, Ui ui) throws CardException {
        cardListCheckListEmpty();
        boolean isDeleted = false;
        for (int i = ISZERO; i < cardLists.size(); i++) {
            if (cardLists.get(i).getName().equals(name)) {
                Card temp = cardLists.get(i);
                cardLists.remove(i);
                ui.printMessage("Card with the following details has been removed:");
                printOneCard(ONE_INDEX, temp, ISSINGLE, ui);
                isDeleted = true;
                break;
            }
        }
        if (!isDeleted) {
            throw new CardException("No such card exist for deletion.");
        }
    }

    /**
     * Throws CardException if CardList is empty.
     *
     * @throws CardException If CardList is empty.
     */
    private void cardListCheckListEmpty() throws CardException {
        if (cardLists.size() <= ISZERO) {
            throw new CardException("There are 0 cards in your profile.");
        }
    }

    /**
     * Gets the size of the cardList which counts the number of credit cards object within.
     *
     * @return size of cardList.
     */
    public int getCardListSize() {
        return cardLists.size();
    }

    /**
     * Checks if the credit card name that the user specified exists.
     *
     * @param cardName name of credit card.
     * @return the result specifying whether the credit card name already exists.
     */
    private boolean cardExists(String cardName) {
        for (int i = ISZERO; i < getCardListSize(); i++) {
            if (cardName.equals(cardLists.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Throws CardException if the credit card name that the user specified does not exists.
     *
     * @param cardName name of credit card.
     * @throws CardException if the credit card name that the user specified does not exists.
     */
    public void checkCardExists(String cardName) throws CardException {
        boolean isExist = false;
        for (int i = ISZERO; i < getCardListSize(); i++) {
            if (cardName.equals(cardLists.get(i).getName())) {
                isExist = true;
            }
        }
        if (!isExist) {
            throw new CardException("Credit card " + cardName + " does not exist!");
        }
    }

    /**
     * Checks if new card name is unique.
     *
     * @param currentCard The card to be changed.
     * @param newCardName The new name of the card.
     * @throws CardException If new card name is not unique.
     */
    private void compareCard(Card currentCard, String newCardName) throws CardException {
        for (int i = ISZERO; i < getCardListSize(); i++) {
            if (cardLists.get(i).getName().equals(newCardName) && !cardLists.get(i).equals(currentCard)) {
                throw new CardException("There is already a credit card with the name: " + newCardName);
            }
        }
    }

    /**
     * Checks if all credit card expenditures have been paid else cannot edit card limit.
     *
     * @param card The card object.
     * @throws CardException If credit card contains unpaid expenditures.
     */
    private void checkUnpaidCannotEditLimit(Card card) throws CardException {
        if (!card.isEmpty()) {
            throw new CardException("Card limit cannot be edited if there are unpaid expenditures");
        }
    }

    /**
     * Edits the credit card details.
     *
     * @param name    Credit Card to be edited.
     * @param newName New name of credit card if any.
     * @param limit   New limit of credit card if any.
     * @param rebate  New rebate of credit card if any.
     * @param ui      Required for printing.
     * @throws CardException If card cannot be found or new card name already exist.
     */
    public void cardListEditCard(String name, String newName, String limit, String rebate, Ui ui)
            throws CardException {
        for (int i = ISZERO; i < cardLists.size(); i++) {
            if (cardLists.get(i).getName().equals(name)) {
                if (!(newName.isEmpty() || newName.isBlank())) {
                    compareCard(cardLists.get(i), newName);
                    cardLists.get(i).setName(newName);
                }
                if (!(limit.isEmpty() || limit.isBlank())) {
                    this.checkUnpaidCannotEditLimit(cardLists.get(i));
                    cardLists.get(i).setLimit(Double.parseDouble(limit));
                }
                if (!(rebate.isEmpty() || rebate.isBlank())) {
                    cardLists.get(i).setRebate(Double.parseDouble(rebate));
                }
                ui.printMessage("New details of the cards: ");
                printOneCard(ONE_INDEX, cardLists.get(i), ISSINGLE, ui);
                return;
            }
        }
        throw new CardException("Card could not be found for editing card details.");
    }

    /**
     * Lists all credit cards details.
     *
     * @param ui Required for printing.
     * @throws CardException If CardList is empty.
     */
    public void cardListListCards(Ui ui) throws CardException {
        cardListCheckListEmpty();
        ui.printCardHeader();
        for (int i = ISZERO; i < cardLists.size(); i++) {
            printOneCard((i + ONE_INDEX), cardLists.get(i), ISMULTIPLE, ui);
        }
        ui.printDivider();
    }

    /**
     * Adds an expenditure tied to a credit card.
     * This will store the expenditure in the ExpenditureList in the credit card.
     *
     * @param cardName The credit card name.
     * @param exp      The instance of the expenditure.
     * @param ui       Required for printing.
     * @param type     Type of account to add expenditure into
     * @throws CardException If the credit card name cannot be found.
     */
    public void cardListAddExpenditure(String cardName, Transaction exp, Ui ui, String type)
            throws CardException {
        for (int i = ISZERO; i < cardLists.size(); i++) {
            if (cardLists.get(i).getName().equals(cardName)) {
                cardLists.get(i).addInExpenditure(exp, ui, type);
                return;
            }
        }
        throw new CardException("Card cannot be found for adding expenditure:" + cardName);
    }

    /**
     * Lists expenditures in the credit card.
     *
     * @param cardToList The name of the credit card.
     * @param ui         required for printing.
     * @param displayNum Number of expenditures to list.
     * @throws CardException        If the credit card name cannot be found.
     * @throws TransactionException If no expenditure found or no expenditure is in the list.
     */
    public void cardListListCardExpenditure(String cardToList, Ui ui, int displayNum)
            throws TransactionException, CardException {
        for (int i = ISZERO; i < cardLists.size(); i++) {
            if (cardToList.equals(cardLists.get(i).getName())) {
                cardLists.get(i).listAllExpenditure(ui, displayNum);
                return;
            }
        }
        throw new CardException("Card cannot be found to list expenditure: " + cardToList);
    }

    /**
     * Deletes an expenditure from the transactionList in the card object.
     *
     * @param expNum                The transaction number.
     * @param deleteFromAccountCard The name of the card.
     * @param ui                    Required for printing.
     * @throws CardException        If card does not exist.
     * @throws TransactionException If invalid transaction.
     */
    public void cardListDeleteExpenditure(int expNum, String deleteFromAccountCard, Ui ui)
            throws CardException, TransactionException {
        for (int i = ISZERO; i < cardLists.size(); i++) {
            if (deleteFromAccountCard.equals(cardLists.get(i).getName())) {
                cardLists.get(i).deleteExpenditure(expNum, ui);
                return;
            }
        }
        throw new CardException("Card cannot be found for deleting expenditure: " + deleteFromAccountCard);
    }

    /**
     * Edits an expenditure from the transactionList in the card object.
     *
     * @param expNum       The transaction number.
     * @param editFromCard The name of the card.
     * @param desc         The description of the expenditure.
     * @param amount       The amount of the expenditure.
     * @param date         The date of the expenditure.
     * @param category     The category of the expenditure.
     * @param ui           Required for printing.
     * @throws CardException        If card does not exist.
     * @throws TransactionException If incorrect date format.
     */
    public void cardListEditExpenditure(int expNum, String editFromCard, String desc, String amount,
            String date, String category, Ui ui) throws CardException, TransactionException {
        for (int i = ISZERO; i < cardLists.size(); i++) {
            if (cardLists.get(i).getName().equals(editFromCard)) {
                cardLists.get(i).editExpenditureDetails(expNum, desc, amount, date, category, ui);
                return;
            }
        }
        throw new CardException("Card cannot be found for editing expenditure: " + editFromCard);
    }

    /**
     * Prints card details.
     *
     * @param num                Represents the numbering of the card.
     * @param card               The card object to be printed.
     * @param isMultiplePrinting Represents whether the function will be called for printing once or
     *                           multiple time
     * @param ui                 The object use for printing.
     */
    private void printOneCard(int num, Card card, boolean isMultiplePrinting, Ui ui) {
        if (!isMultiplePrinting) {
            ui.printCardHeader();
        }
        ui.printCard(num, card.getName(),
                "$" + new DecimalFormat("0.00").format(card.getLimit()),
                "$" + new DecimalFormat("0.00").format(card.getRemainingLimitNow()),
                new DecimalFormat("0.00").format(card.getRebate()) + "%");
        if (!isMultiplePrinting) {
            ui.printDivider();
        }
    }

    /**
     * Prints the card header details once only when listing of multiple card.
     *
     * @param num Represents the current number of card being listed.
     * @param ui  The object use for printing.
     */
    private void printOneCardHeader(int num, Ui ui) {
        if (num == ISZERO) {
            ui.printBankHeader();
        }
    }

    /**
     * Finds card that matches the name provided by user.
     *
     * @param cardName The name of the card to match against.
     * @param ui       The object required for printing.
     * @throws CardException If there is no matches for card object.
     */
    public void findCard(String cardName, Ui ui) throws CardException {
        ArrayList<Card> tempCardList = new ArrayList<Card>();
        String matchingWord = cardName.toUpperCase();

        for (int i = ISZERO; i < getCardListSize(); i++) {
            if (cardLists.get(i).getName().toUpperCase().contains(matchingWord)) {
                tempCardList.add(cardLists.get(i));
            }
        }
        if (tempCardList.isEmpty()) {
            throw new CardException("Card with the following keyword could not be found: " + cardName);
        }

        for (int i = ISZERO; i < tempCardList.size(); i++) {
            printOneCardHeader(i, ui);
            printOneCard((i + ONE_INDEX), tempCardList.get(i), ISMULTIPLE, ui);
        }
        ui.printDivider();
    }

    /**
     * Finds matching card transactions from the card specified by the user.
     *
     * @param cardName    The name of the card object to be searched for matching transaction.
     * @param fromDate    The date to search from.
     * @param toDate      The date to search until.
     * @param description The description keyword to match against.
     * @param category    The category keyword to match against.
     * @param ui          The object required for printing.
     * @throws CardException        If card with the name does not exist.
     * @throws TransactionException If parsing of date fails.
     */
    public void cardListFindTransaction(String cardName, String fromDate, String toDate,
            String description, String category, Ui ui) throws CardException, TransactionException {
        for (int i = ISZERO; i < getCardListSize(); i++) {
            if (cardLists.get(i).getName().equals(cardName)) {
                cardLists.get(i).findTransaction(fromDate, toDate, description, category, ui);
                return;
            }
        }
        throw new CardException("Card with the following name does not exist: " + cardName);
    }

    /**
     * Returns the total unpaid expenditure amount based on the specified date.
     *
     * @param card      The credit card to search the expenditures from.
     * @param date      The YearMonth date of the expenditures to search.
     * @return          The total unpaid expenditure amount based on the specified date.
     * @throws CardException If card does not exist.
     */
    public double getUnpaidBillAmount(String card, YearMonth date) throws CardException {
        checkCardExists(card);
        double billAmount = 0;
        for (int i = 0; i < cardLists.size(); i++) {
            if (card.equals(cardLists.get(i).getName())) {
                billAmount = cardLists.get(i).getUnpaidBillAmount(date);
            }
        }
        return billAmount;
    }

    /**
     * Returns the total paid expenditure amount based on the specified date.
     *
     * @param card      The credit card to search the expenditures from.
     * @param date      The YearMonth date of the expenditures to search.
     * @return          The total unpaid expenditure amount based on the specified date.
     * @throws CardException    If card does not exist.
     */
    public double getPaidBillAmount(String card, YearMonth date) throws CardException {
        checkCardExists(card);
        double billAmount = 0;
        for (int i = 0; i < cardLists.size(); i++) {
            if (card.equals(cardLists.get(i).getName())) {
                billAmount = cardLists.get(i).getPaidBillAmount(date);
            }
        }
        return billAmount;
    }

    /**
     * Returns the monthly rebate of the credit card.
     *
     * @param card  The credit card to get the monthly rebate information from.
     * @return      The monthly rebate of the credit card.
     * @throws CardException    If card does not exist.
     */
    public double getRebateAmount(String card) throws CardException {
        checkCardExists(card);
        double rebateAmount = 0;
        for (int i = 0; i < cardLists.size(); i++) {
            if (card.equals(cardLists.get(i).getName())) {
                rebateAmount = cardLists.get(i).getRebate();
            }
        }
        return rebateAmount;
    }

    /**
     * Returns the id of the credit card.
     *
     * @param card  The credit card to get the id from.
     * @return      The id of the credit card.
     * @throws CardException    If card does not exist.
     */
    public UUID getCardId(String card) throws CardException {
        checkCardExists(card);
        UUID id = null;
        for (int i = 0; i < cardLists.size(); i++) {
            if (card.equals(cardLists.get(i).getName())) {
                id = cardLists.get(i).getId();
            }
        }
        return id;
    }

    /**
     * Transfers expenditures from unpaid list to paid list.
     *
     * @param card      The credit card of which the expenditures to transfer.
     * @param cardDate  The YearMonth date of expenditures to transfer.
     * @param type      Type of expenditure (card or bank).
     * @throws TransactionException If invalid transaction when deleting.
     */
    public void transferExpUnpaidToPaid(String card, YearMonth cardDate, String type)
            throws TransactionException {
        for (int i = 0; i < cardLists.size(); i++) {
            if (card.equals(cardLists.get(i).getName())) {
                cardLists.get(i).transferExpUnpaidToPaid(cardDate, type);
            }
        }
    }

    /**
     * Transfers expenditures from paid list to unpaid list.
     *
     * @param card      The credit card of which the expenditures to transfer.
     * @param cardDate  The YearMonth date of expenditures to transfer.
     * @param type      Type of expenditure (card or bank).
     * @throws TransactionException If invalid transaction when deleting.
     */
    public void transferExpPaidToUnpaid(String card, YearMonth cardDate, String type)
            throws TransactionException {
        for (int i = 0; i < cardLists.size(); i++) {
            if (card.equals(cardLists.get(i).getName())) {
                cardLists.get(i).transferExpPaidToUnpaid(cardDate, type);
            }
        }
    }
}
