package me.salieri.Java_Course.config;

import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.repository.AuthorityRepository;
import me.salieri.Java_Course.repository.UserRepository;
import me.salieri.Java_Course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Component
public class BasicSetup implements ApplicationListener<ContextRefreshedEvent> {
  private boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthorityRepository authorityRepository;
  @Autowired
  UserService userService;


  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    setup();
  }

  @Transactional
  public void setup() {
    if (alreadySetup) {
      return;
    }

    // Создадим роли
    Authority owner = authorityRepository.findById(1L).orElseGet(() -> createAuthority(1L, "OWNER"));
    authorityRepository.findById(2L).orElseGet(() -> createAuthority(2L, "ADMIN"));
    authorityRepository.findById(3L).orElseGet(() -> createAuthority(3L, "USER"));

    // Добавим нашего администратора
    userRepository.findByUsername("salieri").orElseGet(() -> createUser(
            "salieri",
            "$2a$10$lijOnxYI0YLqiqVzR5rCueOFGmEHn46dOf0wdj1TxdUbnKBCEHcse",
            Collections.singleton(owner)
    ));

    alreadySetup = true;
  }

  @Transactional
  public Authority createAuthority(final Long id, final String name) {
    Authority authority = new Authority(id, name);
    authorityRepository.save(authority);
    return authority;
  }

  @Transactional
  public User createUser(final String name, final String password, final Set<Authority> authorities) {
    User user = new User(name, password);
    user.setAuthorities(authorities);
    userRepository.save(user);
    return user;
  }

  public boolean isAlreadySetup() {
    return alreadySetup;
  }

  public void setAlreadySetup(boolean alreadySetup) {
    this.alreadySetup = alreadySetup;
  }
}
