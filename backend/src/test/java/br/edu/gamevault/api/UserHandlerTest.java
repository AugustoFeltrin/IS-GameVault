package br.edu.gamevault.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.sun.net.httpserver.HttpHandler;

import br.edu.gamevault.service.UserService;

public class UserHandlerTest {

    @Test
    public void deveCriarUserHandler() {

        UserService service = mock(UserService.class);

        UserHandler handler = new UserHandler(service);

        assertNotNull(handler);
    }

    @Test
    public void deveImplementarHttpHandler() {

        UserService service = mock(UserService.class);

        UserHandler handler = new UserHandler(service);

        assertTrue(handler instanceof HttpHandler);
    }
}