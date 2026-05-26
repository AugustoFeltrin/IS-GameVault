package br.edu.gamevault.service;
import br.edu.gamevault.model.*;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final GameVaultRepository repository;
    public UserServiceImpl(GameVaultRepository repository) { this.repository = repository; }

    @Override
    public User addUser(User user) { return repository.addUser(user); }
    
    @Override
    public Optional<User> getUserById(int id) { return repository.searchUserByID(id); }
}