package me.salieri.Java_Course.config;

import java.util.HashMap;
import java.util.Map;

public class RolesConfig {
  public static final Long ownerID = 1L;
  public static final Long adminID = 2L;
  public static final Map<Long, String> roles = new HashMap<Long, String>() {
    {
      put(ownerID, "OWNER");
      put(adminID, "ADMIN");
      put(3L, "USER");
      put(4L, "PERSON");
      put(5L, "TEACHER");
      put(6L, "STUDENT");
    }
  };

  public static String getOwner() {
    return roles.get(ownerID);
  }

  public static String getAdmin() {
    return roles.get(adminID);
  }
}
