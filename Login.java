package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class Login extends Application {
    private StackPane stackPane;
    private TextField nameField;
    private PasswordField passwordField;
    private Set<String> usedIDs = new HashSet<>();
    private Map<String, User> users;
    
    //create a file for user data manage
    String dataManageFile = "dataManage.txt";
    
    // a unique ID for the user
    String userID;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        // Create the login form layout
        VBox loginBox = new VBox(10);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(20));

        // Add UI elements to the login form layout
        Label loginLabel = new Label("Login as:");
        loginLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 20px;");
        
        Button patientButton = new Button("Patient");
        patientButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        patientButton.setPrefWidth(150);
        patientButton.setPrefHeight(20);
        
        Button doctorButton = new Button("Doctor");
        doctorButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        doctorButton.setPrefWidth(150);
        doctorButton.setPrefHeight(20);
        
        Button nurseButton = new Button("Nurse");
        nurseButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        nurseButton.setPrefWidth(150);
        nurseButton.setPrefHeight(20);

        // Set the actions for the role selection buttons
        patientButton.setOnAction(e -> showLogin("Patient"));
        doctorButton.setOnAction(e -> showLogin("Doctor"));
        nurseButton.setOnAction(e -> showLogin("Nurse"));

        // Create an ImageView to hold the logo image
        ImageView logoImageView = new ImageView(new Image("https://drive.google.com/uc?id=1erBoFkcdqvkQIENMmP6cN0zInW_H7ubX"));

        // Set the size of the ImageView as desired
        logoImageView.setFitWidth(200);
        logoImageView.setFitHeight(150);

        // Add the logo image, loginLabel, and role buttons to the loginBox
        loginBox.getChildren().addAll(logoImageView, loginLabel, patientButton, doctorButton, nurseButton);

        // Create a StackPane to switch between the role selection and login form
        stackPane = new StackPane(loginBox);
        stackPane.setPrefSize(500, 500);

        // Set the background color
        stackPane.setStyle("-fx-background-color: #fbe493;");

        // Create the scene and set it on the stage
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLogin(String role) {
        // Update the login form title based on the selected role
        Label nameLabel = new Label("Username:");
        nameLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 15px;");
        nameField = new TextField();
        
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 15px;");
        Hyperlink signUpLink = new Hyperlink("Sign up here");
        signUpLink.setOnAction(e -> showSignUp());
        passwordField = new PasswordField();
        
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        loginButton.setPrefWidth(150); 
        loginButton.setPrefHeight(20);

        // Set the action for the login button
        loginButton.setOnAction(e -> handleLogin(role));
        
        // Create a VBox to hold the logo and login form elements
        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);

        // Create an ImageView for the logo
        ImageView logoImage = new ImageView("https://drive.google.com/uc?id=1erBoFkcdqvkQIENMmP6cN0zInW_H7ubX");
        logoImage.setFitHeight(100);
        logoImage.setPreserveRatio(true);

        // Create a Label for the "Login as" text
        Label loginAsLabel = new Label("Login as " + role);
        loginAsLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 19px;");

        // Add the logo, loginAsLabel, and other login form elements to the VBox
        loginBox.getChildren().addAll(logoImage, loginAsLabel, nameLabel, nameField, passwordLabel, passwordField, signUpLink, loginButton);

        // Switch to the login form view
        stackPane.getChildren().set(0, loginBox);
    }

    private void showSignUp() {
        // Create the sign-up form layout
        Label signUpLabel = new Label("Create Account");
        signUpLabel.setStyle("-fx-text-fill: #384e6b; -fx-font-size: 19px;");
        
        TextField fullNameField = new TextField();
        fullNameField.setStyle("-fx-text-fill: #384e6b;");
        
        TextField dateOfBirthField = new TextField();
        dateOfBirthField.setStyle("-fx-text-fill: #384e6b;");
        
        TextField phoneNumberField = new TextField();
        phoneNumberField.setStyle("-fx-text-fill: #384e6b;");
        
        TextField emailAddressField = new TextField();
        emailAddressField.setStyle("-fx-text-fill: #384e6b;");
        
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setStyle("-fx-text-fill: #384e6b;");
        
        PasswordField passwordConfirmationField = new PasswordField();
        passwordConfirmationField.setStyle("-fx-text-fill: #384e6b;");
        
        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #384e6b;");
        signUpButton.setPrefWidth(150);
        signUpButton.setPrefHeight(30);
        
        // Set the action for the sign-up button
        signUpButton.setOnAction(e -> handleSignUp(fullNameField.getText(), dateOfBirthField.getText(),
                phoneNumberField.getText(), emailAddressField.getText(), newPasswordField.getText(),
                passwordConfirmationField.getText()));

        GridPane signUpForm = new GridPane();
        signUpForm.setPadding(new Insets(20));
        signUpForm.setHgap(10);
        signUpForm.setVgap(10);
        signUpForm.add(new Label("Full Name:"), 0, 0);
        signUpForm.add(fullNameField, 1, 0);
        signUpForm.add(new Label("Date of Birth:"), 0, 1);
        signUpForm.add(dateOfBirthField, 1, 1);
        signUpForm.add(new Label("Phone Number:"), 0, 2);
        signUpForm.add(phoneNumberField, 1, 2);
        signUpForm.add(new Label("Email Address:"), 0, 3);
        signUpForm.add(emailAddressField, 1, 3);
        signUpForm.add(new Label("New Password:"), 0, 4);
        signUpForm.add(newPasswordField, 1, 4);
        signUpForm.add(new Label("Password Confirmation:"), 0, 5);
        signUpForm.add(passwordConfirmationField, 1, 5);
        signUpForm.add(signUpButton, 1, 9);

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(signUpLabel, signUpForm);

        // Clear the form fields
        fullNameField.clear();
        dateOfBirthField.clear();
        phoneNumberField.clear();
        emailAddressField.clear();
        newPasswordField.clear();
        passwordConfirmationField.clear();

        // Switch to the sign-up form view
        stackPane.getChildren().set(0, vbox);
    }

    private void handleLogin(String role) {
        String username = nameField.getText();
        String password = passwordField.getText();

        if (isValidUser(username, password) == true) {
        	showSuccessMessage("Login successful");
        	// Check the role and perform role-specific actions
        	if (role.equalsIgnoreCase("Patient")) {
        		// Redirect to patient portal
        		PatientView patientView = new PatientView();
        		patientView.start(new Stage(), userID);
        	} else if (role.equalsIgnoreCase("Nurse")) {
                // Redirect to nurse portal
        		NurseView nurseView = new NurseView();
        		nurseView.start(new Stage());
            } else if (role.equalsIgnoreCase("Doctor")) {
                // Redirect to doctor portal
            	Doctor doctor = new Doctor();
            	doctor.start(new Stage());
            }  else {
                // Handle other roles or display an error message
                showErrorMessage("Invalid role. Please try again.");
            }
        } else {
            showErrorMessage("Incorrect Password or username\n" + "The password or username you entered is incorrect.\n" + "Please try again.");
        }
    }

    private void handleSignUp(String fullName, String dateOfBirth, String phoneNumber, String emailAddress, String newPassword, String passwordConfirmation) {
        // Validate the form fields
        if (fullName.isEmpty() || dateOfBirth.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty() ||
                newPassword.isEmpty() || passwordConfirmation.isEmpty()) {
        	showErrorMessage("All fields are required");
            return;
        }

        if (!newPassword.equals(passwordConfirmation)) {
        	showErrorMessage("Password and confirmation do not match");
            return;
        }

        // Generate a unique ID for the user
        userID = generateUniqueID();
        
        //add user to hash map
        addUser(fullName, newPassword, userID);        

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataManageFile))) {
            for (Map.Entry<String, User> entry : users.entrySet()) {
            	String username = entry.getKey();
                User user = entry.getValue();
                String password = user.getPassword();
                String patientId = user.getPatientId();

                String line = username + ":" + password + ":" + patientId;
                writer.write(line);
                writer.newLine();
            }
            showSuccessMessage("Data written to the file successfully.");
        } catch (IOException e) {
        	showErrorMessage("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        
        String fileName = userID + "_PersonalInfo.txt";
        
        // Create a file and Store the user details in the file
        try {
    		FileWriter fileWriter = new FileWriter(fileName);
    		
    		fileWriter.write("Full Name: " + fullName + "\n");
    		fileWriter.write("Date of Birth: " + dateOfBirth + "\n");
    		fileWriter.write("Phone Number: " + phoneNumber + "\n");
    		fileWriter.write("Email Address: " + emailAddress + "\n");
    		fileWriter.write("New Password: " + newPassword + "\n");
    		fileWriter.write("Password Confirmation: " + passwordConfirmation + "\n");
    		fileWriter.close();
    		showSuccessMessage("Your Account has created!\n" + "Your Unique 5 digit number is " + userID);
    	} catch (IOException e) {
    		e.printStackTrace();
    		showErrorMessage("Error occurred while creating the patient infomation file.");
    	}
        
        // Clear the form fields
        nameField.clear();
        passwordField.clear();

        // Switch to the sign-in form view
        showLogin("Patient");
    }
    
    public void addUser(String username, String password, String patientId) {
    	users = new HashMap<>();
    	users.put(username, new User(username, password, patientId));
    }
    
    private boolean isValidUser(String username, String password) {
    	 try (BufferedReader reader = new BufferedReader(new FileReader(dataManageFile))) {
    	        String line;
    	        while ((line = reader.readLine()) != null) {
    	            String[] data = line.split(":");
    	            String storedUsername = data[0];
    	            String storedPassword = data[1];
    	            userID = data[2];

    	            if (username.equals(storedUsername) && password.equals(storedPassword)) {
    	                return true; // if username and password match
    	            }
    	        }
    	    } catch (IOException e) {
    	        showErrorMessage("An error occurred while reading the file.");
    	        e.printStackTrace();
    	    }

    	 return false; // if username and password do not match or an error occurred
    }

    private String generateUniqueID() {
        // Generate a random 5-digit ID
        Random random = new Random();
        String id = String.format("%05d", random.nextInt(100000));

        // Check if the ID is already used
        while (usedIDs.contains(id)) {
            id = String.format("%05d", random.nextInt(100000));
        }
        return id;
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
