package com.demo.utility;

import com.demo.entity.Location;

public final class DistanceUtil {

    private final static double EARTH_RADIUS_KILOMETER = 6371;

    /**
     * To calculate the distance between two locations, based on respective coordinates.
     * See: <a href="https://en.wikipedia.org/wiki/Haversine_formula">Wikipedia - Haversine Formula</a>
     *
     * @param locationOne First {@link Location} point
     * @param locationTwo Second {@link Location} point
     * @return The distance between two locations.
     */
    public static double calculateDistance(Location locationOne, Location locationTwo) {
        if (null == locationOne || null == locationTwo) {
            throw new IllegalArgumentException("Parameter(s) cannot be null.");
        }

        double lon1Radians = Math.toRadians(locationOne.getLongitude());
        double lon2Radians = Math.toRadians(locationTwo.getLongitude());

        double lat1Radians = Math.toRadians(locationOne.getLatitude());
        double lat2Radians = Math.toRadians(locationTwo.getLatitude());

        double a = haversine(lat1Radians, lat2Radians) +
                Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS_KILOMETER * c);
    }

    private static double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private static double square(double x) {
        return x * x;
    }
}
