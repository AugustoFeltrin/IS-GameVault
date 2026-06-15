package br.edu.gamevault.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.sun.net.httpserver.HttpHandler;

import br.edu.gamevault.service.ReviewService;

public class ReviewHandlerTest {

    @Test
    public void deveCriarReviewHandler() {

        ReviewService service = mock(ReviewService.class);

        ReviewHandler handler = new ReviewHandler(service);

        assertNotNull(handler);
    }

    @Test
    public void deveImplementarHttpHandler() {

        ReviewService service = mock(ReviewService.class);

        ReviewHandler handler = new ReviewHandler(service);

        assertTrue(handler instanceof HttpHandler);
    }
}