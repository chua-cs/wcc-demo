package com.demo.service;

import com.demo.entity.Location;
import com.demo.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @InjectMocks private LocationService serviceInTest;

    @Mock private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        lenient().when(locationRepository.findLocationViaPostcode(anyString())).thenReturn(new Location());
    }

    @Test
    void getLocationViaPostcode() {
        Location location = serviceInTest.getLocationViaPostcode("AB10 1XG");
        assertNotNull(location);
    }

    @Test
    void getDistanceBetweenLocation() {
        double outcome = serviceInTest.getDistanceBetweenLocation(new Location(), new Location());
        assertTrue(outcome >= 0.0);

        assertThrowsExactly(IllegalArgumentException.class,
                () -> serviceInTest.getDistanceBetweenLocation(null, new Location()));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> serviceInTest.getDistanceBetweenLocation(new Location(), null));
    }
}