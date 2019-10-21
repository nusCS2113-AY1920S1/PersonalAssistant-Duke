package wallet.storage;

import wallet.model.contact.Contact;
import wallet.model.contact.ContactList;
import wallet.model.record.Loan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LoanStorage extends Storage<Loan> {
    public static final String DEFAULT_STORAGE_FILEPATH_LOAN = "./data/loan.txt";
    private ContactStorage contactStorage = null;

    /**
     * Loads the loans from loan.txt into a temporary ArrayList of Loan objects.
     *
     * @return The ArrayList of Loan objects.
     */
    @Override
    public ArrayList<Loan> loadFile() {
        ArrayList<Loan> loanList = new ArrayList<>();
        Contact person;
        boolean isLend;
        boolean isSettled;

        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_LOAN, "r");
            String str;
            while (raf.getFilePointer() != raf.length()) {
                str = raf.readLine();
                String[] data = str.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Loan loan = null;

                isLend = !data[4].equals("0");
                isSettled = !data[5].equals("0");


                ContactList contactList = new ContactList(contactStorage.loadFile());
                int index = contactList.findIndexWithId(Integer.parseInt(data[6]));
                person = contactList.getContact(index);
                if (data.length == 7 && person != null) {
                    loan = new Loan(data[1], LocalDate.parse(data[3], formatter), Double.parseDouble(data[2]),
                            isLend, isSettled, person);
                }

                if (loan != null) {
                    loan.setId(Integer.parseInt(data[0]));
                    loanList.add(loan);
                }
            }
            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved loans found.");
        } catch (IOException e) {
            System.out.println("End of file.");
        }
        return loanList;
    }

    /**
     * Writes the loan list into loan.txt.
     *
     * @param loanList The Loan object.
     */
    @Override
    public void writeListToFile(ArrayList<Loan> loanList) {
        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_LOAN, "rws");
            raf.setLength(0);

            for (Loan loan : loanList) {
                if (raf.getFilePointer() != 0) {
                    raf.writeBytes("\r\n");
                }
                raf.writeBytes(loan.writeToFile());
            }
            raf.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Sets the ContactStorage object.
     *
     * @param contactStorage The ContactStorage object.
     */
    public void setContactStorage(ContactStorage contactStorage) {
        this.contactStorage = contactStorage;
    }

    /**
     * Returns the ContactStorage object.
     *
     * @return The ContactStorage object.
     */
    public ContactStorage getContactStorage() {
        return this.contactStorage;
    }
}
