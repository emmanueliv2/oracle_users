package com.oracle.user.repository;

import com.oracle.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {
    List<User> findByRole(String role);

/*    @Query("UPDATE u SET ?1 = ?2 WHERE u.id = ?3")
    User updateUser(String attribute, String value, Integer userId);*/
}
