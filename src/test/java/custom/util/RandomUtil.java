package custom.util;

import java.security.SecureRandom;

public class RandomUtil {

    private static final String LETTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();

    public static int generateInt(int n) {
        return random.nextInt(n);
    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            sb.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        return sb.toString();
    }

    public static String generateEmail() {
        return  generateRandomString(6) + "@" +
                generateRandomString(5) + "." +
                generateRandomString(3);
    }
}
