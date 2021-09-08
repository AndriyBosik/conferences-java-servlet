package handler;

import com.conferences.config.HttpMethod;
import com.conferences.handler.abstraction.IPermissionsHandler;
import com.conferences.handler.implementation.PermissionsHandler;
import org.junit.Test;

import static org.junit.Assert.*;

public class PermissionsHandlerTest {

    private static final int OK = 200;
    private static final int FORBIDDEN = 403;

    private static IPermissionsHandler permissionsHandler;
    private static PermissionsHandler.Builder permissionsHandlerBuilder;

    @Test
    public void shouldAllowAvailableUrlsForAllUsers() {
        permissionsHandlerBuilder = new PermissionsHandler.Builder();
        permissionsHandler = permissionsHandlerBuilder
            .init()
            .controlUrls("", "/profile", "/html", "/meetings")
                .withMethods(HttpMethod.POST)
                    .allowAllRoles()
            .build();

        assertEquals(FORBIDDEN, permissionsHandler.checkPermission("", HttpMethod.GET, "user"));
        assertEquals(OK, permissionsHandler.checkPermission("/profile", HttpMethod.POST, "speaker"));
        assertEquals(FORBIDDEN, permissionsHandler.checkPermission("/topics", HttpMethod.POST, "guest"));
        assertEquals(OK, permissionsHandler.checkPermission("/html", HttpMethod.POST, "moderator"));
        assertEquals(OK, permissionsHandler.checkPermission("", HttpMethod.POST, "guest"));
    }

    @Test
    public void shouldAllowUrlsOnlyForModerator() {
        permissionsHandlerBuilder = new PermissionsHandler.Builder();
        permissionsHandler = permissionsHandlerBuilder
            .init()
            .controlUrls("/profile", "/home", "/meetings")
                .withMethods(HttpMethod.POST, HttpMethod.GET)
                    .allowAnyRoleOf("moderator")
            .build();

        assertEquals(OK, permissionsHandler.checkPermission("/profile", HttpMethod.GET, "moderator"));
        assertEquals(OK, permissionsHandler.checkPermission("/home", HttpMethod.POST, "moderator"));
        assertEquals(FORBIDDEN, permissionsHandler.checkPermission("/profile", HttpMethod.GET, "speaker"));
        assertEquals(FORBIDDEN, permissionsHandler.checkPermission("/meetings", HttpMethod.GET, "user"));
    }

    @Test
    public void shouldCheckAnyPermissions() {
        permissionsHandlerBuilder = new PermissionsHandler.Builder();
        permissionsHandler = permissionsHandlerBuilder
            .init()
            .controlUrls("/topics/delete/*", "/topics/edit/*")
                .allowAnyRoleOf("moderator")
            .controlUrls("/home", "/profile", "/topics", "/meetings")
                .withMethods(HttpMethod.GET)
                    .allowAllRoles()
            .controlUrls("/topic/set-speaker")
                .withMethods(HttpMethod.POST)
                    .allowAnyRoleOf("speaker")
            .build();

        assertEquals(OK, permissionsHandler.checkPermission("/topics/delete/6", HttpMethod.GET, "moderator"));
        assertEquals(OK, permissionsHandler.checkPermission("/topics/edit/1", HttpMethod.POST, "moderator"));
        assertEquals(FORBIDDEN, permissionsHandler.checkPermission("/topics/edit/5", HttpMethod.POST, "speaker"));
        assertEquals(OK, permissionsHandler.checkPermission("/topic/set-speaker", HttpMethod.POST, "speaker"));
        assertEquals(FORBIDDEN, permissionsHandler.checkPermission("/topic/set-speaker", HttpMethod.POST, "user"));
    }
}