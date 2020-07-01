package banking.model;

import banking.db.DataBaseConnection;
import java.util.Arrays;
import java.util.Random;

public class Bank {

    private static Random random = new Random();

    public static Account addAccount() {
        int[] newCardNumber = generateCardNumber();
        int[] newPin = generatePin();
        Account newAccount = new Account(newCardNumber, newPin);
        DataBaseConnection.insertNewAccount(newCardNumber, newPin);
        return newAccount;
    }

    public static void closeAccount(int[]  cardNumber) {
        DataBaseConnection.deleteAccount(cardNumber);
    }

    public static Account getAccount(int[]  cardNumber) {
        return DataBaseConnection.getAccountByCardNumber(cardNumber);
    }

    public static boolean verifyAccount(int[] pin, int[] cardNumber) {
        Account account = DataBaseConnection.getAccountByCardNumber(cardNumber);
        if (Arrays.equals(account.getPin(), pin)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateCardNumber(int[] cardNumber) {
        return (cardNumber[cardNumber.length-1] + luhnChecksum(cardNumber)) % 10 == 0;
    }

    public static int getAccountBalance(int[] cardNumber) {
        Account account = DataBaseConnection.getAccountByCardNumber(cardNumber);
        return account.getBalance();
    }

    public static void addIncome(int[] cardNumber, int income) {
        Account account = DataBaseConnection.getAccountByCardNumber(cardNumber);

        DataBaseConnection.updateBalance(
                account.getCardNumber(),
                account.getBalance()+income);
    }

    public static void addPayment(int[] cardNumber, int payment) {
        Account account = DataBaseConnection.getAccountByCardNumber(cardNumber);

        DataBaseConnection.updateBalance(
                account.getCardNumber(),
                account.getBalance()-payment);
    }

    private static int[] generateCardNumber() {
        int[] newCardNumber = new int[16];
        // MII and IIN
        newCardNumber[0] = 4;
        for (int i = 1; i < 6; i++) {
            newCardNumber[i] = 0;
        }
        // Account identifier
        do {
            for (int i = 7; i < newCardNumber.length-1; i++) {
                newCardNumber[i] = random.nextInt(10);
            }
            newCardNumber[newCardNumber.length-1] = generateCheckSum(newCardNumber);
        } while (DataBaseConnection.getAccountByCardNumber(newCardNumber) != null);

        return newCardNumber;
    }

    private static int[] generatePin() {
        int[] pin = new int[4];
        for (int i =0; i < 4; i++) {
            pin[i] = random.nextInt(10);
        }
        return pin;
    }

    private static int luhnChecksum(int[] cardNumber) {
        int sum = 0;
        for (int i = 0; i < cardNumber.length-1; i++) {
            if (i % 2 == 1) {
                sum += cardNumber[i];
            } else {
                sum += 2*cardNumber[i] > 9 ? 2*cardNumber[i] - 9 : 2*cardNumber[i];
            }
        }
        return sum;
    }

    private static int generateCheckSum(int[] cardNumber) {
        int checksum = 0;
        int sum = luhnChecksum(cardNumber);
        for (int i = 0; i < 10; i++) {
            if ((sum + i) % 10 == 0) {
                checksum = i;
                break;
            }
        }
        return checksum;
    }

}
