package me.salieri.Java_Course.repository;

import me.salieri.Java_Course.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>  {

}
