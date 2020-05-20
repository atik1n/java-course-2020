package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.EntityRequest;
import me.salieri.Java_Course.model.PersonRequest;
import me.salieri.Java_Course.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {
  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private AuthorityService authorityService;
  @Autowired
  private GroupService groupService;
  @Autowired
  private UserService userService;

  public Person loadPersonByRequest(EntityRequest request) throws NotFoundException {
    return loadPersonById(request.getId());
  }

  public Person loadPersonById(Long id) throws NotFoundException {
    Optional<Person> person = personRepository.findById(Objects.requireNonNull(id));
    if (person.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    return person.get();
  }

  public Person createPerson(PersonRequest request, String group)
      throws NotFoundException, NullPointerException, StackOverflowError {
    char type;
    Set<Authority> authorities;
    if (group != null) {
      type = Person.STUDENT;
      authorities = new HashSet<>(Arrays.asList(
          authorityService.loadAuthorityByName("PERSON"),
          authorityService.loadAuthorityByName("STUDENT")
      ));
    } else {
      type = Person.TEACHER;
      authorities = new HashSet<>(Arrays.asList(
          authorityService.loadAuthorityByName("PERSON"),
          authorityService.loadAuthorityByName("TEACHER")
      ));
    }

    Person person = new Person(request.getFirstName(), request.getLastName(), request.getPatherName(), type);
    if (type == Person.STUDENT) {
      person.setGroup(groupService.loadGroupByName(group));
    }

    String username = person.generateUsername();
    int i = 1;
    while (i <= 255) {
      try {
        userService.loadUserByUsername(username);
      } catch (UsernameNotFoundException e) {
        break;
      }
      username = person.generateUsername(i++);
    }
    if (i == 256) {
      throw new StackOverflowError();
    }
    User user = new User(username);
    String password = Integer.toHexString(user.hashCode());
    person.setPassword(password);
    user.setPassword(password);

    person = savePerson(person);
    user.setPerson(person);
    user.setAuthorities(authorities);
    userService.saveUser(user);
    return person;
  }

  public Person savePerson(Person person) {
    return personRepository.save(person);
  }

  public boolean deactivatePerson(Person person) {
    if (person == null) {
      return false;
    }
    if (person.getType() == Person.DISABLE) {
      return false;
    }
    User user = person.getUser();
    if (user != null) {
      user.setActive(false);
    }
    person.setType(Person.DISABLE);
    person.setGroup(null);
    return true;
  }

  public List<Person> allPersons() {
    return personRepository.findAll();
  }

  public List<Person> allStudents() {
    return personRepository.findAllByType(Person.STUDENT);
  }

  public List<Person> allTeachers() {
    return personRepository.findAllByType(Person.TEACHER);
  }
}
