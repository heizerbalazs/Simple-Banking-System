package banking.app;


import banking.model.Session;
import banking.services.BankService;

import java.util.Scanner;

public class UserInterface {

    static Scanner scanner = new Scanner(System.in);

    public static void startPageMenu(Session session) {
        StartPageOption option;

        try {
            do {
                option = StartPageOption.getByInput();
                switch (option) {
                    case CREATE_ACCOUNT:
                        BankService.createAccount();
                        break;
                    case LOG_IN:
                        session = BankService.logIn(session);
                        if (session.isVerified()) {
                            session = AccountPageMenu(session);
                        }
                        break;
                    case EXIT:
                        BankService.exit();
                        break;
                    default:
                        System.out.println("There is no such option. Please try again: ");
                        break;
                }
            } while (option != StartPageOption.EXIT);
        } catch (Exception e) {
            System.out.println("No such choice.");
        }

    }

    public static Session AccountPageMenu(Session session) {
        AccountPageOption option;
        try {
            do {
                option = AccountPageOption.getByInput();
                switch (option) {
                    case BALANCE:
                        BankService.getBalance(session);
                        break;
                    case INCOME:
                        BankService.addIncome(session);
                        break;
                    case TRANSFER:
                        BankService.doTransfer(session);
                        break;
                    case CLOSE:
                        return BankService.closeAccount(session);
                    case LOG_OUT:
                        return BankService.logOut(session);
                    case EXIT:
                        BankService.exit();
                        break;
                    default:
                        System.out.println("There is no such option. Please try again: ");
                        break;
                }
            } while (option != AccountPageOption.EXIT);
         } catch (Exception e) {
            System.out.println("No such choice.");
        }

        return null;
    }

    enum StartPageOption {
        CREATE_ACCOUNT(1, "Create an account"),
        LOG_IN(2, "Log into account"),
        EXIT(0, "Exit");

        int input;
        String text;

        StartPageOption(int input, String text) {
            this.input = input;
            this.text = text;
        }

        static void printChoices() {
            for(StartPageOption option : StartPageOption.values()) {
                System.out.printf("%d. %s\n", option.input, option.text);
            }
        }

        static StartPageOption getByInput() throws Exception {
            printChoices();
            int input = scanner.nextInt();
            StartPageOption selected = null;
            while (selected == null) {
                for (StartPageOption option : StartPageOption.values()) {
                    if (option.input == input) {
                        selected = option;
                    }
                }
            }
            return selected;
        }

    }

    enum AccountPageOption {
        BALANCE(1, "Balance"),
        INCOME(2, "Add income"),
        TRANSFER(3, "Do transfer"),
        CLOSE(4, "Close account"),
        LOG_OUT(5, "Log out"),
        EXIT(0, "Exit");

        int input;
        String text;

        AccountPageOption(int input, String text) {
            this.input = input;
            this.text = text;
        }

        static void printChoices() {
            for(AccountPageOption option : AccountPageOption.values()) {
                System.out.printf("%d. %s\n", option.input, option.text);
            }
        }

        static AccountPageOption getByInput() throws Exception{
            printChoices();
            int input = scanner.nextInt();
            AccountPageOption selected = null;
            while (selected == null) {
                for (AccountPageOption option : AccountPageOption.values()) {
                    if (option.input == input) {
                        selected = option;
                    }
                }
            }
            return selected;
        }

    }
}
