package com.advertisementproject.zuulgateway.security.Utils;

import com.advertisementproject.zuulgateway.api.exceptions.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;

public class ServletResponseUtility {

    public static <T> void sendResponse(HttpServletResponse response,
                                        Integer status,
                                        T responseBody) throws IOException {

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
        response.getOutputStream().flush();
    }

    public static ErrorMessage toResponseMessage(String responseMsg, int statusCode) {
        return ErrorMessage.builder()
                .timestamp(toCurrentDate())
                .statusCode(statusCode)
                .message(responseMsg)
                .build();
    }

    private static String toCurrentDate() {
        return Date.from(Instant.now()).toString();
    }
}
