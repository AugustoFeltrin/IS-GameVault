package br.edu.gamevault.service;

import br.edu.gamevault.model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final GameVaultRepository repository;
    
    public UserServiceImpl(GameVaultRepository repository) { 
        this.repository = repository; 
    }

    @Override
    public User addUser(User user) { 
        String hashedPassword = BCrypt.withDefaults().hashToString(10, user.password().toCharArray());
        
        User securedUser = new User(user.id(), user.name(), user.email(), hashedPassword);
    
        return repository.addUser(securedUser); 
    }
    
    @Override
    public Optional<User> getUserById(int id) { 
        return repository.searchUserByID(id); 
    }
    
    @Override
    public Optional<User> authenticate(String email, String password) {
        return repository.findByEmail(email)
            .filter(user -> {
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.password().toCharArray());
                
                return result.verified;
            });
    }
}