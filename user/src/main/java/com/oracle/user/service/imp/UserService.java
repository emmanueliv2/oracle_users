package com.oracle.user.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.oracle.user.enums.Roles;
import com.oracle.user.exception.list.BadCredentialsException;
import com.oracle.user.exception.list.InternalErrorException;
import com.oracle.user.exception.list.NotFoundException;
import com.oracle.user.model.User;
import com.oracle.user.repository.IUserRepository;
import com.oracle.user.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public ResponseEntity<?> getUser(int userId) {
        try {
            User data = userRepository.findById(userId).orElse(null);
            if(Objects.isNull(data))
                throw new NotFoundException("The user with the id doesn't exist");
            return new ResponseEntity(data, HttpStatus.OK);
        }catch (InternalErrorException ex){
            log.info("getUser: error {}", ex.getMessage());
            throw new InternalErrorException("a error has occurred into the service, please try in another moment");
        }
    }

    @Override
    public ResponseEntity<?> getUsers() {
        try {
            List<User> data = userRepository.findAll();
            if(data.isEmpty())
                throw new NotFoundException("users not exists");
            return new ResponseEntity(data, HttpStatus.OK);
        }catch (InternalErrorException ex){
            log.info("getUsers: error {}", ex.getMessage());
            throw new InternalErrorException("a error has occurred into the service, please try in another moment");
        }
    }

    @Override
    public ResponseEntity<?> getRoleUsers(String roleId) {
        roleId = roleId.toUpperCase();
        if(roleId.matches(Roles.DEVELOPER.name())|| roleId.matches(Roles.ARCHITECT.name())){
            try {
                List<User> data = userRepository.findByRole(roleId);
                if(data.isEmpty())
                    throw new NotFoundException("user with role ".concat(roleId).concat(" not exists"));
                return new ResponseEntity(data, HttpStatus.OK);
            }catch (InternalErrorException ex){
                log.info("getRoleUsers: error {}", ex.getMessage());
                throw new InternalErrorException("a error has occurred into the service, please try in another moment");
            }
        }
        throw new BadCredentialsException("this role is not allowed");
    }

    @Override
    public ResponseEntity<?> postUser(User user) {
        user.setRole(user.getRole().toUpperCase());
        if(user.getRole().matches(Roles.DEVELOPER.name())|| user.getRole().matches(Roles.ARCHITECT.name())){
            try {
                User data = userRepository.save(user);
                if(Objects.isNull(data))
                    throw new NotFoundException("user not created");
                return new ResponseEntity(data, HttpStatus.OK);
            }catch (InternalErrorException ex){
                log.info("postUsers: error {}", ex.getMessage());
                throw new InternalErrorException("a error has occurred into the service, please try in another moment");
            }
        }
        throw new BadCredentialsException("this role is not allowed");
    }

    @Override
    public ResponseEntity<?> putUser(User user, Integer userId) {
        user.setRole(user.getRole().toUpperCase());
        if(user.getRole().matches(Roles.DEVELOPER.name())|| user.getRole().matches(Roles.ARCHITECT.name())){
            try{
                User data = userRepository.findById(userId).orElse(null);
                if(Objects.isNull(data))
                    throw new NotFoundException("user not updated");
                user.setUserId(userId);
                userRepository.save(user);
                return new ResponseEntity(user, HttpStatus.OK);
            }catch (InternalErrorException ex){
                log.info("putUsers: error {}", ex.getMessage());
                throw new InternalErrorException("a error has occurred into the service, please try in another moment");
            }
        }
        throw new BadCredentialsException("this role is not allowed");
    }

    @Override
    public ResponseEntity<?> patchUser(JsonPatch patch, Integer userId) throws JsonPatchException {
        User data = userRepository.findById(userId).orElse(null);
        if(Objects.isNull(data))
            throw new NotFoundException("user not updated");
        JsonNode patched = patch.apply(new ObjectMapper().convertValue(data,JsonNode.class));
        User dataUpdate = new ObjectMapper().convertValue(patched, User.class);
        dataUpdate.setRole(dataUpdate.getRole().toUpperCase());
        if(dataUpdate.getRole().matches(Roles.DEVELOPER.name())|| dataUpdate.getRole().matches(Roles.ARCHITECT.name()))
            return new ResponseEntity<>(userRepository.save(dataUpdate),HttpStatus.OK);
        throw new BadCredentialsException("this role is not allowed");
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer userId) {
        try {
            userRepository.deleteById(userId);
            return ResponseEntity.ok(null);
        }catch (InternalErrorException ex){
            log.info("getUser: error {}", ex.getMessage());
            throw new InternalErrorException("a error has occurred into the service, please try in another moment");
        }
    }
}
