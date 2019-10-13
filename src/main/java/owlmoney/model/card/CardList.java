package owlmoney.model.card;

import java.util.ArrayList;

import owlmoney.logic.command.exception.CommandException;
import owlmoney.logic.parser.exception.CardException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

public class CardList {
    private ArrayList<Card> cardLists;

    /**
     * Constructor that creates an arrayList of Cards.
     */
    public CardList() {
        cardLists = new ArrayList<Card>();
    }

    /**
     * Adds an instance of card into the CardList.
     *
     * @param newCard a new card object to be added.
     * @param ui required for printing.
     */
    public void addCard(Card newCard, Ui ui) {
        cardLists.add(newCard);
        ui.printMessage("Added new card: ");
        ui.printMessage(newCard.getDetails());
    }

    /**
     * Deletes an instance of a card from the CardList.
     *
     * @param name name of the card to be deleted.
     * @param ui required for printing.
     */
    public void deleteCard(String name, Ui ui) throws CardException {
        checkCardListEmpty(ui);
        boolean isDeleted = false;
        for (int i = 0; i < cardLists.size(); i++) {
            if (cardLists.get(i).getName().equals(name)) {
                ui.printMessage("Removing " + cardLists.get(i).getName());
                cardLists.remove(i);
                isDeleted = true;
                break;
            }
        }
        if (!isDeleted) {
            throw new CardException("No such card exist.");
        }
    }

    /**
     * Throws CardException if CardList is empty.
     *
     * @param ui required for printing.
     * @throws CardException If CardList is empty.
     */
    public void checkCardListEmpty(Ui ui) throws CardException {
        if (cardLists.size() <= 0) {
            throw new CardException("There are 0 cards in your profile");
        }
    }


    /**
     * Throws CardException if card name already exist in CardList.
     *
     * @param ui required for printing.
     * @throws CardException If card name already exist in CardList.
     */
    /*
    public void checkCardNameDuplicate(String name, Ui ui) throws CardException {
        for (int i = 0; i < cardLists.size(); i++) {
            if(cardLists.get(i).getName().equals(name)) {
                throw new CardException("Card name already exist.");
            }
        }
    }
    */

    /**
     * Edits the credit card details.
     *
     * @param name     Credit Card to be edited.
     * @param newName  New name of credit card if any.
     * @param limit    New limit of credit card if any.
     * @param rebate   New rebate of credit card if any.
     * @param ui       Required for printing.
     */
    public void editCard(String name, String newName, String limit, String rebate, Ui ui) throws CardException {
        boolean isEdit = false;
        for (int i = 0; i < cardLists.size(); i++) {
            if (cardLists.get(i).getName().equals(name)) {
                isEdit = true;
                if (!(newName.isEmpty() || newName.isBlank())) {
                    cardLists.get(i).setName(newName);
                }
                if (!(limit.isEmpty() || limit.isBlank())) {
                    System.out.println("1");
                    cardLists.get(i).setLimit(Double.parseDouble(limit));
                }
                if (!(rebate.isEmpty() || rebate.isBlank())) {
                    cardLists.get(i).setRebate(Double.parseDouble(rebate));
                }
                ui.printMessage("New details of the cards:\n");
                ui.printMessage(cardLists.get(i).getDetails() + "\n");
                break;
            }
        }
        if (isEdit == false) {
            throw new CardException("Card could not be found ");
        }
    }

    /**
     * Lists all credit cards details.
     *
     * @param ui required for printing.
     */
    public void listCards(Ui ui) throws CardException {
        checkCardListEmpty(ui);
        for (int i = 0; i < cardLists.size(); i++) {
            ui.printMessage(cardLists.get(i).getDetails());
        }
    }

    /**
     * Adds an expenditure tied to a credit card.
     * This will store the expenditure in the ExpenditureList in the credit card.
     *
     * @param cardName The credit card name.
     * @param exp      The instance of the expenditure.
     * @param ui       Required for printing.
     */
    //need change exception class in the future for this
    public void addExpenditure(String cardName, Transaction exp, Ui ui)
            throws owlmoney.model.card.exception.CardException {
        for (int i = 0; i < cardLists.size(); i++) {
            if (cardLists.get(i).getName().equals(cardName)) {
                cardLists.get(i).addInExpenditure(exp, ui);
                return;
            }
        }
        throw new owlmoney.model.card.exception.CardException("There are no credit card named :" + cardName);
    }

    /**
     * Lists expenditures in the credit card.
     *
     * @param cardToList The name of the credit card.
     * @param ui         required for printing.
     * @param displayNum Number of expenditures to list.
     */
    public void listCardExpenditure(String cardToList, Ui ui, int displayNum)
            throws TransactionException, owlmoney.model.card.exception.CardException {
        for (int i = 0; i < cardLists.size(); i++) {
            if (cardToList.equals(cardLists.get(i).getName())) {
                cardLists.get(i).listAllExpenditure(ui, displayNum);
                return;
            }
        }
        throw new owlmoney.model.card.exception.CardException("Cannot find bank with name: " + cardToList);
    }
}
