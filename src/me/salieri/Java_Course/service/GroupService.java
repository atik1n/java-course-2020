package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.model.GroupRequest;
import me.salieri.Java_Course.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
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

  public Group loadGroupById(Long id) throws NotFoundException {
    Optional<Group> group = groupRepository.findById(Objects.requireNonNull(id));
    if (group.isEmpty()) {
      throw new NotFoundException("Group not found");
    }
    return group.get();
  }

  public Group loadGroupByRequest(GroupRequest request) throws NotFoundException, InvalidParameterException {
    Group group;
    if (request.getId() != null) {
      group = loadGroupById(request.getId());
    } else if (request.getName() != null) {
      group = loadGroupByName(request.getName());
    } else {
      throw new InvalidParameterException();
    }

    return group;
  }

  public Group saveGroup(Group group) {
    Optional<Group> loaded = groupRepository.findByName(Objects.requireNonNull(group.getName()));
    if (loaded.isPresent()) {
      return null;
    }
    return groupRepository.save(group);
  }

  public boolean deleteGroupByName(String name) {
    return deleteGroup(groupRepository.findByName(name).orElse(null));
  }

  public boolean deleteGroupById(Long id) {
    return deleteGroup(groupRepository.findById(id).orElse(null));
  }

  private boolean deleteGroup(Group group) {
    if (group == null) {
      return false;
    }
    groupRepository.deleteById(group.getId());
    return true;
  }

  public List<Group> allGroups() {
    return groupRepository.findAll();
  }
}
