package com.oracle.user.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.oracle.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 public interface IUserService {
    @GetMapping(value = "users/{user-id}")
    ResponseEntity<?> getUser(int userId);
    @GetMapping(value = "users")
    ResponseEntity<?> getUsers();
    @GetMapping(value = "users/role/{role-id}")
    ResponseEntity<?> getRoleUsers(String roleId);
    @PostMapping(value = "users")
    ResponseEntity<?> postUser(User user);
    @PutMapping(value = "users/{user-id}")
    ResponseEntity<?> putUser(User user, Integer userId);
    @PatchMapping(value = "users/{user-id}")
    ResponseEntity<?> patchUser(JsonPatch patch, Integer userId) throws JsonPatchException;
    @DeleteMapping(value = "users/{user-id}")
    ResponseEntity<?> deleteUser(Integer userId);
}
