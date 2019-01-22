package ca.pcraig3.holidays.province;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
class ProvinceResourceAssembler implements ResourceAssembler<Province, Resource<Province>> {

    @Override
    public Resource<Province> toResource(Province province) {

        return new Resource<>(province,
                linkTo(methodOn(ProvinceController.class).one(province.getId())).withSelfRel(),
                linkTo(methodOn(ProvinceController.class).all()).withRel("provinces"));
    }
}