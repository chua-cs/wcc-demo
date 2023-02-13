package com.demo.service;

import com.demo.entity.Location;
import com.demo.repository.LocationRepository;
import com.demo.utility.DistanceUtil;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * To obtain the {@link Location} associated with a postcode.
     *
     * @param postcode The postcode value.
     * @return {@link Location}
     */
    public Location getLocationViaPostcode(String postcode) {
        return locationRepository.findLocationViaPostcode(postcode);
    }

    /**
     * To obtain the distance between two {@link Location}.
     *
     * @param firstLocation {@link Location}
     * @param secondLocation {@link Location}
     * @return The distance (in double).
     */
    public double getDistanceBetweenLocation(Location firstLocation, Location secondLocation) {
        return DistanceUtil.calculateDistance(firstLocation, secondLocation);
    }

    /**
     * To update a {@link Location} object.
     * @param location The {@link Location} object to be updated.
     * @return The updated {@link Location}.
     */
    public Location updateLocation(Location location) {
        return locationRepository.save(location);
    }
}
