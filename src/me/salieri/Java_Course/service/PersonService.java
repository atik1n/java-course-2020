package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
  @Autowired
  private PersonRepository personRepository;

  public Person savePerson(Person person) {
    return personRepository.save(person);
  }
}
