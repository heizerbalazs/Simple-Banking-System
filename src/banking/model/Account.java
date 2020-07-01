package banking.model;

import java.util.Arrays;

public class Account {

    private int[] pin = new int[4];
    private int[] cardNumber = new int[16];
    private int balance;

    public Account(int[] cardNumber, int[] pin) {
        this.pin = Arrays.copyOf(pin, pin.length);
        this.cardNumber = Arrays.copyOf(cardNumber, cardNumber.length);
        this.balance = 0;
    }

    public int[] getPin() {
        return pin;
    }

    /*public void setPin(short pin) {
        this.pin = pin;
    }*/

    public int[] getCardNumber() {
        return cardNumber;
    }

    /*public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }*/

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}