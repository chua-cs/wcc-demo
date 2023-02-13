package com.demo.repository;

import com.demo.entity.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends CrudRepository<Location, Long> {

    /**
     * Fetch the {@link Location} associated with the postcode.
     *
     * @param postcode The postcode value.
     * @return {@link Location}
     */
    @Query(value = "SELECT * FROM Location l WHERE l.postcode = :postcode", nativeQuery = true)
    Location findLocationViaPostcode(@Param("postcode") String postcode);
}
