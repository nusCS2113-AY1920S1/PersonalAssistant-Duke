package controlpanel;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import money.*;
import javafx.util.Pair;

public class MoneyStorage {

    private String fileName;
    private DateTimeFormatter dateTimeFormatter;

    public MoneyStorage(String filePath) {
        fileName = filePath;
        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    public Account load() {
        Account account = new Account();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //if (line.contains("#")) { continue; }
                String[] info = line.split(" @ ");
                if (!(info[0].equals("BS") || info[0].equals("INC") || info[0].equals("EXP") || info[0].equals("SEX") ||
                        info[0].equals("G") || info[0].equals("INS") || info[0].equals("INIT") ||
                        info[0].equals("LOA") || info[0].equals("BAN"))) {
                    throw new DukeException("OOPS!! Your file has been corrupted/ input file is invalid!");
                }
                switch(info[0]) {
                    case "INIT":
                        account.setToInitialize(Boolean.parseBoolean(info[1]));
                        break;
                    case "BS":
                        account.setBaseSavings(Float.parseFloat((info[1])));
                        break;
                    case "INC":
                        Income i = new Income(Float.parseFloat(info[1]), info[2],
                                LocalDate.parse(info[3], dateTimeFormatter));
                        account.getIncomeListTotal().add(i);
                        break;
                    case "EXP":
                        if (info.length > 5) {
                            Bill bill = new Bill(Float.parseFloat(info[1]), info[2], info[3],
                                    LocalDate.parse(info[4], dateTimeFormatter),
                                    LocalDate.parse(info[5], dateTimeFormatter));
                            account.getExpListTotal().add(bill);
                        } else {
                            Expenditure exp = new Expenditure(Float.parseFloat(info[1]), info[2], info[3],
                                    LocalDate.parse(info[4], dateTimeFormatter));
                            account.getExpListTotal().add(exp);
                        }
                        break;
                    case "SEX":
                        String[] names = info[5].split(" ! ");
                        ArrayList<Pair<String, Boolean>> parties = new ArrayList<>();
                        for (String name : names) {
                            name = name.replaceAll(" ", "");
                            String[] splitStr = name.split("&", 2);
                            boolean status = false;
                            if (splitStr[1].equals("1")) {
                                status = true;
                            }

                            Pair<String, Boolean> temp = new Pair<>(splitStr[0], status);
                            parties.add(temp);
                        }
                        Split spiltExp = new Split(Float.parseFloat(info[1]), info[2], info[3],
                                LocalDate.parse(info[4], dateTimeFormatter), parties);
                        account.getExpListTotal().add(spiltExp);
                        break;
                    case "G":
                        Goal g = new Goal(Float.parseFloat(info[1]), info[2], info[3],
                                LocalDate.parse(info[4], dateTimeFormatter), info[5]);
                        account.getShortTermGoals().add(g);
                        break;
                    case "INS":
                        Instalment ins = new Instalment(Float.parseFloat(info[1]), info[2], info[3],
                                LocalDate.parse(info[4], dateTimeFormatter), Integer.parseInt(info[5]),
                                Float.parseFloat(info[6]) * 100);
                        account.getInstalments().add(ins);
                        break;
                    case "LOA":
                        Loan l = new Loan(Float.parseFloat(info[1]), info[2],
                                LocalDate.parse(info[3], dateTimeFormatter),
                            Loan.Type.ALL);
                        l.updateExistingLoan(info[4], info[5], Integer.parseInt(info[6]), Float.parseFloat(info[7]));
                        account.getLoans().add(l);
                        break;
                    case "BAN":
                        BankTracker b = new BankTracker(info[2], Float.parseFloat(info[1]), LocalDate.parse(info[3]),
                                Double.parseDouble(info[4]));
                        account.getBankTrackerList().add(b);
                        break;
                    default:
                        break;
                }
            }
            bufferedReader.close();
        } catch (IOException | DateTimeParseException | DukeException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void writeToFile(Account account) {
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");

            bufferedWriter.write("INIT @ " + account.isToInitialize() + "\n");
            bufferedWriter.write("BS @ " + account.getBaseSavings() + "\n");

            for (Income i : account.getIncomeListTotal()) {
                bufferedWriter.write("INC @ " + i.getPrice() + " @ " + i.getDescription() +
                        " @ " + i.getPaidTime() + "\n");
            }

            for (Expenditure exp : account.getExpListTotal()) {
                if (exp instanceof Split) {
                    bufferedWriter.write("SEX @ " + exp.getPrice() + " @ " + exp.getDescription() + " @ " +
                            exp.getCategory() + " @ " + exp.getBoughtDate() + " @ " +
                            ((Split) exp).getNamesOfPeople() + "\n");
                } else if (exp instanceof Bill) {
                    bufferedWriter.write("EXP @ " + exp.getPrice() + " @ " + exp.getDescription() + " @ " +
                            exp.getCategory() + " @ " + exp.getBoughtDate() + " @ " +
                            ((Bill) exp).getNextPayDay() + "\n");
                } else {
                    bufferedWriter.write("EXP @ " + exp.getPrice() + " @ " + exp.getDescription() + " @ " +
                            exp.getCategory() + " @ " + exp.getBoughtDate() + "\n");
                }
            }

            for (Goal g : account.getShortTermGoals()) {
                bufferedWriter.write("G @ " + g.getPrice() + " @ " + g.getDescription() + " @ " +
                        g.getCategory() + " @ " + g.getGoalBy() + " @ " + g.getPriority() + "\n");
            }

            for (Instalment ins : account.getInstalments()) {
                bufferedWriter.write("INS @ " + ins.getPrice() + " @ " + ins.getDescription() + " @ " +
                        ins.getCategory() + " @ " + ins.getBoughtDate() + " @ " + ins.getNumOfPayments() + " @ " +
                        ins.getAIR() + "\n");
            }

            for (Loan l : account.getLoans()) {
                bufferedWriter.write("LOA @ " + l.getPrice() + " @ " + l.getDescription() +
                        " @ " + l.getStartDate() + " @ " + l.getType().toString() + " @ " +
                        l.getEndDate() + " @ " + l.getStatusInt() + " @ " + l.getOutstandingLoan() + "\n");
            }

            for (BankTracker b : account.getBankTrackerList()) {
                bufferedWriter.write("BAN @ " + b.getAmt() + " @ " + b.getDescription() +
                        " @ " + b.getLatestDate().toString() + " @ " + b.getRate() + "\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void markDeletedEntry(String type, int index) throws DukeException {
        try {
            File tempFile = File.createTempFile("moneyAccountTemp", ".txt",
                    new File("data/"));
            File file = new File(fileName);
            String fileNameTemp = tempFile.getAbsolutePath();
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter(fileNameTemp);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String line;
            int i = index;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(type) && !line.contains("#")) {
                    i--;
                }
                if (line.startsWith(type) && i == 0) {
                    line = line.replaceAll("@", "#");
                }
                bufferedWriter.write(line + '\n');
            }
            bufferedReader.close();
            bufferedWriter.close();
            if (!file.delete()) {
                throw new DukeException(" OOPS! File cannot be deleted!");
            } else if (!tempFile.renameTo(file)) {
                throw new DukeException(" OOPS! File cannot be updated!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void undoDeletedEntry(Account account, String dataType, int index) throws DukeException {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            boolean isLineRead = false;
            while (!isLineRead && (line = bufferedReader.readLine()) != null) {
                if (line.startsWith(dataType) && line.contains("#")) {
                    String[] info = line.split(" # ");
                    switch(dataType) {
                        case "EXP":
                            if (info.length > 5) {
                                Bill bill = new Bill(Float.parseFloat(info[1]), info[2], info[3],
                                        LocalDate.parse(info[4], dateTimeFormatter),
                                        LocalDate.parse(info[5], dateTimeFormatter));
                                if (index > account.getExpListTotal().size()) {
                                    account.getExpListTotal().add(bill);
                                } else {
                                    account.getExpListTotal().add(index - 1, bill);
                                }
                            } else {
                                Expenditure exp = new Expenditure(Float.parseFloat(info[1]), info[2], info[3],
                                        LocalDate.parse(info[4], dateTimeFormatter));
                                if (index > account.getExpListTotal().size()) {
                                    account.getExpListTotal().add(exp);
                                } else {
                                    account.getExpListTotal().add(index - 1, exp);
                                }
                            }
                            break;
                        case "INC":
                            Income i = new Income(Float.parseFloat(info[1]), info[2],
                                    LocalDate.parse(info[3], dateTimeFormatter));
                            if (index > account.getIncomeListTotal().size()) {
                                account.getIncomeListTotal().add(i);
                            } else {
                                account.getIncomeListTotal().add(index - 1, i);
                            }
                            break;
                        case "G":
                            Goal g = new Goal(Float.parseFloat(info[1]), info[2], info[3],
                                    LocalDate.parse(info[4], dateTimeFormatter), info[5]);
                            if (index > account.getShortTermGoals().size()) {
                                account.getShortTermGoals().add(g);
                            } else {
                                account.getShortTermGoals().add(index - 1, g);
                            }
                            break;
                        case "INS":
                            Instalment ins = new Instalment(Float.parseFloat(info[1]), info[2], info[3],
                                    LocalDate.parse(info[4], dateTimeFormatter), Integer.parseInt(info[5]),
                                    Float.parseFloat(info[6]) * 100);
                            if (index > account.getInstalments().size()) {
                                account.getInstalments().add(ins);
                            } else {
                                account.getInstalments().add(index - 1, ins);
                            }
                            break;
                        case "BAN":
                            BankTracker b = new BankTracker(info[2], Float.parseFloat(info[1]),
                                    LocalDate.parse(info[3]), Double.parseDouble(info[4]));
                            account.getBankTrackerList().add(index - 1, b);
                            break;
                        default:
                            break;
                    }
                    isLineRead = true;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
