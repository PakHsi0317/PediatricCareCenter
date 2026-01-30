package application;

import javafx.scene.text.Font;
//import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.nio.file.*;
import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class NurseView extends Login {
	VBox homepage;
	HBox homeButtons;
	VBox searchPage;
	VBox agreementPage;
	VBox historyPage;
	VBox messagePage;

	ImageView logo = new ImageView(new Image("https://drive.google.com/uc?id=1erBoFkcdqvkQIENMmP6cN0zInW_H7ubX"));

	Button searchPatient = new Button("Search Patient");
	Button messageCenter = new Button("Message Center");
	Button searchNext = new Button("Next");
	Button agreeNext = new Button("Next");
	Button infoNext = new Button("Next");
	Button medSave = new Button("Save");
	
	Button searchBack = new Button("Back");
	Button agreeBack = new Button("Back");
	Button infoBack = new Button("Back");
	Button medBack = new Button("Back");

	TextField weight;
	TextField height;
	TextField temperature;
	TextField bp;
	TextArea notes;
	
	Stage searchStage;
	Stage agreementStage;
	Stage infoStage;
	Stage medStage;
	
	String patientID;
	String patientName;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Nurse View");
		
			GridPane homepage = new GridPane();
			homepage.setHgap(10);
			homepage.setVgap(5);
			homepage.setPadding(new Insets(10, 10, 10, 10)); //top, right, bottom, left
			homepage.setAlignment(Pos.CENTER);
			
			ImageView logoImageView = new ImageView(new Image("https://drive.google.com/uc?id=1erBoFkcdqvkQIENMmP6cN0zInW_H7ubX"));
			logoImageView.setFitWidth(200);
			logoImageView.setPreserveRatio(true);
			logoImageView.setSmooth(true);

			ImageView patientLogo = new ImageView(new Image("https://drive.google.com/uc?id=1zOKxSDAkotY-CB0bprPktgS2tYA2z9vW"));
			patientLogo.setFitWidth(100);
			patientLogo.setPreserveRatio(true);
			patientLogo.setSmooth(true);

			ImageView envelope = new ImageView(new Image("https://drive.google.com/uc?id=1xeMwxwBVtNnH6GRWMGD8cR0d4vNwZiXs"));
			envelope.setFitWidth(100);
			envelope.setPreserveRatio(true);
			envelope.setSmooth(true);

			Label welcomeLabel = new Label("Welcome Nurse!");
			welcomeLabel.setStyle("-fx-text-fill: #384e6b;");
			
			Button searchPatient = new Button("Search Patient");
			searchPatient.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
			
			Button messageCenter = new Button("Message Center");
			messageCenter.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
			
			VBox vbox = new VBox(10);
	        vbox.setAlignment(Pos.CENTER);
	        vbox.setStyle("-fx-background-color: #fbe493");
	        
	        homepage.add(patientLogo, 0, 0); 
	        homepage.add(envelope, 1, 0);
	        homepage.add(searchPatient, 0, 1);
	        homepage.add(messageCenter, 1, 1);
	        
	        vbox.getChildren().addAll(logoImageView, welcomeLabel, homepage);
	        Scene scene = new Scene(vbox, 500, 500); //width, height 
			primaryStage.setScene(scene);
			primaryStage.show();
			
			searchPatient.setOnAction(e -> {
				searchPatient(primaryStage);
			});
			
			messageCenter.setOnAction(e -> {
				openMessageCenter(primaryStage);
			});
	}

	private void searchPatient(Stage primaryStage) {
		searchPage = new VBox();
		searchPage.setPrefWidth(600);
		searchPage.setPrefHeight(600); 
		
		Button find = new Button("Find");
		
		ImageView logo = new ImageView(new Image("https://drive.google.com/uc?id=1erBoFkcdqvkQIENMmP6cN0zInW_H7ubX"));
		logo.setFitWidth(200);
		logo.setPreserveRatio(true);
		logo.setSmooth(true);
		searchPage.getChildren().add(logo);
		searchPage.setStyle("-fx-background-color: #fbe493;");
		
		TextField bar = new TextField();
		bar.setPromptText("🔍 Find your patient");
		bar.setMaxWidth(200);
	    
		searchPage.getChildren().addAll(new Label("Find your patient:"), bar, find);
		searchPage.getChildren().addAll(searchNext,searchBack);
		searchPage.setSpacing(30);
		searchPage.setPadding(new Insets(80, 0, 0, 0));
		searchPage.setAlignment(Pos.TOP_CENTER);
		find.setOnAction(e -> {
							patientID = bar.getText(); 
							findPatient(patientID);
		});

		searchBack.setOnAction(e -> goHomePortal(primaryStage));
		
		searchNext.setOnAction(e -> {
		    patientID = bar.getText();
		    if (patientID.isEmpty()) {
		        // Display an error message or take appropriate action
		        showErrorMessage("Please enter a patient ID.");
		    } else {
		    	findPatient(patientID);
		        patientAgreement(primaryStage, patientID);
		    }
		});
		
		Scene searchScene = new Scene(searchPage, 500, 600);
		primaryStage.setScene(searchScene);
		primaryStage.show();
	}
	
	private void findPatient(String patientID) {
	    String fileName = patientID + "_PersonalInfo.txt";
	    Path filePath = Paths.get(fileName);
	    boolean fileExists = Files.exists(filePath);
	    
	    if (fileExists) {
	        try {
	            // Read the content of the file
	            List<String> lines = Files.readAllLines(filePath);
	            
	            if (!lines.isEmpty()) {
	                // Extract the patient name from the first line
	                patientName = lines.get(0);
	                
	                // Update the searchResultLabel with the patient name
	                showSuccessMessage("Patient record found for ID: " + patientID + "\n" + patientName);
	            } else {
	            	showErrorMessage("Patient record found for ID: " + patientID + "\nNo patient name found in the file.");
	            }
	        } catch (IOException e) {
	        	showErrorMessage("Error reading the file.");
	            e.printStackTrace();
	        }
	    } else {
	    	showErrorMessage("Patient record not found for ID: " + patientID);
	    }
	}

	private void patientAgreement(Stage primaryStage, String patientID) {
		agreementPage = new VBox();
		agreementPage.setPrefWidth(600);
		agreementPage.setPrefHeight(600); 
		
		CheckBox check = new CheckBox();
		HBox group = new HBox();
		group.getChildren().addAll(check, new Label("I agree that the patient is over 12"));
		
		ImageView logo = new ImageView(new Image("https://drive.google.com/uc?id=1erBoFkcdqvkQIENMmP6cN0zInW_H7ubX"));
		logo.setFitWidth(300);
		logo.setPreserveRatio(true);
		logo.setSmooth(true);
		
		agreeBack.setPrefWidth(100);
		agreeBack.setPrefHeight(10); 
		
		agreeNext.setPrefWidth(100);
		agreeNext.setPrefHeight(10); 
		
		agreementPage.getChildren().addAll(logo, group);
		agreementPage.getChildren().addAll(new VBox(agreeNext,agreeBack));
		searchPage.setSpacing(30);
		searchPage.setPadding(new Insets(80, 0, 0, 0));
		searchPage.setAlignment(Pos.TOP_CENTER);
		agreementPage.setStyle("-fx-background-color: #fbe493;");
	
		agreeBack.setOnAction(e -> searchPatient(primaryStage));
		agreeNext.setOnAction(e -> patientInfo(check.isSelected(), primaryStage, patientID));
		
		Scene agreementScene = new Scene(agreementPage, 400, 400);
		primaryStage.setScene(agreementScene);
		primaryStage.show();
	}

	private void patientInfo(boolean oldEnough, Stage primaryStage, String patientID ) {
		notes = new TextArea();

		GridPane nurseGrid = new GridPane();
		nurseGrid.setAlignment(Pos.CENTER);
		nurseGrid.setHgap(10);
		nurseGrid.setVgap(10);
		nurseGrid.setPadding(new Insets(20));
		
		DatePicker datePicker = new DatePicker();
		datePicker.setPrefWidth(250);
		datePicker.setPrefHeight(10);
		
		Label infoTitle = new Label("Patient Info");
		infoTitle.setFont(new Font("Arial", 30));
		weight = new TextField();
		height = new TextField();
		temperature = new TextField();
		bp = new TextField();
		
		Label noteLabel = new Label("Note:");
		Label weightLabel = new Label("Weight:");
		Label heightLabel = new Label("Height:");
		Label temLabel = new Label("Body Temperature:");
		Label bpLabel = new Label("Blood Pressure:");
		
		infoNext.setPrefWidth(100);
		infoNext.setPrefHeight(10); 
		
		infoBack.setPrefWidth(100);
		infoBack.setPrefHeight(10); 
		
		nurseGrid.add(infoTitle, 1, 0);
		nurseGrid.add(datePicker, 1, 1);
		nurseGrid.add(weightLabel, 1, 2);
		nurseGrid.add(heightLabel, 1, 3);
		nurseGrid.add(temLabel, 1, 4);
		nurseGrid.add(bpLabel, 1, 5);
		nurseGrid.add(weight, 2, 2);
		nurseGrid.add(height, 2, 3);
		nurseGrid.add(temperature, 2, 4);
		nurseGrid.add(bp, 2, 5);
		nurseGrid.add(noteLabel, 1, 6);
		nurseGrid.add(notes, 1, 7);
		nurseGrid.add(infoNext, 2, 8);
		nurseGrid.add(infoBack, 2, 9);
		
		if (oldEnough) {
			bp.setEditable(oldEnough);
		} else {
			bp.setPromptText("N/A");
			bp.setEditable(false);
		}
		
		infoBack.setOnAction(e -> patientAgreement(primaryStage, patientID));
		
		infoNext.setOnAction(e -> {
			if (datePicker == null || weight == null || height == null || temperature == null || notes == null) {
		        showErrorMessage("Please fill out blanks for patient ID: " + patientID);
			}
			
			bp.setEditable(true);
			LocalDate selectedDate = datePicker.getValue();
			String Weight = weight.getText();
			String Height = height.getText();
			String Temperature = temperature.getText();
			String Pressure = bp.getText();
			    
			String PatientName = patientName.substring(11);
			String fileName = selectedDate.toString() + "_" + PatientName + "_PatientInfo.txt";
			String content = "Date: " + selectedDate + "\nWeight: " + Weight + "\nHeight: " + Height
			                     + "\nTemperature: " + Temperature + "\nBlood Pressure: " + Pressure;
	
			saveToFile(fileName, content);
			medicalHistory(oldEnough, primaryStage, patientID);
			});
		
		StackPane stackPane = new StackPane(nurseGrid);
        stackPane.setPrefSize(800, 450);

        // Set the background color
        stackPane.setStyle("-fx-background-color: #fbe493;");

        // Create the scene and set it on the stage
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.show();;
	}

	private void medicalHistory(boolean oldEnough, Stage primaryStage, String patientIDm ) {
		medSave.setOnAction(e -> goHomePortal(primaryStage));
		medBack.setOnAction(e -> patientInfo(oldEnough, primaryStage, patientID));
		
		TableView<String> table = new TableView<String>();
		 try {
		        String fileName = patientID + "_Result.txt";
		        Path filePath = Paths.get(fileName);
		        
		        // Read the content of the file
		        List<String> lines = Files.readAllLines(filePath);

		        for (String line : lines) {
		        	table.getItems().add(line);
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		  
		  Scene scene = new Scene(table);
	      primaryStage.setScene(scene);
	      primaryStage.show();;
	}

	private void openMessageCenter(Stage primaryStage) {
		// Create and show the message center window
		messageScreen();
	}

	private void messageScreen() {
		// Create the message screen layout
		VBox messageLayout = new VBox();
		messageLayout.setSpacing(10);
		messageLayout.setPadding(new Insets(10));

		 // Create a list view to display the message history
		 ListView<String> messageHistory = new ListView<>();
		 messageHistory.setPrefHeight(400);

		 // Create a text area for composing new messages
		 TextArea messageInput = new TextArea();
		 messageInput.setPrefHeight(100);
		 messageInput.setWrapText(true);

		        // Create buttons for sending and clearing messages
		        Button sendButton = new Button("Send");
		        Button clearButton = new Button("Clear");

		        // Handle the send button click event
		        sendButton.setOnAction(e -> {
			        String message = messageInput.getText();
			        // Update the message history with the sent message
			        messageHistory.getItems().add("Sent: " + message);
	
			        // Clear the message input area
			         messageInput.clear();
		        });

		        // Handle the clear button click event
		        clearButton.setOnAction(e -> {
		            // Clear the message input area
		            messageInput.clear();
		        });

		        // Add the UI elements to the message layout
		        messageLayout.getChildren().addAll(messageHistory, messageInput, new HBox(sendButton, clearButton));

		        // Create a new stage for the message screen
		        Stage messageStage = new Stage();
		        messageStage.setTitle("Message Center");
		        messageStage.setScene(new Scene(messageLayout, 600, 500));
		        messageStage.show();
		    }
	
	private void goHomePortal(Stage primaryStage) {
		start(primaryStage);
	}
	
	private void saveToFile(String fileName, String content) {
	    try {
	    	Path filePath = Paths.get(fileName);

	        // Create the file if it doesn't exist
	        if (!Files.exists(filePath)) {
	            Files.createFile(filePath);
	        }
	        Files.write(Paths.get(fileName), content.getBytes());
	        showSuccessMessage("File saved successfully.");
	    } catch (IOException e) {
	    	 e.printStackTrace();
	    	showErrorMessage("An error occurred while saving the file.");
	    }
	}
	
	private void showErrorMessage(String message) {
		 Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText(null);
		    alert.setContentText(message);
		    alert.showAndWait();
	}
	    
	 private void showSuccessMessage(String message) {
		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Success");
		    alert.setHeaderText(null);
		    alert.setContentText(message);
		    alert.showAndWait();
	}
}