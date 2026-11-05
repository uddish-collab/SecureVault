
import java.util.ArrayList;
import java.util.Scanner;

public class VaultManager {

    // Master PIN and encryption key for demonstration
    private static final String MASTER_PIN = "2206";
    private static final int ENCRYPTION_KEY = 7;

    // Stores all credentials in memory
    private static final ArrayList<Credential> vault = new ArrayList<>();

    // Tracks whether passwords are currently encrypted
    private static boolean encrypted = false;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("    SECURE VAULT & CREDENTIAL LEDGER");
        System.out.println("========================================");

        // Authenticate before allowing access
        if (!authenticate()) {
            scanner.close();
            return;
        }

        // Keep displaying the menu until the user exits
        while (true) {

            displayMenu();

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    viewVault();
                    pause();
                    break;

                case "2":
                    addCredential();
                    pause();
                    break;

                case "3":
                    encryptVault();
                    pause();
                    break;

                case "4":
                    decryptVault();
                    pause();
                    break;

                case "5":
                    searchCredential();
                    pause();
                    break;

                case "6":
                    editCredential();
                    pause();
                    break;

                case "7":
                    deleteCredential();
                    pause();
                    break;

                case "8":
                    System.out.println("\nVault closed. Goodbye!");
                    scanner.close();
                    
                    return;

                default:
                    System.out.println("\nInvalid choice. Select 1-8.");
            }
        }
    }

    // Checks the Master PIN and allows three attempts
    private static boolean authenticate() {

        int attempts = 3;

        while (attempts > 0) {

            System.out.print("\nEnter Master PIN: ");
            String pin = scanner.nextLine();

            if (MASTER_PIN.equals(pin)) {
                System.out.println("\nAccess granted! Welcome.");
                return true;
            }

            attempts--;
            System.out.println("Incorrect PIN. Attempts remaining: " + attempts);
        }

        System.out.println("\nAccess denied. Vault locked.");
        return false;
    }

    // Displays the main menu
    private static void displayMenu() {

        System.out.println("\n========================================");
        System.out.println("              MAIN MENU");
        System.out.println("========================================");
        System.out.println(" 1. View Vault Ledger");
        System.out.println(" 2. Add New Credentials");
        System.out.println(" 3. Encrypt All Passwords");
        System.out.println(" 4. Decrypt All Passwords");
        System.out.println(" 5. Search Credentials");
        System.out.println(" 6. Edit Credentials");
        System.out.println(" 7. Delete Credentials");
        System.out.println(" 8. Exit");
        System.out.println("----------------------------------------");
        System.out.println(" Ledger Status: " + (encrypted ? "ENCRYPTED" : "DECRYPTED"));
        System.out.println("========================================");
    }

    // Adds a new account to the ArrayList
    private static void addCredential() {

        if (encrypted) {
            System.out.println("\nDecrypt the vault before adding credentials.");
            return;
        }

        System.out.println("\n--- ADD NEW CREDENTIAL ---");

        String website = readRequiredInput("Website: ");
        String username = readRequiredInput("Username: ");
        String password = readRequiredInput("Password: ");

        vault.add(new Credential(website, username, password));

        System.out.println("Credential added successfully!");
    }

    // Reads non-empty input from the user
    private static String readRequiredInput(String message) {

        String input;

        do {
            System.out.print(message);
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Try again.");
            }

        } while (input.isEmpty());

        return input;
    }

    // Displays all stored credentials
    private static void viewVault() {

        if (vault.isEmpty()) {
            System.out.println("\nVault is empty.");
            return;
        }

        System.out.println("\n---------------------- VAULT LEDGER -----------------------");
        System.out.printf("%-5s %-20s %-20s %-20s%n",
                "No.", "Website", "Username", "Password");
        System.out.println("-----------------------------------------------------------");

        for (int i = 0; i < vault.size(); i++) {

            Credential credential = vault.get(i);

            // Show scrambled passwords when encrypted, otherwise mask them
            String passwordDisplay = encrypted
                    ? credential.getPassword()
                    : credential.getMaskedPassword(true);

            System.out.printf("%-5d %-20s %-20s %-20s%n",
                    i + 1,
                    credential.getWebsite(),
                    credential.getUsername(),
                    passwordDisplay);
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println("Total accounts: " + vault.size());
    }

    // Encrypts every password in the vault
    private static void encryptVault() {

        if (vault.isEmpty()) {
            System.out.println("\nVault is empty. Nothing to encrypt.");
            return;
        }

        if (encrypted) {
            System.out.println("\nVault is already encrypted.");
            return;
        }

        for (Credential credential : vault) {

            String original = credential.getPassword();

            String encryptedPassword =
                    CipherEngine.encrypt(original, ENCRYPTION_KEY);

            credential.setPassword(encryptedPassword);
        }

        encrypted = true;
        System.out.println("\nAll passwords encrypted successfully!");
    }

    // Decrypts every password in the vault
    private static void decryptVault() {

        if (!encrypted) {
            System.out.println("\nVault is already decrypted.");
            return;
        }

        for (Credential credential : vault) {

            String encryptedPassword = credential.getPassword();

            String original =
                    CipherEngine.decrypt(encryptedPassword, ENCRYPTION_KEY);

            credential.setPassword(original);
        }

        encrypted = false;
        System.out.println("\nAll passwords decrypted successfully!");
    }

    // Searches for an account using its website name
    private static void searchCredential() {

        if (vault.isEmpty()) {
            System.out.println("\nVault is empty.");
            return;
        }

        String search = readRequiredInput("\nEnter website to search: ");

        boolean found = false;

        for (Credential credential : vault) {

            if (credential.getWebsite().equalsIgnoreCase(search)) {

                System.out.println("\nAccount found!");
                System.out.println("Website  : " + credential.getWebsite());
                System.out.println("Username : " + credential.getUsername());
                System.out.println("Password : "
                        + (encrypted ? credential.getPassword()
                                     : credential.getMaskedPassword(true)));

                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching account found.");
        }
    }

    // Edits an existing account
    private static void editCredential() {

        if (encrypted) {
            System.out.println("\nDecrypt the vault before editing credentials.");
            return;
        }

        if (vault.isEmpty()) {
            System.out.println("\nVault is empty.");
            return;
        }

        viewVault();

        int index = readAccountNumber();

        if (index == -1) {
            return;
        }

        Credential credential = vault.get(index);

        System.out.println("\nEnter updated details:");

        credential.setWebsite(readRequiredInput("New website: "));
        credential.setUsername(readRequiredInput("New username: "));
        credential.setPassword(readRequiredInput("New password: "));

        System.out.println("Credential updated successfully!");
    }

    // Deletes an account from the vault
    private static void deleteCredential() {

        if (encrypted) {
            System.out.println("\nDecrypt the vault before deleting credentials.");
            return;
        }

        if (vault.isEmpty()) {
            System.out.println("\nVault is empty.");
            return;
        }

        viewVault();

        int index = readAccountNumber();

        if (index == -1) {
            return;
        }

        vault.remove(index);

        System.out.println("Credential deleted successfully!");
    }

    // Reads and validates an account number
    private static int readAccountNumber() {

        System.out.print("\nEnter account number: ");
        String input = scanner.nextLine().trim();

        try {

            int number = Integer.parseInt(input);

            if (number < 1 || number > vault.size()) {
                System.out.println("Invalid account number.");
                return -1;
            }

            return number - 1;

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid number.");
            return -1;
        }
    }
    // Waits for the user to press Enter before showing the menu again.
private static void pause() {
    System.out.println("\nPress Enter to continue...");
    scanner.nextLine();
}
}
