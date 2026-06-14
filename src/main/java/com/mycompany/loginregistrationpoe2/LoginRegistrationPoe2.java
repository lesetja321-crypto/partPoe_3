
package com.mycompany.loginregistrationpoe2;

import java.util.Scanner;

/**
 *
 * @author William
 */
public class LoginRegistrationPoe2 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //Welcoming users to register
        System.out.println("======================");
        System.out.println(" WELCOME TO QUICKCHAT    ");
        System.out.println("======================");
        System.out.println("-----Registration-----");
        System.out.print("Enter your first name : >>");
        String Firstname = input.nextLine();
        System.out.print("Enter your last name :>>");
        String Lastname = input.nextLine();
        System.out.print("Create a username(That contains a maximum of 5 character and a underscore _ ) : >>");
        String Username = input.nextLine();
        System.out.print("Create a password (That contains a minimum of eight characters , one capital letter , numbers and one special character) : >>");
        String Password = input.nextLine();
        System.out.print("Enter your Phone number (+27 followed by 9 digits : >>");
        String PhoneNumber = input.nextLine();

//Object declarations for the LoginLogicPoeP2
        LoginLogicPoe Logic = new LoginLogicPoe(Firstname, Lastname, Username, Password, PhoneNumber);
        //Checks username
        if (Logic.checkUserName(Username)) {

            System.out.println("Username successfully captured.");
        } else {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is at least five characters in length");
        }
        //Checks password 
        if (Logic.checkPasswordComplexity(Password)) {

            System.out.println("Password successfully captured");
        } else {
            System.out.println("Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }
        //checks cellphonenumbers
        if (Logic.checkCellPhoneNumbers(PhoneNumber)) {

            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        }

        //Register user 
        String Registration = Logic.registerUser(Username, Password);
        System.out.println(Registration);
//Login Successfully it preduce this content
        if (Logic.isRegistered()) {
            System.out.println("User has been successfully registered.");

        }
        //Loads storeded messages into arrays
        MessagePoe.loadStoredMessagesFromJson();

        //Accepts the registered user so they can Login
        System.out.println("******LOGIN******");
        System.out.print("Username: >>");
        String loginUsername = input.nextLine();
        System.out.print("Password: >>");
        String loginPassword = input.nextLine();

//Aprrove the login detail from registration such as username and password
        String loginStatus = Logic.returnLoginStatus(loginUsername, loginPassword);
        System.out.println(loginStatus);

//If the user has successfully enter it welcome the user
        if (Logic.loginUser(Username, Password)) {
            System.out.println("WELCOME TO QUICKCHAT");

        }
        //Menu with While loops,
        //do while loop and for loop
        //Menu that has menu option

        int menu = 0;                              //REF : https://www.w3schools.com/java/ref_keyword_while.asp
        while (menu < 1) {
            System.out.println("  ======= MENU =======");
            System.out.println(" 1) Send Messages");
            System.out.println(" 2) Show recently sent messages (Coming Soon)");
            System.out.println(" 3) Quit");
            System.out.println(" 4) Stored messages");
            System.out.print("Choose Option: >>");
            int option = input.nextInt();
            input.nextLine();

            //Cases with options
            switch (option) {                                     //REF : https://www.w3schools.com/java/ref_keyword_case.asp
                case 1:
                    sendMessages(input);
                    break;
                case 2:
                    System.out.println("Coming Soon.....");
                    break;
                case 3:
                    menu++;
                    System.out.println("Thank you for using QuickChat.");
                    break;
                case 4:
                    storedMessagesMenu(input, Firstname + "" + Lastname);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void sendMessages(Scanner input) {
        //Asks the user how many messages they want to sent
        System.out.print("How many messages would you like to send? : >>");
        int numMessages = input.nextInt();
        input.nextLine();
//for loop
        for (int index = 0; index < numMessages; index++) {
            System.out.println("\n--- Message " + (index + 1) + " ---");

            //This check does the recipient have a +27 in they are number else it will return a invaild number
            String recipient;
            do {                                                                                                             //REF : https://www.w3schools.com/java/java_while_loop.asp
                System.out.print("Recipient cell number (format +27XXXXXXXXX): >>");
                recipient = input.nextLine();
                if (!recipient.matches("^\\+27\\d{9}$")) {                                                                                  //Ref https://www.w3schools.com/java/java_ref_arrays.asp
                    System.out.println("Invalid cell number. Must start with +27 and be followed by 9 digits.");
                }

            } while (!recipient.matches("^\\+27\\d{9}$")); //Ref https://www.w3schools.com/java/java_ref_arrays.asp                                         
                                                                                 // REF : https://www.w3schools.com/java/java_while_loop.asp

            //This tell the user that they messageText must not exceed 250 messages
            String messageText;
            do {                                                                                                                // REF: https://www.w3schools.com/java/java_while_loop.asp
                System.out.print("Message (max 250 characters): ");
                messageText = input.nextLine();
                if (messageText.length() > 250) {
                    System.out.println("Please enter a message of less than 250 characters.");
                }
            } while (messageText.length() > 250);                                                                        // REF: https://www.w3schools.com/java/java_while_loop.asp

            //Object class of Messsage
            MessagePoe messageObj = new MessagePoe(index + 1, recipient, messageText);
//this give the user a what would like to do with they are text
            System.out.println("What would you like to do with this message?");
            System.out.println(" 1) Send Message");
            System.out.println(" 2) Disregard Message");
            System.out.println(" 3) Store Message to send later");
            System.out.print("Choice: >> ");
            int choice = -1;
            while (choice == -1)
           try {                                                                                                                       //REF: https://youtu.be/1XAfapkBQjk?si=pZO7VFld1kRDMyHO
                choice = input.nextInt();
                input.nextLine();
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
               System.out.println("Please enter a vaild number (1-3) .");
               input.nextLine();
            }

            System.out.println(messageObj.sentMessage(choice));

            switch (choice) {
                case 1:
                    MessagePoe.totalMessagesSent++;
                    messageObj.storeMessageInJson();
                    messageObj.addToSentMessages();
                    System.out.println(messageObj.printMessages());
                    break;
                case 2:
                    MessageManagerPoe.addToDisregardedMessages(messageText);
                    break;
                case 3:
                    messageObj.storeMessageInJson(); // This stores the message to Json file
                    MessageManagerPoe.addToStoredMessages(messageObj.getMessage(), messageObj.getMessageHash() , messageObj.getMessageID() , recipient);
                    break;
                default:
                    System.out.println("Invalid choice, message not processed."); //if the user does choice one of this option it will reture a invaild message
            }
        }
//This count the total message produced during the chat
        System.out.println("\nTotal messages sent this session: " + MessagePoe.returnTotalMessages());
    }
    private static void storedMessagesMenu(Scanner input , String senderName){
    int storeMenu = 0 ;
    while (storeMenu < 1){
        System.out.println("=====STORED MESSAGES=====");
        System.out.println("1)Display the sender and recipient of all stored messages.");
        System.out.println("2)Display the longest stored message.");
        System.out.println("3)Search for a message ID and display the corresponding recipient and message.");
        System.out.println("4)Search for all the messages stored for a particular recipient.");
        System.out.println("5)Delete a message using the message hash.");
        System.out.println("6)Display a report that lists the full details of all the stored messages");
        System.out.println("7) Back to main menu");
        System.out.print("Choose Option: >>");
        int option = input.nextInt();
        input.nextLine();
        
        switch(option){
            case 1:
                //a) Display sender and recipient of all stored messages
           MessageManagerPoe.displaySenderRecipient(senderName);
                break;
            case 2:
                //b) Display the longest message
                 MessageManagerPoe.displayLongestMessage();
                break;
            case 3:
                //c) Search for a message by ID
                System.out.print("Enter Message ID to search: >>");
                String searchID = input.nextLine();
                MessageManagerPoe.searchByMessageID(searchID);
                break;
            case 4:
                //d) Search all messages for a particular recipient
                System.out.print("Enter recipient number (+27XXXXXXXXX): >>");
                String searchRecipient = input.nextLine();
                MessageManagerPoe.searchByRecipient(searchRecipient);
                break;
            case 5:
                //e) Delete a message using the message hash
                System.out.print("Enter message hash to delete: >>");
                String hash = input.nextLine();
                MessageManagerPoe.deleteMessageByHash(hash);
                break;
            case 6:
                //f) Display full report of all messages
               MessageManagerPoe.displayReport();
                break;
            case 7:
                storeMenu++;
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    }
}


