package com.demo.web.api;

import com.demo.entity.Location;
import com.demo.service.LocationService;
import com.demo.web.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/public")
public class PublicController extends CustomResponse {

    private static final String UNIT_KILOMETER = "kilometer";

    private final LocationService locationService;

    public PublicController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * [GET] - To calculate the distance (in kilometer) between two postcodes.
     *
     * @param postcode1 First postcode
     * @param postcode2 Second postcode
     * @return {@link ResponseEntity}, which will produce response in JSON String representation.
     */
    @GetMapping("/distance")
    public ResponseEntity<Object> distanceBetweenTwoPostcode(
            @RequestParam(value = "postcode_1") String postcode1,
            @RequestParam(value = "postcode_2") String postcode2
    ) {
        if (StringUtils.isBlank(postcode1) || StringUtils.isBlank(postcode2)) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid parameter value(s).");
        }
        log.info(String.format("Received parameter(s): %s, %s", postcode1, postcode2));

        try {
            Location firstLocation = locationService.getLocationViaPostcode(postcode1);
            Location secondLocation = locationService.getLocationViaPostcode(postcode2);

            double distance = locationService.getDistanceBetweenLocation(firstLocation, secondLocation);

            return buildResponse(
                    HttpStatus.OK,
                    String.format("The distance between two locations is %.3fKM.", distance),
                    Map.of(
                            "distance", distance,
                            "unit", UNIT_KILOMETER,
                            "locations", List.of(firstLocation, secondLocation)
                    )
            );
        } catch(Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Operation unable to complete.");
    }

    /**
     * [GET] - To fetch the information regarding a location, via postcode.
     *
     * @param postcode The postcode value.
     * @return {@link ResponseEntity}, which will produce response in JSON String representation.
     */
    @GetMapping("/location/{postcode}")
    public ResponseEntity<Object> retrieveLocation(@PathVariable String postcode) {
        if (StringUtils.isBlank(postcode)) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid parameter value(s).");
        }
        log.info(String.format("Received parameter(s): %s", postcode));

        try {
            Location location = locationService.getLocationViaPostcode(postcode);

            if (null != location) {
                return buildResponse(
                        HttpStatus.OK,
                        String.format("Successfully fetch postcode: %s", postcode),
                        Map.of("location", location)
                );
            } else {
                log.info(String.format("Postcode: <%s> not found.", postcode));
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Operation unable to complete.");
    }

    /**
     * [PUT] - To update the information of a location.
     *
     * @param postcode The postcode value.
     * @param json The request body in JSON String.
     * @return {@link ResponseEntity}, which will produce response in JSON String representation.
     */
    @PutMapping("/location/{postcode}")
    public ResponseEntity<Object> updateLocation(@PathVariable String postcode, @RequestBody Map<String, Object> json) {
        if (StringUtils.isBlank(postcode) || MapUtils.isEmpty(json)) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid parameter value(s).");
        }
        log.info(String.format("Received parameter(s): %s", postcode));

        try {
            Location location = locationService.getLocationViaPostcode(postcode);
            double latitude = (double) json.get("latitude");
            double longitude = (double) json.get("longitude");

            if (null != location) {
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                Location updatedLocation = locationService.updateLocation(location);

                return buildResponse(
                        HttpStatus.OK,
                        String.format("Successfully updated postcode: %s", postcode),
                        Map.of("location", updatedLocation)
                );
            } else {
                log.info(String.format("Postcode: <%s> not found.", postcode));
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Operation unable to complete.");
    }
}
