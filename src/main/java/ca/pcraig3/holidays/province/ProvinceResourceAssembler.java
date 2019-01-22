package ca.pcraig3.holidays.province;

import ca.pcraig3.holidays.holiday.Holiday;
import ca.pcraig3.holidays.holiday.HolidayController;
import ca.pcraig3.holidays.holiday.HolidayResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ProvinceResourceAssembler extends ResourceAssemblerSupport<Province, ProvinceResource> {

    ProvinceResourceAssembler() {
        super(ProvinceController.class, ProvinceResource.class);
    }

    @Override
    public ProvinceResource toResource(Province province) {

        ProvinceResource pr = super.createResourceWithId(province.getId(), province);
        pr.setNameEn(province.getNameEn());
        pr.setNameFr(province.getNameFr());

        HolidayResourceAssembler hra = new HolidayResourceAssembler();

        List<Resource<Holiday>> h2 = new ArrayList<>(province.getHolidays())
                .stream()
                .map(hra::toResource)
                .collect(Collectors.toList());

        pr.setHolidays(new Resources<>(h2));

        /*
        return new Resource<>(province,

                linkTo(methodOn(ProvinceController.class).one(province.getId())).withSelfRel(),
                linkTo(methodOn(ProvinceController.class).all()).withRel("provinces"));
        */
        return pr;

    }
}