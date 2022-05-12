package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import javax.naming.AuthenticationException;
import java.net.SocketTimeoutException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    AccountService accountsService = new AccountService();
    UserService userService = new UserService();
    TransferService transferService = new TransferService();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        currentUser = null;
        while (currentUser == null) {
            UserCredentials credentials = consoleService.promptForCredentials();
            currentUser = authenticationService.login(credentials);
            accountsService.setAuthToken(currentUser.getToken());
            userService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        double balance = accountsService.getUserBalance(currentUser.getUser().getId());
        System.out.println("Your current account balance is " +balance);

    }

    private void viewTransferHistory() {
        // TODO Auto-generated method stub

        Transfer[] listOfTransfers = transferService.getAllTransfers();

        System.out.println("\nACCOUNT ID:  TRANSFER ID:  TRANSFER TYPE:  TRANSFER STATUS:  RECIPIENT ID:  TRANSFER AMOUNT: \n--------------------------------------------------------------------------------------------");

        for(Transfer transfers : listOfTransfers){
            System.out.println(currentUser.getUser().getId() + "\t\t  " + transfers.getTransfer_id() + "\t\t\t" + transfers.getTransfer_type_id() + "\t\t\t\t" + transfers.getTransfer_status_id() + "\t\t\t\t  " + transfers.getAccount_to() + "\t\t\t$" + transfers.getAmount());
        }
        System.out.println("\n");

//
//        System.out.println("\nUSER:  RECIPIENT ID:  TRANSFER AMOUNT: \n---------------------------------------------");
//
//        for(Transfer transfers : listOfTransfers){
//            System.out.println(currentUser.getUser().getUsername() + "\t" + transfers.getAccount_to() + "\t$" + transfers.getAmount());
//        }
//        System.out.println("\n");
//        System.out.print("-------------------------------------------\r\n" +
//                "Please enter transfer ID to view details (0 to cancel): ");
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
//        if (Integer.parseInt(input) != 0) { //making sure the input was not = to 0
//            boolean foundTransferId = false;
//            for (Transfer transfer : listOfTransfers) {
//                if (Integer.parseInt(input) == transfer.getTransfer_id()) {
//                    Transfer temp = restTemplate.exchange(API_BASE_URL + "transfers/" + i.getTransfer_id(), HttpMethod.GET, makeAuthEntity(), Transfers.class).getBody();
//                    foundTransferId = true;
//                    System.out.println("--------------------------------------------\r\n" +
//                            "Transfer Details\r\n" +
//                            "--------------------------------------------\r\n" +
//                            " Id: "+ temp.getTransferId() + "\r\n" +
//                            " From: " + temp.getUserFrom() + "\r\n" +
//                            " To: " + temp.getUserTo() + "\r\n" +
//                            " Type: " + temp.getTransferType() + "\r\n" +
//                            " Status: " + temp.getTransferStatus() + "\r\n" +
//                            " Amount: $" + temp.getAmount());
//                }
//            }
//            if (!foundTransferId) {
//                System.out.println("Not a valid transfer ID");
//            }
//        }

    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

//        Transfer[] pendingTransfers = transferService.getPendingTransfers();
//
//        System.out.println("\nACCOUNT ID:  TRANSFER ID:  TRANSFER TYPE:  TRANSFER STATUS:  RECIPIENT ID:  TRANSFER AMOUNT: \n--------------------------------------------------------------------------------------------");
//
//        for(Transfer transfers : pendingTransfers){
//            System.out.println(currentUser.getUser().getId() + "\t\t  " + transfers.getTransfer_id() + "\t\t\t" + transfers.getTransfer_type_id() + "\t\t\t\t" + transfers.getTransfer_status_id() + "\t\t\t\t  " + transfers.getAccount_to() + "\t\t\t$" + transfers.getAmount() + "\n");
//        }

    }

    private void sendBucks() {
        // TODO Auto-generated method stub
        User[] listOfUsers = userService.getAllUsers();
        TransferDTO transferDTO = new TransferDTO();

        System.out.println("ID: \t USERNAME:\n------------------");

        for(User user : listOfUsers)
        {
            System.out.println(user.getId()+"\t "+ user.getUsername());
        }
        System.out.println("\n");

//        Transfer newTransfer = new Transfer();

        transferDTO.setUser_id_from(currentUser.getUser().getId());
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter user id to send money to: ");
        String transfer_user = scanner.nextLine();
        transferDTO.setUser_id_to(Long.parseLong(transfer_user)); // looking for id in listofusers

        // newTransfer.setAccount_to(user_input);
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("\nEnter amount: ");
        String amount = scanner1.nextLine(); //convert to double
        transferDTO.setAmount(Double.parseDouble(amount));
        transferService.sendTransfer(transferDTO);

        System.out.println("\nTransfer successful");

        // newTransfer.setAmount(user_input);

//        newTransfer.setTransfer_status_id(2);
//        newTransfer.setTransfer_type_id(2);

//        System.out.println("User: " + newTransfer.getAccount_from() + "\n Recipient: " + newTransfer.getAccount_to() + "\n");
//        System.out.println("Amount: " + newTransfer.getAmount() + "\n");

        // Transfer transfer = TransferService.createNewTransfer(newTransfer);
    }

        private void requestBucks() {
        // TODO Auto-generated method stub

    }

}
