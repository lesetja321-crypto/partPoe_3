package com.mycompany.loginregistrationpoe2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Part 3 Unit Tests 
public class MessageManagerTest {

    private MessagePoe msg1; // Sent     
    private MessagePoe msg2; // Stored   
    private MessagePoe msg3; // Disregard
    private MessagePoe msg4; // Sent     
    private MessagePoe msg5; // Stored 

    @BeforeEach
    public void setUp() {

        MessageManagerPoe.sentMessages = new String[100];
        MessageManagerPoe.disregardedMessages = new String[100];
       MessageManagerPoe.storedMessages = new String[100];
        MessageManagerPoe.messageHashArray = new String[100];
        MessageManagerPoe.messageIDArray = new String[100];
        MessageManagerPoe.recipientArray = new String[100];
        MessageManagerPoe.sentCount = 0;
        MessageManagerPoe.disregardedCount = 0;
        MessageManagerPoe.storedCount = 0;
        MessageManagerPoe.hashCount = 0;
        MessagePoe.totalMessagesSent = 0;

        msg1 = new MessagePoe(1, "+27834557896", "Did you get the cake?");
        msg2 = new MessagePoe(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg3 = new MessagePoe(3, "+27834484567", "Yohoooo, I am at your gate.");
        msg4 = new MessagePoe(4, "+27838884567", "It is dinner time!"); // 0838884567 normalized to +27 format for regex
        msg5 = new MessagePoe(5, "+27838884567", "Ok, I am leaving without you.");

        //Populate arrays exactly as the application would
        // Sent: msg1, msg4
        MessageManagerPoe.addToSentMessages(msg1.getMessage(), msg1.getMessageHash(), msg1.getMessageID(), msg1.getRecipient());
        MessageManagerPoe.addToSentMessages(msg4.getMessage(), msg4.getMessageHash(), msg4.getMessageID(), msg4.getRecipient());

        // Stored: msg2, msg5
        MessageManagerPoe.addToStoredMessages(msg2.getMessage(), msg2.getMessageHash(), msg2.getMessageID(), msg2.getRecipient());
        MessageManagerPoe.addToStoredMessages(msg5.getMessage(), msg5.getMessageHash(), msg5.getMessageID(), msg5.getRecipient());

        // Disregarded: msg3
        MessageManagerPoe.addToDisregardedMessages(msg3.getMessage());
    }
    //Arrays that populates

    // The Sent Messages array contains the expected test data. 
    @Test
    public void testSentMessagesArrayPopulatedCorrectly() {
        assertEquals(2, MessageManagerPoe.sentCount);
        assertEquals("Did you get the cake?", MessageManagerPoe.sentMessages[0]);
        assertEquals("It is dinner time!", MessageManagerPoe.sentMessages[1]);
    }

    // The Stored Messages array contains the expected test data. 
    @Test
    public void testStoredMessagesArrayPopulatedCorrectly() {
        assertEquals(2, MessageManagerPoe.storedCount);
        assertEquals("Where are you? You are late! I have asked you to be on time.", MessageManagerPoe.storedMessages[0]);
        assertEquals("Ok, I am leaving without you.", MessageManagerPoe.storedMessages[1]);
    }

    //The Disregarded Messages array contains the expected test data. 
    @Test
    public void testDisregardedMessagesArrayPopulatedCorrectly() {
        assertEquals(1, MessageManagerPoe.disregardedCount);
        assertEquals("Yohoooo, I am at your gate.", MessageManagerPoe.disregardedMessages[0]);
    }

    // The Message Hash and Message ID parallel arrays are populated for every sent/stored message. 
    @Test
    public void testHashAndIDArraysPopulatedCorrectly() {
        assertEquals(4, MessageManagerPoe.hashCount);
        for (int i = 0; i < MessageManagerPoe.hashCount; i++) {
            assertNotNull(MessageManagerPoe.messageHashArray[i], "Hash at index " + i + " should not be null");
            assertNotNull(MessageManagerPoe.messageIDArray[i], "ID at index " + i + " should not be null");
            assertNotNull(MessageManagerPoe.recipientArray[i], "Recipient at index " + i + " should not be null");
        }
    }

    // output  the longest message
    // "Where are you? You are late! I have asked you to be on time." is the longest.
    @Test
    public void testDisplayLongestMessage() {
        String longest = "";

        for (int i = 0; i < MessageManagerPoe.sentCount; i++) {
            if (MessageManagerPoe.sentMessages[i].length() > longest.length()) {
                longest = MessageManagerPoe.sentMessages[i];
            }
        }
        for (int i = 0; i < MessageManagerPoe.storedCount; i++) {
            if (MessageManagerPoe.storedMessages[i].length() > longest.length()) {
                longest = MessageManagerPoe.storedMessages[i];
            }
        }

        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    // 3. SEARCH FOR A MESSAGE ID 
    //Searching for the message ID of msg4 ("It is dinner time!") returns the correct recipient and message text.
    @Test
    public void testSearchByMessageID_Found() {
        String searchID = msg4.getMessageID();

        boolean found = false;
        String foundRecipient = "";
        String foundMessage = "";

        for (int i = 0; i < MessageManagerPoe.hashCount; i++) {
            if (MessageManagerPoe.messageIDArray[i].equals(searchID)) {
                found = true;
                foundRecipient = MessageManagerPoe.recipientArray[i];
                foundMessage = (i < MessageManagerPoe.sentCount)
                        ? MessageManagerPoe.sentMessages[i]
                        : MessageManagerPoe.storedMessages[i - MessageManagerPoe.sentCount];
            }
        }

        assertTrue(found, "Message ID should be found");
        assertEquals("+27838884567", foundRecipient);
        assertEquals("It is dinner time!", foundMessage);
    }

    //Searching for a message ID that does not exist returns "not found". 
    @Test
    public void testSearchByMessageID_NotFound() {
        String searchID = "0000000000";

        boolean found = false;
        for (int i = 0; i < MessageManagerPoe.hashCount; i++) {
            if (MessageManagerPoe.messageIDArray[i].equals(searchID)) {
                found = true;
            }
        }

        assertFalse(found, "Message ID should NOT be found");
    }

    //4. search all messages for a particular recipient
    //Searching for recipient +27838884567 returns both the sent message  "It is dinner time!" and the two stored messages for that recipient. 
    @Test
    public void testSearchByRecipient_MultipleMatches() {
        String searchRecipient = "+27838884567";
        int count = 0;

        for (int i = 0; i < MessageManagerPoe.hashCount; i++) {
            if (MessageManagerPoe.recipientArray[i].equals(searchRecipient)) {
                count++;
            }
        }
        assertEquals(3, count);
    }

    //searching for a recipient with no messages returns zero matches. 
    @Test
    public void testSearchByRecipient_NoMatches() {
        String searchRecipient = "+27000000000";
        int count = 0;

        for (int i = 0; i < MessageManagerPoe.hashCount; i++) {
            if (MessageManagerPoe.recipientArray[i].equals(searchRecipient)) {
                count++;
            }
        }

        assertEquals(0, count);
    }

    //5.Delete a message using a message hash
    // Deleting msg2's hash "Where are you? ..." removes it from the stored array and shifts the parallel arrays correctly.
    @Test
    public void testDeleteMessageByHash_RemovesStoredMessage() {
        String hashToDelete = msg2.getMessageHash();
        int beforeStoredCount = MessageManagerPoe.storedCount;
        int beforeHashCount = MessageManagerPoe.hashCount;

        MessageManagerPoe.deleteMessageByHash(hashToDelete);

        assertEquals(beforeStoredCount - 1, MessageManagerPoe.storedCount);
        assertEquals(beforeHashCount - 1, MessageManagerPoe.hashCount);

        // Confirm the hash no longer exists anywhere in the array
        for (int i = 0; i < MessageManagerPoe.hashCount; i++) {
            assertNotEquals(hashToDelete, MessageManagerPoe.messageHashArray[i]);
        }

        // Confirm the message text is gone from storedMessages
        for (int i = 0; i < MessageManagerPoe.storedCount; i++) {
            assertNotEquals("Where are you? You are late! I have asked you to be on time.",
                    MessageManagerPoe.storedMessages[i]);
        }
    }

    // Deleting msg1's hash "Did you get the cake?" removes it from the sent array and shifts the parallel arrays correctly.
    @Test
    public void testDeleteMessageByHash_RemovesSentMessage() {
        String hashToDelete = msg1.getMessageHash();
        int beforeSentCount = MessageManagerPoe.sentCount;
        int beforeHashCount = MessageManagerPoe.hashCount;

        MessageManagerPoe.deleteMessageByHash(hashToDelete);

        assertEquals(beforeSentCount - 1, MessageManagerPoe.sentCount);
        assertEquals(beforeHashCount - 1, MessageManagerPoe.hashCount);

        for (int i = 0; i < MessageManagerPoe.sentCount; i++) {
            assertNotEquals("Did you get the cake?", MessageManagerPoe.sentMessages[i]);
        }
    }

    //Attempting to delete a hash that does not exist leaves all arrays unchanged.
    @Test
    public void testDeleteMessageByHash_HashNotFound() {
        int beforeSentCount = MessageManagerPoe.sentCount;
        int beforeStoredCount = MessageManagerPoe.storedCount;
        int beforeHashCount = MessageManagerPoe.hashCount;

        MessageManagerPoe.deleteMessageByHash("ZZ:9:NOTREAL");

        assertEquals(beforeSentCount, MessageManagerPoe.sentCount);
        assertEquals(beforeStoredCount, MessageManagerPoe.storedCount);
        assertEquals(beforeHashCount, MessageManagerPoe.hashCount);
    }

    // 6. Display report 
    //The report covers every sent and stored message, and each entry has a non-null hash, ID, recipient, and message text.
    @Test
    public void testDisplayReport_CoversAllMessages() {
        // 2 sent + 2 stored = 4 total entries expected in the report
        assertEquals(4, MessageManagerPoe.hashCount);

        for (int i = 0; i < MessageManagerPoe.hashCount; i++) {
            assertNotNull(MessageManagerPoe.messageHashArray[i]);
            assertNotNull(MessageManagerPoe.messageIDArray[i]);
            assertNotNull(MessageManagerPoe.recipientArray[i]);

            // Message text should resolve from either sent or stored arrays
            String msgText = (i < MessageManagerPoe.sentCount)
                    ? MessageManagerPoe.sentMessages[i]
                    : MessageManagerPoe.storedMessages[i - MessageManagerPoe.sentCount];
            assertNotNull(msgText);
            assertFalse(msgText.isEmpty());
        }

        // displayReport() itself should run without throwing for non-empty arrays
        MessageManagerPoe.displayReport();
    }

    // displayReport() handles the empty array case without throwing.
    @Test
    public void testDisplayReport_EmptyArrays() {
        // Reset to simulate no messages at all
        MessageManagerPoe.sentCount = 0;
        MessageManagerPoe.storedCount = 0;
        MessageManagerPoe.hashCount = 0;

        // Should print "No messages to report." and return without error
        MessageManagerPoe.displayReport();

        assertEquals(0, MessageManagerPoe.sentCount);
        assertEquals(0, MessageManagerPoe.storedCount);
    }
}
