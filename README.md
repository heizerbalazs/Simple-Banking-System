# Simple Banking System

In this project I developed the a simple banking system, which handles accounts and transactions. In the current implementation I use SQLite as the persistence layer and the application has a command line interface with the following capabilities:

1. Create new account
2. Log in
3. Get balance
4. Deposite income
5. Transfer money
6. Close account
7. Log out
8. Exit program

## The model

My model consist of thre class:

1. **Account**: the instances of this class are stored in the database, and they encapsulates three pieces of information: cardnumber, pin, balance;
2. **Bank**: the Bank object handles the operations related to an accounts and sessions. I implemented Lhun's algorithm to check the validity of the card numbers and ensure that the new accounts get properly formatted numbers.
3. **Session**: the Session object encapsulates the cardnumber and pin given by the users and the validity of the (cardnumber, pin) tuple. If the everithing is ok, then the user can access functionality like: get balance, deposite money, transfer money and close account. 
