package banking.io;

import java.util.Scanner;

public class InputReader {

    private static Scanner scanner = new Scanner(System.in);

    public static int[] getCode(String message) {
        System.out.println(message);

        String input = scanner.next().trim();
        int[] code = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            code[i] = Integer.parseInt(input.substring(i,i+1));
        }

        return code;
    }

    public static int getIncomeAmount() {
        System.out.println("Enter income: ");
        return scanner.nextInt();
    }

    public static int getTransferAmount() {
        System.out.println("Enter how much money you want to transfer: ");
        return scanner.nextInt();
    }
}
