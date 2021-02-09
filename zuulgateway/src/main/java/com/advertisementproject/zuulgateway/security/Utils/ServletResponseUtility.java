package com.advertisementproject.zuulgateway.security.Utils;

import com.advertisementproject.zuulgateway.api.exceptions.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;

/**
 * Utility for sending responses to the client that made a request, both successful login responses as well as any
 * type of error response.
 */
@Slf4j
public class ServletResponseUtility {

    /**
     * Sends a response to the client. Meant for a successful response and used after successful login
     *
     * @param response     the response to be sent
     * @param status       the status of the response
     * @param responseBody the response body to be sent
     * @param <T>          any type can be accepted as a response body
     * @throws IOException if a data read/write error occurs
     */
    public static <T> void sendResponse(HttpServletResponse response,
                                        Integer status,
                                        T responseBody) throws IOException {

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
        response.getOutputStream().flush();
    }

    /**
     * Sends an error response to the client
     *
     * @param response    the response to be sent
     * @param responseMsg the message to include in the response
     * @param statusCode  the error status code
     * @throws IOException if a data read/write error occurs
     */
    public static void sendErrorResponse(HttpServletResponse response,
                                         String responseMsg,
                                         int statusCode) throws IOException {
        ApiError apiError = toErrorResponse(responseMsg, statusCode);
        log.warn(apiError.toString());
        sendResponse(response, apiError.getStatusCode(), apiError);
    }

    /**
     * Helper method to create and format an error response object
     *
     * @param responseMsg error message
     * @param statusCode  error status code
     * @return error response object
     */
    private static ApiError toErrorResponse(String responseMsg, int statusCode) {
        return ApiError.builder()
                .timestamp(toCurrentDate())
                .statusCode(statusCode)
                .message(responseMsg)
                .build();
    }

    /**
     * Helper method to get the current timestamp as a string
     *
     * @return formatted date string of the current timestamp
     */
    private static String toCurrentDate() {
        return Date.from(Instant.now()).toString();
    }
}
