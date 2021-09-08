package handler;

import com.conferences.entity.Role;
import com.conferences.handler.abstraction.IJsonHandler;
import com.conferences.handler.implementation.JsonHandler;
import custom.CustomServletInputStream;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class JsonHandlerTest {

    private static IJsonHandler jsonHandler;

    @BeforeClass
    public static void beforeTest() {
        jsonHandler = new JsonHandler();
    }

    @Test
    public void shouldParseJsonToRoleObject() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ServletInputStream sis = new CustomServletInputStream("{id:5,title:'title'}".getBytes(StandardCharsets.UTF_8));
        Mockito.when(request.getInputStream()).thenReturn(sis);

        Role role = jsonHandler.parseJsonRequestBodyToObject(request, Role.class);
        assertEquals(5, role.getId());
        assertEquals("title", role.getTitle());
    }

    @Test
    public void shouldReturnNullWhenErrorInputStream() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getInputStream()).thenThrow(IOException.class);
        Role role = jsonHandler.parseJsonRequestBodyToObject(request, Role.class);

        assertNull(role);
    }

    @Test
    public void shouldReturnNullForNullInputStream() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getInputStream()).thenReturn(null);
        Role role = jsonHandler.parseJsonRequestBodyToObject(request, Role.class);

        assertNull(role);
    }
}
