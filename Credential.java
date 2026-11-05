
public class Credential {

    // Private variables to store account details
    private String website;
    private String username;
    private String password;

    // Constructor initializes a new credential
    public Credential(String website, String username, String password) {
        this.website = website;
        this.username = username;
        this.password = password;
    }

    // Getters return the stored values
    public String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters update the stored values
    public void setWebsite(String website) {
        this.website = website;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Displays the password as asterisks when masking is enabled
    public String getMaskedPassword(boolean masked) {
        if (masked) {
            return "********";
        }

        return password;
    }
}
