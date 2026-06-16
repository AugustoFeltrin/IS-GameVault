package br.edu.gamevault.service;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.gamevault.model.GameVaultRepository;
import br.edu.gamevault.model.User;

public class UserServiceImplTest {

    private GameVaultRepository repository;
    private UserServiceImpl service;

    private User USER_SEM_ID;
    private User USER_COM_ID;
    private User USER_OUTRO;

    @Before
    public void setUp() {
        repository = Mockito.mock(GameVaultRepository.class);
        service    = new UserServiceImpl(repository);

        String hashSenha123 = BCrypt.withDefaults().hashToString(10, "senha123".toCharArray());
        String hashAbc456   = BCrypt.withDefaults().hashToString(10, "abc456".toCharArray());

        USER_SEM_ID = new User(0, "Augusto", "augusto@email.com", "senha123");
        USER_COM_ID = new User(1, "Augusto", "augusto@email.com", hashSenha123);
        USER_OUTRO  = new User(2, "João",    "joao@email.com",    hashAbc456);
    }

    @Test
    public void addUser_deveRetornarUsuarioComIdGerado() {
        when(repository.addUser(any(User.class))).thenReturn(USER_COM_ID);

        User resultado = service.addUser(USER_SEM_ID);

        assertEquals(1, resultado.id());
        assertEquals("Augusto", resultado.name());
        verify(repository, times(1)).addUser(any(User.class));
    }

    @Test
    public void addUser_deveDelegarAoRepositorioComSenhaCriptografada() {
        when(repository.addUser(any(User.class))).thenReturn(USER_COM_ID);

        service.addUser(USER_SEM_ID);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).addUser(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("Augusto", capturedUser.name());
        assertEquals("augusto@email.com", capturedUser.email());
        assertNotEquals("senha123", capturedUser.password());
        assertTrue(BCrypt.verifyer().verify("senha123".toCharArray(), capturedUser.password().toCharArray()).verified);
    }

    @Test
    public void addUser_quandoRepositorioLancaExcecao_devePropagar() {
        when(repository.addUser(any(User.class))).thenThrow(new RuntimeException("Email duplicado"));

        try {
            service.addUser(USER_SEM_ID);
            fail("Esperava RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Email duplicado", e.getMessage());
        }
    }

    @Test
    public void getUserById_quandoUsuarioExiste_deveRetornarOptionalPreenchido() {
        when(repository.searchUserByID(1)).thenReturn(Optional.of(USER_COM_ID));

        Optional<User> resultado = service.getUserById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Augusto", resultado.get().name());
    }

    @Test
    public void getUserById_quandoUsuarioNaoExiste_deveRetornarOptionalVazio() {
        when(repository.searchUserByID(999)).thenReturn(Optional.empty());

        Optional<User> resultado = service.getUserById(999);

        assertFalse(resultado.isPresent());
    }

    @Test
    public void getUserById_deveDelegarAoRepositorioComIdCorreto() {
        when(repository.searchUserByID(42)).thenReturn(Optional.empty());

        service.getUserById(42);

        verify(repository).searchUserByID(42);
    }

    @Test
    public void authenticate_quandoCredenciaisCorretas_deveRetornarUsuario() {
        when(repository.findByEmail("augusto@email.com")).thenReturn(Optional.of(USER_COM_ID));

        Optional<User> resultado = service.authenticate("augusto@email.com", "senha123");

        assertTrue(resultado.isPresent());
        assertEquals("Augusto", resultado.get().name());
    }

    @Test
    public void authenticate_quandoSenhaErrada_deveRetornarOptionalVazio() {
        when(repository.findByEmail("augusto@email.com")).thenReturn(Optional.of(USER_COM_ID));

        Optional<User> resultado = service.authenticate("augusto@email.com", "senhaErrada");

        assertFalse(resultado.isPresent());
    }

    @Test
    public void authenticate_quandoEmailNaoExiste_deveRetornarOptionalVazio() {
        when(repository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        Optional<User> resultado = service.authenticate("naoexiste@email.com", "qualquerSenha");

        assertFalse(resultado.isPresent());
    }

    @Test
    public void authenticate_deveBuscarPorEmailNoRepositorio() {
        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.of(USER_OUTRO));

        service.authenticate("joao@email.com", "abc456");

        verify(repository).findByEmail("joao@email.com");
    }

    @Test
    public void authenticate_quandoEmailCorretoMasSenhaVazia_deveRetornarOptionalVazio() {
        when(repository.findByEmail("augusto@email.com")).thenReturn(Optional.of(USER_COM_ID));

        Optional<User> resultado = service.authenticate("augusto@email.com", "");

        assertFalse(resultado.isPresent());
    }

    @Test
    public void authenticate_quandoEmailEhNull_naoDeveLancarExcecaoNoServico() {
        when(repository.findByEmail(null)).thenReturn(Optional.empty());

        Optional<User> resultado = service.authenticate(null, "senha123");

        assertFalse(resultado.isPresent());
    }
}