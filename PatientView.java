package application;

//import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import java.io.*;
//import java.time.LocalDate;
//import java.util.Map;

public class PatientView extends Login{
	
	private TextField fullNameField;
	private TextField dateOfBirthField;
	private TextField phoneNumberField;
	private TextField emailAddressField;
	private PasswordField newPasswordField;
	private PasswordField passwordConfirmationField;
    
    public void start(Stage primaryStage, String patientID) {
        primaryStage.setTitle("Patient View");
        
        GridPane patientGrid = new GridPane();
        patientGrid.setAlignment(Pos.CENTER);
        patientGrid.setHgap(10);
        patientGrid.setVgap(10);
        patientGrid.setPadding(new Insets(20));
        
        String filePath = patientID + "_Schedule.txt";
        String patientMemo = readFromFile(filePath);

        Text myPage = new Text("My Page");
        myPage.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 25px;");
        Text welcome = new Text("Welcome!");
        welcome.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 15px;");
        patientGrid.add(myPage, 0, 0);
        patientGrid.add(welcome, 0, 1);
        
        Button emailButton = new Button("Email");
        emailButton.setPrefWidth(50);
        emailButton.setPrefHeight(50);
        patientGrid.add(emailButton, 1, 0);
        
        TextArea textArea = new TextArea(patientMemo);
        textArea.setPrefWidth(250);
        textArea.setPrefHeight(400);
        patientGrid.add(textArea, 0, 2);
        
        Button recordtButton = new Button("View Previous Record");
        recordtButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        recordtButton.setPrefWidth(250);
        recordtButton.setPrefHeight(10);
        patientGrid.add(recordtButton, 0, 3);
        
        Button editButton = new Button("Edit my personal information");
        editButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        editButton.setPrefWidth(250);
        editButton.setPrefHeight(10); 
        patientGrid.add(editButton, 0, 4);
        
        Button faqButton = new Button("FAQ");
        faqButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        faqButton.setPrefWidth(250);
        faqButton.setPrefHeight(10); 
        patientGrid.add(faqButton, 0, 5);

        // Set the actions for the role selection buttons
        emailButton.setOnAction(e -> openMessageCenter(primaryStage));
        
        recordtButton.setOnAction(e -> { viewRecord(primaryStage, patientID); 
									        String content = textArea.getText();
									        saveToFile(filePath, content);});
        editButton.setOnAction(e -> { editInfo(primaryStage, patientID); 
									        String content = textArea.getText();
									        saveToFile(filePath, content);});
        
        faqButton.setOnAction(e -> { faq(primaryStage, patientID); 
								        String content = textArea.getText();
								        saveToFile(filePath, content);});
        
        StackPane stackPane = new StackPane(patientGrid);
        stackPane.setPrefSize(400, 450);

        // Set the background color
        stackPane.setStyle("-fx-background-color: #fbe493;");

        // Create the scene and set it on the stage
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(event -> {
        	String content = textArea.getText();
        	saveToFile(filePath, content);
        });
	}
	private void viewRecord(Stage primaryStage, String patientID) {
		GridPane patientGrid = new GridPane();
        patientGrid.setAlignment(Pos.CENTER);
        patientGrid.setHgap(10);
        patientGrid.setVgap(10);
        patientGrid.setPadding(new Insets(20));
        
        String resultData = readFromFile(patientID + "_Result.txt");

        Text myPageLabel = new Text("My Page");
        myPageLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 25px;");
        Text welcomeLabel = new Text("Welcome!");
        welcomeLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 15px;");
        Text myRecordLabel = new Text("My Previous Record Summary");
        myRecordLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 15px;");
        patientGrid.add(myPageLabel, 0, 0);
        patientGrid.add(welcomeLabel, 0, 1);
        patientGrid.add(myRecordLabel, 0, 2);
        
        Button emailButton = new Button("Email");
        emailButton.setPrefWidth(50);
        emailButton.setPrefHeight(50);
        patientGrid.add(emailButton, 1, 0);
        
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(250);
        textArea.setPrefHeight(400); 
        if (resultData != null) {
	        textArea.setText(resultData);
	    } else {
	    	showErrorMessage("Error loading the your result.");
	    }
        patientGrid.add(textArea, 0, 4);
        
        Button homeButton = new Button("Back to Home");
        homeButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        homeButton.setPrefWidth(250);
        homeButton.setPrefHeight(10);
        patientGrid.add(homeButton, 0, 5);
        
        Button editButton = new Button("Edit my personal information");
        editButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        editButton.setPrefWidth(250);
        editButton.setPrefHeight(10); 
        patientGrid.add(editButton, 0, 6);
        
        Button faqButton = new Button("FAQ");
        faqButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        faqButton.setPrefWidth(250);
        faqButton.setPrefHeight(10); 
        patientGrid.add(faqButton, 0, 7);
        
        //emailButton.setOnAction(e -> email(primaryStage, patientID));
        homeButton.setOnAction(e -> goHomePortal(primaryStage, patientID));
        editButton.setOnAction(e -> editInfo(primaryStage, patientID));
        faqButton.setOnAction(e -> faq(primaryStage, patientID));
        
        StackPane stackPane = new StackPane(patientGrid);
        stackPane.setPrefSize(400, 450);

        // Set the background color
        stackPane.setStyle("-fx-background-color: #fbe493;");

        // Create the scene and set it on the stage
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void editInfo(Stage primaryStage, String patientID) {
		String fileName = patientID + "_PersonalInfo.txt";
		fullNameField = new TextField();
		dateOfBirthField = new TextField();
		phoneNumberField = new TextField();
		emailAddressField = new TextField();
		newPasswordField = new PasswordField();
		passwordConfirmationField = new PasswordField();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(":");
                String fieldName = data[0].trim();
                String fieldValue = data[1].trim();

                if (fieldName.equals("Full Name")) {
                	fullNameField.setText(fieldValue);
                } else if (fieldName.equals("Date of Birth")) {
                	dateOfBirthField.setText(fieldValue);
                } else if (fieldName.equals("Phone Number")) {
                	phoneNumberField.setText(fieldValue);
                } else if (fieldName.equals("Email Address")) {
                	emailAddressField.setText(fieldValue);
                } else if (fieldName.equals("New Password")) {
                	newPasswordField.setText(fieldValue);
                } else if (fieldName.equals("Password Confirmation")) {
                	passwordConfirmationField.setText(fieldValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("An error occurred while reading your information.");
        }
		
		if (fullNameField == null || dateOfBirthField == null || phoneNumberField == null || emailAddressField == null || newPasswordField == null || passwordConfirmationField == null) {
		        showErrorMessage("No data available for the patient ID: " + patientID);
		}else {
			GridPane gridPane = new GridPane();
			gridPane.setPadding(new Insets(10));
	        gridPane.setVgap(10);
	        gridPane.setHgap(10);
	        
	        Text editLabel = new Text("Edit my info");
	        editLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 18px;");
	        
	        Text fullNameLabel = new Text("Full Name:");
	        fullNameLabel.setStyle("-fx-text-fill: #384e6b;");
	        fullNameField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        
	        Text dateOfBirthLabel = new Text("Date of Birth:");
	        dateOfBirthLabel.setStyle("-fx-text-fill: #384e6b;");
	        dateOfBirthField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        
	        Text phoneNumberLabel = new Text("Phone Number:");
	        phoneNumberLabel.setStyle("-fx-text-fill: #384e6b;");
	        phoneNumberField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        
	        Text emailAddressLabel = new Text("Email Address");
	        emailAddressLabel.setStyle("-fx-text-fill: #384e6b;");
	        emailAddressField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        
	        Text newPasswordLabel = new Text("New Password:");
	        newPasswordLabel.setStyle("-fx-text-fill: #384e6b;");
	        newPasswordField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        
	        Text passwordConfirmationLabel = new Text("Password Confirmation:");
	        passwordConfirmationLabel.setStyle("-fx-text-fill: #384e6b;");
	        passwordConfirmationField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        
	        Button saveButton = new Button("Save");
	        saveButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        saveButton.setPrefWidth(250);
	        saveButton.setPrefHeight(30);
	        
	        Button immunzationButton = new Button("Update my immunzation history");
	        immunzationButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        immunzationButton.setPrefWidth(250);
	        immunzationButton.setPrefHeight(30);
	        
	        Button cancelButton = new Button("Cancel");
	        cancelButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        cancelButton.setPrefWidth(250);
	        cancelButton.setPrefHeight(30);
	        
	        Button homeButton = new Button("Home");
	        homeButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
	        homeButton.setPrefWidth(80);
	        homeButton.setPrefHeight(30);
	        
	        gridPane.add(editLabel, 0, 0);
	        gridPane.add(homeButton, 2, 0);
	        gridPane.add(fullNameLabel, 0, 2);
	        gridPane.add(fullNameField, 1, 2);
	        gridPane.add(dateOfBirthLabel, 0, 3);
	        gridPane.add(dateOfBirthField, 1, 3);
	        gridPane.add(phoneNumberLabel, 0, 4);
	        gridPane.add(phoneNumberField, 1, 4);
	        gridPane.add(emailAddressLabel, 0, 5);
	        gridPane.add(emailAddressField, 1, 5);
	        gridPane.add(newPasswordLabel, 0, 6);
	        gridPane.add(newPasswordField, 1, 6);
	        gridPane.add(passwordConfirmationLabel, 0, 7);
	        gridPane.add(passwordConfirmationField, 1, 7);
	        
	        VBox vBox = new VBox(10);
	        vBox.setAlignment(Pos.CENTER);
	        vBox.setPadding(new Insets(20));
	        vBox.getChildren().addAll(saveButton, immunzationButton, cancelButton);
	        gridPane.add(vBox, 1, 8);
	        
	        homeButton.setOnAction(e -> goHomePortal(primaryStage, patientID));
	        saveButton.setOnAction(e -> saveNewInfo(patientID));
	        immunzationButton.setOnAction(e -> updateImmunzationInfo(primaryStage, patientID));
	        cancelButton.setOnAction(e -> goHomePortal(primaryStage, patientID));
	        
	        StackPane stackPane = new StackPane(gridPane);
	        stackPane.setPrefSize(550, 450);
	        
	        stackPane.setStyle("-fx-background-color: #fbe493;");
	        
	        // Create the scene and set it on the stage
	        Scene scene = new Scene(stackPane);
	        primaryStage.setScene(scene);
	        primaryStage.show();
		 }
	}
	
	private void saveNewInfo(String patientID) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientID + "_PersonalInfo.txt"))) {
	        // Write the updated patient information to the file
			writer.write("Full Name: " + fullNameField.getText() + "\n");
	        writer.write("Date of Birth: " + dateOfBirthField.getText() + "\n");
	        writer.write("Phone Number: " + phoneNumberField.getText() + "\n");
	        writer.write("Email Address: " + emailAddressField.getText() + "\n");
	        writer.write("New Password: " + newPasswordField.getText() + "\n");
	        writer.write("Password Confirmation: " + passwordConfirmationField.getText() + "\n");
	        writer.close();
	        // Show success message
	        showSuccessMessage("Your information updated successfully.");
	    } catch (IOException e) {
	    	e.printStackTrace();
	        showErrorMessage("An error occurred while saving your information.");
	    }
		
		String dataManageFile = "dataManage.txt";
		 try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataManageFile))) {
			 boolean keepAdding = true;
			    while (keepAdding) {
			        String username = fullNameField.getText();
			        String password = newPasswordField.getText();
			        String patientId = patientID;
			        String line = username + ":" + password + ":" + patientId;
			        writer.write(line);
			        writer.newLine();
			    }
	            showSuccessMessage("Data updated to the file successfully.");
	     } catch (IOException e) {
	        	showErrorMessage("An error occurred while updating to the file.");
	            e.printStackTrace();
	     }
	}
	
	private void updateImmunzationInfo(Stage primaryStage, String patientID) {
		
		// Create the GridPane layout
		GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        
        Text updateLabel = new Text("Update immunization history");
        updateLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 18px;");
        
        Label recordLabel = new Label("Record:");
        recordLabel.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        TextField recordField1 = new TextField();
        TextField recordField2 = new TextField();
        TextField recordField3 = new TextField();
        TextField recordField4 = new TextField();
        TextField recordField5 = new TextField();
        recordField1.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        recordField2.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        recordField3.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        recordField4.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        recordField5.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        
        Label dateLabel = new Label("Date:");
        dateLabel.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        DatePicker datePicker1 = new DatePicker();
        DatePicker datePicker2 = new DatePicker();
        DatePicker datePicker3 = new DatePicker();
        DatePicker datePicker4 = new DatePicker();
        DatePicker datePicker5 = new DatePicker();
        datePicker1.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        datePicker2.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        datePicker3.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        datePicker4.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        datePicker5.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 10px;");
        
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        saveButton.setPrefWidth(150);
        saveButton.setPrefHeight(30);
        saveButton.setOnAction(e -> saveImmunizationInfo(patientID, recordField1.getText(), recordField2.getText(),recordField3.getText(), recordField4.getText(), recordField5.getText(), datePicker1, datePicker2, datePicker3, datePicker4, datePicker5));
        
        Button homeButton = new Button("Home");
        homeButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        homeButton.setPrefWidth(80);
        homeButton.setPrefHeight(30);
        homeButton.setOnAction(e -> goHomePortal(primaryStage, patientID));
        
        gridPane.add(updateLabel, 0, 0);
        gridPane.add(homeButton, 1, 0);
        gridPane.add(recordLabel, 0, 2);
        gridPane.add(recordField1, 0, 3);
        gridPane.add(recordField2, 0, 4);
        gridPane.add(recordField3, 0, 5);
        gridPane.add(recordField4, 0, 6);
        gridPane.add(recordField5, 0, 7);
        gridPane.add(dateLabel, 1, 2);
        gridPane.add(datePicker1, 1, 3);
        gridPane.add(datePicker2, 1, 4);
        gridPane.add(datePicker3, 1, 5);
        gridPane.add(datePicker4, 1, 6);
        gridPane.add(datePicker5, 1, 7);
        gridPane.add(saveButton, 1, 9);
        
        StackPane stackPane = new StackPane(gridPane);
        stackPane.setPrefSize(400, 400);
        
        stackPane.setStyle("-fx-background-color: #fbe493;");
        
        // Create the scene and set it on the stage
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void saveImmunizationInfo(String patientID, String recordField1, String recordField2, String recordField3, String recordField4, String recordField5, DatePicker datePicker1, DatePicker datePicker2, DatePicker datePicker3, DatePicker datePicker4, DatePicker datePicker5) {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientID + "_ImmuneRecord.txt", true))) {
            // Append the immunization info to the file
            writer.write("Patient ID: " + patientID);
            writer.newLine();
            writer.write("Record Field 1: " + (recordField1.isEmpty() ? "N/A" : recordField1));
            writer.newLine();
            writer.write("Record Field 2: " + (recordField2.isEmpty() ? "N/A" : recordField2));
            writer.newLine();
            writer.write("Record Field 3: " + (recordField3.isEmpty() ? "N/A" : recordField3));
            writer.newLine();
            writer.write("Record Field 4: " + (recordField4.isEmpty() ? "N/A" : recordField4));
            writer.newLine();
            writer.write("Record Field 5: " + (recordField5.isEmpty() ? "N/A" : recordField5));
            writer.newLine();
            writer.write("Date 1: " + (datePicker1.getValue() != null ? datePicker1.getValue().toString() : "N/A"));
            writer.newLine();
            writer.write("Date 2: " + (datePicker2.getValue() != null ? datePicker2.getValue().toString() : "N/A"));
            writer.newLine();
            writer.write("Date 3: " + (datePicker3.getValue() != null ? datePicker3.getValue().toString() : "N/A"));
            writer.newLine();
            writer.write("Date 4: " + (datePicker4.getValue() != null ? datePicker4.getValue().toString() : "N/A"));
            writer.newLine();
            writer.write("Date 5: " + (datePicker5.getValue() != null ? datePicker5.getValue().toString() : "N/A") + "\n");
            writer.newLine();
            showSuccessMessage("Immunization Histry updated to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while saving immunization info.");
        }
    }

	private void goHomePortal(Stage primaryStage, String patientID) {
		start(primaryStage, patientID);
	}
	
	private void faq(Stage primaryStage, String patientID) {
		GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        
        Label faq = new Label("FAQ");
        faq.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 30px;");
        gridPane.add(faq, 0, 0);
        
        Button homeButton = new Button("Home");
        homeButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        homeButton.setPrefWidth(80);
        homeButton.setPrefHeight(30);
        homeButton.setOnAction(e -> goHomePortal(primaryStage, patientID));
        gridPane.add(homeButton, 4, 0);
        
        Label faq1 = new Label("Can I change my information?");
        faq1.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        gridPane.add(faq1, 0, 1);
        
        Label answer1 = new Label();
        answer1.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        answer1.setText("Yes, you can change your information. Please go to Edit my personal information.");
        gridPane.add(answer1, 1, 1);
        
        Label faq2 = new Label("Should I bring something?");
        faq2.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        gridPane.add(faq2, 0, 2);
        
        Label answer2 = new Label();
        answer2.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        answer2.setText("Please bring patient ID and parent ID.");
        gridPane.add(answer2, 1, 2);
        
        Label faq3 = new Label("How can I prepare my child for a pediatric visit?");
        faq3.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        gridPane.add(faq3, 0, 3);
        
        Label answer3 = new Label();
        answer3.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        answer3.setText("Please visit or call our center and set the schedule for your child.");
        gridPane.add(answer3, 1, 3);
        
        Label faq4 = new Label("What services do pediatricians provide?");
        faq4.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        gridPane.add(faq4, 0, 4);
        
        Label answer4 = new Label();
        answer4.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        answer4.setText("Pediatricians provide preventive health care for children in good health\n" + "and medical care for children who are acutely or chronically ill.");
        gridPane.add(answer4, 1, 4);
        
        Label faq5 = new Label("My record doesn't show up. What can I do?");
        faq5.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        gridPane.add(faq5, 0, 5);
        
        Label answer5 = new Label();
        answer5.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        answer5.setText("Please wait little more untill it will be shown up. If it doesn't show up, please call our center.");
        gridPane.add(answer5, 1, 5);
        
        Label faq6 = new Label("How can I ensure my child's safety during procedures?");
        faq6.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        gridPane.add(faq6, 0, 6);
        
        Label answer6 = new Label();
        answer6.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        answer6.setText("We will make sure your child knows never to walk away with strangers.");
        gridPane.add(answer6, 1, 6);
        
        Label faq7 = new Label("How can I promote good sleep habits for my child?");
        faq7.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        gridPane.add(faq7, 0, 7);
        
        Label answer7 = new Label();
        answer7.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        answer7.setText("Set up a bedtime routine and Relax before bedtime.");
        gridPane.add(answer7, 1, 7);
        
        Label faq8 = new Label("I changed my phone and email but I am still not linked to those accounts.");
        faq8.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        gridPane.add(faq8, 0, 8);
        
        Label answer8 = new Label();
        answer8.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b; -fx-font-size: 13px;");
        answer8.setText("It may take time to update. Sorry for the inconvenience.");
        gridPane.add(answer8, 1, 8);

        StackPane stackPane = new StackPane(gridPane);
        stackPane.setPrefSize(1100, 350);
        
        stackPane.setStyle("-fx-background-color: #fbe493;");
        
        // Create the scene and set it on the stage
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void saveToFile(String filePath, String content) {
	    try {
	        File file = new File(filePath);
	        
	        if (!file.exists()) {
	            file.createNewFile();
	        }
	        
	        FileWriter fileWriter = new FileWriter(file);
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

	        bufferedWriter.write(content);

	        bufferedWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private String readFromFile(String filePath) {
	    try {
	        // Open the file for reading
	        File file = new File(filePath);
	        
	        if (!file.exists()) {
	            return null;
	        }
	        
	        FileReader fileReader = new FileReader(file);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        // Read the file content
	        StringBuilder stringBuilder = new StringBuilder();
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(System.lineSeparator());
	        }

	        // Close the file
	        bufferedReader.close();

	        return stringBuilder.toString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return null;
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
