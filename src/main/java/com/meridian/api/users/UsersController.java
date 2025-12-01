package com.meridian.api.users;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    // TODO: only allowed in dev environments
    @PostMapping("/users")
    public ResponseEntity<UsersDTO> createUser(@Valid @RequestBody UsersDTO newAuthor) {

        UsersDTO entity = usersService.createUser(newAuthor);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    // TODO: only allowed in dev environments
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UsersDTO> deleteUserById(@PathVariable("id") Long id) {

        usersService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsersDTO>> getAllUsers() {

        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") Long id) {

        UsersDTO user = usersService.getUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // TODO: only allowed in dev environments
    @PutMapping("/users/{id}")
    public ResponseEntity<UsersDTO> updateUser(
            @Valid @RequestBody UsersDTO usersDTO, @PathVariable("id") Long id) {

        UsersDTO updateAuthor = usersService.updateUser(usersDTO, id);

        return new ResponseEntity<>(updateAuthor, HttpStatus.OK);
    }
}
