/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginregistrationpoe2;

/**
 *
 * @author William
 */
public class MessageManagerPoe {

    //Array that stores messages Part3
    public static String[] sentMessages = new String[100];
    public static String[] disregardedMessages = new String[100];
    public static String[] storedMessages = new String[100];
    public static String[] messageHashArray = new String[100];
    public static String[] messageIDArray = new String[100];
    public static String[] recipientArray = new String[100];

    //set counters how many items are in each array
    public static int sentCount = 0;
    public static int disregardedCount = 0;
    public static int storedCount = 0;
    public static int hashCount = 0;
    
 public static void addToSentMessages(String message, String messageHash, String messageID, String recipient) {
        sentMessages[sentCount] = message;
        messageHashArray[hashCount] = messageHash;
        messageIDArray[hashCount] = messageID;
        recipientArray[hashCount] = recipient;
        sentCount++;
        hashCount++;
    }
 
    // Adds message to the disregarded array
    public static void addToDisregardedMessages(String message) {
        disregardedMessages[disregardedCount] = message;
        disregardedCount++;
    }
 
    // Adds message to the stored array and parallel arrays
    public static void addToStoredMessages(String message, String messageHash, String messageID, String recipient) {
        storedMessages[storedCount] = message;
        messageHashArray[hashCount] = messageHash;
        messageIDArray[hashCount] = messageID;
        recipientArray[hashCount] = recipient;
        storedCount++;
        hashCount++;
    }
 
  
    // Part 3 - Search and Display Methods
    
    //Displays the sender and recipient of all stored messages 
  public static void displaySenderRecipient(String senderName) {
    if (storedCount == 0) {
        System.out.println("No stored messages found.");
        return; 
    }
      System.out.println("=======Stored message senders and recipient=======");
    for(int i = 0 ; i < storedCount; i ++){
        int idx = sentCount + i;
        System.out.println("Sender     :" + senderName);
        System.out.println("Recipient :" + recipientArray[idx]);
        System.out.println("============================================");
        
    }
  }
    // Display the longest message from sent and stored messages        REF: https://www.w3schools.com/java/java_ref_arrays.asp
    public static void displayLongestMessage() {
        String longest = "";
 
        for (int i = 0; i < sentCount; i++) {                          // REF: https://www.w3schools.com/java/java_for_loop.asp
            if (sentMessages[i].length() > longest.length()) {
                longest = sentMessages[i];
            }
        }
 
        for (int i = 0; i < storedCount; i++) {                        // REF: https://www.w3schools.com/java/java_for_loop.asp
            if (storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
 
        if (longest.isEmpty()) {
            System.out.println("No messages found");
        } else{
            System.out.println("Longest message:"+ longest);
    }
    }
    
    // Search for a message by ID and return recipient and message      REF: https://www.w3schools.com/java/java_ref_arrays.asp
    public static void searchByMessageID(String searchID) {
        boolean found = false;
        for (int i = 0; i < hashCount; i++) {                          // REF: https://www.w3schools.com/java/java_for_loop.asp
            if (messageIDArray[i] != null && messageIDArray[i].equals(searchID)) {
                String msgText = "";
                if (i < sentCount) {
                    msgText = sentMessages[i];
                } else {
                    int storedIndex = i - sentCount;
                    if (storedIndex < storedCount) {
                        msgText = storedMessages[storedIndex];
                    }
                }
                System.out.println("Recipient :" + recipientArray[i]);
                System.out.println("message   :"  + msgText);
                found = true;
            }
        }
        if(!found){
            System.out.println("Message ID " + searchID + " not found.");
        }
    }
    // Search all messages for a particular recipient                   REF: https://www.w3schools.com/java/java_ref_arrays.asp
    public static void searchByRecipient(String searchRecipient) {
       
        int count = 0;
 
        for (int i = 0; i < hashCount; i++) {                          // REF: https://www.w3schools.com/java/java_for_loop.asp
            if (recipientArray[i] != null && recipientArray[i].equals(searchRecipient)) {
                String msgText = "";
                if (i < sentCount) {
                    msgText = sentMessages[i];
                } else {
                    int storedIndex = i - sentCount;
                    if (storedIndex < storedCount) {
                        msgText = storedMessages[storedIndex];
                    }
                }
                System.out.println("\"" + msgText + "\"");
                count++;
            }
        }
        if(count== 0){
            System.out.println("No message found for recipient:"+ searchRecipient);
        }
        }
        
    // Delete a message using message hash                              REF: https://www.w3schools.com/java/java_ref_arrays.asp
    public static void deleteMessageByHash(String hash) {
        boolean found = false;
        for (int i = 0; i < hashCount; i++) {                          // REF: https://www.w3schools.com/java/java_for_loop.asp
            if (messageHashArray[i] != null && messageHashArray[i].equals(hash)) {
                String deletedMessage = "";
 found = true;
 
                // Find and remove from the correct message array
                if (i < sentCount) {
                    deletedMessage = sentMessages[i];
                    // Shift sent array left
                    for (int j = i; j < sentCount - 1; j++) {          // REF: https://www.w3schools.com/java/java_for_loop.asp
                        sentMessages[j] = sentMessages[j + 1];
                    }
                    sentMessages[sentCount - 1] = null;
                    sentCount--;
                } else {
                    int storedIndex = i - sentCount;
                    if (storedIndex < storedCount) {
                        deletedMessage = storedMessages[storedIndex];
                        // Shift stored array left
                        for (int j = storedIndex; j < storedCount - 1; j++) { // REF: https://www.w3schools.com/java/java_for_loop.asp
                            storedMessages[j] = storedMessages[j + 1];
                        }
                        storedMessages[storedCount - 1] = null;
                        storedCount--;
                    }
                }
 
                // Shift parallel arrays left
                for (int j = i; j < hashCount - 1; j++) {             // REF: https://www.w3schools.com/java/java_for_loop.asp
                    messageHashArray[j] = messageHashArray[j + 1];
                    messageIDArray[j] = messageIDArray[j + 1];
                    recipientArray[j] = recipientArray[j + 1];
                }
                messageHashArray[hashCount - 1] = null;
                messageIDArray[hashCount - 1] = null;
                recipientArray[hashCount - 1] = null;
                hashCount--;
 
                System.out.println("Message: " + deletedMessage + "successfully deleted.");
            break;
            }
        }
 if(!found){
     System.out.println("Hash not found:"+ hash);
 }
    }
    // Display full report of all messages                              REF: https://www.w3schools.com/java/java_ref_arrays.asp
    public static void displayReport() {
        if (sentCount == 0 && storedCount == 0) {
            System.out.println("No messages to report.;"); 
            return;
        }
 
        System.out.println("=============================");
        System.out.println( " QUICKCHAT MESSAGE REPORT ");
        System.out.println("==============================");
        for (int i = 0; i < hashCount; i++) {                          // REF: https://www.w3schools.com/java/java_for_loop.asp
            String msgText = "";
            if (i < sentCount) {
                msgText = sentMessages[i];
            } else {
                int storedIndex = i - sentCount;
                if (storedIndex < storedCount) {
                    msgText = storedMessages[storedIndex];
                }
            }
            System.out.println("Message Hash : " + messageHashArray[i]); 
            System.out.println("Message ID     : " + messageIDArray[i]);
            System.out.println("Recipient        : " + recipientArray[i]);
            System.out.println("Message          : " + msgText);
            System.out.println("====================================");
                    }
    }
}
       