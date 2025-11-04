package com.meridian.api.author;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorModelAssembler authorsModelAssembler;

    @Autowired
    private AuthorService authorService;

    public AuthorController(AuthorModelAssembler assembler) {

        this.authorsModelAssembler = assembler;
    }

    // TODO: only allowed in dev environments
    @PostMapping("/authors")
    public ResponseEntity<EntityModel<Author>> createAuthor(@RequestBody Author newAuthor) {

        EntityModel<Author> entityModel =
                authorsModelAssembler.toModel(authorService.createAuthor(newAuthor));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // TODO: only allowed in dev environments
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<EntityModel<Author>> deleteAuthorById(@PathVariable("id") Long id) {

        authorService.deleteAuthorById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/authors")
    public CollectionModel<EntityModel<Author>> getAllAuthors() {

        List<EntityModel<Author>> platforms =
                authorService.getAllAuthors().stream()
                        .map(authorsModelAssembler::toModel)
                        .toList();

        return CollectionModel.of(
                platforms,
                linkTo(methodOn(AuthorController.class).getAllAuthors()).withSelfRel());
    }

    @GetMapping("/authors/{id}")
    public EntityModel<Author> getAuthorById(@PathVariable("id") Long id) {

        Author author = authorService.getAuthorById(id);

        return authorsModelAssembler.toModel(author);
    }

    // TODO: only allowed in dev environments
    @PutMapping("/authors/{id}")
    public ResponseEntity<EntityModel<Author>> updateAuthor(
            @RequestBody Author updatedAuthor, @PathVariable("id") Long id) {

        Author updateAuthor = authorService.updateAuthor(updatedAuthor, id);

        EntityModel<Author> entityModel = authorsModelAssembler.toModel(updateAuthor);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
