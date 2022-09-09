package com.oracle.user.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.oracle.user.model.User;
import com.oracle.user.service.IUserService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("oracle-employees/v1/")
@Log4j2
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "users/{user-id}")
    public ResponseEntity<?> getUser(@PathVariable("user-id") int userId) {
        log.info("consulting {} user ", userId);
        return userService.getUser(userId);
    }
    @GetMapping(value = "users")
    public ResponseEntity<?> getUsers() {
        log.info("consulting all users");
        return userService.getUsers();
    }
    @GetMapping(value = "users/role/{role-id}")
    public ResponseEntity<?> getRoleUsers(@PathVariable("role-id") String roleId) {
        log.info("consulting {} roles ", roleId);
        return userService.getRoleUsers(roleId);
    }
    @PostMapping(value = "users")
    public ResponseEntity<?> postUser(@RequestBody @Valid User user) {
        log.info("generate a new user");
        return userService.postUser(user);
    }
    @PutMapping(value = "users/{user-id}")
    public ResponseEntity<?> putUser(@RequestBody User user, @PathVariable("user-id") Integer userId) {
        log.info("modifying {} user", userId);
        return userService.putUser(user, userId);
    }
    @PatchMapping(value = "users/{user-id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchUser(@RequestBody JsonPatch patch, @PathVariable("user-id") Integer userId) throws JsonPatchException {
        log.info("modifying {} user", userId);
        return userService.patchUser(patch, userId);
    }
    @DeleteMapping(value = "users/{user-id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user-id") Integer userId) {
        log.info("delete {} user", userId);
        return userService.deleteUser(userId);
    }
}