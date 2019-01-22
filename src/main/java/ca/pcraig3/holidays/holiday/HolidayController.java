package ca.pcraig3.holidays.holiday;

import ca.pcraig3.holidays.province.Province;
import ca.pcraig3.holidays.province.ProvinceBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Slf4j
@RequestMapping(value = "/holidays", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
public class HolidayController {

    private final HolidayRepository repository;
    private final HolidayResourceAssembler assembler;

    HolidayController(HolidayRepository repository, HolidayResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    Resources<Resource<Holiday>> all(@RequestParam(value = "national", required=false) Boolean national, @RequestParam(value = "province", required=false) String provinceId) {
        List<Resource<Holiday>> holidays = null;

        if(national != null) {
            String message = national ? "national" : "non-national";

            holidays = list2Resource(this.repository.findByIsNational(national));
            log.info(String.format("Get all %s '/holidays'. Found: %d.", message, holidays.size()));
        }

        // string should not be null or empty
        if(!StringUtils.isEmpty(provinceId)){
            provinceId = provinceId.toUpperCase();

            // make sure we have a valid province ID
            if(!Province.isProvinceId(provinceId))
                throw new ProvinceBadRequestException(provinceId);

            holidays = list2Resource(this.repository.findByProvinceId(provinceId));
            log.info(String.format("Get '/holidays' for province '%s'. Found: %d.", provinceId, holidays.size()));
        }

        if(holidays == null) {
            holidays = list2Resource(this.repository.findAll());
            log.info(String.format("Get all '/holidays'. Found: %d.", holidays.size()));
        }

        return new Resources<>(holidays,
                linkTo(methodOn(HolidayController.class).all(national, provinceId)).withSelfRel().expand());
    }

    private List<Resource<Holiday>> list2Resource(List<Holiday> holidays) {
        return holidays.stream().map(assembler::toResource).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    Resource<Holiday> one(@PathVariable Long id) {
        log.info("Get '/holidays/" + id + "'");
        Holiday holiday = this.repository.findById(id).orElseThrow(
                () -> new HolidayNotFoundException(id));

        return this.assembler.toResource(holiday);
    }

}

