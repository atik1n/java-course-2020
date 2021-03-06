package me.salieri.Java_Course.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.model.GroupRequest;
import me.salieri.Java_Course.service.GroupService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Set;

@RestController
public class GroupController {
  @Autowired
  GroupService groupService;

  @GetMapping("/admin/groups.create")
  @Transactional
  public ResponseEntity<?> createGroup(@RequestBody GroupRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    Group group;
    try {
      group = new Group(request.getName());
    } catch (NullPointerException e) {
      return APIUtils.emptyApiResponse(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    group = groupService.saveGroup(group);
    if (group != null) {
      json.set("group", mapper.valueToTree(group));
      status = HttpStatus.CREATED;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping("/admin/groups.delete")
  @Transactional
  public ResponseEntity<?> deleteGroup(@RequestBody GroupRequest request) {
    HttpStatus status;
    try {
      status = deleteGroup(groupService.loadGroupByRequest(request));
    } catch (NotFoundException e) {
      status = HttpStatus.BAD_REQUEST;
    } catch (InvalidParameterException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.emptyApiResponse(status);
  }

  private HttpStatus deleteGroup(Group group) {
    HttpStatus status = HttpStatus.OK;
    if (!group.getPeople().isEmpty()) {
      status = HttpStatus.BAD_REQUEST;
    } else if (!groupService.deleteGroupById(group.getId())) {
      status = HttpStatus.BAD_REQUEST;
    }

    return status;
  }
}
