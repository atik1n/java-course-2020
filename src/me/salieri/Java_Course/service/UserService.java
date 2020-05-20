package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.entity.SecuredUser;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.GroupRequest;
import me.salieri.Java_Course.model.UserRequest;
import me.salieri.Java_Course.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
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
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(Objects.requireNonNull(username));
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    return user.get();
  }

  public SecuredUser loadSecuredUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(Objects.requireNonNull(username));
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    SecuredUser secUser = new SecuredUser();
    BeanUtils.copyProperties(user.get(), secUser);
    return secUser;
  }

  public User loadUserById(Long id) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findById(Objects.requireNonNull(id));
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    return user.get();
  }

  public SecuredUser loadSecuredUserById(Long id) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findById(Objects.requireNonNull(id));
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    SecuredUser secUser = new SecuredUser();
    BeanUtils.copyProperties(user.get(), secUser);
    return secUser;
  }

  public User loadUserByPrincipal(Object user) throws UsernameNotFoundException {
    return loadUserByUsername(((User)Objects.requireNonNull(user)).getUsername());
  }

  public SecuredUser loadSecuredUserByPrincipal(Object user) throws UsernameNotFoundException {
    return loadSecuredUserByUsername(((User)Objects.requireNonNull(user)).getUsername());
  }

  public User loadUserByRequest(UserRequest request) throws UsernameNotFoundException, InvalidParameterException {
    User user;
    if (request.getId() != null) {
      user = loadUserById(request.getId());
    } else if (request.getUsername() != null) {
      user = loadUserByUsername(request.getUsername());
    } else {
      throw new InvalidParameterException();
    }

    return user;
  }

  public User saveUser(User user) {
    Optional<User> loaded = userRepository.findByUsername(user.getUsername());
    if (loaded.isPresent()) {
      return null;
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User changeUserPassword(User user, String password) {
    Optional<User> loaded = userRepository.findByUsername(user.getUsername());
    if (loaded.isEmpty()) {
      return null;
    }

    user.setPassword(passwordEncoder.encode(password));
    return user;
  }

  public boolean deactivateUserByUsername(String username) {
    return deactivateUser(userRepository.findByUsername(username).orElse(null));
  }

  public boolean deactivateUserById(Long id) {
    return deactivateUser(userRepository.findById(id).orElse(null));
  }

  private boolean activateUser(User user) {
    if (user == null) {
      return false;
    } else if (authorityService.hasAuthority(user, "OWNER")) {
      return false;
    } if (user.isActive()) {
      return false;
    }
    user.setActive(true);
    return true;
  }

  private boolean deactivateUser(User user) {
    if (user == null) {
      return false;
    } else if (authorityService.hasAuthority(user, "OWNER")) {
      return false;
    } if (!user.isActive()) {
      return false;
    }
    user.setActive(false);
    return true;
  }

  public List<User> allUsers() {
    return userRepository.findAll();
  }
}
