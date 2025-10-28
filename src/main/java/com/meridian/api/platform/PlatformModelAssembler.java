package com.meridian.api.platform;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlatformModelAssembler
        implements RepresentationModelAssembler<Platform, EntityModel<Platform>> {

    @Override
    public EntityModel<Platform> toModel(Platform platform) {

        return EntityModel.of(
                platform,
                linkTo(methodOn(PlatformController.class).getPlatformById(platform.getId()))
                        .withSelfRel(),
                linkTo(methodOn(PlatformController.class).getAllPlatforms()).withRel("platforms"));
    }
}
