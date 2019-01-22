package ca.pcraig3.holidays.province;

import ca.pcraig3.holidays.holiday.resource.HolidayResource;
import ca.pcraig3.holidays.holiday.resource.HolidayResourceAssembler;
import ca.pcraig3.holidays.province.resource.ProvinceResource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class ProvinceHolidayResourceAssembler extends ResourceAssemblerSupport<Province, ProvinceHolidayResource> {

    ProvinceHolidayResourceAssembler() {
        super(ProvinceController.class, ProvinceHolidayResource.class);
    }

    @Override
    public ProvinceHolidayResource toResource(Province province) {

        ProvinceHolidayResource phr = super.createResourceWithId(province.getId(), province);
        phr.setNameEn(province.getNameEn());
        phr.setNameFr(province.getNameFr());

        HolidayResourceAssembler hra = new HolidayResourceAssembler();

        List<HolidayResource> h2 = new ArrayList<>(province.getHolidays())
                .stream()
                .map(hra::toResource)
                .collect(Collectors.toList());

        phr.setHolidays(new Resources<HolidayResource>(h2));

        return phr;
    }
}


class ProvinceHolidayResource extends ProvinceResource {

    @Getter
    @Setter
    private Resources<HolidayResource> holidays;
}
