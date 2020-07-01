package banking.services;

import banking.io.CodeSerializer;
import banking.io.InputReader;
import banking.model.Account;
import banking.model.Bank;
import banking.model.Session;

import java.util.Arrays;


public class BankService {

    public static void createAccount() {
        Account newAccount = Bank.addAccount();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(CodeSerializer.serialize(newAccount.getCardNumber()));
        System.out.println("Your card PIN:");
        System.out.println(CodeSerializer.serialize(newAccount.getPin()));
    }

    public static Session closeAccount(Session session) {
        try {
            if (session.isVerified()) {
                Bank.closeAccount(session.getCardNumber());
                System.out.println("The account has been closed!");
                session.setPin(null);
                session.setCardNumber(null);
                session.setVerified(false);
            } else {
                System.out.println("Access denied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public static Session logIn(Session session) {
        session.setCardNumber(InputReader.getCode("Enter your card number:"));
        session.setPin(InputReader.getCode("Enter your PIN:"));
        try {
            boolean s = Bank.verifyAccount(session.getPin(), session.getCardNumber());
            session.setVerified(s);
            if (session.isVerified() && Bank.validateCardNumber(session.getCardNumber())) {
                System.out.println("You have successfully logged in!");
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return session;
        }
    }

    public static Session logOut(Session session) {
        session.setVerified(false);
        session.setCardNumber(null);
        session.setPin(null);
        System.out.println("You have successfully logged out!");
        return session;
    }

    public static void getBalance(Session session) {
        try {
            if (session.isVerified()) {
                int balance = Bank.getAccountBalance(session.getCardNumber());
                System.out.printf("Balance: %d\n", balance);
            } else {
                System.out.println("Access denied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addIncome(Session session) {
        int income = InputReader.getIncomeAmount();
        try {
            if (session.isVerified()) {
                Bank.addIncome(session.getCardNumber(), income);
                System.out.println("Income was added!");
            } else {
                System.out.println("Access denied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doTransfer(Session session) {
        System.out.println("Transfer");
        int[] recieverCardNumber = InputReader.getCode("Enter card number:");
        try {
            if (session.isVerified()) {
                int balance = Bank.getAccountBalance(session.getCardNumber());
                if (Arrays.equals(session.getCardNumber(), recieverCardNumber)) {
                    System.out.println("You can't transfer money to the same account!");
                } else if (!Bank.validateCardNumber(recieverCardNumber)) {
                    System.out.println("Probably you made mistake in the card number. Please try again!");
                } else if (Bank.getAccount(recieverCardNumber) == null) {
                    System.out.println("Such a card does not exist.");
                } else {
                    int transfer = InputReader.getTransferAmount();
                    if (transfer > balance) {
                        System.out.println("Not enough money!");
                    } else {
                        Bank.addIncome(recieverCardNumber, transfer);
                        Bank.addPayment(session.getCardNumber(), transfer);
                        System.out.println("Success!");
                    }
                }
            } else {
                System.out.println("Access denied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exit() {
        System.out.println("Bye!");
    }
}
