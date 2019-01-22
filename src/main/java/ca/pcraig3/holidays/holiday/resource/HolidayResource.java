package ca.pcraig3.holidays.holiday.resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;


public class HolidayResource extends ResourceSupport {

    @Getter
    @Setter
    private String date;

    @Getter
    @Setter
    private String nameEn;

    @Getter
    @Setter
    private String nameFr;

    @Getter
    @Setter
    private boolean national;
}