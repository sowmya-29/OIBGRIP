import java.util.HashMap;
import java.util.Scanner;

class Account {
    private Scanner sc;
    private String name;
    private int pin;
    private String userId;
    private String acc_num;
    private HashMap<String, Integer> details;
    private HashMap<String, String> details_name;
    private HashMap<String, String> transac_history;
    private HashMap<String, String> details_accNo;
    private HashMap<String, Integer> details_balance;
    private String check_userId;
    private int check_pin;

    Account() {
        sc = new Scanner(System.in);
        details = new HashMap<>();
        details_name = new HashMap<>();
        details_balance = new HashMap<>();
        details_accNo = new HashMap<>();
        transac_history = new HashMap<>();
    }

    public void register() {
        System.out.println("\n----------------- Registration---------------------");
        System.out.println("\nPlease enter your Name : ");
        name = sc.nextLine();

        while (true) {
            System.out.println("Please enter your UserId : ");
            userId = sc.nextLine();

            if (!details.containsKey(userId)) {
                break;
            } else {
                System.out.println("Please enter valid userId!");
            }
        }

        while (true) {
            System.out.println("Please enter your Account number : ");
            acc_num = sc.nextLine();

            if (!details.containsKey(acc_num) && acc_num != name && acc_num != userId) {
                break;
            } else {
                System.out.println("Please enter valid Account number!");
            }
        }

        while (true) {
            System.out.println("Please enter your pin : ");
            pin = sc.nextInt();
            if (pin >= 1000) {
                break;
            } else {
                System.out.println("Your pin must be of 4 digits!");
            }
        }

        details.put(userId, pin);
        details_name.put(userId, name);
        details_accNo.put(acc_num, userId);
        sc.nextLine();

        System.out.println("\nRegistration Successful..!");
    }

    public void logIn() {
        Boolean exit = true;
        int attempts = 0;

        System.out.println("\n-------------------- LogIn --------------------------");
        while (exit) {
            System.out.println("\nPlease enter your UserId : ");
            check_userId = sc.nextLine();
            attempts++;

            if (!details.containsKey(check_userId)) {
                if (attempts == 3) {
                    System.out.println("Many Attempts!");
                }
                System.out.println("Please enter a valid UserId!");
            } else {
                exit = false;
            }
        }
        exit = true;
        attempts = 0;

        while (exit) {
            System.out.println("Please enter your pin : ");
            check_pin = sc.nextInt();
            attempts++;
            if (details.get(check_userId) != check_pin) {
                if (attempts == 3) {
                    System.out.println("Many attempts!");
                }
                System.out.println("Please enter A valid Pin!");
            } else {
                exit = false;
                System.out.println("Welcome " + this.getName(check_userId) + " ..");
            }
        }

    }

    String getName(String id) {
        return details_name.get(id);
    }

    public void withdraw() {
        System.out.println("\n-------------------- Withdraw -----------------");
        System.out.println("\nPlease enter the amount you want to withdraw:");
        int amount = sc.nextInt();

        if (details_balance.get(check_userId) != null && amount <= details_balance.get(check_userId)) {
            details_balance.put(check_userId, details_balance.get(check_userId) - amount);

            String his = amount + "   withdrawn from your acccount\n";
            if (transac_history.get(check_userId) == null) {
                transac_history.put(check_userId, his);
            } else {
                his = transac_history.get(check_userId) + his;
                transac_history.put(check_userId, his);
            }
            System.out.println(amount + "  successfully withdrawn from your acccount..");
        } else {
            System.out.println("Balance is insufficient!");
        }
    }

    public void deposit() {
        System.out.println("\n-------------- Deposit ------------------");
        System.out.println("\nPlease enter amount you want to deposit:");
        int amount = sc.nextInt();
        String his = amount + "   Deposited to your acccount..\n";

        if (transac_history.get(check_userId) == null) {
            transac_history.put(check_userId, his);
        } else {
            his = transac_history.get(check_userId) + his;
            transac_history.put(check_userId, his);
        }

        System.out.println(amount + "  successfully deposited to your acccount..");

        if (details_balance.get(check_userId) != null) {
            amount += details_balance.get(check_userId);
        }

        details_balance.put(check_userId, amount);

    }

    public void transfer() {
        System.out.println("\n---------- Transfer money to account ---------");
        System.out.println("Please enter the account number you want to transfer the amount :");
        String acc_num = sc.next();

        System.out.println("Please enter the amount :");
        int transferAmount = sc.nextInt();
        int receivedAmount = transferAmount;

        if (transferAmount <= details_balance.get(check_userId) && details_balance.get(check_userId) != null) {
            details_balance.put(check_userId, details_balance.get(check_userId) - transferAmount);
            String his = transferAmount + "   transferred from your acccount..\n";

            if (transac_history.get(check_userId) == null) {
                transac_history.put(check_userId, his);
            } else {
                his = transac_history.get(check_userId) + his;
                transac_history.put(check_userId, his);
            }
            System.out.println("Rs"+transferAmount + "  successfully transfered from your acccount..");
        } else {
            System.out.println("Balance is insufficient!");
        }

        String id = details_accNo.get(acc_num);

        if (details_balance.get(id) != null) {
            transferAmount += details_balance.get(check_userId);
        }

        details_balance.put(id, transferAmount);
        String his = receivedAmount + "   received by other acccount..\n";

        if (transac_history.get(id) == null) {
            transac_history.put(id, his);
        } else {
            his = transac_history.get(id) + his;
            transac_history.put(id, his);
        }

    }

    public void transac_history() {
        System.out.println("\n----------------- Transaction History ------------");
        String history = transac_history.get(check_userId);
        System.out.println(history);
    }
}

public class ATM {
    public static void main(String[] args) {
        Account my_acc = new Account();
        try (Scanner sc = new Scanner(System.in)) {
            int choice;

            while (true) {
                System.out.println("\n-----------------------------------------------------");
                System.out.println("       Welcome to ATM System ");
                System.out.println("-------------------------------------------------------");
                System.out.println("\n1)Press 1 to Register your account..");
                System.out.println("2)Press 2 to Log In to your existing account..");
                System.out.println("3)Press 0 to exit from the system..");
                
                choice = sc.nextInt();

                if (choice == 1) {
                    my_acc.register();
                } else if (choice == 2) {
                    my_acc.logIn();
                } else {
                    System.out.println("Thank you for visiting..!!!");
                    break;
                }

                boolean exit = true;

                while (exit) {
                    System.out.println("\n\n1.Transaction History");
                    System.out.println("2.Withdraw");
                    System.out.println("3.Deposit");
                    System.out.println("4.Transfer");
                    System.out.println("5.Quit");

                    int ch = sc.nextInt();

                    switch (ch) {
                        case 1: {
                            my_acc.transac_history();
                            break;
                        }
                        case 2: {
                            my_acc.withdraw();
                            break;
                        }
                        case 3: {
                            my_acc.deposit();
                            break;
                        }
                        case 4: {
                            my_acc.transfer();
                            break;

                        }
                        default: {
                            exit = false;
                            System.out.println("Thank you for visiting..!!!");
                        }
                    }
                }
            }
        }

    }
}
