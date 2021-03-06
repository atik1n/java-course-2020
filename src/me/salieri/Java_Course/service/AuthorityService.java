package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorityService {
  @Autowired
  AuthorityRepository authorityRepository;

  public Authority loadAuthorityByName(String name) throws NotFoundException {
    Optional<Authority> authority = authorityRepository.findByAuthority(Objects.requireNonNull(name));
    if (authority.isEmpty()) {
      throw new NotFoundException("Authority not found");
    }
    return authority.get();
  }

  public Authority loadAuthorityById(Long id) throws NotFoundException {
    Optional<Authority> authority = authorityRepository.findById(Objects.requireNonNull(id));
    if (authority.isEmpty()) {
      throw new NotFoundException("Authority not found");
    }
    return authority.get();
  }

  public boolean hasAuthority(@NotNull User user, String name) {
    for (GrantedAuthority authority : user.getAuthorities()) {
      if (authority.getAuthority().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public List<Authority> allAuthorities() {
    return authorityRepository.findAll();
  }
}
