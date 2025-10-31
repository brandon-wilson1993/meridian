package com.meridian.api.authors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorsController {

    private final AuthorsRepository authorsRepository;
    private final AuthorsModelAssembler authorsModelAssembler;

    public AuthorsController(AuthorsRepository authorsRepository, AuthorsModelAssembler assembler) {

        this.authorsRepository = authorsRepository;
        this.authorsModelAssembler = assembler;
    }

    // TODO: only allowed in dev environments
    @PostMapping("/authors")
    public ResponseEntity<?> createAuthor(Authors newAuthor) {

        EntityModel<Authors> entityModel =
                authorsModelAssembler.toModel(authorsRepository.save(newAuthor));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // TODO: only allowed in dev environments
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") Long id) {

        authorsRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/authors")
    public CollectionModel<EntityModel<Authors>> getAllAuthors() {

        List<EntityModel<Authors>> platforms =
                authorsRepository.findAll().stream()
                        .map(authorsModelAssembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                platforms,
                linkTo(methodOn(AuthorsController.class).getAllAuthors()).withSelfRel());
    }

    @GetMapping("/authors/{id}")
    public EntityModel<Authors> getAuthorById(@PathVariable("id") Long id) {

        Authors platform =
                authorsRepository
                        .findById(id)
                        .orElseThrow(() -> new AuthorsNotFoundException(id));

        return authorsModelAssembler.toModel(platform);
    }

    // TODO: only allowed in dev environments
    @PutMapping("/authors/{id}")
    public ResponseEntity<?> updateAuthor(
            @RequestBody Authors updatedAuthor, @PathVariable("id") Long id) {

        Authors updateAuthor =
                authorsRepository
                        .findById(id)
                        .map(
                                platform -> {
                                    platform.setFirstName(updatedAuthor.getFirstName());
                                    platform.setLastName(updatedAuthor.getLastName());
                                    return authorsRepository.save(platform);
                                })
                        .orElseGet(
                                () -> {
                                    return authorsRepository.save(updatedAuthor);
                                });

        EntityModel<Authors> entityModel = authorsModelAssembler.toModel(updateAuthor);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
