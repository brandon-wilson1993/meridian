package com.meridian.api.authors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorsModelAssembler
        implements RepresentationModelAssembler<Authors, EntityModel<Authors>> {

    @Override
    public EntityModel<Authors> toModel(Authors author) {

        return EntityModel.of(
                author,
                linkTo(methodOn(AuthorsController.class).getAuthorById(author.getId()))
                        .withSelfRel(),
                linkTo(methodOn(AuthorsController.class).getAllAuthors()).withRel("authors"));
    }
}
