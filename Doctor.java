package application; //please uncomment this as necessary!

//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/*
 * create a scene for each screen
 * and change the scene that gets displayed on the stage when different buttons are clicked
 */
public class Doctor extends Login
{
    //background color: #fbe493 || 251, 226,143
    Scene welcomeScreen, searchPatientScreen, messageCenterScreen;
    Scene searchTest, searchPrescription, searchHistory;
    @Override
    public void start(Stage stage) //starting scene is the doctor's homepage
    {
        //SCENE 1: WELCOMESCREEN
        //logo
        ImageView logo1 = new ImageView(new Image("https://drive.google.com/uc?id=1erBoFkcdqvkQIENMmP6cN0zInW_H7ubX"));
        logo1.setFitWidth(200);
        logo1.setPreserveRatio(true);
        logo1.setSmooth(true);
        //label
        Label welcome = new Label("Welcome Doctor!");
        
        //search patient
        ImageView searchPatientLogo = new ImageView(new Image("https://drive.google.com/uc?id=1zOKxSDAkotY-CB0bprPktgS2tYA2z9vW"));
        searchPatientLogo.setFitWidth(100);
        searchPatientLogo.setPreserveRatio(true);
        searchPatientLogo.setSmooth(true);
        Button searchPatientButton = new Button(" Search Patient ");
        searchPatientButton.setOnAction(e -> stage.setScene(searchPatientScreen));
        
        //message center
        ImageView messageCenterLogo = new ImageView(new Image("https://drive.google.com/uc?id=1xeMwxwBVtNnH6GRWMGD8cR0d4vNwZiXs"));
        messageCenterLogo.setFitWidth(100);
        messageCenterLogo.setPreserveRatio(true);
        messageCenterLogo.setSmooth(true);
        Button messageCenterButton = new Button("Message Center");
        messageCenterButton.setOnAction(e -> stage.setScene(messageCenterScreen));
        
        //create the boxes (panes/etc) and their dimensions here
        GridPane gridpane1 = new GridPane();
        gridpane1.setHgap(10);
        gridpane1.setVgap(5);
        gridpane1.setPadding(new Insets(10, 10, 10, 10)); //top, right, bottom, left
        gridpane1.setAlignment(Pos.CENTER);
        VBox vbox1 = new VBox(10);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setStyle("-fx-background-color: #fbe493");
        
        //add stuff to boxes
        gridpane1.add(searchPatientLogo, 0, 0); //col, row
        gridpane1.add(messageCenterLogo, 1, 0);
        gridpane1.add(searchPatientButton, 0, 1);
        gridpane1.add(messageCenterButton, 1, 1);
        vbox1.getChildren().addAll(logo1, welcome, gridpane1);
        welcomeScreen = new Scene(vbox1, 500, 500); //width, height     
        
        //SCENE2: SEARCHPATIENTSCREEN
        ImageView logo2 = new ImageView(new Image("https://drive.google.com/uc?id=1erBoFkcdqvkQIENMmP6cN0zInW_H7ubX"));
        logo2.setFitWidth(200);
        logo2.setPreserveRatio(true);
        logo2.setSmooth(true);
        Label find = new Label("Find your patient:");
        
        //search box here
        Button searchNext = new Button("Next");
        searchNext.setOnAction(e -> stage.setScene(searchTest));
        VBox vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setStyle("-fx-background-color: #fbe493");
        vbox2.getChildren().addAll(logo2, find, searchNext);
        searchPatientScreen = new Scene(vbox2, 500, 500);
        
        //SCENE2.1: SEARCHTEST
        Label test = new Label("Test Results & Notes");
        TextField results = new TextField("Any results or findings");
        Button testNext = new Button("Next");
        testNext.setOnAction(e -> stage.setScene(searchPrescription));
        VBox vbox3 = new VBox(10);
        vbox3.setAlignment(Pos.CENTER);
        vbox3.setStyle("-fx-background-color: #fbe493");
        vbox3.getChildren().addAll(test, results, testNext);
        searchTest = new Scene(vbox3, 500, 500);
        
        //SCENE2.2: SEARCHPRESCRIPTION
        Label meds = new Label("Prescription Medicines");
        TextField medField = new TextField();
        Button prescNext = new Button("Next");
        prescNext.setOnAction(e -> stage.setScene(searchHistory));
        VBox vbox4 = new VBox(10);
        vbox4.setAlignment(Pos.CENTER);
        vbox4.setStyle("-fx-background-color: #fbe493");
        vbox4.getChildren().addAll(meds, medField, prescNext);
        searchPrescription = new Scene(vbox4, 500, 500);
        
        //SCENE2.3: SEARCHHISTORY
        TextField hist = new TextField("Medical History");
        Button histNext = new Button("Next");
        VBox vbox5 = new VBox(10);
        vbox5.setAlignment(Pos.CENTER);
        vbox5.setStyle("-fx-background-color: #fbe493");
        vbox5.getChildren().addAll(hist, histNext);
        searchHistory = new Scene(vbox5, 500, 500);
        
        //SCENE3: MESSAGECENTERSCREEN
        Label message = new Label("Message");
        //search box here
        TextField messageCenter = new TextField();
        Button newMessage = new Button("New Message");
        VBox vbox6 = new VBox(10);
        vbox6.setAlignment(Pos.CENTER);
        vbox6.setStyle("-fx-background-color: #fbe493");
        vbox6.getChildren().addAll(message, messageCenter, newMessage);
        messageCenterScreen = new Scene(vbox6, 500, 500);
        
        //set up display  
        stage.setTitle("Doctor View");
        stage.setScene(welcomeScreen);
        stage.show();
    }   
}
