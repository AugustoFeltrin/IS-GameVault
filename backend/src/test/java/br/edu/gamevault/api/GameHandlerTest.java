package br.edu.gamevault.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.edu.gamevault.service.GameService;

public class GameHandlerTest {

    @Test
    public void deveCriarGameHandler() {

        GameService service = null;

        GameHandler handler = new GameHandler(service);

        assertNotNull(handler);
    }

    @Test
    public void deveImplementarHttpHandler() {

        GameService service = null;

        GameHandler handler = new GameHandler(service);

        assertNotNull(handler);
    }
}