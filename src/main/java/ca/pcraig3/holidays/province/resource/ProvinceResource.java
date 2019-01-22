package ca.pcraig3.holidays.province.resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

public class ProvinceResource extends ResourceSupport {

    @Getter
    @Setter
    private String nameEn;

    @Getter
    @Setter
    private String nameFr;
}