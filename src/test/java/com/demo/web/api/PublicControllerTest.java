package com.demo.web.api;

import com.demo.entity.Location;
import com.demo.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicControllerTest {

    @InjectMocks private PublicController controllerInTest;

    @Mock private LocationService locationService;

    @BeforeEach
    void setUp() {
        lenient().when(locationService.getLocationViaPostcode(anyString())).thenReturn(new Location());
        lenient().when(locationService.updateLocation(any(Location.class))).thenReturn(new Location());
    }

    @Test
    void distanceBetweenTwoPostcode() {
        // Case: Happy Path
        ResponseEntity<Object> responseEntity = controllerInTest.distanceBetweenTwoPostcode("AB10 1XG", "AB10 7JB");
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Case: Invalid parameter value
        responseEntity = controllerInTest.distanceBetweenTwoPostcode("", "AB10 7JB");
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is4xxClientError());

        // Case: NPE
        when(locationService.getLocationViaPostcode(anyString())).thenReturn(null);
        responseEntity = controllerInTest.distanceBetweenTwoPostcode("AB10 1XG", "AB10 7JB");
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is5xxServerError());
    }

    @Test
    void retrieveLocation() {
        ResponseEntity<Object> responseEntity = controllerInTest.retrieveLocation("AB10 1XG");
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Case: Invalid parameter value
        responseEntity = controllerInTest.retrieveLocation("");
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is4xxClientError());

        // Case: NPE
        when(locationService.getLocationViaPostcode(anyString())).thenReturn(null);
        responseEntity = controllerInTest.retrieveLocation("AB10 1XG");
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is5xxServerError());
    }

    @Test
    void updateLocation() {
        ResponseEntity<Object> responseEntity = controllerInTest.updateLocation("AB10 1XG", Map.of("latitude", 50.00, "longitude", -6.00));
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Case: Invalid parameter value
        responseEntity = controllerInTest.updateLocation("", null);
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is4xxClientError());
    }
}