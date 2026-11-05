
public class CipherEngine {

    // Encrypts text by shifting each character forward
    public static String encrypt(String text, int key) {

        StringBuilder encrypted = new StringBuilder();

        // Normalize the key to keep it within printable ASCII range
        int shift = Math.floorMod(key, 95);

        for (int i = 0; i < text.length(); i++) {

            char character = text.charAt(i);

            // Shift printable ASCII characters (32 to 126)
            if (character >= 32 && character <= 126) {
                int shifted = 32 + Math.floorMod(character - 32 + shift, 95);
                encrypted.append((char) shifted);
            } else {
                // Keep other characters unchanged
                encrypted.append(character);
            }
        }

        return encrypted.toString();
    }

    // Decrypts text by reversing the character shift
    public static String decrypt(String text, int key) {

        StringBuilder decrypted = new StringBuilder();

        int shift = Math.floorMod(key, 95);

        for (int i = 0; i < text.length(); i++) {

            char character = text.charAt(i);

            if (character >= 32 && character <= 126) {
                int shifted = 32 + Math.floorMod(character - 32 - shift, 95);
                decrypted.append((char) shifted);
            } else {
                decrypted.append(character);
            }
        }

        return decrypted.toString();
    }
}
