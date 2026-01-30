package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MessageCenter extends Application {
    private List<Message> nurseMessages = new ArrayList<>();
    private List<Message> doctorMessages = new ArrayList<>();
    private ListView<String> messageList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the ListView to display messages
        messageList = new ListView<>();
        messageList.setPrefSize(400, 300);

        // Create a VBox to hold the ListView
        VBox vbox = new VBox(messageList);
        vbox.setPadding(new Insets(10));

        // Set up the scene
        Scene scene = new Scene(vbox, 400, 300);

        // Set the stage title
        primaryStage.setTitle("Message Center");

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public void showMessageCenter() {
        // Clear the message list
        messageList.getItems().clear();

        // Iterate over nurse messages and add them to the list
        for (Message message : nurseMessages) {
            String item = "From: " + message.getSender() + "\n" +
                          "To: Nurse\n" +
                          "Subject: " + message.getSubject() + "\n" +
                          "Content: " + message.getContent();
            messageList.getItems().add(item);
        }

        // Iterate over doctor messages and add them to the list
        for (Message message : doctorMessages) {
            String item = "From: " + message.getSender() + "\n" +
                          "To: Doctor\n" +
                          "Subject: " + message.getSubject() + "\n" +
                          "Content: " + message.getContent();
            messageList.getItems().add(item);
        }
    }

    public void sendMessageToNurse(String sender, String subject, String content) {
        Message message = new Message(sender, "Nurse", subject, content);
        nurseMessages.add(message);
    }

    public void sendMessageToDoctor(String sender, String subject, String content) {
        Message message = new Message(sender, "Doctor", subject, content);
        doctorMessages.add(message);
    }

    public List<Message> getNurseMessages() {
        return nurseMessages;
    }

    public List<Message> getDoctorMessages() {
        return doctorMessages;
    }

    public static class Message {
        private String sender;
        private String receiver;
        private String subject;
        private String content;

        public Message(String sender, String receiver, String subject, String content) {
            this.sender = sender;
            this.receiver = receiver;
            this.subject = subject;
            this.content = content;
        }

        public String getSender() {
            return sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public String getSubject() {
            return subject;
        }

        public String getContent() {
            return content;
        }
    }
}