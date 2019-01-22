package ca.pcraig3.holidays.holiday;

import ca.pcraig3.holidays.holiday.resource.HolidayResource;
import ca.pcraig3.holidays.province.resource.ProvinceResource;
import ca.pcraig3.holidays.province.resource.ProvinceResourceAssembler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class HolidayProvinceResourceAssembler extends ResourceAssemblerSupport<Holiday, HolidayProvinceResource> {

    public HolidayProvinceResourceAssembler() {
        super(HolidayController.class, HolidayProvinceResource.class);
    }

    @Override
    public HolidayProvinceResource toResource(Holiday holiday) {

        HolidayProvinceResource hpr = super.createResourceWithId(holiday.getId(), holiday);
        hpr.setDate(holiday.getDate());
        hpr.setNameEn(holiday.getNameEn());
        hpr.setNameFr(holiday.getNameFr());


        ProvinceResourceAssembler pra = new ProvinceResourceAssembler();

        List<ProvinceResource> p2 = new ArrayList<>(holiday.getProvinces())
                .stream()
                .map(pra::toResource)
                .collect(Collectors.toList());

        hpr.setProvinces(new Resources<>(p2));

        return hpr;
    }
}

class HolidayProvinceResource extends HolidayResource {

    @Getter
    @Setter
    private Resources<ProvinceResource> provinces;
}
