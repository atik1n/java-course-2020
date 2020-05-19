package me.salieri.Java_Course.service;

import me.salieri.Java_Course.config.RolesConfig;
import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthorityService authorityService;
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

  public User loadUserById(Long id) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }

    return user.get();
  }

  public User loadUserByPrincipal(Object user) throws UsernameNotFoundException {
    return loadUserByUsername(((User)user).getUsername());
  }

  public boolean saveUser(User user, Set<Authority> authorities) {
    Optional<User> loaded = userRepository.findByUsername(user.getUsername());

    if (loaded.isPresent()) {
      return false;
    }

    user.setAuthorities(authorities);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return true;
  }

  public boolean deleteUser(User user) {
    Optional<User> loaded = userRepository.findByUsername(user.getUsername());

    if (loaded.isEmpty()) {
      return false;
    }

    if (authorityService.hasAuthority(loaded.get(), "OWNER")) {
      return false;
    }

    userRepository.delete(user);
    return true;
  }

  public List<User> allUsers() {
    return userRepository.findAll();
  }
}
