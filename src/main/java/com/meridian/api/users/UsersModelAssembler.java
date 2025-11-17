package com.meridian.api.users;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsersModelAssembler
        implements RepresentationModelAssembler<Users, EntityModel<Users>> {

    @Override
    public EntityModel<Users> toModel(Users author) {

        return EntityModel.of(
                author,
                linkTo(methodOn(UsersController.class).getUserById(author.getId()))
                        .withSelfRel(),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("authors"));
    }
}
