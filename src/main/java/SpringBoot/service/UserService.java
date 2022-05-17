package SpringBoot.service;

import SpringBoot.model.User;
import SpringBoot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
            return userRepository.findAll();
        }

    public User getUserById(int id) {
            return userRepository.getById(id);
        }


    public void add(User user) {
            userRepository.save(user);
        }

    public void delete(int id) {
            userRepository.deleteById(id);
        }
}


