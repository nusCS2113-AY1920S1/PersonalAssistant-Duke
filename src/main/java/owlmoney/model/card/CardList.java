package owlmoney.model.card;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import owlmoney.logic.parser.exception.CardException;
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
        if(!isDeleted) {
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

    public void editCard(String name, String newName, String limit, String rebate, String dueDate, Ui ui) {
        for (int i = 0; i < cardLists.size(); i++) {
            if (!(newName.isEmpty() || newName.isBlank())) {
                cardLists.get(i).setName(newName);
            }
            if (!(limit.isEmpty() || limit.isBlank())) {
                cardLists.get(i).setLimit(Double.parseDouble(limit));
            }
            if (!(rebate.isEmpty() || rebate.isBlank())) {
                cardLists.get(i).setRebate(Double.parseDouble(rebate));
            }
        }
    }

    /**
     * Lists all cards in the bank account.
     *
     * @param ui required for printing.
     */
    public void listCards(Ui ui) throws CardException {
            checkCardListEmpty(ui);
            for (int i = 0; i < cardLists.size(); i++) {
                ui.printMessage(cardLists.get(i).getDetails());
            }
    }
}
