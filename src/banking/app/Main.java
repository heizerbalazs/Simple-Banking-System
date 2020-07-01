package banking.app;

import banking.db.DataBaseConnection;
import banking.model.Session;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DataBaseConnection db = new DataBaseConnection(args[1]);
        UserInterface ui = new UserInterface();
        ui.startPageMenu(new Session());
    }
}
