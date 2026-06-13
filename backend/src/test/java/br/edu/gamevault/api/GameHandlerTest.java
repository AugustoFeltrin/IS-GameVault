package br.edu.gamevault.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.sun.net.httpserver.HttpHandler;

import br.edu.gamevault.service.GameService;

public class GameHandlerTest {

    @Test
    public void deveCriarGameHandler() {

        GameService service = mock(GameService.class);

        GameHandler handler = new GameHandler(service);

        assertNotNull(handler);
    }

    @Test
    public void deveImplementarHttpHandler() {

        GameService service = mock(GameService.class);

        GameHandler handler = new GameHandler(service);

        assertTrue(handler instanceof HttpHandler);
    }
}