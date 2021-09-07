package handler;

import com.conferences.config.Defaults;
import com.conferences.handler.implementation.LinkHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkHandlerTest {

    private static LinkHandler linkHandler;

    @BeforeClass
    public static void beforeTest() {
        linkHandler = new LinkHandler();
    }

    @Test
    public void shouldReturnDefaultLangForEmptyUrl() {
        String lang = linkHandler.getLangFromUrl("");
        assertEquals(Defaults.DEFAULT_LANG.toString(), lang);
    }

    @Test
    public void shouldReturnLangForUrlWithOnlyOnePart() {
        String lang = linkHandler.getLangFromUrl("/ru");
        assertEquals("ru", lang);
    }

    @Test
    public void shouldReturnLangForUrlWithMultipleParts() {
        String lang = linkHandler.getLangFromUrl("/uk/home/profile");
        assertEquals("uk", lang);
    }

    @Test
    public void shouldReturnConcatenatedUrlPathAndLang() {
        String url = linkHandler.addLangToUrl("/home/profile", "uk");
        assertEquals("/uk/home/profile", url);
    }

    @Test
    public void shouldCorrectlyAddLangForHomePage() {
        String url = linkHandler.addLangToUrl("", "uk");
        assertEquals("/uk", url);
    }

}
