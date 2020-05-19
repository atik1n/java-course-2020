package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class GroupService {
  @Autowired
  GroupRepository groupRepository;

  public Group loadGroupByName(String name) throws NotFoundException {
    Optional<Group> group = groupRepository.findByName(Objects.requireNonNull(name));
    if (group.isEmpty()) {
      throw new NotFoundException("Group not found");
    }
    return group.get();
  }

  public Group saveGroup(Group group) {
    Optional<Group> loaded = groupRepository.findByName(Objects.requireNonNull(group.getName()));
    if (loaded.isPresent()) {
      return null;
    }
    return groupRepository.save(group);
  }
}
