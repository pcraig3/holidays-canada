package ca.pcraig3.holidays.holiday;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class HolidayResourceAssembler implements ResourceAssembler<Holiday, Resource<Holiday>> {

    @Override
    public Resource<Holiday> toResource(Holiday holiday) {

        return new Resource<>(holiday,
                linkTo(methodOn(HolidayController.class).one(holiday.getId())).withSelfRel(),
                linkTo(methodOn(HolidayController.class).all(null, null)).withRel("holidays").expand());
    }
}