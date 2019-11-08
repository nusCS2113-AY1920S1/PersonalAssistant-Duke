package controlpanel;

import money.Account;
import money.BankTracker;
import money.Income;
import money.Item;
import money.Expenditure;
import money.Instalment;
import money.Goal;
import money.Loan;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Stack;

public class MoneyStorage {

    private String fileName;
    private DateTimeFormatter dateTimeFormatter;
    private static Stack<Item> deletedEntries;
    private static Stack<BankTracker> deletedBanks;

    /**
     * Constructor for the MoneyStorage Object.
     * @param filePath FilePath of the data text file
     */
    public MoneyStorage(String filePath) {
        fileName = filePath;
        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");
        deletedEntries = new Stack<>();
        deletedBanks = new Stack<>();
    }

    //@@author chengweixuan
    private void parseIncome(String[] info, Account account) {
        Income i = new Income(Float.parseFloat(info[1]), info[2],
                LocalDate.parse(info[3], dateTimeFormatter));
        account.getIncomeListTotal().add(i);
    }

    private void parseExpenditure(String[] info, Account account) {
        Expenditure exp = new Expenditure(Float.parseFloat(info[1]), info[2], info[3],
                LocalDate.parse(info[4], dateTimeFormatter));
        account.getExpListTotal().add(exp);
    }

    private void parseGoal(String[] info, Account account) throws DukeException {
        Goal g = new Goal(Float.parseFloat(info[1]), info[2], info[3],
                LocalDate.parse(info[4], dateTimeFormatter), info[5]);
        account.getShortTermGoals().add(g);
    }

    private void parseInstalment(String[] info, Account account) {
        Instalment ins = new Instalment(Float.parseFloat(info[1]), info[2], info[3],
                LocalDate.parse(info[4], dateTimeFormatter), Integer.parseInt(info[5]),
                Float.parseFloat(info[6]) * 100);
        account.getInstalments().add(ins);
    }

    private void parseLoan(String[] info, Account account) {
        Loan l = new Loan(Float.parseFloat(info[1]), info[2],
                LocalDate.parse(info[3], dateTimeFormatter),
                Loan.Type.ALL);
        l.updateExistingLoan(info[4], info[5], Integer.parseInt(info[6]), Float.parseFloat(info[7]));
        account.getLoans().add(l);
    }

    private void parseBankAccount(String[] info, Account account) {
        BankTracker b = new BankTracker(info[2], Float.parseFloat(info[1]), LocalDate.parse(info[3]),
                Double.parseDouble(info[4]));
        account.getBankTrackerList().add(b);
    }

    /**
     * This method loads the information from the data file into the system.
     * @return the account of the user.
     * @throws IOException if the program cannot read the data file.
     */
    public Account load() throws IOException {
        Account account = new Account();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //if (line.contains("#")) { continue; }
                String[] info = line.split(" @ ");

                switch (info[0]) {
                case "INIT":
                    account.setToInitialize(Boolean.parseBoolean(info[1]));
                    break;
                case "BS":
                    account.setBaseSavings(Float.parseFloat((info[1])));
                    break;
                case "INC":
                    parseIncome(info, account);
                    break;
                case "EXP":
                    parseExpenditure(info, account);
                    break;
                case "G":
                    parseGoal(info, account);
                    break;
                case "INS":
                    parseInstalment(info, account);
                    break;
                case "LOA":
                    parseLoan(info, account);
                    break;
                case "BAN":
                    parseBankAccount(info, account);
                    break;
                default:
                    throw new DukeException("OOPS!! Your file has been corrupted/ input file is invalid!");
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException err) {
            final File parentDir = new File("dataFG");
            parentDir.mkdir();
            final String hash = "moneyAccount";
            final String fileName = hash + ".txt";
            final File file = new File(parentDir, fileName);
            file.createNewFile();
        } catch (IOException | DateTimeParseException | DukeException e) {
            e.printStackTrace();
        }
        return account;
    }

    //@@author therealnickcheong

    /**
     * methods to write the different types of items into the data file.
     * @param i item to be written.
     * @param bufferedWriter used to write into the data file.
     * @throws IOException if there is an error writing to data file.
     */
    public void writeIncome(Income i, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("INC @ " + i.getPrice() + " @ " + i.getDescription()
                +  " @ " + i.getPaidTime() + "\n");
    }
    /**
     * to write the different types of items into the data file.
     * @param exp item to be written.
     * @param bufferedWriter used to write into the data file.
     * @throws IOException if there is an error writing to data file.
     */

    public void writeExp(Expenditure exp, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("EXP @ " + exp.getPrice() + " @ " + exp.getDescription() + " @ "
                + exp.getCategory() + " @ " + exp.getBoughtDate() + "\n");
    }
    /**
     * to write the different types of items into the data file.
     * @param g item to be written.
     * @param bufferedWriter used to write into the data file.
     * @throws IOException if there is an error writing to data file.
     */

    public void writeGoal(Goal g, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("G @ " + g.getPrice() + " @ " + g.getDescription() + " @ "
                + g.getCategory() + " @ " + g.getGoalBy() + " @ " + g.getPriority() + "\n");
    }

    /**
     * to write the different types of items into the data file.
      * @param ins item to be written.
     * @param bufferedWriter used to write into the data file.
     * @throws IOException if there is an error writing to data file.
     */

    public void writeInstalment(Instalment ins, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("INS @ " + ins.getPrice() + " @ " + ins.getDescription() + " @ "
                + ins.getCategory() + " @ " + ins.getBoughtDate() + " @ " + ins.getNumOfPayments() + " @ "
                + ins.getAnnualInterestRate() + "\n");
    }
    /**
     * to write the different types of items into the data file.
     * @param l item to be written.
     * @param bufferedWriter used to write into the data file.
     * @throws IOException if there is an error writing to data file.
     */

    public void writeLoan(Loan l, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("LOA @ " + l.getPrice() + " @ " + l.getDescription()
                + " @ " + l.getStartDate() + " @ " + l.getType().toString() + " @ "
                + l.getEndDate() + " @ " + l.getStatusInt() + " @ " + l.getOutstandingLoan() + "\n");
    }
    /**
     * to write the different types of items into the data file.
     * @param b item to be written.
     * @param bufferedWriter used to write into the data file.
     * @throws IOException if there is an error writing to data file.
     */

    public void writeBank(BankTracker b, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("BAN @ " + b.getAmt() + " @ " + b.getDescription()
                + " @ " + b.getLatestDate().toString() + " @ " + b.getRate() + "\n");
    }
    /**
     * to write the different types of items into the data file.
     * @param account item to be written.
     * @param bufferedWriter used to write into the data file.
     * @throws IOException if there is an error writing to data file.
     */

    public void writeInit(Account account, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("INIT @ " + account.isToInitialize() + "\n");
    }
    /**
     * to write the different types of items into the data file.
     * @param account item to be written.
     * @param bufferedWriter used to write into the data file.
     * @throws IOException if there is an error writing to data file.
     */

    public void writeBaseSavings(Account account, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("BS @ " + account.getBaseSavings() + "\n");
    }

    /**
     * method to write the items into the data file.
     * @param account
     */

    public void writeToFile(Account account) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            writeInit(account, bufferedWriter);
            writeBaseSavings(account,bufferedWriter);

            for (Income i : account.getIncomeListTotal()) {
                writeIncome(i, bufferedWriter);
            }

            for (Expenditure exp : account.getExpListTotal()) {
                writeExp(exp, bufferedWriter);
            }

            for (Goal g : account.getShortTermGoals()) {
                writeGoal(g, bufferedWriter);
            }

            for (Instalment ins : account.getInstalments()) {
                writeInstalment(ins, bufferedWriter);
            }

            for (Loan l : account.getLoans()) {
                writeLoan(l,bufferedWriter);
            }

            for (BankTracker b : account.getBankTrackerList()) {
                writeBank(b,bufferedWriter);
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author Chianhaoplanks
    public void addDeletedEntry(Item item) {
        deletedEntries.push(item);
        if (deletedEntries.size() > 5) {
            deletedEntries.removeElementAt(0);
        }
    }

    public Item getDeletedEntry() {
        Item item = deletedEntries.lastElement();
        deletedEntries.pop();
        return item;
    }

    public void addDeletedBank(BankTracker bankTracker) {
        deletedBanks.push(bankTracker);
        if (deletedBanks.size() > 5) {
            deletedBanks.removeElementAt(0);
        }
    }

    public BankTracker getDeletedBankTracker() {
        BankTracker bt = deletedBanks.lastElement();
        deletedBanks.pop();
        return bt;
    }
}
