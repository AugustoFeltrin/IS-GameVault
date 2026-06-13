package br.edu.gamevault.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.edu.gamevault.service.ReviewService;

public class ReviewHandlerTest {

    @Test
    public void deveCriarReviewHandler() {

        ReviewService service = null;

        ReviewHandler handler = new ReviewHandler(service);

        assertNotNull(handler);
    }

    @Test
    public void deveImplementarHttpHandler() {

        ReviewService service = null;

        ReviewHandler handler = new ReviewHandler(service);

        assertNotNull(handler);
    }
}