package com.advertisementproject.zuulgateway.api.exceptions;

import com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ServletResponseUtility.sendResponse(
                response,
                401,
                ServletResponseUtility.toResponseMessage(
                        authException.getLocalizedMessage(),
                        401));

    }
}
