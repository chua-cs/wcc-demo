package com.demo.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The purpose of this class is to introduce a standardized REST API Response format.
 */
public abstract class CustomResponse {

    /**
     * Generate the {@link ResponseEntity}.
     *
     * @param status {@link HttpStatus}
     * @param message The response message to be included.
     * @param responseObject {@link Object}. Preferably JSON serializable object, eg: ArrayList/Map/Object.
     * @param isError <code>true</code> if this response is to notify regarding error(s), else <code>false</code>.
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<Object> buildResponse(
            HttpStatus status,
            String message,
            Object responseObject,
            boolean isError
    ) {
        Map<String, Object> responseBody = new HashMap<>(3);
        responseBody.put("message", message);
        responseBody.put("error", isError);

        if (responseObject instanceof Serializable || responseObject instanceof Collection<?>) {
            responseBody.put("data", responseObject);
        }

        return new ResponseEntity<>(responseBody, status);
    }

    /**
     * Calling this method will always carry value "error" = <code>false</code> by default.<br/>
     * To produce "error" = true, see: <p>{@link #buildErrorResponse(HttpStatus, String)}</p>
     *
     * @param status {@link HttpStatus}
     * @param message The response message to be included.
     * @param responseObject {@link Object}. Preferably JSON serializable object, eg: ArrayList/Map/Object.
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<Object> buildResponse(HttpStatus status, String message, Object responseObject) {
        return buildResponse(status, message, responseObject, false);
    }

    /**
     * Calling this method will always carry value "error" = <code>true</code> by default.<br/>
     * Use this method to generate a simple response to indicates erroneous process.
     *
     * @param status {@link HttpStatus}
     * @param message The response message to be included.
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        return buildResponse(status, message, null, true);
    }
}