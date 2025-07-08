import java.util.Base64;

public class Base64Serializer {

    // Method to encode a byte array to a Base64 encoded string
    public static String encodeToString(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    // Method to decode a Base64 encoded string to a byte array
    public static byte[] decodeFromString(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    // Method to encode a string to a Base64 encoded string
    public static String encodeStringToString(String data) {
        return encodeToString(data.getBytes());
    }

    // Method to decode a Base64 encoded string to a string
    public static String decodeStringFromString(String base64String) {
        return new String(decodeFromString(base64String));
    }

    public static void main(String[] args) {
        // Example usage
        String originalString = "Hello, World!";
        String encodedString = encodeStringToString(originalString);
        String decodedString = decodeStringFromString(encodedString);

        System.out.println("Original String: " + originalString);
        System.out.println("Encoded String: " + encodedString);
        System.out.println("Decoded String: " + decodedString);
    }
}