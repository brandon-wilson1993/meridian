package com.meridian.api.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersModelAssembler usersModelAssembler;

    @Autowired private UsersService usersService;

    public UsersController(UsersModelAssembler assembler) {

        this.usersModelAssembler = assembler;
    }

    // TODO: only allowed in dev environments
    @PostMapping("/users")
    public ResponseEntity<UsersDTO> createUser(@Valid @RequestBody UsersDTO newAuthor) {

        logger.info("Creating new user: {} {}", newAuthor.getFirstName(), newAuthor.getLastName());
//        EntityModel<Users> entityModel =
//                usersModelAssembler.toModel(usersService.createUser(newAuthor));

        UsersDTO entity = usersService.createUser(newAuthor);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    // TODO: only allowed in dev environments
    @DeleteMapping("/users/{id}")
    public ResponseEntity<EntityModel<Users>> deleteUserById(@PathVariable("id") Long id) {

        usersService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<Users>> getAllUsers() {

        List<EntityModel<Users>> platforms =
                usersService.getAllUsers().stream().map(usersModelAssembler::toModel).toList();

        return CollectionModel.of(
                platforms, linkTo(methodOn(UsersController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<Users> getUserById(@PathVariable("id") Long id) {

        Users author = usersService.getUserById(id);

        return usersModelAssembler.toModel(author);
    }

    // TODO: only allowed in dev environments
    @PutMapping("/users/{id}")
    public ResponseEntity<EntityModel<Users>> updateUser(
            @RequestBody Users updatedAuthor, @PathVariable("id") Long id) {

        Users updateAuthor = usersService.updateUser(updatedAuthor, id);

        EntityModel<Users> entityModel = usersModelAssembler.toModel(updateAuthor);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
