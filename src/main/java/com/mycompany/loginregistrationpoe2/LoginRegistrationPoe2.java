
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
        System.out.println("     ======================");
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
        LoginLogicPoeP2 Logic = new LoginLogicPoeP2(Firstname, Lastname, Username, Password, PhoneNumber);
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
            System.out.print("Choose Option: ");
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
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    private static void sendMessages(Scanner input) {
        //Asks the user how many messages they want to sent
        System.out.print("How many messages would you like to send? ");
        int numMessages = input.nextInt();
        input.nextLine();
//for loop
        for (int index = 0; index < numMessages; index++) {
            System.out.println("\n--- Message " + (index+ 1) + " ---");

            //This check does the recipient have a +27 in they are number else it will return a invaild number
            String recipient;
            do {                                                                                                             //REF : https://www.w3schools.com/java/java_while_loop.asp
                System.out.print("Recipient cell number (format +27XXXXXXXXX): ");
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
            MessageP2 messageObj = new MessageP2(index + 1, recipient, messageText);
//this give the user a what would like to do with they are text
            System.out.println("What would you like to do with this message?");
            System.out.println(" 1) Send Message");
            System.out.println(" 2) Disregard Message");
            System.out.println(" 3) Store Message to send later");
            System.out.print("Choice: ");
            int choice = -1;
            while (choice == -1)
           try {                                                                                                                       //REF: https://youtu.be/1XAfapkBQjk?si=pZO7VFld1kRDMyHO
                choice = input.nextInt();
                input.nextLine();
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
               
            }

            System.out.println(messageObj.sentMessage(choice));

            switch (choice) {
                case 1:
                    MessageP2.totalMessagesSent++;
                    messageObj.storeMessageInJson();
                    System.out.println(messageObj.printMessages());
                    break;
                case 2:
                    // This option is still under development and does work
                    break;
                case 3:
                    messageObj.storeMessageInJson(); // This stores the message to Json file
                    break;
                default:
                    System.out.println("Invalid choice, message not processed."); //if the user does choice one of this option it will reture a invaild message
            }
        }
//This count the total message produced during the chat
        System.out.println("\nTotal messages sent this session: " + MessageP2.returnTotalMessages());
    }
}
