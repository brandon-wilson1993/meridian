package com.meridian.api.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    private final UsersModelAssembler usersModelAssembler;

    @Autowired
    private UsersService usersService;

    public UsersController(UsersModelAssembler assembler) {

        this.usersModelAssembler = assembler;
    }

    // TODO: only allowed in dev environments
    @PostMapping("/authors")
    public ResponseEntity<EntityModel<Users>> createUser(@RequestBody Users newAuthor) {

        EntityModel<Users> entityModel =
                usersModelAssembler.toModel(usersService.createUser(newAuthor));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // TODO: only allowed in dev environments
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<EntityModel<Users>> deleteUserById(@PathVariable("id") Long id) {

        usersService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/authors")
    public CollectionModel<EntityModel<Users>> getAllUsers() {

        List<EntityModel<Users>> platforms =
                usersService.getAllUsers().stream()
                        .map(usersModelAssembler::toModel)
                        .toList();

        return CollectionModel.of(
                platforms,
                linkTo(methodOn(UsersController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/authors/{id}")
    public EntityModel<Users> getUserById(@PathVariable("id") Long id) {

        Users author = usersService.getUserById(id);

        return usersModelAssembler.toModel(author);
    }

    // TODO: only allowed in dev environments
    @PutMapping("/authors/{id}")
    public ResponseEntity<EntityModel<Users>> updateUser(
            @RequestBody Users updatedAuthor, @PathVariable("id") Long id) {

        Users updateAuthor = usersService.updateUser(updatedAuthor, id);

        EntityModel<Users> entityModel = usersModelAssembler.toModel(updateAuthor);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
