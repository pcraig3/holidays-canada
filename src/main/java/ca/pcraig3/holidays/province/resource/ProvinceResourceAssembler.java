package ca.pcraig3.holidays.province.resource;

import ca.pcraig3.holidays.province.Province;
import ca.pcraig3.holidays.province.ProvinceController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProvinceResourceAssembler extends ResourceAssemblerSupport<Province, ProvinceResource> {

    public ProvinceResourceAssembler() {
        super(ProvinceController.class, ProvinceResource.class);
    }

    @Override
    public ProvinceResource toResource(Province province) {

        ProvinceResource phr = super.createResourceWithId(province.getId(), province);
        phr.setNameEn(province.getNameEn());
        phr.setNameFr(province.getNameFr());

        /*

        linkTo(methodOn(HolidayController.class).one(holiday.getId())).withSelfRel(),
-       linkTo(methodOn(HolidayController.class).all(null, null)).withRel("holidays").expand());
         */

        return phr;
    }
}