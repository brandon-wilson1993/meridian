package com.meridian.api.platform;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlatformController {

    private final PlatformRepository platformRepository;
    private final PlatformModelAssembler platformModelAssembler;

    public PlatformController(PlatformRepository platformRepository, PlatformModelAssembler assembler) {

        this.platformRepository = platformRepository;
        this.platformModelAssembler = assembler;
    }

    // TODO: only allowed in dev environments
    @PostMapping("/platform")
    public ResponseEntity<?> createPlatform(Platform newPlatform) {

        EntityModel<Platform> entityModel =
                platformModelAssembler.toModel(platformRepository.save(newPlatform));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // TODO: only allowed in dev environments
    @DeleteMapping("/platform/{id}")
    public ResponseEntity<?> deletePlatform(@PathVariable("id") Long id) {

        platformRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/platform")
    public CollectionModel<EntityModel<Platform>> getAllPlatforms() {

        List<EntityModel<Platform>> platforms =
                platformRepository.findAll().stream()
                        .map(platformModelAssembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                platforms,
                linkTo(methodOn(PlatformController.class).getAllPlatforms()).withSelfRel());
    }

    @GetMapping("/platform/{id}")
    public EntityModel<Platform> getPlatformById(@PathVariable("id") Long id) {

        Platform platform =
                platformRepository
                        .findById(id)
                        .orElseThrow(() -> new PlatformNotFoundException(id));

        return platformModelAssembler.toModel(platform);
    }

    // TODO: only allowed in dev environments
    @PutMapping("/platform/{id}")
    public ResponseEntity<?> updatePlatform(
            @RequestBody Platform updatedPlatform, @PathVariable("id") Long id) {

        Platform updatePlatform =
                platformRepository
                        .findById(id)
                        .map(
                                platform -> {
                                    platform.setPlatormName(updatedPlatform.getPlatormName());
                                    platform.setManufacturer(updatedPlatform.getManufacturer());
                                    return platformRepository.save(platform);
                                })
                        .orElseGet(
                                () -> {
                                    return platformRepository.save(updatedPlatform);
                                });

        EntityModel<Platform> entityModel = platformModelAssembler.toModel(updatePlatform);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
