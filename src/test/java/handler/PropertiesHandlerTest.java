package handler;

import com.conferences.handler.abstraction.IPropertiesHandler;
import com.conferences.handler.implementation.PropertiesHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesHandlerTest {

    private static IPropertiesHandler propertiesHandler;

    @BeforeClass
    public static void beforeTest() {
        propertiesHandler = new PropertiesHandler();
    }

    @Test
    public void shouldGetAppleProperty() {
        String value = propertiesHandler.getPropertyValue("messages", "en", "apple");
        assertEquals("apple", value);
    }

    @Test
    public void shouldGetCyrillic() {
        String value = propertiesHandler.getPropertyValue("messages", "uk", "table");
        assertEquals("стіл", value);
    }

    @Test
    public void shouldReturnOnlyKeyForNotExistingProperty() {
        String value = propertiesHandler.getPropertyValue("messages", "uk", "not_existing_property");
        assertEquals("not_existing_property", value);
    }
}
