/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginregistrationpoe2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author William
 */
public class MessageP2 {
    //Declaration
      private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    //Constructor
    public static int totalMessagesSent = 0;
  public MessageP2(String messageID, int messageNumber, String recipient,
                       String message, String messageHash) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = messageHash;
    }

    public MessageP2(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }//end of constructor
    
//Randomly generates numbers
    private String generateMessageID() {
        Random rand = new Random();                                                               // REF:  https://youtu.be/-tt98ICTHtQ?si=dMhapn5FAZPSSUq
        long id = 1_000_000_000L + (long)(rand.nextDouble() * 9_000_000_000L);
        return String.valueOf(id);
    }
//Ensure message Id is not 10 character long
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }
//check Recipient number that it starts with +27 followed by 9 digits
    public String checkRecipientCell() {
        if ( recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully added.";                                                                             
        } else {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
    }

    public String createMessageHash() {
        String[] words = message.trim().split("");
        if (words.length == 0) return "00:0:EMPTY";
        String firstWord = words[0];
        String lastWord  = words[words.length - 1];
        String combined = (firstWord + lastWord).toUpperCase();
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + combined;
    }
//This is the sentMessage where it tell the user o disaplay a message saying they are message has been sent
    public String sentMessage(int choice) {
        switch (choice) {
            case 1: return "Message successfully sent.";
            case 2: return "Press 0 to delete the message.";
            case 3: return "Message successfully stored.";
            default: return "Invalid option.";
        }
    }

    public String printMessages() { // Displays the message
        return "---------------------------------\n"
                + "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + message + "\n"
                + "---------------------------------";
    }
//Return the total message sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
//Store message                                              
    public void storeMessageInJson() {
        try (FileWriter fw = new FileWriter("messages.json", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
                                                                                                                                       //https://www.w3schools.com/java/java_ref_bufferedwriter.asp
            String json = "{"                                                                                                //https://www.w3schools.com/java/java_ref_bufferedreader.asp
                    + "\"messageID\":\"" + escapeJson(messageID) + "\","
                    + "\"messageNumber\":" + messageNumber + ","
                    + "\"recipient\":\"" + escapeJson(recipient) + "\","
                    + "\"message\":\"" + escapeJson(message) + "\","
                    + "\"messageHash\":\"" + escapeJson(messageHash) + "\""
                    + "}";
            out.println(json);
        } catch (IOException e) {
            System.out.println("Error saving message to JSON: " + e.getMessage());
        }
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
