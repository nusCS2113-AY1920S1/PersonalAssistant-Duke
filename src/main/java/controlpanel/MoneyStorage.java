package controlpanel;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import money.Account;
import money.Expenditure;
import money.Goal;
import money.Income;
import money.Instalment;

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
                String[] info = line.split(" @ ");
                if (!(info[0].equals("BS") || info[0].equals("I") || info[0].equals("EXP") ||
                        info[0].equals("G") || info[0].equals("INS") || info[0].equals("INIT"))) {
                    throw new DukeException("OOPS!! Your file has been corrupted/ input file is invalid!");
                }
                switch(info[0]) {
                    case "INIT":
                        account.setToInitialize(Boolean.parseBoolean(info[1]));
                        break;
                    case "BS":
                        account.setBaseSavings(Float.parseFloat((info[1])));
                        break;
                    case "I":
                        Income i = new Income(Float.parseFloat(info[1]), info[2],
                                LocalDate.parse(info[3], dateTimeFormatter));
                        account.getIncomeListTotal().add(i);
                        break;
                    case "EXP":
                        Expenditure exp = new Expenditure(Float.parseFloat(info[1]), info[2], info[3],
                                LocalDate.parse(info[4], dateTimeFormatter));
                        account.getExpListTotal().add(exp);
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
                bufferedWriter.write("I @ " + i.getPrice() + " @ " + i.getDescription() +
                        " @ " + i.getPaidTime() + "\n");
            }

            for (Expenditure exp : account.getExpListTotal()) {
                bufferedWriter.write("EXP @ " + exp.getPrice() + " @ " + exp.getDescription() + " @ " +
                        exp.getCategory() + " @ " + exp.getBoughtDate() + "\n");
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

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void markDeletedEntry(String type, String stringRead, String stringWrite,
                                 int index) throws DukeException {
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
                if (line.startsWith(type)) {
                    i--;
                    if (i == 0) {
                        line.replaceAll(stringRead, stringWrite);
                    }
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
}
