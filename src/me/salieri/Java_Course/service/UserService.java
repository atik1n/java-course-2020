package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.repository.AuthorityRepository;
import me.salieri.Java_Course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
  @PersistenceContext
  private EntityManager em;
  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthorityRepository roleRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public User loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(s);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return user;
  }

  public boolean saveUser(User user) {
    User loaded = userRepository.findByUsername(user.getUsername());
    if (loaded != null) {
      return false;
    }

    user.setAuthorities(Collections.singleton(new Authority(1L, "USER")));
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return true;
  }

  public List<User> allUsers() {
    return userRepository.findAll();
  }
}
