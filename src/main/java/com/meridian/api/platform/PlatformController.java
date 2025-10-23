package com.meridian.api.platform;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlatformController {

    private final PlatformRepository platformRepository;

    public PlatformController(PlatformRepository platformRepository) {

        this.platformRepository = platformRepository;
    }

    // TODO: only allowed in dev environments
    @PostMapping("/platform")
    public Platform createPlatform(Platform newPlatform) {

        return platformRepository.save(newPlatform);
    }

    // TODO: only allowed in dev environments
    @DeleteMapping("/platform/{id}")
    public void deletePlatform(@PathVariable("id") Long id) {

        platformRepository.deleteById(id);
    }

    @GetMapping("/platform")
    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll();
    }

    @GetMapping("/platform/{id}")
    public EntityModel<Platform> getPlatformById(@PathVariable("id") Long id) {

        Platform platform =
                platformRepository
                        .findById(id)
                        .orElseThrow(() -> new PlatformNotFoundException(id));

        return EntityModel.of(
                platform,
                linkTo(methodOn(PlatformController.class).getPlatformById(id)).withSelfRel(),
                linkTo(methodOn(PlatformController.class).getAllPlatforms()).withRel("platform"));
    }

    // TODO: only allowed in dev environments
    @PutMapping("/platform/{id}")
    public Platform updatePlatform(
            @RequestBody Platform updatedPlatform, @PathVariable("id") Long id) {

        return platformRepository
                .findById(id)
                .map(
                        platform -> {
                            platform.setManufacturer(updatedPlatform.getManufacturer());
                            platform.setPlatormName(updatedPlatform.getPlatormName());
                            return platformRepository.save(platform);
                        })
                .orElseGet(
                        () -> {
                            return platformRepository.save(updatedPlatform);
                        });
    }
}
