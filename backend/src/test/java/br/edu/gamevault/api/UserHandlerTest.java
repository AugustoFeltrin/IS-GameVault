package br.edu.gamevault.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.edu.gamevault.service.UserService;

public class UserHandlerTest {

    @Test
    public void deveCriarUserHandler() {

        UserService service = null;

        UserHandler handler = new UserHandler(service);

        assertNotNull(handler);
    }

    @Test
    public void deveImplementarHttpHandler() {

        UserService service = null;

        UserHandler handler = new UserHandler(service);

        assertNotNull(handler);
    }
}