package com.advertisementproject.zuulgateway.ZuulFilter;

import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * Zuul Filter that extracts the user id from a bearer token in the request header and attaches the user id to be
 * forwarded to the requested endpoint. Also logs which type of request is being routed and to which endpoint.
 */
@Slf4j
@RequiredArgsConstructor
public class ZuulRequestFilter extends ZuulFilter {

    /**
     * Service that handles JWT related tasks such as extracting the subject from a JWT token.
     */
    private final JwtUtils jwtUtils;

    /**
     * Sets the type of filter
     *
     * @return string indicating the type of filter
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * Sets the filter order
     *
     * @return integer indicating the filter order
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * Sets whether filters should be run
     *
     * @return always returns true
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * Runs the filter. Extracts user id from jwt token in header using JwtUtils and attaches it to the header of the
     * request which is about to be forwarded. Logs which type of request has been sent and to which endpoint.
     *
     * @return null
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        String token = header.replace("Bearer ", "");
        String userId = jwtUtils.extractSubject(token);

        ctx.addZuulRequestHeader("userId", userId);
        return null;
    }

}
