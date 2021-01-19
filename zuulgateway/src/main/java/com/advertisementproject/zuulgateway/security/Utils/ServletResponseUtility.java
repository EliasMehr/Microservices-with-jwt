package com.advertisementproject.zuulgateway.security.Utils;

import com.advertisementproject.zuulgateway.api.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;

public class ServletResponseUtility {

    private static final Logger logger = LoggerFactory.getLogger(ServletResponseUtility.class);

    public static <T> void sendResponse(HttpServletResponse response,
                                        Integer status,
                                        T responseBody) throws IOException {

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
        response.getOutputStream().flush();

    }

    public static void sendErrorResponse(HttpServletResponse response,
                                         ErrorResponse errorResponse) throws IOException {

        logger.warn(errorResponse.toString());
        sendResponse(response, errorResponse.getStatusCode(), errorResponse);

    }

    public static ErrorResponse toErrorResponse(String responseMsg, int statusCode) {
        return ErrorResponse.builder()
                .timestamp(toCurrentDate())
                .statusCode(statusCode)
                .message(responseMsg)
                .build();
    }

    private static String toCurrentDate() {
        return Date.from(Instant.now()).toString();
    }
}
