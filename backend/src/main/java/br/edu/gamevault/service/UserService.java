package br.edu.gamevault.service;
import br.edu.gamevault.model.User;
import java.util.Optional;

public interface UserService {
    User addUser(User user);
    Optional<User> getUserById(int id);
}