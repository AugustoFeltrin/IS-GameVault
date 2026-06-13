package br.edu.gamevault.service;

import br.edu.gamevault.model.Game;
import br.edu.gamevault.model.GameVaultRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameServiceImplTest {

    private GameVaultRepository repository;
    private GameServiceImpl service;

    private static final Game GAME_SEM_ID = new Game(0, "Hollow Knight", "Metroidvania desafiador", "https://img.com/hk.jpg", "igdb-123");
    private static final Game GAME_COM_ID  = new Game(1, "Hollow Knight", "Metroidvania desafiador", "https://img.com/hk.jpg", "igdb-123");
    private static final Game GAME_2       = new Game(2, "Celeste",        "Plataforma narrativo",    "https://img.com/cl.jpg", "igdb-456");

    @Before
    public void setUp() {
        repository = Mockito.mock(GameVaultRepository.class);
        service    = new GameServiceImpl(repository);
    }

    @Test
    public void addGame_deveRetornarJogoComIdGerado() {
        when(repository.addGame(GAME_SEM_ID)).thenReturn(GAME_COM_ID);

        Game resultado = service.addGame(GAME_SEM_ID);

        assertEquals(1, resultado.id());
        assertEquals("Hollow Knight", resultado.title());
        verify(repository, times(1)).addGame(GAME_SEM_ID);
    }

    @Test
    public void addGame_deveDelegarAoRepositorio() {
        when(repository.addGame(any(Game.class))).thenReturn(GAME_COM_ID);

        service.addGame(GAME_SEM_ID);

        verify(repository).addGame(GAME_SEM_ID);
    }

    @Test
    public void addGame_quandoRepositorioLancaExcecao_devePropagar() {
        when(repository.addGame(any())).thenThrow(new RuntimeException("Erro no banco"));

        try {
            service.addGame(GAME_SEM_ID);
            fail("Esperava RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Erro no banco", e.getMessage());
        }
    }


    @Test
    public void getAllGames_deveRetornarListaCompleta() {
        when(repository.listGames()).thenReturn(Arrays.asList(GAME_COM_ID, GAME_2));

        List<Game> resultado = service.getAllGames();

        assertEquals(2, resultado.size());
        assertEquals("Hollow Knight", resultado.get(0).title());
        assertEquals("Celeste",       resultado.get(1).title());
    }

    @Test
    public void getAllGames_quandoNaoHaJogos_deveRetornarListaVazia() {
        when(repository.listGames()).thenReturn(List.of());

        List<Game> resultado = service.getAllGames();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void getAllGames_deveDelegarAoRepositorio() {
        when(repository.listGames()).thenReturn(List.of());

        service.getAllGames();

        verify(repository, times(1)).listGames();
    }


    @Test
    public void getGameById_quandoJogoExiste_deveRetornarOptionalPreenchido() {
        when(repository.searchGameByID(1)).thenReturn(Optional.of(GAME_COM_ID));

        Optional<Game> resultado = service.getGameById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Hollow Knight", resultado.get().title());
    }

    @Test
    public void getGameById_quandoJogoNaoExiste_deveRetornarOptionalVazio() {
        when(repository.searchGameByID(999)).thenReturn(Optional.empty());

        Optional<Game> resultado = service.getGameById(999);

        assertFalse(resultado.isPresent());
    }

    @Test
    public void getGameById_deveDelegarAoRepositorioComIdCorreto() {
        when(repository.searchGameByID(42)).thenReturn(Optional.empty());

        service.getGameById(42);

        verify(repository).searchGameByID(42);
    }
}
