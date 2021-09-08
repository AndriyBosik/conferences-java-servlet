package handler;

import com.conferences.handler.abstraction.IEncryptor;
import com.conferences.handler.implementation.Encryptor;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptorTest {

    private static IEncryptor encryptor;

    @BeforeClass
    public static void beforeTest() {
        encryptor = new Encryptor();
    }

    @Test
    public void shouldReturnTrueForCorrectPassword() {
        String hash = encryptor.encrypt("login");
        assertTrue(encryptor.check("login", hash));
    }

    @Test
    public void shouldReturnFalseForNotCorrectPassword() {
        String hash = encryptor.encrypt("password");
        assertFalse(encryptor.check("login", hash));
    }
}
