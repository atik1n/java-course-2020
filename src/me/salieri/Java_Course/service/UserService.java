package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.repository.AuthorityRepository;
import me.salieri.Java_Course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
  @PersistenceContext
  private EntityManager em;
  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthorityRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(s);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return user;
  }

  public List<User> allUsers() {
    return userRepository.findAll();
  }
}
