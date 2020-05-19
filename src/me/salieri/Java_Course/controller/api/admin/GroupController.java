package me.salieri.Java_Course.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.model.GroupRequest;
import me.salieri.Java_Course.service.GroupService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {
  @Autowired
  GroupService groupService;

  @GetMapping("/admin/createGroup")
  @Transactional
  public ResponseEntity<?> createGroup(@RequestBody GroupRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    Group group;
    try {
      group = new Group(request.getName());
    } catch (NullPointerException e) {
      return APIUtils.apiResponse(json, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    group = groupService.saveGroup(group);
    if (group != null) {
      json.set("group", mapper.valueToTree(group));
      status = HttpStatus.CREATED;
    }

    return APIUtils.apiResponse(json, status);
  }
}
