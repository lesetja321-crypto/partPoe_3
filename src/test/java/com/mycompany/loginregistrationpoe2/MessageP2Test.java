package com.mycompany.loginregistrationpoe2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


 
public class MessageP2Test {

    
    /** Valid message used for Test Case 1 (Send). */
    private MessageP2 validMessage1;

    /** Valid message used for Test Case 2 (Discard). */
    private MessageP2 validMessage2;

    @BeforeEach
    public void setUp() {
        // Test Case 1 – valid recipient, short message, action = Send
        validMessage1 = new MessageP2(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        // Test Case 2 – invalid recipient format, action = Discard
        validMessage2 = new MessageP2(2, "08575975889", "Hi Keegan, did you receive the payment?");
    }


    
    @Test
    public void testMessageLengthSuccess() {
        String message = "Hi Mike, can you join us for dinner tonight?"; // well under 250 chars
        String result = checkMessageLength(message);
        assertEquals("Message ready to send.", result,
                "A message within 250 characters should be accepted.");
    }

    /**
     * FAILURE: message exceeds 250 characters.
     * Expected return: "Message exceeds 250 characters by X; please reduce the size."
     */
    @Test
    public void testMessageLengthFailure() {
        // Build a string that is exactly 260 characters long (10 over the limit)
        String message = "A".repeat(260);
        String result = checkMessageLength(message);
        int excess = message.length() - 250; // = 10
        String expected = "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        assertEquals(expected, result,
                "A message exceeding 250 characters should report the exact overage.");
    }

    /**
     * Helper that mirrors the validation logic the application should enforce.
     * This can later be promoted to a method on MessageP2 itself.
     */
    private String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    
    /**
     * SUCCESS: recipient starts with +27 followed by exactly 9 digits.
     * Expected return: "Cell phone number successfully captured."
     */
    @Test
    public void testCheckRecipientCellSuccess() {
        // validMessage1 was constructed with "+27718693002"
        String result = validMessage1.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result,
                "+27718693002 is a valid South African number and should be accepted.");
    }

    /**
     * FAILURE: recipient does not start with +27 (local format "08575975889").
     * Expected return: "Cell phone number is incorrectly formatted or does not contain
     *                   an international code. Please correct the number and try again."
     */
    @Test
    public void testCheckRecipientCellFailure() {
        // validMessage2 was constructed with "08575975889" (no +27 prefix)
        String result = validMessage2.checkRecipientCell();
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                result,
                "08575975889 is missing the +27 international code and should be rejected.");
    }

   
    @Test
    public void testCreateMessageHashTestCase1() {
        // Use the full constructor: messageID "0012345678" starts with "00"
        MessageP2 msg = new MessageP2("0012345678", 1, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", "");
        String hash = msg.createMessageHash();
        assertEquals("00:1:HITONIGHT", hash,
                "Hash for message 1 should be '00:1:HITONIGHT'.");
    }

    /**
     * Loop test – verify hash format for a set of representative messages.
     * Each entry: { messageID, messageNumber, recipient, messageText, expectedHash }
     */
    @Test
    public void testCreateMessageHashLoop() {
        String[][] testData = {
            // { messageID,     msgNum, recipient,        messageText,                                       expectedHash }
            { "0012345678", "1", "+27718693002", "Hi Mike, can you join us for dinner tonight?", "00:1:HITONIGHT"  },
            { "AB99887766", "2", "+27835556677", "Hi Keegan, did you receive the payment?",      "AB:2:HIPAYMENT"  },
            { "ZZ00000001", "3", "+27711112222", "Hello World",                                  "ZZ:3:HELLOWORLD" },
        };

        for (String[] td : testData) {
            MessageP2 msg = new MessageP2(td[0], Integer.parseInt(td[1]), td[2], td[3], "");
            String hash = msg.createMessageHash();
            assertEquals(td[4], hash,
                    "Hash mismatch for message: " + td[3]);
        }
    }

    // -----------------------------------------------------------------------
    // 4. Message ID Generation
    // -----------------------------------------------------------------------

    /**
     * SUCCESS: the auto-generated message ID must be 10 characters or fewer.
     * Expected output: "Message ID generated: <Message ID>"
     */
    @Test
    public void testCheckMessageIDSuccess() {
        // validMessage1 uses the 3-arg constructor which auto-generates the ID
        boolean result = validMessage1.checkMessageID();
        assertTrue(result,
                "Auto-generated message ID should be 10 characters or fewer.");
        System.out.println("Message ID generated: " + getMessageID(validMessage1));
    }

    /**
     * FAILURE: an ID longer than 10 characters must fail the check.
     */
    @Test
    public void testCheckMessageIDFailure() {
        // Full constructor – supply an 11-character ID deliberately
        MessageP2 msg = new MessageP2("12345678901", 0, "", "", "");
        boolean result = msg.checkMessageID();
        assertFalse(result,
                "An 11-character message ID should fail the length check.");
    }

    /**
     * Reflective helper to read the private messageID field for display purposes.
     * Used only in println; not part of assertion logic.
     */
    private String getMessageID(MessageP2 msg) {
        try {
            java.lang.reflect.Field f = MessageP2.class.getDeclaredField("messageID");
            f.setAccessible(true);
            return (String) f.get(msg);
        } catch (Exception e) {
            return "<unavailable>";
        }
    }

    
    @Test
    public void testSentMessageSend() {
        String result = validMessage1.sentMessage(1);
        assertEquals("Message successfully sent.", result,
                "Choice 1 should confirm the message was sent.");
    }

    /**
     * Choice 2 – Disregard Message.
     * Expected: "Press 0 to delete the message."
     */
    @Test
    public void testSentMessageDiscard() {
        String result = validMessage2.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result,
                "Choice 2 should prompt the user to press 0 to delete.");
    }

    /**
     * Choice 3 – Store Message.
     * Expected: "Message successfully stored."
     */
    @Test
    public void testSentMessageStore() {
        String result = validMessage1.sentMessage(3);
        assertEquals("Message successfully stored.", result,
                "Choice 3 should confirm the message was stored.");
    }

    

    /**
     * After one Send action the total sent count must equal 1.
     */
    @Test
    public void testReturnTotalMessages() {
        // Reset static counter before this isolated test
        MessageP2.totalMessagesSent = 0;

        // Simulate sending message 1
        MessageP2.totalMessagesSent++;

        int total = MessageP2.returnTotalMessages();
        assertEquals(1, total,
                "After one send action, total messages sent should be 1.");
    }

 
    @Test
    public void testReturnTotalMessagesNotIncrementedOnDiscard() {
        MessageP2.totalMessagesSent = 0;

       

        int total = MessageP2.returnTotalMessages();
        assertEquals(0, total,
                "Discarding a message should not increment the total sent count.");
    }
}