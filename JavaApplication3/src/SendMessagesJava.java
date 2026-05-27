import java.util.Scanner;

// Main class (only ONE public class allowed)
public class SendMessagesJava {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Get user input
        System.out.print("Enter recipient (+27XXXXXXXXX): ");
        String cell = input.nextLine();

        System.out.print("Enter your message: ");
        String text = input.nextLine();

        // Create message object
        Message msg = new Message(cell, text);

        // Validate and display
        System.out.println(msg.checkRecipientCell());

        if (msg.checkMessage()) {
            System.out.println(msg.sendMessage());
        } else {
            System.out.println("Message is invalid (empty or too long)");
        }

        // Print message details
        System.out.println("\n--- Message Details ---");
        System.out.println(msg.printMessage());

        input.close();
    }
}

// Message class (NOT public)
class Message {
    private static int totalMessages = 0;

    private final String messageID;
    private final int messageNumber;
    private final String recipientCell;
    private final String messageContent;
    private final String messageHash;
    private String status;

    public Message(String recipientCell, String messageContent) {
        this.messageNumber = ++totalMessages;
        this.recipientCell = recipientCell != null ? recipientCell : "";
        this.messageContent = messageContent != null ? messageContent : "";
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.status = "Stored";
    }

    public boolean checkMessage() {
        return !messageContent.trim().isEmpty() &&
               messageContent.length() <= 250;
    }

    public boolean isValidRecipientCell() {
        return recipientCell.matches("\\+27\\d{9}");
    }

    public String checkRecipientCell() {
        return isValidRecipientCell()
                ? "Cell phone number successfully captured"
                : "Cell phone number is incorrectly formatted";
    }

    public String createMessageHash() {
        if (messageContent.trim().isEmpty()) {
            return messageID.substring(0, 2) + ":" + messageNumber + ":EMPTY";
        }

        String[] words = messageContent.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (messageID.substring(0, 2) + ":" + messageNumber + ":" +
                firstWord + lastWord).toUpperCase();
    }

    public String sendMessage() {
        this.status = "Sent";
        return "Message successfully sent";
    }

    public String printMessage() {
        return "MessageID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipientCell +
               "\nMessage: " + messageContent +
               "\nStatus: " + status;
    }

    private String generateMessageID() {
        long number = (long)(Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(number);
    }
}