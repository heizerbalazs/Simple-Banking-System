package banking.model;

public class Session {

    private boolean verified;
    private int action;
    private int[] cardNumber;
    private int[] pin;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int[] getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int[] cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int[] getPin() {
        return pin;
    }

    public void setPin(int[] pin) {
        this.pin = pin;
    }
}