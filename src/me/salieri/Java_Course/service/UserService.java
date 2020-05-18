package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.repository.AuthorityRepository;
import me.salieri.Java_Course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
  @PersistenceContext
  private EntityManager em;
  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthorityRepository authorityRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public User loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(s);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }

    return user.get();
  }

  public boolean saveUser(User user) {
    Optional<User> loaded = userRepository.findByUsername(user.getUsername());

    if (loaded.isPresent()) {
      return false;
    }

    Authority role = authorityRepository.findByAuthority("USER").orElse(new Authority(1337L, "USER"));
    user.setAuthorities(Collections.singleton(role));
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return true;
  }

  public List<User> allUsers() {
    return userRepository.findAll();
  }
}
