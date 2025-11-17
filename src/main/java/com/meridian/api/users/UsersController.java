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

    private final UsersModelAssembler authorsModelAssembler;

    @Autowired
    private AuthorService authorService;

    public UsersController(UsersModelAssembler assembler) {

        this.authorsModelAssembler = assembler;
    }

    // TODO: only allowed in dev environments
    @PostMapping("/authors")
    public ResponseEntity<EntityModel<Users>> createAuthor(@RequestBody Users newAuthor) {

        EntityModel<Users> entityModel =
                authorsModelAssembler.toModel(authorService.createAuthor(newAuthor));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // TODO: only allowed in dev environments
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<EntityModel<Users>> deleteAuthorById(@PathVariable("id") Long id) {

        authorService.deleteAuthorById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/authors")
    public CollectionModel<EntityModel<Users>> getAllAuthors() {

        List<EntityModel<Users>> platforms =
                authorService.getAllAuthors().stream()
                        .map(authorsModelAssembler::toModel)
                        .toList();

        return CollectionModel.of(
                platforms,
                linkTo(methodOn(UsersController.class).getAllAuthors()).withSelfRel());
    }

    @GetMapping("/authors/{id}")
    public EntityModel<Users> getAuthorById(@PathVariable("id") Long id) {

        Users author = authorService.getAuthorById(id);

        return authorsModelAssembler.toModel(author);
    }

    // TODO: only allowed in dev environments
    @PutMapping("/authors/{id}")
    public ResponseEntity<EntityModel<Users>> updateAuthor(
            @RequestBody Users updatedAuthor, @PathVariable("id") Long id) {

        Users updateAuthor = authorService.updateAuthor(updatedAuthor, id);

        EntityModel<Users> entityModel = authorsModelAssembler.toModel(updateAuthor);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
