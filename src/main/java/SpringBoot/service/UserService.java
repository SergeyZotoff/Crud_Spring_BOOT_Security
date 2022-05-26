package SpringBoot.service;

import SpringBoot.model.Role;
import SpringBoot.model.User;
import SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;

        Set<Role> temp = new HashSet<>();
        Role role = new Role();
        role.setRole("ADMIN");
        temp.add(role);
        User admin = new User("Sergey", "Zotov", 1234, "1234", temp);
        admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        userRepository.save(admin);
        temp.clear();
        role = new Role();
        role.setRole("USER");
        temp.add(role);
        User user = new User("Oxana", "Zotova", 1234, "1234", temp);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        temp.clear();
        role = new Role();
        role.setRole("ADMIN");
        temp.add(role);
        role = new Role();
        role.setRole("USER");
        temp.add(role);
        User sup = new User("Daniil", "Zotov", 1234, "1234", temp);
        sup.setPassword(new BCryptPasswordEncoder().encode(sup.getPassword()));
        userRepository.save(sup);

    }
    @Transactional
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public Optional<User> findUserById(long id) {
            return userRepository.findById(id);
        }

//Проверить этот метод
    public void add(User user) {
            userRepository.save(user);
        }

    public void delete(long id) {
            userRepository.deleteById(id);
        }

    public void edit(User user) {
        User extracted = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Edited User not exists!"));
        extracted.setUsername(user.getUsername());
        extracted.setSurname(user.getSurname());
        extracted.setPhoneNumber(user.getPhoneNumber());
        extracted.setRoles(user.getRoles());
        if (!passwordEncoder.matches(extracted.getPassword(), user.getPassword())) {
            extracted.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
    }
        return user;
    }
}


