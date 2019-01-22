package ca.pcraig3.holidays.province;

import ca.pcraig3.holidays.holiday.Holiday;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;


public class ProvinceResource extends ResourceSupport {

    @Getter
    @Setter
    private String nameEn;

    @Getter
    @Setter
    private String nameFr;

    @Getter
    @Setter
    private Resources<Resource<Holiday>> holidays;
}
