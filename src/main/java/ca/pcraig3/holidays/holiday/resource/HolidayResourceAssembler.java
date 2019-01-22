package ca.pcraig3.holidays.holiday.resource;

import ca.pcraig3.holidays.holiday.Holiday;
import ca.pcraig3.holidays.holiday.HolidayController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class HolidayResourceAssembler extends ResourceAssemblerSupport<Holiday, HolidayResource> {

    public HolidayResourceAssembler() {
        super(HolidayController.class, HolidayResource.class);
    }

    @Override
    public HolidayResource toResource(Holiday holiday) {

    HolidayResource hr = super.createResourceWithId(holiday.getId(), holiday);
    hr.setDate(holiday.getDate());
    hr.setNameEn(holiday.getNameEn());
    hr.setNameFr(holiday.getNameFr());
    hr.setNational(holiday.getNational());

    return hr;
    }
}

