package ca.pcraig3.holidays.province;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Slf4j
@RequestMapping(value = "/provinces", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
public class ProvinceController {

    private final ProvinceRepository repository;
    private final ProvinceResourceAssembler assembler;

    ProvinceController(ProvinceRepository repository, ProvinceResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    Resources<Resource<Province>> all() {
        log.info("Get all '/provinces/'");
        
        List<Resource<Province>> provinces = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(provinces,
                linkTo(methodOn(ProvinceController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    Resource<Province> one(@PathVariable String id) {
        log.info("Get '/provinces/" + id + "'");
        String provinceId = id.toUpperCase();

        Province province = this.repository.findById(provinceId).orElseThrow(
                () -> new ProvinceBadRequestException(provinceId));

        return this.assembler.toResource(province);
    }
}
