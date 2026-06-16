package br.edu.gamevault.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.edu.gamevault.model.GameVaultRepository;
import br.edu.gamevault.model.Review;

public class ReviewServiceImplTest {

    private GameVaultRepository repository;
    private ReviewServiceImpl service;

    private static final LocalDate DATA_FIXA = LocalDate.of(2025, 6, 1);

    private static final Review REVIEW_COMPLETA =
            new Review(0, 1, 10, 8, "Jogo incrível!", DATA_FIXA);

    private static final Review REVIEW_SEM_NOTA =
            new Review(0, 1, 10, null, null, DATA_FIXA);

    private static final Review REVIEW_SEM_DATA =
            new Review(0, 1, 10, 7, "Bom jogo", null);

    @Before
    public void setUp() {
        repository = Mockito.mock(GameVaultRepository.class);
        service    = new ReviewServiceImpl(repository);
    }

    @Test
    public void addReview_quandoValidaComNota_deveSalvarNoBanco() {
        Review salva = new Review(1, 1, 10, 8, "Jogo incrível!", DATA_FIXA);
        when(repository.addReview(any(Review.class))).thenReturn(salva);

        Review resultado = service.addReview(REVIEW_COMPLETA);

        assertNotNull(resultado);
        assertEquals(1, resultado.id());
        verify(repository, times(1)).addReview(any(Review.class));
    }

    @Test
    public void addReview_quandoNotaEhNull_devePermitirSalvar() {
        Review salva = new Review(1, 1, 10, null, null, DATA_FIXA);
        when(repository.addReview(any(Review.class))).thenReturn(salva);

        Review resultado = service.addReview(REVIEW_SEM_NOTA);

        assertNull(resultado.rating());
        verify(repository).addReview(any(Review.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addReview_quandoNotaAcimaDeZez_deveLancarExcecao() {
        Review reviewInvalida = new Review(0, 1, 10, 11, "Nota impossível", DATA_FIXA);
        service.addReview(reviewInvalida);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addReview_quandoNotaAbaixoDeZero_deveLancarExcecao() {
        Review reviewInvalida = new Review(0, 1, 10, -1, "Nota negativa", DATA_FIXA);
        service.addReview(reviewInvalida);
    }

    @Test
    public void addReview_quandoNotaEhZero_deveSerValida() {
        Review salva = new Review(1, 1, 10, 0, "Péssimo", DATA_FIXA);
        when(repository.addReview(any())).thenReturn(salva);

        Review resultado = service.addReview(new Review(0, 1, 10, 0, "Péssimo", DATA_FIXA));

        assertEquals(Integer.valueOf(0), resultado.rating());
    }

    @Test
    public void addReview_quandoNotaEhDez_deveSerValida() {
        Review salva = new Review(1, 1, 10, 10, "Perfeito", DATA_FIXA);
        when(repository.addReview(any())).thenReturn(salva);

        Review resultado = service.addReview(new Review(0, 1, 10, 10, "Perfeito", DATA_FIXA));

        assertEquals(Integer.valueOf(10), resultado.rating());
    }

    @Test
    public void addReview_quandoDataEhNull_deveUsarDataDeHoje() {
        when(repository.addReview(any())).thenAnswer(inv -> inv.getArgument(0));

        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        service.addReview(REVIEW_SEM_DATA);
        verify(repository).addReview(captor.capture());

        assertNotNull(captor.getValue().reviewDate());
        assertEquals(LocalDate.now(), captor.getValue().reviewDate());
    }

    @Test
    public void addReview_quandoDataEhInformada_deveManterDataOriginal() {
        when(repository.addReview(any())).thenAnswer(inv -> inv.getArgument(0));

        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        service.addReview(REVIEW_COMPLETA);
        verify(repository).addReview(captor.capture());

        assertEquals(DATA_FIXA, captor.getValue().reviewDate());
    }

    @Test
    public void getReviewsByUserId_deveRetornarReviewsDoUsuario() {
        Review r1 = new Review(1, 5, 10, 8, "Bom",    DATA_FIXA);
        Review r2 = new Review(2, 5, 20, 6, "Médio",  DATA_FIXA);
        when(repository.searchReviewByUser(5)).thenReturn(Arrays.asList(r1, r2));

        List<Review> resultado = service.getReviewsByUserId(5);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(r -> r.userId() == 5));
    }

    @Test
    public void getReviewsByUserId_quandoNaoHaReviews_deveRetornarListaVazia() {
        when(repository.searchReviewByUser(99)).thenReturn(List.of());

        List<Review> resultado = service.getReviewsByUserId(99);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void getReviewsByUserId_deveDelegarAoRepositorioComIdCorreto() {
        when(repository.searchReviewByUser(7)).thenReturn(List.of());

        service.getReviewsByUserId(7);

        verify(repository).searchReviewByUser(7);
    }


    @Test
    public void getReviewsByGameId_deveRetornarReviewsDoJogo() {
        Review r1 = new Review(1, 1, 42, 9,    "Top",    DATA_FIXA);
        Review r2 = new Review(2, 2, 42, null, null,     DATA_FIXA);
        when(repository.searchReviewByGame(42)).thenReturn(Arrays.asList(r1, r2));

        List<Review> resultado = service.getReviewsByGameId(42);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(r -> r.gameId() == 42));
    }

    @Test
    public void getReviewsByGameId_quandoNaoHaReviews_deveRetornarListaVazia() {
        when(repository.searchReviewByGame(999)).thenReturn(List.of());

        List<Review> resultado = service.getReviewsByGameId(999);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void getReviewsByGameId_deveDelegarAoRepositorioComIdCorreto() {
        when(repository.searchReviewByGame(15)).thenReturn(List.of());

        service.getReviewsByGameId(15);

        verify(repository).searchReviewByGame(15);
    }
}
