package application;

public class User {
	private String username;
    private String password;
    private String patientId;

    public User(String username, String password, String patientId) {
        this.username = username;
        this.password = password;
        this.patientId = patientId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPatientId() {
        return patientId;
    }
}